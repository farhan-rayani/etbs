DELIMITER $$

SET NAMES UTF8 $$

DROP PROCEDURE IF EXISTS  `handleNewAndInactiveUsers` $$

CREATE PROCEDURE `handleNewAndInactiveUsers`(in password varchar(2000),out  status varchar(2000))
BEGIN
DECLARE code CHAR(5) DEFAULT '00000';
DECLARE msg TEXT;
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
ROLLBACK;

 GET DIAGNOSTICS CONDITION 1 code = RETURNED_SQLSTATE, msg = MESSAGE_TEXT;
SELECT 'ERROR Occured'||msg into status ;
END;

SET SQL_SAFE_UPDATES = 0;
SET autocommit=0;


insert into user (version, account_expired, account_locked, enabled, password_expired, username, email,password)
select distinct 0,0,0,1,0,SUBSTRING_INDEX(Email_address, '@', 1),email_address, password 
from staff_details where Email_address not in
(SELECT email FROM user) and employee_status='ACTIVE';
INSERT INTO user_role
(user_id,
role_id)
select id,2 from user
where id not in (select user_id from user_role);

UPDATE user 
SET 
    account_locked = 1,
    enabled = 0
WHERE
    email IN (SELECT 
            email_address
        FROM
            staff_details
        WHERE
            UPPER(employee_status) = 'INACTIVE');
            
 commit;   

SELECT 'Successfully Completed' into status ;
END $$

DELIMITER ;