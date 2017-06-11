/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : stms

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-06-11 17:49:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_button
-- ----------------------------
DROP TABLE IF EXISTS `t_button`;
CREATE TABLE `t_button` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) NOT NULL,
  `button_id` int(11) NOT NULL,
  `button_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `review_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_button
-- ----------------------------
INSERT INTO `t_button` VALUES ('1', '12', '100', '新增', '2017-06-11 15:33:03', '2017-06-11 15:42:26');
INSERT INTO `t_button` VALUES ('2', '12', '101', '编辑', '2017-06-11 15:43:03', '2017-06-11 15:43:03');
INSERT INTO `t_button` VALUES ('3', '12', '102', '审核/撤销', '2017-06-11 15:43:17', '2017-06-11 15:43:49');
INSERT INTO `t_button` VALUES ('4', '12', '103', '删除', '2017-06-11 15:43:35', '2017-06-11 15:43:35');
INSERT INTO `t_button` VALUES ('5', '12', '104', '查询', '2017-06-11 15:44:25', '2017-06-11 15:44:25');
INSERT INTO `t_button` VALUES ('6', '13', '105', '新增', '2017-06-11 15:44:47', '2017-06-11 15:44:47');
INSERT INTO `t_button` VALUES ('7', '13', '106', '删除', '2017-06-11 15:45:09', '2017-06-11 15:45:09');
INSERT INTO `t_button` VALUES ('8', '13', '107', '编辑', '2017-06-11 15:45:29', '2017-06-11 15:45:29');
INSERT INTO `t_button` VALUES ('9', '13', '108', '查询', '2017-06-11 15:45:42', '2017-06-11 15:45:42');

-- ----------------------------
-- Table structure for t_company
-- ----------------------------
DROP TABLE IF EXISTS `t_company`;
CREATE TABLE `t_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(50) NOT NULL,
  `state` tinyint(1) NOT NULL DEFAULT '1',
  `remark` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `review_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_company
-- ----------------------------
INSERT INTO `t_company` VALUES ('1', '尚德', '1', '', '2017-06-06 16:25:08', '2017-06-06 16:25:08');
INSERT INTO `t_company` VALUES ('2', '申通运输公司', '1', '申通运输公司', '2017-06-06 17:27:24', '2017-06-06 17:27:24');
INSERT INTO `t_company` VALUES ('3', '中通运输公司', '1', '中通运输公司', '2017-06-06 17:28:04', '2017-06-06 17:28:04');
INSERT INTO `t_company` VALUES ('4', '圆通运输公司', '1', '圆通运输公司', '2017-06-06 16:17:05', '2017-06-06 16:30:15');
INSERT INTO `t_company` VALUES ('5', '天天运输公司', '0', '', '2017-06-07 11:19:07', '2017-06-07 11:19:07');
INSERT INTO `t_company` VALUES ('6', 'AA', '1', 'AA', '2017-06-09 13:22:11', '2017-06-09 13:22:11');

-- ----------------------------
-- Table structure for t_department
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `department_name` varchar(50) NOT NULL COMMENT '部门名',
  `company_id` int(11) NOT NULL COMMENT '所属公司',
  `state` tinyint(1) NOT NULL DEFAULT '1',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `review_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_department
-- ----------------------------
INSERT INTO `t_department` VALUES ('1', '销售部', '2', '1', '11', '2017-06-07 09:32:33', '2017-06-07 10:24:59');
INSERT INTO `t_department` VALUES ('2', 'Java部', '1', '0', '', '2017-06-07 11:11:42', '2017-06-07 11:11:42');
INSERT INTO `t_department` VALUES ('3', '商务部', '3', '1', '', '2017-06-07 11:12:00', '2017-06-07 11:12:00');
INSERT INTO `t_department` VALUES ('4', '商务2部', '3', '1', '', '2017-06-07 13:36:10', '2017-06-07 13:36:10');

-- ----------------------------
-- Table structure for t_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `t_dictionary`;
CREATE TABLE `t_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(50) NOT NULL,
  `key` varchar(50) NOT NULL,
  `value` varchar(50) NOT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dictionary
