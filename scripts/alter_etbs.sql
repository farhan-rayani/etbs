ALTER TABLE `phone_bill_summary` 
ADD COLUMN `department_id` INT(11) NULL COMMENT '' ;

ALTER TABLE `staff_phone_details` 
CHANGE COLUMN `phone_status` `phone_status` VARCHAR(15) NOT NULL DEFAULT 'ACTIVE' ;

ALTER TABLE `phone_bill_details` 
ADD COLUMN `modified_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '' ;

ALTER TABLE `phone_bill_summary` 
CHANGE COLUMN `call_amount` `call_amount` DECIMAL(10,4) NULL DEFAULT NULL ;

-- Added on 09/09/2015  start ----
ALTER TABLE `phone_bill_details` drop column  `modified_date` ;
ALTER TABLE `phone_bill_details` ADD COLUMN `modified_date` DATETIME NULL DEFAULT NULL;


ALTER TABLE `phone_bill_details` 
ADD COLUMN `created_by` VARCHAR(20) NULL COMMENT '' ,
ADD COLUMN `modified_by` VARCHAR(20) NULL COMMENT '';


ALTER TABLE `phone_bill_summary` 
ADD COLUMN `created_by` VARCHAR(20) NULL COMMENT '' ;


ALTER TABLE  regular_contact_details
ADD COLUMN `created_by` VARCHAR(20) NULL COMMENT '' ,
ADD COLUMN `modified_by` VARCHAR(20) NULL COMMENT '',
ADD COLUMN `modified_date` DATETIME NULL DEFAULT NULL;

-- Added on 09/09/2015  Ends ----
-- Added on 14/09/2015   ----

ALTER TABLE `monthly_bill_window` 
ADD COLUMN `version` BIGINT(20) NULL;


CREATE TABLE `audit_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `actor` varchar(45) DEFAULT NULL,
  `class_name` varchar(45) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `event_name` varchar(45) DEFAULT NULL,
  `last_updated` varchar(45) DEFAULT NULL,
  `new_value` varchar(45) DEFAULT NULL,
  `old_value` varchar(45) DEFAULT NULL,
  `persisted_object_id` varchar(45) DEFAULT NULL,
  `persisted_object_version` varchar(45) DEFAULT NULL,
  `property_name` varchar(45) DEFAULT NULL,
  `uri` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Added on 15/09/2015  ..already Run in prod  ----
set sql_safe_updates =0;
update monthly_bill_window set version =0;

-- Added on 18/09/2015  ..already Run in prod  ----
ALTER TABLE `staff_details` 
ADD INDEX `emailIndex` (`Email_address` ASC)  COMMENT '';


-- Added on 19/09/2015 
ALTER TABLE regular_contact_details
ADD CONSTRAINT uk_regular_contact_details UNIQUE (staff_id,destination_no);

set sql_safe_updates =0;

Delete from regular_contact_details where destination_no in ('EtisalatRoaming','EtisalatData')

ALTER TABLE `staff_details` 
ADD COLUMN `description` VARCHAR(200) NULL AFTER `is_dummy`;

ALTER TABLE `staff_phone_details` 
ADD COLUMN `description` VARCHAR(200) NULL DEFAULT NULL AFTER `sim_type`;
