SELECT A.cost_centre,
       A.yyyy_mm,
       -- CAST(MB.end_date AS DATE) AS Submittion_End_Date,
       -- CAST(NOW() AS DATE) as CurrentDate,
       A.Month,
       Source_No,
       staff_id,
       Staff_Name,
       call_duration,
       Personal_Calls,
       OFFICIAL_Calls,
       (OFFICIAL_Calls + Personal_Calls) AS Total_Calls
from
(     
SELECT 
    sd.cost_centre,
    pbd.yyyy_mm,
   source_phone_no as  Source_No,
   UPPER(DATE_FORMAT(CONCAT(yyyy_mm, '01'), '%b')) AS Month,
    pbd.staff_id,
    sd.Staff_Name,
    SUM(call_duration) AS call_duration,
    -- call_type,
    sum((CASE
        WHEN pbd.CALL_TYPE = 'PERSONAL' THEN (pbd.CALL_AMOUNt)
        ELSE 0
    END)) AS Personal_Calls,
    SUM((CASE
        WHEN pbd.CALL_TYPE = 'BUSINESS' THEN (pbd.CALL_AMOUNT)
        ELSE 0
    END)) AS OFFICIAL_Calls 
FROM
    staff_details sd
        INNER JOIN
    phone_bill_details pbd ON sd.staff_id = pbd.staff_id
    where service_provider = 'ET'
    and sd.is_dummy = 0
    GROUP BY sd.cost_centre,
    pbd.yyyy_mm,
    UPPER(DATE_FORMAT(CONCAT(yyyy_mm, '01'), '%b')),
    pbd.staff_id,
    source_phone_no,
    sd.Staff_Name    
    )AS A
    LEFT OUTER JOIN monthly_bill_window MB ON A.yyyy_mm = MB.yyyy_mm;
    -- WHERE DATE_ADD((CAST(MB.yyyy_mm AS DATE)),INTERVAL 1 DAY) = CAST(NOW() AS DATE);