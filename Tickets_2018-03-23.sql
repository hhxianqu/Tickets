# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.17)
# Database: Tickets
# Generation Time: 2018-03-23 09:13:44 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT '',
  `identity` varchar(8) NOT NULL DEFAULT '',
  `account` varchar(11) NOT NULL DEFAULT '',
  `balance` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;

INSERT INTO `account` (`id`, `username`, `identity`, `account`, `balance`)
VALUES
	(1,'735913885@qq.com','user','支付宝',5530),
	(2,'hx36w35@163.com','user','支付宝',10000),
	(3,'767085586@qq.com','user','支付宝',8860),
	(4,'00000001','manager','支付宝',95340),
	(5,'0000001','venue','支付宝',159880),
	(6,'0000002','venue','支付宝',0),
	(7,'0000003','venue','支付宝',0),
	(8,'0000004','venue','支付宝',0),
	(9,'0000005','venue','支付宝',0),
	(10,'0000006','venue','工商银行',0),
	(11,'0000007','venue','工商银行',0),
	(12,'0000008','venue','工商银行',73920),
	(13,'0000009','venue','工商银行',0),
	(14,'0000010','venue','支付宝',181860),
	(15,'0000011','venue','支付宝',0),
	(16,'0000012','venue','工商银行',0),
	(17,'0000013','venue','中国银行',0),
	(18,'0000014','venue','中国银行',0),
	(19,'0000015','venue','中国银行',0),
	(20,'0000016','venue','中国银行',0),
	(21,'0000017','venue','工商银行',0),
	(22,'0000018','venue','工商银行',0),
	(23,'0000019','venue','支付宝',0),
	(24,'0000024','venue','支付宝',0),
	(25,'0000025','user','支付宝',-1460),
	(26,NULL,'venue','支付宝',0),
	(27,NULL,'venue','支付宝',0),
	(28,NULL,'venue','支付宝',0),
	(29,NULL,'venue','支付宝',0);

/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table changed_venue
# ------------------------------------------------------------

DROP TABLE IF EXISTS `changed_venue`;

CREATE TABLE `changed_venue` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(20) NOT NULL DEFAULT '',
  `venue_number` varchar(7) NOT NULL DEFAULT '',
  `venue_name` varchar(40) NOT NULL DEFAULT '',
  `audit` tinyint(1) NOT NULL,
  `pass` tinyint(1) NOT NULL,
  `city` varchar(11) NOT NULL DEFAULT '',
  `detail_position` varchar(40) NOT NULL DEFAULT '',
  `seat` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `changed_venue` WRITE;
/*!40000 ALTER TABLE `changed_venue` DISABLE KEYS */;

INSERT INTO `changed_venue` (`id`, `email`, `venue_number`, `venue_name`, `audit`, `pass`, `city`, `detail_position`, `seat`)
VALUES
	(1,'735913885@qq.com','0000001','无锡大剧院',1,1,'无锡','大剧院路1号',500),
	(2,'735913885@qq.com','0000025','NJU',1,1,'南京','汉口路22号',500);

/*!40000 ALTER TABLE `changed_venue` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table discount_market
# ------------------------------------------------------------

DROP TABLE IF EXISTS `discount_market`;

CREATE TABLE `discount_market` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `discount_num` int(11) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `score_to_deduct` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `discount_market` WRITE;
/*!40000 ALTER TABLE `discount_market` DISABLE KEYS */;

INSERT INTO `discount_market` (`id`, `discount_num`, `start_date`, `end_date`, `score_to_deduct`)
VALUES
	(1,20,'2018-01-01 00:00:00','2018-12-31 00:00:00',400),
	(2,50,'2018-01-01 00:00:00','2018-12-31 00:00:00',1000);

/*!40000 ALTER TABLE `discount_market` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table discount_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `discount_user`;

CREATE TABLE `discount_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_email` varchar(20) DEFAULT NULL,
  `discount_id` int(11) DEFAULT NULL,
  `own_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `discount_user` WRITE;
/*!40000 ALTER TABLE `discount_user` DISABLE KEYS */;

