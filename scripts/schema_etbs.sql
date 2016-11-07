-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: etbsdemo
-- ------------------------------------------------------
-- Server version	5.6.23-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin_tab`
--

DROP TABLE IF EXISTS `admin_tab`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_tab` (
  `admin_tab_id` int(11) NOT NULL AUTO_INCREMENT,
  `yyyy_mm` int(6) DEFAULT NULL,
  `employee_grade` varchar(6) DEFAULT NULL,
  `amount_limit` decimal(8,4) DEFAULT NULL,
  `deadline_date` datetime DEFAULT NULL,
  `service_type` varchar(15) DEFAULT NULL,
  `contact_type` varchar(15) DEFAULT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`admin_tab_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `department_details`
--

DROP TABLE IF EXISTS `department_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department_details` (
  `DEPARTMENT_ID` int(11) NOT NULL,
  `BUSINESS_GROUP_ID` int(11) DEFAULT NULL,
  `DEPARTMENT_TYPE_CODE` varchar(30) DEFAULT NULL,
  `DATE_FROM` datetime DEFAULT NULL,
  `DATE_TO` datetime DEFAULT NULL,
  `DEPARTMENT_TYPE` varchar(4000) DEFAULT NULL,
  `DEPARTMENT_NAME` varchar(240) DEFAULT NULL,
  `COST_CENTRE_ID` varchar(60) DEFAULT NULL,
  `LM_EMPLOYEE_NUMBER` varchar(30) DEFAULT NULL,
  `LM_FULL_NAME` varchar(240) DEFAULT NULL,
  `DEPARTMENT_STATUS` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`DEPARTMENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `error_codes`
--

DROP TABLE IF EXISTS `error_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `error_codes` (
  `error_code` varchar(30) NOT NULL DEFAULT '',
  `error_description` varchar(100) DEFAULT NULL,
  `active_indicator` varchar(1) DEFAULT 'Y',
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `error_query` varchar(5000) DEFAULT NULL,
  `error_sequence` int(11) DEFAULT NULL,
  PRIMARY KEY (`error_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `error_phone_bill_details`
--

DROP TABLE IF EXISTS `error_phone_bill_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `error_phone_bill_details` (
  `error_phone_bill_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_provider` varchar(2) DEFAULT NULL,
  `yyyy_mm` int(6) DEFAULT NULL,
  `staff_id` varchar(15) DEFAULT NULL,
  `upload_phone_bill_detail_id` int(11) DEFAULT NULL,
  `source_phone_no` varchar(15) DEFAULT NULL,
  `call_date_time` datetime DEFAULT NULL,
  `destination_no` varchar(75) DEFAULT NULL,
  `destination_country` varchar(200) DEFAULT NULL,
  `call_duration` decimal(10,4) DEFAULT NULL,
  `call_amount` decimal(8,4) DEFAULT NULL,
  `call_type` varchar(20) DEFAULT NULL,
  `error_code` varchar(20) DEFAULT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `flex_field1` varchar(100) DEFAULT NULL,
  `flex_field2` varchar(100) DEFAULT NULL,
  `flex_field3` varchar(100) DEFAULT NULL,
  `flex_field4` varchar(100) DEFAULT NULL,
  `flex_field5` varchar(100) DEFAULT NULL,
  `caller_name` varchar(100) DEFAULT NULL,
  `table_source` varchar(45) DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  `charge_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`error_phone_bill_detail_id`),
  KEY `upload_phone_bill_detail_id` (`upload_phone_bill_detail_id`),
  KEY `error_phone_bill_details_ibfk_2` (`error_code`),
  CONSTRAINT `error_phone_bill_details_ibfk_1` FOREIGN KEY (`upload_phone_bill_detail_id`) REFERENCES `upload_phone_bill_details` (`upload_phone_bill_detail_id`),
  CONSTRAINT `error_phone_bill_details_ibfk_2` FOREIGN KEY (`error_code`) REFERENCES `error_codes` (`error_code`)
) ENGINE=InnoDB AUTO_INCREMENT=315702 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `grade_limit`
--