-- ----------------------------
INSERT INTO `t_dictionary` VALUES ('1', '男', 'gender', '1', '男性');
INSERT INTO `t_dictionary` VALUES ('2', '女', 'gender', '0', '女性');

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `module_name` varchar(50) NOT NULL COMMENT '模块名',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `url` varchar(255) DEFAULT NULL COMMENT '路径',
  `pid` int(11) DEFAULT NULL COMMENT '父级ID',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `review_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('1', '物流公司管理', null, '', '0', '物流公司管理', '2017-06-06 11:08:09', '2017-06-06 11:28:26');
INSERT INTO `t_menu` VALUES ('2', '进货管理', null, '', '0', null, '2017-06-06 11:16:46', '2017-06-06 11:16:46');
INSERT INTO `t_menu` VALUES ('3', '国际运输管理', null, '', '0', null, '2017-06-06 11:17:00', '2017-06-06 11:17:00');
INSERT INTO `t_menu` VALUES ('4', '国内运输管理', null, '', '0', null, '2017-06-06 11:17:24', '2017-06-06 11:17:24');
INSERT INTO `t_menu` VALUES ('5', '送货单管理', null, '', '0', null, '2017-06-06 11:17:40', '2017-06-06 11:17:40');
INSERT INTO `t_menu` VALUES ('6', '仓储管理', null, '', '0', null, '2017-06-06 11:17:55', '2017-06-06 11:17:55');
INSERT INTO `t_menu` VALUES ('7', '手册管理', null, '', '0', null, '2017-06-06 11:18:08', '2017-06-06 11:18:08');
INSERT INTO `t_menu` VALUES ('8', '运价管理', null, '', '0', null, '2017-06-06 11:18:23', '2017-06-06 11:18:23');
INSERT INTO `t_menu` VALUES ('9', '共享数据库管理', null, '', '0', null, '2017-06-06 11:18:44', '2017-06-06 11:18:44');
INSERT INTO `t_menu` VALUES ('10', '报表中心', null, '', '0', null, '2017-06-06 11:18:58', '2017-06-06 11:18:58');
INSERT INTO `t_menu` VALUES ('11', '系统管理', null, '', '0', null, '2017-06-06 11:19:16', '2017-06-06 11:19:16');
INSERT INTO `t_menu` VALUES ('12', '物流公司资质管理', null, '/supplier/quality', '1', '物流公司资质管理', '2017-06-06 16:51:00', '2017-06-06 16:51:00');
INSERT INTO `t_menu` VALUES ('13', '物流公司信息管理', null, '/supplier/info', '1', '物流公司信息管理', '2017-06-06 17:20:34', '2017-06-06 17:20:34');
INSERT INTO `t_menu` VALUES ('14', '物流公司考核标准', null, '/supplier/level', '1', '物流公司考核标准', '2017-06-06 17:21:21', '2017-06-06 17:21:21');
INSERT INTO `t_menu` VALUES ('15', '物流公司月度考核', null, '/supplier/month', '1', '物流公司月度考核', '2017-06-06 17:21:59', '2017-06-06 17:21:59');
INSERT INTO `t_menu` VALUES ('16', '物流公司年度考核', null, '/supplier/year', '1', '物流公司年度考核', '2017-06-06 17:22:38', '2017-06-06 17:22:38');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `role_type` varchar(50) NOT NULL COMMENT '角色类型',
  `company_id` int(11) NOT NULL COMMENT '公司ID',
  `remark` varchar(100) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `review_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '超级管理员', '1', '超级管理员', '2017-06-07 16:25:36', '2017-06-07 16:25:36');