INSERT INTO `discount_user` (`id`, `user_email`, `discount_id`, `own_num`)
VALUES
	(1,'123',1,4),
	(2,'123',2,1),
	(3,'735913885@qq.com',1,1),
	(4,'767085586@qq.com',1,1);

/*!40000 ALTER TABLE `discount_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table manager
# ------------------------------------------------------------

DROP TABLE IF EXISTS `manager`;

CREATE TABLE `manager` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `manager_password` varchar(20) NOT NULL DEFAULT '',
  `manager_number` varchar(8) DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;

INSERT INTO `manager` (`id`, `manager_password`, `manager_number`, `account_id`)
VALUES
	(1,'123456','00000001',4);

/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table orders
# ------------------------------------------------------------

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_state` varchar(11) NOT NULL DEFAULT '',
  `perform_id` int(11) NOT NULL,
  `vip_num` int(3) NOT NULL,
  `normal_num` int(3) NOT NULL,
  `email` varchar(20) NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;

INSERT INTO `orders` (`id`, `order_state`, `perform_id`, `vip_num`, `normal_num`, `email`, `create_time`, `price`)
VALUES
	(1,'已退款',5,2,0,'123','2018-03-22 10:59:26',910),
	(2,'已取消',9,2,2,'123','2018-03-22 14:22:56',940),
	(3,'已完成',5,0,1,'735913885@qq.com','2018-03-22 15:32:04',180),
	(4,'已完成',1,10,0,'123','2018-03-22 16:58:27',10550),
	(5,'已取消',1,2,1,'767085586@qq.com','2018-03-23 13:24:00',455),
	(6,'已完成',5,2,1,'767085586@qq.com','2018-03-23 13:36:10',1140),
	(7,'已取消',5,2,0,'767085586@qq.com','2018-03-23 13:42:25',960),
	(8,'已取消',5,3,2,'123','2018-03-23 14:08:18',1800),
	(9,'已退款',1,2,0,'735913885@qq.com','2018-03-23 16:50:10',2090),
	(10,'已完成',23,10,0,'735913885@qq.com','2018-03-23 16:51:13',2200),
	(11,'已取消',40,0,1,'735913885@qq.com','2018-03-23 16:51:39',80);

/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table perform
# ------------------------------------------------------------

DROP TABLE IF EXISTS `perform`;

CREATE TABLE `perform` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `perform_name` varchar(34) NOT NULL DEFAULT '',
  `venue_number` varchar(7) NOT NULL DEFAULT '',
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `perform_type` varchar(11) NOT NULL DEFAULT '',
  `perform_pass` tinyint(1) NOT NULL,
  `perform_audit` tinyint(1) NOT NULL,
  `ticket_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `perform` WRITE;
/*!40000 ALTER TABLE `perform` DISABLE KEYS */;

