/*
MySQL Data Transfer
Source Host: localhost
Source Database: aihudong_user
Target Host: localhost
Target Database: aihudong_user
Date: 2018/6/13 10:29:19
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for record
-- ----------------------------
CREATE TABLE `record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `screen_id` varchar(255) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `system_id` varchar(255) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for room
-- ----------------------------
CREATE TABLE `room` (
  `id` varchar(255) NOT NULL,
  `num` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for screen
-- ----------------------------
CREATE TABLE `screen` (
  `id` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `room_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `times` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `resolution` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `truename` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `job` varchar(255) DEFAULT NULL,
  `job_type` varchar(255) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `admin_id` varchar(255) DEFAULT NULL,
  `screen_num` int(11) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `times` int(11) DEFAULT NULL,
  `remake` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `record` VALUES ('1', 'us1', null, '1', null, '2018-03-06 10:45:37', null);
INSERT INTO `record` VALUES ('2', 'us1', 'sc1', '1', null, '2018-03-06 10:56:11', null);
INSERT INTO `record` VALUES ('3', 'us1', 'sc1', '1', null, '2018-03-06 11:10:37', '2018-03-06 11:10:57');
INSERT INTO `record` VALUES ('4', 'us1', 'sc1', '1', null, '2018-03-06 11:56:38', '2018-03-06 11:56:54');
INSERT INTO `record` VALUES ('5', 'us1', 'sc1', '1', null, '2018-03-06 12:54:35', null);
INSERT INTO `record` VALUES ('6', 'us8', null, '1', null, '2018-03-13 14:20:54', null);
INSERT INTO `record` VALUES ('7', 'us8', null, '1', null, '2018-03-13 14:21:27', null);
INSERT INTO `record` VALUES ('8', 'us8', null, '1', null, '2018-03-13 14:22:18', null);
INSERT INTO `record` VALUES ('9', 'us8', null, '1', null, '2018-03-13 14:23:33', null);
INSERT INTO `record` VALUES ('10', 'us8', null, '1', null, '2018-03-13 14:37:02', null);
INSERT INTO `record` VALUES ('11', 'sc4', null, '4', null, '2018-03-13 15:06:19', null);
INSERT INTO `record` VALUES ('12', 'us3', 'sc4', '1', null, '2018-03-13 15:07:48', null);
INSERT INTO `record` VALUES ('13', 'us3', 'sc4', '1', null, '2018-03-13 15:10:06', '2018-03-13 15:13:40');
INSERT INTO `record` VALUES ('14', 'sc4', null, '4', null, '2018-03-13 15:11:26', '2018-03-13 15:14:12');
INSERT INTO `record` VALUES ('15', 'us8', 'sc1', '1', null, '2018-03-13 15:13:57', '2018-03-13 15:15:15');
INSERT INTO `record` VALUES ('16', 'sc1', null, '4', null, '2018-03-13 15:14:19', '2018-03-13 15:14:40');
INSERT INTO `record` VALUES ('17', 'sc5', null, '4', null, '2018-03-13 15:14:46', '2018-03-13 15:15:56');
INSERT INTO `record` VALUES ('18', 'us8', 'sc5', '1', null, '2018-03-13 15:15:25', '2018-03-13 15:17:47');
INSERT INTO `record` VALUES ('19', 'sc5', null, '4', null, '2018-03-13 15:17:00', null);
INSERT INTO `record` VALUES ('20', 'sc5', null, '4', null, '2018-03-13 15:18:05', null);
INSERT INTO `record` VALUES ('21', 'sc5', null, '4', null, '2018-03-13 15:18:12', null);
INSERT INTO `record` VALUES ('22', 'sc5', null, '4', null, '2018-03-13 15:18:13', null);
INSERT INTO `record` VALUES ('23', 'sc5', null, '4', null, '2018-03-13 15:18:49', null);
INSERT INTO `record` VALUES ('24', 'sc5', null, '4', null, '2018-03-13 15:19:35', null);
INSERT INTO `record` VALUES ('25', 'sc5', null, '4', null, '2018-03-13 15:21:06', null);
INSERT INTO `record` VALUES ('26', 'sc5', null, '4', null, '2018-03-13 15:22:43', null);
INSERT INTO `record` VALUES ('27', 'sc5', null, '4', null, '2018-03-13 15:23:34', null);
INSERT INTO `record` VALUES ('28', 'sc5', null, '4', null, '2018-03-13 15:23:44', null);
INSERT INTO `record` VALUES ('29', 'sc5', null, '4', null, '2018-03-13 15:23:45', null);
INSERT INTO `record` VALUES ('30', 'sc5', null, '4', null, '2018-03-13 15:23:46', null);
INSERT INTO `record` VALUES ('31', 'sc5', null, '4', null, '2018-03-13 15:23:47', null);
INSERT INTO `record` VALUES ('32', 'sc5', null, '4', null, '2018-03-13 15:23:48', null);
INSERT INTO `record` VALUES ('33', 'sc5', null, '4', null, '2018-03-13 15:23:49', null);
INSERT INTO `room` VALUES ('1002218', '房间4', null, null);
INSERT INTO `room` VALUES ('1002219', '房间5', null, null);
INSERT INTO `room` VALUES ('1002848', 'dell市场部', null, null);
INSERT INTO `room` VALUES ('1021142', '房间1', null, null);
INSERT INTO `room` VALUES ('878971', '房间2', null, null);
INSERT INTO `room` VALUES ('879302', '房间3', null, null);
INSERT INTO `room` VALUES ('899926', '房间1', null, null);
INSERT INTO `screen` VALUES ('sc1', '00000001', '123', '899926', 'us1', '0:0:20', '1', '1', null);
INSERT INTO `screen` VALUES ('sc10', '00000010', '123', '879302', 'us6', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc11', '00000011', '123', '879302', 'us6', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc12', '00000012', '123', '878971', 'us2', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc13', '00000013', '123', '878971', 'us2', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc14', '00000014', '123', '878971', 'us2', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc15', '00000015', '123', '878971', 'us2', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc16', '00000016', '123', '1002218', 'us4', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc17', '00000017', '123', '1002218', 'us4', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc18', '00000018', '123', '1002218', 'us4', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc19', '00000019', '123', '1002218', 'us4', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc2', '00000002', '123', '899926', 'us1', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc20', '00000020', '123', '1002218', 'us4', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc21', '00000021', '123', '1002218', 'us4', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc22', '00000022', '123', '1002218', 'us4', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc23', '00000023', '123', '1021142', 'us11', '00:00:00', '0', null, null);
INSERT INTO `screen` VALUES ('sc24', '00000024', '123', '1021142', 'us11', '00:00:00', '0', null, null);
INSERT INTO `screen` VALUES ('sc3', '00000003', '123', '899926', 'us1', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc30', '00000030', '123', '1002219', 'us3', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc31', '00000031', '123', '1002848', 'us8', '00:00:00', '0', null, null);
INSERT INTO `screen` VALUES ('sc32', '00000032', '123', '1002848', 'us8', '00:00:00', '0', null, null);
INSERT INTO `screen` VALUES ('sc4', '00000004', '123', '899926', 'us1', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc5', '00000005', '123', '899926', 'us1', '0:1:10', '1', '1', null);
INSERT INTO `screen` VALUES ('sc6', '00000006', '123', '899926', 'us1', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc7', '00000007', '123', '879302', 'us6', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc8', '00000008', '123', '879302', 'us6', '00:00:00', '0', '1', null);
INSERT INTO `screen` VALUES ('sc9', '00000009', '123', '879302', 'us6', '00:00:00', '0', '1', null);
INSERT INTO `user` VALUES ('us1', 'jack', '123', '杰克', '1', '15511110000', '123@163.com', '软件开发', 'IT', '1', null, '100', '0:0:35', '2', null);
INSERT INTO `user` VALUES ('us10', 'wangnao', 'asj123', '王闹', '0', '110', '', '爱视界', '教育', '1', null, '2', '00:00:00', '0', null);
INSERT INTO `user` VALUES ('us11', 'konka2', 'kj966111', null, null, '12334', null, '11111', '金融', '1', null, '2', '00:00:00', '0', null);
INSERT INTO `user` VALUES ('us12', 'konka21', '123', 'konka21', '0', '12334', null, null, null, '2', 'us11', '0', '00:00:00', '0', null);
INSERT INTO `user` VALUES ('us13', 'konka22', '123', 'konka22', '0', '12334', null, null, null, '2', 'us11', '0', '00:00:00', '0', null);
INSERT INTO `user` VALUES ('us14', 'konka23', '123', 'konka23', '0', '12334', null, null, null, '2', 'us11', '0', '00:00:00', '0', null);
INSERT INTO `user` VALUES ('us15', 'konka24', '123', 'konka24', '0', '12334', null, null, null, '2', 'us11', '0', '00:00:00', '0', null);
INSERT INTO `user` VALUES ('us16', 'konka25', '123', 'konka25', '0', '12334', null, null, null, '2', 'us11', '0', '00:00:00', '0', null);
INSERT INTO `user` VALUES ('us17', 'konka29', '123', null, null, null, null, null, null, '2', null, null, '00:00:00', '0', null);
INSERT INTO `user` VALUES ('us18', 'konka26', '123', null, null, null, null, null, null, '2', null, '0', '00:00:00', '0', null);
INSERT INTO `user` VALUES ('us19', 'konka', '123', 'konka', '0', null, null, null, null, '0', null, '0', null, null, null);
INSERT INTO `user` VALUES ('us2', 'zyl', '12231101', null, null, '15209713366', null, '爱视界', '教育', '1', null, '2', '0:3:38', '2', null);
INSERT INTO `user` VALUES ('us3', 'genji', '123', '根级', '1', '111', 'lll', '1', null, '1', null, '9', '0:3:33', '1', null);
INSERT INTO `user` VALUES ('us4', 'bbc', '123', 'BBC', '0', '157', '157@163.COM', '1', null, '2', null, '0', '00:00:00', '0', null);
INSERT INTO `user` VALUES ('us5', 'hh', '123a', null, null, '123', null, null, null, '1', null, '9', '00:00:00', '0', null);
INSERT INTO `user` VALUES ('us6', 'laser', '111111a', null, null, '13701241641', null, null, null, '1', null, '9', '00:00:00', '0', null);
INSERT INTO `user` VALUES ('us7', '1111', 'a111111', null, null, '1111', null, null, null, '1', null, '9', '00:00:00', '0', null);
INSERT INTO `user` VALUES ('us8', 'hegang', '123', '何刚', '0', '17001160998', 'legou_he@dell.com', 'dell销售', '1', '1', null, '9', '00:00:00', '0', null);
INSERT INTO `user` VALUES ('us9', 'zyltest123', 'zyltest1223', null, null, '12231101', null, '', '教育', '1', null, '2', '00:00:00', '0', null);
