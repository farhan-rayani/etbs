-- Added by Farhan on 28/06/2015
ALTER TABLE `etbs1`.`upload_phone_bill_file` 
ADD COLUMN `migrated` BIT(1) NOT NULL DEFAULT 0 AFTER `flex_field5`;

ALTER TABLE `etbs1`.`phone_bill_details` 
ADD COLUMN `is_submit` BIT(1) NOT NULL DEFAULT 0 AFTER `flex_field5`;

-- Added by Farhan on 30/06/2015
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `authority` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_irsamgnera6angm0prq1kemt2` (`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_it77eq964jhfqtu54081ebtio` (`role_id`),
  CONSTRAINT `FK_apcc8lxk2xnug8377fatvbn04` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_it77eq964jhfqtu54081ebtio` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Added by Philip on 30/06/2015 for table changes by Sujith 
ALTER TABLE `etbs1`.`staff_details` 
DROP COLUMN `service_type`;

ALTER TABLE `etbs1`.`staff_details` 
DROP COLUMN `First_Name`;

ALTER TABLE `etbs1`.`staff_details` 
DROP COLUMN `Last_Name`;

ALTER TABLE `etbs1`.`staff_details` 
ADD COLUMN `Staff_Name` VARCHAR(200) NULL ;

-- Added by Farhan on 01/07/2015
ALTER TABLE `etbs1`.`phone_bill_details` 
CHANGE COLUMN `service_provider` `service_provider` VARCHAR(2) NOT NULL ,
CHANGE COLUMN `yyyy_mm` `yyyy_mm` INT(6) NOT NULL ,
CHANGE COLUMN `source_phone_no` `source_phone_no` VARCHAR(15) NOT NULL ,
CHANGE COLUMN `call_date_time` `call_date_time` TIMESTAMP NOT NULL ,
CHANGE COLUMN `destination_no` `destination_no` VARCHAR(15) NOT NULL ,
CHANGE COLUMN `call_duration` `call_duration` INT(10) NOT NULL ,
CHANGE COLUMN `call_amount` `call_amount` DECIMAL(8,4) NOT NULL ,
CHANGE COLUMN `call_type` `call_type` VARCHAR(10) NOT NULL ;
CHANGE COLUMN `staff_id` `staff_id` VARCHAR(15) NOT NULL ,
CHANGE COLUMN `upload_phone_bill_detail_id` `upload_phone_bill_detail_id` INT(11) NOT NULL ;

-- Added by Philip on 2/07/2015

ALTER TABLE `etbs1`.`regular_contact_details` 
ADD COLUMN `dest_person_name` VARCHAR(45) NULL ;

-- Added by Philip on 2/07/2015
-- ensure to fill with dummy values before alter
update staff_details set Staff_Name= 'Staff' where Staff_Name is null
update staff_details set email_address ="staff@email.com" where email_address is null
ALTER TABLE `etbs1`.`staff_details` 
CHANGE COLUMN `Email_address` `Email_address` VARCHAR(80) NOT NULL COMMENT '' ,
CHANGE COLUMN `Staff_Name` `Staff_Name` VARCHAR(200) NOT NULL COMMENT '' ;

-- Added by Sujith on 5/07/2015 to replace detination name to destination person name
ALTER TABLE `upload_phone_bill_details` 
CHANGE COLUMN `destination_name` `destination_country` VARCHAR(45) NULL DEFAULT NULL ;
ALTER TABLE `upload_phone_bill_details` 
ADD COLUMN `destination_name` VARCHAR(45) NULL AFTER `flex_field5`;
ALTER TABLE `phone_bill_details` 
CHANGE COLUMN `destination_name` `destination_country` VARCHAR(200) NULL DEFAULT NULL ;
ALTER TABLE `phone_bill_details` 
ADD COLUMN `destination_name` VARCHAR(200) NULL AFTER `flex_field5`;

-- Added by Sujith on 5/07/2015
ALTER TABLE `staff_details` 
ADD COLUMN `dept_id` INT NULL AFTER `creation_date`,
ADD COLUMN `department` VARCHAR(200) NULL AFTER `dept_id`;

-- Added by Farhan of 5/07/2015 no need of destination_name
ALTER TABLE `etbs1`.`upload_phone_bill_details` 
DROP COLUMN `destination_name`;
ALTER TABLE `etbs1`.`phone_bill_details` 
DROP COLUMN `destination_name`;

-- Added by Farhan of 5/07/2015 Also in these 2 tables
ALTER TABLE `etbs1`.`error_phone_bill_details` 
CHANGE COLUMN `destination_name` `destination_country` VARCHAR(200) NULL DEFAULT NULL ;
ALTER TABLE `etbs1`.`regular_contact_details` 
CHANGE COLUMN `destination_name` `destination_country` VARCHAR(45) NULL DEFAULT NULL ;


