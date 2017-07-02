/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : stms

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-06-30 20:53:54
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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_button
-- ----------------------------
INSERT INTO `t_button` VALUES ('1', '12', '100', '新增', '2017-06-11 15:33:03', '2017-06-26 13:48:32');
INSERT INTO `t_button` VALUES ('2', '12', '101', '编辑', '2017-06-11 15:43:03', '2017-06-26 13:48:39');
INSERT INTO `t_button` VALUES ('3', '12', '102', '审核/撤销', '2017-06-11 15:43:17', '2017-06-11 15:43:49');
INSERT INTO `t_button` VALUES ('4', '12', '103', '删除', '2017-06-11 15:43:35', '2017-06-11 15:43:35');
INSERT INTO `t_button` VALUES ('5', '12', '104', '查询', '2017-06-11 15:44:25', '2017-06-11 15:44:25');
INSERT INTO `t_button` VALUES ('6', '13', '105', '新增', '2017-06-11 15:44:47', '2017-06-11 15:44:47');
INSERT INTO `t_button` VALUES ('7', '13', '106', '删除', '2017-06-11 15:45:09', '2017-06-11 15:45:09');
INSERT INTO `t_button` VALUES ('8', '13', '107', '编辑', '2017-06-11 15:45:29', '2017-06-11 15:45:29');
INSERT INTO `t_button` VALUES ('9', '13', '108', '查询', '2017-06-11 15:45:42', '2017-06-11 15:45:42');
INSERT INTO `t_button` VALUES ('10', '14', '109', '新增', '2017-06-12 13:04:21', '2017-06-12 13:04:21');
INSERT INTO `t_button` VALUES ('11', '14', '110', '删除', '2017-06-12 13:05:28', '2017-06-12 13:05:28');
INSERT INTO `t_button` VALUES ('12', '14', '111', '编辑', '2017-06-12 13:06:48', '2017-06-12 13:06:48');
INSERT INTO `t_button` VALUES ('13', '14', '112', '批量删除', '2017-06-12 13:07:09', '2017-06-12 13:07:09');
INSERT INTO `t_button` VALUES ('14', '15', '113', '新增', '2017-06-12 13:07:53', '2017-06-12 13:07:53');
INSERT INTO `t_button` VALUES ('15', '15', '114', '删除', '2017-06-12 13:08:10', '2017-06-12 13:08:10');
INSERT INTO `t_button` VALUES ('16', '15', '115', '编辑', '2017-06-12 13:08:26', '2017-06-12 13:08:26');
INSERT INTO `t_button` VALUES ('17', '15', '116', '查询', '2017-06-12 13:09:13', '2017-06-12 13:09:13');
INSERT INTO `t_button` VALUES ('18', '15', '117', '批量删除', '2017-06-12 13:09:43', '2017-06-12 13:09:43');
INSERT INTO `t_button` VALUES ('19', '15', '118', '导入Excel', '2017-06-12 13:10:02', '2017-06-12 13:10:02');
INSERT INTO `t_button` VALUES ('20', '15', '119', '查看附件', '2017-06-12 13:10:22', '2017-06-12 13:10:22');
INSERT INTO `t_button` VALUES ('21', '16', '120', '新增', '2017-06-12 13:12:47', '2017-06-12 13:12:47');
INSERT INTO `t_button` VALUES ('22', '16', '121', '删除', '2017-06-12 13:13:04', '2017-06-12 13:13:04');
INSERT INTO `t_button` VALUES ('23', '16', '122', '编辑', '2017-06-12 13:13:21', '2017-06-12 13:13:21');
INSERT INTO `t_button` VALUES ('24', '16', '123', '查询', '2017-06-12 13:13:38', '2017-06-12 13:13:38');
INSERT INTO `t_button` VALUES ('25', '16', '124', '批量删除', '2017-06-12 13:14:09', '2017-06-12 13:14:09');
INSERT INTO `t_button` VALUES ('26', '16', '125', '评分', '2017-06-12 13:14:43', '2017-06-12 13:14:43');

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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_company
-- ----------------------------
INSERT INTO `t_company` VALUES ('1', '尚德', '0', '', '2017-06-06 16:25:08', '2017-06-06 16:25:08');
INSERT INTO `t_company` VALUES ('2', '申通运输公司', '1', '申通运输公司', '2017-06-06 17:27:24', '2017-06-06 17:27:24');
INSERT INTO `t_company` VALUES ('3', '中通运输公司', '1', '中通运输公司', '2017-06-06 17:28:04', '2017-06-06 17:28:04');
INSERT INTO `t_company` VALUES ('4', '圆通运输公司', '1', '圆通运输公司', '2017-06-06 16:17:05', '2017-06-06 16:30:15');
INSERT INTO `t_company` VALUES ('5', '天天运输公司', '0', '', '2017-06-07 11:19:07', '2017-06-07 11:19:07');
INSERT INTO `t_company` VALUES ('6', 'BB', '1', 'BB', '2017-06-09 13:22:11', '2017-06-28 14:21:22');
INSERT INTO `t_company` VALUES ('7', '华泰运输', '1', '江苏', '2017-06-13 16:40:46', '2017-06-13 16:40:46');
INSERT INTO `t_company` VALUES ('8', '百达通物流', '1', '啥的', '2017-06-26 13:21:09', '2017-06-26 13:21:09');
INSERT INTO `t_company` VALUES ('9', '万事顺物流', '1', '', '2017-06-26 13:22:07', '2017-06-26 13:22:07');
INSERT INTO `t_company` VALUES ('10', '哈儿物流', '1', '', '2017-06-26 13:23:27', '2017-06-26 13:23:27');
INSERT INTO `t_company` VALUES ('11', '和顺物流', '1', '上海龙达', '2017-06-26 13:23:07', '2017-06-26 13:23:07');
INSERT INTO `t_company` VALUES ('12', '测试看看', '1', '111', '2017-06-27 15:33:19', '2017-06-27 15:33:19');

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
INSERT INTO `t_department` VALUES ('1', '销售部', '2', '1', '11', '2017-06-07 09:32:33', '2017-06-26 13:32:05');
INSERT INTO `t_department` VALUES ('2', 'Java部', '1', '0', '', '2017-06-07 11:11:42', '2017-06-07 11:11:42');
INSERT INTO `t_department` VALUES ('3', '商务部', '3', '0', '', '2017-06-07 11:12:00', '2017-06-07 11:12:00');
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dictionary
-- ----------------------------
INSERT INTO `t_dictionary` VALUES ('1', '男', 'gender', '1', '男性');
INSERT INTO `t_dictionary` VALUES ('2', '女', 'gender', '0', '女性');
INSERT INTO `t_dictionary` VALUES ('3', '女', 'gender', '0', '女性');
INSERT INTO `t_dictionary` VALUES ('4', '男', 'gender', '0', '女性');
INSERT INTO `t_dictionary` VALUES ('5', '女', 'gender', '0', '女性');
INSERT INTO `t_dictionary` VALUES ('6', '女', 'gender', '0', '女性');
INSERT INTO `t_dictionary` VALUES ('7', '女', 'gender', '0', '女性');
INSERT INTO `t_dictionary` VALUES ('8', '男', 'gender', '0', '女性');
INSERT INTO `t_dictionary` VALUES ('9', '女', 'gender', '0', '女性');
INSERT INTO `t_dictionary` VALUES ('10', '女', 'gender', '0', '女性');
INSERT INTO `t_dictionary` VALUES ('11', '男', 'gender', '1', '男性');
INSERT INTO `t_dictionary` VALUES ('12', '男', 'gender', '0', '男性');
INSERT INTO `t_dictionary` VALUES ('13', '男', 'gender', '1', '男性');

