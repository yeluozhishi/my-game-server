/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : 127.0.0.1:3306
 Source Schema         : admin

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 11/09/2023 15:09:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for player
-- ----------------------------
DROP TABLE IF EXISTS `player`;
CREATE TABLE `player`  (
  `id` bigint NOT NULL,
  `user_account_id` bigint NULL DEFAULT NULL COMMENT '外联 user_account',
  `career` int NULL DEFAULT NULL COMMENT '职业',
  `sex` tinyint NULL DEFAULT NULL COMMENT '性别',
  `last_login` bigint NULL DEFAULT NULL COMMENT '上次登录',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of player
-- ----------------------------

-- ----------------------------
-- Table structure for server_info
-- ----------------------------
DROP TABLE IF EXISTS `server_info`;
CREATE TABLE `server_info`  (
  `id` bigint NOT NULL COMMENT '服务器id',
  `zone` int NULL DEFAULT NULL COMMENT '大区',
  `instance_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '服务器实例id',
  `server_type` int NULL DEFAULT NULL COMMENT '服务器类型',
  `server_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '服务器名',
  `open_server_time` datetime NULL DEFAULT NULL COMMENT '开服时间',
  `open_entrance_time` datetime NULL DEFAULT NULL COMMENT '开放入口时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of server_info
-- ----------------------------
INSERT INTO `server_info` VALUES (1, 1, 'game-server-2', 2, '1区', '2023-09-11 10:30:56', '2023-09-11 10:30:58');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint NULL DEFAULT 100 COMMENT '部门ID',
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
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 317 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 103, 'admin', '博承', '00', 'ry@163.com', '15888888888', '0', 'https://file.sogoun.com/tms/2023/07/07/bbf6d17a45964f6fb627d3441d1e098db', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '127.0.0.1', '2022-11-09 11:21:38', '0', 'admin', '2022-11-09 11:21:38', '', '2023-07-03 22:06:30', '0', '管理员');
INSERT INTO `sys_user` VALUES (293, 105, '1222333', '汪宏坤', '00', '', '15640075560', '0', '', '$2a$10$YTKzruU/DdCjDUW9kOda0eJRxxc4HXmj8/pzYE1K.IQq55YRym1ua', '', NULL, '0', 'admin', '2023-07-05 21:32:50', '1222333', '2023-07-14 03:10:36', '0', NULL);
INSERT INTO `sys_user` VALUES (299, 106, 'nana', '娜娜', '00', '', '', '0', '', '$2a$10$e4jGO3U5WHVDP/ZkeVcV7.LcCRhNEaGQpK5jmm7Ti6W2sQ6D2XLaa', '', NULL, '0', 'admin', '2023-07-18 21:43:13', 'admin', '2023-07-18 21:44:10', '2', NULL);
INSERT INTO `sys_user` VALUES (300, 106, 'weiwei', '维维', '00', '', '', '0', 'https://file.sogoun.com/tms/2023/07/19/348e09bc2eb342849d18555d3b07518ab', '$2a$10$XiWsR3LR0zVByx9XGthPVONoGGFNeYU64otZEo7UvBxia88wvtK8S', '', NULL, '0', 'admin', '2023-07-18 21:43:43', 'admin', '2023-07-18 21:44:21', '2', NULL);
INSERT INTO `sys_user` VALUES (303, 106, 'nana', '娜娜', '00', '', '', '0', '', '$2a$10$8u8HpxchAGK8N1.zFdAM5.dR6XkRXIaaNBlV.fyHhdtcA9f.Fzbkm', '', NULL, '0', 'admin', '2023-07-19 03:10:27', 'admin', '2023-07-19 03:13:31', '0', NULL);

-- ----------------------------
-- Table structure for user_account
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account`  (
  `user_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `passward` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '第三方id',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_account
-- ----------------------------
INSERT INTO `user_account` VALUES (1, 'whk', '123', '2023-09-11 03:17:09', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
