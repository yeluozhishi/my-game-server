/*
 Navicat Premium Data Transfer

 Source Server         : mysql8
 Source Server Type    : MySQL
 Source Server Version : 80408
 Source Host           : localhost:3316
 Source Schema         : game-scene

 Target Server Type    : MySQL
 Target Server Version : 80408
 File Encoding         : 65001

 Date: 17/04/2026 17:52:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` int NOT NULL COMMENT '活动id',
  `activity_data` blob NULL COMMENT '活动数据',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