INSERT INTO `t_role` VALUES ('2', '管理员', '1', '管理员', '2017-06-07 16:39:03', '2017-06-07 16:39:03');
INSERT INTO `t_role` VALUES ('3', '仓管', '3', '', '2017-06-07 16:41:15', '2017-06-07 16:42:48');
INSERT INTO `t_role` VALUES ('4', '总经理', '2', '', '2017-06-07 16:42:11', '2017-06-07 16:42:11');
INSERT INTO `t_role` VALUES ('5', '董事长', '5', '', '2017-06-07 16:42:30', '2017-06-07 16:42:30');

-- ----------------------------
-- Table structure for t_role_button
-- ----------------------------
DROP TABLE IF EXISTS `t_role_button`;
CREATE TABLE `t_role_button` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `button_ids` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `review_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_button
-- ----------------------------

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `menu_ids` varchar(1024) NOT NULL,
  `create_time` datetime NOT NULL,
  `review_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES ('1', '1', '1,12,13,14,15,16,2', '2017-06-08 17:09:13', '2017-06-08 17:09:13');
INSERT INTO `t_role_menu` VALUES ('2', '4', '0,1,12', '2017-06-11 17:32:38', '2017-06-11 17:32:38');

-- ----------------------------
-- Table structure for t_supplier
-- ----------------------------
DROP TABLE IF EXISTS `t_supplier`;
CREATE TABLE `t_supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `supplier_id` int(11) NOT NULL,
  `contract_no` varchar(20) NOT NULL COMMENT '合同号',
  `supplier_level` varchar(10) DEFAULT NULL COMMENT '货代等级',
  `supplier_field` varchar(20) DEFAULT NULL COMMENT '业务范围',
  `supplier_years` int(2) DEFAULT NULL COMMENT '合作年限',
  `supplier_bail` decimal(10,4) DEFAULT NULL COMMENT '保证金',
  `contact` varchar(20) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `review_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_supplier
-- ----------------------------
INSERT INTO `t_supplier` VALUES ('2', '3', '123ddd', 'B', '运输', '5', '3434.8763', '贺知章', '13069876567', 'www@jj.com', '不错', '2017-05-31 11:58:18', '2017-06-09 14:27:47');
INSERT INTO `t_supplier` VALUES ('3', '2', '8273nhfs', 'A', '运输', '2', '234.9378', '陈莲眉', '13098765432', 'cml@qq.com', '不错', '2017-05-31 13:13:45', '2017-06-05 11:04:07');
INSERT INTO `t_supplier` VALUES ('4', '4', '333', 'A', '运输', '2', '123.0000', '何建国', '13040506060', 'hjg@qq.com', '', '2017-06-09 14:28:13', '2017-06-09 14:30:58');

-- ----------------------------
-- Table structure for t_supplier_level
-- ----------------------------
DROP TABLE IF EXISTS `t_supplier_level`;
CREATE TABLE `t_supplier_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `supplier_level` varchar(10) DEFAULT NULL COMMENT '等级',
  `supplier_score` varchar(20) DEFAULT NULL COMMENT '得分',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `review_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_supplier_level
-- ----------------------------
INSERT INTO `t_supplier_level` VALUES ('1', 'AA', '96~100', '2017-05-27 14:48:47', '2017-05-27 14:48:49');
INSERT INTO `t_supplier_level` VALUES ('2', 'A', '90~95', '2017-05-27 14:49:26', '2017-05-27 14:49:29');
INSERT INTO `t_supplier_level` VALUES ('3', 'B', '80~89', '2017-05-27 14:49:26', '2017-05-27 14:49:29');
INSERT INTO `t_supplier_level` VALUES ('4', 'C', '70~79', '2017-05-27 14:49:26', '2017-05-27 14:49:29');
INSERT INTO `t_supplier_level` VALUES ('5', 'D', '0~69', '2017-05-27 14:49:26', '2017-05-27 14:49:29');

