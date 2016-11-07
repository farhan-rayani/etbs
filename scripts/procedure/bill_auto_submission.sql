DELIMITER $$

SET NAMES UTF8 $$

DROP PROCEDURE IF EXISTS  `autosubmit` $$

CREATE PROCEDURE `autosubmit`(out  status varchar(2000))
BEGIN

/**
	 * 1. Get All distinct unknown phone Numbers from Phone Bill Details -- the numbers will not be in staffdetails, regcontact
	 * 2. Insert Into regular contacts as personal 
	 * 3. consolidate for phone nos which are unknown
	 * 4. Update in phonebill details as submitted
	 * 5. Update in phonebill Unknown as personal
	 * 
	 * 
	 */
DECLARE no_more_rows BOOLEAN;
DECLARE num_rows INT DEFAULT 0;
DECLARE phonenumber VARCHAR(255);
DECLARE code CHAR(5) DEFAULT '00000';
DECLARE msg TEXT;

DECLARE unknownphone_cursor CURSOR FOR select distinct source_phone_no from phone_bill_details  where  (call_type = 'UNKNOWN' or is_submit <> "SUBMITTED" ) and department_id is null and yyyy_mm = @billwindow;     


DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
ROLLBACK;
 GET DIAGNOSTICS CONDITION 1 code = RETURNED_SQLSTATE, msg = MESSAGE_TEXT;


SELECT 'ERROR Occured'||msg into status ;

END;
DECLARE CONTINUE HANDLER FOR NOT FOUND
SET no_more_rows = TRUE;

SET SQL_SAFE_UPDATES = 0;
SET autocommit=0;

SELECT 
    MAX(yyyy_mm)
INTO @billwindow FROM
    monthly_bill_window;
    
    
/*INSERT INTO `regular_contact_details`
(`staff_id`,
`destination_no`,
`destination_country`,
`contact_type`,
`dest_person_name`,
`created_by`)
select distinct det.staff_id , det.destination_no,'','Personal','','AutoSubmit' from phone_bill_details det  
 where det.yyyy_mm = @billwindow and det.call_type ='UNKNOWN'
 and destination_no not in ('EtisalatData','EtisalatRoaming');*/

OPEN unknownphone_cursor;
SELECT FOUND_ROWS() INTO num_rows;

 the_loop: LOOP

FETCH unknownphone_cursor INTO phonenumber;

IF no_more_rows THEN
        CLOSE unknownphone_cursor;
        LEAVE the_loop;
    END IF;

 INSERT INTO `phone_bill_summary`
(`service_provider`,
`yyyy_mm`,
`staff_id`,
`source_phone_no`,
`call_amount`,
`call_duration`,
`call_type`,
`created_by`)
SELECT 
    service_provider,
    yyyy_mm,
    staff_id,
    source_phone_no,    
    ROUND(SUM(call_amount),2) call_amount,
    ROUND(SUM(call_duration),2) call_duration,
    'Business',
    'AutoSubmit'
FROM
    phone_bill_details
WHERE
    source_phone_no = phonenumber
        AND yyyy_mm = @billwindow
        and call_type ='Business'
group by service_provider,
    yyyy_mm,
    staff_id,
    source_phone_no;
    
    INSERT INTO `phone_bill_summary`
(`service_provider`,
`yyyy_mm`,
`staff_id`,
`source_phone_no`,
`call_amount`,
`call_duration`,
`call_type`,
`created_by`)
SELECT 
    service_provider,
    yyyy_mm,
    staff_id,
    source_phone_no,   
     ROUND(SUM(call_amount),2) call_amount,
     ROUND(SUM(call_duration),2) call_duration,
    'Personal',
    'AutoSubmit'
FROM
    phone_bill_details
WHERE
    source_phone_no = phonenumber
        AND yyyy_mm = @billwindow
        and call_type <> 'Business'
group by service_provider,
    yyyy_mm,
    staff_id,
    source_phone_no;
    

 END LOOP the_loop;
 
 	-- depaartment start

 
 
  INSERT INTO `phone_bill_summary`
 (`service_provider`,
 `yyyy_mm`,
 `staff_id`,
 `source_phone_no`,
 `call_amount`,
 `call_duration`,
 `call_type`,
 `department_id`,
 `created_by`)
 SELECT 
     service_provider,
     yyyy_mm,
     staff_id,
     source_phone_no,   
      ROUND(SUM(call_amount),2) call_amount,
      ROUND(SUM(call_duration),2) call_duration,
     'Business',
     department_id,
      'AutoSubmit'
 FROM
     phone_bill_details
 WHERE
         yyyy_mm = @billwindow
         and department_id is  not null   
         and staff_id is null
         and is_submit="SUBMITTED" 
 group by service_provider,
     yyyy_mm,     
     source_phone_no,
     department_id;    

	-- depaartment end
-- Dummy Start


 INSERT INTO `phone_bill_summary`
 (`service_provider`,
 `yyyy_mm`,
 `staff_id`,
 `source_phone_no`,
 `call_amount`,
 `call_duration`,
 `call_type`,
 `department_id`,
 `created_by`)
  SELECT 
     det.service_provider,
     det.yyyy_mm,
     det.staff_id,
     det.source_phone_no,   
      ROUND(SUM(det.call_amount),2) call_amount,
      ROUND(SUM(det.call_duration),2) call_duration,
     'Business',
     det.department_id,
     'AutoSubmit'
 FROM
     phone_bill_details det ,staff_details staff
 WHERE
         det.yyyy_mm = @billwindow         
		and det.staff_id =staff.staff_id
        and staff.is_dummy =1       
        and  det.is_submit="SUBMITTED" 
 group by service_provider,
     yyyy_mm,     
     source_phone_no;
-- dummy End
 
UPDATE phone_bill_details 
SET 
    call_type = 'Personal',
    modified_by ='AutoSubmit',
     modified_date = now()
WHERE
    call_type ='UNKNOWN'
 AND yyyy_mm = @billwindow; 
        
UPDATE phone_bill_details 
SET  
    is_submit = 'SUBMITTED',
    modified_date = now(),
     modified_by ='AutoSubmit'
WHERE
 yyyy_mm = @billwindow 
 AND  is_submit <> 'SUBMITTED';
 
 
commit;
SELECT 'Successfully Completed' into status ;
END $$

DELIMITER ;