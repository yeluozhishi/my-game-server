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

 Date: 19/11/2024 14:49:53
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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for player_bag
-- ----------------------------
DROP TABLE IF EXISTS `player_bag`;
CREATE TABLE `player_bag`  (
  `id` bigint(20) NOT NULL,
  `bag_data` blob NULL COMMENT '背包数据',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for player_module
-- ----------------------------
DROP TABLE IF EXISTS `player_module`;
CREATE TABLE `player_module`  (
  `id` bigint(20) NOT NULL,
  `data` blob NULL COMMENT '玩家模块数据',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for player_repository
-- ----------------------------
DROP TABLE IF EXISTS `player_repository`;
CREATE TABLE `player_repository`  (
  `id` bigint(20) NOT NULL,
  `data` blob NULL COMMENT '仓库数据',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
