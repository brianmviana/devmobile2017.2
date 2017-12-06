/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : qdetective

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-12-06 01:39:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for denuncia
-- ----------------------------
DROP TABLE IF EXISTS `denuncia`;
CREATE TABLE `denuncia` (
  `id` int(11) NOT NULL,
  `categoria` int(11) DEFAULT NULL,
  `data` datetime DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `uriMidia` varchar(255) DEFAULT NULL,
  `usuario` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of denuncia
-- ----------------------------
INSERT INTO `denuncia` VALUES ('1', '1', '2017-12-05 20:29:55', 'Rodovia BR-122 entre os municípios de Quixadá com Banabuiú está em péssimas condições', '-5.173719', '-38.9164127', '1.jpg', 'hanibalce');
INSERT INTO `denuncia` VALUES ('2', '1', '2017-12-05 21:36:57', 'Rodovia BR-122 entre Quixadá e Ibaretama ', '-4.838369', '-38.8435417', '2.mp4', 'hanibalce');