-- ----------------------------
-- Table structure for t_supplier_month_assess
-- ----------------------------
DROP TABLE IF EXISTS `t_supplier_month_assess`;
CREATE TABLE `t_supplier_month_assess` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `year` year(4) DEFAULT NULL COMMENT '年份',
  `month` int(2) DEFAULT NULL COMMENT '月份',
  `month_score` int(2) NOT NULL COMMENT '月度得分',
  `supplier_level` varchar(10) DEFAULT NULL,
  `supplier_id` int(11) NOT NULL COMMENT '供应商ID',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `review_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_supplier_month_assess
-- ----------------------------
INSERT INTO `t_supplier_month_assess` VALUES ('4', '2010', '10', '19', 'AA', '2', '', '2017-06-01 11:22:18', '2017-06-09 14:56:06');
INSERT INTO `t_supplier_month_assess` VALUES ('5', '2010', '12', '99', 'AA', '2', '', '2017-06-01 14:31:34', '2017-06-05 11:06:04');
INSERT INTO `t_supplier_month_assess` VALUES ('7', '2017', '12', '77', 'C', '3', '', '2017-06-01 14:46:30', '2017-06-05 11:06:29');
INSERT INTO `t_supplier_month_assess` VALUES ('8', '2015', '12', '77', 'D', '2', '', '2017-06-01 15:13:28', '2017-06-05 11:07:32');
INSERT INTO `t_supplier_month_assess` VALUES ('9', '2016', '12', '87', 'B', '3', '', '2017-06-01 15:13:54', '2017-06-05 11:07:20');
INSERT INTO `t_supplier_month_assess` VALUES ('10', '2017', '7', '88', 'B', '2', '88', '2017-06-02 08:02:12', '2017-06-02 17:42:18');
INSERT INTO `t_supplier_month_assess` VALUES ('11', '2017', '8', '88', 'B', '2', '88', '2017-06-02 08:02:12', '2017-06-02 17:42:09');
INSERT INTO `t_supplier_month_assess` VALUES ('12', '2017', '6', '90', 'A', '2', '', '2017-06-02 17:42:48', '2017-06-02 17:42:48');
INSERT INTO `t_supplier_month_assess` VALUES ('13', '2017', '5', '99', 'A', '2', '', '2017-06-02 17:43:10', '2017-06-09 14:55:27');
INSERT INTO `t_supplier_month_assess` VALUES ('14', '2017', '4', '99', 'AA', '2', '', '2017-06-02 17:44:05', '2017-06-02 17:44:05');
INSERT INTO `t_supplier_month_assess` VALUES ('15', '2017', '3', '99', 'AA', '2', '', '2017-06-02 17:44:30', '2017-06-02 17:44:30');
INSERT INTO `t_supplier_month_assess` VALUES ('16', '2017', '2', '90', 'A', '2', '', '2017-06-02 17:45:28', '2017-06-02 17:45:28');
INSERT INTO `t_supplier_month_assess` VALUES ('17', '2017', '1', '90', 'A', '2', '', '2017-06-02 17:45:52', '2017-06-02 17:45:52');
INSERT INTO `t_supplier_month_assess` VALUES ('18', '2017', '9', '99', 'AA', '2', '', '2017-06-02 17:46:23', '2017-06-02 17:46:23');
INSERT INTO `t_supplier_month_assess` VALUES ('19', '2017', '10', '88', 'B', '2', '88', '2017-06-03 14:48:07', '2017-06-03 14:48:07');
INSERT INTO `t_supplier_month_assess` VALUES ('20', '2017', '11', '99', 'AA', '2', '99', '2017-06-03 14:48:23', '2017-06-03 14:48:23');
INSERT INTO `t_supplier_month_assess` VALUES ('21', '2017', '12', '91', 'A', '2', '99', '2017-06-03 14:48:36', '2017-06-09 14:56:31');
INSERT INTO `t_supplier_month_assess` VALUES ('22', '2015', '11', '88', 'B', '2', '88', '2017-06-05 11:08:12', '2017-06-05 11:09:48');
INSERT INTO `t_supplier_month_assess` VALUES ('23', '2010', '11', '77', 'C', '2', '77', '2017-06-05 11:09:12', '2017-06-05 11:09:12');
INSERT INTO `t_supplier_month_assess` VALUES ('26', '2017', '11', '66', 'D', '3', '', '2017-06-05 16:45:56', '2017-06-05 16:45:56');
INSERT INTO `t_supplier_month_assess` VALUES ('27', '2009', '7', '77', 'C', '4', '', '2017-06-09 14:56:53', '2017-06-09 14:56:53');

-- ----------------------------
-- Table structure for t_supplier_qualification
-- ----------------------------
DROP TABLE IF EXISTS `t_supplier_qualification`;
CREATE TABLE `t_supplier_qualification` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `registration_code` varchar(20) NOT NULL,
  `supplier_id` int(11) NOT NULL COMMENT '供应商ID',
  `short_name` varchar(20) DEFAULT NULL,
  `state` int(1) NOT NULL DEFAULT '2' COMMENT '资质状态',
  `review_file` varchar(500) DEFAULT NULL COMMENT '审核附件',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `review_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_supplier_qualification
