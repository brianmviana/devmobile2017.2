/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : agenda

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-12-04 19:22:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for contato
-- ----------------------------
DROP TABLE IF EXISTS `contato`;
CREATE TABLE `contato` (
  `id` bigint(20) NOT NULL,
  `celular` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `data_aniversario` datetime DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `nome` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `uriFoto` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of contato
-- ----------------------------
INSERT INTO `contato` VALUES ('991', '8599999991', '1987-05-26 01:43:50', 'melody.meunier@example.com', 'Melody Meunier', '32.jpg');
INSERT INTO `contato` VALUES ('992', '8599999992', '1993-11-06 06:05:08', 'andy.richardson@example.com', 'Andyric Hardson', '39.jpg');
INSERT INTO `contato` VALUES ('993', '8599999993', '1993-07-06 18:43:19', 'patrick.hayes@example.com', 'Patrick Hayes', '85.jpg');
INSERT INTO `contato` VALUES ('994', '8899999994', '1970-11-12 12:14:16', 'christina.gutierrez@example.com', 'Christina Gutierrez', '29.jpg');
INSERT INTO `contato` VALUES ('995', '8899999995', '1980-11-14 02:33:33', 'addison.rivera@example.com', 'Addison Rivera', '42.jpg');