-- ----------------------------
-- Table structure for t_manual_import
-- ----------------------------
DROP TABLE IF EXISTS `t_manual_import`;
CREATE TABLE `t_manual_import` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `import_num` varchar(15) DEFAULT NULL,
  `import_record_num` varchar(15) DEFAULT NULL,
  `import_code` varchar(15) DEFAULT NULL,
  `import_name` varchar(20) DEFAULT NULL,
  `import_specification` varchar(10) DEFAULT NULL,
  `main_material` varchar(15) DEFAULT NULL,
  `import_unit` varchar(15) DEFAULT NULL,
  `import_fixed_unit` varchar(15) DEFAULT NULL,
  `import_report_num` decimal(10,2) DEFAULT NULL,
  `import_report_unit_price` decimal(14,4) DEFAULT NULL,
  `import_report_total_price` decimal(14,4) DEFAULT NULL,
  `currency_system` varchar(10) DEFAULT NULL,
  `import_pro_market` varchar(10) DEFAULT NULL,
  `fixed_unit_ratio` decimal(4,2) DEFAULT NULL,
  `import_levy_mode` varchar(10) DEFAULT NULL,
  `import_handle_flag` varchar(10) DEFAULT NULL,
  `tax_rate` decimal(10,2) DEFAULT NULL,
  `version` varchar(12) DEFAULT NULL,
  `manual_id` varchar(15) NOT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_manual_import
-- ----------------------------
INSERT INTO `t_manual_import` VALUES ('15', '34DD1', '34RR', 'h55', '太阳能电池', 'h41', 'f33', '片', '片', '600.00', '600.0000', '600.0000', 'RMB', '中国', '7.77', '进料加工', 'h54', '9.90', '600.0', '6.0', 'SC001');
INSERT INTO `t_manual_import` VALUES ('16', '34DD2', '34RR', 'h56', '太阳能电池', 'h42', 'f33', '件', '件', '600.00', '600.5500', '600.6600', 'RMB', '中国', '8.77', '进料加工', 'h55', '10.90', '600.55', '7.0', 'SC002');
INSERT INTO `t_manual_import` VALUES ('17', '34DD3', '34RR', 'h57', '太阳能电池', 'h43', 'f33', '片', '片', '600.00', '600.0000', '600.0000', 'RMB', '中国', '8.77', '进料加工', 'h55', '10.90', '600.0', '7.0', 'SC003');
INSERT INTO `t_manual_import` VALUES ('18', '34DD4', '34RR', 'h58', '太阳能电池', 'h44', 'f33', '件', '件', '600.00', '600.5500', '600.6600', 'RMB', '中国', '8.77', '进料加工', 'h55', '10.90', '600.55', '7.0', 'SC004');

