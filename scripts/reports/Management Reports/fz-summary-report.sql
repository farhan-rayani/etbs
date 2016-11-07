SELECT department,
      department_id,
       Month,
       call_duration,
       -- Personal_Calls,
       OFFICIAL_Calls
       -- (OFFICIAL_Calls) AS Total_Calls
from
(       
SELECT 
    -- ifnull(sd.department,'Executive') AS Department,
     (CASE 
         WHEN sd.dept_id IS NULL AND SD.IS_DUMMY = 0 THEN 'Executive'
     WHEN sd.dept_id IS NULL AND SD.IS_DUMMY = 1 THEN 'Dummy'
     ELSE sd.department
     END) AS Department,
     ifnull(sd.dept_id,'NULL') as Department_id,
    UPPER(DATE_FORMAT(CONCAT(yyyy_mm, '01'), '%b')) AS Month,
    SUM(call_duration) AS call_duration,
    SUM(CASE
        WHEN pbd.CALL_TYPE = 'Personal' THEN (pbd.CALL_AMOUNt)
        ELSE 0
    END) AS Personal_Calls,
    SUM(CASE
        WHEN pbd.CALL_TYPE = 'Business' THEN (pbd.CALL_AMOUNT)
        ELSE 0
    END) AS OFFICIAL_Calls    
FROM
    staff_details sd
        INNER JOIN
    phone_bill_details pbd ON sd.staff_id = pbd.staff_id
   GROUP BY(CASE 
         WHEN sd.dept_id IS NULL AND SD.IS_DUMMY = 0 THEN 'Executive'
     WHEN sd.dept_id IS NULL AND SD.IS_DUMMY = 1 THEN 'Dummy'
     ELSE sd.department
     END),
    sd.dept_id,
    UPPER(DATE_FORMAT(CONCAT(yyyy_mm, '01'), '%b'))
    )AS A;