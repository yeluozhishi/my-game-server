/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50651
 Source Host           : localhost:3306
 Source Schema         : game-server

 Target Server Type    : MySQL
 Target Server Version : 50651
 File Encoding         : 65001

 Date: 28/06/2024 17:59:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for player
-- ----------------------------
DROP TABLE IF EXISTS `player`;
CREATE TABLE `player`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `career` int(11) NULL DEFAULT NULL COMMENT '职业',
  `sex` tinyint(4) NULL DEFAULT NULL COMMENT '性别',
  `last_login` bigint(20) NULL DEFAULT NULL COMMENT '上次登录',
  `coin` bigint(20) NULL DEFAULT NULL COMMENT '货币',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of player
-- ----------------------------
INSERT INTO `player` VALUES (1, 1, 1, 1696738433700, NULL);
INSERT INTO `player` VALUES (2, 1, 1, 1696738434172, NULL);
INSERT INTO `player` VALUES (3, 1, 1, 1696738434675, NULL);
INSERT INTO `player` VALUES (4, 1, 1, 1696738435179, NULL);
INSERT INTO `player` VALUES (5, 1, 1, 1696738435682, NULL);
INSERT INTO `player` VALUES (6, 1, 1, 1696738436183, NULL);
INSERT INTO `player` VALUES (7, 1, 1, 1696738436685, NULL);
INSERT INTO `player` VALUES (8, 1, 1, 1696738437186, NULL);
INSERT INTO `player` VALUES (9, 1, 1, 1696738504361, NULL);
INSERT INTO `player` VALUES (10, 1, 1, 1696738583750, NULL);

SET FOREIGN_KEY_CHECKS = 1;