-- Added by Farhan of 6/07/2015 Too add one more error code
ALTER TABLE `etbs1`.`error_codes` 
CHANGE COLUMN `error_code` `error_code` VARCHAR(30) NOT NULL DEFAULT '' ,
CHANGE COLUMN `error_description` `error_description` VARCHAR(100) NULL ;
INSERT INTO `etbs1`.`error_codes` (`error_code`, `error_description`, `active_indicator`, `creation_date`) VALUES ('PHONE_STAFF_NOT_ACTIVE', 'Staff not active', 'Y', '2015-06-08 20:49:23');

-- Added by Farhan of 8/07/2015 change is_submit from boolean to string
ALTER TABLE `phone_bill_details` 
CHANGE COLUMN `is_submit` `is_submit` VARCHAR(20) NOT NULL DEFAULT 'NOT_SUBMITTED' COMMENT '' ;
update etbs1.phone_bill_details set is_submit="SUBMITTED" where is_submit="1"
update etbs1.phone_bill_details set is_submit="NOT_SUBMITTED" where is_submit="0"

-- added by PJ on 09/07/2015
ALTER TABLE `phone_bill_details` 
ADD COLUMN `caller_name` VARCHAR(45) NULL COMMENT '';
ALTER TABLE `phone_bill_details` 
ADD COLUMN `table_source` VARCHAR(45) NULL COMMENT '';

--added by Farhan Rayani 13/07/2015 call_duration in summary table
ALTER TABLE `etbs1`.`phone_bill_summary` 
ADD COLUMN `call_duration` INT(10) NOT NULL DEFAULT 0 AFTER `call_amount`;

--added by Farhan Rayani 13/07/2015
ALTER TABLE `staff_details` 
CHANGE COLUMN `Email_address` `Email_address` VARCHAR(80) NOT NULL ,
CHANGE COLUMN `Staff_Name` `Staff_Name` VARCHAR(200) NOT NULL ;

--added by Farhan Rayani 15/07/2015 Upload file status
ALTER TABLE `upload_phone_bill_file`
CHANGE COLUMN `migrated` `migrated` BIT(1) NOT NULL DEFAULT b'0' AFTER `creation_date`,
CHANGE COLUMN `success_count` `success_count` INT(10) NOT NULL DEFAULT 0 ,
CHANGE COLUMN `error_count` `error_count` INT(10) NOT NULL DEFAULT 0 ,
ADD COLUMN `upload_status` VARCHAR(25) NOT NULL DEFAULT 'PENDING' AFTER `migrated`,
ADD COLUMN `upload_count` INT NOT NULL DEFAULT 0 AFTER `upload_status`,
ADD COLUMN `upload_success_count` INT NOT NULL DEFAULT 0 AFTER `upload_count`,
ADD COLUMN `upload_error_count` INT NOT NULL DEFAULT 0 AFTER `upload_success_count`;
DROP COLUMN `uploaded`;

--added by Farhan Rayani 19/07/2015
ALTER TABLE `phone_bill_details` 
CHANGE COLUMN `call_type` `call_type` VARCHAR(20) NOT NULL ;
<<<<<<< HEAD

--added by Farhan Rayani 23/07/2015 for start billing date
ALTER TABLE `admin_tab` 
ADD COLUMN `start_date` DATETIME NULL DEFAULT NULL AFTER `amount_limit`;

--added by Farhan Rayani 23/07/2015 for increasing dest no len
ALTER TABLE `upload_phone_bill_details` 
CHANGE COLUMN `destination_no` `destination_no` VARCHAR(75) NULL DEFAULT NULL ;

--added by Philip 22/07/2015
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'E.11  ',500.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'E.10',1000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'E.08',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'E.09',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'M.08',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'P.05',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'M.07',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'FC.07',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'P.04',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'P.06',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'CC.03',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'CC.02',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'S.04',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'S.02',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'S.03',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'S.05',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'M.06',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'FC.06',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'P.03',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'ENG.07',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'ENG.06',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'ENG.05',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'S.01',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'ENG.04',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'ENG.03',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'P.07',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'ENG.02',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'FC.05',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'FC.04',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
INSERT INTO `admin_tab` (`yyyy_mm`,`employee_grade`,`amount_limit`,`deadline_date`,`service_type`,`contact_type`,`creation_date`) VALUES (0,'CS.02',2000.0000,'2015-07-30 00:00:00',NULL,NULL,'2015-06-28 14:10:12');
--added by Philip 23/07/2015
-- drop table admin_tab;


CREATE TABLE `monthly_bill_window` (
  `monthly_bill_window_id` int not null AUTO_INCREMENT ,
  `yyyy_mm` int(6)  not null,
  `start_date` datetime not null ,
  `end_date` datetime not null ,
   `created_date` timestamp default current_timestamp ,
  primary key (`monthly_bill_window_id`)  comment '');

