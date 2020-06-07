/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : dev

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 07/06/2020 22:24:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for delegate_task
-- ----------------------------
DROP TABLE IF EXISTS `delegate_task`;
CREATE TABLE `delegate_task` (
  `id` bigint(20) NOT NULL,
  `state` int(255) DEFAULT NULL,
  `flow_task_id` bigint(20) DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `complete_time` datetime DEFAULT NULL,
  `overdue_time` datetime DEFAULT NULL,
  `overdue_flag` int(255) DEFAULT NULL,
  `review_state` int(255) DEFAULT NULL,
  `review_flag` int(255) DEFAULT NULL,
  `is_active` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for flow_task
-- ----------------------------
DROP TABLE IF EXISTS `flow_task`;
CREATE TABLE `flow_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `state` int(255) NOT NULL,
  `module` int(255) DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  `insert_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `complete_time` datetime DEFAULT NULL,
  `overdue_time` datetime DEFAULT NULL,
  `overdue_flag` int(255) DEFAULT NULL,
  `review_state` int(255) DEFAULT NULL,
  `review_flag` int(255) DEFAULT NULL,
  `delegate_state` int(255) DEFAULT NULL,
  `delegate_flag` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for review_task
-- ----------------------------
DROP TABLE IF EXISTS `review_task`;
CREATE TABLE `review_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `out_task_type` int(255) DEFAULT NULL COMMENT '任务类型，0-flow, 1-delegate',
  `out_task_id` int(11) NOT NULL COMMENT '任务ID',
  `out_task_state` int(255) DEFAULT NULL COMMENT '任务状态',
  `owner_id` bigint(20) NOT NULL COMMENT '审核人',
  `insert_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `complete_time` datetime DEFAULT NULL COMMENT '审核时间',
  `overdue_time` datetime DEFAULT NULL COMMENT '逾期时间',
  `overdue_flag` int(255) DEFAULT NULL COMMENT '逾期标记',
  `is_active` int(255) DEFAULT '1' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for transfer_history
-- ----------------------------
DROP TABLE IF EXISTS `transfer_history`;
CREATE TABLE `transfer_history` (
  `id` bigint(20) NOT NULL,
  `task_id` int(11) DEFAULT NULL COMMENT '关联任务ID',
  `module` int(255) NOT NULL COMMENT '任务模型，来料或问题等',
  `task_state` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '各任务的节点状态',
  `p_owner` bigint(255) DEFAULT NULL COMMENT '当前执行人',
  `n_owner` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '目标执行人',
  `insert_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
