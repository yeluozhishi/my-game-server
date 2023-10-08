/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : 127.0.0.1:3306
 Source Schema         : game-server

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 08/10/2023 14:53:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for player
-- ----------------------------
DROP TABLE IF EXISTS `player`;
CREATE TABLE `player`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_account_id` bigint NULL DEFAULT NULL COMMENT '外联 user_account',
  `career` int NULL DEFAULT NULL COMMENT '职业',
  `sex` tinyint NULL DEFAULT NULL COMMENT '性别',
  `last_login` bigint NULL DEFAULT NULL COMMENT '上次登录',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of player
-- ----------------------------
INSERT INTO `player` VALUES (1, 1, 1, 1, 1696738433700);
INSERT INTO `player` VALUES (2, 1, 1, 1, 1696738434172);
INSERT INTO `player` VALUES (3, 1, 1, 1, 1696738434675);
INSERT INTO `player` VALUES (4, 1, 1, 1, 1696738435179);
INSERT INTO `player` VALUES (5, 1, 1, 1, 1696738435682);
INSERT INTO `player` VALUES (6, 1, 1, 1, 1696738436183);
INSERT INTO `player` VALUES (7, 1, 1, 1, 1696738436685);
INSERT INTO `player` VALUES (8, 1, 1, 1, 1696738437186);
INSERT INTO `player` VALUES (9, 1, 1, 1, 1696738504361);
INSERT INTO `player` VALUES (10, 1, 1, 1, 1696738583750);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', '0', 'admin', '2022-11-09 11:21:38', '', NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, '5', 1, 1, '0', '0', 'admin', '2022-11-09 11:21:38', 'admin', '2023-07-19 02:19:25', '普通角色');
INSERT INTO `sys_role` VALUES (3, '业务员', 'business', 3, '1', 1, 1, '0', '0', 'admin', '2023-03-01 18:13:46', 'admin', '2023-07-23 21:25:27', NULL);
INSERT INTO `sys_role` VALUES (4, '审核员', 'checker', 4, '1', 1, 1, '0', '0', 'admin', '2023-03-01 18:14:10', 'admin', '2023-07-23 21:24:49', NULL);
INSERT INTO `sys_role` VALUES (5, '财务', 'financer', 7, '1', 1, 1, '0', '0', 'admin', '2023-03-01 18:14:33', 'admin', '2023-07-20 03:54:13', NULL);
INSERT INTO `sys_role` VALUES (6, '小程序用户', 'applets', 5, '1', 1, 1, '0', '0', 'admin', '2023-03-27 14:06:00', 'admin', '2023-07-19 05:13:20', NULL);
INSERT INTO `sys_role` VALUES (7, '司机', 'driver', 6, '1', 1, 1, '0', '0', 'admin', '2023-06-20 04:33:40', 'admin', '2023-07-07 02:14:15', NULL);
INSERT INTO `sys_role` VALUES (8, '出纳', 'cashier', 8, '1', 1, 1, '0', '0', 'admin', '2023-06-28 02:54:21', 'admin', '2023-07-20 03:54:20', NULL);
INSERT INTO `sys_role` VALUES (9, '战略合作伙伴', 'friend', 9, '5', 1, 0, '0', '0', 'admin', '2023-06-28 03:01:17', 'admin', '2023-07-19 05:11:38', NULL);
INSERT INTO `sys_role` VALUES (10, '测试用户', 'testUser', 0, '1', 1, 1, '0', '0', 'admin', '2023-07-11 03:21:33', 'admin', '2023-07-11 03:45:37', NULL);

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
INSERT INTO `sys_user` VALUES (2, 105, 'ry', '第二层', '00', 'ry@qq.com', '15666666666', '0', '', '$2a$10$kVqlibEeyfZTH/h1nGIE2e7P0605IlvC9F39nK4rETuIzYlAxo5SW', '127.0.0.1', '2022-11-09 11:21:38', '0', 'admin', '2022-11-09 11:21:38', 'admin', '2023-04-29 15:02:24', '2', '测试员');
INSERT INTO `sys_user` VALUES (100, 103, 'test', '第三层', '00', '', '', '0', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '', NULL, '0', 'admin', '2022-11-11 09:09:27', 'admin', '2023-03-06 11:31:11', '2', '第三层');
INSERT INTO `sys_user` VALUES (101, 100, '22', '哈哈哈哈哈哈', '00', '', '', '1', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-11-18 08:59:48', 'admin', '2023-03-01 18:15:00', '0', NULL);
INSERT INTO `sys_user` VALUES (102, 103, '没有', '呵呵呵呵', '00', '', '19913552556', '0', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-11-21 15:15:35', 'admin', '2023-03-01 18:15:08', '2', NULL);
INSERT INTO `sys_user` VALUES (103, 100, 'we', '2424', '00', '', '', '0', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-11-29 18:56:02', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (104, 100, 'applets', '小程序', '00', '', '', '0', '', '$2a$10$a./PUyaQCFNMN/.8LHU47./Q2Ir5bVS5pbd4zY5WdJlizoKZgxIOy', '', NULL, '0', 'admin', '2022-12-15 16:12:52', 'admin', '2023-03-27 14:07:00', '0', '小程序登录用户');
INSERT INTO `sys_user` VALUES (105, 100, '张三', '张三', '00', '', '', '0', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', '没有', '2022-12-15 16:14:33', 'admin', '2023-06-20 05:03:57', '0', NULL);
INSERT INTO `sys_user` VALUES (106, 100, '张三1', 'zhangsan', '00', '', '', '0', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', '没有', '2022-12-15 16:15:25', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (107, 100, '11', '11', '00', '', '', '0', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-15 16:19:41', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (108, 100, '111', '11', '00', '', '', '0', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-15 16:19:46', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (109, 100, '222', '222', '00', '', '', '0', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-15 16:20:13', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (110, 100, '2222', '222', '00', '', '', '0', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-15 16:20:22', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (111, 100, '2332', '1212', '00', '', '', '0', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-15 16:20:41', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (112, 100, 'sss', '1111', '00', '', '', '0', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-16 10:08:55', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (113, 100, 'hhghgh', 'ljn111', '00', '', '13237894611', '1', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-16 17:47:30', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (114, 100, 'ljn2', 'ljn111', '00', '', '132', '1', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-16 17:55:29', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (115, 100, 'ljn3', 'ljn111', '00', '', '1322', '1', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-16 17:57:10', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (116, 100, 'ljn4', 'ljn111', '00', '', '1324', '1', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-16 17:57:55', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (117, 100, 'ljn5', 'ljn111', '00', '', '1325', '1', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-16 18:08:48', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (118, 100, 'ljn6', 'ljn111', '00', '', '1326', '1', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-16 18:10:22', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (119, 100, 'ljn7', 'ljn111', '00', '', '1327', '1', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-16 18:11:20', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (120, 100, 'ljn8', 'ljn111', '00', '', '1328', '1', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-16 18:14:11', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (121, 100, '12121', 'lkk', '00', '', '', '0', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-19 10:02:24', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (122, 100, 'ii家', '李四', '00', '', '', '0', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-19 10:08:53', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (123, 100, 'ljn9', 'ljn111', '00', 'ry2@163.com', '1329', '1', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-19 10:10:35', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (124, 100, 'gg', 'lkk', '00', '', '', '0', '', '$10$q/3o4uc3U5Mz0j5RUFTk3uqv8g7sHjIDKdhKE31tMPqSMghkjS0M.', '', NULL, '0', 'admin', '2022-12-19 10:25:33', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (125, 100, '00', '00', '00', '', '', '0', '', '$2a$10$8m9B7FC4h1j0i837.yS6re/zmAdE8JpHPqM7/eXcMcjN9iKC9MsX6', '', NULL, '0', 'admin', '2022-12-19 10:49:52', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (126, 100, '000', '000', '00', '', '', '0', '', '$2a$10$kEag1xgPVH1zEzbDnkJ4EejYQd0xbYn2z1iAVwbzwhdMgdedZFpdW', '', NULL, '0', 'admin', '2022-12-19 10:54:17', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (127, 100, '0', '0', '00', '', '', '0', '', '$2a$10$zB1do5Xl4JM4Ey/hc5eMsOEJqtpkyItou.knntv1M4DSGprxCFADm', '', NULL, '0', 'admin', '2022-12-19 10:54:58', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (128, 100, '0000', '00', '00', '', '', '0', '', '$2a$10$uJnEXoFjx7WPGkhtGJBX9O4OMox1ixNfhTv7BgZ6VRGIVbnDjEGfq', '', NULL, '0', 'admin', '2022-12-19 11:06:14', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (129, 100, '000000', '1', '00', '', '', '0', '', '$2a$10$1RBBTR1xc1cer0iuDv/or.iJJ.5.bZkkuCNBAjJRM3R10PBSuuFfK', '', NULL, '0', 'admin', '2022-12-19 11:06:31', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (130, 100, '02', '02', '00', '', '', '0', '', '$2a$10$hbPrWlNc7f7L4xu8M5KL/.JwX2O8TlicCYe65DZe9zl7E7QfaCS7a', '', NULL, '0', 'admin', '2022-12-19 14:22:41', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (131, 100, '0101', '0101', '00', '', '', '0', '', '$2a$10$N1VTL65CQTTtKLmlt7NqdO47egXc5AzAqlaDfsag/Ybib1YI6GSOu', '', NULL, '0', 'admin', '2022-12-19 14:26:48', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (132, 100, '010122', '01010', '00', '', '', '0', '', '$2a$10$3NlbDKl2.XPqzbxcLnhemOVkTWujWe30w.KQsmh98O/7vvLNPdLcK', '', NULL, '0', 'admin', '2022-12-19 14:27:04', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (133, 100, '.0.0', '020', '00', '', '', '0', '', '$2a$10$PJv8qgRyAz01.2R.7eL5geW8E6hh6d4azg7NICrbbALY8NV7euQ/i', '', NULL, '0', 'admin', '2022-12-19 14:31:39', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (134, 100, '1123', '1111', '00', '', '', '0', '', '$2a$10$0NwZLY5iV3PESQYEdx2eN.wWa7yyqdA0nJy4v6Fxg/Q5unJk6hIQG', '', NULL, '0', 'admin', '2023-01-05 17:52:06', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (135, 100, '11', '111', '00', '', '55', '0', '', '$2a$10$ejaRM3QjE5MBG2jxi9Ykqe6p0Q5Q5ElCOC7ek7zcuxeVeF4HgbJdu', '', NULL, '0', 'admin', '2023-01-05 18:15:30', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (136, 111, '111', '222', '00', '', '222', '1', '', '$2a$10$y8XcOeHoOMZq8y682U5ocu5VEIxgh/1KlBKn/qAKYLw7jXrYfD7fW', '', NULL, '0', 'admin', '2023-01-05 18:46:10', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (137, 100, '1234', 'aa', '00', '', '', '0', '', '$2a$10$r0iABOIMtBAB.kEzipQ69ucEG6pLavrDCsmyUCY2zP3Ly0q2/J7p6', '', NULL, '0', 'admin', '2023-01-30 14:35:37', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (138, 100, '1113787878', '1111111', '00', '', '', '0', '', '$2a$10$MWUrrpCER2w/EeleFe4OkejFA2jGIQaZG.v2mqnPk1/voxNsL5j02', '', NULL, '0', 'admin', '2023-02-01 11:02:06', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (139, 100, '11137878708', '1111111', '00', '', '', '0', '', '$2a$10$g.3ejw2/o8MggoMCgsRuJeZNQC5fG0IPwF3TpFCZtDkJumx8eNna2', '', NULL, '0', 'admin', '2023-02-01 11:02:25', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (140, 100, '111378', '1111111', '00', '', '', '0', '', '$2a$10$jASQT.IB4PwjijPrEUxhIewPHuS/LvjHgpUwYOCUAq5Bva6Pu47mu', '', NULL, '0', 'admin', '2023-02-01 11:04:11', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (141, 100, '1113225252578', '11111112', '00', '', '', '0', '', '$2a$10$jADvv8CnVbvrPmGKgT2IIunil5DM1fij50hiA8olDTqrYCNJyI94G', '', NULL, '0', 'admin', '2023-02-01 11:07:58', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (142, 100, '1234CC', '222211', '00', '', '', '0', '', '$2a$10$XE.Kx/dtEQpsOZ9T6BV3KOJSZwXUoAfjlOKToh74qdyOOia5PPdrC', '', NULL, '0', '', '2023-02-01 11:13:09', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (143, 100, 'ljn123', 'ljn123', '00', '', '', '0', '', '$2a$10$JNnLz1QmRzTaBRV45jSI.OlPhX0tIsmHfepI9Zrzb7gP2mh8NiyPS', '', NULL, '0', 'admin', '2023-02-01 11:14:04', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (144, 100, '11132252522020578', '11111112', '00', '', '', '0', '', '$2a$10$mMgIcpELBDK4vGPisiasJuTh5Qyi250pdQNlVxtuHsk0VIpk1I4Cu', '', NULL, '0', 'admin', '2023-02-01 11:15:25', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (145, 100, '1234C34C', '222211', '00', '', '', '0', '', '$2a$10$mlaajPjytiMXb0H/ayYtkeKTDlNct2y9J6Sr/uag93hMMXqQSCRF6', '', NULL, '0', '', '2023-02-01 11:44:31', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (146, 100, '1234C34uC', '222211', '00', '', '', '0', '', '$2a$10$U8Jc9VRKr.85UhW3.wAQhenBDAy5PWooCqoh8JjBYIebe9nralB1G', '', NULL, '0', '', '2023-02-01 14:18:52', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (147, 100, '1234C3C', '222211', '00', '', '', '0', '', '$2a$10$0l7ssxu755cIHGB.YgFgJ.BxDgf0pMaQCSbxocYwTv2xNlbrgj1r.', '', NULL, '0', '', '2023-02-01 14:21:29', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (148, 100, 'ljn1234', 'ljn1234', '00', '', '', '0', '', '$2a$10$1RMS6Um.kUGDJm88YBQh..tRuRheyGS2MQ/v/nSwBrGRuO/Uw2cv6', '', NULL, '0', '', '2023-02-01 14:22:45', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (149, 100, '1234C33C', '222211', '00', '', '', '0', '', '$2a$10$zV6rLJYKWZqaYCOyNxxV6eA8C.sj8EdnrqTaCN9jBcHUXCn7MZlRO', '', NULL, '0', '', '2023-02-01 15:03:19', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (150, 100, '13462589635', '22', '00', '', '', '0', '', '$2a$10$azOi2HNDCFhxY/iYyTAEfeF0HXhVY6AxRCr1XqplQatF6VS8lKUgS', '', NULL, '0', '', '2023-02-01 15:13:37', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (151, 100, '13462589633', '子夏', '00', '', '', '0', '', '$2a$10$3Pab6iSMn5dejKGoCRiPJeAVUqrUOWNGAwfaf.YBILOyZhGi1qbb6', '', NULL, '0', '', '2023-02-01 15:14:24', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (152, 100, '19913447112', '子夏', '00', '', '', '0', '', '$2a$10$4W8uoeI61k/xX79tT9tRM.TqYu3VBU3ABu3etA9CGhUCNrmn8sx2W', '', NULL, '0', '', '2023-02-01 15:15:29', 'admin', '2023-07-18 21:41:59', '0', NULL);
INSERT INTO `sys_user` VALUES (153, 100, '13462589632', '赵楠', '00', '', '', '0', '', '$2a$10$CAU8Sz8LHlfOOxJkZ8DBL.HLcqvqLD6zc1qEKvpP33q/nOqFyf/X6', '', NULL, '0', '', '2023-02-01 15:20:13', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (154, 100, '13462589632222', '2', '00', '', '', '0', '', '$2a$10$7MJnm1HiVlntWe36O4c3lOFAZUkt0Do0SaRD6Y8o4qQqF1pH6gxe6', '', NULL, '0', '', '2023-02-01 15:23:48', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (155, 100, 'c123', '小伍', '00', '123456@qq.com', '16800008888', '0', '', '$2a$10$2XamRYv1njKkTnEHMncJ7eN3hCH.ARZ.54w0zU13c2WnoHe3iDS9O', '', NULL, '0', '', '2023-02-01 17:16:10', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (156, 100, '13462589635222224343467', '25', '00', '', '', '0', '', '$2a$10$RvIgPE2OJzLlBftHuIvXveQ9Tj/.Elm8QeeYA27JWONfIx3egs8o.', '', NULL, '0', '', '2023-02-01 17:23:18', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (157, 100, '3', '4545', '00', '', '', '0', '', '$2a$10$sftWBkBlSge3o47jivEzbOoD5pi92asiR6ZFUMnElWLfGDJNSYW0W', '', NULL, '0', '', '2023-02-01 18:07:09', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (158, 100, '13462589035', '3434', '00', '', '', '0', '', '$2a$10$oM6J96hujYjU5AiUZJiDtePqg6nA/63rYQ/.65ZiT8ZkEmdORmVjO', '', NULL, '0', '', '2023-02-02 10:09:35', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (159, 100, '13462589630', '253', '00', '', '', '0', '', '$2a$10$2FgzrITyy3hibDOAfbUo.OqNMAe3tRS4jBzlOo8uXCOCd7vyUZ5dC', '', NULL, '0', '', '2023-02-02 10:11:23', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (160, 100, 'ljn123452', 'ljn123451', '00', '', '', '0', '', '$2a$10$9R.gmFWytIQuOay39Z3l7.B7IHhJ12KjsLpl8k1A1e8ebqzcCBHMS', '', NULL, '0', '', '2023-02-03 11:55:50', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (161, 100, '19913447110', '252', '00', '', '', '0', '', '$2a$10$QQmlfymSFHJcJxLNW9GM6OgoZtJiudLQSfrBYW0WBm.oTMp17JdrW', '', NULL, '0', '', '2023-02-03 15:24:43', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (162, 100, '007', '老默', '00', '1234567@qq.com', '16800008887', '0', '', '$2a$10$N6/uBShro2A30tQBKrazoOgPQ2ddHL7OdVq2WhV9k9mMWn49tPRqy', '', NULL, '0', 'admin', '2023-02-04 11:22:36', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (163, 100, '12215336552', '323', '00', '', '', '0', '', '$2a$10$4AGXkPPIeU2boYK.VWGQcOL7uQBpeCdOTO75FyVDMhYFAdBP1dwR2', '', NULL, '0', '', '2023-02-04 11:29:27', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (164, 100, '11111111111', '11', '00', '', '', '0', '', '$2a$10$LnKzJ1KEkO/GJfZ..SgnZuxtCIs7.cDBZr2YjkQEjV8Vj7TKrm2By', '', NULL, '0', '', '2023-02-04 11:50:34', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (165, 100, '23456767654', '23', '00', '', '', '0', '', '$2a$10$tTWQKo/8TfdGjX2psBV6T.qJjQIJYWqJqm990UVkOdT2bXfBL3ZAu', '', NULL, '0', '', '2023-02-04 11:55:06', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (166, 100, '12345678909', '11', '00', '', '', '0', '', '$2a$10$czNPBph7UOEt5lUboIirMOwtsMjGWcCTHYnQBhVTxJLttN/PosMyS', '', NULL, '0', '', '2023-02-04 12:06:19', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (167, 100, '12363663225', '3434', '00', '', '', '0', '', '$2a$10$YjKkrcHv3APFZ0gz.Q.Bl.rKtWskGm/pMAHSdKFKY/UWXUBLrKrMu', '', NULL, '0', '', '2023-02-04 15:19:12', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (168, 109, '11222', '222', '00', '', '', '0', '', '$2a$10$J3zEvfKD7svnuW0BPvpj7uKUPsk8it.UvttzY/urH.JXls4hHXKv6', '', NULL, '0', 'admin', '2023-02-06 11:29:40', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (169, 107, '22333', '111', '00', '', '', '0', '', '$2a$10$Yif.JF7RN3zmgBBI9aurBeDob.bqDE/m1Bul/C6NZQEg/Bo4yaXmW', '', NULL, '0', 'admin', '2023-02-07 15:13:46', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (170, 103, '12', '12', '00', '', '', '0', '', '$2a$10$8FFzNausGYvFB1oF0vxSeOOLGMjZjsNGUDf3L2aJfjXVcCF4qk9s2', '', NULL, '0', 'admin', '2023-02-09 08:46:11', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (171, 100, 'c1111', '1111', '00', '', '', '0', '', '$2a$10$IdBDSUDiti1lyE8dvoEgqu5I.eoKKwGIlHLEKqkFjWVWHfI64H/c6', '', NULL, '0', '', '2023-03-09 11:36:38', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (172, 100, '1212121', '1212121', '00', '', '', '0', '', '$2a$10$4csyqKlhj1nA9fOfJgxVqu4ZGeXO..zkphWnhPbTPiW2JP3a1OMBG', '', NULL, '0', '', '2023-03-09 16:07:36', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (173, 100, '111ccc', '222', '00', '', '777', '0', '', '$2a$10$ZXM8dVQhcLbVWf.Z3UNfk.7IW3qYecEuPYaFKKALeJDU7k.b.KmFO', '', NULL, '0', '', '2023-03-09 17:37:41', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (174, 100, '1222', '3333', '00', '', '', '0', '', '$2a$10$mBEj5K63.3EBa13XPnAP8epDL8nqfs1GKqJDsyeq8.9I4LK0/YJ3m', '', NULL, '0', '', '2023-03-10 14:38:51', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (175, 100, '232323', '3333', '00', '', '', '0', '', '$2a$10$csJdXpEc.kS13Drs0sh3WOSjezv8mrH9S9AF9krhU0nDViulY0WJy', '', NULL, '0', '', '2023-03-17 15:53:00', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (176, 103, 'YF01', '大力', '00', '', '16800008886', '1', '', '$2a$10$fRv3iCWnKaj/yrsSfDpYleGZoseqmPP2hCbhxyhGdzSD1HBJlpS2i', '', NULL, '0', 'admin', '2023-04-01 14:39:40', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (177, 103, 'YF02', '阿萨法', '00', '', '16500008888', '0', '', '$2a$10$xHxV4A.quhEC2BwZuR6x0.fHU14Mf.nUESOa9nkF9/voZxCCPBXVq', '', NULL, '0', 'admin', '2023-04-01 15:35:20', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (178, 103, 'SJ01', '霍霍', '00', '', '18600008888', '0', '', '$2a$10$UIDMXVe4RWM5mZoKa1yZhOH0jKst0/4KsdkwCjgqCE31r53v0oo/G', '', NULL, '0', 'admin', '2023-04-06 10:28:31', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (179, 103, 'SJ02', '高火', '00', '', '17800008888', '0', '', '$2a$10$dr1aQSAexA78r7Ggdet0du9QYzPqkctQjWFjxJrP5WP3UoK495zPq', '', NULL, '0', 'admin', '2023-04-06 10:34:48', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (180, 103, 'SJ03', '高火', '00', '', '17800008889', '0', '', '$2a$10$xrqYJ44swfWvPG/M8aANNeshlEhD1P/ZxMtVyecou.wpplLmMX5OW', '', NULL, '0', 'admin', '2023-04-06 10:35:13', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (181, 103, 'SJ08', '高火', '00', '', '17800008899', '0', '', '$2a$10$aKIrgVWVEyC4iP.QdCOm2O6o1fF2tWrH65bAIbC9V8kVHzhRy6gWq', '', NULL, '0', 'admin', '2023-04-06 10:35:38', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (182, 103, 'SJ11', '高火火', '00', '', '16800007788', '0', '', '$2a$10$54W5xGc5F4dRj6hSKGLRe.qxDboGMAxFcjVpzmWB1DYSET.uZjE46', '', NULL, '0', 'admin', '2023-04-06 10:36:47', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (183, 103, 'SJ12', '高火火', '00', '', '16800007789', '0', '', '$2a$10$6BhOvgk.Ppdybmv43cvvC.Rd9/DSuiqgjiUKSreXfz80ThZBVRZry', '', NULL, '0', 'admin', '2023-04-06 10:37:31', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (184, 103, 'GH', '高火建', '00', '', '15600007777', '0', '', '$2a$10$.NPiEI4mJLb5fOpVGcyCceN95BnQlED1CcgQia3JpxXDa21Iv4P9y', '', NULL, '0', 'admin', '2023-04-06 10:51:50', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (185, 103, 'GH22', '高火建', '00', '', '15600007778', '0', '', '$2a$10$fwPfSzYaGygPkAvDf0Mu6Oaiphv2o0exzZpIJod0mEbg8x6c9mcdy', '', NULL, '0', 'admin', '2023-04-06 10:52:16', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (186, 103, 'GH23', '哈哈哈', '00', '', '', '0', '', '$2a$10$c22xkIg80uqTpdXljdE3nebqM0HM26PkvwUbbdsZXvY.GH8owf7a2', '', NULL, '0', 'admin', '2023-04-06 10:53:07', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (187, 103, 'SJ05', '急急急', '00', '', '', '0', '', '$2a$10$FY56lLe3RIEqjkiMRM4rmu/6za4D9UIx8.Z5YpYujLcHIJ03kVqXW', '', NULL, '0', 'admin', '2023-04-06 11:06:11', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (188, 103, 'SJ25', '发发发', '00', '', '', '0', '', '$2a$10$.Z/roh.wP7DeonEfvpcfW.NGdYvNnnbRoK5/ftugd7pwSs5eRdHKm', '', NULL, '0', 'admin', '2023-04-06 11:07:39', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (189, 103, 'SJ29', '发发发', '00', '', '13400008888', '0', '', '$2a$10$rn45aTJ/ReHFnRqmehdEc.D34XJnbWxYLxhCPBCPrK40cWttSDexa', '', NULL, '0', 'admin', '2023-04-06 11:07:55', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (190, 103, 'SJ99', '发发发', '00', '', '13400008887', '0', '', '$2a$10$dQpe9Bvty9UD/Mfkr5Y0se6sxdvuzC/tlspAUDmpFhTUnMMaJBJtO', '', NULL, '0', 'admin', '2023-04-06 11:08:23', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (191, 103, 'KK01', '发发发', '00', '', '', '0', '', '$2a$10$YSl.0x8BryVFWwckIkaecuFHscWabp.ABGXSp/Mw3b4l2tcRlCQee', '', NULL, '0', 'admin', '2023-04-06 11:09:00', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (192, 103, 'KK02', '1', '00', '', '', '0', '', '$2a$10$0VuNRTu9kF2VNf4sCQ0CMuzfxRTg1C2ks1vCAGHgcTjqdn9lb1MVi', '', NULL, '0', 'admin', '2023-04-06 11:09:21', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (193, 103, '4444', '1', '00', '', '', '0', '', '$2a$10$vkhNdP2e/Qk5t8n/pWGuq.2qJ8O2UPIfEOBv6g5vjJbV/IoB3PJ5S', '', NULL, '0', 'admin', '2023-04-06 11:10:04', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (194, 103, 'KK05', '3', '00', '', '', '0', '', '$2a$10$hi/LJA.LX1CHkSGnE5mfwe.hv8XgHxQLZkPh0Bs4uWwCi047qsatK', '', NULL, '0', 'admin', '2023-04-06 11:10:24', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (195, 103, 'KK07', '就看看', '00', '', '', '0', '', '$2a$10$2dRN.tYwZR5QApMRmEtx.OaRv/fOJeBlb4q.V5lxRf7/MTo/f71JW', '', NULL, '0', 'admin', '2023-04-06 11:11:01', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (196, 103, 'KK08', '好好好', '00', '', '', '0', '', '$2a$10$VxmSvBV42wSWqMYxxvbkheKnOwapYsydhVs4iqYwa/RXs8q3yEuvC', '', NULL, '0', 'admin', '2023-04-06 11:12:10', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (197, 103, 'KK00', '就看看', '00', '', '', '0', '', '$2a$10$YsUOXjVafCNhid6FHhwbeezb3eg1J62e6wsxYUvU8ZUHlmvIskTvu', '', NULL, '0', 'admin', '2023-04-06 11:14:06', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (198, 103, 'KK12', '就看看', '00', '', '', '0', '', '$2a$10$zpyWAJnKfM0KfvAFZI.qROHuew0mcc5N0s3h8M.S4/Se9VCJ0mrwy', '', NULL, '0', 'admin', '2023-04-06 11:15:12', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (199, 103, 'AK12', '就看看', '00', '', '', '0', '', '$2a$10$JSEAK8trtE.fmaB92pxcN.cCjSYy2y/9kzjVIeOrgXARJ2.8WdnGS', '', NULL, '0', 'admin', '2023-04-06 11:15:49', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (200, 103, 'Aq12', '就看看', '00', '', '', '0', '', '$2a$10$7Ul2pazv0igm75TOYVHWoOLvO0gftCJluVsTMs8k74WhcGOIWKH3e', '', NULL, '0', 'admin', '2023-04-06 11:17:57', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (201, 103, 'KK09', '快看', '00', '', '', '0', '', '$2a$10$CVTYI2NmTSAAlxpxOMCJGOcwSXhSZ0Tc4njDLsWnKvnirjHtr6mkS', '', NULL, '0', 'admin', '2023-04-06 11:22:37', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (202, 103, 'KK11', '快看', '00', '', '', '0', '', '$2a$10$hc5zo77wO1NFoe0.owRspu79w86bP/VT6p6QbavusAbg03wsK4a4y', '', NULL, '0', 'admin', '2023-04-06 11:22:50', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (203, 103, 'KK20', '快看', '00', '', '', '0', '', '$2a$10$X/Va.7TXbH0E6ZxwHuYPoObA9P3oAP68vxfJCj3fXl.q9zTcX4Q6y', '', NULL, '0', 'admin', '2023-04-06 11:22:57', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (204, 103, 'KK28', '快看', '00', '', '', '0', '', '$2a$10$PrU//2387iz7CeJJxMBole7U1L.6PonWlqZZX5LxeMaROFTieJLYa', '', NULL, '0', 'admin', '2023-04-06 11:23:16', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (205, 103, 'KK30', '快看', '00', '', '', '0', '', '$2a$10$XAq7LQ1Wr166msUUH83BWO4Pyh0XL.y7PyFVvXufMNvtJTly9gCGW', '', NULL, '0', 'admin', '2023-04-06 11:23:33', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (206, 103, 'KK31', '快看', '00', '', '', '0', '', '$2a$10$XwXX2HjI2xjCOgwp77mpaOkbWVYLjy0KA/F1KiXgA52IpwU9d9FW2', '', NULL, '0', 'admin', '2023-04-06 11:24:54', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (207, 103, '2311', '快看', '00', '', '', '0', '', '$2a$10$yLPLWIH8P45BR5/pUhgGcey.Y6iy.QYEzGq5q6DPa2CM1pkJJZdu6', '', NULL, '0', 'admin', '2023-04-06 11:25:04', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (208, 103, '2e11', '快看', '00', '', '', '0', '', '$2a$10$w9Tg7VqcxVUdBqjgo8q0qeScvInPz.YrAeSoXVvQFjSVh.nzdgvJ6', '', NULL, '0', 'admin', '2023-04-06 11:25:31', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (209, 103, '2e1', '快看', '00', '', '', '0', '', '$2a$10$HvlBIbd/Wg3k/rumMkCuv.LA8PgfNZ6OEHVWQ3rmCS2dRdQhmtgIu', '', NULL, '0', 'admin', '2023-04-06 11:26:13', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (210, 103, '211', '快看', '00', '', '', '0', '', '$2a$10$tnX6eXGUTDbN91NOjpDVtufuAq.DZqD.rY2vJGigWJBAfXFOpn2ke', '', NULL, '0', 'admin', '2023-04-06 11:39:14', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (211, 103, '2111', '快看', '00', '', '', '0', '', '$2a$10$sgd50AiONqc30Kz4O40B2ugYwvvlyl1kU/dHVQgZYAnpj/YB2aSIm', '', NULL, '0', 'admin', '2023-04-06 11:44:55', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (212, 103, '1212', '快看', '00', '', '', '0', '', '$2a$10$A8YPZ7QT8IGIR03kglk6..woFfRM7WmWxZyjy2UPMxjFNt53oQLJm', '', NULL, '0', 'admin', '2023-04-06 11:44:59', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (213, 103, 'f212', '快看', '00', '', '', '0', '', '$2a$10$E4HoxltbC4rNhaYjqSFcXeo4N6pTDk1qWWB/ahfUWvLqhxj6KPPgu', '', NULL, '0', 'admin', '2023-04-06 11:45:44', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (214, 103, 'f2f2', '快看', '00', '', '', '0', '', '$2a$10$HE7DE0T1oJ9aOgRxG2PG6uF6hJpUJOkuDeL3m0HciapFTTs3.TxCa', '', NULL, '0', 'admin', '2023-04-06 11:46:49', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (215, 103, 'f2g2', '快看', '00', '', '', '0', '', '$2a$10$MhoTR7QMTuIxxXNyx4fFye91TWwBH2oBMbL/Me68iFrbrpW96b7qG', '', NULL, '0', 'admin', '2023-04-06 11:48:15', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (216, 103, 'f412', '快看', '00', '', '', '0', '', '$2a$10$gKXUFjYJZ.OXBvx2.BKNCOqUh6wDvNAGeWJs6aILgfScQbvthZkmm', '', NULL, '0', 'admin', '2023-04-06 11:54:52', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (217, 103, '1', '快看', '00', '', '', '0', '', '$2a$10$vOVcryPTb1WEY4I1NBkPJ.kCvvihCtaM3XnDLpfRwNZvsGvUMEWmS', '', NULL, '0', 'admin', '2023-04-06 11:55:21', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (218, 103, '2', '快看', '00', '', '', '0', '', '$2a$10$93HkpCgBSZVymTvUXcSfiOp8uxxhBMPSR8zQGjaqlFcqgBM7C7vqe', '', NULL, '0', 'admin', '2023-04-06 11:55:24', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (219, 103, '4', '快看', '00', '', '', '0', '', '$2a$10$quroYrALDsbfYZjXj2/2QeeQYidneRoJqMGt5FTis0PAIlhPvSa7i', '', NULL, '0', 'admin', '2023-04-06 11:55:44', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (220, 103, '5', '快看', '00', '', '', '0', '', '$2a$10$FOvtcAIoKcePS8UsQjXfpeHWnBP7OTRPtR7AMSCadrujxQiA6dNFK', '', NULL, '0', 'admin', '2023-04-06 11:56:43', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (221, 103, '6', '快看', '00', '', '', '0', '', '$2a$10$NcR0am8nnBe7yyLTZhATW.VRSviBF6ZutX8R1AHNd/lOUl5S4RRbC', '', NULL, '0', 'admin', '2023-04-06 12:00:53', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (222, 103, '7', '快看', '00', '', '', '0', '', '$2a$10$51Vqk3AOIHe9MLliEHWhhOopgSYKidIwdXOrxHPqwKmLZeKKauD5i', '', NULL, '0', 'admin', '2023-04-06 12:01:02', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (223, 10, '8', '快看', '00', '', '', '0', '', '$2a$10$f0Ovfk6nlfF3lnKVipGhW.3Skk1At954Ozy4xErsPHg09dbu1k3cC', '', NULL, '0', 'admin', '2023-04-06 12:01:28', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (224, 10, '9', '快看', '00', '', '', '0', '', '$2a$10$Nojq8PegZznw7F4JaabbLOuHsYj9UXbg70zwLTLRETEgVgG.Rc4d2', '', NULL, '0', 'admin', '2023-04-06 12:20:20', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (225, 205, 'wuliuceshi', '物流测试', '00', 'c1@sogoun.com', '13677895441', '0', '', '$2a$10$e1drXtIA9gH7R6DCJcQuLeCD/Obq8o9LAZUkwdQUPuCYPS/HrdhVi', '', NULL, '0', 'admin', '2023-04-18 09:38:23', 'admin', '2023-04-18 09:40:10', '0', '11111');
INSERT INTO `sys_user` VALUES (226, 100, 'Rqqx', '反反复复', '00', '', '17022225555', '0', '', '$2a$10$/.U5olqnvzGxDI9ppaGeD.zgz8r.4buke4VacPxIHqlmJkMZdcENS', '', NULL, '0', '', '2023-04-21 09:53:22', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (227, 103, '4312', '121', '00', '', '', '0', '', '$2a$10$cLiIbGDtMpxcZZX0MEMNhe89GPPZMIvZAnfZk0sVd42TdXLyvgY0W', '', NULL, '0', 'admin', '2023-04-21 16:16:19', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (228, 100, '111111aasdasd', '111111aasdasd', '00', '', '', '0', '', '$2a$10$u3UgIwKXpqY56XI3Kxx0IuKVF/4t/.QQo2r9efyQHEU.lak039Wcm', '', NULL, '0', '', '2023-05-06 14:51:52', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (229, 100, '111111aasdasd22222', '111111aasdasd22222', '00', '', '', '0', '', '$2a$10$dGckyD6HyMsFWrdmB9eSfe3dgzRwhRHlcBE9aqsnN2zhIScE/R3h.', '', NULL, '0', 'admin', '2023-05-08 10:11:06', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (230, 100, 'oBEIe5f9py614y7ShCZHglDM0BlE', 'oBEIe5f9py614y7ShCZHglDM0BlE', '00', '', '', '0', '', '$2a$10$E.u7aUu5odFYmG9qAY9kKe2QciYK2KtVbD0d3ftj5WP3jdDTe8c/K', '', NULL, '0', 'admin', '2023-05-09 09:02:46', 'admin', '2023-05-09 02:38:10', '0', NULL);
INSERT INTO `sys_user` VALUES (231, 100, 'oBasdqwehuoihzasd', 'oBasdqwehuoihzasd', '00', '', '', '0', '', '$2a$10$so5QwVC8Bk7RCU4Sr04Bperq/9K4yG9D6HXMo4egW8AP82alPRhf6', '', NULL, '0', 'admin', '2023-05-09 10:13:54', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (232, 100, '111111aasdasd11', '111111aasdasd11', '00', '', '', '0', '', '$2a$10$4RrAWg9Cp4nC4ti5OrxMu.rf0bF6CU3U0c65zRcWbQ5TowAX/FFni', '', NULL, '0', '', '2023-05-09 10:28:07', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (233, 100, '3222', '3222', '00', '', '', '0', '', '$2a$10$XbVkHQngrtSMujNlzzuuleMf8GfaYu5tLGe3Vqm6HRN5VhmGlJgWy', '', NULL, '0', '', '2023-05-08 22:56:13', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (234, 104, 'LTR', '李天然', '00', '15500008888@163.com', '15500008888', '0', '', '$2a$10$JHvAO/mximPHdNW9QKIz/OIhlTLrw0H44MSYbuPz4TAGxya6XH1.K', '', NULL, '0', 'admin', '2023-05-10 04:38:55', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (235, 100, 'oBEIe5YoTwqKeD4CesEd5DMlzO6U', 'oBEIe5YoTwqKeD4CesEd5DMlzO6U', '00', '', '', '0', '', '$2a$10$97zN5.7SocERubvILm6bNOVUEAm787iQ3WeCz0k4I7PJWne4/LixW', '', NULL, '0', '', '2023-05-13 04:35:34', 'admin', '2023-05-26 05:51:10', '0', NULL);
INSERT INTO `sys_user` VALUES (236, 100, '11111', '111', '00', '', '', '0', '', '$2a$10$OK0U/04uXYgIzbaDAf9ztOq/EEbtkrOO7Fl1ze9NMocD3okHgZz42', '', NULL, '0', 'admin', '2023-05-15 05:18:47', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (237, 100, 'CSGYS001', '测试供应商001', '00', '', '15600005555', '0', '', '$2a$10$q4BcqaNwJjB1WjTjoGaw3OZQJIbWEa87oGiU1ysxukGE/gr/V.sH6', '', NULL, '0', '', '2023-05-24 04:34:47', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (238, 100, 'MYJ5', '美宜佳5', '00', '', '', '0', '', '$2a$10$Tn2BPozQbD4ydiIBmOkaNOLEt1Ug05IXSTSsceBYHVYbxMtNGgvTG', '', NULL, '0', '', '2023-05-24 05:22:57', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (239, 100, 'RRL01', '人人乐01', '00', '', '', '0', '', '$2a$10$izl1ZTx47O3BgvTQUufTKuLhuKK6CVMVmSVMiRVISApd7YSupDZcu', '', NULL, '0', '', '2023-05-24 05:36:10', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (240, 100, 'YTJZXGS', '盐田集装箱公司', '00', 'ytjzx123@163.com', '', '0', '', '$2a$10$hHbPwWnHrZJFoauR0H91sOl6Lmc23CFs8Dx4CWdl/7J3ugNHhnjjC', '', NULL, '0', '', '2023-05-24 23:04:06', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (241, 100, 'WDZYYTD', '维达纸业盐田店', '00', '', '', '0', '', '$2a$10$dsQhg5jSxJu4nXhkS5464ujEsXLrmLTSs1tP4EtyDZPaJmrIdRrUa', '', NULL, '0', '', '2023-05-24 23:09:31', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (242, 205, 'FQY11', '风清扬', '00', 'FQY123@163.com', '16800008889', '0', '', '$2a$10$MLWPmoYB/bjxOw/PQ5MvheDFDS61FuXg1/6F.TFTS/5L5khqGX7z6', '', NULL, '0', 'admin', '2023-06-15 02:35:56', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (243, 100, '123', '123', '00', '', '', '0', '', '$2a$10$Rwvww.nHIlUwtUog4NCYF.MuWkmX1Ck33PUKnfF7IvEop317fH90e', '', NULL, '0', '', '2023-06-16 05:47:03', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (248, 100, '111111aasdasd1111112s', '111111aasdasd1111112s', '00', '', '', '0', '', '$2a$10$aEtFMCJL/yLg3KKSrQz61OFR8BVRukOIITB86xmc.zGZDg7Qrjdcq', '', NULL, '0', '', '2023-06-20 05:33:51', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (249, 100, '111111aasdasd11111111122ss', '111111aasdasd11111111122ss', '00', '', '', '0', '', '$2a$10$NCve0kXn2i6ayzDv3rk1vuLzJQk2tQ0W0pxbDh0qknsJxcMvpX1Eq', '', NULL, '0', '', '2023-06-20 05:36:07', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (250, 100, '111111aaeee11111111122ss', '111111aaeee11111111122ss', '00', '', '', '0', '', '$2a$10$5u8zkeOfnsdgRBWhp4pV0.sxJi1iP9cl6YKga2E3vxrXxSBEiVAeu', '', NULL, '0', '', '2023-06-20 05:37:39', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (251, 100, '111111aaeee11ff11122ss', '111111aaeee11ff11122ss', '00', '', '', '0', '', '$2a$10$rKe9FuUl.X0q7.8JZhVEQOuqPW5T4.bndzd0PU.uzy2Bhk8auGEgO', '', NULL, '0', '', '2023-06-20 05:40:41', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (252, 100, 'XMZJ01', '小米之家01', '00', '26700007777@qq.com', '16700007777', '0', '', '$2a$10$ej7w8DjjYqNrnIryOLl8X.d6T40akN607y8Lcvui8uYtA6SZZtnvi', '', NULL, '0', '', '2023-06-22 05:34:19', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (253, 100, 'MHYSBC', '米哈游手办厂', '00', '23600006666@qq.com', '13600006666', '0', '', '$2a$10$hSeYge8EBwY9VsESrWD6buAd/8ZC7Wx4RtQhp2xPhqvagaIqRvvGa', '', NULL, '0', '', '2023-06-22 10:29:41', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (254, 100, 'CS001', '测试001', '00', '', '', '0', '', '$2a$10$g1ugMVTWxn7/GIww0WVbwuQEistKX9YEP.8sMao1AmVIfoA5SMCuK', '', NULL, '0', '', '2023-06-24 05:49:48', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (255, 100, 'CS002', '测试002', '00', '', '', '0', '', '$2a$10$RQDsBvTF7cir7IgI7qaHoeP6A2liDysNWPKM5VOGouBkLTGCHTAqy', '', NULL, '0', '', '2023-06-24 07:02:08', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (256, 100, 'HZHB001', '合作伙伴001', '00', '', '15600005566', '0', '', '$2a$10$iF.PYhnHkTRhe8CbMtH/buhak7ZpxdiTI5MmmkPfTQgF91m8kiTVy', '', NULL, '0', '', '2023-06-24 07:41:09', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (257, 100, 'CS0023', '测试0023', '00', '', '', '0', '', '$2a$10$oglhlOVJ7KT3E2MgUZDF/uHWCxqzW3WR75LFFbEIfhl31b6mKLLQm', '', NULL, '0', '', '2023-06-24 08:35:51', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (258, 100, 'SFSDF', '十分士大夫', '00', '', '', '0', '', '$2a$10$XNOKFPlGYAbM8e1sjI9XX.xXVUkiLMs7sCqDGQaVpLQwmKW8XcMrq', '', NULL, '0', '', '2023-06-24 08:41:41', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (259, 100, 'SDSDS', '萨达萨达是', '00', '', '', '0', '', '$2a$10$VQNDjjr.ssmqqiRUK1dZAOsVTkJ7WMn2c8ql9MtM/Uj3QBUjxZjPi', '', NULL, '0', '', '2023-06-24 08:59:16', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (260, 100, 'CSHB001', '测试伙伴001', '00', '13500006666@qq.com', '13500006666', '0', '', '$2a$10$3xuTLelRjWIJD4c2UMsU..2zLdCgwjzAYcuSSo1BCGYo0XInQG3qG', '', NULL, '0', '', '2023-06-24 23:58:47', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (261, 100, 'SDSD', '撒旦撒旦', '00', '', '', '0', '', '$2a$10$QSWfS4Q0MEJvv1jVsM34ZOp1Dt0Eb8BFu03AOSVP3MMdV/VpsarUC', '', NULL, '0', '', '2023-06-25 00:00:50', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (262, 100, '1111ddd', '1111', '00', '', '', '0', '', '$2a$10$JY4MuQrP45RVEkyZCoDyRu1qIKP84ZIZAuRiH6MpkbjUG1LqErgoi', '', NULL, '0', '', '2023-06-25 02:47:38', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (263, 100, 'jhasd', 'jhasd', '00', '', '', '0', '', '$2a$10$9K52ccc2XQ2Twun3R/jr8OX0dc95JOQNZR4OjaiWi9Up.mmO5T8oy', '', NULL, '0', '', '2023-06-25 02:55:13', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (264, 100, '13751033134', '13751033134', '00', '', '', '0', '', '$2a$10$M2AIUwJaHeQuuiIp9w.oxeFM.VVpY2NhiNPJSDVX2xkdxlth/WfP.', '', NULL, '0', 'admin', '2023-06-26 00:05:56', 'admin', '2023-06-27 00:03:27', '0', NULL);
INSERT INTO `sys_user` VALUES (266, 100, '13751033135', '13751033135', '00', '', '', '0', '', '$2a$10$UhRbtlIbEagNIn4jUkCAieOFxGP3.6UUbJjKXx3VJI7tj8Nzkd96S', '', NULL, '0', 'admin', '2023-06-27 02:48:50', 'admin', '2023-06-27 03:02:05', '0', NULL);
INSERT INTO `sys_user` VALUES (267, 100, '13751033136', '13751033136', '00', '', '', '0', '', '$2a$10$WadJHrZuYI9o2OnqPWSqv.r8VEFjpjN0uoYB7DpwOUh8JlovLlZtO', '', NULL, '0', 'admin', '2023-06-27 03:18:25', 'admin', '2023-06-27 03:18:25', '0', NULL);
INSERT INTO `sys_user` VALUES (268, 205, '13828425563', '梅超风', '00', '', '13828425563', '0', '', '$2a$10$FbwO30ynlptZHyYweiOrk.vLLe6735BzavenrM64VctBIlBWyOLHu', '', NULL, '0', 'admin', '2023-06-27 05:50:44', 'admin', '2023-06-27 21:57:48', '0', NULL);
INSERT INTO `sys_user` VALUES (269, 205, '19913447119', 'sjzn', '00', '', '19913447119', '0', '', '$2a$10$a44CtQ8HNY3gEWLgXSxgUeFdgNtWf0E3reBUouUUdZSormfTHJqNO', '', NULL, '0', 'admin', '2023-06-27 06:22:04', 'admin', '2023-07-18 21:59:22', '0', NULL);
INSERT INTO `sys_user` VALUES (270, 205, '13700005555', '老默', '00', '2536401452@163.com', '13700005555', '0', '', '$2a$10$PD9UrCCfNDSmjE9wtfgO/.qalorbWI3OZMZNKD.6wCRCCksoxGmF2', '', NULL, '0', 'admin', '2023-06-27 23:38:54', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (271, 205, '1232', 'qwe', '00', '', '1232', '0', '', '$2a$10$6Pe2oXDyxxqss3239jI6T.ivjp7zpTpBojxdkL4EFMXJLJRr/qy3W', '', NULL, '0', 'admin', '2023-06-28 05:05:07', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (272, 104, '12344', '123', '00', '', '', '0', '', '$2a$10$4hbZgzzt9LemnXbHLbOl..fdHxobGd9GSfxefhA5xaVI71aXLE0xS', '', NULL, '0', 'admin', '2023-06-28 05:45:58', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (273, 104, 'zxcv', 'zxcv', '00', '', '', '0', '', '$2a$10$Dp2vccQf2GsUZ5gxe8gsw.sWgyRgowYr6FFkiuZA4S/LNYuLkIKRW', '', NULL, '0', 'admin', '2023-06-28 05:47:11', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (274, 205, 'qweee', 'qweee', '00', '', '', '0', '', '$2a$10$G3k/QdfxsSPSWq8cM3eb8.WlS7OMdNnwIUVgkqI6JLXhmKAFMZeqO', '', NULL, '0', 'admin', '2023-06-28 05:54:47', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (275, 104, 'asdzxc', 'zxc', '00', '', '', '0', '', '$2a$10$PXbuOkjFLjSJQ6me17S1Y.fyBMthK7X4QaAj.zb/1QVDK4SophkqS', '', NULL, '0', 'admin', '2023-06-28 06:03:57', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (276, 103, '123133uuuuuuuu', '12333', '00', '', '', '0', '', '$2a$10$5AwBCGEGfYhbN1TdBn2Md.ExlLkR2Py3DUYcZmU4tIriA8QZoc88W', '', NULL, '0', 'admin', '2023-06-28 06:40:05', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (277, 103, '请问', '请问', '00', '', '', '0', '', '$2a$10$rO7BpbVKlQnIeS8T2B45PudhK7fgoDxc0j/bLcE3r5SBwK8NI9tqa', '', NULL, '0', 'admin', '2023-06-28 21:09:49', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (278, 104, '123311', '111', '00', '', '', '0', '', '$2a$10$1.zJocbSisGhTfogkYOT5O8RW23ImnxrZD5yMEIhZBVXrTPJ2keU.', '', NULL, '0', 'admin', '2023-06-28 21:14:25', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (279, 205, '阿斯顿', '阿斯顿', '00', '', '', '0', '', '$2a$10$rTWS9zRSYRMWP4Zoqx0RG.eC40MD.m4piVa5Yul0fIdgx.EREp0jy', '', NULL, '0', 'admin', '2023-06-28 21:50:03', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (280, 205, '请问阿斯蒂芬', '请问', '00', '', '', '0', '', '$2a$10$eal8GXZ0TwjvbMKqE.6zg.vd59gqukMNRjKbXpnBbxkqx02y/vMoy', '', NULL, '0', 'admin', '2023-06-28 21:53:42', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (281, 205, '请问阿斯蒂芬阿斯蒂芬', '请问', '00', '', '', '0', '', '$2a$10$pig8STsGOhJw2/khsfHah.uC48ZnShmkDu63zRwCpCKU/Kj1lJlui', '', NULL, '0', 'admin', '2023-06-28 21:53:50', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (282, 205, '请问阿斯蒂芬阿斯蒂芬阿斯蒂芬', '请问的萨芬', '00', '', '', '0', '', '$2a$10$jJj7EyLD/HUZj2umb5/4/ORb9xLail6CA2N3brmGa4R2MfaaMXWPO', '', NULL, '0', 'admin', '2023-06-28 21:54:20', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (283, 205, '1234未确认', '请问的萨芬', '00', '', '', '0', '', '$2a$10$839MLIRokhuyGOssERRUZetVeswu3L1fgMlG3uH9EMIhQfA/Il8qm', '', NULL, '0', 'admin', '2023-06-28 21:54:38', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (284, 205, '1233请问', '暗室逢灯', '00', '', '', '0', '', '$2a$10$yZhpIkXGnhpk9MV38AHmaOg2PIFv6NiS6isXNdM8R/LKRipVDsb5y', '', NULL, '0', 'admin', '2023-06-28 21:59:37', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (285, 205, '1233请问请问', '暗室逢灯', '00', '', '', '0', '', '$2a$10$z/YvbKJkpJrm5Ezn4cFELedWkNbpB7aFVPuyrQkMRPg6evMPds14.', '', NULL, '0', 'admin', '2023-06-28 22:02:01', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (286, 100, 'YC', '永春', '00', '', '', '0', '', '$2a$10$0rgzFfYHcznAHk2/ycfnEOURvii9/qZUXyaKp6AglJ.xSFmvchpH2', '', NULL, '0', '', '2023-06-29 04:31:55', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (287, 100, 'YC02', '永春02', '00', '', '', '0', '', '$2a$10$5yqKSCOYvL7AluDaXDqREuhXSdVNSq.wPDHfYsNwqzgxCAqu/blea', '', NULL, '0', '', '2023-06-29 04:40:59', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (288, 206, 'WYC', '王元仓', '00', '', '', '0', '', '$2a$10$Mk0vvDE1l5Q5iNGS/UfaAeeH6pCduaDShuyWDM06qUCR2wtCK.Q0W', '', NULL, '0', '', '2023-06-29 04:50:45', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (289, 205, '17800002323', '张师傅', '00', '17800002323@163.com', '17800002323', '0', '', '$2a$10$U8iEZKCkzfKF2Kcrq4dx8.c8VaoYXMcpYN5l0msNl3QVVPZXRi59y', '', NULL, '0', 'admin', '2023-07-01 03:07:53', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (290, 206, 'BYG', '搬运工', '00', '', '0', '0', '', '$2a$10$A3i6JAD0j0H/xEzPqinqPuYMO7UhoiyrOcTfTKzQQnvxJfu9X5r7C', '', NULL, '0', '', '2023-07-04 02:20:25', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (291, 206, 'BYG1', '搬运工1', '00', '', '', '0', '', '$2a$10$SAonQetgLb1o6rghTz5Rz.2Vx1Nf/FNahFocIZvQY8y8Xaoxvo8MG', '', NULL, '0', '', '2023-07-04 02:59:31', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (292, 206, 'BYG2', '搬运工2', '00', '', '', '0', '', '$2a$10$/An3GZSvz8dGnNLkg5ozkuhaYUig52VDoldnLXXxmBuEFloD69TqG', '', NULL, '0', '', '2023-07-04 03:04:39', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (293, 105, '1222333', '汪宏坤', '00', '', '15640075560', '0', '', '$2a$10$YTKzruU/DdCjDUW9kOda0eJRxxc4HXmj8/pzYE1K.IQq55YRym1ua', '', NULL, '0', 'admin', '2023-07-05 21:32:50', '1222333', '2023-07-14 03:10:36', '0', NULL);
INSERT INTO `sys_user` VALUES (294, 100, '16800008080', '李小天', '00', '', '16800008080', '0', '', '$2a$10$1K04ItOEKxxP2SGwWV7N1eBWKSyGc5uVAx3wOJzjZpCpIPChsCOxu', '', NULL, '0', 'admin', '2023-07-05 22:11:56', '', NULL, '0', '新进业务员');
INSERT INTO `sys_user` VALUES (295, 103, 'zre', 'zre', '00', '', '', '0', 'https://file.sogoun.com/tms/2023/07/19/e4a3fb31a4484eeebbe8c4939d533a6fb', '$2a$10$SroHco.Q4HN48b1yGpC5W.Rd8p7y/8zxQMrvavecgiCl.NcFlD/zW', '', NULL, '0', 'admin', '2023-07-07 03:29:05', 'admin', '2023-07-17 23:24:34', '2', NULL);
INSERT INTO `sys_user` VALUES (296, 103, '17500002525', '笑笑', '00', '', '17500002525', '0', '', '$2a$10$1e4zrP1z3w0FRPkaPn0PTOUztwBYqCmIhCbPrO5HGiM47gMiBBpOu', '', NULL, '0', 'admin', '2023-07-11 03:22:50', '', NULL, '2', '123456');
INSERT INTO `sys_user` VALUES (298, 206, 'BYG2', '搬运工2', '00', '', '', '0', '', '$2a$10$Bc/faE3Jl1RAQa1LBBzUV.2dOGGY6l26g7kPNj5a3CqJVmk49Yamy', '', NULL, '0', '', '2023-07-13 03:01:40', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (299, 106, 'nana', '娜娜', '00', '', '', '0', '', '$2a$10$e4jGO3U5WHVDP/ZkeVcV7.LcCRhNEaGQpK5jmm7Ti6W2sQ6D2XLaa', '', NULL, '0', 'admin', '2023-07-18 21:43:13', 'admin', '2023-07-18 21:44:10', '2', NULL);
INSERT INTO `sys_user` VALUES (300, 106, 'weiwei', '维维', '00', '', '', '0', 'https://file.sogoun.com/tms/2023/07/19/348e09bc2eb342849d18555d3b07518ab', '$2a$10$XiWsR3LR0zVByx9XGthPVONoGGFNeYU64otZEo7UvBxia88wvtK8S', '', NULL, '0', 'admin', '2023-07-18 21:43:43', 'admin', '2023-07-18 21:44:21', '2', NULL);
INSERT INTO `sys_user` VALUES (301, 105, 'zre123', '赵荣额', '00', '', '', '0', '', '$2a$10$sgH2/DUm3Lc6NJyjnoxYS.bY/LARe.c8bx7SvXViu2foL/NTP809O', '', NULL, '0', 'admin', '2023-07-19 02:56:31', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (302, 105, 'zre', 'zre', '00', '', '', '0', '', '$2a$10$L1awj1E9uunwPYqENuVmfOygbmBUiSF9nZvVISy2H14cgB8VVG2ge', '', NULL, '0', 'admin', '2023-07-19 03:07:59', 'admin', '2023-07-19 03:13:15', '2', NULL);
INSERT INTO `sys_user` VALUES (303, 106, 'nana', '娜娜', '00', '', '', '0', '', '$2a$10$8u8HpxchAGK8N1.zFdAM5.dR6XkRXIaaNBlV.fyHhdtcA9f.Fzbkm', '', NULL, '0', 'admin', '2023-07-19 03:10:27', 'admin', '2023-07-19 03:13:31', '0', NULL);
INSERT INTO `sys_user` VALUES (304, 106, 'weiwei', '维维', '00', '', '', '0', 'https://file.sogoun.com/tms/2023/07/19/348e09bc2eb342849d18555d3b07518ab', '$2a$10$aIqyC1kLirybTLPcRzm4rOFglKHSxlnEVMw0lVtNFcacwzPv7JQj6', '', NULL, '0', 'admin', '2023-07-19 03:11:34', 'admin', '2023-07-19 03:13:38', '0', NULL);
INSERT INTO `sys_user` VALUES (305, 105, 'zre', 'zre', '00', '', '', '0', '', '$2a$10$u7jSxCgOx9NoxSvV3ZDbculIdZk.qFzq.NghjLZXR.NrFHlv/dZJe', '', NULL, '0', 'admin', '2023-07-19 03:26:58', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (306, 105, 'zre', 'zre', '00', '', '', '0', '', '$2a$10$soluzHjAjcFNGpscFtymhOeir6.8g13I3BveHC41mHIZGOz4T.2Rm', '', NULL, '0', 'admin', '2023-07-19 03:27:38', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (307, 105, 'zre', 'zre', '00', '', '', '0', '', '$2a$10$zbv1zhArgiaaj8moqFRdhePTqX8Qm88brhnzCH6A.d/HPmk/pvIye', '', NULL, '0', 'admin', '2023-07-19 03:28:20', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (308, 105, 'zre', 'zre', '00', '', '', '0', '', '$2a$10$q4x44szbu9QP4g/vZDDbZetMRl0sSFnSFpjdkxAiRvmJar4yy9Yxy', '', NULL, '0', 'admin', '2023-07-19 03:28:46', 'admin', '2023-07-19 03:38:56', '2', NULL);
INSERT INTO `sys_user` VALUES (309, 206, '11qq11', '11qq11', '00', '', '', '0', '', '$2a$10$3ByKVRGq8Wnlyp1.4WhUdeLd0LpiEugRD1TZcrG9LlOCSoxIr56aK', '', NULL, '0', '', '2023-07-19 03:29:52', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (310, 105, 'zre1', 'zre', '00', '', '', '0', 'https://file.sogoun.com/tms/2023/07/19/5e650ec4ddf144d7bec458317ebb23ecb', '$2a$10$1ofO65xnQE/XHBRrauaoN.kmdAJ7m0YO326v8jYd6iCq74WEjK72C', '', NULL, '0', 'admin', '2023-07-19 03:39:35', 'admin', '2023-07-19 03:39:52', '2', NULL);
INSERT INTO `sys_user` VALUES (311, 105, 'zre', 'zre', '00', '', '', '0', '', '$2a$10$faLefHqncHdn03m5./WPe.JC0U32OQhC2raJLv/YMO95ypBRTKlY6', '', NULL, '0', 'admin', '2023-07-19 04:32:46', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (312, 105, 'zre111', 'zre', '00', '', '', '0', '', '$2a$10$RblaiXPYcIB43iXjBc9zheJE3J5zGFaQUBwJHGzHJMaXGbsin3Fjy', '', NULL, '0', 'admin', '2023-07-19 04:33:25', '', NULL, '2', NULL);
INSERT INTO `sys_user` VALUES (313, 105, 're', 're', '00', '', '', '0', '', '$2a$10$0Lm2/FOnZsNuzVg6j9OKGejbUuiOyybvZzIzwidpb8oOSmgYHyq3C', '', NULL, '0', 'admin', '2023-07-19 04:38:51', 'admin', '2023-07-19 04:39:13', '2', NULL);
INSERT INTO `sys_user` VALUES (314, 105, 'zre222', 'zre', '00', '', '', '0', 'https://file.sogoun.com/tms/2023/07/20/1f2f6d3b27064b89a2a0e8494f911525b', '$2a$10$2/d9KvPq0o62H5B18ekbHeJpQ2a9QdzSlDHI1mLrrpw8yMmHdIQxa', '', NULL, '0', 'admin', '2023-07-19 05:08:18', 'admin', '2023-07-23 21:30:11', '0', NULL);
INSERT INTO `sys_user` VALUES (315, 206, 'cz2023', 'cz2023', '00', '', '', '0', '', '$2a$10$8a/5jmcit7cbReIuXC44Yekyy9GLtfurx4DxhaO5lEUfhMAYpScra', '', NULL, '0', 'admin', '2023-07-20 03:56:05', '', NULL, '0', NULL);
INSERT INTO `sys_user` VALUES (316, 100, 'gsf', 'gsf', '00', '', '', '0', '', '$2a$10$VS9z5ES4SMxHTGV8P5jMweMYWQXF8.p7D38U72J5q1wh6.Q8tpqgi', '', NULL, '0', 'admin', '2023-07-23 21:25:10', '', NULL, '0', NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (101, 2);
INSERT INTO `sys_user_role` VALUES (101, 4);
INSERT INTO `sys_user_role` VALUES (104, 6);
INSERT INTO `sys_user_role` VALUES (105, 6);
INSERT INTO `sys_user_role` VALUES (225, 2);
INSERT INTO `sys_user_role` VALUES (230, 3);
INSERT INTO `sys_user_role` VALUES (230, 6);
INSERT INTO `sys_user_role` VALUES (235, 3);
INSERT INTO `sys_user_role` VALUES (235, 6);
INSERT INTO `sys_user_role` VALUES (264, 7);
INSERT INTO `sys_user_role` VALUES (265, 7);
INSERT INTO `sys_user_role` VALUES (266, 7);
INSERT INTO `sys_user_role` VALUES (267, 7);
INSERT INTO `sys_user_role` VALUES (268, 7);
INSERT INTO `sys_user_role` VALUES (269, 7);
INSERT INTO `sys_user_role` VALUES (287, 9);
INSERT INTO `sys_user_role` VALUES (288, 9);
INSERT INTO `sys_user_role` VALUES (289, 7);
INSERT INTO `sys_user_role` VALUES (293, 10);
INSERT INTO `sys_user_role` VALUES (294, 3);
INSERT INTO `sys_user_role` VALUES (303, 8);
INSERT INTO `sys_user_role` VALUES (304, 5);
INSERT INTO `sys_user_role` VALUES (314, 4);
INSERT INTO `sys_user_role` VALUES (315, 9);
INSERT INTO `sys_user_role` VALUES (316, 4);

SET FOREIGN_KEY_CHECKS = 1;