-- ----------------------------
INSERT INTO `t_supplier_qualification` VALUES ('47', '123abc', '4', '圆通', '2', null, '', '2017-05-27 15:33:41', '2017-06-09 14:30:58');
INSERT INTO `t_supplier_qualification` VALUES ('48', '123abc', '2', '申通', '1', null, '', '2017-05-27 15:35:01', '2017-05-27 15:35:01');
INSERT INTO `t_supplier_qualification` VALUES ('49', '123abc', '3', '中通', '2', null, '', '2017-05-27 16:10:37', '2017-06-09 14:27:47');
INSERT INTO `t_supplier_qualification` VALUES ('50', '123abc', '6', 'A', '1', null, '', '2017-06-09 13:23:14', '2017-06-09 13:23:14');

-- ----------------------------
-- Table structure for t_supplier_year_assess
-- ----------------------------
DROP TABLE IF EXISTS `t_supplier_year_assess`;
CREATE TABLE `t_supplier_year_assess` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `year_score` int(2) NOT NULL COMMENT '年度得分',
  `year` year(4) NOT NULL COMMENT '年份',
  `supplier_id` int(11) NOT NULL COMMENT '供应商ID',
  `supplier_level` varchar(10) NOT NULL COMMENT '等级',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `review_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_supplier_year_assess
-- ----------------------------
INSERT INTO `t_supplier_year_assess` VALUES ('4', '88', '2015', '2', 'B', null, '2017-06-02 10:40:09', '2017-06-09 15:04:50');
INSERT INTO `t_supplier_year_assess` VALUES ('18', '87', '2016', '3', 'AA', null, '2017-06-05 15:57:39', '2017-06-05 16:45:08');
INSERT INTO `t_supplier_year_assess` VALUES ('21', '94', '2017', '2', 'A', null, '2017-06-05 16:46:42', '2017-06-05 16:46:42');
INSERT INTO `t_supplier_year_assess` VALUES ('22', '72', '2017', '3', 'C', null, '2017-06-05 16:46:42', '2017-06-05 16:46:42');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `company_id` int(11) NOT NULL,
  `department_id` int(11) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  `review_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '123456', '1', null, '1', '2017-06-08 10:18:15', '2017-06-08 10:18:15');
INSERT INTO `t_user` VALUES ('2', 'user1', '123456', '3', null, '3', '2017-06-08 10:50:05', '2017-06-08 11:15:01');

-- ----------------------------
-- Table structure for t_user_log
-- ----------------------------
DROP TABLE IF EXISTS `t_user_log`;
CREATE TABLE `t_user_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `ip` varchar(15) NOT NULL,
  `agent` varchar(100) NOT NULL,
  `login_time` datetime NOT NULL,
  `logout_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_log
-- ----------------------------
