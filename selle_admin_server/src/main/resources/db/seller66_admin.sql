-- ----------------------------
-- 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `seller66_admin` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `seller66_admin`;

-- ----------------------------
-- 管理员表
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin`;
CREATE TABLE `ums_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `icon` varchar(500) DEFAULT NULL COMMENT '头像',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(200) DEFAULT NULL COMMENT '昵称',
  `note` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` int(1) DEFAULT '1' COMMENT '帐号启用状态：0->禁用；1->启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

-- ----------------------------
-- 初始化管理员数据
-- ----------------------------
INSERT INTO `ums_admin` (`id`, `username`, `password`, `icon`, `email`, `nick_name`, `note`, `create_time`, `login_time`, `status`) VALUES
(1, 'admin', '$2a$10$NZ5o7r2E.ayT2ZoxgjlI.eJ6OEYqjH7INR/F.mXDbjZJi9HF0YCVG', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 'admin@seller66.com', '超级管理员', '超级管理员', '2023-02-04 16:13:33', NULL, 1);

-- ----------------------------
-- 角色表
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(1) DEFAULT '1' COMMENT '启用状态：0->禁用；1->启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- 初始化角色数据
-- ----------------------------
INSERT INTO `ums_role` (`id`, `name`, `description`, `create_time`, `status`) VALUES
(1, 'admin', '超级管理员', '2023-02-04 16:14:16', 1),
(2, 'editor', '编辑员', '2023-02-04 16:14:27', 1);

-- ----------------------------
-- 管理员角色关系表
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin_role_relation`;
CREATE TABLE `ums_admin_role_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) DEFAULT NULL COMMENT '管理员ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='管理员角色关系表';

-- ----------------------------
-- 初始化管理员角色关系数据
-- ----------------------------
INSERT INTO `ums_admin_role_relation` (`id`, `admin_id`, `role_id`) VALUES
(1, 1, 1); 