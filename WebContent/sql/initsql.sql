/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.5.16 : Database - webbase
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`webbase` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `webbase`;

/*Table structure for table `t_apply_money` */

DROP TABLE IF EXISTS `t_apply_money`;

CREATE TABLE `t_apply_money` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` double(12,2) DEFAULT NULL,
  `time` int(11) DEFAULT NULL,
  `reason` varchar(2000) DEFAULT NULL,
  `make_time` int(11) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `source` varchar(3) DEFAULT NULL COMMENT '01系统02微信',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_apply_money` */

/*Table structure for table `t_attachment` */

DROP TABLE IF EXISTS `t_attachment`;

CREATE TABLE `t_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名称',
  `file_path` varchar(1000) DEFAULT NULL COMMENT '附件所在路径',
  `buss_type` varchar(50) DEFAULT NULL COMMENT '类型attachment_type',
  `buss_id` bigint(20) DEFAULT NULL COMMENT '关联表主键',
  `file_suffix` varchar(20) DEFAULT NULL COMMENT '文件后缀',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作人',
  `create_time` int(11) DEFAULT NULL COMMENT '操作时间',
  `remark` varchar(100) DEFAULT NULL,
  `isdelete` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_attachment` */

/*Table structure for table `t_customer` */

DROP TABLE IF EXISTS `t_customer`;

CREATE TABLE `t_customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `sex` int(1) DEFAULT NULL COMMENT '1男 0女',
  `birthday` int(11) DEFAULT NULL,
  `id_type` varchar(10) DEFAULT NULL,
  `id_card` varchar(40) DEFAULT NULL,
  `e_mail` varchar(300) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `address` varchar(2000) DEFAULT NULL,
  `operator_id` bigint(20) DEFAULT NULL COMMENT 't_user_id',
  `status` varchar(10) DEFAULT '01' COMMENT '状态01正常 02删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_customer` */

insert  into `t_customer`(`id`,`name`,`sex`,`birthday`,`id_type`,`id_card`,`e_mail`,`mobile`,`remark`,`address`,`operator_id`,`status`) values (1,'王庭',1,655488000,'01','110101199001018282','sdf@sf.com','186202020200','这里是备注','天际年徐州',1,'01'),(2,'双大人',0,655487999,'01','322222198910103928','ding@aiwt.com','186222222222','zhelishibeizhu2','徐州天津',2,'01');

/*Table structure for table `t_log` */

DROP TABLE IF EXISTS `t_log`;

