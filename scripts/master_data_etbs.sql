-- added by Farhan 09/08/2015
INSERT INTO `error_codes` (`error_code`, `error_description`, `active_indicator`, `creation_date`) VALUES ('CALL_DURATION_ZERO', 'When call duration is zero', 'Y', '2015-06-09 07:50:14');
INSERT INTO `error_codes` (`error_code`, `error_description`, `active_indicator`, `creation_date`, `error_query`, `error_sequence`) VALUES ('PHONE_NOT_FOUND', 'Source phone number not found', 'Y', '2015-06-09 07:50:14');

INSERT INTO `known_special_numbers` (`known_special_number`,`description`,`creation_date`) VALUES ('5566','Sharjah parking','2015-07-28 19:41:58');
INSERT INTO `known_special_numbers` (`known_special_number`,`description`,`creation_date`) VALUES ('7275','Mparking_Dubai','2015-07-28 19:41:58');
INSERT INTO `known_special_numbers` (`known_special_number`,`description`,`creation_date`) VALUES ('RTA mParking','RTA mParking','2015-07-28 19:18:03');

INSERT INTO `etbs_new`.`monthly_bill_window` (`monthly_bill_window_id`, `yyyy_mm`, `start_date`, `end_date`, `created_date`) VALUES ('1', '201506', '2015-06-11 00:00:00', '2015-06-25 00:00:00', '2015-08-18 14:12:43');
