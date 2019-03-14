/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 80013
Source Host           : 127.0.0.1:3306
Source Database       : note_master

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2019-03-14 14:58:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT '',
  `password` varchar(32) DEFAULT '',
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'jixiaomo', '123456', '23');
INSERT INTO `t_user` VALUES ('2', 'hahaha', '1234', '12');
INSERT INTO `t_user` VALUES ('3', '宋会会', '1234', '12');
INSERT INTO `t_user` VALUES ('4', 'aaaaaa', '1234', '12');
