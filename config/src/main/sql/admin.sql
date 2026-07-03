/*
 Navicat Premium Data Transfer

 Source Server         : mysql8
 Source Server Type    : MySQL
 Source Server Version : 80408
 Source Host           : localhost:3316
 Source Schema         : admin

 Target Server Type    : MySQL
 Target Server Version : 80408
 File Encoding         : 65001

 Date: 17/04/2026 17:52:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for player_info
-- ----------------------------
DROP TABLE IF EXISTS `player_info`;
CREATE TABLE `player_info`  (
  `id` bigint UNSIGNED NOT NULL,
  `user_id` bigint NULL DEFAULT NULL COMMENT '外联 user_account',
  `career` int NULL DEFAULT NULL COMMENT '职业',
  `sex` smallint NULL DEFAULT NULL COMMENT '性别',
  `last_login` bigint NULL DEFAULT NULL COMMENT '上次登录',
  `server_id` bigint NULL DEFAULT NULL COMMENT '服务器id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of player_info
-- ----------------------------
INSERT INTO `player_info` VALUES (18015775396390913, 2, 1, 1, 1776403550417, 1);
INSERT INTO `player_info` VALUES (18015775418484737, 9, 1, 1, 1776414337794, 1);
INSERT INTO `player_info` VALUES (18015775418484738, 5, 1, 1, 1776414338525, 1);
INSERT INTO `player_info` VALUES (18015775418486784, 6, 1, 1, 1776414339068, 1);
INSERT INTO `player_info` VALUES (18015775418488832, 7, 1, 1, 1776414339500, 1);
INSERT INTO `player_info` VALUES (18015775418488833, 1, 1, 1, 1776414340047, 1);
INSERT INTO `player_info` VALUES (18015775418490880, 10, 1, 1, 1776414340564, 1);
INSERT INTO `player_info` VALUES (18015775418490881, 8, 1, 1, 1776414341234, 1);
INSERT INTO `player_info` VALUES (18015775418492928, 3, 1, 1, 1776414341765, 1);
INSERT INTO `player_info` VALUES (18015775418492929, 2, 1, 1, 1776414342314, 1);
INSERT INTO `player_info` VALUES (18015775418494976, 4, 1, 1, 1776414342745, 1);

-- ----------------------------
-- Table structure for server_info
-- ----------------------------
DROP TABLE IF EXISTS `server_info`;
CREATE TABLE `server_info`  (
  `server_id` int NOT NULL COMMENT '服务器id',
  `server_zone` int NULL DEFAULT NULL COMMENT '大区',
  `server_type` int NULL DEFAULT NULL COMMENT '服务器类型1:网关 2:数据服 3:场景服',
  `server_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器名',
  `open_server_time` datetime NULL DEFAULT NULL COMMENT '开服时间',
  `open_entrance_time` datetime NULL DEFAULT NULL COMMENT '开放入口时间',
  `open` bit(1) NULL DEFAULT NULL COMMENT '开启状态',
  PRIMARY KEY (`server_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of server_info
-- ----------------------------
INSERT INTO `server_info` VALUES (1, 1, 2, '1服', '2023-09-11 10:30:56', '2023-09-11 10:30:58', b'1');
INSERT INTO `server_info` VALUES (3, 1, 3, '场景1服', '2024-12-04 10:51:23', '2024-12-04 10:51:27', b'1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint UNSIGNED NOT NULL COMMENT '用户ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (293, '1222333', '汪宏坤', '00', '', '15640075560', '0', '', '$2a$10$YTKzruU/DdCjDUW9kOda0eJRxxc4HXmj8/pzYE1K.IQq55YRym1ua', '', NULL, '0', 'admin', '2023-07-05 21:32:50', '1222333', '2023-07-14 03:10:36', '0', NULL);

-- ----------------------------
-- Table structure for user_account
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account`  (
  `id` bigint UNSIGNED NOT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_account
-- ----------------------------
INSERT INTO `user_account` VALUES (1, 'whk', '123', '2023-09-11 03:17:09', NULL, NULL);
INSERT INTO `user_account` VALUES (2, 'whk1', '123', '2023-09-11 03:17:09', NULL, NULL);
INSERT INTO `user_account` VALUES (3, 'whk2', '123', '2023-09-11 03:17:09', NULL, NULL);
INSERT INTO `user_account` VALUES (4, 'whk3', '123', '2023-09-11 03:17:09', NULL, NULL);
INSERT INTO `user_account` VALUES (5, 'whk4', '123', '2023-09-11 03:17:09', NULL, NULL);
INSERT INTO `user_account` VALUES (6, 'whk5', '123', '2023-09-11 03:17:09', NULL, NULL);
INSERT INTO `user_account` VALUES (7, 'whk6', '123', '2023-09-11 03:17:09', NULL, NULL);
INSERT INTO `user_account` VALUES (8, 'whk7', '123', '2023-09-11 03:17:09', NULL, NULL);
INSERT INTO `user_account` VALUES (9, 'whk8', '123', '2023-09-11 03:17:09', NULL, NULL);
INSERT INTO `user_account` VALUES (10, 'whk9', '123', '2023-09-11 03:17:09', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
