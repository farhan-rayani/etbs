SELECT Dept_id AS Department_id,
       department,
       cost_centre,
       yyyy_mm,
       Source_No,
       Month,
       staff_id,
       Staff_Name,
       call_duration,
       Personal_Calls,
       OFFICIAL_Calls,
       (OFFICIAL_Calls + Personal_Calls) AS Total_Calls
from
(       
SELECT 
    SD.DEPT_ID,
    sd.department,
    sd.cost_centre,
    pbd.yyyy_mm,
	source_phone_no as  Source_No,
    UPPER(DATE_FORMAT(CONCAT(yyyy_mm, '01'), '%b')) AS Month,
    pbd.staff_id,
    sd.Staff_Name,
    sum(call_duration) AS call_duration,
    SUM(CASE
        WHEN UPPER(pbd.CALL_TYPE) = 'PERSONAL' THEN (pbd.CALL_AMOUNt)
        ELSE 0
    END) AS Personal_Calls,
    SUM(CASE
        WHEN UPPER(pbd.CALL_TYPE) = 'BUSINESS' THEN (pbd.CALL_AMOUNT)
        ELSE 0
    END) AS OFFICIAL_Calls    
FROM
    staff_details sd
        INNER JOIN
    phone_bill_details pbd ON sd.staff_id = pbd.staff_id
   -- WHERE 
    --  sd.employee_grade not in ('E.11','E.10','E.09')
    GROUP BY department , yyyy_mm , Source_No,staff_id ,DEPT_ID, Staff_Name)AS A    
    order by cost_centre,staff_id; 