INSERT INTO `perform` (`id`, `perform_name`, `venue_number`, `start_time`, `end_time`, `perform_type`, `perform_pass`, `perform_audit`, `ticket_id`)
VALUES
	(1,'【苏州站】五月天人生无限公司演唱会','0000002','2018-04-06 00:00:00','2018-04-08 00:00:00','concert',1,1,1),
	(2,'【上海站】2018费玉清演唱会','0000004','2018-03-31 00:00:00','2018-03-31 00:00:00','concert',1,1,2),
	(3,'【上海站】FALL OUT BOY“狂热”2018世界巡演','0000004','2018-05-02 00:00:00','2018-05-02 00:00:00','concert',1,1,3),
	(4,'【苏州站】2018Gary曹格 城市唱游格友会','0000005','2018-04-03 00:00:00','2018-04-03 00:00:00','concert',1,1,4),
	(5,'【上海站】世界经典原版音乐剧《猫》CATS','0000006','2018-06-07 00:00:00','2018-06-30 00:00:00','opera',1,1,5),
	(6,'【上海站】法语原版经典音乐剧《罗密欧与朱丽叶》','0000006','2018-04-05 00:00:00','2018-04-22 00:00:00','opera',1,1,6),
	(7,'【上海站】暗恋桃花源（专属版）','0000007','2018-03-29 00:00:00','2018-04-15 00:00:00','opera',1,1,7),
	(8,'【苏州站】开心麻花爆笑舞台剧《羞羞的铁拳》','0000009','2018-04-28 00:00:00','2018-04-28 00:00:00','opera',1,1,8),
	(9,'【无锡-万有音乐系】“天空之城”宫崎骏·久石让动漫视听系','0000008','2018-04-20 00:00:00','2018-04-20 00:00:00','musical',1,1,9),
	(10,'【上海站】2018DOTA2亚洲邀请赛 ','0000010','2018-02-26 00:00:00','2018-02-28 00:00:00','sports',1,1,10),
	(11,'2018赛季中超联赛 上海上港VS江苏苏宁易购','0000010','2018-05-20 00:00:00','2018-05-20 00:00:00','sports',1,1,11),
	(12,'2018赛季中超联赛 上海绿地申花VS江苏苏宁易购','0000010','2018-09-15 00:00:00','2018-09-15 00:00:00','sports',1,1,12),
	(13,'2018赛季中超联赛 上海上港VS广州富力','0000010','2018-08-15 00:00:00','2018-08-15 00:00:00','sports',1,1,13),
	(14,'《四月是你的谎言》“公生”与“薰”的钢琴小提琴唯美经典音乐集','0000003','2018-04-07 00:00:00','2018-07-20 00:00:00','musical',1,1,14),
	(15,'【无锡-万有音乐系】“丝路狂想”马克西姆2018跨界钢琴演奏会','0000008','2018-01-08 00:00:00','2018-01-08 00:00:00','musical',1,1,15),
	(16,'【无锡站】“俄罗斯大师”钢琴三重奏音乐会','0000001','2018-03-17 00:00:00','2018-03-17 00:00:00','musical',1,1,16),
	(17,'【苏州站】爱乐汇·“致爱丽丝”经典浪漫钢琴名曲音乐会','0000005','2018-04-14 00:00:00','2018-04-14 00:00:00','musical',1,1,17),
	(18,'【上海站】上海歌舞团2018舞剧演出季 舞剧《霸王别姬》','0000011','2018-06-08 00:00:00','2018-06-15 00:00:00','dance',1,1,18),
	(19,'【上海站】陶身体剧场《6》《9》','0000011','2018-04-13 00:00:00','2018-04-14 00:00:00','dance',1,1,19),
	(20,'【上海站】大型音乐舞蹈剧《罗密欧与朱丽叶》','0000012','2018-05-25 00:00:00','2018-05-25 00:00:00','dance',1,1,20),
	(21,'【上海站】古典芭蕾舞剧《睡美人》','0000011','2018-05-10 00:00:00','2018-05-13 00:00:00','dance',1,1,21),
	(22,'【上海站】上海迪士尼乐园门票','0000013','2018-02-26 00:00:00','2018-04-11 00:00:00','exhibit',1,1,22),
	(23,'【上海站】【常年】上海玻璃博物馆','0000014','2018-01-01 00:00:00','2018-12-31 00:00:00','exhibit',1,1,23),
	(24,'【上海站】Wonder Festival 2018 ','0000015','2018-04-06 00:00:00','2018-04-07 00:00:00','exhibit',0,0,24),
	(25,'【上海站】第三季上海时尚周末-时尚穿梭博物馆','0000016','2018-04-06 00:00:00','2018-04-08 00:00:00','exhibit',0,0,25),
	(26,'【上海站】黄子韬 2018ISGOØD 巡回演唱会','0000004','2018-04-30 00:00:00','2018-04-30 00:00:00','concert',0,0,26),
	(29,'【无锡站-加场】暗恋桃花源','0000001','2018-03-17 00:00:00','2018-03-19 00:00:00','opera',1,1,29),
	(38,'【苏州站】何美男无限美男巡回演唱会','0000002','2018-03-19 00:00:00','2018-03-20 00:00:00','concert',1,1,41),
	(39,'J2EE','0000024','2018-03-25 00:00:00','2018-03-26 00:00:00','dance',1,1,42),
	(40,'开学典礼','0000025','2018-03-24 00:00:00','2018-03-25 00:00:00','dance',1,1,43);