CREATE TABLE `grade_limit` (
  `grade_limit_id` int not null AUTO_INCREMENT,
  `employe_grade` varchar(6) not null,
  `amount_limit` decimal(8,4) not null,
  `created_date` timestamp default current_timestamp  ,
  primary key (`grade_limit_id`)  comment '');

 --added by Philip 25/07/2015 
set sql_safe_updates=0 ;
update phone_bill_details set call_type ="BUSINESS" where call_type ="OFFICIAL";
update phone_bill_summary set call_type ="BUSINESS" where call_type ="OFFICIAL";

--added by Farhan 26/07/2015
ALTER TABLE `upload_phone_bill_details` 
CHANGE COLUMN `call_duration` `call_duration` DECIMAL(8,4) NULL DEFAULT NULL ;

ALTER TABLE `phone_bill_details` 
CHANGE COLUMN `call_duration` `call_duration` DECIMAL(8,4) NOT NULL ;

--added by Farhan 27/07/2015
ALTER TABLE `phone_bill_details` 
CHANGE COLUMN `destination_no` `destination_no` VARCHAR(75) NOT NULL ;

 --added by Philip 27/07/2015 
INSERT INTO `monthly_bill_window` (`yyyy_mm`,`start_date`,`end_date`,`created_date`) VALUES (201506,'2015-07-11 00:00:00','2015-07-30 00:00:00','2015-07-26 14:12:43');

ALTER TABLE `regular_contact_details` 
CHANGE COLUMN `service_type` `phone_type` VARCHAR(15) NULL DEFAULT NULL ;


 --added by Philip 28/07/2015 
INSERT INTO `monthly_bill_window` (`yyyy_mm`,`start_date`,`end_date`,`created_date`) VALUES (201505,'2015-06-11 00:00:00','2015-06-30 00:00:00','2015-07-26 14:12:43');
INSERT INTO `monthly_bill_window` (`yyyy_mm`,`start_date`,`end_date`,`created_date`) VALUES (201504,'2015-05-11 00:00:00','2015-05-30 00:00:00','2015-07-26 14:12:43');
set sql_safe_updates=0 ;
update  phone_bill_details set caller_name ='RTA mParking' where destination_no ='RTA mParking';
update  phone_bill_details set destination_no ='RTA_mParking' where destination_no ='RTA mParking'
INSERT INTO `known_special_numbers` (`known_special_number`,`description`,`creation_date`) VALUES ('5566','Sharjah parking','2015-07-28 19:41:58');
INSERT INTO `known_special_numbers` (`known_special_number`,`description`,`creation_date`) VALUES ('7275','Mparking_Dubai','2015-07-28 19:41:58');
INSERT INTO `known_special_numbers` (`known_special_number`,`description`,`creation_date`) VALUES ('RTA mParking','RTA mParking','2015-07-28 19:18:03');

ALTER TABLE `phone_bill_details` 
CHANGE COLUMN `call_type` `call_type` VARCHAR(20) NOT NULL ;
ALTER TABLE `phone_bill_details` 
CHANGE COLUMN `caller_name` `caller_name` VARCHAR(100) NULL DEFAULT NULL ;
update   phone_bill_details set call_type='SPECIAL_NUMBER' where destination_no ='RTA_mParking';
update   phone_bill_details set call_type='SPECIAL_NUMBER' where destination_no ='5566';
update   phone_bill_details set call_type='SPECIAL_NUMBER' where destination_no ='7275';

--added by Farhan 29/07/2015
ALTER TABLE `upload_phone_bill_details` 
CHANGE COLUMN `call_duration` `call_duration` DECIMAL(10,4) NULL DEFAULT NULL ;
ALTER TABLE `phone_bill_details` 
CHANGE COLUMN `call_duration` `call_duration` DECIMAL(10,4) NOT NULL ;
ALTER TABLE `error_phone_bill_details` 
CHANGE COLUMN `call_duration` `call_duration` DECIMAL(10,4) NULL DEFAULT NULL ;
ALTER TABLE `upload_phone_bill_details` 
CHANGE COLUMN `destination_country` `destination_country` VARCHAR(75) NULL DEFAULT NULL ;
ALTER TABLE `phone_bill_details` 
CHANGE COLUMN `destination_country` `destination_country` VARCHAR(75) NULL DEFAULT NULL ;
ALTER TABLE `error_phone_bill_details` 
CHANGE COLUMN `destination_no` `destination_no` VARCHAR(75) NULL DEFAULT NULL ;


--added by Farhan 29/07/2015 hardcoded need to be implemented in migration
update phone_bill_details set destination_no='Etisalat Roaming' where destination_no like '%etisalat.ae%'

--added by Farhan 03/08/2015
ALTER TABLE `error_phone_bill_details` 
CHANGE COLUMN `call_date_time` `call_date_time` TIMESTAMP NULL DEFAULT NULL ;