-- ----------------------------
-- Table structure for t_manual_loss
-- ----------------------------
DROP TABLE IF EXISTS `t_manual_loss`;
CREATE TABLE `t_manual_loss` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_no` int(11) DEFAULT NULL COMMENT '成品序号',
  `product_name` varchar(11) DEFAULT NULL COMMENT '成品名称',
  `product_specification` varchar(15) DEFAULT NULL COMMENT '成品规格',
  `product_measurement_unit` varchar(15) DEFAULT NULL COMMENT '成品计量单位',
  `materials_no` int(15) DEFAULT NULL COMMENT '料件序号',
  `materials_name` varchar(15) DEFAULT NULL COMMENT '料件名称',
  `materials_specification` varchar(15) DEFAULT NULL COMMENT '料件规格',
  `materials_measurement_unit` varchar(15) DEFAULT NULL COMMENT '料件计量单位',
  `unit_loss` decimal(15,2) DEFAULT NULL COMMENT '单耗/净耗',
  `loss_rate` decimal(10,2) DEFAULT NULL COMMENT '损耗率',
  `loss_handle_flag` varchar(10) DEFAULT NULL COMMENT '处理标志',
  `unbond_material_rate` decimal(10,0) DEFAULT NULL COMMENT '非保税料件比例',
  `version` varchar(20) DEFAULT NULL COMMENT '版本号',
  `manual_no` varchar(12) DEFAULT NULL COMMENT '手册号',
  `customs_department` varchar(20) DEFAULT NULL COMMENT '海关系类',
  `remark` varchar(12) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `review_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=262 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_manual_loss