/*!40000 ALTER TABLE `perform` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table seat
# ------------------------------------------------------------

DROP TABLE IF EXISTS `seat`;

CREATE TABLE `seat` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `perform_id` int(11) NOT NULL,
  `email` varchar(20) NOT NULL DEFAULT '',
  `seat_num` int(11) NOT NULL,
  `checked` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `seat` WRITE;
/*!40000 ALTER TABLE `seat` DISABLE KEYS */;

INSERT INTO `seat` (`id`, `perform_id`, `email`, `seat_num`, `checked`)
VALUES
	(1,1,'73591385@qq.com',1,0),
	(2,1,'73591385@qq.com',21,0),
	(3,1,'73591385@qq.com',102,0),
	(4,5,'123',14,0),
	(5,5,'123',13,0),
	(6,9,'123',10,0),
	(7,9,'123',11,0),
	(8,5,'735913885@qq.com',113,0),
	(9,1,'767085586@qq.com',13,0),
	(10,5,'767085586@qq.com',12,0),
	(11,5,'123',9,0),
	(12,5,'123',10,0),
	(13,1,'735913885@qq.com',8,0),
	(14,1,'735913885@qq.com',9,0),
	(15,40,'735913885@qq.com',112,0);

/*!40000 ALTER TABLE `seat` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table ticket
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ticket`;

CREATE TABLE `ticket` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `vip_num` int(11) NOT NULL,
  `normal_num` int(11) NOT NULL,
  `normal_price` int(11) NOT NULL,
  `vip_price` int(11) NOT NULL,
  `vip_sale` int(11) NOT NULL,
  `normal_sale` int(11) NOT NULL,
  `settled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;

INSERT INTO `ticket` (`id`, `vip_num`, `normal_num`, `normal_price`, `vip_price`, `vip_sale`, `normal_sale`, `settled`)
VALUES
	(1,100,400,455,1055,16,1,0),
	(2,200,300,580,1280,0,0,0),
	(3,100,400,580,1280,0,0,0),
	(4,200,300,380,880,0,0,0),
	(5,100,400,180,480,9,4,0),
	(6,200,300,80,480,0,0,0),
	(7,100,400,100,380,0,0,0),
	(8,200,300,80,400,0,0,0),
	(9,100,400,100,380,2,2,0),
	(10,200,300,899,1699,100,100,1),
	(11,100,400,199,499,0,0,0),
	(12,200,300,199,499,0,0,0),
	(13,100,400,199,499,0,0,0),
	(14,200,300,80,360,0,0,0),
	(15,100,400,880,880,20,100,1),
	(16,200,300,120,1680,20,100,1),
	(17,100,400,128,290,0,0,0),
	(18,200,300,180,280,0,0,0),
	(19,100,400,280,580,0,0,0),
	(20,200,300,280,480,0,0,0),
	(21,100,400,280,480,0,0,0),
	(22,200,300,80,220,0,0,0),
	(23,100,400,120,220,10,0,0),
	(24,200,300,30,80,0,0,0),
	(25,100,400,780,1380,0,0,0),
	(26,200,300,680,1380,0,0,0),
	(27,2345,12345,1234,2345,0,0,0),
	(28,2345,12345,1234,2345,0,0,0),
	(29,200,300,200,380,100,100,1),
	(41,100,400,880,680,0,0,0),
	(42,50,100,50,100,0,0,0),
	(43,100,400,80,100,0,1,0);

/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(20) NOT NULL DEFAULT '',
  `email` varchar(20) NOT NULL DEFAULT '',
  `level` int(11) NOT NULL,
  `score` int(12) NOT NULL,
  `is_delete` tinyint(1) NOT NULL,
  `account_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `username`, `password`, `email`, `level`, `score`, `is_delete`, `account_id`)
VALUES
	(1,'何慧娴','123456','735913885@qq.com',0,12200,0,1),
	(2,'黄潇','13579','hx36w35@163.com',2,4800,1,2),
	(7,'huangxiao','123','huangxiao',0,0,0,3),
	(11,'zhuzhu','123','767085586@qq.com',0,1140,0,0);

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table venue
# ------------------------------------------------------------

DROP TABLE IF EXISTS `venue`;

CREATE TABLE `venue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `venue_name` varchar(40) NOT NULL DEFAULT '',
  `venue_password` varchar(20) NOT NULL DEFAULT '',
  `venue_seat` int(11) NOT NULL,
  `venue_number` varchar(7) DEFAULT '',
  `venue_city` varchar(11) NOT NULL DEFAULT '',
  `venue_detail_position` varchar(40) NOT NULL DEFAULT '',
  `audited` tinyint(1) NOT NULL,
  `passed` tinyint(1) NOT NULL,
  `venue_email` varchar(20) DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `venue` WRITE;
/*!40000 ALTER TABLE `venue` DISABLE KEYS */;