ALTER TABLE `regular_contact_details` 
CHANGE COLUMN `destination_no` `destination_no` VARCHAR(75) NULL DEFAULT NULL ;

ALTER TABLE `regular_contact_details` 
CHANGE COLUMN `dest_person_name` `dest_person_name` VARCHAR(100) NULL DEFAULT NULL ;

ALTER TABLE `phone_bill_details` 
CHANGE COLUMN `call_type` `call_type` VARCHAR(20) NOT NULL ;

ALTER TABLE `error_phone_bill_details` 
CHANGE COLUMN `call_type` `call_type` VARCHAR(20) NULL DEFAULT NULL ;


--added by Philip 05/08/2015

DELIMITER $$


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
DECLARE unknownphone_cursor CURSOR FOR select distinct source_phone_no from phone_bill_details  where call_type ='UNKNOWN';     


DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
ROLLBACK;
   GET DIAGNOSTICS CONDITION 1
        code = RETURNED_SQLSTATE, msg = MESSAGE_TEXT;
SELECT 'An error  occurred '||MESSAGE_TEXT into status ;
-- Insert into error log 
END;
DECLARE CONTINUE HANDLER FOR NOT FOUND
SET no_more_rows = TRUE;

SET SQL_SAFE_UPDATES = 0;
SET autocommit=0;

SELECT 
    MAX(yyyy_mm)
INTO @billwindow FROM
    monthly_bill_window;
    
    
INSERT INTO `regular_contact_details`
(`staff_id`,
`destination_no`,
`destination_country`,
`contact_type`,
`dest_person_name`)
select distinct det.staff_id , det.destination_no,det.destination_country,'PERSONAL',det.caller_name from phone_bill_details det  
 where det.yyyy_mm = @billwindow and det.call_type ='UNKNOWN';

OPEN unknownphone_cursor;
SELECT FOUND_ROWS() INTO num_rows;

 the_loop: LOOP

FETCH unknownphone_cursor INTO phonenumber;

IF no_more_rows THEN
        CLOSE unknownphone_cursor;
        LEAVE the_loop;
    END IF;

DELETE FROM phone_bill_summary 
WHERE yyyy_mm =@billwindow
AND source_phone_no = phonenumber;
    
    INSERT INTO `phone_bill_summary`
(`service_provider`,
`yyyy_mm`,
`staff_id`,
`source_phone_no`,
`call_amount`,
`call_duration`,
`call_type`)
SELECT 
    service_provider,
    yyyy_mm,
    staff_id,
    source_phone_no,    
    ROUND(SUM(call_amount),2) call_amount,
    ROUND(SUM(call_duration),2) call_duration,
    call_type
FROM
    phone_bill_details
WHERE
    source_phone_no = phonenumber
        AND yyyy_mm = @billwindow
        and call_type ='BUSINESS'
group by service_provider,
    yyyy_mm,
    staff_id,
    source_phone_no,
    call_type; 
    
    INSERT INTO `phone_bill_summary`
(`service_provider`,
`yyyy_mm`,
`staff_id`,
`source_phone_no`,
`call_amount`,
`call_duration`,
`call_type`)
SELECT 
    service_provider,
    yyyy_mm,
    staff_id,
    source_phone_no,   
     ROUND(SUM(call_amount),2) call_amount,
     ROUND(SUM(call_duration),2) call_duration,
    'PERSONAL'
FROM
    phone_bill_details
WHERE
    source_phone_no = phonenumber
        AND yyyy_mm = @billwindow
        and call_type <> 'BUSINESS'
group by service_provider,
    yyyy_mm,
    staff_id,
    source_phone_no,
    call_type;
    

 END LOOP the_loop;
 
UPDATE phone_bill_details 
SET 
    call_type = 'PERSONAL'
WHERE
    call_type IN('UNKNOWN')
 AND yyyy_mm = @billwindow; 
        
UPDATE phone_bill_details 
SET  
    is_submit = 'SUBMITTED'
WHERE
 yyyy_mm = @billwindow;    
 
 
commit;
SELECT 'Successfully Completed' into status ;
END $$

DELIMITER ;
-- Ends procedure 

--added by Farhan 09/08/2015
INSERT INTO `error_codes` (`error_code`, `error_description`, `active_indicator`, `creation_date`) VALUES ('CALL_DURATION_ZERO', 'When call duration is zero', 'Y', '2015-06-09 07:50:14');

--added by Farhan 09/08/2015
ALTER TABLE `error_phone_bill_details` 
ADD COLUMN `caller_name` VARCHAR(100) NULL AFTER `flex_field5`,
ADD COLUMN `table_source` VARCHAR(45) NULL AFTER `caller_name`;

--added by Philip 10/08/2015
drop table admin_tab;


