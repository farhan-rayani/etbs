CREATE OR REPLACE FORCE VIEW apps.xxfz_etbs_emp_details (employee_number
                                                        ,employee_name
                                                        ,email_address
                                                        ,grade
                                                        ,division_id
                                                        ,division
                                                        ,dept_id
                                                        ,department
                                                        ,section_id
                                                        ,section
                                                        ,cost_center_code
                                                        ,cost_center_desc
                                                        ,supervisor
                                                        ,supervisor_id
                                                        ,employee_active_flag
                                                        ,organization_id
                                                        ,organization_name)
AS
   SELECT "EMPLOYEE_NUMBER"
         ,"EMPLOYEE_NAME"
         ,"EMAIL_ADDRESS"
         ,"GRADE"
         ,"DIVISION_ID"
         ,"DIVISION"
         ,"DEPT_ID"
         ,"DEPARTMENT"
         ,"SECTION_ID"
         ,"SECTION"
         ,"COST_CENTER_CODE"
         ,"COST_CENTER_DESC"
         ,"SUPERVISOR"
         ,"SUPERVISOR_ID"
         ,"EMPLOYEE_ACTIVE_FLAG"
         ,"ORGANIZATION_ID"
         ,"ORGANIZATION_NAME"
   FROM   (SELECT employee_number
                 ,full_name employee_name
                 ,email_address
                 ,grade
                 , (SELECT organization_id
                    FROM   hr_all_organization_units
                    WHERE  name = division)
                     division_id
                 ,division
                 , (SELECT organization_id
                    FROM   hr_all_organization_units
                    WHERE  name = department)
                     dept_id
                 ,department
                 , (SELECT organization_id
                    FROM   hr_all_organization_units
                    WHERE  name = section)
                     section_id
                 ,section
                 ,cost_center_code
                 ,cost_center_desc
                 ,supervisor
                 ,supervisor_id
                 ,employee_active_flag
                 ,organization_id
                 ,organization_name
           FROM   (SELECT   pap.employee_number
                           ,pap.full_name
                           ,pap.email_address
                           ,xxfz_emp_div (paf.organization_id) division
                           ,xxfz_emp_dept (paf.organization_id) department
                           , (SELECT full_name
                              FROM   per_all_people_f pas
                              WHERE  pas.person_id = paf.supervisor_id
                              AND    SYSDATE BETWEEN pas.effective_start_date AND pas.effective_end_date)
                               supervisor
                           , (SELECT employee_number
                              FROM   per_all_people_f pas
                              WHERE  pas.person_id = paf.supervisor_id
                              AND    SYSDATE BETWEEN pas.effective_start_date AND pas.effective_end_date)
                               supervisor_id
                           , (SELECT name
                              FROM   per_positions
                              WHERE  position_id = paf.position_id
                              AND    organization_id = paf.organization_id)
                               position
                           , (SELECT name
                              FROM   per_grades
                              WHERE  grade_id = paf.grade_id)
                               grade
                           ,pap.date_of_birth
                           ,FLOOR ( (SYSDATE
                                     - (ADD_MONTHS (pap.date_of_birth
                                                   ,-1)
                                        + 1))
                                   / 365)
                               age
                           ,pap.start_date
                           , (SELECT    (   TRUNC (MONTHS_BETWEEN (SYSDATE
                                                                  ,start_date)
                                                   / 12)
                                         || '- Years '
                                         || TRUNC (MONTHS_BETWEEN (SYSDATE
                                                                  ,TO_DATE (   TO_CHAR (start_date
                                                                                       ,'DD-MON')
                                                                            || '-'
                                                                            || (TO_CHAR (start_date
                                                                                        ,'YYYY')
                                                                                + TRUNC (MONTHS_BETWEEN (SYSDATE
                                                                                                        ,start_date)
                                                                                         / 12))))))
                                     || '- Months '
                                     || (TRUNC (SYSDATE)
                                         - ADD_MONTHS ( (TO_DATE (   TO_CHAR (start_date
                                                                             ,'DD-MON')
                                                                  || '-'
                                                                  || (TO_CHAR (start_date
                                                                              ,'YYYY')
                                                                      + TRUNC (MONTHS_BETWEEN (SYSDATE
                                                                                              ,start_date)
                                                                               / 12)))
                                                         - 1)
                                                      ,TRUNC (MONTHS_BETWEEN (SYSDATE
                                                                             ,TO_DATE (   TO_CHAR (start_date
                                                                                                  ,'DD-MON')
                                                                                       || '-'
                                                                                       || (TO_CHAR (start_date
                                                                                                   ,'YYYY')
                                                                                           + TRUNC (MONTHS_BETWEEN (SYSDATE
                                                                                                                   ,start_date)
                                                                                                    / 12)))))))
                                     || '- Days'
                                        days
                              FROM   per_all_people_f
                              WHERE  employee_number = pap.employee_number
                              AND    TRUNC (SYSDATE) BETWEEN effective_start_date AND effective_end_date)
                               tenure
                           , (SELECT meaning
                              FROM   hr_lookups
                              WHERE  lookup_type = 'SEX'
                              AND    lookup_code = pap.sex)
                               gender
                           , (SELECT meaning
                              FROM   hr_lookups
                              WHERE  lookup_code = pap.per_information18
                              AND    lookup_type = 'AE_NATIONALITY')
                               nationality
                           , (SELECT SUM (absence_days)
                              FROM   per_absence_attendances_v
                              WHERE  c_type_desc = 'Annual Leave'
                              AND    person_id = pap.person_id)
                               noof_leaves
                           , (SELECT COUNT (*)
                              FROM   per_contact_relationships
                              WHERE  person_id = pap.person_id
                              AND    contact_type = 'C')
                               noof_child
                           ,hau.organization_id dept_id
                           ,xxfz_emp_sec (paf.organization_id) section
                           ,pak.segment4 cost_center_code
                           ,SUBSTR (gl_flexfields_pkg.get_description_sql (50354
                                                                          ,4
                                                                          ,pak.segment4)
                                   ,1
                                   ,3000)
                               cost_center_desc
                           ,CASE
                               WHEN ppos.leaving_reason IS NULL
                               THEN
                                  'ACTIVE'
                               ELSE
                                  'INACTIVE'
                            END
                               employee_active_flag
                           ,paf.organization_id
                           ,hau.name organization_name
                   FROM     per_all_people_f pap
                           ,per_assignments_f paf
                           ,hr_all_organization_units hau
                           ,pay_cost_allocation_keyflex pak
                           ,per_periods_of_service ppos
                   WHERE    pap.person_id = paf.person_id
                   AND      paf.person_id = ppos.person_id
                   AND      ppos.period_of_service_id = paf.period_of_service_id
                   AND      pap.person_id = ppos.person_id
                   AND      NVL (TRUNC (ppos.actual_termination_date), '31-DEC-4712') = TRUNC (pap.effective_end_date)
                   AND      NVL (TRUNC (ppos.actual_termination_date), '31-DEC-4712') = TRUNC (paf.effective_end_date)
                   AND      pap.employee_number IS NOT NULL
                   AND      paf.organization_id = hau.organization_id
                   AND      hau.cost_allocation_keyflex_id = pak.cost_allocation_keyflex_id(+)
                   ORDER BY pap.employee_number));