DROP TABLE IF EXISTS `grade_limit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grade_limit` (
  `grade_limit_id` int(11) NOT NULL AUTO_INCREMENT,
  `employe_grade` varchar(6) NOT NULL,
  `amount_limit` decimal(8,4) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`grade_limit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `known_special_numbers`
--

DROP TABLE IF EXISTS `known_special_numbers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `known_special_numbers` (
  `known_special_number` varchar(15) NOT NULL,
  `description` varchar(50) DEFAULT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`known_special_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `monthly_bill_window`
--

DROP TABLE IF EXISTS `monthly_bill_window`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monthly_bill_window` (
  `monthly_bill_window_id` int(11) NOT NULL AUTO_INCREMENT,
  `yyyy_mm` int(6) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`monthly_bill_window_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `phone_bill_details`
--

DROP TABLE IF EXISTS `phone_bill_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phone_bill_details` (
  `phone_bill_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_provider` varchar(2) NOT NULL,
  `yyyy_mm` int(6) NOT NULL,
  `staff_id` varchar(15) DEFAULT NULL,
  `upload_phone_bill_detail_id` int(11) NOT NULL,
  `source_phone_no` varchar(15) NOT NULL,
  `call_date_time` datetime DEFAULT NULL,
  `destination_no` varchar(75) NOT NULL,
  `destination_country` varchar(75) DEFAULT NULL,
  `call_duration` decimal(10,4) NOT NULL,
  `call_amount` decimal(8,4) NOT NULL,
  `call_type` varchar(20) NOT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `flex_field1` varchar(100) DEFAULT NULL,
  `flex_field2` varchar(100) DEFAULT NULL,
  `flex_field3` varchar(100) DEFAULT NULL,
  `flex_field4` varchar(100) DEFAULT NULL,
  `flex_field5` varchar(100) DEFAULT NULL,
  `is_submit` varchar(20) NOT NULL DEFAULT 'NOT_SUBMITTED',
  `caller_name` varchar(100) DEFAULT NULL,
  `table_source` varchar(45) DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  `charge_type` varchar(20) DEFAULT NULL,
  `source_service_type` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`phone_bill_detail_id`),
  KEY `upload_phone_bill_detail_id` (`upload_phone_bill_detail_id`),
  KEY `staff_id` (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1156737 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `phone_bill_summary`
--

DROP TABLE IF EXISTS `phone_bill_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phone_bill_summary` (
  `phone_bill_summary_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_provider` varchar(2) DEFAULT NULL,
  `yyyy_mm` int(6) DEFAULT NULL,
  `staff_id` varchar(15) DEFAULT NULL,
  `source_phone_no` varchar(15) DEFAULT NULL,
  `call_amount` decimal(8,4) DEFAULT NULL,
  `call_duration` int(10) NOT NULL DEFAULT '0',
  `call_type` varchar(10) DEFAULT NULL,
  `actioned` varchar(1) DEFAULT NULL,
  `excess_bill` varchar(1) DEFAULT NULL,
  `Approved` varchar(1) DEFAULT NULL,
  `Approved_by_line_manager` varchar(15) DEFAULT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `flex_field1` varchar(100) DEFAULT NULL,
  `flex_field2` varchar(100) DEFAULT NULL,
  `flex_field3` varchar(100) DEFAULT NULL,
  `flex_field4` varchar(100) DEFAULT NULL,
  `flex_field5` varchar(100) DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`phone_bill_summary_id`),
  KEY `staff_id` (`staff_id`),
  CONSTRAINT `phone_bill_summary_ibfk_1` FOREIGN KEY (`staff_id`) REFERENCES `staff_details` (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reference_codes`
--

DROP TABLE IF EXISTS `reference_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reference_codes` (
  `reference_code` varchar(30) NOT NULL,
  `reference_value` varchar(45) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`reference_code`,`reference_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `regular_contact_details`
--

DROP TABLE IF EXISTS `regular_contact_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `regular_contact_details` (
  `regular_contact_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `staff_id` varchar(15) DEFAULT NULL,
  `destination_no` varchar(75) DEFAULT NULL,
  `destination_country` varchar(45) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `phone_type` varchar(15) DEFAULT NULL,
  `contact_type` varchar(15) DEFAULT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dest_person_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`regular_contact_detail_id`),
  KEY `staff_id` (`staff_id`),
  CONSTRAINT `regular_contact_details_ibfk_1` FOREIGN KEY (`staff_id`) REFERENCES `staff_details` (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `authority` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_irsamgnera6angm0prq1kemt2` (`authority`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `staff_details`
--

DROP TABLE IF EXISTS `staff_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff_details` (
  `staff_id` varchar(15) NOT NULL,
  `Email_address` varchar(80) NOT NULL,
  `employee_grade` varchar(6) DEFAULT NULL,
  `cost_centre` varchar(15) DEFAULT NULL,
  `line_manager_staff_id` varchar(15) DEFAULT NULL,
  `employee_status` varchar(15) DEFAULT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dept_id` int(11) DEFAULT NULL,
  `department` varchar(200) DEFAULT NULL,
  `Staff_Name` varchar(200) NOT NULL,
  `is_dummy` bit(1) DEFAULT b'0',
  PRIMARY KEY (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `staff_phone_details`
--

DROP TABLE IF EXISTS `staff_phone_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff_phone_details` (
  `spd_id` int(11) NOT NULL AUTO_INCREMENT,
  `phone_no` varchar(15) NOT NULL,
  `staff_id` varchar(15) DEFAULT NULL,
  `phone_status` varchar(15) NOT NULL DEFAULT 'ACTIVE',
  `service_type` varchar(15) DEFAULT NULL,
  `usage_from` timestamp NULL DEFAULT NULL,
  `usage_to` timestamp NULL DEFAULT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `phone_type` varchar(15) DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  `sim_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`spd_id`),
  KEY `staff_id_fk_idx` (`staff_id`),
  CONSTRAINT `staff_phone_details_ibfk_1` FOREIGN KEY (`staff_id`) REFERENCES `staff_details` (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4839 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `staff_phone_details_bak`
--

DROP TABLE IF EXISTS `staff_phone_details_bak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff_phone_details_bak` (
  `spd_id` int(11) NOT NULL DEFAULT '0',
  `phone_no` varchar(15) CHARACTER SET utf8 NOT NULL,
  `staff_id` varchar(15) CHARACTER SET utf8 NOT NULL,
  `phone_status` varchar(15) CHARACTER SET utf8 DEFAULT NULL,
  `service_type` varchar(15) CHARACTER SET utf8 DEFAULT NULL,
  `usage_from` timestamp NULL DEFAULT NULL,
  `usage_to` timestamp NULL DEFAULT NULL,
  `creation_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `phone_type` varchar(15) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `upload_phone_bill_details`
--

DROP TABLE IF EXISTS `upload_phone_bill_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `upload_phone_bill_details` (
  `upload_phone_bill_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_provider` varchar(2) DEFAULT NULL,
  `yyyy_mm` int(6) DEFAULT NULL,
  `ref_no` varchar(45) DEFAULT NULL,
  `upload_phone_bill_file_id` int(11) DEFAULT NULL,
  `source_phone_no` varchar(15) DEFAULT NULL,
  `call_date_time` varchar(45) DEFAULT NULL,
  `destination_no` varchar(75) DEFAULT NULL,
  `destination_country` varchar(75) DEFAULT NULL,
  `call_duration` decimal(10,4) DEFAULT NULL,
  `call_amount` decimal(8,4) DEFAULT NULL,
  `uploaded_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `flex_field1` varchar(100) DEFAULT NULL,
  `flex_field2` varchar(100) DEFAULT NULL,
  `flex_field3` varchar(100) DEFAULT NULL,
  `flex_field4` varchar(100) DEFAULT NULL,
  `flex_field5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`upload_phone_bill_detail_id`),
  KEY `upload_phone_bill_file_id` (`upload_phone_bill_file_id`),
  CONSTRAINT `upload_phone_bill_details_ibfk_1` FOREIGN KEY (`upload_phone_bill_file_id`) REFERENCES `upload_phone_bill_file` (`upload_phone_bill_file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=117849 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `upload_phone_bill_file`
--

DROP TABLE IF EXISTS `upload_phone_bill_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `upload_phone_bill_file` (
  `upload_phone_bill_file_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_provider` varchar(2) DEFAULT NULL,
  `yyyy_mm` int(6) DEFAULT NULL,
  `file_name` varchar(45) DEFAULT NULL,
  `uploaded_date` datetime DEFAULT NULL,
  `record_count` int(10) DEFAULT NULL,
  `success_count` int(10) NOT NULL DEFAULT '0',
  `error_count` int(10) NOT NULL DEFAULT '0',
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `migrated` bit(1) NOT NULL DEFAULT b'0',
  `upload_status` varchar(25) NOT NULL DEFAULT 'PENDING',
  `upload_count` int(11) NOT NULL DEFAULT '0',
  `upload_success_count` int(11) NOT NULL DEFAULT '0',
  `upload_error_count` int(11) NOT NULL DEFAULT '0',
  `flex_field1` varchar(100) DEFAULT NULL,
  `flex_field2` varchar(100) DEFAULT NULL,
  `flex_field3` varchar(100) DEFAULT NULL,
  `flex_field4` varchar(100) DEFAULT NULL,
  `flex_field5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`upload_phone_bill_file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `account_expired` bit(1) NOT NULL,
  `account_locked` bit(1) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(100) NOT NULL,
  `password_expired` bit(1) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7456 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_it77eq964jhfqtu54081ebtio` (`role_id`),
  CONSTRAINT `FK_apcc8lxk2xnug8377fatvbn04` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_it77eq964jhfqtu54081ebtio` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



-- Dump completed on 2015-08-18 18:52:37
