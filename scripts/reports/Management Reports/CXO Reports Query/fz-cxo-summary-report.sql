select s.dept_id,
       ifnull(s.department,'NULL') AS Department,
       UPPER(DATE_FORMAT(CONCAT(p.yyyy_mm, '01'), '%b')) AS Month,
       SUM(CASE
        WHEN p.CALL_TYPE = 'Business' THEN (p.CALL_AMOUNT)
        ELSE 0
    END) AS OFFICIAL_Calls
from phone_bill_details p 
inner join staff_details s on s.staff_id=p.staff_id
where p.staff_id in
(SELECT staff_id FROM etbs_new.staff_details sd where dept_id in 
(SELECT DEPARTMENT_ID
FROM etbs_new.department_details 
inner join staff_details s on staff_id =  LM_EMPLOYEE_NUMBER and line_manager_staff_id='{?lm}' and employee_status='ACTIVE'
where  DEPARTMENT_STATUS='ACTIVE'
) 
or dept_id in 
(SELECT department_id FROM etbs_new.department_details where LM_EMPLOYEE_NUMBER='{?lm}' and DEPARTMENT_STATUS='ACTIVE'
)
and sd.employee_status='ACTIVE')
group by s.dept_id,s.department,p.yyyy_mm
order by dept_id