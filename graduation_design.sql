/*
 Navicat Premium Data Transfer

 Source Server         : 本机Mysql
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : graduation_design

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 10/03/2022 21:43:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `book_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书籍ID',
  `book_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书籍名称',
  `book_category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '书籍分类',
  `book_Introduction` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书籍简介(0-124位)',
  `book_number` int(0) NOT NULL COMMENT '书籍数量',
  `book_borrowed` int(0) NOT NULL COMMENT '已被借阅数量',
  `book_appointment` int(0) NOT NULL COMMENT '预约借阅数量',
  `book_available` int(0) NOT NULL COMMENT '可预约借阅数量',
  PRIMARY KEY (`book_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('TS1', '测试006', '无类别', '测试006', 10, 0, 0, 10);
INSERT INTO `book` VALUES ('TS2', '测试007', '交通运输', '测试007', 10, 0, 0, 10);
INSERT INTO `book` VALUES ('测试001', '1', '教育-测试', '1', 5, 0, 1, 4);
INSERT INTO `book` VALUES ('测试002', '2', '无类别', '2', 5, 1, 0, 4);
INSERT INTO `book` VALUES ('测试003', '3', ' 数理科学', '11211', 5, 0, 0, 5);
INSERT INTO `book` VALUES ('测试004', '4', '无类别', '4', 5, 0, 0, 5);
INSERT INTO `book` VALUES ('测试005', '5', '无类别', '; and 1=1 and 1=2', 5, 0, 1, 4);

-- ----------------------------
-- Table structure for book_category
-- ----------------------------
DROP TABLE IF EXISTS `book_category`;
CREATE TABLE `book_category`  (
  `book_category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`book_category`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_category
-- ----------------------------
INSERT INTO `book_category` VALUES ('交通运输');
INSERT INTO `book_category` VALUES ('体育');
INSERT INTO `book_category` VALUES ('军事');
INSERT INTO `book_category` VALUES ('农业科学');
INSERT INTO `book_category` VALUES ('化学');
INSERT INTO `book_category` VALUES ('医药');
INSERT INTO `book_category` VALUES ('卫生');
INSERT INTO `book_category` VALUES ('历史');
INSERT INTO `book_category` VALUES ('同人小说');
INSERT INTO `book_category` VALUES ('哲学');
INSERT INTO `book_category` VALUES ('地球科学');
INSERT INTO `book_category` VALUES ('地理');
INSERT INTO `book_category` VALUES ('天文学');
INSERT INTO `book_category` VALUES ('安全科学');
INSERT INTO `book_category` VALUES ('宗教');
INSERT INTO `book_category` VALUES ('工业技术');
INSERT INTO `book_category` VALUES ('思想类');
INSERT INTO `book_category` VALUES ('政治');
INSERT INTO `book_category` VALUES ('教育-测试');
INSERT INTO `book_category` VALUES ('数理科学');
INSERT INTO `book_category` VALUES ('文化');
INSERT INTO `book_category` VALUES ('文字');
INSERT INTO `book_category` VALUES ('文学');
INSERT INTO `book_category` VALUES ('无类别');
INSERT INTO `book_category` VALUES ('法律');
INSERT INTO `book_category` VALUES ('漫画');
INSERT INTO `book_category` VALUES ('玄幻小说');
INSERT INTO `book_category` VALUES ('环境科学');
INSERT INTO `book_category` VALUES ('生物科学');
INSERT INTO `book_category` VALUES ('社会科学总论');
INSERT INTO `book_category` VALUES ('科学');
INSERT INTO `book_category` VALUES ('科普');
INSERT INTO `book_category` VALUES ('经济');
INSERT INTO `book_category` VALUES ('综合性图书');
INSERT INTO `book_category` VALUES ('自然科学');
INSERT INTO `book_category` VALUES ('航天');
INSERT INTO `book_category` VALUES ('航空');
INSERT INTO `book_category` VALUES ('艺术');
INSERT INTO `book_category` VALUES ('语言');

-- ----------------------------
-- Table structure for ebook
-- ----------------------------
DROP TABLE IF EXISTS `ebook`;
CREATE TABLE `ebook`  (
  `ebook_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电子书ID',
  `ebook_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电子书名称',
  `ebook_category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子书分类',
  `collection_times` int(0) NULL DEFAULT NULL COMMENT '收藏次数',
  `download_times` int(0) NULL DEFAULT NULL COMMENT '下载次数',
  `ebook_brief_information` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子书简介',
  `download_user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传者ID',
  `download_user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传者用户名（仅记录上传时用户名称）',
  `ebook_size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子书大小',
  `ebook_state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子书审核状态（审核中、已驳回、上传成功、已取消）',
  `information_state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子书状态（未删除、已删除）',
  PRIMARY KEY (`ebook_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ebook
-- ----------------------------
INSERT INTO `ebook` VALUES ('DTS1', 'Vue源问题.txt', '无类别', 1, 3, '22232', '002', '测试用户002', '477b', '上传成功', '未删除');
INSERT INTO `ebook` VALUES ('DTS11', '内容测试.txt', '交通运输', 2, 0, '22', '001', '管理员', '5.0kb', '上传成功', '未删除');
INSERT INTO `ebook` VALUES ('DTS2', '毕设信息.txt', '交通运输', 1, 0, 'wwdawda', '001', '管理员', '388b', '上传成功', '未删除');
INSERT INTO `ebook` VALUES ('DTS3', '装备搭配.txt', '无类别', 0, 0, '1', '001', '管理员', '1.0kb', '审核中', '未删除');

-- ----------------------------
-- Table structure for ebook_shelf
-- ----------------------------
DROP TABLE IF EXISTS `ebook_shelf`;
CREATE TABLE `ebook_shelf`  (
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `ebook_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子书名称',
  `ebook_category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子书分类',
  `ebook_size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子书大小',
  `ebook_read` int(0) NULL DEFAULT 0 COMMENT '阅读进度',
  `ebook_page` int(0) NULL DEFAULT NULL COMMENT '总页数'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ebook_shelf
-- ----------------------------
INSERT INTO `ebook_shelf` VALUES ('001', '毕设信息.txt', '交通运输', '388b', 1, 1);
INSERT INTO `ebook_shelf` VALUES ('001', 'Vue源问题.txt', '无类别', '477b', 1, 1);
INSERT INTO `ebook_shelf` VALUES ('001', '内容测试.txt', '交通运输', '5.0kb', 5, 5);

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `permissions_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限ID',
  `permissions_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称',
  `permissions_coding` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限编码'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------
INSERT INTO `permissions` VALUES ('1', '管理员', 'admin');
INSERT INTO `permissions` VALUES ('2', '用户', 'user');
INSERT INTO `permissions` VALUES ('0', '游客', 'tourists');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `user_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `permissions_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限ID',
  `portrait` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像地址',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('001', '管理员', '123456', '管理员', '1', 'https://www.qqtouxiang.com/d/file/nvsheng/2016-11-24/7093e3bec58c0a622ebc8fb4288cd889.jpg');
INSERT INTO `user` VALUES ('002', '测试用户002', '123456', '用户', '2', 'https://www.qqtouxiang.com/d/file/nvsheng/2016-11-24/a337577e72aa1a676a4496cb120adcaf.jpg');
INSERT INTO `user` VALUES ('003', '003', '123456', '用户', '2', 'https://www.qqtouxiang.com/d/file/nvsheng/2016-11-24/a337577e72aa1a676a4496cb120adcaf.jpg');
INSERT INTO `user` VALUES ('004', '测试用户004', '123456', '用户', '2', 'https://www.qqtouxiang.com/d/file/nvsheng/2016-11-24/a337577e72aa1a676a4496cb120adcaf.jpg');
INSERT INTO `user` VALUES ('02010', '02010', '123456', '用户', '2', 'https://www.qqtouxiang.com/d/file/nvsheng/2016-11-24/a337577e72aa1a676a4496cb120adcaf.jpg');
INSERT INTO `user` VALUES ('1', '游客', '123456', '游客', '0', 'https://www.qqtouxiang.com/d/file/nvsheng/2016-11-24/bc132876f50568ab697636676e9129cc.jpg');

-- ----------------------------
-- Table structure for user_book
-- ----------------------------
DROP TABLE IF EXISTS `user_book`;
CREATE TABLE `user_book`  (
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `book_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书ID',
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态',
  `appointment_time` date NOT NULL COMMENT '预约时间',
  `expiration_time` date NOT NULL COMMENT '预约过期时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_book
-- ----------------------------
INSERT INTO `user_book` VALUES ('002', '测试002', '已借阅', '2022-01-05', '2022-01-19');
INSERT INTO `user_book` VALUES ('002', '测试002', '已归还', '2022-01-03', '2022-01-17');
INSERT INTO `user_book` VALUES ('002', '测试004', '已归还', '2021-12-03', '2021-12-19');
INSERT INTO `user_book` VALUES ('002', '测试001', '已过期', '2022-01-05', '2022-01-19');
INSERT INTO `user_book` VALUES ('002', '测试005', '已过期', '2022-01-05', '2022-01-19');

SET FOREIGN_KEY_CHECKS = 1;