-- ----------------------------
INSERT INTO `t_manual_loss` VALUES ('174', '54564', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('175', '62146', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('208', '12146', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', null, '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('209', '22562', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('210', '312423', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '', null, null);
INSERT INTO `t_manual_loss` VALUES ('211', '42234', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('212', '54564', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('213', '62146', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('214', '12146', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('215', '22562', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('216', '312423', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('217', '42234', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('218', '54564', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('219', '62146', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('220', '12146', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('221', '22562', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('222', '312423', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('223', '42234', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('224', '54564', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('225', '62146', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', '0.16', 'TC', '20', '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('256', '12146', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', null, 'TC', null, '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('257', '22562', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', null, 'TC', null, '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('258', '312423', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', null, 'TC', null, '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('259', '42234', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', null, 'TC', null, '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('260', '54564', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', null, 'TC', null, '2.0', 'C66963352', 'w_363326', '一切正常', null, null);
INSERT INTO `t_manual_loss` VALUES ('261', '62146', '太阳能电池板', '单晶60电池板', 'psc', '270', '银浆', '556.0', 'psc', '0.22', null, 'TC', null, '2.0', 'C66963352', 'w_363326', '一切正常', null, null);

-- ----------------------------
-- Table structure for t_manual_product
-- ----------------------------
DROP TABLE IF EXISTS `t_manual_product`;
CREATE TABLE `t_manual_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_record_num` varchar(15) DEFAULT NULL COMMENT '记录号',
  `product_code_and_extra` varchar(15) DEFAULT NULL COMMENT '商品编码+附加码',
  `product_name` varchar(20) DEFAULT NULL COMMENT '商品名称',
  `product_specification` varchar(10) DEFAULT NULL COMMENT '规格型号',
  `product_unit` varchar(10) DEFAULT NULL COMMENT '计量单位',
  `product_fixed_unit` varchar(10) DEFAULT NULL COMMENT '法定计量单位',
  `product_report_num` decimal(10,2) DEFAULT NULL COMMENT '申报数量（额度）',
  `product_report_unit_price` decimal(10,2) DEFAULT NULL COMMENT '申报单价',
  `product_report_total_price` decimal(10,2) DEFAULT NULL COMMENT '申报总价',
  `currency_system` varchar(10) DEFAULT NULL COMMENT '币值',
  `product_pro_marker` varchar(10) DEFAULT NULL COMMENT '产销国',
  `fix_unit_ratio` decimal(3,2) DEFAULT NULL COMMENT '法定单位比例',
  `product_levy_mode` varchar(10) DEFAULT NULL COMMENT '免征方式',
  `product_handle_flag` varchar(10) DEFAULT NULL COMMENT '处理标志',
  `product_report_state` varchar(10) DEFAULT NULL COMMENT '申报状态',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `version` varchar(12) DEFAULT NULL COMMENT '版本号',
  `manual_no` varchar(11) DEFAULT NULL COMMENT '手册号',
  `customs_department` varchar(50) DEFAULT NULL COMMENT '海关系列',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `review_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_manual_product
-- ----------------------------
INSERT INTO `t_manual_product` VALUES ('1', 'JL1234', '0220115211', '太阳能组件', 'w_36336', 'psc', '1000', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('2', 'JL1234', '0220115211', '太阳能组件', 'w_36336', 'psc', '1000', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2', 'C66965566', 'w_665655_4744', '2017-06-13 14:50:09', '2017-06-12 14:50:13');
INSERT INTO `t_manual_product` VALUES ('3', 'JL1234', '0220115211', '太阳能组件', 'w_36336', 'psc', '1000', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2', 'C66965566', 'w_665655_4744', '2017-06-06 14:50:40', '2017-06-21 14:50:43');
INSERT INTO `t_manual_product` VALUES ('9', 'JK9000', '5652245564', '太阳能电池板', 'V_99890', 'KG', 'T', '10000.00', '100.00', '1000000.00', '美元', '德国', '0.23', '进口加工', 'HCC', '通过', '9699', '2', 'C6676', 'V_885', null, null);
INSERT INTO `t_manual_product` VALUES ('10', 'JD006', '3320444155', '太阳能电池板', 'w_665255', 'PCS', 'KG', '100.00', '100.00', '1000.00', '美元', '美国', '0.22', '进口加工', 'THY', '已申报', '大大', '3', 'C89888', 'v_58995', null, null);
INSERT INTO `t_manual_product` VALUES ('11', 'JL1234', '0220115211', '太阳能组件', 'w_36336', 'psc', '1000', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('17', 'JL1233', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('18', 'JL1244', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('19', 'JL1255', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('20', 'JL1266', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('39', 'JL1233', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('40', 'JL1244', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('41', 'JL1255', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('42', 'JL1266', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('44', 'JL1233', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('45', 'JL1244', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('46', 'JL1255', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('47', 'JL1266', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('49', 'JL1233', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('50', 'JL1244', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('51', 'JL1255', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('52', 'JL1233', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('53', 'JL1244', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('54', 'JL1255', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('64', 'JL1233', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('65', 'JL1244', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('66', 'JL1255', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('67', 'JL1266', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('68', 'JL1277', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('69', 'JL1233', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('70', 'JL1244', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('71', 'JL1255', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('72', 'JL1266', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);
INSERT INTO `t_manual_product` VALUES ('73', 'JL1277', '220115211', '太阳能组件', 'w_36336', 'psc', '1000.0', '100.00', '100.00', '10000.00', '英镑', '德国', '0.76', '来料加工', 'TC', '申报中', '批次完整', '2.0', 'C66965566', 'w_665655_4744', null, null);

-- ----------------------------
-- Table structure for t_manual_sum
-- ----------------------------
DROP TABLE IF EXISTS `t_manual_sum`;
CREATE TABLE `t_manual_sum` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增 ID',
  `manual_id` varchar(11) NOT NULL COMMENT '手册号',
  `off_state` int(1) NOT NULL COMMENT '核销状态',
  `module_set` varchar(20) DEFAULT NULL COMMENT '组件系列',
  `pre_product_money` decimal(14,4) DEFAULT NULL COMMENT '成品备案金额',
  `pre_import_money` decimal(14,4) DEFAULT NULL COMMENT '进口料件备案金额',
  `pre_money_dis` float(4,2) DEFAULT NULL COMMENT '备案分配率',
  `act_export_money` decimal(14,4) DEFAULT NULL COMMENT '实际出口金额',
  `act_import_money` decimal(14,4) DEFAULT NULL COMMENT '实际进口金额',
  `act_money_dis` float(4,2) DEFAULT NULL COMMENT '实际分配率',
  `exist_date` date DEFAULT NULL COMMENT '手册办理日期',
  `valid_date` int(11) DEFAULT NULL COMMENT '有效期',
  `extension_date1` date DEFAULT NULL,
  `extension_date2` date DEFAULT NULL,
  `report_verificate_date` date DEFAULT NULL COMMENT '报核日期',
  `case_over_date` date DEFAULT NULL COMMENT '结案日期',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `review_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_manual_sum
-- ----------------------------
INSERT INTO `t_manual_sum` VALUES ('55', 'S2001', '1', 'ZJ93853', '33.0000', '66.5556', '55.00', '565.0000', '55.0000', '55.00', '2017-06-29', '1', '2017-05-31', '2017-05-31', '2017-06-15', '2017-06-13', '', null, null);
INSERT INTO `t_manual_sum` VALUES ('56', 'S2002', '1', 'ZJ93853', '33.0000', '6666.6668', '33.00', '33.0000', '33.0000', '33.00', '2017-06-29', '2', '2017-06-06', '2017-05-31', '2017-06-13', '2017-06-13', '33.0', null, null);
INSERT INTO `t_manual_sum` VALUES ('57', 'S2003', '1', 'ZJ93853', '222.7700', '66.5556', '55.00', '565.0000', '55.0000', '55.00', '2017-06-06', '1', '2017-05-31', '2017-05-31', '2017-06-15', '2017-06-13', 'kk', null, null);
INSERT INTO `t_manual_sum` VALUES ('58', 'S2004', '1', 'ZJ93853', '33.0000', '6666.6668', '33.00', '33.0000', '33.0000', '33.00', '2017-06-29', '2', '2017-06-06', '2017-05-31', '2017-06-13', '2017-06-13', '33.0', null, null);
INSERT INTO `t_manual_sum` VALUES ('70', 'S2005', '0', 'ZJ93853', '33.0000', '333.0000', '3.00', '444.0000', '444.0000', '44.00', '2017-06-29', '3', '2017-06-06', '2017-05-31', '2017-06-29', '2017-06-29', '', null, null);

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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('1', '物流公司管理', 'fa-briefcase', '', '0', '物流公司管理', '2017-06-06 11:08:09', '2017-06-26 13:43:06');
INSERT INTO `t_menu` VALUES ('2', '进货管理', 'fa-pie-chart', '', '0', '', '2017-06-06 11:16:46', '2017-06-26 13:43:30');
INSERT INTO `t_menu` VALUES ('3', '国际运输管理', 'fa-plane', '', '0', null, '2017-06-06 11:17:00', '2017-06-06 11:17:00');
INSERT INTO `t_menu` VALUES ('4', '国内运输管理', 'fa-truck', '', '0', null, '2017-06-06 11:17:24', '2017-06-06 11:17:24');
INSERT INTO `t_menu` VALUES ('5', '送货单管理', 'fa-file-text', '', '0', null, '2017-06-06 11:17:40', '2017-06-06 11:17:40');
INSERT INTO `t_menu` VALUES ('6', '仓储管理', 'fa-home', '', '0', null, '2017-06-06 11:17:55', '2017-06-06 11:17:55');
INSERT INTO `t_menu` VALUES ('7', '手册管理', 'fa-book', '', '0', null, '2017-06-06 11:18:08', '2017-06-06 11:18:08');
INSERT INTO `t_menu` VALUES ('8', '运价管理', 'fa-jpy', '', '0', null, '2017-06-06 11:18:23', '2017-06-06 11:18:23');
INSERT INTO `t_menu` VALUES ('9', '共享数据库管理', 'fa-object-group', '', '0', null, '2017-06-06 11:18:44', '2017-06-06 11:18:44');
INSERT INTO `t_menu` VALUES ('10', '报表中心', 'fa-bar-chart', '', '0', null, '2017-06-06 11:18:58', '2017-06-06 11:18:58');
INSERT INTO `t_menu` VALUES ('11', '系统管理', 'fa-cogs', '', '0', null, '2017-06-06 11:19:16', '2017-06-06 11:19:16');
INSERT INTO `t_menu` VALUES ('12', '物流公司资质管理', null, '/supplier/quality', '1', '物流公司资质管理', '2017-06-06 16:51:00', '2017-06-06 16:51:00');
INSERT INTO `t_menu` VALUES ('13', '物流公司信息管理', null, '/supplier/info', '1', '物流公司信息管理', '2017-06-06 17:20:34', '2017-06-06 17:20:34');
INSERT INTO `t_menu` VALUES ('14', '物流公司考核标准', null, '/supplier/level', '1', '物流公司考核标准', '2017-06-06 17:21:21', '2017-06-06 17:21:21');
INSERT INTO `t_menu` VALUES ('15', '物流公司月度考核', null, '/supplier/month', '1', '物流公司月度考核', '2017-06-06 17:21:59', '2017-06-06 17:21:59');
INSERT INTO `t_menu` VALUES ('16', '物流公司年度考核', null, '/supplier/year', '1', '物流公司年度考核', '2017-06-06 17:22:38', '2017-06-06 17:22:38');
INSERT INTO `t_menu` VALUES ('17', '公司管理', null, '/system/company', '11', '', '2017-06-12 17:36:35', '2017-06-12 17:36:35');
INSERT INTO `t_menu` VALUES ('18', '部门管理', null, '/system/department', '11', '', '2017-06-12 17:36:53', '2017-06-12 17:36:53');
INSERT INTO `t_menu` VALUES ('19', '角色管理', null, '/system/role', '11', '', '2017-06-12 17:37:13', '2017-06-12 17:37:13');
INSERT INTO `t_menu` VALUES ('20', '用户管理', null, '/system/user', '11', '', '2017-06-12 17:37:31', '2017-06-12 17:37:31');
INSERT INTO `t_menu` VALUES ('21', '菜单管理', null, '/system/menu', '11', '', '2017-06-12 17:39:15', '2017-06-12 17:39:15');
INSERT INTO `t_menu` VALUES ('22', '按钮管理', null, '/system/button', '11', '', '2017-06-12 17:39:31', '2017-06-12 17:39:31');
INSERT INTO `t_menu` VALUES ('23', '权限管理', null, '/system/authority', '11', '', '2017-06-12 17:39:48', '2017-06-12 17:39:48');
INSERT INTO `t_menu` VALUES ('24', '登录管理', null, '/system/systemlogin', '11', '', '2017-06-12 17:40:03', '2017-06-12 17:40:03');
INSERT INTO `t_menu` VALUES ('25', '基础数据管理', null, '/system/dictionary', '11', '', '2017-06-12 17:40:22', '2017-06-12 17:40:22');
INSERT INTO `t_menu` VALUES ('26', '手册情况汇总', '', '/manual/summary', '7', '手册情况汇总', '2017-06-27 08:14:42', '2017-06-27 08:14:42');
INSERT INTO `t_menu` VALUES ('27', '进口明细', '', '/manual/importation', '7', '进口明细', '2017-06-27 08:18:26', '2017-06-27 08:59:32');
INSERT INTO `t_menu` VALUES ('28', '成品表体', '', '/manual/endproduct', '7', '成品表体', '2017-06-27 08:18:52', '2017-06-27 08:18:52');
INSERT INTO `t_menu` VALUES ('29', '单损耗表', '', '/manual/singleloss', '7', '单损耗表', '2017-06-27 08:19:23', '2017-06-27 08:19:23');

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '超级管理员', '1', '超级管理员', '2017-06-07 16:25:36', '2017-06-07 16:25:36');
INSERT INTO `t_role` VALUES ('2', '管理员', '1', '管理员', '2017-06-07 16:39:03', '2017-06-07 16:39:03');
INSERT INTO `t_role` VALUES ('3', '仓管', '3', '', '2017-06-07 16:41:15', '2017-06-07 16:42:48');
INSERT INTO `t_role` VALUES ('4', '总经理', '2', '', '2017-06-07 16:42:11', '2017-06-07 16:42:11');
INSERT INTO `t_role` VALUES ('5', '董事长', '5', '', '2017-06-07 16:42:30', '2017-06-07 16:42:30');
INSERT INTO `t_role` VALUES ('6', '关务', '1', '关务', '2017-06-12 11:28:25', '2017-06-12 11:28:25');
INSERT INTO `t_role` VALUES ('7', '总监', '2', '总监', '2017-06-12 11:28:59', '2017-06-22 09:47:38');
INSERT INTO `t_role` VALUES ('8', '仓库管理员', '11', '', '2017-06-27 08:27:55', '2017-06-27 08:27:55');
INSERT INTO `t_role` VALUES ('9', '保洁', '2', '', '2017-06-27 08:31:44', '2017-06-27 08:31:44');

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_button
-- ----------------------------
INSERT INTO `t_role_button` VALUES ('1', '1', '100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125', '2017-06-11 22:58:23', '2017-06-27 08:15:04');
INSERT INTO `t_role_button` VALUES ('2', '4', '100,101,102,103,104,105,106,107,108', '2017-06-11 22:59:18', '2017-06-12 10:39:54');
INSERT INTO `t_role_button` VALUES ('3', '3', '104,108,116,123', '2017-06-11 23:21:01', '2017-06-12 13:19:02');
INSERT INTO `t_role_button` VALUES ('7', '2', '100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125', '2017-06-12 14:42:15', '2017-06-27 08:19:34');
INSERT INTO `t_role_button` VALUES ('8', '8', '100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125', '2017-06-27 08:38:08', '2017-06-27 08:38:08');

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES ('1', '1', '0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,260,1,12,13,14,15,16,2,3,4,5,6,7,8,9,10,11,17,18,19,20,21,22,23,24,25', '2017-06-08 17:09:13', '2017-06-27 08:15:04');
INSERT INTO `t_role_menu` VALUES ('3', '3', '0,1,12,13,15,16', '2017-06-11 23:21:01', '2017-06-12 13:19:02');
INSERT INTO `t_role_menu` VALUES ('4', '4', '0,1,12,13,14,15,16', '2017-06-11 23:29:40', '2017-06-12 10:39:54');
INSERT INTO `t_role_menu` VALUES ('7', '2', '0,1,12,13,14,15,16,2,3,4,5,6,7,26,27,28,29,8,9,10,11,17,18,19,20,21,22,23,24,25', '2017-06-12 14:42:15', '2017-06-27 08:19:34');
INSERT INTO `t_role_menu` VALUES ('8', '8', '0,1,12,13,14,15,16,2,3,4', '2017-06-27 08:38:08', '2017-06-27 08:38:08');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_supplier
-- ----------------------------
INSERT INTO `t_supplier` VALUES ('2', '3', '123ddd', 'B', '运输', '5', '3434.8763', '贺知章', '13069876567', 'www@jj.com', '不错', '2017-05-31 11:58:18', '2017-06-09 14:27:47');
INSERT INTO `t_supplier` VALUES ('3', '2', '8273nhfs', 'A', '运输', '2', '234.9378', '陈莲眉', '13098765432', 'cml@qq.com', '不错', '2017-05-31 13:13:45', '2017-06-05 11:04:07');
INSERT INTO `t_supplier` VALUES ('5', '4', 'tt666777', 'B', '运输', '4', '567.8880', '马志宇', '13098765425', 'mzy@qq.com', '', '2017-06-28 08:14:19', '2017-06-28 08:14:19');

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
INSERT INTO `t_supplier_level` VALUES ('1', 'AA', '96~100', '2017-06-28 08:11:43', '2017-06-28 08:11:43');
INSERT INTO `t_supplier_level` VALUES ('2', 'A', '90~95', '2017-06-28 08:12:08', '2017-06-28 08:12:08');
INSERT INTO `t_supplier_level` VALUES ('3', 'B', '80~89', '2017-06-28 08:12:33', '2017-06-28 08:12:33');
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
  `file` varchar(50) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `review_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_supplier_month_assess
-- ----------------------------
INSERT INTO `t_supplier_month_assess` VALUES ('25', '2017', '2', '55', 'D', '3', 'RFC__35.docx', '', '2017-06-14 16:05:34', '2017-06-16 14:37:32');
INSERT INTO `t_supplier_month_assess` VALUES ('26', '2017', '6', '77', 'C', '3', 'RFC__34.docx', '', '2017-06-14 16:26:41', '2017-06-16 14:23:49');
INSERT INTO `t_supplier_month_assess` VALUES ('27', '2017', '3', '99', 'AA', '3', 'RFC__30.docx', '', '2017-06-14 16:26:57', '2017-06-14 16:26:57');
INSERT INTO `t_supplier_month_assess` VALUES ('28', '2017', '4', '88', 'B', '3', 'RFC__29.docx', '', '2017-06-14 16:28:00', '2017-06-14 16:28:00');
INSERT INTO `t_supplier_month_assess` VALUES ('29', '2017', '5', '77', 'C', '3', null, '', '2017-06-14 16:28:23', '2017-06-14 16:28:23');
INSERT INTO `t_supplier_month_assess` VALUES ('31', '2017', '5', '88', 'B', '4', null, '', '2017-06-14 18:29:02', '2017-06-14 18:29:02');
INSERT INTO `t_supplier_month_assess` VALUES ('32', '2017', '7', '44', 'D', '7', 'RFC__27.docx', '', '2017-06-16 14:25:53', '2017-06-16 14:25:53');
INSERT INTO `t_supplier_month_assess` VALUES ('33', '2017', '6', '44', 'D', '6', null, '', '2017-06-19 08:22:02', '2017-06-19 08:22:02');
INSERT INTO `t_supplier_month_assess` VALUES ('34', '2017', '5', '66', 'D', '6', 'RFC__26.docx', '', '2017-06-19 08:22:22', '2017-06-19 08:22:22');
INSERT INTO `t_supplier_month_assess` VALUES ('35', '2016', '6', '88', 'B', '6', null, '', '2017-06-19 08:47:43', '2017-06-19 08:47:43');
INSERT INTO `t_supplier_month_assess` VALUES ('38', '2017', '1', '99', 'AA', '3', '', '', '2017-06-22 08:45:33', '2017-06-22 08:45:33');
INSERT INTO `t_supplier_month_assess` VALUES ('39', '2017', '7', '87', 'B', '3', '', '', '2017-06-22 08:46:53', '2017-06-22 08:46:53');
INSERT INTO `t_supplier_month_assess` VALUES ('40', '2017', '8', '66', 'D', '3', '', '', '2017-06-22 08:47:09', '2017-06-22 08:47:09');
INSERT INTO `t_supplier_month_assess` VALUES ('42', '2016', '2', '90', 'B', '2', null, '0.12', null, null);

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
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_supplier_qualification
-- ----------------------------
INSERT INTO `t_supplier_qualification` VALUES ('48', '123abc', '2', '申通', '1', 'RFC__21.docx', '', '2017-05-27 15:35:01', '2017-05-27 15:35:01');
INSERT INTO `t_supplier_qualification` VALUES ('49', '123abc', '3', '中通', '2', 'RFC__34.docx', '', '2017-05-27 16:10:37', '2017-06-16 10:44:23');
INSERT INTO `t_supplier_qualification` VALUES ('54', '123abc', '4', 'yt', '2', '', '', '2017-06-28 14:14:48', '2017-06-28 14:14:48');
INSERT INTO `t_supplier_qualification` VALUES ('55', '123abc', '6', 'AA', '1', '', '', '2017-06-28 14:15:29', '2017-06-28 14:15:29');

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
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_supplier_year_assess
-- ----------------------------
INSERT INTO `t_supplier_year_assess` VALUES ('78', '88', '2017', '3', 'B', null, '2017-06-26 08:36:50', '2017-06-26 08:37:51');
INSERT INTO `t_supplier_year_assess` VALUES ('79', '88', '2017', '4', 'B', null, '2017-06-26 08:36:50', '2017-06-26 08:36:50');
INSERT INTO `t_supplier_year_assess` VALUES ('82', '44', '2017', '7', 'D', null, '2017-06-26 13:52:03', '2017-06-26 13:52:03');
INSERT INTO `t_supplier_year_assess` VALUES ('83', '99', '2017', '6', 'AA', null, '2017-06-26 14:16:02', '2017-06-26 14:16:15');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `company_id` int(11) NOT NULL,
  `department_id` int(11) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  `review_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '6697BFAAD9A17D787670B699A12BC9DD3B8A544808CE6C73F1722693', '1', null, '1', '2017-06-12 14:47:39', '2017-06-26 13:39:19');
INSERT INTO `t_user` VALUES ('2', 'user1', '286573CD91BD75E93E78D6F181A05355918DECF2212C4043C37C7C68', '1', null, '2', '2017-06-08 10:50:05', '2017-06-13 17:36:50');
INSERT INTO `t_user` VALUES ('3', 'user', '49786E6ADDCBA27B95E0274742D48482E51BD29CA77927F732B4B980', '2', null, '4', '2017-06-12 14:47:39', '2017-06-13 16:51:48');
INSERT INTO `t_user` VALUES ('4', 'customer', '6B7835CBA266524A0A14B10F1A0A75A99D18EF091E3D3B1DD212281B', '1', null, '2', '2017-06-13 16:48:38', '2017-06-13 16:51:36');

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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_log
-- ----------------------------
INSERT INTO `t_user_log` VALUES ('1', '1', '192.168.1.1', 'PC', '2017-06-12 10:05:36', '2017-06-13 10:05:39');
INSERT INTO `t_user_log` VALUES ('2', '2', '192.168.55.22', 'phone', '2017-06-06 10:06:12', '2017-06-13 10:06:17');
INSERT INTO `t_user_log` VALUES ('3', '1', '127.0.0.1', 'PC', '2017-06-12 14:09:14', '2017-06-12 14:15:49');
INSERT INTO `t_user_log` VALUES ('6', '1', '127.0.0.1', 'PC', '2017-06-12 14:20:44', '2017-06-12 14:20:50');
INSERT INTO `t_user_log` VALUES ('7', '2', '127.0.0.1', 'PC', '2017-06-12 14:21:15', '2017-06-12 14:22:55');
INSERT INTO `t_user_log` VALUES ('8', '1', '218.91.207.66', 'phone', '2017-06-12 14:23:49', '2017-06-12 14:23:54');
INSERT INTO `t_user_log` VALUES ('9', '1', '127.0.0.1', 'PC', '2017-06-12 14:30:38', '2017-06-12 14:31:54');
INSERT INTO `t_user_log` VALUES ('10', '1', '0:0:0:0:0:0:0:1', 'PC', '2017-06-13 15:51:09', '2017-06-13 17:24:16');
INSERT INTO `t_user_log` VALUES ('11', '3', '0:0:0:0:0:0:0:1', 'PC', '2017-06-13 17:24:30', '2017-06-13 17:25:22');
INSERT INTO `t_user_log` VALUES ('12', '3', '0:0:0:0:0:0:0:1', 'PC', '2017-06-13 17:25:37', '2017-06-13 17:32:10');
INSERT INTO `t_user_log` VALUES ('13', '2', '0:0:0:0:0:0:0:1', 'PC', '2017-06-13 17:37:19', '2017-06-13 17:37:25');
INSERT INTO `t_user_log` VALUES ('14', '3', '0:0:0:0:0:0:0:1', 'PC', '2017-06-14 17:54:29', '2017-06-14 18:22:54');
INSERT INTO `t_user_log` VALUES ('15', '3', '0:0:0:0:0:0:0:1', 'PC', '2017-06-14 18:23:10', '2017-06-14 18:23:16');
INSERT INTO `t_user_log` VALUES ('16', '1', '0:0:0:0:0:0:0:1', 'PC', '2017-06-19 08:00:32', '2017-06-19 08:13:32');
INSERT INTO `t_user_log` VALUES ('17', '6', '0:0:0:0:0:0:0:1', 'PC', '2017-06-19 10:11:54', '2017-06-19 10:12:04');
INSERT INTO `t_user_log` VALUES ('18', '6', '0:0:0:0:0:0:0:1', 'PC', '2017-06-19 10:15:20', '2017-06-19 10:15:26');
INSERT INTO `t_user_log` VALUES ('19', '1', '0:0:0:0:0:0:0:1', 'PC', '2017-06-19 10:20:29', '2017-06-19 10:21:22');
INSERT INTO `t_user_log` VALUES ('20', '3', '0:0:0:0:0:0:0:1', 'PC', '2017-06-21 09:59:38', '2017-06-21 09:59:47');
INSERT INTO `t_user_log` VALUES ('21', '1', '0:0:0:0:0:0:0:1', 'PC', '2017-06-26 08:14:45', '2017-06-26 08:22:56');
INSERT INTO `t_user_log` VALUES ('22', '1', '0:0:0:0:0:0:0:1', 'PC', '2017-06-26 08:48:00', '2017-06-26 08:48:03');
INSERT INTO `t_user_log` VALUES ('23', '3', '0:0:0:0:0:0:0:1', 'PC', '2017-06-26 08:48:16', '2017-06-26 08:48:19');
INSERT INTO `t_user_log` VALUES ('24', '1', '0:0:0:0:0:0:0:1', 'PC', '2017-06-26 17:19:15', '2017-06-26 17:19:19');
INSERT INTO `t_user_log` VALUES ('25', '1', '0:0:0:0:0:0:0:1', 'PC', '2017-06-26 17:19:26', '2017-06-26 17:30:26');
INSERT INTO `t_user_log` VALUES ('26', '1', '0:0:0:0:0:0:0:1', 'PC', '2017-06-27 08:09:33', '2017-06-27 08:13:21');
INSERT INTO `t_user_log` VALUES ('27', '1', '0:0:0:0:0:0:0:1', 'PC', '2017-06-27 08:13:56', '2017-06-27 08:40:41');
INSERT INTO `t_user_log` VALUES ('28', '1', '0:0:0:0:0:0:0:1', 'PC', '2017-06-27 08:45:57', '2017-06-27 08:46:27');
INSERT INTO `t_user_log` VALUES ('29', '1', '127.0.0.1', 'PC', '2017-06-27 15:20:12', '2017-06-27 15:25:03');
INSERT INTO `t_user_log` VALUES ('30', '1', '127.0.0.1', 'PC', '2017-06-27 15:31:17', '2017-06-27 15:37:11');
INSERT INTO `t_user_log` VALUES ('31', '1', '0:0:0:0:0:0:0:1', 'PC', '2017-06-28 08:02:19', '2017-06-28 08:07:57');