INSERT INTO `venue` (`id`, `venue_name`, `venue_password`, `venue_seat`, `venue_number`, `venue_city`, `venue_detail_position`, `audited`, `passed`, `venue_email`, `account_id`)
VALUES
	(1,'无锡大剧院','123456',500,'0000001','无锡','大剧院路1号',1,1,'735913885@qq.com',5),
	(2,'苏州体育中心体育场','123456',500,'0000002','苏州','三香路183号',1,1,'735913885@qq.com',6),
	(3,'凯迪拉克·上海音乐厅','123456',500,'0000003','上海','延安东路523号',1,1,'735913885@qq.com',7),
	(4,'上海世博文化中心','123456',500,'0000004','上海','浦东新区世博大道1200号',1,1,'735913885@qq.com',8),
	(5,'昆山文化艺术中心-大剧场','123456',500,'0000005','苏州','前进西路1850号',1,1,'735913885@qq.com',9),
	(6,'上汽·上海文化广场','123456',500,'0000006','上海','复兴中路597号',1,1,'735913885@qq.com',10),
	(7,'上剧场(赖声川专属剧场)','123456',500,'0000007','上海','徐汇区美罗城商场5层',1,1,'735913885@qq.com',11),
	(8,'无锡市新工人文化宫','123456',500,'0000008','无锡','滨湖区观山路299号',1,1,'735913885@qq.com',12),
	(9,'苏州保利大剧院','123456',500,'0000009','苏州','吴中区东苑路1号',1,1,'735913885@qq.com',13),
	(10,'上海东方体育中心','123456',500,'0000010','上海','浦东新区泳耀路300号',1,1,'735913885@qq.com',14),
	(11,'上海国际舞蹈中心 大剧场','123456',500,'0000011','上海','长宁区虹桥路1650号',1,1,'735913885@qq.com',15),
	(12,'上海保利大剧院','123456',500,'0000012','上海','嘉定白银路159号',1,1,'735913885@qq.com',16),
	(13,'上海迪士尼度假区','123456',500,'0000013','上海','浦东新区川沙新镇唐黄路180号',1,1,'735913885@qq.com',17),
	(14,'上海玻璃博物馆','123456',500,'0000014','上海','宝山区长江西路685号',1,1,'735913885@qq.com',18),
	(15,'上海新国际博览中心','123456',500,'0000015','上海','浦东新区龙阳路2345号',1,1,'735913885@qq.com',19),
	(16,'上海展览中心','123456',500,'0000016','上海',' 静安区延安中路1000号',1,1,'735913885@qq.com',20),
	(17,'南京保利大剧院','123456',500,'0000017','南京','建邺区邺城路6号',0,0,'735913885@qq.com',21),
	(19,'艺海剧院-先锋剧场','123456',500,'0000019','上海','江宁路466号',0,0,'735913885@qq.com',22),
	(20,'兰心大戏院','123456',500,'0000020','上海','黄浦区茂名南路57号',0,0,'735913885@qq.com',23),
	(22,'12344','1234',0,NULL,'1234','1234',0,0,'1234',26),
	(24,'南京大学','123',500,'0000024','南京','鼓楼',1,1,'123',28),
	(25,'NJU','123456',500,'0000025','南京','汉口路22号',1,1,'735913885@qq.com',29);

/*!40000 ALTER TABLE `venue` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
