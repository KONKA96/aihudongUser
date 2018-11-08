/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : aihudong_user

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 08/11/2018 18:04:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `screen_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` int(11) NULL DEFAULT NULL,
  `system_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES (1, 'us1', NULL, 1, NULL, '2018-03-06 10:45:37', NULL);
INSERT INTO `record` VALUES (2, 'us1', 'sc1', 1, NULL, '2018-03-06 10:56:11', NULL);
INSERT INTO `record` VALUES (3, 'us1', 'sc1', 1, NULL, '2018-03-06 11:10:37', '2018-03-06 11:10:57');
INSERT INTO `record` VALUES (4, 'us1', 'sc1', 1, NULL, '2018-03-06 11:56:38', '2018-03-06 11:56:54');
INSERT INTO `record` VALUES (5, 'us1', 'sc1', 1, NULL, '2018-03-06 12:54:35', NULL);
INSERT INTO `record` VALUES (6, 'us8', NULL, 1, NULL, '2018-03-13 14:20:54', NULL);
INSERT INTO `record` VALUES (7, 'us8', NULL, 1, NULL, '2018-03-13 14:21:27', NULL);
INSERT INTO `record` VALUES (8, 'us8', NULL, 1, NULL, '2018-03-13 14:22:18', NULL);
INSERT INTO `record` VALUES (9, 'us8', NULL, 1, NULL, '2018-03-13 14:23:33', NULL);
INSERT INTO `record` VALUES (10, 'us8', NULL, 1, NULL, '2018-03-13 14:37:02', NULL);
INSERT INTO `record` VALUES (11, 'sc4', NULL, 4, NULL, '2018-03-13 15:06:19', NULL);
INSERT INTO `record` VALUES (12, 'us3', 'sc4', 1, NULL, '2018-03-13 15:07:48', NULL);
INSERT INTO `record` VALUES (13, 'us3', 'sc4', 1, NULL, '2018-03-13 15:10:06', '2018-03-13 15:13:40');
INSERT INTO `record` VALUES (14, 'sc4', NULL, 4, NULL, '2018-03-13 15:11:26', '2018-03-13 15:14:12');
INSERT INTO `record` VALUES (15, 'us8', 'sc1', 1, NULL, '2018-03-13 15:13:57', '2018-03-13 15:15:15');
INSERT INTO `record` VALUES (16, 'sc1', NULL, 4, NULL, '2018-03-13 15:14:19', '2018-03-13 15:14:40');
INSERT INTO `record` VALUES (17, 'sc5', NULL, 4, NULL, '2018-03-13 15:14:46', '2018-03-13 15:15:56');
INSERT INTO `record` VALUES (18, 'us8', 'sc5', 1, NULL, '2018-03-13 15:15:25', '2018-03-13 15:17:47');
INSERT INTO `record` VALUES (19, 'sc5', NULL, 4, NULL, '2018-03-13 15:17:00', NULL);
INSERT INTO `record` VALUES (20, 'sc5', NULL, 4, NULL, '2018-03-13 15:18:05', NULL);
INSERT INTO `record` VALUES (21, 'sc5', NULL, 4, NULL, '2018-03-13 15:18:12', NULL);
INSERT INTO `record` VALUES (22, 'sc5', NULL, 4, NULL, '2018-03-13 15:18:13', NULL);
INSERT INTO `record` VALUES (23, 'sc5', NULL, 4, NULL, '2018-03-13 15:18:49', NULL);
INSERT INTO `record` VALUES (24, 'sc5', NULL, 4, NULL, '2018-03-13 15:19:35', NULL);
INSERT INTO `record` VALUES (25, 'sc5', NULL, 4, NULL, '2018-03-13 15:21:06', NULL);
INSERT INTO `record` VALUES (26, 'sc5', NULL, 4, NULL, '2018-03-13 15:22:43', NULL);
INSERT INTO `record` VALUES (27, 'sc5', NULL, 4, NULL, '2018-03-13 15:23:34', NULL);
INSERT INTO `record` VALUES (28, 'sc5', NULL, 4, NULL, '2018-03-13 15:23:44', NULL);
INSERT INTO `record` VALUES (29, 'sc5', NULL, 4, NULL, '2018-03-13 15:23:45', NULL);
INSERT INTO `record` VALUES (30, 'sc5', NULL, 4, NULL, '2018-03-13 15:23:46', NULL);
INSERT INTO `record` VALUES (31, 'sc5', NULL, 4, NULL, '2018-03-13 15:23:47', NULL);
INSERT INTO `record` VALUES (32, 'sc5', NULL, 4, NULL, '2018-03-13 15:23:48', NULL);
INSERT INTO `record` VALUES (33, 'sc5', NULL, 4, NULL, '2018-03-13 15:23:49', NULL);
INSERT INTO `record` VALUES (34, 'sc1', NULL, 4, NULL, '2018-09-13 15:11:51', NULL);
INSERT INTO `record` VALUES (35, 'sc1', NULL, 4, NULL, '2018-09-13 15:13:36', NULL);
INSERT INTO `record` VALUES (36, 'us1', NULL, 1, NULL, '2018-09-13 15:14:37', NULL);
INSERT INTO `record` VALUES (37, 'us1', NULL, 1, NULL, '2018-09-13 15:16:05', NULL);
INSERT INTO `record` VALUES (38, 'us13', NULL, 2, NULL, '2018-09-13 15:16:20', NULL);
INSERT INTO `record` VALUES (39, 'us11', 'sc22', 1, NULL, '2018-09-13 15:38:33', NULL);
INSERT INTO `record` VALUES (40, 'us11', NULL, 1, NULL, '2018-09-29 16:35:05', NULL);
INSERT INTO `record` VALUES (41, 'us11', NULL, 1, NULL, '2018-09-29 16:36:25', NULL);
INSERT INTO `record` VALUES (42, 'us11', NULL, 1, NULL, '2018-09-29 16:37:22', '2018-09-29 16:37:32');
INSERT INTO `record` VALUES (43, 'us11', 'sc22', 1, NULL, '2018-09-29 16:38:13', '2018-09-29 16:40:27');
INSERT INTO `record` VALUES (44, 'us13', 'sc22', 2, NULL, '2018-09-29 20:21:05', NULL);
INSERT INTO `record` VALUES (45, 'us13', 'sc24', 2, NULL, '2018-09-29 20:40:19', NULL);
INSERT INTO `record` VALUES (46, 'us13', 'sc22', 2, NULL, '2018-09-29 20:42:04', NULL);
INSERT INTO `record` VALUES (47, 'sc1', NULL, 4, NULL, '2018-10-08 14:20:24', NULL);
INSERT INTO `record` VALUES (48, 'sc1', NULL, 4, NULL, '2018-10-08 14:24:11', NULL);
INSERT INTO `record` VALUES (49, 'sc1', NULL, 4, NULL, '2018-10-08 14:24:23', NULL);
INSERT INTO `record` VALUES (50, 'sc1', NULL, 4, NULL, '2018-10-08 14:26:12', NULL);
INSERT INTO `record` VALUES (51, 'sc1', NULL, 4, NULL, '2018-10-08 14:26:35', NULL);
INSERT INTO `record` VALUES (52, 'sc1', NULL, 4, NULL, '2018-10-08 14:27:16', NULL);
INSERT INTO `record` VALUES (53, 'sc1', NULL, 4, NULL, '2018-10-08 14:29:51', NULL);
INSERT INTO `record` VALUES (54, 'us11', NULL, 1, NULL, '2018-10-08 14:37:41', NULL);
INSERT INTO `record` VALUES (55, 'sc1', NULL, 4, NULL, '2018-10-08 14:43:18', NULL);
INSERT INTO `record` VALUES (56, 'sc1', NULL, 4, NULL, '2018-10-08 14:52:54', NULL);
INSERT INTO `record` VALUES (57, 'us11', NULL, 1, NULL, '2018-10-19 10:03:17', NULL);
INSERT INTO `record` VALUES (58, 'us11', NULL, 1, NULL, '2018-10-19 10:03:20', NULL);

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `position` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES ('1002218', '房间4', NULL, NULL, NULL, NULL);
INSERT INTO `room` VALUES ('1002219', '房间5', NULL, NULL, NULL, NULL);
INSERT INTO `room` VALUES ('1002848', 'dell市场部', NULL, NULL, NULL, NULL);
INSERT INTO `room` VALUES ('1021142', '房间1', NULL, NULL, NULL, NULL);
INSERT INTO `room` VALUES ('1022273', '房间asd', NULL, NULL, NULL, NULL);
INSERT INTO `room` VALUES ('1023018', '房间1', NULL, NULL, NULL, NULL);
INSERT INTO `room` VALUES ('1023021', '房间1', NULL, NULL, NULL, NULL);
INSERT INTO `room` VALUES ('2d527c1e-5be8-4799-a96f-a3b7c27c4397', '房间1', NULL, NULL, NULL, NULL);
INSERT INTO `room` VALUES ('5490072b-6256-4b05-8dbc-bed5cb825fb3', '房间1', NULL, NULL, NULL, NULL);
INSERT INTO `room` VALUES ('754533', 'konka2\'s virtual room', '123', NULL, NULL, 'us11');
INSERT INTO `room` VALUES ('871297', 'us2\'s virtual room', NULL, NULL, NULL, 'us2');
INSERT INTO `room` VALUES ('878971', '房间2', NULL, NULL, NULL, NULL);
INSERT INTO `room` VALUES ('879302', '房间3', NULL, NULL, NULL, NULL);
INSERT INTO `room` VALUES ('899926', '房间1', NULL, NULL, NULL, NULL);
INSERT INTO `room` VALUES ('ac7787a5-3720-4e3f-87bd-062a7299a316', '房间1', NULL, NULL, NULL, NULL);
INSERT INTO `room` VALUES ('e4fe6528-ace4-44e9-b96e-c7e10351827a', '房间1', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for screen
-- ----------------------------
DROP TABLE IF EXISTS `screen`;
CREATE TABLE `screen`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `room_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `duration` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `times` int(11) NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `resolution` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of screen
-- ----------------------------
INSERT INTO `screen` VALUES ('sc1', '00000001', '123', '*****', '899926', 'us1', '0:0:20', 1, '1', NULL);
INSERT INTO `screen` VALUES ('sc10', '00000010', '123', NULL, '879302', 'us6', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc11', '00000011', '123', NULL, '879302', 'us6', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc12', '00000012', '123', NULL, '878971', 'us2', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc13', '00000013', '123', NULL, '878971', 'us2', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc14', '00000014', '123', NULL, '878971', 'us2', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc15', '00000015', '123', NULL, '878971', 'us2', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc16', '00000016', '123', NULL, '1002218', 'us4', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc17', '00000017', '123', NULL, '1002218', 'us4', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc18', '00000018', '123', NULL, '1002218', 'us4', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc19', '00000019', '123', NULL, '1002218', 'us4', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc2', '00000002', '123', NULL, '899926', 'us1', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc20', '00000020', '123', NULL, '1002218', 'us4', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc21', '00000021', '123', NULL, '1002218', 'us4', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc22', '00000022', '123', NULL, '1023018', 'us11', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc23', '00000023', '123', NULL, '1023018', 'us11', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc24', '00000024', '123', NULL, '1023018', 'us11', '00:00:00', 0, '3', NULL);
INSERT INTO `screen` VALUES ('sc25', '00000025', '123', NULL, '1022273', 'us12', '00:00:00', 0, NULL, NULL);
INSERT INTO `screen` VALUES ('sc26', '00000026', '123', NULL, '1022273', 'us12', '00:00:00', 0, NULL, NULL);
INSERT INTO `screen` VALUES ('sc27', '00000027', '123', NULL, '1023018', 'us11', '00:00:00', 0, '5', NULL);
INSERT INTO `screen` VALUES ('sc28', '00000028', '123', NULL, '1023018', 'us11', '00:00:00', 0, '2', NULL);
INSERT INTO `screen` VALUES ('sc29', '00000029', '123', NULL, '1023021', 'us5', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc3', '00000003', '123', NULL, '899926', 'us1', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc30', '00000030', '123', NULL, '1002219', 'us3', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc31', '00000031', '123', NULL, '1002848', 'us8', '00:00:00', 0, NULL, NULL);
INSERT INTO `screen` VALUES ('sc32', '00000032', '123', NULL, '1002848', 'us8', '00:00:00', 0, NULL, NULL);
INSERT INTO `screen` VALUES ('sc33', '00000033', '123', NULL, '1023021', 'us5', '00:00:00', 0, '3', NULL);
INSERT INTO `screen` VALUES ('sc34', '00000034', '123', NULL, 'e4fe6528-ace4-44e9-b96e-c7e10351827a', 'us23', '00:00:00', 0, NULL, NULL);
INSERT INTO `screen` VALUES ('sc35', '00000035', '123', NULL, 'e4fe6528-ace4-44e9-b96e-c7e10351827a', 'us23', '00:00:00', 0, NULL, NULL);
INSERT INTO `screen` VALUES ('sc4', '00000004', '123', NULL, '899926', 'us1', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc5', '00000005', '123', NULL, '899926', 'us1', '0:1:10', 1, '1', NULL);
INSERT INTO `screen` VALUES ('sc6', '00000006', '123', NULL, '899926', 'us1', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc7', '00000007', '123', NULL, '879302', 'us6', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc8', '00000008', '123', NULL, '879302', 'us6', '00:00:00', 0, '1', NULL);
INSERT INTO `screen` VALUES ('sc9', '00000009', '123', NULL, '879302', 'us6', '00:00:00', 0, '1', NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `truename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` int(11) NULL DEFAULT NULL,
  `telephone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` int(11) NULL DEFAULT NULL,
  `admin_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `screen_num` int(11) NULL DEFAULT NULL,
  `duration` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `times` int(11) NULL DEFAULT NULL,
  `remake` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('us1', 'jack', '123', '杰克', 1, '15511110000', '123@163.com', '软件开发', 'IT', 1, NULL, 100, '0:0:35', 2, NULL);
INSERT INTO `user` VALUES ('us10', 'wangnao', 'asj123', '王闹', 0, '110', '', '爱视界', '教育', 1, NULL, 2, '00:00:00', 0, NULL);
INSERT INTO `user` VALUES ('us11', 'konka2', '123', '康佳', NULL, '12334', NULL, '11111', '金融', 1, NULL, 20, '0:2:23', 2, NULL);
INSERT INTO `user` VALUES ('us12', 'konka3', '123', NULL, NULL, '111112123', NULL, '111', '金融', 1, NULL, 2, '00:00:00', 0, NULL);
INSERT INTO `user` VALUES ('us13', 'konka22', '123', 'konka22', 0, '12334', NULL, NULL, NULL, 2, 'us11', 0, '00:00:00', 0, NULL);
INSERT INTO `user` VALUES ('us14', 'konka23', '123', 'konka23', 0, '12334', NULL, NULL, NULL, 2, 'us11', 0, '00:00:00', 0, NULL);
INSERT INTO `user` VALUES ('us15', 'konka27', '123', 'konka27', 0, '12334', NULL, NULL, NULL, 2, 'us11', 0, '00:00:00', 0, NULL);
INSERT INTO `user` VALUES ('us16', 'konka25', '123', 'konka25', 0, '12334', NULL, NULL, NULL, 2, 'us11', 0, '00:00:00', 0, NULL);
INSERT INTO `user` VALUES ('us17', 'konka29', '123', NULL, NULL, NULL, NULL, NULL, NULL, 2, NULL, NULL, '00:00:00', 0, NULL);
INSERT INTO `user` VALUES ('us18', 'konka26', '123', NULL, NULL, NULL, NULL, NULL, NULL, 2, NULL, 0, '00:00:00', 0, NULL);
INSERT INTO `user` VALUES ('us19', 'konka', '123', 'konka', 0, NULL, NULL, NULL, NULL, 0, NULL, 0, NULL, NULL, NULL);
INSERT INTO `user` VALUES ('us2', 'zyl', '123', NULL, NULL, '15209713366', NULL, '爱视界', '教育', 1, NULL, 2, '0:3:38', 2, NULL);
INSERT INTO `user` VALUES ('us20', 'konka28', '123', 'konka28', 0, '12334', NULL, NULL, NULL, 2, 'us11', 0, '00:00:00', 0, NULL);
INSERT INTO `user` VALUES ('us21', 'konka210', '123', 'konka210', 0, '12334', NULL, NULL, NULL, 2, 'us11', 0, '00:00:00', 0, NULL);
INSERT INTO `user` VALUES ('us22', 'konka211', '123', 'konka211', 0, '12334', NULL, NULL, NULL, 2, 'us11', 0, '00:00:00', 0, NULL);
INSERT INTO `user` VALUES ('us23', 'kkk', 'kkk123', '康佳', NULL, 'kkk', NULL, '1111', '金融', 1, NULL, 2, '00:00:00', 0, NULL);
INSERT INTO `user` VALUES ('us3', 'genji', '123', '根级', 1, '111', 'lll', '1', NULL, 1, NULL, 9, '0:3:33', 1, NULL);
INSERT INTO `user` VALUES ('us4', 'bbc', '123', 'BBC', 0, '157', '157@163.COM', '1', NULL, 2, NULL, 0, '00:00:00', 0, NULL);
INSERT INTO `user` VALUES ('us5', 'konka5', 'kj966111', NULL, NULL, '552111', NULL, '112233', '金融', 1, NULL, 2, '00:00:00', 0, NULL);
INSERT INTO `user` VALUES ('us6', 'laser', '111111a', NULL, NULL, '13701241641', NULL, NULL, NULL, 1, NULL, 9, '00:00:00', 0, NULL);
INSERT INTO `user` VALUES ('us7', '1111', 'a111111', NULL, NULL, '1111', NULL, NULL, NULL, 1, NULL, 9, '00:00:00', 0, NULL);
INSERT INTO `user` VALUES ('us8', 'hegang', '123', '何刚', 0, '17001160998', 'legou_he@dell.com', 'dell销售', '1', 1, NULL, 9, '00:00:00', 0, NULL);
INSERT INTO `user` VALUES ('us9', 'zyltest123', 'zyltest1223', 'zyl', 0, '12231101', '135@163', '', '教育', 1, NULL, 10, '00:00:00', 0, NULL);

-- ----------------------------
-- Table structure for virtual_room_record
-- ----------------------------
DROP TABLE IF EXISTS `virtual_room_record`;
CREATE TABLE `virtual_room_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `room_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` int(255) NULL DEFAULT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of virtual_room_record
-- ----------------------------
INSERT INTO `virtual_room_record` VALUES (6, 'us23', '754533', 1, '2018-10-16 13:23:39', '2018-10-16 14:23:44');
INSERT INTO `virtual_room_record` VALUES (8, 'us23', '871297', 1, '2018-10-16 14:30:07', '2018-10-16 14:30:21');
INSERT INTO `virtual_room_record` VALUES (9, 'us11', '754533', 1, '2018-10-19 14:02:56', NULL);
INSERT INTO `virtual_room_record` VALUES (10, 'us11', '754533', 1, '2018-10-24 09:51:44', NULL);
INSERT INTO `virtual_room_record` VALUES (11, 'us23', '754533', 1, '2018-10-24 09:52:03', NULL);
INSERT INTO `virtual_room_record` VALUES (12, 'us11', '754533', 1, '2018-10-25 09:51:12', NULL);

SET FOREIGN_KEY_CHECKS = 1;