CREATE TABLE `t_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `t_user_id` bigint(20) DEFAULT NULL COMMENT '操作人',
  `operate_type` varchar(20) DEFAULT NULL COMMENT 'add,edit,delete',
  `create_time` int(11) DEFAULT NULL COMMENT '时间',
  `log_type` varchar(20) DEFAULT NULL COMMENT '业务名称 - 名称t_properties.code',
  `comment` varchar(255) DEFAULT NULL,
  `isdelete` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `t_log` */

insert  into `t_log`(`id`,`t_user_id`,`operate_type`,`create_time`,`log_type`,`comment`,`isdelete`) values (1,1,'add',1416758400,'staff','1111111111111111111111111111111111111111111111',1),(2,2,'delete',1416884392,'staff','对客户1120发卡操作，卡号：6666666666',0),(3,11,'add',1416885559,'customer','对客户1121发卡操作，卡号：3333333',0),(4,1,'edit',1416885645,'customer','对客户孟亚娇【1122】发卡操作，卡号：454545',0),(5,2,'add',1416885653,'staff','对客户孟亚娇【1122】换卡操作，卡号：454545->777777',0),(6,11,'add',1416885658,'staff','对客户孟亚娇【1122】销卡操作，卡号：777777',0),(7,1,'add',1416885754,'staff','对客户孟亚娇【ID:1122】【发卡】操作，卡号：fddfdf',0),(8,1,NULL,1416987945,'user','新建用户ceshi2(cs002)。',0),(9,1,NULL,1416988025,'user','编辑用户wangxueg23(1111123)。',0),(10,1,NULL,1416988138,'user','编辑用户ceshi3(cs003)。',0),(11,2,NULL,1417492131,'user','编辑用户dinglaoda(000003)。',1),(12,2,NULL,1417492166,'user','新建用户tttttt(tttttt)。',0),(13,2,NULL,1417501703,'user','编辑用户tttttt(tttttt)。',0);

/*Table structure for table `t_log_detail` */

DROP TABLE IF EXISTS `t_log_detail`;

CREATE TABLE `t_log_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '该表为日志表的详细字段说明',
  `t_log_id` bigint(20) NOT NULL COMMENT '日志表的log',
  `table_name` varchar(255) NOT NULL COMMENT '哪张表【获取对象名？】',
  `record_id` bigint(20) DEFAULT NULL COMMENT '哪条记录',
  `field_name` varchar(255) DEFAULT NULL COMMENT '哪个字段',
  `field_old_value` varchar(1024) DEFAULT NULL COMMENT '原值',
  `field_new_value` varchar(1024) DEFAULT NULL COMMENT '新值',
  `comment` longtext COMMENT '该字段更改的说明',
  PRIMARY KEY (`id`),
  KEY `ht_log_id` (`t_log_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `t_log_detail` */

insert  into `t_log_detail`(`id`,`t_log_id`,`table_name`,`record_id`,`field_name`,`field_old_value`,`field_new_value`,`comment`) values (1,1,'HtCustomer',12,'cardNo','0000000000','1111111111',NULL),(2,9,'TUser',13,'name','wangxueg2','wangxueg23',NULL),(3,9,'TUser',13,'loginName','111112','1111123',NULL),(4,10,'TUser',23,'name','ceshi2','ceshi3',NULL),(5,10,'TUser',23,'loginName','cs002','cs003',NULL),(6,11,'TUser',12,'name','dinglaoda','dinglaoda',NULL),(7,11,'TUser',12,'loginName','000003','000003',NULL),(8,13,'TUser',26,'name','tttttt','tttttt',NULL),(9,13,'TUser',26,'loginName','tttttt','tttttt',NULL);

/*Table structure for table `t_organization` */

DROP TABLE IF EXISTS `t_organization`;

CREATE TABLE `t_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL COMMENT 'fk_self',
  `name` varchar(64) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL COMMENT '机构代码',
  `incharge_user_id` bigint(20) DEFAULT NULL COMMENT '负责人id',
  `tel` varchar(50) DEFAULT NULL COMMENT '机构电话',
  `is_leaf` bit(1) DEFAULT b'1' COMMENT '是否是父节点',
  `isdelete` int(11) DEFAULT '0',
  `make_time` int(11) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `modify_time` int(11) DEFAULT NULL,
  `modify_operator` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=230 DEFAULT CHARSET=utf8;

/*Data for the table `t_organization` */

insert  into `t_organization`(`id`,`pid`,`name`,`code`,`incharge_user_id`,`tel`,`is_leaf`,`isdelete`,`make_time`,`operator`,`modify_time`,`modify_operator`) values (1,0,'总部','DB01',2,'1112323232','\0',0,NULL,NULL,NULL,NULL),(138,1,'高层','DB0101',1,'1','\0',0,NULL,NULL,NULL,NULL),(139,1,'高层2','DB0102',6,'2werwrwrw','\0',0,NULL,NULL,NULL,NULL),(140,1,'业务部门4','DB0104',1,'8384325','\0',0,NULL,NULL,NULL,NULL),(141,140,'业务子','DB010301',11,'','',1,NULL,NULL,NULL,NULL),(142,140,'sds','DB00000',2,'','',1,NULL,NULL,NULL,NULL),(143,138,'部门1','DB010101',3,'332','',0,NULL,NULL,NULL,NULL),(145,139,'部门3','DB010201',5,'','',0,NULL,NULL,NULL,NULL),(146,138,'部门2','Bdddd',4,'','',0,NULL,NULL,NULL,NULL),(219,1,'财务部','8604',5,'','\0',0,NULL,NULL,NULL,NULL),(220,138,'test','谔谔无法',NULL,'','',0,NULL,NULL,NULL,NULL),(221,139,'爽肤水','sfsf',NULL,'','',0,NULL,NULL,NULL,NULL),(222,139,'热','而特',NULL,'','',0,NULL,NULL,NULL,NULL),(223,140,'淡淡的','复古风格',NULL,'','',0,NULL,NULL,NULL,NULL),(224,140,'任溶溶','而特人',NULL,'','',0,NULL,NULL,NULL,NULL),(225,219,'豆腐干大概','而尔特',NULL,'','',1,NULL,NULL,NULL,NULL),(226,219,'3453','如果特攻队',NULL,'','',1,NULL,NULL,NULL,NULL),(227,219,'范甘迪','范甘迪人',NULL,'','',1,NULL,NULL,NULL,NULL),(228,219,'大范甘迪','发给',NULL,'','',0,NULL,NULL,NULL,NULL),(229,228,'大范甘迪','发给44',NULL,'','',1,NULL,NULL,NULL,NULL);

/*Table structure for table `t_permission` */

DROP TABLE IF EXISTS `t_permission`;

CREATE TABLE `t_permission` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `pid` int(10) DEFAULT NULL COMMENT '自身关联父id',
  `name` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `value` varchar(300) DEFAULT NULL COMMENT '暂未使用',
  `position` int(10) DEFAULT NULL COMMENT '菜单顺序',
  `isdelete` int(2) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `t_permission` */

insert  into `t_permission`(`id`,`pid`,`name`,`permission`,`value`,`position`,`isdelete`) values (1,0,'功能权限菜单','','',1,0),(2,1,'管理员','admin:aaa','',2,0),(3,1,'同级数据权限','user:add3','/user/add3/**',3,0),(4,1,'下级数据权限','charge:update','/user/add4/**',4,0),(5,22,'权限5','charge:delete','/user/add5/**',5,0),(6,22,'权限6','user:add6','/user/add6/**',6,0),(7,22,'权限7','user:add7','/user/add7/**',7,0),(8,11,'权限8','user:add8','/user/add8/**',8,0),(9,22,'权限9','user:add9','/user/add9/**',9,0),(10,22,'权限10','user:add10','/user/add10/**',10,0),(11,0,'父权限3',NULL,'',11,0),(12,11,'权限12','user:add12','/user/add12/**',12,0),(13,11,'权限13','user:add13','/user/add13/**',13,0),(20,11,'权限14','user:add14','/user/add14/**',14,0),(21,11,'权限15','user:add15','/user/add15/**',3,0),(22,0,'功能权限',NULL,NULL,NULL,0),(23,22,'管理员','user:add12',NULL,NULL,0);

/*Table structure for table `t_properties` */

DROP TABLE IF EXISTS `t_properties`;

CREATE TABLE `t_properties` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codetype` varchar(255) DEFAULT NULL COMMENT '类型',
  `code` varchar(255) DEFAULT NULL,
  `codename` varchar(255) DEFAULT NULL COMMENT '名称',
  `make_time` int(11) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `modify_time` int(11) DEFAULT NULL,
  `modify_operator` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `t_properties` */

insert  into `t_properties`(`id`,`codetype`,`code`,`codename`,`make_time`,`operator`,`modify_time`,`modify_operator`) values (1,'log_type','staff','员工test',NULL,NULL,NULL,NULL),(2,'log_type','customer','客户test',NULL,NULL,NULL,NULL),(3,'log_type','user','用户管理',NULL,NULL,NULL,NULL),(4,'attachment_type','staff','员工附件',NULL,NULL,NULL,NULL),(5,'attachment_type','contract','合同附件',NULL,NULL,NULL,NULL),(6,'id_type','01','身份证',NULL,NULL,NULL,NULL),(7,'id_type','02','军官证',NULL,NULL,NULL,NULL),(8,'customer_status','01','正常',NULL,NULL,NULL,NULL),(9,'customer_status','02','封户',NULL,NULL,NULL,NULL),(10,'workflowtask_status','00','未提交',NULL,NULL,NULL,NULL),(11,'workflowtask_status','01','审批中',NULL,NULL,NULL,NULL),(12,'workflowtask_status','02','退回',NULL,NULL,NULL,NULL),(13,'workflowtask_status','03','已通过',NULL,NULL,NULL,NULL),(14,'workflowtask_status','04','作废',NULL,NULL,NULL,NULL),(15,'vacation_type','01','事假',NULL,NULL,NULL,NULL),(16,'vacation_type','02','病假',NULL,NULL,NULL,NULL),(17,'vacation_type','03','产假',NULL,NULL,NULL,NULL),(18,'vacation_type','04','年假',NULL,NULL,NULL,NULL),(19,'vacation_type','05','婚嫁',NULL,NULL,NULL,NULL),(20,'vacation_type','06','工伤',NULL,NULL,NULL,NULL),(21,'vacation_type','07','产检假',NULL,NULL,NULL,NULL),(22,'vacation_type','08','其他',NULL,NULL,NULL,NULL),(23,'source','01','系统',NULL,NULL,NULL,NULL),(24,'source','02','微信',NULL,NULL,NULL,NULL);

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL COMMENT '角色名称',
  `role_value` varchar(1000) DEFAULT NULL,
  `isdelete` varchar(2) DEFAULT '0',
  `make_time` int(11) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `modify_time` int(11) DEFAULT NULL,
  `modify_operator` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `t_role` */

insert  into `t_role`(`id`,`name`,`role_value`,`isdelete`,`make_time`,`operator`,`modify_time`,`modify_operator`) values (10,'管理员','admin','0',1416466361,'admin',NULL,NULL),(11,'主管','test','0',1416466399,'admin',NULL,NULL),(12,'新建角色11','','0',1422109750,'admin',1422110545,'admin'),(13,'新建角色22','','0',1422109811,'admin',1422110550,'admin'),(15,'新建角色33','','0',1422109890,'admin',1422110578,'admin'),(16,'新建角色4','','0',1422110002,'admin',NULL,NULL),(18,'新建角色5','','0',1422110585,'admin',NULL,NULL);

/*Table structure for table `t_role_permission` */

DROP TABLE IF EXISTS `t_role_permission`;

CREATE TABLE `t_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `t_role_id` bigint(20) DEFAULT NULL,
  `t_permission_id` bigint(20) DEFAULT NULL,
  `make_time` int(11) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `modify_time` int(11) DEFAULT NULL,
  `modify_operator` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8;

/*Data for the table `t_role_permission` */

insert  into `t_role_permission`(`id`,`t_role_id`,`t_permission_id`,`make_time`,`operator`,`modify_time`,`modify_operator`) values (80,10,3,1416466367,'admin',NULL,NULL),(81,10,4,1416466367,'admin',NULL,NULL),(82,10,5,1416466367,'admin',NULL,NULL),(83,10,6,1416466367,'admin',NULL,NULL),(84,10,2,1416466367,'admin',NULL,NULL),(85,10,7,1416466367,'admin',NULL,NULL),(89,10,11,1416466367,'admin',NULL,NULL),(90,10,12,1416466367,'admin',NULL,NULL),(91,10,13,1416466367,'admin',NULL,NULL),(92,10,20,1416466367,'admin',NULL,NULL),(110,11,1,1416710202,'user',NULL,NULL),(113,11,7,1416710202,'user',NULL,NULL),(114,11,9,1416710202,'user',NULL,NULL),(115,11,10,1416710202,'user',NULL,NULL),(116,11,7,1422108004,'admin',NULL,NULL),(117,11,9,1422108004,'admin',NULL,NULL),(118,11,10,1422108004,'admin',NULL,NULL),(119,11,1,1422108004,'admin',NULL,NULL),(120,11,4,1422108004,'admin',NULL,NULL),(121,11,11,1422108004,'admin',NULL,NULL),(122,11,13,1422108004,'admin',NULL,NULL),(123,11,20,1422108004,'admin',NULL,NULL),(124,10,22,1422108015,'admin',NULL,NULL),(125,10,23,1422108015,'admin',NULL,NULL),(126,10,5,1422108015,'admin',NULL,NULL),(127,10,6,1422108015,'admin',NULL,NULL),(128,10,7,1422108015,'admin',NULL,NULL),(129,10,9,1422108015,'admin',NULL,NULL),(130,10,10,1422108015,'admin',NULL,NULL),(131,10,1,1422108015,'admin',NULL,NULL),(132,10,2,1422108015,'admin',NULL,NULL),(133,10,3,1422108015,'admin',NULL,NULL),(134,10,4,1422108015,'admin',NULL,NULL),(135,10,22,1422108566,'admin',NULL,NULL),(136,10,23,1422108566,'admin',NULL,NULL),(137,10,5,1422108566,'admin',NULL,NULL),(138,10,6,1422108566,'admin',NULL,NULL),(139,10,7,1422108566,'admin',NULL,NULL),(140,10,9,1422108566,'admin',NULL,NULL),(141,10,10,1422108566,'admin',NULL,NULL),(142,10,1,1422108566,'admin',NULL,NULL),(143,10,2,1422108566,'admin',NULL,NULL),(144,10,3,1422108566,'admin',NULL,NULL),(145,10,4,1422108566,'admin',NULL,NULL),(146,10,11,1422108566,'admin',NULL,NULL),(147,10,21,1422108566,'admin',NULL,NULL),(148,10,8,1422108566,'admin',NULL,NULL),(149,10,12,1422108566,'admin',NULL,NULL),(150,10,13,1422108566,'admin',NULL,NULL),(151,10,20,1422108566,'admin',NULL,NULL),(152,12,22,1422109829,'admin',NULL,NULL),(153,12,23,1422109829,'admin',NULL,NULL),(154,12,6,1422109829,'admin',NULL,NULL),(155,18,22,1422110591,'admin',NULL,NULL),(156,18,6,1422110591,'admin',NULL,NULL),(157,18,1,1422110591,'admin',NULL,NULL),(158,18,3,1422110591,'admin',NULL,NULL),(159,18,11,1422110591,'admin',NULL,NULL),(160,18,8,1422110591,'admin',NULL,NULL);

/*Table structure for table `t_test` */

DROP TABLE IF EXISTS `t_test`;

CREATE TABLE `t_test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name1` varchar(222) DEFAULT NULL,
  `name2` varchar(222) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_test` */

insert  into `t_test`(`id`,`name1`,`name2`) values (1,'name1',NULL),(2,'name1',NULL);

/*Table structure for table `t_travel` */

DROP TABLE IF EXISTS `t_travel`;

CREATE TABLE `t_travel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `start_date` int(11) DEFAULT NULL,
  `end_date` int(11) DEFAULT NULL,
  `app_hours` double(5,1) DEFAULT NULL,
  `reason` varchar(2000) DEFAULT NULL,
  `make_time` int(11) DEFAULT NULL,
  `operator_id` bigint(20) DEFAULT NULL,
  `source` varchar(3) DEFAULT NULL COMMENT '01系统02微信',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `t_travel` */

insert  into `t_travel`(`id`,`start_date`,`end_date`,`app_hours`,`reason`,`make_time`,`operator_id`,`source`) values (1,1424745180,1424917980,43.0,'gdfdgdddf',1423189995,3,'01'),(2,1423190100,1423276500,4.0,'dddddd',1423190166,4,'01'),(3,1423190100,1423276500,4.0,'dddddd',1423190170,4,'01'),(4,1423190220,1423276620,7.0,'sdfsf',1423190231,4,'01'),(5,1423190220,1423276620,5.0,'fdfddfdd',1423190281,4,'01'),(6,1423190460,1423237260,3.0,'333',1423190509,4,'01'),(7,1427882580,1428487380,3.0,'',1427882625,6,'01'),(8,1428573780,1430290680,2.0,'3',1427882666,6,'01'),(9,1427882640,1428341220,3.0,'大幅度',1427882696,4,'01'),(10,1428934440,1429625640,3.0,'sf',1428934463,2,'01');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(255) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `t_organization_id` bigint(20) DEFAULT NULL COMMENT '所属机构',
  `t_organization_code` varchar(50) DEFAULT NULL COMMENT '所属机构code',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) DEFAULT NULL,
  `isdelete` int(1) DEFAULT '0',
  `salt` varchar(64) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `make_time` int(11) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `modify_time` int(11) DEFAULT NULL,
  `modify_operator` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `loginname` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`login_name`,`name`,`t_organization_id`,`t_organization_code`,`mobile`,`email`,`isdelete`,`salt`,`password`,`make_time`,`operator`,`modify_time`,`modify_operator`) values (1,'user','user',1,'DB01','1213123','21321@23.23',0,'e36326745a6c4820','18e24dd147e6fbd6a7ac59ce1c0d5762b4c2d2e2',NULL,NULL,NULL,NULL),(2,'admin','admin',1,'DB01','132123','sdfs@dsf.cd',0,'b42d934376c2f15b','7aba15c7b67b7e07c140080f16e22f5f1f8debdd',NULL,NULL,NULL,NULL),(3,'000003','000003N',143,'DB010101','12123123','yuju@yjht.h',0,'1f67778dcb81541a','2146506410518dca74de73580899e4a5a344ae3f',NULL,NULL,NULL,NULL),(4,'000004','000004N',146,NULL,'13999999999','wer@wer.df',0,'cbddc1800e878132','728f76ae291869e355f4c1d63cf57acd89e2338e',NULL,NULL,NULL,NULL),(5,'000005','000005N',145,NULL,'123123','tutyu@uyg.iuj',0,'81a3005639058bff','b7b80b81c4f2e13565edc0eb001b9eb1405fab59',NULL,NULL,NULL,NULL),(6,'000006','000006N',139,'DB0102','12313123','yht@yuy.ytd',0,'f963074ff762223b','700e68ed8091dd7544074d3fc75e659c0b6d40a9',NULL,NULL,NULL,NULL),(7,'000007','000007N',143,'DB010101','15333333332','e@w.22',0,'2718caebd928a771','50c6e8122db0e4eff4c14692e0b8499bc5280cfa',NULL,NULL,NULL,NULL),(27,'aaaaa','aaaaa',1,'DB01','','',0,'cbddc1800e878132','728f76ae291869e355f4c1d63cf57acd89e2338e',NULL,NULL,NULL,NULL),(28,'bbbbb','bbbbb',1,'DB01','','',0,'cbddc1800e878132','728f76ae291869e355f4c1d63cf57acd89e2338e',NULL,NULL,NULL,NULL),(29,'ccccc','ccccc',1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(30,'ddddd','ddddd',1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(31,'eeeee','eeeee',1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(32,'rrrrr','rrrrr',1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(33,'tttt','ttttt',1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(34,'yyyyy','yyyyy',1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(35,'ggggg','ggggg',1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(36,'vvvvv','vvvvv',1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(37,'qqqqq','qqqqq',1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(38,'sfdsd','wrw',1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(39,'cfvcb','wr',1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(40,'cxbxcvb','wer',1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(41,'gdgdf','wer',1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(42,'dfgd','wr',1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(43,'dfgdgf','er',1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(44,'ngndf','r',1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(45,'fgnfgb','wr',1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(46,'dddd2','aaaa',139,'DB0102','','',0,'077372600c2f88e7','68245bb10efb4a7334014865025974e1dd2f3369',NULL,NULL,NULL,NULL),(47,'dgdfgdfgd','dffgd',139,'DB0102','','',0,'503661fa1cd4a194','6da46cace8c1f947171f6402b7558af8c58c115f',NULL,NULL,NULL,NULL),(48,'sdfsf','sfsf',138,'DB0101','','',0,'a651512f104353c1','3d9dfd8e097f521c6bb3be94dfb78a80260c0e6e',NULL,NULL,NULL,NULL),(49,'sdfsfd','sfdsf',138,'DB0101','','',0,'0addac5e7db95ab4','c54332bbbdef42b8bcede5d72eea07e1b517e73e',NULL,NULL,NULL,NULL),(50,'ddd','aaa',140,'DB0104','11111111111','2222222',0,'b7ef70c74d103fcf','198e9cb8316a880e0b07a79ffaa3e4bf9b0b2f39',NULL,NULL,NULL,NULL),(51,'wer','wer',1,'DB01','wr','',0,'3f0c058d95b02e6d','00ea2f551d2d28be81789f8b235ce0ad2c39c822',NULL,NULL,NULL,NULL),(52,'ddd2','abc',140,'DB0104','','',0,'1435c0f082f1f639','80857414aac8ad514b198d14c586e6b00eed7782',NULL,NULL,NULL,NULL),(53,'erte','ewt',140,'DB0104','','',0,'a45d5c82838142f4','8e55099d31f0ab12c49adddf448171f57606e982',NULL,NULL,NULL,NULL),(54,'rfffff','ertrerte',140,'DB0104','','',0,'9e607d2078040dff','18dd0b6165053b6860192d3887fe1ee9ecdc7fa0',NULL,NULL,NULL,NULL),(55,'222222222','1111111',140,'DB0104','','',0,'ae571d04e253f0ed','892f559acda7df9b3378c90b8e1ef95549dbb008',NULL,NULL,NULL,NULL),(56,'22222','333333',140,'DB0104','','',0,'4395a59f891a92ac','6fb722ce81fcb2fa50a3c87e5a0e7c485ad3a226',NULL,NULL,NULL,NULL),(57,'fgdgdfe','test',1,'DB01','','',0,'886a3b09b9bfc00a','b8db15c83012d62e4ece4416a2c6200a640c0b30',NULL,NULL,NULL,NULL),(58,'fdfgd','dgdfgd',1,'DB01','','',0,'34e29fc46f4d4347','f2782bad03f9c4ebab503e1292c1a08c7889a753',NULL,NULL,NULL,NULL),(59,'asdfasdf','asdfasdf',1,'DB01','','',0,'9f13cfe80c7f4cf6','9c28abc8fbd5221d7c8b5f50ec4f654a9e119946',NULL,NULL,NULL,NULL),(60,'dfdf','sdfs',219,'8604','','',0,'56890bb0c52a367e','f4627e222c370f65d861e3de103f0c5828d57e3d',NULL,NULL,NULL,NULL);

/*Table structure for table `t_user_role` */

DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `t_user_id` bigint(20) DEFAULT NULL,
  `t_role_id` bigint(20) DEFAULT NULL,
  `make_time` int(11) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `modify_time` int(11) DEFAULT NULL,
  `modify_operator` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;

/*Data for the table `t_user_role` */

insert  into `t_user_role`(`id`,`t_user_id`,`t_role_id`,`make_time`,`operator`,`modify_time`,`modify_operator`) values (56,11,10,NULL,NULL,NULL,NULL),(60,12,10,NULL,NULL,NULL,NULL),(61,13,11,NULL,NULL,NULL,NULL),(62,23,11,NULL,NULL,NULL,NULL),(64,26,10,NULL,NULL,NULL,NULL),(67,2,10,NULL,NULL,NULL,NULL),(68,2,11,NULL,NULL,NULL,NULL),(69,3,10,NULL,NULL,NULL,NULL),(70,3,11,NULL,NULL,NULL,NULL),(74,54,11,NULL,NULL,NULL,NULL),(75,55,11,NULL,NULL,NULL,NULL),(76,56,10,NULL,NULL,NULL,NULL),(77,46,11,NULL,NULL,NULL,NULL),(78,6,10,NULL,NULL,NULL,NULL),(79,6,11,NULL,NULL,NULL,NULL),(81,1,10,NULL,NULL,NULL,NULL),(82,27,11,NULL,NULL,NULL,NULL),(83,57,13,NULL,NULL,NULL,NULL),(84,57,15,NULL,NULL,NULL,NULL),(85,52,11,NULL,NULL,NULL,NULL),(86,52,15,NULL,NULL,NULL,NULL),(87,58,11,NULL,NULL,NULL,NULL),(88,58,12,NULL,NULL,NULL,NULL),(89,28,10,NULL,NULL,NULL,NULL);

/*Table structure for table `t_usual_approver` */

DROP TABLE IF EXISTS `t_usual_approver`;

CREATE TABLE `t_usual_approver` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) DEFAULT NULL,
  `approver_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

/*Data for the table `t_usual_approver` */

insert  into `t_usual_approver`(`id`,`uid`,`approver_id`) values (28,2,28),(29,2,45),(30,2,4),(31,4,4),(32,4,2),(33,5,4),(34,4,5),(35,4,3),(36,3,4),(37,6,2);

/*Table structure for table `t_vacation` */

DROP TABLE IF EXISTS `t_vacation`;

CREATE TABLE `t_vacation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `vacation_type` varchar(10) DEFAULT '01' COMMENT '01事假，02病假，03产假，04年假，05婚嫁，06工伤，07产检假，08其他',
  `start_date` int(11) DEFAULT NULL COMMENT '休假开始时间',
  `end_date` int(11) DEFAULT NULL COMMENT '休假结束时间',
  `app_hours` double(5,1) DEFAULT NULL COMMENT '休假小时',
  `reason` varchar(2000) DEFAULT NULL COMMENT '申请原因',
  `make_time` int(11) DEFAULT NULL COMMENT '创建时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '申请人',
  `source` varchar(10) DEFAULT NULL COMMENT '01系统02微信',
  `app_days` double(4,1) DEFAULT NULL COMMENT '休假天数-未使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

/*Data for the table `t_vacation` */

insert  into `t_vacation`(`id`,`vacation_type`,`start_date`,`end_date`,`app_hours`,`reason`,`make_time`,`operator_id`,`source`,`app_days`) values (8,'04',1422200400,1422403200,32.0,'年假啊',1422200476,2,'01',NULL),(9,'02',1422316800,1422352800,8.0,'难受不舒服',1422282882,2,'01',NULL),(10,'',1420383900,1422371100,NULL,'',1422284735,2,'01',NULL),(11,'',1421075400,1422371400,4.0,'大是大非',1422285228,2,'01',NULL),(12,'06',1420557300,1422371700,3.0,'党的',1422285359,2,'01',NULL),(13,'07',1422285300,1422458100,4.0,'党的',1422285371,2,'01',NULL),(14,'04',1422285360,1422371760,4.0,'方法',1422285426,2,'01',NULL),(15,'06',1422285420,1422371820,4.0,'人的风格',1422285471,2,'01',NULL),(16,'02',1422285540,1422371940,4.0,'梵蒂冈',1422285554,2,'01',NULL),(17,'01',1419952800,1422372000,3.0,'地方',1422285620,2,'01',NULL),(18,'01',1422324300,1422410700,2.0,'大苏打',1422324348,2,'01',NULL),(19,'06',1422324300,1422410700,4.0,'党的',1422324364,2,'01',NULL),(20,'06',1422324300,1422410700,4.0,'嗖嗖嗖',1422324560,2,'01',NULL),(21,'08',1421805900,1422324300,4.0,'方法',1422324625,2,'01',NULL),(22,'06',1422535680,1422622080,5.0,'dddd',1422535732,4,'01',NULL),(23,'03',1419426780,1422539040,NULL,'',1422537213,2,'01',NULL),(24,'04',1422545520,1422631920,4.0,'fff',1422545559,5,'01',NULL),(25,'03',1422545520,1422631920,33.0,'dd',1422545692,5,'01',NULL),(26,'01',1422545520,1422631920,3.0,'eee',1422545917,5,'01',NULL),(27,'03',1422545520,1422631920,3.0,'',1422546305,5,'01',NULL),(28,'03',1422545520,1422631920,44.0,'',1422546336,5,'01',NULL),(29,'03',1421249520,1421681520,4.0,'ddd',1422546687,5,'01',NULL),(30,'02',1420385520,1422631920,4.0,'111',1422546804,5,'01',NULL),(31,'03',1420644720,1421767920,33.0,'4343',1422546831,5,'01',NULL),(32,'05',1422545520,1422631920,5.0,'343',1422546851,5,'01',NULL),(33,'03',1420644720,1422631920,4.0,'4343',1422546903,5,'01',NULL),(34,'02',1422545520,1422631920,4.0,'444',1422546954,5,'01',NULL),(35,'03',1420471920,1422631920,4.0,'erte',1422547036,5,'01',NULL),(36,'03',1422545520,1422631920,4.0,'345',1422547057,5,'01',NULL),(37,'03',1420039920,1422545520,4.0,'dgdfg',1422547100,5,'01',NULL),(38,'03',1422545520,1422631920,4.0,'dfgd',1422547116,5,'01',NULL),(39,'05',1422545520,1423323120,55.0,'gdgg',1422547137,5,'01',NULL),(40,'04',1419867120,1421163120,334.0,'',1422547167,5,'01',NULL),(41,'01',1422545520,1422631920,3.0,'sfs',1422547322,5,'01',NULL),(42,'02',1422545520,1422718320,44.0,'dgd',1422547343,5,'01',NULL),(43,'03',1422545520,1422631920,5.0,'343',1422547395,5,'01',NULL),(44,'03',1422545520,1422631920,4.0,'dfg',1422547494,5,'01',NULL),(45,'07',1422545520,1422631920,4.0,'sdf',1422547557,5,'01',NULL),(46,'03',1422545520,1422718320,34.0,'sdf',1422547616,5,'01',NULL),(47,'04',1422545520,1422631920,4.0,'dd',1422547666,5,'01',NULL),(48,'04',1422545520,1422631920,1.0,'11',1422547738,5,'01',NULL),(49,'06',1421249520,1422545520,2.0,'333',1422547749,5,'01',NULL),(50,'03',1422545520,1422718320,3.0,'444',1422547832,5,'01',NULL),(51,'03',1422545520,1422718320,4.0,'ere',1422547858,5,'01',NULL),(52,'04',1422545520,1422718320,4.0,'fdgdf',1422547889,5,'01',NULL),(53,'02',1422545520,1422631920,3.0,'4',1422548071,5,'01',NULL),(54,'06',1422545520,1422631920,4.0,'ff',1422548084,5,'01',NULL),(55,'06',1422545520,1422631920,4.0,'eee',1422548263,5,'01',NULL),(56,'02',1422671220,1422757620,3.0,'dsf',1422671289,2,'01',NULL),(57,'03',1422672720,1423191120,6.0,'ff',1422672760,2,'01',NULL),(58,'03',1423190220,1423708620,55.0,'dfdf',1423190299,4,'01',NULL),(59,'04',1424530800,1425049200,5.0,'3434fds',1424530818,4,'01',NULL),(60,'08',1426673820,1430302620,2.0,'的的的三翻四复是的的三翻四复是的的三翻四复是的的三翻四复是的的三翻四复的的三翻四复是的的三的的三翻四的的三翻的的三翻四复是四复是复是翻四复是是',1427710660,4,'01',NULL);

/*Table structure for table `t_workflow_task` */

DROP TABLE IF EXISTS `t_workflow_task`;

CREATE TABLE `t_workflow_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `business_id` bigint(20) DEFAULT NULL COMMENT '业务id(t_vacation表， id)',
  `business_type` varchar(50) DEFAULT NULL COMMENT '业务类型',
  `participant_id` bigint(20) DEFAULT NULL COMMENT '当前参与人(t_user表id)',
  `app_date` int(11) DEFAULT NULL COMMENT '提交申请的时间',
  `apply_user_id` bigint(20) DEFAULT NULL COMMENT '申请人(t_user表id)',
  `status` varchar(3) DEFAULT NULL COMMENT '00未提交01审批中02退回03已通过',
  `make_time` int(11) DEFAULT NULL COMMENT '创建时间',
  `operate_time` int(11) DEFAULT NULL COMMENT '最近一次操作时间',
  `cancel_date` int(11) DEFAULT NULL COMMENT '作废日期',
  `cur_step` varchar(30) DEFAULT NULL COMMENT '当前步骤',
  `t_organization_id` bigint(20) DEFAULT NULL COMMENT '发出申请的部门(t_organization表id)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;

/*Data for the table `t_workflow_task` */

insert  into `t_workflow_task`(`id`,`title`,`business_id`,`business_type`,`participant_id`,`app_date`,`apply_user_id`,`status`,`make_time`,`operate_time`,`cancel_date`,`cur_step`,`t_organization_id`) values (15,'admin的休假申请。',8,'vacation',4,NULL,2,'00',1422200476,1422282882,NULL,NULL,NULL),(16,'admin的休假申请。edd',9,'vacation',NULL,1422503393,2,'03',1422282882,1422545508,NULL,NULL,NULL),(17,'admin的休假申请。22',10,'vacation',4,NULL,2,'04',1422284735,1422282882,1422414466,NULL,NULL),(18,'admin的休假申请。',11,'vacation',4,NULL,2,'00',1422285228,1422282882,NULL,NULL,NULL),(19,'admin的休假申请。',12,'vacation',39,1422413990,2,'01',1422285359,1422282882,NULL,NULL,NULL),(20,'admin的休假申请。',13,'vacation',4,1422413845,2,'02',1422285371,1422672296,NULL,NULL,NULL),(21,'admin的休假申请。',14,'vacation',4,NULL,2,'04',1422285427,1422282882,NULL,NULL,NULL),(22,'admin的休假申请。',15,'vacation',4,NULL,2,'04',1422285471,1422282882,NULL,NULL,NULL),(23,'admin的休假申请。',16,'vacation',6,1422285558,2,'01',1422285554,1422282882,NULL,NULL,NULL),(24,'admin的休假申请。',17,'vacation',39,1422285629,2,'01',1422285620,1422282882,NULL,NULL,NULL),(25,'admin的休假申请。',18,'vacation',28,1422324354,2,'01',1422324348,1422282882,NULL,NULL,NULL),(26,'admin的休假申请。',19,'vacation',4,1422503388,2,'02',1422324364,1422672374,NULL,NULL,NULL),(27,'admin的休假申请。',20,'vacation',4,1422503382,2,'03',1422324582,1422540648,NULL,NULL,NULL),(28,'admin的休假申请。',21,'vacation',45,1422324667,2,'01',1422324626,1422282882,NULL,NULL,NULL),(29,'000004N(000004)的休假申请。',22,'vacation',NULL,1422535736,4,'02',1422535732,1422672971,NULL,NULL,NULL),(30,'admin(admin)的休假申请。',23,'vacation',NULL,NULL,2,'00',1422537213,1422282882,NULL,NULL,NULL),(31,'000005N(000005)的休假申请。',24,'vacation',NULL,1422545562,5,'03',1422545559,1422545880,NULL,NULL,NULL),(32,'000005N(000005)的休假申请。',25,'vacation',NULL,1422545695,5,'02',1422545692,1422545723,NULL,NULL,NULL),(33,'000005N(000005)的休假申请。',26,'vacation',NULL,1422545920,5,'03',1422545917,1422545931,NULL,NULL,NULL),(34,'000005N(000005)的休假申请。',27,'vacation',2,1422546310,5,'01',1422546305,1423356839,NULL,NULL,NULL),(35,'000005N(000005)的休假申请。',28,'vacation',NULL,1422546341,5,'03',1422546336,1422546354,NULL,NULL,NULL),(36,'000005N(000005)的休假申请。',29,'vacation',NULL,1422546689,5,'03',1422546687,1422546695,NULL,NULL,NULL),(37,'000005N(000005)的休假申请。',30,'vacation',NULL,1422546807,5,'03',1422546804,1422672975,NULL,NULL,NULL),(38,'000005N(000005)的休假申请。',31,'vacation',NULL,1422546833,5,'03',1422546831,1422546839,NULL,NULL,NULL),(39,'000005N(000005)的休假申请。',32,'vacation',NULL,1422546854,5,'02',1422546851,1422546866,NULL,NULL,NULL),(40,'000005N(000005)的休假申请。',33,'vacation',NULL,1422546905,5,'02',1422546903,1422546940,NULL,NULL,NULL),(41,'000005N(000005)的休假申请。',34,'vacation',3,1422546956,5,'01',1422546954,1422546966,NULL,NULL,NULL),(42,'000005N(000005)的休假申请。',35,'vacation',NULL,1422547038,5,'03',1422547036,1423141623,NULL,NULL,NULL),(43,'000005N(000005)的休假申请。',36,'vacation',NULL,1422547058,5,'03',1422547057,1422547064,NULL,NULL,NULL),(44,'000005N(000005)的休假申请。',37,'vacation',NULL,NULL,5,'00',1422547100,NULL,NULL,NULL,NULL),(45,'000005N(000005)的休假申请。',38,'vacation',NULL,1422547118,5,'03',1422547116,1422547125,NULL,NULL,NULL),(46,'000005N(000005)的休假申请。',39,'vacation',NULL,1422547139,5,'02',1422547137,1422547145,NULL,NULL,NULL),(47,'000005N(000005)的休假申请。',40,'vacation',NULL,1422547171,5,'03',1422547167,1422547176,NULL,NULL,NULL),(48,'000005N(000005)的休假申请。',41,'vacation',5,1422547324,5,'01',1422547322,1422547333,NULL,NULL,NULL),(49,'000005N(000005)的休假申请。',42,'vacation',NULL,1422547345,5,'03',1422547343,1422547350,NULL,NULL,NULL),(50,'000005N(000005)的休假申请。',43,'vacation',5,1422547397,5,'01',1422547395,1422547404,NULL,NULL,NULL),(51,'000005N(000005)的休假申请。',44,'vacation',3,1422547496,5,'01',1422547494,1422547501,NULL,NULL,NULL),(52,'000005N(000005)的休假申请。',45,'vacation',NULL,1422547559,5,'03',1422547557,1422547563,NULL,NULL,NULL),(53,'000005N(000005)的休假申请。',46,'vacation',4,1422547618,5,'01',1422547616,1422672995,NULL,NULL,NULL),(54,'000005N(000005)的休假申请。',47,'vacation',3,1422547668,5,'01',1422547666,1422547673,NULL,NULL,NULL),(55,'000005N(000005)的休假申请。',48,'vacation',NULL,1422547740,5,'03',1422547738,1422547780,NULL,NULL,NULL),(56,'000005N(000005)的休假申请。',49,'vacation',3,1422547751,5,'01',1422547749,1422547776,NULL,NULL,NULL),(57,'000005N(000005)的休假申请。',50,'vacation',NULL,1422547833,5,'02',1422547832,1422547842,NULL,NULL,NULL),(58,'000005N(000005)的休假申请。',51,'vacation',NULL,1422547860,5,'03',1422547858,1422672978,NULL,NULL,NULL),(59,'000005N(000005)的休假申请。',52,'vacation',NULL,1422547890,5,'03',1422547889,1422547904,NULL,NULL,NULL),(60,'000005N(000005)的休假申请。',53,'vacation',3,1422548074,5,'01',1422548071,1422548191,NULL,NULL,NULL),(61,'000005N(000005)的休假申请。',54,'vacation',5,1422548086,5,'01',1422548084,1422548152,NULL,NULL,NULL),(62,'000005N(000005)的休假申请。',55,'vacation',3,1422548266,5,'01',1422548263,1422548276,NULL,NULL,NULL),(63,'admin(admin)的休假申请。',56,'vacation',NULL,1422671291,2,'02',1422671289,1422671322,NULL,NULL,NULL),(64,'admin(admin)的休假申请。',57,'vacation',NULL,1422672769,2,'03',1422672760,1422672879,NULL,NULL,NULL),(65,'000003N(000003)的外出申请。',1,'travel',NULL,NULL,3,'00',1423189995,NULL,NULL,NULL,NULL),(66,'000004N(000004)的外出申请。',2,'travel',NULL,NULL,4,'00',1423190166,NULL,NULL,NULL,NULL),(67,'000004N(000004)的外出申请。',3,'travel',NULL,NULL,4,'00',1423190170,NULL,NULL,NULL,NULL),(68,'000004N(000004)的外出申请。',4,'travel',4,1423190243,4,'01',1423190231,1423190262,NULL,NULL,NULL),(69,'000004N(000004)的外出申请。',5,'travel',NULL,NULL,4,'00',1423190281,NULL,NULL,NULL,NULL),(70,'000004N(000004)的休假申请。',58,'vacation',NULL,NULL,4,'00',1423190299,NULL,NULL,NULL,NULL),(71,'000004N(000004)的外出申请。',6,'travel',NULL,1423190512,4,'03',1423190509,1423190694,NULL,NULL,NULL),(72,'000004N(000004)的休假申请。',59,'vacation',2,1424530825,4,'01',1424530818,1424530825,NULL,NULL,NULL),(73,'000004N(000004)的休假申请。',60,'vacation',2,1427710666,4,'01',1427710660,1427710666,NULL,NULL,NULL),(74,'000006N(000006)的外出申请。',7,'travel',2,1427882637,6,'01',1427882625,1427882637,NULL,NULL,NULL),(75,'000006N(000006)的外出申请。',8,'travel',2,1427882671,6,'01',1427882666,1427882671,NULL,NULL,NULL),(76,'000004N(000004)的外出申请。',9,'travel',3,1427882698,4,'01',1427882696,1427882698,NULL,NULL,NULL),(77,'admin(admin)的外出申请。',10,'travel',4,1428934466,2,'01',1428934463,1428934466,NULL,NULL,NULL);

/*Table structure for table `t_workflow_task_detail` */

DROP TABLE IF EXISTS `t_workflow_task_detail`;

CREATE TABLE `t_workflow_task_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `t_workflow_task_id` bigint(20) DEFAULT NULL,
  `operator_id` bigint(20) DEFAULT NULL,
  `operator_type` varchar(3) DEFAULT NULL COMMENT '01提交申请 02通过 03退回 04确认',
  `comment` varchar(1000) DEFAULT NULL,
  `make_time` int(11) DEFAULT NULL,
  `participant_organization_id` bigint(20) DEFAULT NULL COMMENT '当前参与人所负责部门的部门id(t_organization表id)，只在approval.properties里面定义的下一步参与人为leader时候使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8;

/*Data for the table `t_workflow_task_detail` */

insert  into `t_workflow_task_detail`(`id`,`t_workflow_task_id`,`operator_id`,`operator_type`,`comment`,`make_time`,`participant_organization_id`) values (3,1,3,'01','申请流程',1418026746,NULL),(4,1,3,'02','通过了，下一步！',1418026828,NULL),(5,1,4,'02','通过了，下一步！000004',1418026912,NULL),(6,1,5,'04','通过000005了，下一步！000004',1418027029,NULL),(7,2,7,'01','申请流程leader-end',1418027470,143),(8,2,3,'04','通过000003了，下一步！',1418027647,NULL),(11,3,3,'04','申请流程leader-end',1418028201,138),(12,4,7,'01','申请流程leader-leader-end',1418028852,143),(13,4,3,'02','通过000003了，下一步！',1418029983,138),(14,4,1,'02','通过000003了，下一步！',1418030235,NULL),(15,4,5,'04','通过u了，下一步！',1418030635,NULL),(16,5,3,'01','申请流程leader-leader-end',1418030745,138),(17,5,1,'02','通过u了，下一步！',1418030857,NULL),(18,5,5,'04','通过u了，下一步！',1418030904,NULL),(19,6,7,'01','申请流程leader-leader-end',1418031993,143),(20,6,3,'03','通过u了，下一步！',1418032061,NULL),(21,6,7,'01','申请流程leader-leader-end',1418032256,143),(22,6,3,'02','通过u了，下一步！',1418032843,138),(23,6,1,'02','通过u了，下一步！',1418033076,NULL),(24,6,5,'03','通过u了，下一步！',1418033161,NULL),(25,6,7,'01','申请流程leader-leader-end',1418120801,143),(26,6,3,'02','通过u了，下一步！',1418120850,138),(27,6,1,'02','通过u了，下一步！',1418120881,NULL),(28,6,5,'04','通过u了，下一步！',1418120911,NULL),(29,23,2,'01','梵蒂冈',1422285558,NULL),(30,24,2,'01','地方',1422285629,NULL),(31,25,2,'01','大苏打',1422324354,NULL),(32,28,2,'01','方法',1422324671,NULL),(33,20,2,'01','同意',1422413845,NULL),(34,19,2,'01','同意',1422413990,NULL),(35,27,2,'01','同意',1422503382,NULL),(36,26,2,'01','同意',1422503388,NULL),(37,16,2,'01','同意',1422503393,NULL),(38,29,4,'01','dddd',1422535736,NULL),(39,16,4,'01','同意',1422537362,NULL),(40,16,2,'01','同意',1422537373,NULL),(41,27,4,'04','我确认通过的!',1422540648,NULL),(42,20,4,'03','我退回的~~哦',1422540729,NULL),(43,26,4,'03','豆腐干',1422540757,NULL),(44,29,4,'01','同意',1422545433,NULL),(45,16,4,'04','',1422545508,NULL),(46,31,5,'01','fff',1422545562,NULL),(47,31,4,'01','同意',1422545630,NULL),(48,32,5,'01','dd',1422545695,NULL),(49,32,4,'01','同意',1422545710,NULL),(50,32,5,'03','',1422545723,NULL),(51,31,5,'04','fff',1422545880,NULL),(52,33,5,'01','eee',1422545920,NULL),(53,33,4,'04','方法',1422545931,NULL),(54,34,5,'01','44',1422546310,NULL),(55,34,4,'01','同意',1422546318,NULL),(56,35,5,'01','4',1422546341,NULL),(57,35,4,'04','43434',1422546354,NULL),(58,36,5,'01','ddd',1422546689,NULL),(59,36,4,'04','43434',1422546695,NULL),(60,37,5,'01','111',1422546807,NULL),(61,37,4,'01','同意',1422546816,NULL),(62,38,5,'01','4343',1422546833,NULL),(63,38,4,'04','确认通过44',1422546839,NULL),(64,39,5,'01','343',1422546854,NULL),(65,39,4,'03','434',1422546866,NULL),(66,40,5,'01','4343',1422546905,NULL),(67,40,4,'03','44',1422546940,NULL),(68,41,5,'01','444',1422546956,NULL),(69,41,4,'01','同意',1422546966,NULL),(70,42,5,'01','erte',1422547038,NULL),(71,42,4,'01','同意',1422547045,NULL),(72,43,5,'01','345',1422547058,NULL),(73,43,4,'04','确认通过',1422547064,NULL),(74,45,5,'01','dfgd',1422547118,NULL),(75,45,4,'04','确认通过',1422547125,NULL),(76,46,5,'01','gdgg',1422547139,NULL),(77,46,4,'03','个',1422547145,NULL),(78,47,5,'01','rrr',1422547171,NULL),(79,47,4,'04','确认通过',1422547176,NULL),(80,48,5,'01','sfs',1422547324,NULL),(81,48,4,'01','同意',1422547333,NULL),(82,49,5,'01','dgd',1422547345,NULL),(83,49,4,'04','确认通过',1422547350,NULL),(84,50,5,'01','343',1422547397,NULL),(85,50,4,'01','同意',1422547404,NULL),(86,51,5,'01','dfg',1422547496,NULL),(87,51,4,'01','同意',1422547501,NULL),(88,52,5,'01','sdf',1422547559,NULL),(89,52,4,'04','确认通过',1422547563,NULL),(90,53,5,'01','sdf',1422547618,NULL),(91,53,4,'01','同意',1422547624,NULL),(92,54,5,'01','dd',1422547668,NULL),(93,54,4,'01','同意',1422547673,NULL),(94,55,5,'01','11',1422547740,NULL),(95,56,5,'01','333',1422547751,NULL),(96,56,4,'01','同意',1422547776,NULL),(97,55,4,'04','确认通过',1422547780,NULL),(98,57,5,'01','444',1422547833,NULL),(99,57,4,'03','让人',1422547842,NULL),(100,58,5,'01','ere',1422547860,NULL),(101,58,4,'01','同意',1422547866,NULL),(102,59,5,'01','fdgdf',1422547890,NULL),(103,59,4,'04','确认通过',1422547904,NULL),(104,60,5,'01','4',1422548074,NULL),(105,61,5,'01','ff',1422548086,NULL),(106,61,4,'01','同意',1422548152,NULL),(107,60,4,'01','同意',1422548191,NULL),(108,62,5,'01','eee',1422548266,NULL),(109,62,4,'01','同意',1422548276,NULL),(110,63,2,'01','dsf',1422671291,NULL),(111,63,4,'01','同意',1422671301,NULL),(112,63,2,'03','rrrr',1422671322,NULL),(113,20,2,'02','同意~~~',1422672296,NULL),(114,26,2,'02','同意',1422672311,NULL),(115,26,2,'02','同意',1422672339,NULL),(116,26,2,'02','同意',1422672374,NULL),(117,29,2,'03','rr',1422672415,NULL),(118,29,4,'02','同意44',1422672431,NULL),(119,29,4,'02','同意',1422672527,NULL),(120,64,2,'02','ff',1422672847,NULL),(121,64,4,'04','确认通过',1422672879,NULL),(122,42,2,'02','同意',1422672962,NULL),(123,29,2,'03','ee',1422672971,NULL),(124,37,2,'04','确认通过',1422672975,NULL),(125,58,2,'04','确认通过',1422672978,NULL),(126,34,2,'02','同意',1422672982,NULL),(127,53,2,'02','同意',1422672995,NULL),(128,42,4,'04','确认通过',1423141623,NULL),(129,68,4,'01','fffddfdf',1423190243,NULL),(130,68,3,'02','同意',1423190262,NULL),(131,71,4,'01','333',1423190512,NULL),(132,71,3,'02','同意',1423190688,NULL),(133,71,4,'04','确认通过',1423190694,NULL),(134,34,4,'02','同意',1423356839,NULL),(135,72,4,'01','3434fds',1424530825,NULL),(136,73,4,'01','ee',1427710666,NULL),(137,74,6,'01','党的',1427882637,NULL),(138,75,6,'01','333',1427882671,NULL),(139,76,4,'01','大幅度',1427882698,NULL),(140,77,2,'01','sf',1428934466,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
