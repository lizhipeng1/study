-- MySQL dump 10.13  Distrib 8.0.25, for macos11 (x86_64)
--
-- Host: 127.0.0.1    Database: qyxt_db
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `mk_discount`
--

DROP TABLE IF EXISTS `mk_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mk_discount` (
  `discount_id` bigint NOT NULL COMMENT '优惠编号',
  `tenant_id` varchar(30) NOT NULL COMMENT '所属租户',
  `activity_name` varchar(30) NOT NULL COMMENT '活动名称',
  `discount_type` varchar(10) NOT NULL DEFAULT '1' COMMENT '折扣类型 1满减 2满折 3满赠',
  `begin_date` datetime NOT NULL COMMENT '折扣开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '结束时间',
  `enable` tinyint NOT NULL DEFAULT '1' COMMENT '是否生效',
  `version` int NOT NULL DEFAULT '1' COMMENT '版本号',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`discount_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='优惠配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mk_discount`
--

LOCK TABLES `mk_discount` WRITE;
/*!40000 ALTER TABLE `mk_discount` DISABLE KEYS */;
/*!40000 ALTER TABLE `mk_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mk_discount_course`
--

DROP TABLE IF EXISTS `mk_discount_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mk_discount_course` (
  `discount_id` bigint NOT NULL COMMENT '折扣编号',
  `course_id` bigint NOT NULL COMMENT '课程 -1为全部课程',
  KEY `mk_discount_course_discount_id_index` (`discount_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='活动课程';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mk_discount_course`
--

LOCK TABLES `mk_discount_course` WRITE;
/*!40000 ALTER TABLE `mk_discount_course` DISABLE KEYS */;
/*!40000 ALTER TABLE `mk_discount_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mk_discount_dept`
--

DROP TABLE IF EXISTS `mk_discount_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mk_discount_dept` (
  `discount_id` bigint NOT NULL COMMENT '折扣编号',
  `dept_id` bigint NOT NULL COMMENT '校区 -1为全部校区',
  KEY `mk_discount_dept_discount_id_index` (`discount_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='活动校区';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mk_discount_dept`
--

LOCK TABLES `mk_discount_dept` WRITE;
/*!40000 ALTER TABLE `mk_discount_dept` DISABLE KEYS */;
/*!40000 ALTER TABLE `mk_discount_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mk_discount_detail`
--

DROP TABLE IF EXISTS `mk_discount_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mk_discount_detail` (
  `discount_detail_id` bigint NOT NULL COMMENT '明细编号',
  `discount_id` bigint NOT NULL COMMENT '折扣编号',
  `meet_condition` decimal(10,2) NOT NULL COMMENT '满足条件 金额/课时',
  `gift` decimal(10,2) NOT NULL COMMENT '赠送 金额/折扣/课时',
  PRIMARY KEY (`discount_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='优惠配置明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mk_discount_detail`
--

LOCK TABLES `mk_discount_detail` WRITE;
/*!40000 ALTER TABLE `mk_discount_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `mk_discount_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_client_details`
--

DROP TABLE IF EXISTS `oauth_client_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(128) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int DEFAULT NULL,
  `refresh_token_validity` int DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_client_details`
--

LOCK TABLES `oauth_client_details` WRITE;
/*!40000 ALTER TABLE `oauth_client_details` DISABLE KEYS */;
INSERT INTO `oauth_client_details` VALUES ('business-server','','$2a$10$sk8FArfGO7QOSC/GkVzeMuiCPGEkDqUSM0hmtwPyHwbcdJFCvN4B6','all','authorization_code,password,refresh_token','http://localhost:9091/oauth2/callback','',3600,7200,NULL,'all');
/*!40000 ALTER TABLE `oauth_client_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins` DISABLE KEYS */;
/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_cla_time`
--

DROP TABLE IF EXISTS `sc_cla_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_cla_time` (
  `course_time_id` bigint NOT NULL AUTO_INCREMENT COMMENT '排课编号',
  `rule_id` bigint DEFAULT NULL COMMENT '规则编号',
  `cla_id` bigint NOT NULL COMMENT '班级id',
  `cla_date` date NOT NULL COMMENT '上课日期 如:2020-02-05',
  `teacher_id` bigint DEFAULT NULL COMMENT '教师',
  `room_id` bigint DEFAULT NULL COMMENT '教室',
  `room_name` varchar(100) DEFAULT NULL COMMENT '教室',
  `class_theme` varchar(20) DEFAULT NULL COMMENT '上课主题',
  `start_time` time NOT NULL COMMENT '上课开始时间',
  `end_time` time NOT NULL COMMENT '上课结束时间',
  `real_cla_date` date DEFAULT NULL COMMENT '实际上课时间',
  `real_start_time` time DEFAULT NULL COMMENT '实际开始时间',
  `real_end_time` time DEFAULT NULL COMMENT '实际结束时间',
  `pay_hour` decimal(10,2) DEFAULT NULL COMMENT '课时变更数量',
  `pay_total_hour` decimal(10,2) DEFAULT NULL COMMENT '课时消耗',
  `pay_total_fee` decimal(10,2) DEFAULT NULL COMMENT '学费消耗',
  `source` char(1) DEFAULT '1' COMMENT '来源 1:重复排课 2:未排课上课 3:单个新增',
  `status` char(1) DEFAULT '1' COMMENT '状态 1:待上课 2:已上课',
  `need_attend_cnt` int DEFAULT NULL COMMENT '应到人数',
  `real_attend_cnt` int DEFAULT NULL COMMENT '实到人数',
  `at_class_cnt` int DEFAULT NULL COMMENT '到课人数',
  `leave_cnt` int DEFAULT NULL COMMENT '请假人数',
  `out_cnt` int DEFAULT NULL COMMENT '缺勤人数',
  `memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`course_time_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='排课信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_cla_time`
--

LOCK TABLES `sc_cla_time` WRITE;
/*!40000 ALTER TABLE `sc_cla_time` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_cla_time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_cla_time_attend`
--

DROP TABLE IF EXISTS `sc_cla_time_attend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_cla_time_attend` (
  `attend_id` bigint NOT NULL COMMENT '主键',
  `student_course_id` bigint NOT NULL COMMENT 'sc_student_course 主键',
  `course_order_id` bigint DEFAULT NULL COMMENT 'sc_student_course_order.course_order_id',
  `course_time_id` bigint NOT NULL COMMENT 'sc_cla_time.course_time_id',
  `student_id` bigint NOT NULL COMMENT '学生',
  `cla_id` bigint NOT NULL COMMENT '班级 冗余自动',
  `course_id` bigint NOT NULL COMMENT '课程 冗余自动',
  `teacher_id` bigint NOT NULL COMMENT '上课教师',
  `teacher_name` varchar(50) DEFAULT NULL COMMENT '教师',
  `charge_type` varchar(10) DEFAULT NULL COMMENT '收费模式 hour:课时 date:时间 cycle:期',
  `attend_status` varchar(10) NOT NULL COMMENT '出席状态 1:到课 2:请假 3:缺勤',
  `teacher_get_hour` decimal(10,2) DEFAULT '0.00' COMMENT '教师获取课时数量',
  `pay_hour` decimal(10,2) DEFAULT '0.00' COMMENT '扣减课时数量',
  `pay_fee` decimal(10,2) DEFAULT NULL COMMENT '学费消耗',
  `memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`attend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='上课出勤表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_cla_time_attend`
--

LOCK TABLES `sc_cla_time_attend` WRITE;
/*!40000 ALTER TABLE `sc_cla_time_attend` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_cla_time_attend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_cla_time_rule`
--

DROP TABLE IF EXISTS `sc_cla_time_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_cla_time_rule` (
  `rule_id` bigint NOT NULL COMMENT '规则id',
  `cla_id` bigint NOT NULL COMMENT '班级',
  `rule_type` varchar(1) NOT NULL DEFAULT '1' COMMENT '规则类型 1重复排课 2单次排课',
  `begin_date` date DEFAULT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `once_date` longtext COMMENT '单次排课 日期',
  `repeat_type` varchar(10) DEFAULT NULL COMMENT '重复方式 1每周重复 2隔天重复 3隔周重复',
  `week_day` varchar(20) DEFAULT NULL COMMENT '上课星期 周几上课',
  `filter_holiday` tinyint DEFAULT '1' COMMENT '是否过滤节假日 1过滤 0不过滤',
  `start_time` time DEFAULT NULL COMMENT '上课时间',
  `end_time` time DEFAULT NULL COMMENT '下课时间',
  `teacher_id` bigint DEFAULT NULL COMMENT '任课教师',
  `room_id` bigint DEFAULT NULL COMMENT '教室',
  `room_name` varchar(100) DEFAULT NULL COMMENT '上课教室',
  `class_theme` varchar(20) DEFAULT NULL COMMENT '上课主题',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`rule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='上课时间配置规则';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_cla_time_rule`
--

LOCK TABLES `sc_cla_time_rule` WRITE;
/*!40000 ALTER TABLE `sc_cla_time_rule` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_cla_time_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_course`
--

DROP TABLE IF EXISTS `sc_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_course` (
  `course_id` bigint NOT NULL COMMENT '课程id',
  `tenant_id` varchar(30) NOT NULL COMMENT '所属租户',
  `course_name` varchar(50) NOT NULL COMMENT '课程名称',
  `course_type_id` bigint DEFAULT NULL COMMENT '课程类型',
  `teaching_mode` char(1) NOT NULL COMMENT '授课模式 1 班课 2 一对一',
  `course_intro` varchar(100) DEFAULT '' COMMENT '课程简介',
  `sale` char(1) NOT NULL DEFAULT '1' COMMENT '是否开售 1开售 0停售',
  `delete_flag` char(1) DEFAULT '0' COMMENT '删除标志（1删除 0在用）',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `import_id` bigint NOT NULL DEFAULT '-1' COMMENT '批量导入id',
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='课程信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_course`
--

LOCK TABLES `sc_course` WRITE;
/*!40000 ALTER TABLE `sc_course` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_course_charge`
--

DROP TABLE IF EXISTS `sc_course_charge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_course_charge` (
  `charge_id` bigint NOT NULL COMMENT '收费编号',
  `course_id` bigint NOT NULL COMMENT '课程编号',
  `depart_id` bigint NOT NULL DEFAULT '-1' COMMENT '校区 -1为全部校区',
  `charge_type` varchar(10) NOT NULL COMMENT '收费模式 hour:课时 date:时间 cycle:期',
  `count` decimal(10,2) NOT NULL COMMENT '课时数量',
  `total_fee` decimal(10,2) NOT NULL COMMENT '总价',
  `date_unit` varchar(10) DEFAULT NULL COMMENT '时间周期 天/月/季/年',
  PRIMARY KEY (`charge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='课程收费模式';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_course_charge`
--

LOCK TABLES `sc_course_charge` WRITE;
/*!40000 ALTER TABLE `sc_course_charge` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_course_charge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_course_cla`
--

DROP TABLE IF EXISTS `sc_course_cla`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_course_cla` (
  `cla_id` bigint NOT NULL COMMENT '班级id',
  `tenant_id` varchar(30) NOT NULL COMMENT '所属租户',
  `course_id` bigint NOT NULL COMMENT '课程id',
  `depart_id` bigint NOT NULL COMMENT '开班校区',
  `staff_id` bigint NOT NULL COMMENT '班主任id',
  `cla_name` varchar(30) NOT NULL COMMENT '班级名称',
  `cla_color` varchar(10) NOT NULL DEFAULT '#409EFF' COMMENT '班级颜色',
  `capacity` int NOT NULL DEFAULT '1' COMMENT '满班人数',
  `recruit_status` char(1) NOT NULL DEFAULT '1' COMMENT '招生状态 1开放 2满班后停止 0停止',
  `every_stu_lose_hour` decimal(4,2) NOT NULL DEFAULT '1.00' COMMENT '每次上课学生扣除课时',
  `every_tea_get_hour` decimal(4,2) NOT NULL DEFAULT '1.00' COMMENT '每次上课教师获得课时',
  `open_date` date NOT NULL COMMENT '开班日期',
  `close_date` date DEFAULT NULL COMMENT '结班日期',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `delete_flag` char(1) DEFAULT '0' COMMENT '删除标志（1删除 0在用）',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`cla_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='课程班级信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_course_cla`
--

LOCK TABLES `sc_course_cla` WRITE;
/*!40000 ALTER TABLE `sc_course_cla` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_course_cla` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_course_type`
--

DROP TABLE IF EXISTS `sc_course_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_course_type` (
  `course_type_id` bigint NOT NULL,
  `tenant_id` varchar(30) NOT NULL COMMENT '所属租户',
  `course_type` varchar(20) NOT NULL COMMENT '课程类型名',
  `sort` int NOT NULL DEFAULT '1' COMMENT '排序',
  `in_use` char(1) DEFAULT '1' COMMENT '部门状态（1正常 0停用）',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`course_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='课程类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_course_type`
--

LOCK TABLES `sc_course_type` WRITE;
/*!40000 ALTER TABLE `sc_course_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_course_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_order`
--

DROP TABLE IF EXISTS `sc_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_order` (
  `order_id` bigint NOT NULL COMMENT '订单编号',
  `tenant_id` varchar(30) NOT NULL COMMENT '所属租户',
  `student_id` bigint NOT NULL COMMENT '学员编号',
  `student_name` varchar(30) NOT NULL COMMENT '学员名称',
  `phone` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `order_type` varchar(10) NOT NULL DEFAULT '1' COMMENT '订单类型 1报名',
  `original_total_fee` decimal(10,2) NOT NULL COMMENT '原价',
  `actual_total_fee` decimal(10,2) NOT NULL COMMENT '实际价格=原价-优惠',
  `receipt_fee` decimal(10,2) NOT NULL COMMENT '收款金额 receipt_fee+balance_fee=actual_total_fee',
  `balance_fee` decimal(10,2) DEFAULT '0.00' COMMENT '使用余额支付金额',
  `order_tag` varchar(100) DEFAULT NULL COMMENT '订单标签',
  `sale_source_tag` varchar(100) DEFAULT NULL COMMENT '销售来源标签',
  `sale_staff_id` bigint DEFAULT NULL COMMENT '销售员工',
  `sale_staff_name` varchar(50) DEFAULT NULL COMMENT '销售员工',
  `order_status` varchar(10) NOT NULL DEFAULT '1' COMMENT '订单状态 1待支付 2已支付 3已作废',
  `memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `handle_dept_id` bigint NOT NULL COMMENT '经办校区',
  `handle_dept_name` varchar(30) NOT NULL COMMENT '经办校区名称',
  `handle_date` date NOT NULL COMMENT '经办日期',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_order`
--

LOCK TABLES `sc_order` WRITE;
/*!40000 ALTER TABLE `sc_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_order_account`
--

DROP TABLE IF EXISTS `sc_order_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_order_account` (
  `order_account_id` bigint NOT NULL COMMENT '主键',
  `order_id` bigint NOT NULL COMMENT '订单编号',
  `account_id` bigint NOT NULL COMMENT '收款账户',
  `account_name` varchar(30) DEFAULT NULL COMMENT '账户名称',
  `fee` decimal(10,2) NOT NULL COMMENT '实收',
  PRIMARY KEY (`order_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单收款账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_order_account`
--

LOCK TABLES `sc_order_account` WRITE;
/*!40000 ALTER TABLE `sc_order_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_order_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_order_detail`
--

DROP TABLE IF EXISTS `sc_order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_order_detail` (
  `order_detail_id` bigint NOT NULL COMMENT '详单编号',
  `order_id` bigint NOT NULL COMMENT '订单编号',
  `course_id` bigint DEFAULT NULL COMMENT '课程',
  `course_name` varchar(100) DEFAULT NULL COMMENT '课程',
  `cla_id` bigint DEFAULT NULL COMMENT '班级',
  `cla_name` varchar(50) DEFAULT NULL COMMENT '班级',
  `dept_id` bigint NOT NULL COMMENT '校区',
  `dept_name` varchar(30) DEFAULT NULL COMMENT '校区',
  `detail_tag` varchar(10) NOT NULL DEFAULT '1' COMMENT '类型 1新报 2续报',
  `charge_name` varchar(50) DEFAULT NULL COMMENT '收费方式名称',
  `charge_type` varchar(10) DEFAULT NULL COMMENT '收费模式',
  `charge_count` decimal(10,2) DEFAULT NULL COMMENT '收费方式包含课时数量',
  `charge_fee` decimal(10,2) DEFAULT NULL COMMENT '收费方式金额',
  `date_unit` varchar(10) DEFAULT NULL COMMENT '时间周期 天/月/季/年',
  `buy_count` decimal(10,2) DEFAULT NULL COMMENT '购买数量',
  `original_fee` decimal(10,2) NOT NULL COMMENT '原价  buy_count*charge_fee',
  `begin_date` date DEFAULT NULL COMMENT '生效时间',
  `end_date` date DEFAULT NULL COMMENT '失效时间',
  `expire_date` date DEFAULT NULL COMMENT '过期时间',
  `discount_id` bigint DEFAULT NULL COMMENT '优惠编号',
  `activity_name` varchar(30) DEFAULT NULL COMMENT '活动名称',
  `discount_type` varchar(10) DEFAULT NULL COMMENT '折扣类型',
  `discount_detail_id` bigint DEFAULT NULL COMMENT '优惠明细编号',
  `meet_condition` decimal(10,2) DEFAULT NULL COMMENT '满足条件 金额/课时',
  `gift` decimal(10,2) DEFAULT NULL COMMENT '赠送 金额/折扣/课时',
  `direct_discount` decimal(4,2) DEFAULT '10.00' COMMENT '直接折扣',
  `direct_reduce_fee` decimal(10,2) DEFAULT '0.00' COMMENT '直接减免金额',
  `actual_fee` decimal(10,2) NOT NULL COMMENT '实际价格  original_fee-满减-直接减免',
  `inside_memo` varchar(100) DEFAULT NULL COMMENT '内部备注',
  `outside_memo` varchar(100) DEFAULT NULL COMMENT '内部备注',
  `order_detail_status` varchar(10) NOT NULL DEFAULT '1' COMMENT '订单状态 1待支付 2已支付 3已作废',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`order_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单详情';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_order_detail`
--

LOCK TABLES `sc_order_detail` WRITE;
/*!40000 ALTER TABLE `sc_order_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_room`
--

DROP TABLE IF EXISTS `sc_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_room` (
  `room_id` bigint NOT NULL COMMENT '教室',
  `tenant_id` varchar(30) NOT NULL COMMENT '所属租户',
  `dept_id` bigint DEFAULT NULL COMMENT '所属校区',
  `room_name` varchar(100) DEFAULT NULL COMMENT '教室名',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='教室';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_room`
--

LOCK TABLES `sc_room` WRITE;
/*!40000 ALTER TABLE `sc_room` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_school`
--

DROP TABLE IF EXISTS `sc_school`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_school` (
  `school_id` bigint NOT NULL AUTO_INCREMENT COMMENT '学校id',
  `tenant_id` varchar(30) NOT NULL,
  `province_code` varchar(6) DEFAULT NULL COMMENT '省份编码',
  `city_code` varchar(5) DEFAULT NULL COMMENT '区号 如0431',
  `school_name` varchar(30) NOT NULL,
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`school_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学校信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_school`
--

LOCK TABLES `sc_school` WRITE;
/*!40000 ALTER TABLE `sc_school` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_school` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_student`
--

DROP TABLE IF EXISTS `sc_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_student` (
  `student_id` bigint NOT NULL COMMENT '学生id',
  `tenant_id` varchar(30) NOT NULL COMMENT '所属机构',
  `school_id` bigint DEFAULT NULL COMMENT '所属学校',
  `student_name` varchar(30) NOT NULL COMMENT '学生姓名',
  `birth_day` date DEFAULT NULL COMMENT '出生日期',
  `sex` char(1) NOT NULL DEFAULT 'M' COMMENT '性别 M男 F女',
  `phone` varchar(30) DEFAULT NULL,
  `in_time` date DEFAULT NULL COMMENT '入校时间',
  `delete_flag` char(1) DEFAULT '0' COMMENT '删除标志（1删除 0在用）',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学生基本信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_student`
--

LOCK TABLES `sc_student` WRITE;
/*!40000 ALTER TABLE `sc_student` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_student_account`
--

DROP TABLE IF EXISTS `sc_student_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_student_account` (
  `account_id` bigint NOT NULL COMMENT '账户编号',
  `student_id` bigint NOT NULL COMMENT '学生',
  `tenant_id` varchar(30) NOT NULL,
  `balance_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '账户余额',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `sc_student_account_student_id_uindex` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学生余额账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_student_account`
--

LOCK TABLES `sc_student_account` WRITE;
/*!40000 ALTER TABLE `sc_student_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_student_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_student_account_log`
--

DROP TABLE IF EXISTS `sc_student_account_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_student_account_log` (
  `account_log_id` varchar(30) NOT NULL,
  `account_id` varchar(30) NOT NULL COMMENT '账户id',
  `student_id` bigint NOT NULL COMMENT '学生',
  `log_type` varchar(10) NOT NULL DEFAULT '1' COMMENT '1储值 2消费',
  `change_fee` decimal(10,2) DEFAULT '0.00' COMMENT '变更金额',
  `change_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '变更时间',
  `change_memo` varchar(200) DEFAULT NULL COMMENT '变更备注',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '变更人',
  PRIMARY KEY (`account_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='账户变更日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_student_account_log`
--

LOCK TABLES `sc_student_account_log` WRITE;
/*!40000 ALTER TABLE `sc_student_account_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_student_account_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_student_contact`
--

DROP TABLE IF EXISTS `sc_student_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_student_contact` (
  `contact_id` bigint NOT NULL COMMENT '联系人编号',
  `student_id` bigint NOT NULL COMMENT '学生',
  `contact_nick` varchar(30) DEFAULT NULL COMMENT '联系人称呼',
  `contact_relation` varchar(30) DEFAULT NULL COMMENT '与学生关系',
  `contact_phone` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='联系人';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_student_contact`
--

LOCK TABLES `sc_student_contact` WRITE;
/*!40000 ALTER TABLE `sc_student_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_student_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_student_course`
--

DROP TABLE IF EXISTS `sc_student_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_student_course` (
  `student_course_id` bigint NOT NULL COMMENT '主键',
  `tenant_id` varchar(30) NOT NULL,
  `student_id` bigint NOT NULL COMMENT '学生',
  `course_id` bigint NOT NULL COMMENT '课程',
  `course_name` varchar(50) DEFAULT NULL COMMENT '课程',
  `dept_id` bigint NOT NULL COMMENT '报读校区',
  `cla_id` bigint DEFAULT NULL COMMENT '班级',
  `cla_name` varchar(50) DEFAULT NULL COMMENT '班级',
  `charge_type` varchar(10) DEFAULT NULL COMMENT '收费模式 hour:课时 date:时间 cycle:期',
  `total_day` decimal(10,2) DEFAULT NULL COMMENT '总天数',
  `total_hour` decimal(10,2) DEFAULT NULL COMMENT '总课时',
  `balance_hour` decimal(10,2) DEFAULT NULL COMMENT '剩余课时',
  `total_fee` decimal(10,2) NOT NULL COMMENT '总学费',
  `status` varchar(10) NOT NULL COMMENT '状态 1在读 2停课 ',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`student_course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学生课程';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_student_course`
--

LOCK TABLES `sc_student_course` WRITE;
/*!40000 ALTER TABLE `sc_student_course` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_student_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_student_course_log`
--

DROP TABLE IF EXISTS `sc_student_course_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_student_course_log` (
  `log_id` bigint NOT NULL COMMENT '主键',
  `tenant_id` varchar(30) NOT NULL COMMENT '所属租户',
  `student_id` bigint NOT NULL COMMENT '学生',
  `log_type` varchar(5) NOT NULL COMMENT '日志类型 1缴费 2上课 3删除上课记录 4作废订单',
  `course_id` bigint NOT NULL COMMENT '课程',
  `course_name` varchar(50) NOT NULL COMMENT '课程名称',
  `cla_id` bigint DEFAULT NULL COMMENT '班级',
  `cla_name` varchar(30) DEFAULT NULL COMMENT '班级名称',
  `dept_name` varchar(30) NOT NULL COMMENT '经办校区',
  `change_hour` decimal(10,2) DEFAULT '0.00' COMMENT '变更课时',
  `after_balance_hour` decimal(10,2) DEFAULT NULL COMMENT '变更后剩余课时',
  `change_fee` decimal(10,2) DEFAULT '0.00' COMMENT '变更金额',
  `memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='课程缴费扣费记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_student_course_log`
--

LOCK TABLES `sc_student_course_log` WRITE;
/*!40000 ALTER TABLE `sc_student_course_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_student_course_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_student_course_order`
--

DROP TABLE IF EXISTS `sc_student_course_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sc_student_course_order` (
  `course_order_id` bigint NOT NULL COMMENT '主键',
  `student_course_id` bigint NOT NULL,
  `order_id` bigint NOT NULL COMMENT '订单',
  `order_detail_id` bigint NOT NULL COMMENT '购买课程订单',
  `total_hour` decimal(10,2) DEFAULT NULL COMMENT '购买课时数量',
  `balance_hour` decimal(10,2) DEFAULT NULL COMMENT '剩余课时数量',
  `total_day` decimal(10,2) DEFAULT NULL COMMENT '购买天数',
  `begin_date` date DEFAULT NULL COMMENT '开始时间 按时间收费',
  `end_date` date DEFAULT NULL COMMENT '结束时间 按时间收费',
  `expire_date` date DEFAULT NULL COMMENT '失效时间',
  `total_fee` decimal(10,2) DEFAULT NULL COMMENT '总金额',
  `unit_fee` decimal(10,2) DEFAULT NULL COMMENT '单价',
  `valid` tinyint DEFAULT '1' COMMENT '有效 1:有效 0:作废',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`course_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学生课程关联订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_student_course_order`
--

LOCK TABLES `sc_student_course_order` WRITE;
/*!40000 ALTER TABLE `sc_student_course_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `sc_student_course_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint DEFAULT '-1' COMMENT '父部门id',
  `tenant_id` varchar(30) NOT NULL COMMENT '所属租户',
  `dept_type` char(1) NOT NULL DEFAULT '1' COMMENT '1部门 2校区',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `sort` int DEFAULT '1' COMMENT '显示顺序',
  `in_use` char(1) DEFAULT '1' COMMENT '部门状态（1正常 0停用）',
  `delete_flag` char(1) DEFAULT '0' COMMENT '删除标志（1删除 0在用）',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_data` (
  `dict_data_id` varchar(30) NOT NULL COMMENT '字典编码',
  `parent_value` varchar(100) DEFAULT '' COMMENT '父编码',
  `dict_sort` int DEFAULT '1' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT '1' COMMENT '是否默认（1是 0否）',
  `in_use` char(1) DEFAULT '1' COMMENT '状态（0停用 1正常）',
  `create_user` varchar(20) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(20) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_data_id`),
  KEY `sys_dict_data_p_value_index` (`parent_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` VALUES ('1217819100265328642',NULL,1,'展示','1','is_show',NULL,NULL,'1','1','','2020-01-16 22:41:14','','2020-01-16 22:41:14','展示1'),('1217819145379262465',NULL,2,'隐藏','0','is_show',NULL,NULL,'1','1','','2020-01-16 22:41:24','','2020-01-16 22:41:24','隐藏0'),('1218165281520676866',NULL,1,'在用','1','in_use',NULL,NULL,'1','1','','2020-01-17 21:36:50','','2020-01-17 21:36:50',NULL),('1218165322486444033',NULL,2,'停用','0','in_use',NULL,NULL,'1','1','','2020-01-17 21:36:59','','2020-01-17 21:36:59',NULL),('1239746429069676545',NULL,1,'男','M','sex',NULL,NULL,'1','1','1192453807573942273','2020-03-17 10:52:36','','2020-03-17 10:52:36',NULL),('1239746459277053953',NULL,2,'女','F','sex',NULL,NULL,'1','1','1192453807573942273','2020-03-17 10:52:43','','2020-03-17 10:52:43',NULL),('1239779495267823618',NULL,1,'星期一','1','week_day',NULL,NULL,'1','1','1192453807573942273','2020-03-17 13:04:00','','2020-03-17 13:04:00',NULL),('1239779527152922626',NULL,2,'星期二','2','week_day',NULL,NULL,'1','1','1192453807573942273','2020-03-17 13:04:07','','2020-03-17 13:04:07',NULL),('1239779555674189826',NULL,3,'星期三','3','week_day',NULL,NULL,'1','1','1192453807573942273','2020-03-17 13:04:14','','2020-03-17 13:04:14',NULL),('1239779580512858114',NULL,4,'星期四','4','week_day',NULL,NULL,'1','1','1192453807573942273','2020-03-17 13:04:20','','2020-03-17 13:04:20',NULL),('1239779607272517633',NULL,5,'星期五','5','week_day',NULL,NULL,'1','1','1192453807573942273','2020-03-17 13:04:27','','2020-03-17 13:04:27',NULL),('1239779641061830658',NULL,6,'星期六','6','week_day',NULL,NULL,'1','1','1192453807573942273','2020-03-17 13:04:35','','2020-03-17 13:04:35',NULL),('1239779673316028417',NULL,7,'星期日','7','week_day',NULL,NULL,'1','1','1192453807573942273','2020-03-17 13:04:42','','2020-03-17 13:04:42',NULL),('1254388207424890431','220',431,'长春市','0431','city_code',NULL,NULL,'1','1','','2020-11-14 14:52:13','','2020-11-14 14:52:13',NULL),('1254388207424890432','220',432,'吉林市','0432','city_code',NULL,NULL,'1','1','','2020-11-14 14:52:13','','2020-11-14 14:52:13',NULL),('1254388207424890433','222',433,'延边朝鲜族自治州','0433','city_code',NULL,NULL,'1','1','','2020-11-14 14:52:13','','2020-11-14 14:52:13',NULL),('1254388207424890434','220',434,'四平市','0434','city_code',NULL,NULL,'1','1','','2020-11-14 14:52:13','','2020-11-14 14:52:13',NULL),('1254388207424890435','220',435,'通化市','0435','city_code',NULL,NULL,'1','1','','2020-11-14 14:52:13','','2020-11-14 14:52:13',NULL),('1254388207424890436','220',436,'白城市','0436','city_code',NULL,NULL,'1','1','','2020-11-14 14:52:13','','2020-11-14 14:52:13',NULL),('1254388207424890437','220',437,'辽源市','0437','city_code',NULL,NULL,'1','1','','2020-11-14 14:52:13','','2020-11-14 14:52:13',NULL),('1254388207424890438','220',438,'松原市','0438','city_code',NULL,NULL,'1','1','','2020-11-14 14:52:13','','2020-11-14 14:52:13',NULL),('1254388207424890439','220',439,'白山市','0439','city_code',NULL,NULL,'1','1','','2020-11-14 14:52:13','','2020-11-14 14:52:13',NULL),('1254388207424898110',NULL,11,'北京市','110','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898120',NULL,12,'天津市','120','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898130',NULL,13,'河北省','130','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898140',NULL,14,'山西省','140','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898150',NULL,15,'内蒙古自治区','150','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898210',NULL,21,'辽宁省','210','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898220',NULL,22,'吉林省','220','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898230',NULL,23,'黑龙江省','230','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898310',NULL,31,'上海市','310','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898320',NULL,32,'江苏省','320','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898330',NULL,33,'浙江省','330','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898340',NULL,34,'安徽省','340','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898350',NULL,35,'福建省','350','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898360',NULL,36,'江西省','360','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898370',NULL,37,'山东省','370','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898410',NULL,41,'河南省','410','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898420',NULL,42,'湖北省','420','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898430',NULL,43,'湖南省','430','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898440',NULL,44,'广东省','440','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898450',NULL,45,'广西壮族自治区','450','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898460',NULL,46,'海南省','460','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898500',NULL,50,'重庆市','500','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898510',NULL,51,'四川省','510','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898520',NULL,52,'贵州省','520','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898530',NULL,53,'云南省','530','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898540',NULL,54,'西藏自治区','540','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898610',NULL,61,'陕西省','610','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898620',NULL,62,'甘肃省','620','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898630',NULL,63,'青海省','630','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898640',NULL,64,'宁夏回族自治区','640','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898650',NULL,65,'新疆维吾尔自治区','650','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898710',NULL,71,'台湾省','710','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898810',NULL,81,'香港特别行政区','810','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898820',NULL,82,'澳门特别行政区','820','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1254388207424898900',NULL,90,'外国','900','province_code',NULL,NULL,'1','1','','2020-11-14 14:19:59','','2020-11-14 14:19:59',NULL),('1268133063011983363',NULL,1,'班课','1','teaching_mode',NULL,NULL,'1','1','1192453807573942273','2020-06-03 18:50:57','','2020-06-03 18:50:57',NULL),('1268133063011983364',NULL,2,'一对一','2','teaching_mode',NULL,NULL,'1','1','1192453807573942273','2020-06-03 18:50:57','','2020-06-03 18:50:57',NULL),('1268133063011983365',NULL,1,'开售','1','sale',NULL,NULL,'1','1','1192453807573942273','2020-06-03 18:50:57','','2020-06-03 18:50:57',NULL),('1268133063011983366',NULL,2,'停售','0','sale',NULL,NULL,'1','1','1192453807573942273','2020-06-03 18:50:57','','2020-06-03 18:50:57',NULL),('1268133063011983367',NULL,1,'按课时','hour','charge_type',NULL,NULL,'1','1','1192453807573942273','2020-06-03 18:50:57','','2020-06-03 18:50:57',NULL),('1268133063011983368',NULL,2,'按时间','date','charge_type',NULL,NULL,'1','1','1192453807573942273','2020-06-03 18:50:57','','2020-06-03 18:50:57',NULL),('1268133063011983369',NULL,3,'按期','cycle','charge_type',NULL,NULL,'1','1','1192453807573942273','2020-06-03 18:50:57','','2020-06-03 18:50:57',NULL),('1268133063011983370',NULL,1,'元/天','day','date_unit',NULL,NULL,'1','1','1192453807573942273','2020-06-03 18:50:57','','2020-06-03 18:50:57',NULL),('1268133063011983371',NULL,2,'元/月','month','date_unit',NULL,NULL,'1','1','1192453807573942273','2020-06-03 18:50:57','','2020-06-03 18:50:57',NULL),('1268133063011983372',NULL,3,'元/季','season','date_unit',NULL,NULL,'1','1','1192453807573942273','2020-06-03 18:50:57','','2020-06-03 18:50:57',NULL),('1268133063011983373',NULL,4,'元/年','year','date_unit',NULL,NULL,'1','1','1192453807573942273','2020-06-03 18:50:57','','2020-06-03 18:50:57',NULL),('1295327174387154945',NULL,1,'正式员工','1','personnel_status',NULL,NULL,'1','1','1192453807573942273','2020-08-17 19:50:39','','2020-08-17 19:50:39',NULL),('1295327211657740290',NULL,2,'实习员工','2','personnel_status',NULL,NULL,'1','1','1192453807573942273','2020-08-17 19:50:47','','2020-08-17 19:50:47',NULL),('1295327251314884610',NULL,3,'试用期','3','personnel_status',NULL,NULL,'1','1','1192453807573942273','2020-08-17 19:50:57','','2020-08-17 19:50:57',NULL),('1295327301088690177',NULL,4,'兼职员工','4','personnel_status',NULL,NULL,'1','1','1192453807573942273','2020-08-17 19:51:09','','2020-08-17 19:51:09',NULL),('1295327340913606657',NULL,5,'停薪留职','5','personnel_status',NULL,NULL,'1','1','1192453807573942273','2020-08-17 19:51:18','','2020-08-17 19:51:18','停薪留职'),('1295327373385908226',NULL,6,'主动离职','6','personnel_status',NULL,NULL,'1','1','1192453807573942273','2020-08-17 19:51:26','','2020-08-17 19:51:26',NULL),('1295327405778518017',NULL,7,'解聘','7','personnel_status',NULL,NULL,'1','1','1192453807573942273','2020-08-17 19:51:34','','2020-08-17 19:51:34',NULL),('1295327405778518021',NULL,1,'销售来源','1','tag_type',NULL,NULL,'1','1','1192453807573942273','2020-09-03 15:03:04','','2020-09-03 15:03:04',NULL),('1295327405778518022',NULL,2,'订单标签','2','tag_type',NULL,NULL,'1','1','1192453807573942273','2020-09-03 15:03:04','','2020-09-03 15:03:04',NULL),('1295327405778518023',NULL,1,'报名','1','order_type',NULL,NULL,'1','1','1192453807573942273','2020-09-11 17:25:53','','2020-09-11 17:25:53',NULL),('1306869637228564481',NULL,1,'重复排课','1','cla_time_rule_type',NULL,NULL,'1','1','1192453807573942273','2020-09-18 16:16:16','','2020-09-18 16:16:16',NULL),('1306869671802212353',NULL,2,'单次排课','2','cla_time_rule_type',NULL,NULL,'1','1','1192453807573942273','2020-09-18 16:16:24','','2020-09-18 16:16:24',NULL),('1306869742874693634',NULL,1,'每周重复','1','cla_time_repeat_type',NULL,NULL,'1','1','1192453807573942273','2020-09-18 16:16:41','','2020-09-18 16:16:41',NULL),('1306869780472434689',NULL,2,'隔天重复','2','cla_time_repeat_type',NULL,NULL,'1','1','1192453807573942273','2020-09-18 16:16:50','','2020-09-18 16:16:50',NULL),('1306869817273257986',NULL,3,'隔周重复','3','cla_time_repeat_type',NULL,NULL,'1','1','1192453807573942273','2020-09-18 16:16:59','','2020-09-18 16:16:59',NULL),('1306869962178072578',NULL,1,'是','1','is_or_not',NULL,NULL,'1','1','1192453807573942273','2020-09-18 16:17:34','','2020-09-18 16:17:34',NULL),('1306870011456950274',NULL,2,'否','0','is_or_not',NULL,NULL,'1','1','1192453807573942273','2020-09-18 16:17:45','','2020-09-18 16:17:45',NULL),('1306870011456950275',NULL,2,'爸爸','daddy','contact_relation',NULL,NULL,'1','1','','2020-09-28 09:18:08','','2020-09-28 09:18:08',NULL),('1306870011456950276',NULL,1,'妈妈','mommy','contact_relation',NULL,NULL,'1','1','','2020-09-28 09:18:08','','2020-09-28 09:18:08',NULL),('1306870011456950277',NULL,3,'爷爷','yy','contact_relation',NULL,NULL,'1','1','','2020-09-28 09:18:08','','2020-09-28 09:18:08',NULL),('1306870011456950278',NULL,4,'奶奶','nn','contact_relation',NULL,NULL,'1','1','','2020-09-28 09:18:08','','2020-09-28 09:18:08',NULL),('1306870011456950279',NULL,5,'姥爷','ly','contact_relation',NULL,NULL,'1','1','','2020-09-28 09:18:08','','2020-09-28 09:18:08',NULL),('1306870011456950280',NULL,6,'姥姥','ll','contact_relation',NULL,NULL,'1','1','','2020-09-28 09:18:08','','2020-09-28 09:18:08',NULL),('1306870011456950281',NULL,8,'其他','other','contact_relation',NULL,NULL,'1','1','','2020-09-28 09:18:08','','2020-09-28 09:18:08',NULL),('1306870011456950282',NULL,0,'本人','self','contact_relation',NULL,NULL,'1','1','','2020-09-28 11:04:03','','2020-09-28 11:04:03',NULL),('1310909498394000100','',1,'安徽大学出版社','0001','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394000200','',1,'安徽电子音像','0002','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394000300','',1,'安徽教育出版社','0003','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394000400','',1,'安徽教育电子音像出版社','0004','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394000500','',1,'安徽科学技术出版社','0005','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394000600','',1,'安徽美术出版社','0006','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394000700','',1,'安徽人民出版社','0007','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394000800','',1,'安徽少年儿童出版社','0008','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394000900','',1,'安徽省音像出版社','0009','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394001000','',1,'安徽师范大学出版社','0010','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394001100','',1,'安徽文化音像出版社','0011','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394001200','',1,'安徽文艺出版社','0012','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394001300','',1,'安徽音像出版社','0013','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394001400','',1,'奥林匹克出版社','0014','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394001500','',1,'八一出版社','0015','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394001600','',1,'巴蜀书社','0016','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394001700','',1,'白山出版社','0017','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394001800','',1,'百花文艺出版社','0018','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394001900','',1,'百花洲文艺出版社','0019','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394002000','',1,'百家出版社','0020','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394002100','',1,'半岛音像出版社','0021','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394002200','',1,'宝文堂书店','0022','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394002300','',1,'北方妇女儿童出版社','0023','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394002400','',1,'北方文艺出版社','0024','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394002500','',1,'北京百年树人软件技术有限公司','0025','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394002600','',1,'北京北影录音录像公司','0026','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394002700','',1,'北京伯通电子出版社','0027','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394002800','',1,'北京财经电子音像出版社','0028','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394002900','',1,'北京财经音像','0029','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394003000','',1,'北京长江新世纪文化传媒有限公司','0030','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394003100','',1,'北京出版社','0031','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394003200','',1,'北京大学出版社','0032','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394003300','',1,'北京大学医学出版社','0033','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394003400','',1,'北京大学音像出版社','0034','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394003500','',1,'北京电视艺术中心出版社','0035','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394003600','',1,'北京电视艺术中心音像出版社','0036','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394003700','',1,'北京电子出版社','0037','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394003800','',1,'北京电子出版物出版中心','0038','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394003900','',1,'北京电子音像出版社','0039','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394004000','',1,'北京电子音像出版中心','0040','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394004100','',1,'北京东方影音公司','0041','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394004200','',1,'北京法制教育音像出版社','0042','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394004300','',1,'北京高教音像出版社','0043','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394004400','',1,'北京工业大学出版社','0044','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394004500','',1,'北京工艺美术出版社','0045','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394004600','',1,'北京古籍出版社','0046','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394004700','',1,'北京广播学院出版社','0047','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394004800','',1,'北京广播学院电子音像出版社','0048','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394004900','',1,'北京航空航天大学出版社','0049','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394005000','',1,'北京华夏树人数码科技有限公司','0050','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394005100','',1,'北京环球音像出版社','0051','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394005200','',1,'北京交通大学出版社','0052','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394005300','',1,'北京交通大学音像出版社','0053','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394005400','',1,'北京教育出版社','0054','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394005500','',1,'北京科海电子出版社','0055','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394005600','',1,'北京科海集团公司','0056','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394005700','',1,'北京科海培中技术有限责任公司','0057','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394005800','',1,'北京科学技术出版社','0058','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394005900','',1,'北京蓝光电子出版社','0059','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394006000','',1,'北京理工大学出版社','0060','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394006100','',1,'北京理工大学出版社音像部','0061','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394006200','',1,'北京理工大学金洪恩电脑公司','0062','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394006300','',1,'北京联合出版公司','0063','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394006400','',1,'北京美术摄影出版社','0064','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394006500','',1,'北京牡丹四星音像有限公司','0065','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394006600','',1,'北京普教电子音像出版社','0066','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394006700','',1,'北京普教音像出版社','0067','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394006800','',1,'北京日报出版社','0068','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394006900','',1,'北京赛迪电子出版社','0069','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394007000','',1,'北京三联出版社','0070','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394007100','',1,'北京少年儿童出版','0071','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394007200','',1,'北京师范大学出版社','0072','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394007300','',1,'北京师范大学音像出版社','0073','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394007400','',1,'北京师范学院出版社','0074','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394007500','',1,'北京十月文艺出版社','0075','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394007600','',1,'北京时代华文书局','0076','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394007700','',1,'北京世界知识音像电子出版社','0077','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394007800','',1,'北京市青少年音像出版社','0078','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394007900','',1,'北京市外文音像出版社','0079','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394008000','',1,'北京数通电子出版社','0080','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394008100','',1,'北京腾图电子出版社','0081','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394008200','',1,'北京体育大学版社','0082','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394008300','',1,'北京体育大学出版社','0083','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394008400','',1,'北京体育学院出版社','0084','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394008500','',1,'北京外语音像出版社','0085','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394008600','',1,'北京伟地电子','0086','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394008700','',1,'北京伟地电子出版社','0087','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394008800','',1,'北京文化艺术音像出版社','0088','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394008900','',1,'北京希望电子出版社','0089','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394009000','',1,'北京新华刊网企划有限公司','0090','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394009100','',1,'北京燕山出版社','0091','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394009200','',1,'北京医科大学出版社','0092','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394009300','',1,'北京艺术与科学电子出版社','0093','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394009400','',1,'北京艺术与科学电子出版社2','0094','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394009500','',1,'北京音像出版社','0095','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394009600','',1,'北京银冠电子出版社','0096','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394009700','',1,'北京邮电大学出版社','0097','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394009800','',1,'北京语言大学出版社','0098','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394009900','',1,'北京语言大学电子音像出版社','0099','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394010000','',1,'北京语言文化大学出版社','0100','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394010100','',1,'北京中唱时代音像出版有限公司','0101','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394010200','',1,'北京中电电子出版社','0102','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394010300','',1,'北京中录同方音像出版社','0103','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394010400','',1,'北京中软件电子出版社','0104','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394010500','',1,'北京中体音像出版中心','0105','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394010600','',1,'北京周报出版社','0106','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394010700','',1,'北影录音录像公司','0107','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394010800','',1,'北岳文艺出版社','0108','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394010900','',1,'贝贝特电子出版有限公司','0109','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394011000','',1,'奔流电子音像出版社','0110','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394011100','',1,'奔流音像','0111','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394011200','',1,'本社','0112','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394011300','',1,'兵器工业出版社','0113','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394011400','',1,'博文书社','0114','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394011500','',1,'渤海湾出版公司','0115','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394011600','',1,'测绘出版社','0116','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394011700','',1,'测试','0117','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394011800','',1,'长白山音像','0118','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394011900','',1,'长城出版社','0119','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394012000','',1,'长城艺术文化中心','0120','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394012100','',1,'长春出版社','0121','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394012200','',1,'长虹出版公司','0122','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394012300','',1,'长江出版传媒','0123','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394012400','',1,'长江出版社','0124','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394012500','',1,'长江少年儿童出版社','0125','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394012600','',1,'长江少年儿童出版社有限公司','0126','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394012700','',1,'长江文艺出版社','0127','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394012800','',1,'长征出版社','0128','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394012900','',1,'朝花少年儿童出版社','0129','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394013000','',1,'朝华出版社','0130','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394013100','',1,'晨光出版社','0131','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394013200','',1,'成都出版社','0132','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394013300','',1,'成都地图出版社','0133','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394013400','',1,'成都电子科技大学出版社','0134','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394013500','',1,'成都科技大学出版社','0135','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394013600','',1,'成都时代出版社','0136','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394013700','',1,'崇文书局','0137','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394013800','',1,'春风文艺出版社','0138','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394013900','',1,'春秋出版社','0139','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394014000','',1,'大恒电子出版社','0140','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394014100','',1,'大恒电子音像出版社','0141','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394014200','',1,'大连出版社','0142','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394014300','',1,'大连海事大学出版社','0143','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394014400','',1,'大连理工出版社','0144','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394014500','',1,'大连理工大学出版社','0145','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394014600','',1,'大连理工大学电子音像出版社','0146','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394014700','',1,'大象出版社','0147','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394014800','',1,'大有书局','0148','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394014900','',1,'大众文艺出版社','0149','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394015000','',1,'当代世界出版社','0150','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394015100','',1,'当代中国出版社','0151','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394015200','',1,'当代中国音像出版社','0152','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394015300','',1,'党建读物出版社','0153','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394015400','',1,'德宏出版社','0154','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394015500','',1,'德宏民族出版社','0155','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394015600','',1,'德宏民族社','0156','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394015700','',1,'地震出版社','0157','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394015800','',1,'地址出版社','0158','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394015900','',1,'地质出版社','0159','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394016000','',1,'第二军医大学出版社','0160','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394016100','',1,'第九艺术学院','0161','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394016200','',1,'第四军医大学出版社','0162','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394016300','',1,'电化教育电子出版社','0163','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394016400','',1,'电脑报电子音像出版社','0164','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394016500','',1,'电脑报社','0165','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394016600','',1,'电子报社','0166','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394016700','',1,'电子出版物数据中心','0167','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394016800','',1,'电子工业出版社','0168','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394016900','',1,'电子科技大学出版社','0169','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394017000','',1,'电子制作杂志社','0170','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394017100','',1,'东北财经大学出版社','0171','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394017200','',1,'东北大学出版社','0172','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394017300','',1,'东北林业大学出版社','0173','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394017400','',1,'东北师范大学出版社','0174','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394017500','',1,'东方出版社','0175','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394017600','',1,'东方出版社中心','0176','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394017700','',1,'东方出版中心','0177','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394017800','',1,'东方出版中心音像出版社','0178','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394017900','',1,'东方音像电子出版社','0179','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394018000','',1,'东华大学出版社','0180','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394018100','',1,'东华理工大学出版社','0181','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394018200','',1,'东南大学出版社','0182','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394018300','',1,'东南大学电子音像出版社','0183','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394018400','',1,'东软电子出版社','0184','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394018500','',1,'都市小说杂志社','0185','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394018600','',1,'对外经济贸易大学出版社','0186','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394018700','',1,'对外经济贸易大学出版社音像出版部','0187','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394018800','',1,'敦煌文艺出版社','0188','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394018900','',1,'峨眉电影制片厂音像出版社','0189','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394019000','',1,'二十一世纪出版社','0190','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394019100','',1,'二十一世纪音像电子出版社','0191','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394019200','',1,'法律出版社','0192','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394019300','',1,'法律电子出版社','0193','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394019400','',1,'方圆电子音像出版社','0194','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394019500','',1,'方志出版社','0195','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394019600','',1,'飞天电子音像出版社','0196','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394019700','',1,'非安全黑客手册','0197','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394019800','',1,'凤凰出版社','0198','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394019900','',1,'福建电子音像出版社','0199','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394020000','',1,'福建教育出版社','0200','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394020100','',1,'福建科学技术出版社','0201','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394020200','',1,'福建美术出版社','0202','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394020300','',1,'福建人民出版社','0203','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394020400','',1,'福建少年儿童出版社','0204','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394020500','',1,'福建省地图出版社','0205','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394020600','',1,'福建省文艺音像出版社','0206','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394020700','',1,'福建省音像出版社','0207','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394020800','',1,'复旦大学出版社','0208','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394020900','',1,'改革出版社','0209','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394021000','',1,'甘肃教育出版社','0210','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394021100','',1,'甘肃科学技术出版社','0211','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394021200','',1,'甘肃民族出版社','0212','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394021300','',1,'甘肃人民出版社','0213','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394021400','',1,'甘肃人民美术出版社','0214','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394021500','',1,'甘肃少年儿童出版社','0215','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394021600','',1,'甘肃文化出版社','0216','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394021700','',1,'高等教育出版社','0217','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394021800','',1,'高等教育电子音像出版社','0218','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394021900','',1,'格致出版社','0219','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394022000','',1,'工商出版社','0220','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394022100','',1,'公安部华盛音像','0221','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394022200','',1,'古吴轩出版社','0222','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394022300','',1,'光明日报出版社','0223','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394022400','',1,'广东大音音像出版社','0224','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394022500','',1,'广东高等教育出版社','0225','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394022600','',1,'广东海燕电子音像出版社','0226','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394022700','',1,'广东嘉应音像出版社','0227','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394022800','',1,'广东教育出版社','0228','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394022900','',1,'广东经济出版社','0229','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394023000','',1,'广东科技出版社','0230','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394023100','',1,'广东旅游出版社','0231','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394023200','',1,'广东人民出版社','0232','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394023300','',1,'广东省出版社','0233','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394023400','',1,'广东省地图出版社','0234','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394023500','',1,'广东省语言音像出版社','0235','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394023600','',1,'广东省语言音像电子出版社','0236','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394023700','',1,'广东太平洋电子出版社','0237','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394023800','',1,'广东新世纪音像电子出版社','0238','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394023900','',1,'广东音像','0239','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394024000','',1,'广东音像出版社','0240','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394024100','',1,'广东语言音像出版社','0241','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394024200','',1,'广东珠江音像出版社','0242','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394024300','',1,'广陵书社','0243','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394024400','',1,'广西教育出版社','0244','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394024500','',1,'广西金海湾电子音像出版社','0245','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394024600','',1,'广西科技','0246','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394024700','',1,'广西科学技术出版社','0247','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394024800','',1,'广西美术出版社','0248','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394024900','',1,'广西民族出版社','0249','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394025000','',1,'广西人民出版社','0250','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394025100','',1,'广西师范大学出版社','0251','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394025200','',1,'广西音像出版社','0252','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394025300','',1,'广州出版社','0253','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394025400','',1,'广州暨南大学出版社','0254','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394025500','',1,'广州暨南大学出版社有限责任公司','0255','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394025600','',1,'广州外语音像','0256','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394025700','',1,'广州外语音像出版社','0257','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394025800','',1,'广州文化出版社','0258','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394025900','',1,'广州新时代影音公司','0259','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394026000','',1,'广州音像出版社','0260','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394026100','',1,'贵州大学出版社','0261','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394026200','',1,'贵州东方音像出版社','0262','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394026300','',1,'贵州教育出版社','0263','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394026400','',1,'贵州科技出版社','0264','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394026500','',1,'贵州科学技术出版社','0265','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394026600','',1,'贵州美术出版社','0266','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394026700','',1,'贵州民族出版社','0267','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394026800','',1,'贵州人民出版社','0268','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394026900','',1,'贵州文化音像出版社','0269','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394027000','',1,'桂林贝贝特电子音像出版社','0270','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394027100','',1,'国防大学出版社','0271','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394027200','',1,'国防工业出版社','0272','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394027300','',1,'国防科技大学出版社','0273','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394027400','',1,'国国际广播音像出版社','0274','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394027500','',1,'国际出版公司','0275','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394027600','',1,'国际交流基金','0276','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394027700','',1,'国际文化出版公司','0277','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394027800','',1,'国际文化出版社公司','0278','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394027900','',1,'国际文化交流音像出版社','0279','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394028000','',1,'国家开放大学出版社','0280','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394028100','',1,'国家图书馆出版社','0281','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394028200','',1,'国家行政学院出版社','0282','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394028300','',1,'国家行政学院音像出版社','0283','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394028400','',1,'哈尔滨出版社','0284','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394028500','',1,'哈尔滨地图出版社','0285','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394028600','',1,'哈尔滨工程大学出版社','0286','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394028700','',1,'哈尔滨工业大学出版社','0287','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394028800','',1,'哈工业大学音像出版社','0288','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394028900','',1,'海潮出版社','0289','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394029000','',1,'海潮摄影艺术出版社','0290','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394029100','',1,'海电子出版有限公司','0291','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394029200','',1,'海风出版社','0292','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394029300','',1,'海军出版社','0293','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394029400','',1,'海南出版社','0294','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394029500','',1,'海南人民出版社','0295','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394029600','',1,'海南摄影美术出版社','0296','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394029700','',1,'海南省电子音像出版社','0297','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394029800','',1,'海天出版 社','0298','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394029900','',1,'海天出版社','0299','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394030000','',1,'海豚出版社','0300','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394030100','',1,'海文音像出版社','0301','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394030200','',1,'海峡书局','0302','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394030300','',1,'海峡文艺出版社','0303','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394030400','',1,'海燕出版社','0304','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394030500','',1,'海洋出版社','0305','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394030600','',1,'海洋国际出版社','0306','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394030700','',1,'海洋国家出版社','0307','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394030800','',1,'韩国','0308','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394030900','',1,'韩语商务文化韩语部','0309','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394031000','',1,'汉书出版','0310','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394031100','',1,'杭州出版社','0311','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394031200','',1,'杭州大学出版社','0312','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394031300','',1,'航空工业出版社','0313','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394031400','',1,'合肥工业大学出版社','0314','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394031500','',1,'河北百灵音像出版社','0315','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394031600','',1,'河北大学出版社','0316','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394031700','',1,'河北教育出版社','0317','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394031800','',1,'河北教育音像出版社','0318','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394031900','',1,'河北科学技术出版社','0319','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394032000','',1,'河北美术出版社','0320','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394032100','',1,'河北人民出版社','0321','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394032200','',1,'河北少年儿童出版社','0322','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394032300','',1,'河北音像出版社','0323','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394032400','',1,'河海大学出版社','0324','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394032500','',1,'河南大学出版社','0325','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394032600','',1,'河南电子音像出版社','0326','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394032700','',1,'河南科学技术出版社','0327','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394032800','',1,'河南美术出版社','0328','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394032900','',1,'河南人民出版社','0329','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394033000','',1,'河南文艺出版社','0330','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394033100','',1,'黑龙江朝鲜民族出版社','0331','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394033200','',1,'黑龙江大学出版社','0332','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394033300','',1,'黑龙江教育出版社','0333','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394033400','',1,'黑龙江科学技术出版社','0334','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394033500','',1,'黑龙江美术出版社','0335','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394033600','',1,'黑龙江人民出版社','0336','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394033700','',1,'黑龙江少年儿童出版社','0337','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394033800','',1,'黑龙江文化电子音像出版社','0338','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394033900','',1,'黑龙江文化音像出版社','0339','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394034000','',1,'红旗出版社','0340','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394034100','',1,'红旗音像电子出版社','0341','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394034200','',1,'红星电子音像出版社','0342','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394034300','',1,'洪恩儿童教育','0343','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394034400','',1,'湖北教育出版社','0344','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394034500','',1,'湖北九通电子音像出版社','0345','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394034600','',1,'湖北科学技术出版社','0346','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394034700','',1,'湖北美术出版社','0347','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394034800','',1,'湖北人民出版社','0348','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394034900','',1,'湖北少年儿童出版社','0349','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394035000','',1,'湖北音像艺术出版社','0350','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394035100','',1,'湖南大学出版社','0351','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394035200','',1,'湖南地图出版社','0352','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394035300','',1,'湖南地图出版社有限责任公司','0353','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394035400','',1,'湖南电子音像出版社','0354','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394035500','',1,'湖南电子音像出版社出版','0355','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394035600','',1,'湖南教育出版社','0356','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394035700','',1,'湖南教育电子出版社','0357','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394035800','',1,'湖南教育音像出版社','0358','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394035900','',1,'湖南科技出版社','0359','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394036000','',1,'湖南科学技术出版社','0360','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394036100','',1,'湖南美术出版社','0361','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394036200','',1,'湖南人民出版社','0362','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394036300','',1,'湖南人民出版社2','0363','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394036400','',1,'湖南少年儿童出版社','0364','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394036500','',1,'湖南少年儿童音像出版社','0365','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394036600','',1,'湖南省教育音像','0366','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394036700','',1,'湖南师范大学出版社','0367','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394036800','',1,'湖南文化丛书出版社','0368','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394036900','',1,'湖南文化音像出版社','0369','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394037000','',1,'湖南文艺出版社','0370','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394037100','',1,'湖南文艺出版社音像部','0371','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394037200','',1,'花城出版社','0372','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394037300','',1,'花冠文化','0373','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394037400','',1,'花山文艺出版社','0374','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394037500','',1,'华东理工大学出版社','0375','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394037600','',1,'华东师范大学出版社','0376','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394037700','',1,'华东师范大学电子音像出版社','0377','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394037800','',1,'华科音像出版社','0378','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394037900','',1,'华乐出版社','0379','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394038000','',1,'华龄出版社','0380','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394038100','',1,'华录电子出版有限公司','0381','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394038200','',1,'华录电子音像出版有限公司','0382','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394038300','',1,'华南理工大学出版社','0383','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394038400','',1,'华声电子出版社','0384','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394038500','',1,'华文出版社','0385','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394038600','',1,'华夏出版社','0386','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394038700','',1,'华艺出版社','0387','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394038800','',1,'华语教学出版社','0388','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394038900','',1,'华语教育出版社','0389','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394039000','',1,'华岳文艺出版社','0390','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394039100','',1,'华中科技大学出版社','0391','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394039200','',1,'华中科技大学电子音像出版社','0392','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394039300','',1,'华中理工大学出版社','0393','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394039400','',1,'华中师范大学出版社','0394','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394039500','',1,'化学工业出版社','0395','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394039600','',1,'黄河出版社','0396','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394039700','',1,'黄河水利出版社','0397','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394039800','',1,'黄河文艺出版社','0398','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394039900','',1,'黄山书社','0399','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394040000','',1,'机械工业','0400','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394040100','',1,'机械工业出版社','0401','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394040200','',1,'吉林出版集团股份有限公司','0402','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394040300','',1,'吉林出版集团有限责任公司','0403','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394040400','',1,'吉林大学出版社','0404','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394040500','',1,'吉林电子','0405','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394040600','',1,'吉林电子出版社','0406','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394040700','',1,'吉林教育出版社','0407','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394040800','',1,'吉林科学技术出版社','0408','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394040900','',1,'吉林美术出版社','0409','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394041000','',1,'吉林人民出版社','0410','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394041100','',1,'吉林摄影出版社','0411','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394041200','',1,'吉林省教育音像','0412','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394041300','',1,'吉林文化音像出版社','0413','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394041400','',1,'吉林文史出版社','0414','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394041500','',1,'吉林音像出版社','0415','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394041600','',1,'吉林银声音像出版社','0416','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394041700','',1,'济南出版社','0417','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394041800','',1,'暨南大学出版社','0418','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394041900','',1,'家电维修杂志社','0419','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394042000','',1,'剑桥出版社','0420','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394042100','',1,'江南音像出版社','0421','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394042200','',1,'江苏大学出版社','0422','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394042300','',1,'江苏电子音像出版社','0423','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394042400','',1,'江苏凤凰电子音像出版社','0424','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394042500','',1,'江苏凤凰科学技术出版社','0425','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394042600','',1,'江苏凤凰美术出版社','0426','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394042700','',1,'江苏凤凰少年儿童出版社','0427','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394042800','',1,'江苏凤凰文艺出版社','0428','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394042900','',1,'江苏古籍出��社','0429','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394043000','',1,'江苏广陵书社','0430','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394043100','',1,'江苏教育出版社','0431','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394043200','',1,'江苏科学技术出版社','0432','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394043300','',1,'江苏美术出版社','0433','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394043400','',1,'江苏人民出版社','0434','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394043500','',1,'江苏少年儿童出版社','0435','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394043600','',1,'江苏文艺出版社','0436','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394043700','',1,'江西高校出版社','0437','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394043800','',1,'江西高校社','0438','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394043900','',1,'江西教育出版社','0439','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394044000','',1,'江西教育音像电子出版社','0440','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394044100','',1,'江西科学技术出版社','0441','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394044200','',1,'江西美术出版社','0442','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394044300','',1,'江西人民出版社','0443','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394044400','',1,'江西省出版总社','0444','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394044500','',1,'江西文化音像出版社','0445','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394044600','',1,'教育部电化教育音像出版社','0446','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394044700','',1,'教育科学出版社','0447','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394044800','',1,'教育科学电子出版社','0448','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394044900','',1,'接力出版社','0449','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394045000','',1,'接力音像电子出版社','0450','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394045100','',1,'解放军出版社','0451','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394045200','',1,'解放军卫生音像出版社','0452','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394045300','',1,'解放军文艺出版社','0453','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394045400','',1,'今日中国出版社','0454','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394045500','',1,'今天杂志社','0455','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394045600','',1,'金版电子出版社','0456','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394045700','',1,'金报电子音像出版中心','0457','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394045800','',1,'金城出版社','0458','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394045900','',1,'金出版社电子出版社','0459','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394046000','',1,'金盾出版社','0460','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394046100','',1,'金盾音像出版社','0461','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394046200','',1,'京华出版社','0462','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394046300','',1,'经济管理出版社','0463','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394046400','',1,'经济科学出版社','0464','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394046500','',1,'经济日报出版社','0465','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394046600','',1,'荆楚书社','0466','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394046700','',1,'警官教育出版社','0467','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394046800','',1,'九通电子音像出版社','0468','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394046900','',1,'九州出版社','0469','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394047000','',1,'九州音像出版公司','0470','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394047100','',1,'九洲图书出版社','0471','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394047200','',1,'九洲音像','0472','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394047300','',1,'九洲音像出版公司','0473','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394047400','',1,'九洲音像出版社','0474','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394047500','',1,'军事科学出版社','0475','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394047600','',1,'军事医学科学出版社','0476','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394047700','',1,'军事谊文出版社','0477','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394047800','',1,'喀什维吾尔文出版社','0478','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394047900','',1,'开明出版社','0479','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394048000','',1,'开明文教音像','0480','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394048100','',1,'开明文教音像出版社','0481','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394048200','',1,'科学出版社','0482','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394048300','',1,'科学技术文献出版社','0483','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394048400','',1,'科学教育出版社','0484','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394048500','',1,'科学普及出版社','0485','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394048600','',1,'克孜勒苏柯尔克孜文出版社','0486','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394048700','',1,'孔学堂书局','0487','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394048800','',1,'昆仑出版社','0488','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394048900','',1,'兰州大学出版社','0489','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394049000','',1,'蓝天出版社','0490','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394049100','',1,'漓江出版社','0491','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394049200','',1,'李相志','0492','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394049300','',1,'立信会计出版社','0493','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394049400','',1,'辽海出版社','0494','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394049500','',1,'辽海图书发行有限公司','0495','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394049600','',1,'辽宁大学出版社','0496','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394049700','',1,'辽宁电子出版社','0497','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394049800','',1,'辽宁广播电视音像出版社','0498','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394049900','',1,'辽宁教育出版社','0499','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394050000','',1,'辽宁教育电子出版公司','0500','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394050100','',1,'辽宁教育电子音像出版社','0501','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394050200','',1,'辽宁科学技术','0502','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394050300','',1,'辽宁科学技术出版社','0503','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394050400','',1,'辽宁美术出版社','0504','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394050500','',1,'辽宁民族出版社','0505','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394050600','',1,'辽宁人民出版社','0506','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394050700','',1,'辽宁少年儿童出版社','0507','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394050800','',1,'辽宁师范大学出版社','0508','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394050900','',1,'辽宁文化艺术音像出版社','0509','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394051000','',1,'辽宁音像出版社','0510','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394051100','',1,'岭南美术出版社','0511','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394051200','',1,'龙门书局','0512','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394051300','',1,'鹭江出版社','0513','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394051400','',1,'旅游教育出版社','0514','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394051500','',1,'麦特立达','0515','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394051600','',1,'煤炭工业出版社','0516','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394051700','',1,'民主与建设出版社','0517','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394051800','',1,'民族出版社','0518','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394051900','',1,'民族音像出版社','0519','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394052000','',1,'明天出版社','0520','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394052100','',1,'内蒙古大学出版社','0521','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394052200','',1,'内蒙古教育出版社','0522','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394052300','',1,'内蒙古科技出版社','0523','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394052400','',1,'内蒙古人民出版社','0524','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394052500','',1,'内蒙古少年儿童出版社','0525','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394052600','',1,'内蒙古文化出版社','0526','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394052700','',1,'内蒙古文艺出版社','0527','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394052800','',1,'南方出版社','0528','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394052900','',1,'南方日报出版社','0529','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394053000','',1,'南海出版社','0530','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394053100','',1,'南京出版社','0531','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394053200','',1,'南京大学出版社','0532','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394053300','',1,'南京大学电子音像出版社','0533','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394053400','',1,'南京师范大学出版社','0534','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394053500','',1,'南京音像出版社','0535','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394053600','',1,'南开大学出版社','0536','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394053700','',1,'南开大学电子音像出版社','0537','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394053800','',1,'能源出版社','0538','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394053900','',1,'宁波出版社','0539','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394054000','',1,'宁夏大地音像出版社','0540','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394054100','',1,'宁夏人民出版社','0541','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394054200','',1,'宁夏人民教育出版社','0542','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394054300','',1,'宁夏少年儿童出版社','0543','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394054400','',1,'农村读物出版社','0544','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394054500','',1,'农业教育声像出版社','0545','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394054600','',1,'浦东电子','0546','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394054700','',1,'浦东电子出版社','0547','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394054800','',1,'齐鲁电子出版社','0548','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394054900','',1,'齐鲁电子音像出版社','0549','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394055000','',1,'齐鲁书社','0550','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394055100','',1,'齐鲁音像出版社','0551','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394055200','',1,'企业管理出版社','0552','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394055300','',1,'气象出版社','0553','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394055400','',1,'青岛出版社','0554','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394055500','',1,'青岛电子音像出版社','0555','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394055600','',1,'青岛海洋大学出版社','0556','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394055700','',1,'青海民族出版社','0557','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394055800','',1,'青海人民出版社','0558','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394055900','',1,'清华大学出版社','0559','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394056000','',1,'清华大学音像','0560','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394056100','',1,'清华同方光盘电子出版社','0561','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394056200','',1,'清华同光电子出版社','0562','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394056300','',1,'求实出版社','0563','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394056400','',1,'求真出版社','0564','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394056500','',1,'全国继续医学教育委员会中华医学会','0565','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394056600','',1,'群言出版社','0566','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394056700','',1,'群益堂','0567','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394056800','',1,'群众出版社','0568','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394056900','',1,'人民出版社','0569','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394057000','',1,'人民法院出版社','0570','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394057100','',1,'人民法院音像出版社','0571','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394057200','',1,'人民画报社','0572','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394057300','',1,'人民交通出版社','0573','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394057400','',1,'人民交通音像电子出版社','0574','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394057500','',1,'人民教育出版社','0575','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394057600','',1,'人民教育电子音像出版社','0576','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394057700','',1,'人民军医出版社','0577','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394057800','',1,'人民军医出版社发行部','0578','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394057900','',1,'人民军医电子出版社','0579','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394058000','',1,'人民美术出版社','0580','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394058100','',1,'人民日报出版社','0581','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394058200','',1,'人民体育出版社','0582','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394058300','',1,'人民卫生出版社','0583','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394058400','',1,'人民卫生电子音像出版社','0584','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394058500','',1,'人民文学出版社','0585','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394058600','',1,'人民武警出版社','0586','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394058700','',1,'人民音乐出版社','0587','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394058800','',1,'人民邮电出版社','0588','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394058900','',1,'人民中国出版社','0589','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394059000','',1,'日本文学','0590','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394059100','',1,'荣宝斋出版社','0591','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394059200','',1,'三辰影库音像出版社','0592','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394059300','',1,'三环出版社','0593','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394059400','',1,'三联书店上海分店','0594','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394059500','',1,'三联书店音像电子出版社','0595','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394059600','',1,'三秦出版社','0596','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394059700','',1,'山东大学出版社','0597','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394059800','',1,'山东电子音响出版集团','0598','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394059900','',1,'山东电子音像出版社','0599','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394060000','',1,'山东电子影像出版社','0600','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394060100','',1,'山东画报出版社','0601','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394060200','',1,'山东教育出版社','0602','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394060300','',1,'山东教育电子音像出版社','0603','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394060400','',1,'山东科学技术出版社','0604','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394060500','',1,'山东美术出版社','0605','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394060600','',1,'山东明天图书发行中心','0606','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394060700','',1,'山东农业大学电子音像出版社','0607','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394060800','',1,'山东人民出版社','0608','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394060900','',1,'山东省地图出版社','0609','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394061000','',1,'山东文化音像出版社','0610','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394061100','',1,'山东文艺出版社','0611','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394061200','',1,'山东友谊出版社','0612','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394061300','',1,'山西出版社集团(三晋出版社)','0613','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394061400','',1,'山西春秋音像出版社','0614','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394061500','',1,'山西高校联合出版社','0615','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394061600','',1,'山西古籍出版社','0616','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394061700','',1,'山西教育出版社','0617','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394061800','',1,'山西经济出版社','0618','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394061900','',1,'山西科学技术出版社','0619','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394062000','',1,'山西人民出版社','0620','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394062100','',1,'陕西电子音像出版社','0621','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394062200','',1,'陕西科学技术出版社','0622','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394062300','',1,'陕西旅游出版社','0623','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394062400','',1,'陕西人民出版社','0624','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394062500','',1,'陕西人民教育出版社','0625','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394062600','',1,'陕西人民美术出版社','0626','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394062700','',1,'陕西摄影出版社','0627','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394062800','',1,'陕西师范大学出版社','0628','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394062900','',1,'陕西师范大学音像出版社','0629','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394063000','',1,'汕头大学出版社','0630','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394063100','',1,'汕头海洋音像出版社','0631','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394063200','',1,'商务印书馆','0632','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394063300','',1,'商务印书馆电子音像出版中心','0633','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394063400','',1,'商务印书馆国际有限公司','0634','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394063500','',1,'上海百家出版社','0635','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394063600','',1,'上海财经大学出版社','0636','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394063700','',1,'上海财经大学电子出版社','0637','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394063800','',1,'上海辞书出版社','0638','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394063900','',1,'上海大学出版社','0639','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394064000','',1,'上海电影音像出版社','0640','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394064100','',1,'上海电子出版有限公司','0641','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394064200','',1,'上海翻译出版公司','0642','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394064300','',1,'上海高教电子音像出版社','0643','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394064400','',1,'上海高教音像出版社','0644','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394064500','',1,'上海古籍出版社','0645','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394064600','',1,'上海画报出版社','0646','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394064700','',1,'上海交通大学出版社','0647','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394064800','',1,'上海交通大学电子音像出版社','0648','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394064900','',1,'上海教育出版社','0649','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394065000','',1,'上海科技教育出版社','0650','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394065100','',1,'上海科学技术出版社','0651','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394065200','',1,'上海科学技术文献出版社','0652','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394065300','',1,'上海科学普及出版社','0653','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394065400','',1,'上海录像公司','0654','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394065500','',1,'上海人教海文图书音像有限公司','0655','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394065600','',1,'上海人民出版社','0656','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394065700','',1,'上海人民美术出版社','0657','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394065800','',1,'上海社会科学院出版社','0658','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394065900','',1,'上海声像出版社','0659','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394066000','',1,'上海声像电子出版社','0660','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394066100','',1,'上海世界出版集团','0661','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394066200','',1,'上海书店出版社','0662','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394066300','',1,'上海书画出版社','0663','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394066400','',1,'上海外语出版社','0664','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394066500','',1,'上海外语电子出版社','0665','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394066600','',1,'上海外语教育出版社','0666','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394066700','',1,'上海外语音像出版社','0667','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394066800','',1,'上海文化出版社','0668','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394066900','',1,'上海文艺出版社','0669','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394067000','',1,'上海文艺出版总社','0670','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394067100','',1,'上海文艺音像','0671','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394067200','',1,'上海信息传播音像出版社','0672','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394067300','',1,'上海医科大学出版社','0673','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394067400','',1,'上海译文出版社','0674','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394067500','',1,'上海音乐出版社','0675','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394067600','',1,'上海音乐学院出版社','0676','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394067700','',1,'上海音像公司','0677','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394067800','',1,'上海远东出版社','0678','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394067900','',1,'上海中药大学出版社','0679','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394068000','',1,'上海中医药大学出版社','0680','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394068100','',1,'少年儿童出版社','0681','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394068200','',1,'社会科学文献出版社','0682','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394068300','',1,'社会科学文献电子音像出版社','0683','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394068400','',1,'深圳报业集团出版社','0684','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394068500','',1,'深圳摄影出版社','0685','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394068600','',1,'深圳市都市创想文化传播有限公司','0686','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394068700','',1,'深圳市激光节目发行公司','0687','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394068800','',1,'深圳音像出版公司','0688','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394068900','',1,'深圳音像公司','0689','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394069000','',1,'深圳音像公司出版','0690','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394069100','',1,'沈阳出版社','0691','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394069200','',1,'沈阳师范大学出版社','0692','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394069300','',1,'生活书店出版有限公司','0693','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394069400','',1,'石油大学出版社','0694','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394069500','',1,'石油工业出版社','0695','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394069600','',1,'时代出版传媒股份有限公司','0696','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394069700','',1,'时代文艺出版社','0697','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394069800','',1,'时代新媒体出版社','0698','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394069900','',1,'时事出版社','0699','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394070000','',1,'时闻出版社','0700','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394070100','',1,'世界图书','0701','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394070200','',1,'世界图书出版公司','0702','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394070300','',1,'世界图书出版社','0703','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394070400','',1,'世界图书音像电子出版社','0704','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394070500','',1,'世界知识出版社','0705','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394070600','',1,'世界知识音像出版社','0706','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394070700','',1,'世图音像电子出版社','0707','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394070800','',1,'收获文学杂志社','0708','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394070900','',1,'首都经济贸易大学出版社','0709','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394071000','',1,'首都师范大学出版社','0710','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394071100','',1,'淑馨出版','0711','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394071200','',1,'水利水电出版社','0712','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394071300','',1,'四川出版集团','0713','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394071400','',1,'四川出版社集团巴蜀书社','0714','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394071500','',1,'四川辞书出版社','0715','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394071600','',1,'四川大学出版社','0716','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394071700','',1,'四川大学电子出版社','0717','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394071800','',1,'四川大学音像出版社','0718','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394071900','',1,'四川党建音像出版社','0719','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394072000','',1,'四川电子音像出版社','0720','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394072100','',1,'四川电子音像出版中心','0721','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394072200','',1,'四川电子音中心','0722','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394072300','',1,'四川教育出版社','0723','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394072400','',1,'四川科学技术出版社','0724','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394072500','',1,'四川美术出版社','0725','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394072600','',1,'四川民族出版社','0726','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394072700','',1,'四川人民出版社','0727','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394072800','',1,'四川少年儿童出版社','0728','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394072900','',1,'四川省教育音像','0729','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394073000','',1,'四川省社会科学院出版社','0730','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394073100','',1,'四川师范大学','0731','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394073200','',1,'四川师范大学电子出版社','0732','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394073300','',1,'四川文艺出版社','0733','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394073400','',1,'四川远程电子出版社','0734','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394073500','',1,'苏州大学出版社','0735','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394073600','',1,'台海出版社','0736','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394073700','',1,'台声出版社','0737','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394073800','',1,'太白文艺出版社','0738','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394073900','',1,'太平洋影音公司','0739','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394074000','',1,'泰山出版社','0740','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394074100','',1,'天大电子出版社音像部','0741','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394074200','',1,'天地出版社','0742','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394074300','',1,'天健电子音像出版社','0743','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394074400','',1,'天津北洋音像出版社','0744','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394074500','',1,'天津出版传媒集团','0745','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394074600','',1,'天津大学出版社','0746','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394074700','',1,'天津大学电子出版社','0747','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394074800','',1,'天津电子出版社','0748','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394074900','',1,'天津古籍出版社','0749','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394075000','',1,'天津教育出版社','0750','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394075100','',1,'天津科技翻译出版公司','0751','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394075200','',1,'天津科学技术出版社','0752','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394075300','',1,'天津人民出版社','0753','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394075400','',1,'天津人民美术出版社','0754','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394075500','',1,'天津社会科学出版社','0755','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394075600','',1,'天津社会科学院出版社','0756','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394075700','',1,'天津市文化艺术音像出版社','0757','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394075800','',1,'天津外语音像出版社','0758','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394075900','',1,'天津杨柳青画社','0759','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394076000','',1,'天津音像公司','0760','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394076100','',1,'天下文化书坊','0761','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394076200','',1,'天则出版社','0762','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394076300','',1,'同济大学出版社','0763','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394076400','',1,'同济大学电子音像出版社','0764','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394076500','',1,'同济大学音响出版社','0765','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394076600','',1,'同心出版社','0766','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394076700','',1,'童趣','0767','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394076800','',1,'团结出版社','0768','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394076900','',1,'外国文学出版社','0769','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394077000','',1,'外文出版社','0770','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394077100','',1,'外研社','0771','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394077200','',1,'外语教学与研究出版社','0772','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394077300','',1,'万方数据','0773','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394077400','',1,'万方数据电子出版社','0774','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394077500','',1,'万国学术出版社','0775','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394077600','',1,'万卷出版公司','0776','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394077700','',1,'万卷出版社公司','0777','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394077800','',1,'网迷杂志社','0778','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394077900','',1,'微型计算机杂志社','0779','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394078000','',1,'未来出版社','0780','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394078100','',1,'文光出版社','0781','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394078200','',1,'文化教育出版社','0782','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394078300','',1,'文化艺术出版社','0783','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394078400','',1,'文汇出版社','0784','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394078500','',1,'文津出版社','0785','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394078600','',1,'文物出版社','0786','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394078700','',1,'文心出版社','0787','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394078800','',1,'文心社','0788','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394078900','',1,'文学古籍出版社','0789','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394079000','',1,'文字改革出版社','0790','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394079100','',1,'无出版社信息','0791','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394079200','',1,'五洲传播出版社','0792','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394079300','',1,'五洲传播电子出版社','0793','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394079400','',1,'武汉测绘科技大学出版社','0794','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394079500','',1,'武汉出版社','0795','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394079600','',1,'武汉大学出版社','0796','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394079700','',1,'武汉理工大学出版社','0797','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394079800','',1,'武汉水利电力大学出版社','0798','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394079900','',1,'武警音像出版社','0799','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394080000','',1,'西安出版社','0800','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394080100','',1,'西安地图出版社','0801','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394080200','',1,'西安电影录音录像出版社','0802','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394080300','',1,'西安电子科技大学出版社','0803','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394080400','',1,'西安电子科技大学电子出版社','0804','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394080500','',1,'西安交通大学出版社','0805','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394080600','',1,'西安交通大学音像出版社','0806','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394080700','',1,'西安外语音像教材','0807','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394080800','',1,'西安文艺出版社','0808','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394080900','',1,'西北大学出版社','0809','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394081000','',1,'西北工业大学出版社','0810','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394081100','',1,'西北工业大学音像出版社','0811','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394081200','',1,'西北工业大学音像电子出版社','0812','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394081300','',1,'西北工业大学影响电子出版社','0813','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394081400','',1,'西北农林科技大学出版社','0814','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394081500','',1,'西藏藏文古籍出版社','0815','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394081600','',1,'西藏人民出版社','0816','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394081700','',1,'西藏印社出版社','0817','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394081800','',1,'西泠印社','0818','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394081900','',1,'西泠印社出版社','0819','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394082000','',1,'西南财经大学出版社','0820','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394082100','',1,'西南交通大学出版社','0821','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394082200','',1,'西南交通大学社','0822','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394082300','',1,'西南科技文化出版社','0823','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394082400','',1,'西南师范大学出版社','0824','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394082500','',1,'西南师范大学音像出版社','0825','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394082600','',1,'西苑出版社','0826','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394082700','',1,'希望出版社','0827','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394082800','',1,'厦门大学出版社','0828','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394082900','',1,'厦门大学电子出版社','0829','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394083000','',1,'厦门音像出版社','0830','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394083100','',1,'现代出版社','0831','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394083200','',1,'现代教育出版社','0832','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394083300','',1,'线装书局','0833','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394083400','',1,'香港文汇出版社','0834','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394083500','',1,'湘潭大学出版社','0835','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394083600','',1,'新华出版社','0836','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394083700','',1,'新华音像中心出版社','0837','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394083800','',1,'新疆大学出版社','0838','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394083900','',1,'新疆电子出版社','0839','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394084000','',1,'新疆电子音像出版社','0840','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394084100','',1,'新疆教育出版社','0841','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394084200','',1,'新疆科技卫生出版社','0842','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394084300','',1,'新疆科学技术出版社','0843','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394084400','',1,'新疆科学技术出版珠宝店','0844','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394084500','',1,'新疆美术摄影出版社','0845','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394084600','',1,'新疆青少年出版社','0846','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394084700','',1,'新疆人民出版社','0847','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394084800','',1,'新疆少儿出版社','0848','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394084900','',1,'新疆生产建设兵团出版社','0849','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394085000','',1,'新蕾出版社','0850','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394085100','',1,'新时代出版社','0851','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394085200','',1,'新时代影音公司','0852','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394085300','',1,'新世纪出��社','0853','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394085400','',1,'新世界出版社','0854','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394085500','',1,'新星出版社','0855','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394085600','',1,'兴园音像电子','0856','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394085700','',1,'星球地图出版社','0857','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394085800','',1,'学林','0858','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394085900','',1,'学林出版社','0859','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394086000','',1,'学术期刊出版社','0860','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394086100','',1,'学习出版社','0861','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394086200','',1,'学习出版社 人名出版社','0862','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394086300','',1,'学苑出版社','0863','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394086400','',1,'学苑音像出版社','0864','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394086500','',1,'延边大学出版社','0865','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394086600','',1,'延边教育出版社','0866','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394086700','',1,'延边人民出版社','0867','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394086800','',1,'研究出版社','0868','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394086900','',1,'演讲与口才杂志社','0869','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394087000','',1,'燕山大学出版社','0870','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394087100','',1,'羊城晚报','0871','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394087200','',1,'羊城晚报出版社','0872','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394087300','',1,'阳光出版社','0873','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394087400','',1,'冶金工业出版社','0874','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394087500','',1,'伊犁人民出版社','0875','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394087600','',1,'宜新文化事业有限公司','0876','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394087700','',1,'译林出版社','0877','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394087800','',1,'银声音像出版社','0878','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394087900','',1,'银夏出版社','0879','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394088000','',1,'银夏社信息','0880','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394088100','',1,'印刷工业出版社','0881','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394088200','',1,'英华电子音像出版社','0882','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394088300','',1,'宇航出版社','0883','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394088400','',1,'语文.音像出版社','0884','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394088500','',1,'语文出版社','0885','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394088600','',1,'原子能出版社','0886','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394088700','',1,'远方出版社','0887','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394088800','',1,'岳麓出版社','0888','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394088900','',1,'岳麓书社','0889','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394089000','',1,'云南出版集团公司 晨光出版社','0890','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394089100','',1,'云南大学出版社','0891','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394089200','',1,'云南大学音像出版社','0892','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394089300','',1,'云南教育出版社','0893','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394089400','',1,'云南教育音像电子出版社','0894','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394089500','',1,'云南科技出版社','0895','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394089600','',1,'云南美术出版社','0896','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394089700','',1,'云南民族出版社','0897','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394089800','',1,'云南民族文化音像出版社','0898','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394089900','',1,'云南人民出版社','0899','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394090000','',1,'云南人民出版社(云南人民电子音像)','0900','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394090100','',1,'云南人民电子音像出版社','0901','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394090200','',1,'云南文艺出版社','0902','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394090300','',1,'浙江出版集团数字传媒有限公司','0903','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394090400','',1,'浙江大学出版社','0904','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394090500','',1,'浙江大学电子音像出版社','0905','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394090600','',1,'浙江电子音像出版社','0906','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394090700','',1,'浙江电子音像出版社有限公司','0907','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394090800','',1,'浙江工商大学出版社','0908','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394090900','',1,'浙江古籍出版社','0909','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394091000','',1,'浙江教育出版社','0910','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394091100','',1,'浙江科学技术','0911','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394091200','',1,'浙江科学技术出版社','0912','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394091300','',1,'浙江人民出版社','0913','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394091400','',1,'浙江人民美术出版社','0914','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394091500','',1,'浙江少年儿童出版社','0915','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394091600','',1,'浙江摄影出版社','0916','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394091700','',1,'浙江文艺出版社','0917','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394091800','',1,'浙江文艺音像出版社','0918','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394091900','',1,'浙江音像出版社','0919','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394092000','',1,'郑州大学出版社','0920','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394092100','',1,'知识产权出版社','0921','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394092200','',1,'知识出版社','0922','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394092300','',1,'中唱深圳公司','0923','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394092400','',1,'中共党史出版社','0924','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394092500','',1,'中共中央党校出版社','0925','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394092600','',1,'中国ISBN中心','0926','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394092700','',1,'中国标准出版社','0927','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394092800','',1,'中国财政经济出版社','0928','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394092900','',1,'中国藏学出版社','0929','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394093000','',1,'中国策划出版社','0930','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394093100','',1,'中国长安出版社','0931','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394093200','',1,'中国唱片广州公司','0932','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394093300','',1,'中国唱片上海公司','0933','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394093400','',1,'中国唱片深圳公司','0934','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394093500','',1,'中国唱片总公司','0935','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394093600','',1,'中国城市出版社','0936','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394093700','',1,'中国传媒大学出版社','0937','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394093800','',1,'中国大百科全书出版社','0938','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394093900','',1,'中国大百科全书电子音像出版社','0939','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394094000','',1,'中国大地出版社','0940','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394094100','',1,'中国档案出版社','0941','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394094200','',1,'中国地图出版社','0942','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394094300','',1,'中国地质大学出版社','0943','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394094400','',1,'中国电力出版社','0944','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394094500','',1,'中国电力音像电子出版社','0945','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394094600','',1,'中国电脑教育报社','0946','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394094700','',1,'中国电影出版社','0947','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394094800','',1,'中国电子音像出版社','0948','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394094900','',1,'中国对外翻译出版公司','0949','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394095000','',1,'中国发展出版社','0950','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394095100','',1,'中国法制出版社','0951','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394095200','',1,'中国方正出版社','0952','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394095300','',1,'中国纺织出版社','0953','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394095400','',1,'中国妇女出版社','0954','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394095500','',1,'中国工人出版社','0955','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394095600','',1,'中国工商出版社','0956','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394095700','',1,'中国广播电视出版社','0957','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394095800','',1,'中国广播音像出版社','0958','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394095900','',1,'中国国际电视总公司','0959','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394096000','',1,'中国国际广播出版社','0960','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394096100','',1,'中国国际广播音像出版社','0961','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394096200','',1,'中国国家地理杂志社','0962','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394096300','',1,'中国海关出版社','0963','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394096400','',1,'中国海洋大学出版社','0964','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394096500','',1,'中国航海图书出版社','0965','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394096600','',1,'中国和平出版社','0966','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394096700','',1,'中国和平音像电子出版社','0967','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394096800','',1,'中国华侨','0968','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394096900','',1,'中国华侨出版公司','0969','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394097000','',1,'中国华侨出版社','0970','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394097100','',1,'中国画报出版社','0971','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394097200','',1,'中国环境科学出版社','0972','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394097300','',1,'中国集邮出版社','0973','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394097400','',1,'中国计划出版社','0974','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394097500','',1,'中国检察出版社','0975','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394097600','',1,'中国建材工业出版社','0976','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394097700','',1,'中国建筑工业出版社','0977','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394097800','',1,'中国教育文化出版社','0978','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394097900','',1,'中国金融出版社','0979','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394098000','',1,'中国经济出版社','0980','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394098100','',1,'中国康艺音像出版社','0981','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394098200','',1,'中国科技大学','0982','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394098300','',1,'中国科技大学音像','0983','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394098400','',1,'中国科学技术出版社','0984','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394098500','',1,'中国科学技术大学出版社','0985','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394098600','',1,'中国科学技术协会音像中心','0986','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394098700','',1,'中国科学文化出版社','0987','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394098800','',1,'中国科学文化音像出版社','0988','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394098900','',1,'中国矿业大学出版社','0989','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394099000','',1,'中国劳动保障音像电子出版社','0990','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394099100','',1,'中国劳动出版社','0991','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394099200','',1,'中国劳动社会保障出版社','0992','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394099300','',1,'中国林业出版社','0993','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394099400','',1,'中国录音录像出版总社','0994','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394099500','',1,'中国旅游出版社','0995','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394099600','',1,'中国盲文出版社','0996','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394099700','',1,'中国盲文出版社有声读物部','0997','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394099800','',1,'中国美术学院出版社','0998','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394099900','',1,'中国民航出版社','0999','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394100000','',1,'中国民间文艺出版社','1000','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394100100','',1,'中国民主法制出版社','1001','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394100200','',1,'中国民族摄影艺术出版社','1002','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394100300','',1,'中国民族音像出版社','1003','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394100400','',1,'中国农业出版社','1004','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394100500','',1,'中国农业大学出版社','1005','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394100600','',1,'中国农业机械出版社','1006','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394100700','',1,'中国农业科技出版社','1007','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394100800','',1,'中国农业科学技术出版','1008','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394100900','',1,'中国农业科学技术出版社','1009','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394101000','',1,'中国农影音像出版社','1010','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394101100','',1,'中国青年出版社','1011','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394101200','',1,'中国青年电子出版社','1012','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394101300','',1,'中国青少年电子音像出版社','1013','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394101400','',1,'中国青少年音像出版社','1014','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394101500','',1,'中国轻工业出版社','1015','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394101600','',1,'中国曲艺出版社','1016','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394101700','',1,'中国人口出版社','1017','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394101800','',1,'中国人民大学出版社','1018','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394101900','',1,'中国人民公安大学出版社','1019','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394102000','',1,'中国人民解放军音像出版社','1020','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394102100','',1,'中国人事出版社','1021','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394102200','',1,'中国三环音像社','1022','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394102300','',1,'中国三峡出版社','1023','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394102400','',1,'中国商务出版社','1024','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394102500','',1,'中国商务出版社(对外经济贸易出版社)','1025','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394102600','',1,'中国商业出版社','1026','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394102700','',1,'中国少儿音像','1027','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394102800','',1,'中国少年儿童出版社','1028','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394102900','',1,'中国社会出版社','1029','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394103000','',1,'中国社会科学出版社','1030','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394103100','',1,'中国摄影出版社','1031','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394103200','',1,'中国石化出版社','1032','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394103300','',1,'中国时代经济出版社','1033','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394103400','',1,'中国时代经济音像电子出版社','1034','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394103500','',1,'中国食品出版社','1035','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394103600','',1,'中国世界语出版社','1036','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394103700','',1,'中国市场出版社','1037','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394103800','',1,'中国式社工黑商','1038','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394103900','',1,'中国书店出版社','1039','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394104000','',1,'中国书籍出版社','1040','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394104100','',1,'中国水利水电出版社','1041','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394104200','',1,'中国税务出版社','1042','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394104300','',1,'中国铁道出版社','1043','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394104400','',1,'中国统计出版社','1044','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394104500','',1,'中国文采声像出版公司','1045','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394104600','',1,'中国文联出版社','1046','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394104700','',1,'中国文联音像出版社','1047','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394104800','',1,'中国文史出版社','1048','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394104900','',1,'中国文学出版社','1049','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394105000','',1,'中国五洲传播音像出版社','1050','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394105100','',1,'中国舞蹈出版社','1051','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394105200','',1,'中国物价出版社','1052','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394105300','',1,'中国物质出版社','1053','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394105400','',1,'中国戏剧出版社','1054','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394105500','',1,'中国协和医科大学出版社','1055','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394105600','',1,'中国新闻出版社','1056','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394105700','',1,'中国言实出版社','1057','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394105800','',1,'中国医药科技出版社','1058','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394105900','',1,'中国音乐家音像出版社','1059','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394106000','',1,'中国友谊出版公司','1060','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394106100','',1,'中国宇航出版社','1061','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394106200','',1,'中国原子能出版社','1062','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394106300','',1,'中国展望出版社','1063','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394106400','',1,'中国政法大学出版社','1064','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394106500','',1,'中国职工音像','1065','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394106600','',1,'中国质检出版社','1066','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394106700','',1,'中国致公出版社','1067','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394106800','',1,'中国中医药出版社','1068','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394106900','',1,'中国卓越出版公司','1069','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394107000','',1,'中华地图学社','1070','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394107100','',1,'中华工商出版社','1071','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394107200','',1,'中华工商联合出版社','1072','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394107300','',1,'中华书局出版社','1073','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394107400','',1,'中华文艺音像出版社','1074','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394107500','',1,'中华医学电子音像出版社','1075','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394107600','',1,'中华医学音像出版社','1076','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394107700','',1,'中经录音录像公司','1077','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394107800','',1,'中科多媒体电子出版社','1078','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394107900','',1,'中科普传媒发展有限公司','1079','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394108000','',1,'中南大学出版社','1080','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394108100','',1,'中南大学音像出版社','1081','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394108200','',1,'中南工业大学出版社','1082','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394108300','',1,'中山大学出版社','1083','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394108400','',1,'中山大学音像出版社','1084','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394108500','',1,'中税华瑞电子出版有限公司','1085','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394108600','',1,'中外文化出版公司','1086','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394108700','',1,'中新音像出版社','1087','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394108800','',1,'中信出版集团股份有限公司','1088','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394108900','',1,'中信出版社','1089','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394109000','',1,'中央编译出版社','1090','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394109100','',1,'中央广播电视大学出版社','1091','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394109200','',1,'中央广播电视大学音像出版社','1092','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394109300','',1,'中央民族大学出版社','1093','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394109400','',1,'中央文献出版社','1094','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394109500','',1,'中央音乐学院出版社','1095','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394109600','',1,'中医古籍出版社','1096','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394109700','',1,'中影音像出版社','1097','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394109800','',1,'中原农民出版社','1098','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394109900','',1,'中州古籍出版社','1099','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394110000','',1,'重庆出版社','1100','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394110100','',1,'重庆大学出版社','1101','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394110200','',1,'重庆大学电子出版社','1102','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394110300','',1,'重庆大学电子音像出版社','1103','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394110400','',1,'重庆电子出版社','1104','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394110500','',1,'重庆电子音像','1105','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394110600','',1,'珠海百年电子音像出版社','1106','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394110700','',1,'珠海出版社','1107','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394110800','',1,'珠海特区音像出版社','1108','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394110900','',1,'珠江电影制片公司白天鹅音像出版社','1109','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394111000','',1,'珠江电子出版公司','1110','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394111100','',1,'紫禁城出版社','1111','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394111200','',1,'宗教文化出版社','1112','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394111300','',1,'纵横音像出版社','1113','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909498394111400','',1,'作家出版社','1114','press',NULL,NULL,'1','1','','2021-01-16 16:00:30','','2021-01-16 16:00:30',NULL),('1310909566418022401',NULL,1,'在读','1','student_course_status',NULL,NULL,'1','1','1192453807573942273','2020-09-29 19:49:30','','2020-09-29 19:49:30',NULL),('1310909593492254722',NULL,2,'停课','2','student_course_status',NULL,NULL,'1','1','1192453807573942273','2020-09-29 19:49:37','','2020-09-29 19:49:37',NULL),('1310909593492254723',NULL,1,'到课','1','attend_status',NULL,NULL,'1','1','','2020-10-01 08:28:46','','2020-10-01 08:28:46',NULL),('1310909593492254724',NULL,2,'请假','2','attend_status',NULL,NULL,'1','1','','2020-10-01 08:28:46','','2020-10-01 08:28:46',NULL),('1310909593492254725',NULL,3,'缺勤','3','attend_status',NULL,NULL,'1','1','','2020-10-01 08:28:46','','2020-10-01 08:28:46',NULL),('1310909593492254726',NULL,2,'已支付','2','order_status',NULL,NULL,'1','1','','2020-10-10 14:22:06','','2020-10-10 14:22:06',NULL),('1310909593492254727',NULL,3,'已作废','3','order_status',NULL,NULL,'1','1','','2020-10-10 14:22:06','','2020-10-10 14:22:06',NULL),('1310909593492254728',NULL,1,'开放','1','recruit_status',NULL,NULL,'1','1','','2020-10-10 17:09:18','','2020-10-10 17:09:18',NULL),('1310909593492254729',NULL,2,'满班后停止','2','recruit_status',NULL,NULL,'1','1','','2020-10-10 17:09:18','','2020-10-10 17:09:18',NULL),('1310909593492254730',NULL,3,'停止','0','recruit_status',NULL,NULL,'1','1','','2020-10-10 17:09:18','','2020-10-10 17:09:18',NULL),('1310909593492254731',NULL,1,'新报','1','order_detail_tag',NULL,NULL,'1','1','','2020-10-12 15:09:40','','2020-10-12 15:09:40',NULL),('1310909593492254732',NULL,2,'续报','2','order_detail_tag',NULL,NULL,'1','1','','2020-10-12 15:09:40','','2020-10-12 15:09:40',NULL),('1310909593492254733',NULL,3,'扩科','3','order_detail_tag',NULL,NULL,'1','1','','2020-10-12 15:09:40','','2020-10-12 15:09:40',NULL),('1500202203211219000','',1,'犹豫','01','deal_result_type',NULL,NULL,'1','1','','2022-03-22 10:47:40','','2022-03-22 10:47:40',NULL),('1500202203211219100','',1,'产品不适用','02','deal_result_type',NULL,NULL,'1','1','','2022-03-22 10:47:40','','2022-03-22 10:47:40',NULL),('1500202203211219200','',1,'其他','03','deal_result_type',NULL,NULL,'1','1','','2022-03-22 10:47:40','','2022-03-22 10:47:40',NULL),('1505102203202158100','',1,'10天','10','intention_days',NULL,NULL,'1','1','','2022-03-21 11:42:02','','2022-03-21 11:42:02',NULL),('1505102203202159200','',1,'15天','15','intention_days',NULL,NULL,'1','1','','2022-03-21 11:42:02','','2022-03-21 11:42:02',NULL),('1505102203202160300','',1,'30天','30','intention_days',NULL,NULL,'1','1','','2022-03-21 11:42:02','','2022-03-21 11:42:02',NULL),('1505102203202161400','',1,'60天','60','intention_days',NULL,NULL,'1','1','','2022-03-21 11:42:02','','2022-03-21 11:42:02',NULL),('1505102203202162500','',1,'90天','90','intention_days',NULL,NULL,'1','1','','2022-03-21 11:42:02','','2022-03-21 11:42:02',NULL);
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_type` (
  `dict_type_id` varchar(30) NOT NULL COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `in_use` char(1) DEFAULT '1' COMMENT '状态（0停用 1正常）',
  `can_change` char(1) NOT NULL DEFAULT '1' COMMENT '是否可修改 1可修改 0不可修改',
  `create_user` varchar(20) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(20) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_type_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` VALUES ('1217813759985201153','是否在用','in_use','1','0','','2020-01-16 22:20:00','','2020-01-16 22:20:00','是否在用 1:在用 0:停用'),('1217818797797289985','是否展示','is_show','1','0','','2020-01-16 22:40:02','','2020-01-16 22:40:02','是否展示 1:展示 2:隐藏'),('1239746361008705537','性别','sex','1','0','1192453807573942273','2020-03-17 10:52:20','','2020-03-17 10:52:20','性别 男:M 女:F'),('1239779361029124098','星期','week_day','1','0','1192453807573942273','2020-03-17 13:03:28','','2020-03-17 13:03:28','星期一 到 星期日'),('1241729790009008129','收费方式','charge_unit','1','0','1192453807573942273','2020-03-22 22:13:46','','2020-03-22 22:13:46',NULL),('1254388051564560385','省份','province_code','1','1','1192453807573942273','2020-04-26 20:33:11','','2020-04-26 20:33:11','省份'),('1254388121366167553','城市','city_code','1','1','1192453807573942273','2020-04-26 20:33:28','','2020-04-26 20:33:28',NULL),('1268132967981637635','课程教学模式','teaching_mode','1','0','','2020-01-16 22:20:00','','2020-01-16 22:20:00',''),('1268132967981637636','课程是否开售','sale','1','0','','2020-01-16 22:20:00','','2020-01-16 22:20:00',''),('1268132967981637637','课程收费模式','charge_type','1','0','','2020-01-16 22:20:00','','2020-01-16 22:20:00',''),('1268132967981637638','课程时间周期','date_unit','1','0','','2020-01-16 22:20:00','','2020-01-16 22:20:00',''),('1295327057030529026','人事状态','personnel_status','1','0','1192453807573942273','2020-08-17 19:50:11','','2020-08-17 19:50:11',NULL),('1295327057030529028','标签类型','tag_type','1','0','1192453807573942273','2020-08-17 19:50:11','','2020-08-17 19:50:11',NULL),('1295327057030529030','订单类型','order_type','1','0','1192453807573942273','2020-09-11 17:25:16','','2020-09-11 17:25:16',NULL),('1306869455178993665','排课规则','cla_time_rule_type','1','0','1192453807573942273','2020-09-18 16:15:33','','2020-09-18 16:15:33','sc_cla_time_rule.rule_type'),('1306869576667009025','排课重复方式','cla_time_repeat_type','1','0','1192453807573942273','2020-09-18 16:16:02','','2020-09-18 16:16:02','sc_cla_time_rule.repeat_type'),('1306869909304676353','是否','is_or_not','1','0','1192453807573942273','2020-09-18 16:17:21','','2020-09-18 16:17:21',NULL),('1306869909304676354','联系人关系','contact_relation','1','0','1192453807573942273','2020-09-28 09:15:04','','2020-09-28 09:15:04',NULL),('1310909498394800130','学生课程状态','student_course_status','1','0','1192453807573942273','2020-09-29 19:49:14','','2020-09-29 19:49:14','在读、停课等'),('1310909498394800131','到课状态','attend_status','1','0','1192453807573942273','2020-10-01 08:27:42','','2020-10-01 08:27:42',NULL),('1310909498394800132','订单状态','order_status','1','0','1192453807573942273','2020-10-01 08:27:42','','2020-10-01 08:27:42',NULL),('1310909498394800133','班级招生状态','recruit_status','1','0','','2020-10-10 17:08:29','','2020-10-10 17:08:29',NULL),('1310909498394800134','订单明细标签','order_detail_tag','1','0','','2020-10-12 15:08:10','','2020-10-12 15:08:10',NULL),('1310909498394800145','出版社','press','1','0','1192453807573942273','2021-01-16 10:52:20','','2021-01-16 10:52:20',NULL),('1505120220320215800','试用天数','intention_days','1','1',NULL,'2022-03-20 22:01:25','','2022-03-20 22:01:25',NULL),('1505120220321121900','试用处理结果类型','deal_result_type','1','1',NULL,'2022-03-21 12:20:40','','2022-03-21 12:20:40',NULL);
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_file`
--

DROP TABLE IF EXISTS `sys_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_file` (
  `file_id` varchar(30) NOT NULL,
  `parent_id` varchar(30) DEFAULT NULL COMMENT '所属父键',
  `reference_id` varchar(30) DEFAULT NULL COMMENT '关联表主键',
  `reference_table` varchar(30) DEFAULT NULL COMMENT '关联表',
  `pre_path` varchar(200) DEFAULT NULL COMMENT '存储路径前缀',
  `file_name` varchar(200) DEFAULT NULL COMMENT '文件名',
  `logical_name` varchar(200) DEFAULT NULL COMMENT '文件逻辑名称',
  `file_size` varchar(10) DEFAULT NULL COMMENT '文件大小',
  `file_suffix` varchar(10) DEFAULT NULL COMMENT '文件后缀',
  `file_p_type` varchar(20) DEFAULT '' COMMENT '文件大类',
  `file_type` varchar(30) DEFAULT NULL COMMENT '文件类型',
  `enable` char(1) DEFAULT '1',
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='附件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_file`
--

LOCK TABLES `sys_file` WRITE;
/*!40000 ALTER TABLE `sys_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_holiday`
--

DROP TABLE IF EXISTS `sys_holiday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_holiday` (
  `day` varchar(10) NOT NULL COMMENT '日期 格式yyyyMMdd',
  `day_type` varchar(2) NOT NULL COMMENT '日期类型 0工作日 1 假日 2节日',
  PRIMARY KEY (`day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='节假日表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_holiday`
--

LOCK TABLES `sys_holiday` WRITE;
/*!40000 ALTER TABLE `sys_holiday` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_holiday` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `menu_id` varchar(30) NOT NULL COMMENT '菜单ID',
  `parent_id` varchar(30) DEFAULT '0' COMMENT '父菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `menu_type` varchar(10) NOT NULL DEFAULT '' COMMENT '菜单类型（dir menu button）',
  `router_path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `out_url` char(1) DEFAULT '0' COMMENT '是否为外链（0否 1是）',
  `request_url` varchar(200) DEFAULT '' COMMENT '请求路径',
  `is_show` char(1) DEFAULT '1' COMMENT '是否显示（1显示 0隐藏）',
  `permission_meta` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `sort` int DEFAULT '0' COMMENT '显示顺序',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `enable` char(1) NOT NULL DEFAULT '1' COMMENT '是否在用 1在用 0非在用',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES ('1217417496517201910','-1','系统管理','dir','system',NULL,'1','','1',NULL,'system',10,'','2020-01-15 10:39:06','1'),('1217417496517201911','1217417496517201910','菜单管理','menu','menu','system/menu/index','0','','1','system:menu:list','list',1,'','2020-01-15 10:39:06','1'),('1217417496517201913','1217417496517201911','菜单新增','button','',NULL,'1','/api/system/menu/add/addMenu','1','system:menu:add','#',1,'','2020-01-15 12:36:47','1'),('1217417496517201914','1217417496517201911','菜单修改','button','',NULL,'1','/api/system/menu/update/updateMenu','1','system:menu:update','#',2,'','2020-01-15 12:39:00','1'),('1217417496517201915','1217417496517201911','菜单删除','button','',NULL,'1','/api/system/menu/delete/deleteById/*','1','system:menu:delete','#',3,'','2020-01-15 20:05:24','1'),('1217453627459817473','1217417496517201910','字典管理','menu','dict/type','system/dict/type','1','','1','system:dict:list','dict',2,'','2020-01-15 22:28:58','1'),('1217456967912574977','1217453627459817473','字典新增','button','',NULL,'1','/api/system/dict/type/add/addDictType','1','system:dict:add','#',1,'','2020-01-15 22:42:15','1'),('1217457410877214722','1217453627459817473','字典修改','button','',NULL,'1','/api/system/dict/type/update/updateDictType','1','system:dict:update','#',2,'','2020-01-15 22:44:00','1'),('1217457554142056450','1217453627459817473','字典删除','button','',NULL,'1','/api/system/dict/type/delete/deleteById/*','1','system:dict:delete','#',3,'','2020-01-15 22:44:34','1'),('1217816440560693250','1217417496517201910','字典数据','menu','dict/data/:dictTypeId(\\d+)','system/dict/data','1','','0',NULL,'documentation',3,'','2020-01-16 22:30:40','1'),('1218406446337634305','1217417496517201910','部门管理','menu','dept','system/dept/index','0','/api/system/menu/list/searchList','1','system:dept:list','tree',4,'','2020-01-18 13:35:08','1'),('1218407456770633730','1218406446337634305','部门新增','button','',NULL,'1','/api/system/dept/add/addDept','1','system:dept:add','#',1,'','2020-01-18 13:39:09','1'),('1218407593777573890','1218406446337634305','部门修改','button','',NULL,'1','/api/system/dept/update/updateDept','1','system:dept:update','#',2,'','2020-01-18 13:39:41','1'),('1218407770844311553','1218406446337634305','部门删除','button','',NULL,'1','/api/system/dept/delete/deleteById/*','1','system:dept:delete','#',3,'','2020-01-18 13:40:24','1'),('1218446673412378626','1217417496517201910','租户管理','menu','tenant','system/tenant/index','0','/api/system/tenant/list/searchList','1','system:tenant:list','peoples',5,'','2020-01-18 16:14:59','1'),('1218446825204240386','1218446673412378626','租户入驻','button','',NULL,'1','/api/system/tenant/add/addTenant','1','system:tenant:add','#',1,'','2020-01-18 16:15:35','1'),('1218446983354667010','1218446673412378626','租户信息变更','button','',NULL,'1','/api/system/tenant/update/updateTenant','1','system:tenant:update','#',2,'','2020-01-18 16:16:13','1'),('1218447223449210882','1218446673412378626','租户删除','button','',NULL,'1','/api/system/tenant/delete/deleteById/*','1','system:tenant:delete','#',3,'','2020-01-18 16:17:10','1'),('1218802523935113217','-1','系统工具','dir','tool','','0','','1','','tool',99,'','2020-01-19 15:49:00','1'),('1218803542106603521','1218802523935113217','代码生成','menu','gen/index','tool/gen/index','0','/api/tool/gen/table/list/searchList','1','tool:gen:list','code',1,'','2020-01-19 15:53:03','1'),('1218804534994829314','1218803542106603521','导入配置','button','',NULL,'1','/api/tool/gen/table/add/importFormDb','1','tool:gen:import','#',1,'','2020-01-19 15:57:00','1'),('1218804710195101697','1218803542106603521','修改配置','button','',NULL,'1','/api/tool/gen/table/update/updateGenTable','1','tool:gen:update','#',2,'','2020-01-19 15:57:41','1'),('1218804866328068097','1218803542106603521','删除配置','button','',NULL,'1','/api/tool/gen/table/delete/deleteById/*','1','tool:gen:delete','#',3,'','2020-01-19 15:58:19','1'),('1218805168708026369','1218803542106603521','生成代码','button','',NULL,'1','/api/tool/gen/table/export/genCode','1','tool:gen:code','#',4,'','2020-01-19 15:59:31','1'),('1218824467107659777','1218802523935113217','生成表配置','menu','gen/editTable','tool/gen/editTable','0','/api/tool/gen/table/update/updateGenTable','0','tool:gen:update','list',2,'','2020-01-19 17:16:12','1'),('1223120937717833729','1217417496517201910','角色管理','menu','role','system/role','1','/api/system/role/list/searchList','1','system:role:list','post',6,'','2020-01-31 13:48:50','1'),('1223121120925032449','1223120937717833729','角色新增','button','',NULL,'1','/api/system/role/add/addSysRole','1','system:role:add','#',1,'','2020-01-31 13:49:34','1'),('1223121282657394689','1223120937717833729','角色修改','button','',NULL,'1','/api/system/role/update/updateSysRole','1','system:role:update','#',2,'','2020-01-31 13:50:12','1'),('1223121393676427266','1223120937717833729','角色删除','button','',NULL,'1','/api/system/role/delete/deleteById/*','1','system:role:delete','#',3,'','2020-01-31 13:50:39','1'),('1224174853515497474','1217417496517201910','用户管理','menu','user/index','system/user/index','0','/api/system/user/list/searchList','1','system:user:list','user',7,'','2020-02-03 11:36:43','1'),('1224175040283660290','1224174853515497474','用户添加','button','',NULL,'1','/api/system/user/add/addSysUser','1','system:user:add','#',1,'','2020-02-03 11:37:28','1'),('1224175236547727362','1224174853515497474','用户信息修改','button','',NULL,'1','/api/system/user/update/updateSysUser','1','system:user:update','#',2,'','2020-02-03 11:38:15','1'),('1224175746986135554','1224174853515497474','用户删除','button','',NULL,'1','/api/system/user/delete/deleteById/*','1','system:user:delete','#',3,'','2020-02-03 11:40:16','1'),('1225222189733683202','1224174853515497474','重置密码','button','',NULL,'1','/api/system/user/update/resetUserPwd','1','system:user:resetPwd','#',4,'','2020-02-06 08:58:28','1'),('1225222705758904321','1224174853515497474','分配角色','button','',NULL,'1','/api/system/user/update/changeUserRole','1','system:user:updateRole','#',5,'','2020-02-06 09:00:31','1'),('1225593390775746562','1224174853515497474','分配租户','button','',NULL,'1','/api/system/tenant/update/updateTenant','1','system:user:updateTenant','#',6,'','2020-02-07 09:33:29','1'),('1230000001587905507','1239919510027341825','学生信息','menu','student','sc/student','0','/api/sc/student/list/searchList','1','sc:student:list','peoples',4,'','2020-03-17 22:30:19','1'),('1230000001587905509','1230000001587905507','新学生','button','',NULL,'1','/api/sc/student/add/addScStudent','1','sc:student:add','#',1,'','2020-03-17 22:32:16','1'),('1230000001587905511','1230000001587905507','信息变更','button','',NULL,'1','/api/sc/student/update/updateScTeacher','1','sc:student:update','#',2,'','2020-03-17 22:32:44','1'),('1230000001587905512','1230000001587905507','删除','button','',NULL,'1','/api/sc/student/delete/deleteById/*','1','sc:student:delete','#',3,'','2020-03-17 22:34:05','1'),('1234813915582472193','-1','系统监控','dir','monitor',NULL,'0','','1',NULL,'example',12,'','2020-03-03 20:12:33','1'),('1234814364322668545','1234813915582472193','在线用户','menu','online','monitor/online','0','/api/monitor/online/list/searchList','1','monitor:online:list','eye',1,'','2020-03-03 20:14:20','1'),('1239919510027341825','-1','教务管理','dir','edu',NULL,'0','','1',NULL,'documentation',1,'','2020-03-17 22:20:22','1'),('1239921371895664641','1239919510027341825','课程信息','menu','course','sc/course/index','0','/api/sc/course/list/searchList','1','sc:course:list','dict',1,'','2020-03-17 22:27:46','1'),('1239922013267660801','1309821601025445890','员工管理','menu','staff','system/staff/index','0','/api/sys/staff/list/searchList','1','system:staff:list','user',2,'','2020-03-17 22:30:19','1'),('1239922505691533313','1239922013267660801','新员工入职','button','',NULL,'1','/api/sys/staff/add/addSysStaff','1','system:staff:add','#',1,'','2020-03-17 22:32:16','1'),('1239922623304011777','1239922013267660801','信息变更','button','',NULL,'1','/api/sys/staff/update/updateSysStaff','1','system:staff:update','#',2,'','2020-03-17 22:32:44','1'),('1239922962929389569','1239922013267660801','删除员工','button','',NULL,'1','/api/sys/staff/delete/deleteById/*','1','system:staff:delete','#',3,'','2020-03-17 22:34:05','1'),('1239922962929389570','1310909593492254732','新增','button','',NULL,'0','/api/sys/receipt/add/addSysReceiptAccount','1','sys:receipt:add','#',1,'','2020-10-16 15:09:58','1'),('1239922962929389571','1310909593492254732','修改','button','',NULL,'0','/api/sys/receipt/add/updateSysReceiptAccount','1','sys:receipt:update','#',2,'','2020-10-16 15:09:58','1'),('1239922962929389572','1310909593492254732','删除','button','',NULL,'0','/api/sys/receipt/add/deleteById/*','1','sys:receipt:delete','#',3,'','2020-10-16 15:09:58','1'),('1239923102700376065','1239921371895664641','新设课程','button','',NULL,'1','/api/sc/course/add/addScCourse','1','sc:course:add','#',1,'','2020-03-17 22:34:39','1'),('1239923418330140674','1239921371895664641','信息变更','button','',NULL,'1','/api/sc/course/update/updateScCourse','1','sc:course:update','#',2,'','2020-03-17 22:35:54','1'),('1239923565625708545','1239921371895664641','取消课程','button','',NULL,'1','/api/sc/course/delete/deleteById/*','1','sc:course:delete','#',3,'','2020-03-17 22:36:29','1'),('1240925846567464961','1239919510027341825','班级信息','menu','cla','sc/course/cla/index','0','/api/sc/course/cla/list/searchList','1','sc:cla:list','table',2,'','2020-03-20 16:59:11','1'),('1240926029145518081','1240925846567464961','新设班级','button','',NULL,'1','/api/sc/course/cla/add/addScCourseCla','1','sc:cla:add','#',1,'','2020-03-20 16:59:55','1'),('1242067340028297218','1240925846567464961','变更信息','button','',NULL,'1','/api/sc/course/cla/update/updateScCourseCla','1','sc:cla:update','#',2,'','2020-03-23 20:35:05','1'),('1242432382515142658','1240925846567464961','取消班级','button','',NULL,'1','/api/sc/course/cla/delete/deleteById/*','1','sc:cla:delete','#',3,'','2020-03-24 20:45:37','1'),('1254732233185452033','-1','基础数据','dir','base',NULL,'0','','1',NULL,'bug',11,'','2020-04-27 19:20:50','1'),('1254732643988168705','1254732233185452033','学校信息','menu','school','sc/school','0','/api/sc/school/list/searchList','1','sc:school:list','table',1,'','2020-04-27 19:22:28','1'),('1254732779367718914','1254732643988168705','添加','button','',NULL,'1','/api/sc/school/add/addScSchool','1','sc:school:add','#',1,'','2020-04-27 19:23:01','1'),('1254732899643580418','1254732643988168705','修改','button','',NULL,'1','/api/sc/school/update/updateScSchool','1','sc:school:update','#',2,'','2020-04-27 19:23:29','1'),('1254732993755373570','1254732643988168705','删除','button','',NULL,'1','3','1','sc:school:delete','#',3,'','2020-04-27 19:23:52','1'),('1274252421513682946','1239919510027341825','学员中心','menu','studentInfo','sc/student/studentInfo','0','','1',NULL,'guide',6,'','2020-06-20 16:07:06','1'),('1297838637005742082','-1','办理中心','dir','order',NULL,'0','','1',NULL,'money',2,'','2020-08-24 18:10:18','1'),('1297839502798168065','1297838637005742082','业务办理','menu','handle/index','sc/order/index','0','','1',NULL,'form',1,'','2020-08-24 18:13:44','1'),('1298181761636814850','1297838637005742082','新报','menu','handle/signUp','sc/order/signUp','0','','0','sc:order:handleSignUp','sign-up',1,'','2020-08-25 16:53:45','1'),('1307663783946108930','1239919510027341825','排课','menu','claTimeRule','sc/claTime/rule/index','1','','1',NULL,'build',7,'','2020-09-20 20:51:55','1'),('1307674275158691842','1307663783946108930','新增','button','',NULL,'1','','1','sc:claTimeRule:add','#',1,'','2020-09-20 21:33:37','1'),('1307674344234684418','1307663783946108930','修改','button','',NULL,'1','','1','sc:claTimeRule:update','#',2,'','2020-09-20 21:33:53','1'),('1307674455815753730','1307663783946108930','删除','button','',NULL,'1','','1','sc:claTimeRule:delete','#',3,'','2020-09-20 21:34:20','1'),('1308738414878687233','1309821601025445890','教室管理','menu','room','sc/room/index','0','','1',NULL,'tree',3,'','2020-09-23 20:02:07','1'),('1308738610895290369','1308738414878687233','新增','button','',NULL,'1','/api/sc/room/add/addScRoom','1','sc:room:add','#',1,'','2020-09-23 20:02:54','1'),('1308738714079363073','1308738414878687233','修改','button','',NULL,'1','/api/sc/room/update/updateScRoom','1','sc:room:update','#',2,'','2020-09-23 20:03:19','1'),('1308738827870830593','1308738414878687233','删除','button','',NULL,'1','/api/sc/room/delete/deleteById/*','1','sc:room:delete','#',3,'','2020-09-23 20:03:46','1'),('1309821601025445890','-1','机构管理','dir','schoolManage',NULL,'0','','1',NULL,'tree-table',3,'','2020-09-26 19:46:19','1'),('1309821601025445891','1309821601025445890','校区管理','menu','schoolMange','system/dept/index','0','/api/system/menu/list/searchList','1','system:dept:list','tree',4,'','2020-01-18 13:35:08','1'),('1310540539443093505','1239919510027341825','班级详情','menu','cla/detail/:claId(\\d+)','sc/course/cla/detail','0','','0',NULL,'education',2,'','2020-09-28 19:23:07','1'),('1310909593492254732','1309821601025445890','收款账户','menu','receipt','sys/receipt/index','0','/api/sys/receipt/list/searchList','1','sys:receipt:list','money',5,'','2020-10-16 13:35:08','1'),('1314424070170755073','1239919510027341825','学生详情','menu','student/detail/:studentId(\\d+)','sc/student/studentInfo','0','','0',NULL,'download',6,'','2020-10-09 12:34:53','1'),('1317323682166632449','1223120937717833729','角色列表','button','',NULL,'0','/api/system/role/list/treeSelect','1','system:role:treeSelect','#',4,'','2020-10-17 12:36:55','1'),('1317328225973620737','1309821601025445891','添加校区','button','',NULL,'0','','1','system:dept:add','#',1,'','2020-10-17 12:54:58','1'),('1317328311856189441','1309821601025445891','修改校区','button','',NULL,'0','','1','system:dept:update','#',2,'','2020-10-17 12:55:18','1'),('1317328533206388737','1309821601025445891','删除校区','button','',NULL,'0','','1','system:dept:delete','#',3,'','2020-10-17 12:56:11','1'),('1326870394199605250','1239922013267660801','重置密码','button','',NULL,'0','/api/system/user/update/resetUserPwd','1','system:user:resetPwd','#',4,'','2020-11-12 20:52:08','1'),('1326870520070668289','1307663783946108930','按教师查询排课','button','',NULL,'0','/api/sys/staff/list/searchList','1','system:staff:list','#',4,'','2020-11-12 20:52:38','1'),('1331548642670120961','1297838637005742082','打印业务凭证','menu','handle/print/:orderId','sc/order/orderPrint','0','','0','sc:order:print','tool',3,'','2020-11-25 18:41:50','1'),('1333780484576051201','1239919510027341825','上课记录','menu','claTime','sc/claTime/index','0','','1',NULL,'nested',8,'','2020-12-30 23:19:50','1'),('1333782938852081665','1333780484576051201','变更上课记录','button','',NULL,'0','/api/sc/cla/time/update/updateAttendedClaTime','1','sc:claTime:update','#',1,'','2020-12-30 23:19:52','1'),('1333783283883917313','1333780484576051201','删除已上课信息','button','',NULL,'0','/api/sc/cla/time/delete/deleteAttendedClaTime','1','sc:claTime:delete','#',2,'','2020-12-30 23:19:52','1'),('1333784258443034626','1333780484576051201','上课记录列表','button','',NULL,'0',NULL,'1','sc:claTime:list','#',3,'','2020-12-30 23:19:52','1'),('1341255227243782145','1297839502798168065','批量作废订单','button','',NULL,'0','','1','sc:order:batchInvalid','#',1,'','2020-12-30 23:21:32','1'),('1342056934513082369','-1','报表','dir','report',NULL,'0','','1',NULL,'chart',4,'','2020-12-30 23:21:38','1'),('1342058967622254593','1342056934513082369','经营报表','menu','analysis','sc/report/analysis','0','','1',NULL,'chart',1,'','2020-12-30 23:21:39','1');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_operate_log`
--

DROP TABLE IF EXISTS `sys_operate_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_operate_log` (
  `operate_id` varchar(30) NOT NULL COMMENT '日志主键',
  `operate_module` varchar(50) DEFAULT '' COMMENT '操作模块',
  `business_type` varchar(20) DEFAULT '0' COMMENT '业务类型',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `client_type` varchar(5) DEFAULT '' COMMENT '客户端 pc app',
  `user_id` varchar(30) DEFAULT '' COMMENT '操作人员',
  `user_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `operate_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `operate_ip` varchar(50) DEFAULT '' COMMENT '主机地址',
  `operate_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `req_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `resp_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `error` tinyint DEFAULT '1' COMMENT '是否异常（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `operate_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`operate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_operate_log`
--

LOCK TABLES `sys_operate_log` WRITE;
/*!40000 ALTER TABLE `sys_operate_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_operate_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_receipt_account`
--

DROP TABLE IF EXISTS `sys_receipt_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_receipt_account` (
  `account_id` bigint NOT NULL COMMENT '主键',
  `tenant_id` varchar(30) NOT NULL COMMENT '所属租户',
  `account_name` varchar(50) NOT NULL COMMENT '账户名称',
  `memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(20) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(20) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='收款账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_receipt_account`
--

LOCK TABLES `sys_receipt_account` WRITE;
/*!40000 ALTER TABLE `sys_receipt_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_receipt_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT '-1',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `role_code` varchar(30) DEFAULT '' COMMENT '角色编码',
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `sort` int DEFAULT '1' COMMENT '显示顺序',
  `in_use` char(1) DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `delete_flag` char(1) DEFAULT '0' COMMENT '删除标志（1删除 0在用）',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `sys_role_role_code_uindex` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,-1,'-1','admin','系统管理员',1,'1','0','1192453807573942273','2020-01-31 15:04:04','1192453807573942273','2020-10-31 10:24:53'),(20,1,'-1,1','tenantManager','租户管理',2,'1','0','1192453807573942273','2020-10-17 11:55:12','1192453807573942273','2020-10-31 10:25:07'),(21,-1,'-1','school','学校',3,'1','0','1192453807573942273','2020-10-17 12:29:01','1192453807573942273','2020-10-31 13:39:54'),(22,21,'-1,21','teachingManager','教务管理',3,'1','0','1192453807573942273','2020-10-17 12:48:05','1192453807573942273','2020-12-01 22:47:39'),(23,21,'-1,21','teacher','教师',2,'1','0','1192453807573942273','2020-10-29 13:56:57','1192453807573942273','2020-12-01 22:47:48'),(24,1,'-1,1','manager','管理员',2,'1','0','1192453807573942273','2020-10-31 10:24:24','1192453807573942273','2021-01-15 19:50:45'),(25,21,'-1,21','schoolManager','校长',1,'1','0','1192453807573942273','2020-10-31 13:35:49','1192453807573942273','2021-01-09 17:50:11');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL,
  `menu_id` varchar(30) NOT NULL,
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`role_id`,`menu_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (20,'1217417496517201910',1192453683439271937),(20,'1218446673412378626',1192453683439271937),(20,'1218446825204240386',1192453683439271937),(20,'1218446983354667010',1192453683439271937),(22,'1230000001587905507',1192453683439271937),(22,'1230000001587905509',1192453683439271937),(22,'1230000001587905511',1192453683439271937),(22,'1230000001587905512',1192453683439271937),(22,'1239919510027341825',1192453683439271937),(22,'1239921371895664641',1192453683439271937),(22,'1239922013267660801',1192453683439271937),(22,'1239922505691533313',1192453683439271937),(22,'1239922623304011777',1192453683439271937),(22,'1239922962929389569',1192453683439271937),(22,'1239922962929389570',1192453683439271937),(22,'1239922962929389571',1192453683439271937),(22,'1239922962929389572',1192453683439271937),(22,'1239923102700376065',1192453683439271937),(22,'1239923418330140674',1192453683439271937),(22,'1239923565625708545',1192453683439271937),(22,'1240925846567464961',1192453683439271937),(22,'1240926029145518081',1192453683439271937),(22,'1242067340028297218',1192453683439271937),(22,'1242432382515142658',1192453683439271937),(22,'1254732233185452033',1192453683439271937),(22,'1254732643988168705',1192453683439271937),(22,'1254732779367718914',1192453683439271937),(22,'1254732899643580418',1192453683439271937),(22,'1254732993755373570',1192453683439271937),(22,'1274252421513682946',1192453683439271937),(22,'1297838637005742082',1192453683439271937),(22,'1297839502798168065',1192453683439271937),(22,'1298181761636814850',1192453683439271937),(22,'1307663783946108930',1192453683439271937),(22,'1307674275158691842',1192453683439271937),(22,'1307674344234684418',1192453683439271937),(22,'1307674455815753730',1192453683439271937),(22,'1308738414878687233',1192453683439271937),(22,'1308738610895290369',1192453683439271937),(22,'1308738714079363073',1192453683439271937),(22,'1308738827870830593',1192453683439271937),(22,'1309821601025445890',1192453683439271937),(22,'1309821601025445891',1192453683439271937),(22,'1310540539443093505',1192453683439271937),(22,'1310909593492254732',1192453683439271937),(22,'1314424070170755073',1192453683439271937),(22,'1317328225973620737',1192453683439271937),(22,'1317328311856189441',1192453683439271937),(22,'1317328533206388737',1192453683439271937),(22,'1331548642670120961',1192453683439271937),(22,'1333780484576051201',1192453683439271937),(22,'1333782938852081665',1192453683439271937),(22,'1333783283883917313',1192453683439271937),(22,'1333784258443034626',1192453683439271937),(23,'1230000001587905507',1192453683439271937),(23,'1230000001587905509',1192453683439271937),(23,'1230000001587905511',1192453683439271937),(23,'1230000001587905512',1192453683439271937),(23,'1239919510027341825',1192453683439271937),(23,'1239921371895664641',1192453683439271937),(23,'1239923102700376065',1192453683439271937),(23,'1239923418330140674',1192453683439271937),(23,'1239923565625708545',1192453683439271937),(23,'1240925846567464961',1192453683439271937),(23,'1240926029145518081',1192453683439271937),(23,'1242067340028297218',1192453683439271937),(23,'1242432382515142658',1192453683439271937),(23,'1274252421513682946',1192453683439271937),(23,'1307663783946108930',1192453683439271937),(23,'1307674275158691842',1192453683439271937),(23,'1307674344234684418',1192453683439271937),(23,'1307674455815753730',1192453683439271937),(23,'1310540539443093505',1192453683439271937),(23,'1314424070170755073',1192453683439271937),(23,'1333780484576051201',1192453683439271937),(23,'1333784258443034626',1192453683439271937),(24,'1217417496517201910',1192453683439271937),(24,'1217417496517201911',1192453683439271937),(24,'1217417496517201913',1192453683439271937),(24,'1217417496517201914',1192453683439271937),(24,'1217417496517201915',1192453683439271937),(24,'1217453627459817473',1192453683439271937),(24,'1217456967912574977',1192453683439271937),(24,'1217457410877214722',1192453683439271937),(24,'1217457554142056450',1192453683439271937),(24,'1217816440560693250',1192453683439271937),(24,'1218406446337634305',1192453683439271937),(24,'1218407456770633730',1192453683439271937),(24,'1218407593777573890',1192453683439271937),(24,'1218407770844311553',1192453683439271937),(24,'1218446673412378626',1192453683439271937),(24,'1218446825204240386',1192453683439271937),(24,'1218446983354667010',1192453683439271937),(24,'1218447223449210882',1192453683439271937),(24,'1218802523935113217',1192453683439271937),(24,'1218803542106603521',1192453683439271937),(24,'1218804534994829314',1192453683439271937),(24,'1218804710195101697',1192453683439271937),(24,'1218804866328068097',1192453683439271937),(24,'1218805168708026369',1192453683439271937),(24,'1218824467107659777',1192453683439271937),(24,'1223120937717833729',1192453683439271937),(24,'1223121120925032449',1192453683439271937),(24,'1223121282657394689',1192453683439271937),(24,'1223121393676427266',1192453683439271937),(24,'1224174853515497474',1192453683439271937),(24,'1224175040283660290',1192453683439271937),(24,'1224175236547727362',1192453683439271937),(24,'1224175746986135554',1192453683439271937),(24,'1225222189733683202',1192453683439271937),(24,'1225222705758904321',1192453683439271937),(24,'1225593390775746562',1192453683439271937),(24,'1230000001587905507',1192453683439271937),(24,'1230000001587905509',1192453683439271937),(24,'1230000001587905511',1192453683439271937),(24,'1230000001587905512',1192453683439271937),(24,'1234813915582472193',1192453683439271937),(24,'1234814364322668545',1192453683439271937),(24,'1239919510027341825',1192453683439271937),(24,'1239921371895664641',1192453683439271937),(24,'1239922013267660801',1192453683439271937),(24,'1239922505691533313',1192453683439271937),(24,'1239922623304011777',1192453683439271937),(24,'1239922962929389569',1192453683439271937),(24,'1239922962929389570',1192453683439271937),(24,'1239922962929389571',1192453683439271937),(24,'1239922962929389572',1192453683439271937),(24,'1239923102700376065',1192453683439271937),(24,'1239923418330140674',1192453683439271937),(24,'1239923565625708545',1192453683439271937),(24,'1240925846567464961',1192453683439271937),(24,'1240926029145518081',1192453683439271937),(24,'1242067340028297218',1192453683439271937),(24,'1242432382515142658',1192453683439271937),(24,'1254732233185452033',1192453683439271937),(24,'1254732643988168705',1192453683439271937),(24,'1254732779367718914',1192453683439271937),(24,'1254732899643580418',1192453683439271937),(24,'1254732993755373570',1192453683439271937),(24,'1274252421513682946',1192453683439271937),(24,'1297838637005742082',1192453683439271937),(24,'1297839502798168065',1192453683439271937),(24,'1298181761636814850',1192453683439271937),(24,'1307663783946108930',1192453683439271937),(24,'1307674275158691842',1192453683439271937),(24,'1307674344234684418',1192453683439271937),(24,'1307674455815753730',1192453683439271937),(24,'1308738414878687233',1192453683439271937),(24,'1308738610895290369',1192453683439271937),(24,'1308738714079363073',1192453683439271937),(24,'1308738827870830593',1192453683439271937),(24,'1309821601025445890',1192453683439271937),(24,'1309821601025445891',1192453683439271937),(24,'1310540539443093505',1192453683439271937),(24,'1310909593492254732',1192453683439271937),(24,'1314424070170755073',1192453683439271937),(24,'1317323682166632449',1192453683439271937),(24,'1317328225973620737',1192453683439271937),(24,'1317328311856189441',1192453683439271937),(24,'1317328533206388737',1192453683439271937),(24,'1341255227243782145',1192453683439271937),(24,'1347842243867725826',1192453683439271937),(24,'1347845199711195138',1192453683439271937),(24,'1347845199711195148',1192453683439271937),(24,'1347845680504262657',1192453683439271937),(24,'1347845680504262667',1192453683439271937),(24,'1347846104523231234',1192453683439271937),(24,'1347846104523231244',1192453683439271937),(24,'1348966907251785730',1192453683439271937),(24,'1348967820913803266',1192453683439271937),(24,'1348967820913803277',1192453683439271937),(24,'1348971249161859074',1192453683439271937),(24,'1348971249161859075',1192453683439271937),(24,'1348971249161859076',1192453683439271937),(24,'1348971249161859077',1192453683439271937),(24,'1348971249161859078',1192453683439271937),(24,'1348971249161859079',1192453683439271937),(24,'1500202203230000200',1192453683439271937),(24,'1500202203230000300',1192453683439271937),(24,'1500202203230000400',1192453683439271937),(24,'1500202203230000500',1192453683439271937),(25,'1230000001587905507',1192453683439271937),(25,'1230000001587905509',1192453683439271937),(25,'1230000001587905511',1192453683439271937),(25,'1230000001587905512',1192453683439271937),(25,'1239919510027341825',1192453683439271937),(25,'1239921371895664641',1192453683439271937),(25,'1239922013267660801',1192453683439271937),(25,'1239922505691533313',1192453683439271937),(25,'1239922623304011777',1192453683439271937),(25,'1239922962929389569',1192453683439271937),(25,'1239922962929389570',1192453683439271937),(25,'1239922962929389571',1192453683439271937),(25,'1239922962929389572',1192453683439271937),(25,'1239923102700376065',1192453683439271937),(25,'1239923418330140674',1192453683439271937),(25,'1239923565625708545',1192453683439271937),(25,'1240925846567464961',1192453683439271937),(25,'1240926029145518081',1192453683439271937),(25,'1242067340028297218',1192453683439271937),(25,'1242432382515142658',1192453683439271937),(25,'1254732233185452033',1192453683439271937),(25,'1254732643988168705',1192453683439271937),(25,'1254732779367718914',1192453683439271937),(25,'1254732899643580418',1192453683439271937),(25,'1254732993755373570',1192453683439271937),(25,'1274252421513682946',1192453683439271937),(25,'1297838637005742082',1192453683439271937),(25,'1297839502798168065',1192453683439271937),(25,'1298181761636814850',1192453683439271937),(25,'1307663783946108930',1192453683439271937),(25,'1307674275158691842',1192453683439271937),(25,'1307674344234684418',1192453683439271937),(25,'1307674455815753730',1192453683439271937),(25,'1308738414878687233',1192453683439271937),(25,'1308738610895290369',1192453683439271937),(25,'1308738714079363073',1192453683439271937),(25,'1308738827870830593',1192453683439271937),(25,'1309821601025445890',1192453683439271937),(25,'1309821601025445891',1192453683439271937),(25,'1310540539443093505',1192453683439271937),(25,'1310909593492254732',1192453683439271937),(25,'1314424070170755073',1192453683439271937),(25,'1317328225973620737',1192453683439271937),(25,'1317328311856189441',1192453683439271937),(25,'1317328533206388737',1192453683439271937),(25,'1331548642670120961',1192453683439271937),(25,'1333780484576051201',1192453683439271937),(25,'1333782938852081665',1192453683439271937),(25,'1333783283883917313',1192453683439271937),(25,'1333784258443034626',1192453683439271937),(25,'1341255227243782145',1192453683439271937),(25,'1342056934513082369',1192453683439271937),(25,'1342058967622254593',1192453683439271937),(25,'1347842243867725826',1192453683439271937),(25,'1347845199711195138',1192453683439271937),(25,'1347845199711195138',1347845680504262657),(25,'1347845199711195138',1347846104523231234),(25,'1347845199711195148',1192453683439271937),(25,'1347845680504262667',1192453683439271937),(25,'1347846104523231244',1192453683439271937),(25,'1500202203230000200',1192453683439271937),(25,'1500202203230000300',1192453683439271937),(25,'1500202203230000400',1192453683439271937),(25,'1500202203230000500',1192453683439271937);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_staff`
--

DROP TABLE IF EXISTS `sys_staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_staff` (
  `staff_id` bigint NOT NULL COMMENT '员工id',
  `tenant_id` varchar(30) NOT NULL COMMENT '所属租户',
  `user_id` varchar(30) DEFAULT NULL COMMENT '用户id',
  `super_staff` tinyint NOT NULL DEFAULT '0' COMMENT '是否为机构管理者',
  `staff_name` varchar(50) NOT NULL COMMENT '员工姓名',
  `phone` varchar(30) NOT NULL COMMENT '联系电话',
  `email_address` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `sex` char(1) NOT NULL DEFAULT 'M' COMMENT '性别 M男 F女',
  `entry_date` date DEFAULT NULL COMMENT '入职日期',
  `personnel_status` varchar(1) DEFAULT '1' COMMENT '人事状态 1正式员工 2实习员工 3试用期 4兼职员工 5停薪留职 6主动离职 7解聘',
  `teacher` tinyint(1) DEFAULT '0' COMMENT '是否为任课教师 1是 0否',
  `avatar_img` varchar(20) DEFAULT NULL COMMENT '头像',
  `dept_id` bigint DEFAULT NULL COMMENT '员工部门',
  `delete_flag` char(1) DEFAULT '0' COMMENT '删除标志（1删除 0在用）',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`staff_id`),
  UNIQUE KEY `sys_staff_user_id_uindex` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='员工信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_staff`
--

LOCK TABLES `sys_staff` WRITE;
/*!40000 ALTER TABLE `sys_staff` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_tag`
--

DROP TABLE IF EXISTS `sys_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_tag` (
  `tag_id` bigint NOT NULL COMMENT '主键',
  `tenant_id` varchar(30) NOT NULL COMMENT '所属租户',
  `tag_name` varchar(50) NOT NULL COMMENT '标签名称',
  `tag_type` varchar(10) NOT NULL COMMENT '标签类型',
  `create_user` varchar(20) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(20) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='标签';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_tag`
--

LOCK TABLES `sys_tag` WRITE;
/*!40000 ALTER TABLE `sys_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_tenant`
--

DROP TABLE IF EXISTS `sys_tenant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_tenant` (
  `tenant_id` varchar(30) NOT NULL,
  `tenant_name` varchar(30) NOT NULL COMMENT '租户名称',
  `super_tenant` tinyint(1) DEFAULT '0' COMMENT '是否为超级管理租户',
  `contact_name` varchar(20) NOT NULL COMMENT '联系人',
  `contact_phone` varchar(50) NOT NULL COMMENT '联系人电话',
  `contact_address` varchar(100) DEFAULT NULL COMMENT '联系地址',
  `begin_time` datetime NOT NULL COMMENT '生效开始时间',
  `end_time` datetime NOT NULL COMMENT '生效结束时间',
  `memo` varchar(200) DEFAULT NULL,
  `in_use` char(1) DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `delete_flag` char(1) DEFAULT '0' COMMENT '删除标志（1删除 0在用）',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='租户信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_tenant`
--

LOCK TABLES `sys_tenant` WRITE;
/*!40000 ALTER TABLE `sys_tenant` DISABLE KEYS */;
INSERT INTO `sys_tenant` VALUES ('1192453683439271937','综合管理租户',1,'张三','18888888888',NULL,'2020-10-29 13:54:20','2050-12-31 23:59:59',NULL,'1','0',NULL,'2020-10-29 13:54:30','','2020-10-29 13:54:30');
/*!40000 ALTER TABLE `sys_tenant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `user_id` varchar(30) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email_address` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `enable` char(1) DEFAULT '1',
  `locked` char(1) DEFAULT '0',
  `account_expired` char(1) DEFAULT '0',
  `pwd_expired` char(1) DEFAULT '0',
  `wechat_user_id` bigint DEFAULT NULL COMMENT '微信用户openId',
  `dept_id` bigint DEFAULT NULL COMMENT '部门',
  `avatar_img` varchar(20) DEFAULT NULL COMMENT '头像',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `sys_user_username_uindex` (`username`),
  UNIQUE KEY `sys_user_open_id_uindex` (`wechat_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES ('1192453807573942273','admin','$2a$10$y8KLbTJPd0cVGBRzBmv/oO0Df85cg2wF.cRHIhKiPd8s9S5O3KA9a','系统管理员','18231231233','13770922@qq.com','1','0','0','0',NULL,10000,'','2019-11-07 22:48:37');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_dept`
--

DROP TABLE IF EXISTS `sys_user_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_dept` (
  `user_id` varchar(30) NOT NULL COMMENT '用户id',
  `dept_id` bigint NOT NULL COMMENT '校区id',
  `tenant_id` varchar(30) NOT NULL COMMENT '所属租户',
  PRIMARY KEY (`user_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户拥有的校区权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_dept`
--

LOCK TABLES `sys_user_dept` WRITE;
/*!40000 ALTER TABLE `sys_user_dept` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_login_log`
--

DROP TABLE IF EXISTS `sys_user_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_login_log` (
  `login_log_id` bigint NOT NULL,
  `user_id` varchar(30) NOT NULL,
  `username` varchar(30) DEFAULT NULL COMMENT '用户名',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `login_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `login_ip` varchar(30) DEFAULT NULL COMMENT '登录IP',
  `browser_info` varchar(200) DEFAULT NULL COMMENT '登录浏览器',
  PRIMARY KEY (`login_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='登录日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_login_log`
--

LOCK TABLES `sys_user_login_log` WRITE;
/*!40000 ALTER TABLE `sys_user_login_log` DISABLE KEYS */;
INSERT INTO `sys_user_login_log` VALUES (1537095902110461954,'1192453807573942273','admin','系统管理员','2022-06-15 23:33:15','127.0.0.1','Mac OS X,Chrome 10/102.0.0.0');
/*!40000 ALTER TABLE `sys_user_login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` varchar(30) NOT NULL,
  `role_id` bigint NOT NULL,
  `tenant_id` varchar(30) NOT NULL COMMENT '所属租户',
  PRIMARY KEY (`role_id`,`user_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES ('1192453807573942273',1,'1192453683439271937'),('1192453807573942273',20,'1192453683439271937'),('1192453807573942273',24,'1192453683439271937');
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_tenant`
--

DROP TABLE IF EXISTS `sys_user_tenant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_tenant` (
  `user_id` varchar(30) NOT NULL,
  `tenant_id` varchar(30) NOT NULL,
  PRIMARY KEY (`user_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户所属租户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_tenant`
--

LOCK TABLES `sys_user_tenant` WRITE;
/*!40000 ALTER TABLE `sys_user_tenant` DISABLE KEYS */;
INSERT INTO `sys_user_tenant` VALUES ('1192453807573942273','1192453683439271937');
/*!40000 ALTER TABLE `sys_user_tenant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tool_gen_table`
--

DROP TABLE IF EXISTS `tool_gen_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tool_gen_table` (
  `table_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
  `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) DEFAULT 'curd' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径 如 com.study.business，最终路径 包路径+模块名+业务名',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名 如 sys',
  `child_module_name` varchar(30) DEFAULT NULL COMMENT '子模块 如admin',
  `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名 如 role',
  `tree_code` varchar(30) DEFAULT NULL COMMENT '树键',
  `tree_parent_code` varchar(30) DEFAULT NULL COMMENT '树父键',
  `tree_name` varchar(30) DEFAULT NULL COMMENT '树名称字段',
  `code_author` varchar(50) DEFAULT NULL COMMENT '作者',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项 json格式',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='代码生成表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_gen_table`
--

LOCK TABLES `tool_gen_table` WRITE;
/*!40000 ALTER TABLE `tool_gen_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `tool_gen_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tool_gen_table_column`
--

DROP TABLE IF EXISTS `tool_gen_table_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tool_gen_table_column` (
  `column_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `sort` int DEFAULT NULL COMMENT '排序',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_user` varchar(30) DEFAULT '' COMMENT '更新者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='代码生成表字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_gen_table_column`
--

LOCK TABLES `tool_gen_table_column` WRITE;
/*!40000 ALTER TABLE `tool_gen_table_column` DISABLE KEYS */;
/*!40000 ALTER TABLE `tool_gen_table_column` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tool_import`
--

DROP TABLE IF EXISTS `tool_import`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tool_import` (
  `import_id` bigint NOT NULL AUTO_INCREMENT,
  `import_type` varchar(20) NOT NULL COMMENT '导入 类型',
  `file_size` bigint NOT NULL COMMENT '文件大小',
  `import_result` varchar(20) DEFAULT NULL COMMENT '导入结果',
  `result_memo` varchar(100) DEFAULT NULL COMMENT '导入结果说明',
  `create_user` varchar(30) NOT NULL COMMENT '导入人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '导入时间',
  PRIMARY KEY (`import_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='导入';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_import`
--

LOCK TABLES `tool_import` WRITE;
/*!40000 ALTER TABLE `tool_import` DISABLE KEYS */;
/*!40000 ALTER TABLE `tool_import` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_user_info`
--

DROP TABLE IF EXISTS `wechat_user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wechat_user_info` (
  `wechat_user_id` bigint NOT NULL,
  `open_id` varchar(50) NOT NULL COMMENT '用户openId',
  `app_id` varchar(50) NOT NULL COMMENT '所属app',
  `subscribe` char(1) DEFAULT '0' COMMENT '是否关注 0 未关注，1 关注',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '用户昵称',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别 0：未知、1：男、2：女',
  `city` varchar(20) DEFAULT NULL,
  `country` varchar(20) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL,
  `language` varchar(20) DEFAULT NULL,
  `avatar_url` varchar(200) DEFAULT NULL,
  `subscribe_time` varchar(15) DEFAULT NULL,
  `union_id` varchar(50) DEFAULT NULL COMMENT '只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。',
  `remark` varchar(30) DEFAULT NULL,
  `groupid` varchar(10) DEFAULT NULL,
  `tagid_list` varchar(200) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '初始化时间',
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`wechat_user_id`),
  KEY `wx_user_info_openid_index` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='微信用户信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_user_info`
--

LOCK TABLES `wechat_user_info` WRITE;
/*!40000 ALTER TABLE `wechat_user_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_user_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-15 23:39:59
