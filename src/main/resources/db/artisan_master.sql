
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for elegant_essay
-- ----------------------------
DROP TABLE
IF EXISTS `elegant_essay_0`;

CREATE TABLE `elegant_essay_0` (
	`essay_id` BIGINT (20) NOT NULL AUTO_INCREMENT COMMENT '文章ID',
	`title` VARCHAR (255) NOT NULL COMMENT '文章标题',
	`publish_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章发布时间',
	`summary` TINYTEXT COMMENT '摘要',
	`allow_comment` INT (1) DEFAULT NULL COMMENT '是否允许评论0:允许1:不允许',
	`sort` INT (8) DEFAULT '0' COMMENT '展示顺序（权重）',
	`content` LONGTEXT NOT NULL COMMENT '文章内容',
	`img_url` VARCHAR (255) DEFAULT NULL COMMENT '主展示图',
	`status` VARCHAR (16) NOT NULL DEFAULT 'draf' COMMENT '状态published:发布draf:草稿',
	`deleted` INT (1) DEFAULT '0' COMMENT '是否删除0:未删除1:删除',
	`user_id` BIGINT (20) DEFAULT NULL COMMENT '作者ID',
	`create_time` datetime DEFAULT NULL COMMENT '文章创建时间',
	`update_time` datetime DEFAULT NULL COMMENT '文章修改时间',
	PRIMARY KEY (`essay_id`)
) ENGINE = INNODB AUTO_INCREMENT = 6 DEFAULT CHARSET = utf8mb4 COMMENT = '文章表';

DROP TABLE
IF EXISTS `elegant_essay_1`;

CREATE TABLE `elegant_essay_1` (
	`essay_id` BIGINT (20) NOT NULL AUTO_INCREMENT COMMENT '文章ID',
	`title` VARCHAR (255) NOT NULL COMMENT '文章标题',
	`publish_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章发布时间',
	`summary` TINYTEXT COMMENT '摘要',
	`allow_comment` INT (1) DEFAULT NULL COMMENT '是否允许评论0:允许1:不允许',
	`sort` INT (8) DEFAULT '0' COMMENT '展示顺序（权重）',
	`content` LONGTEXT NOT NULL COMMENT '文章内容',
	`img_url` VARCHAR (255) DEFAULT NULL COMMENT '主展示图',
	`status` VARCHAR (16) NOT NULL DEFAULT 'draf' COMMENT '状态published:发布draf:草稿',
	`deleted` INT (1) DEFAULT '0' COMMENT '是否删除0:未删除1:删除',
	`user_id` BIGINT (20) DEFAULT NULL COMMENT '作者ID',
	`create_time` datetime DEFAULT NULL COMMENT '文章创建时间',
	`update_time` datetime DEFAULT NULL COMMENT '文章修改时间',
	PRIMARY KEY (`essay_id`)
) ENGINE = INNODB AUTO_INCREMENT = 6 DEFAULT CHARSET = utf8mb4 COMMENT = '文章表';

-- ----------------------------
-- Table structure for elegant_user
-- ----------------------------
DROP TABLE
IF EXISTS `elegant_user`;

CREATE TABLE `elegant_user` (
	`user_id` BIGINT (20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
	`user_name` VARCHAR (64) NOT NULL COMMENT '账户名',
	`password` VARCHAR (64) NOT NULL COMMENT '账户密码',
	`real_name` VARCHAR (12) DEFAULT NULL COMMENT '真实姓名',
	`phone` VARCHAR (16) DEFAULT NULL COMMENT '手机号',
	`email` VARCHAR (64) DEFAULT NULL COMMENT '邮箱地址',
	`user_type` INT (1) DEFAULT NULL COMMENT '用户类型0:超级管理员1:其他',
	`deleted` INT (1) DEFAULT NULL COMMENT '是否删除0:未删除1:删除',
	`create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`update_time` datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`user_id`),
	UNIQUE KEY `user_name` (`user_name`) USING BTREE,
	KEY `user_name_sn` (`user_name`) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 5 DEFAULT CHARSET = utf8mb4 COMMENT = '用户表';

DROP TABLE
IF EXISTS artisan_goods;

CREATE TABLE artisan_goods (
	goods_id BIGINT (20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '商品ID',
	goods_name VARCHAR (255) NOT NULL COMMENT '商品名称',
	goods_icon VARCHAR (255) NOT NULL COMMENT '商品展示图标',
	goods_brief VARCHAR (255) NOT NULL COMMENT '商品简介',
	goods_desc LONGTEXT NOT NULL COMMENT '商品描述',
	price DECIMAL (8, 2) DEFAULT NULL COMMENT '商品价格',
	promotion_price DECIMAL (8, 2) DEFAULT NULL COMMENT '商品促销价',
	category INTEGER (6) DEFAULT NULL COMMENT '商品所属类别',
	sales_volume INTEGER (8) DEFAULT 0 COMMENT '商品销量',
	page_view INTEGER (8) DEFAULT 0 COMMENT '商品浏览量',
	stocks INTEGER (6) DEFAULT 0 COMMENT '商品库存',
	sort INTEGER (9) DEFAULT 0 COMMENT '商品展示权重',
	deleted INT (2) NOT NULL DEFAULT 0 COMMENT '商品状态0:正常1:删除',
	start_time datetime DEFAULT NULL COMMENT '商品上架时间,为空时无限制',
	end_time datetime DEFAULT NULL COMMENT '商品下架时间,为空时无限制',
	create_time datetime NOT NULL COMMENT '商品创建时间',
	update_time datetime DEFAULT NULL COMMENT '商品更新时间'
) ENGINE = INNODB AUTO_INCREMENT = 1 COMMENT '商品表';

DROP TABLE
IF EXISTS goods_order_0;

CREATE TABLE goods_order_0 (
	order_id BIGINT (20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID',
	price DECIMAL (6, 2) DEFAULT NULL COMMENT '订单总价',
	pay_amount DECIMAL (8, 2) DEFAULT NULL COMMENT '支付金额',
	pay_account VARCHAR (150) DEFAULT NULL COMMENT '支付账户',
	goods_weight DECIMAL (8, 2) DEFAULT NULL COMMENT '商品重量单位KG',
	pay_status INT (2) DEFAULT 0 COMMENT '订单支付状态0:未支付1:支付成功',
	order_status INT (2) DEFAULT NULL COMMENT '订单状态0:未发货1:已发货2:已签收3:已完成',
	deleted INT (2) DEFAULT 0 COMMENT '订单状态0:正常1:删除',
	user_id BIGINT (20) NOT NULL COMMENT '用户ID',
	user_name VARCHAR (64) NOT NULL COMMENT '用户名称',
	pay_way INT (5) DEFAULT NULL COMMENT '支付方式0:支付宝1:微信',
	trade_num VARCHAR (64) DEFAULT NULL COMMENT '交易序号',
	cellphone VARCHAR (32) NOT NULL COMMENT '手机号',
	shipping_address VARCHAR (255) NOT NULL COMMENT '收货地址',
	pay_time datetime DEFAULT NULL COMMENT '付款时间',
	consignment_time datetime DEFAULT NULL COMMENT '发货时间',
	complete_time datetime DEFAULT NULL COMMENT '成交时间',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE = INNODB AUTO_INCREMENT = 1 COMMENT '订单主表';

DROP TABLE
IF EXISTS goods_order_1;

CREATE TABLE goods_order_1 (
	order_id BIGINT (20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID',
	price DECIMAL (6, 2) DEFAULT NULL COMMENT '订单总价',
	pay_amount DECIMAL (8, 2) DEFAULT NULL COMMENT '支付金额',
	pay_account VARCHAR (150) DEFAULT NULL COMMENT '支付账户',
	goods_weight DECIMAL (8, 2) DEFAULT NULL COMMENT '商品重量单位KG',
	pay_status INT (2) DEFAULT 0 COMMENT '订单支付状态0:未支付1:支付成功',
	order_status INT (2) DEFAULT NULL COMMENT '订单状态0:未发货1:已发货2:已签收3:已完成',
	deleted INT (2) DEFAULT 0 COMMENT '订单状态0:正常1:删除',
	user_id BIGINT (20) NOT NULL COMMENT '用户ID',
	user_name VARCHAR (64) NOT NULL COMMENT '用户名称',
	pay_way INT (5) DEFAULT NULL COMMENT '支付方式0:支付宝1:微信',
	trade_num VARCHAR (64) DEFAULT NULL COMMENT '交易序号',
	cellphone VARCHAR (32) NOT NULL COMMENT '手机号',
	shipping_address VARCHAR (255) NOT NULL COMMENT '收货地址',
	pay_time datetime DEFAULT NULL COMMENT '付款时间',
	consignment_time datetime DEFAULT NULL COMMENT '发货时间',
	complete_time datetime DEFAULT NULL COMMENT '成交时间',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE = INNODB AUTO_INCREMENT = 1 COMMENT '订单主表';

DROP TABLE
IF EXISTS goods_order_2;

CREATE TABLE goods_order_2 (
	order_id BIGINT (20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID',
	price DECIMAL (6, 2) DEFAULT NULL COMMENT '订单总价',
	pay_amount DECIMAL (8, 2) DEFAULT NULL COMMENT '支付金额',
	pay_account VARCHAR (150) DEFAULT NULL COMMENT '支付账户',
	goods_weight DECIMAL (8, 2) DEFAULT NULL COMMENT '商品重量单位KG',
	pay_status INT (2) DEFAULT 0 COMMENT '订单支付状态0:未支付1:支付成功',
	order_status INT (2) DEFAULT NULL COMMENT '订单状态0:未发货1:已发货2:已签收3:已完成',
	deleted INT (2) DEFAULT 0 COMMENT '订单状态0:正常1:删除',
	user_id BIGINT (20) NOT NULL COMMENT '用户ID',
	user_name VARCHAR (64) NOT NULL COMMENT '用户名称',
	pay_way INT (5) DEFAULT NULL COMMENT '支付方式0:支付宝1:微信',
	trade_num VARCHAR (64) DEFAULT NULL COMMENT '交易序号',
	cellphone VARCHAR (32) NOT NULL COMMENT '手机号',
	shipping_address VARCHAR (255) NOT NULL COMMENT '收货地址',
	pay_time datetime DEFAULT NULL COMMENT '付款时间',
	consignment_time datetime DEFAULT NULL COMMENT '发货时间',
	complete_time datetime DEFAULT NULL COMMENT '成交时间',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE = INNODB AUTO_INCREMENT = 1 COMMENT '订单主表';

DROP TABLE
IF EXISTS goods_order_3;

CREATE TABLE goods_order_3 (
	order_id BIGINT (20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID',
	price DECIMAL (6, 2) DEFAULT NULL COMMENT '订单总价',
	pay_amount DECIMAL (8, 2) DEFAULT NULL COMMENT '支付金额',
	pay_account VARCHAR (150) DEFAULT NULL COMMENT '支付账户',
	goods_weight DECIMAL (8, 2) DEFAULT NULL COMMENT '商品重量单位KG',
	pay_status INT (2) DEFAULT 0 COMMENT '订单支付状态0:未支付1:支付成功',
	order_status INT (2) DEFAULT NULL COMMENT '订单状态0:未发货1:已发货2:已签收3:已完成',
	deleted INT (2) DEFAULT 0 COMMENT '订单状态0:正常1:删除',
	user_id BIGINT (20) NOT NULL COMMENT '用户ID',
	user_name VARCHAR (64) NOT NULL COMMENT '用户名称',
	pay_way INT (5) DEFAULT NULL COMMENT '支付方式0:支付宝1:微信',
	trade_num VARCHAR (64) DEFAULT NULL COMMENT '交易序号',
	cellphone VARCHAR (32) NOT NULL COMMENT '手机号',
	shipping_address VARCHAR (255) NOT NULL COMMENT '收货地址',
	pay_time datetime DEFAULT NULL COMMENT '付款时间',
	consignment_time datetime DEFAULT NULL COMMENT '发货时间',
	complete_time datetime DEFAULT NULL COMMENT '成交时间',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE = INNODB AUTO_INCREMENT = 1 COMMENT '订单主表';

DROP TABLE
IF EXISTS goods_order_item_0;

CREATE TABLE goods_order_item_0 (
	order_item_id BIGINT (20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '订单项ID',
	goods_id BIGINT (20) NOT NULL COMMENT '商品ID',
	goods_name VARCHAR (255) NOT NULL COMMENT '商品名称',
	goods_icon VARCHAR (255) NOT NULL COMMENT '商品图标',
	price DECIMAL (8, 2) NOT NULL COMMENT '商品成交价',
	trade_status INT (2) DEFAULT NULL COMMENT '订单状态0:正常1:退款中2:已退款',
	quantity INTEGER (5) NOT NULL DEFAULT 1 COMMENT '购买数量',
	order_id BIGINT (20) NOT NULL COMMENT '所属的订单ID'
) ENGINE = INNODB AUTO_INCREMENT = 1 COMMENT '订单从表';

DROP TABLE
IF EXISTS goods_order_item_1;

CREATE TABLE goods_order_item_1 (
	order_item_id BIGINT (20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '订单项ID',
	goods_id BIGINT (20) NOT NULL COMMENT '商品ID',
	goods_name VARCHAR (255) NOT NULL COMMENT '商品名称',
	goods_icon VARCHAR (255) NOT NULL COMMENT '商品图标',
	price DECIMAL (8, 2) NOT NULL COMMENT '商品成交价',
	trade_status INT (2) DEFAULT NULL COMMENT '订单状态0:正常1:退款中2:已退款',
	quantity INTEGER (5) NOT NULL DEFAULT 1 COMMENT '购买数量',
	order_id BIGINT (20) NOT NULL COMMENT '所属的订单ID'
) ENGINE = INNODB AUTO_INCREMENT = 1 COMMENT '订单从表';

DROP TABLE
IF EXISTS goods_order_item_2;

CREATE TABLE goods_order_item_2 (
	order_item_id BIGINT (20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '订单项ID',
	goods_id BIGINT (20) NOT NULL COMMENT '商品ID',
	goods_name VARCHAR (255) NOT NULL COMMENT '商品名称',
	goods_icon VARCHAR (255) NOT NULL COMMENT '商品图标',
	price DECIMAL (8, 2) NOT NULL COMMENT '商品成交价',
	trade_status INT (2) DEFAULT NULL COMMENT '订单状态0:正常1:退款中2:已退款',
	quantity INTEGER (5) NOT NULL DEFAULT 1 COMMENT '购买数量',
	order_id BIGINT (20) NOT NULL COMMENT '所属的订单ID'
) ENGINE = INNODB AUTO_INCREMENT = 1 COMMENT '订单从表';

DROP TABLE
IF EXISTS goods_order_item_3;

CREATE TABLE goods_order_item_3 (
	order_item_id BIGINT (20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '订单项ID',
	goods_id BIGINT (20) NOT NULL COMMENT '商品ID',
	goods_name VARCHAR (255) NOT NULL COMMENT '商品名称',
	goods_icon VARCHAR (255) NOT NULL COMMENT '商品图标',
	price DECIMAL (8, 2) NOT NULL COMMENT '商品成交价',
	trade_status INT (2) DEFAULT NULL COMMENT '订单状态0:正常1:退款中2:已退款',
	quantity INTEGER (5) NOT NULL DEFAULT 1 COMMENT '购买数量',
	order_id BIGINT (20) NOT NULL COMMENT '所属的订单ID'
) ENGINE = INNODB AUTO_INCREMENT = 1 COMMENT '订单从表';

DROP TABLE
IF EXISTS shipping_address;

CREATE TABLE shipping_address (
	address_id BIGINT (20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '地址ID',
	user_id BIGINT (20) NOT NULL COMMENT '用户ID',
	area_id BIGINT (20) NOT NULL COMMENT '区域ID',
	street_id BIGINT (20) NOT NULL COMMENT '街道ID',
	zip_code VARCHAR (18) DEFAULT NULL COMMENT '邮编',
	address_desc VARCHAR (255) NOT NULL COMMENT '地址详情',
	cellphone VARCHAR (64) NOT NULL COMMENT '收货人手机号',
	consignee_name VARCHAR (32) NOT NULL COMMENT '收货人姓名',
	is_default INT (2) DEFAULT 0 COMMENT '是否是默认地址1:默认0:非默认',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE = INNODB AUTO_INCREMENT = 1 COMMENT '用户地址表';

DROP TABLE
IF EXISTS artisan_area;

CREATE TABLE artisan_area (
	area_id BIGINT (20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '区域ID',
	area_name VARCHAR (64) NOT NULL COMMENT '区域名称',
	parent_id BIGINT (20) DEFAULT NULL COMMENT '父级区域ID',
	area_level INT (2) DEFAULT 0 COMMENT '区域级别1:一级2:二级3:三级...',
	area_type INT (2) NOT NULL COMMENT '区域类型',
	deleted INT (2) DEFAULT 0 COMMENT '状态0:正常1:删除',
	area_desc VARCHAR (255) DEFAULT NULL COMMENT '描述',
	create_time datetime DEFAULT NULL COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE = INNODB AUTO_INCREMENT = 1 COMMENT '区域表';

DROP TABLE
IF EXISTS ds_app_biz_uid;

CREATE TABLE ds_app_biz_uid (
	id INTEGER (10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	app_name VARCHAR (128) NOT NULL COMMENT '应用名称',
	biz_name VARCHAR (128) NOT NULL COMMENT '业务名称',
	biz_uid BIGINT (20) DEFAULT 1 COMMENT '业务主键当前值',
	create_time datetime DEFAULT NULL COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE = INNODB AUTO_INCREMENT = 1 COMMENT '分布式系统应用业务主键分配记录表';

DROP TABLE
IF EXISTS trans_msg_state_record;

CREATE TABLE trans_msg_state_record (
	id BIGINT (20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	topic VARCHAR (64) NOT NULL COMMENT '消息主题',
	flag INT (6) DEFAULT 0 COMMENT '标示',
	msg_body LONGTEXT NOT NULL COMMENT '消息体',
	trans_id VARCHAR (64) NOT NULL COMMENT '事务ID',
	trans_state INT (6) DEFAULT 0 COMMENT '事务状态',
	msg_keys VARCHAR (64) DEFAULT NULL COMMENT '消息的keys',
	msg_is_tran TINYINT (1) DEFAULT FALSE COMMENT '是否是事务消息',
	msg_uniq_key VARCHAR (64) DEFAULT NULL COMMENT '消息的我唯一索引',
	msg_is_wait TINYINT (1) DEFAULT FALSE COMMENT '是否',
	msg_group VARCHAR (64) NOT NULL COMMENT '所属的生产组',
	msg_tags VARCHAR (128) DEFAULT NULL COMMENT '消息的标签',
	biz_type INT (6) NOT NULL COMMENT '业务类型1:测试',
	create_time datetime DEFAULT NULL COMMENT '创建时间',
	update_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '分布式事务记录表';

DROP TABLE
IF EXISTS trans_msg_consume_record;

CREATE TABLE trans_msg_consume_record (
	id BIGINT (20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	topic VARCHAR (64) NOT NULL COMMENT '消息主题',
	flag INT (6) DEFAULT 0 COMMENT '标示',
	msg_body LONGTEXT NOT NULL COMMENT '消息体',
	msg_id VARCHAR (64) NOT NULL COMMENT '消息ID',
	born_time TIMESTAMP NOT NULL COMMENT '消息born时间',
	store_time TIMESTAMP NOT NULL COMMENT '消息store时间',
	reconsume_times INT (2) DEFAULT 0 COMMENT '消费重试次数',
	queue_id INT (11) DEFAULT 0 COMMENT '队列ID',
	sys_flag INT (6) DEFAULT 0 COMMENT '系统标示',
	trans_id VARCHAR (64) NOT NULL COMMENT '事务ID',
	trans_state INT (6) DEFAULT 0 COMMENT '事务状态',
	msg_keys VARCHAR (64) DEFAULT NULL COMMENT '消息的keys',
	msg_is_tran TINYINT (1) DEFAULT FALSE COMMENT '是否是事务消息',
	msg_tran_check_times INT (6) DEFAULT 0 COMMENT '事务回查次数',
	msg_uniq_key VARCHAR (64) DEFAULT NULL COMMENT '消息的我唯一索引',
	msg_is_wait TINYINT (1) DEFAULT FALSE COMMENT '是否',
	msg_group VARCHAR (64) NOT NULL COMMENT '所属的生产组',
	msg_tags VARCHAR (128) DEFAULT NULL COMMENT '消息的标签',
	msg_real_queue_id INT (11) DEFAULT 0 COMMENT '消息的真实队列ID',
	biz_type INT (6) NOT NULL COMMENT '业务类型1:测试',
	create_time datetime DEFAULT NULL COMMENT '创建时间',
	update_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '分布式事务消息消费记录表';

-- 初始化数据
INSERT INTO `artisan_goods`
VALUES
	(
		'1',
		'小米移动电源',
		'http://baidu.com',
		'小米移动电源',
		'小米移动电源',
		'59.00',
		'49.00',
		'1',
		'100',
		'10',
		'10000',
		'0',
		'0',
		NULL,
		NULL,
		'2018-11-17 11:29:07',
		NULL
	);

INSERT INTO `artisan_goods`
VALUES
	(
		'2',
		'小米笔记本PRO',
		'http://baidu.com',
		'小米笔记本PRO',
		'小米笔记本PRO',
		'5999.00',
		'5799.00',
		'1',
		'100',
		'10',
		'10000',
		'0',
		'0',
		NULL,
		NULL,
		'2018-11-17 11:29:07',
		NULL
	);

INSERT INTO `artisan_goods`
VALUES
	(
		'3',
		'macbook pro 256SSD',
		'http://baidu.com',
		'macbook',
		'macbook',
		'12000.00',
		'10888.00',
		'1',
		'100',
		'10',
		'10000',
		'0',
		'0',
		NULL,
		NULL,
		'2018-11-17 11:29:07',
		NULL
	);

INSERT INTO `artisan_goods`
VALUES
	(
		'4',
		'macbook air 256SSD',
		'http://baidu.com',
		'macbook',
		'macbook',
		'11000.00',
		'10500.00',
		'1',
		'100',
		'10',
		'10000',
		'0',
		'0',
		NULL,
		NULL,
		'2018-11-17 11:29:07',
		NULL
	);

INSERT INTO `artisan_goods`
VALUES
	(
		'5',
		'小米手机MIX3 翡翠绿',
		'http://baidu.com',
		'小米手机MIX3',
		'小米手机MIX3',
		'3299.00',
		'3099.00',
		'1',
		'100',
		'10',
		'10000',
		'0',
		'0',
		NULL,
		NULL,
		'2018-11-17 11:29:07',
		NULL
	);

INSERT INTO `artisan_goods`
VALUES
	(
		'6',
		'小米MIX',
		'http://baidu.com',
		'小米MIX',
		'小米MIX',
		'2499.00',
		'2099.00',
		'1',
		'100',
		'10',
		'10000',
		'0',
		'0',
		NULL,
		NULL,
		'2018-11-17 11:29:07',
		NULL
	);

INSERT INTO `elegant_essay_0`
VALUES
	(
		'1',
		'Sharding-Slave 1 DB One',
		'2018-10-26 00:00:00',
		'slave 1 One',
		'0',
		'4',
		'<p>1.AAAAAAAAAAAAAAAAAAAAAAA</p>\n<p>2.BBBBBBBBBBBBBBBBBBBBBBB</p>\n<p><img class=\"wscnph\" src=\"https://art-share.oss-cn-beijing.aliyuncs.com/255c2ab561ec48708861663a17a22d0b.jpg\" width=\"3001\" height=\"300\" /></p>\n<p>3.CCCCCCCCCCCCCCCCCCCCCCC</p>\n<p>&nbsp;</p>\n<p>&nbsp;</p>',
		'https://art-share.oss-cn-beijing.aliyuncs.com/62b9b89059554759a7f1dda1666d6f4d.jpg',
		'published',
		NULL,
		'1',
		'2018-11-09 13:23:12',
		'2018-11-09 13:23:12'
	);

INSERT INTO `elegant_essay_0`
VALUES
	(
		'2',
		'Sharding-Master  DB Two',
		'2018-10-25 00:00:00',
		'文章摘要测试',
		'0',
		'4',
		'<p>1.PPPPPPPPPPPPPPPPPPPPPPPPPPPP</p>\n<p>2.WWWWWWWWWWWWWWWWWWWWW</p>\n<p>HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH</p>\n<p><img class=\"wscnph\" src=\"https://art-share.oss-cn-beijing.aliyuncs.com/efdc31026e584b74bd23df21e617cf19.jpg\" width=\"400\" height=\"300\" /></p>',
		'https://art-share.oss-cn-beijing.aliyuncs.com/af48dd241bed4ec8a34244baca0889c0.png',
		'published',
		NULL,
		'1',
		NULL,
		'2018-10-17 22:30:57'
	);

INSERT INTO `elegant_essay_0`
VALUES
	(
		'3',
		'Sharding-Master  DB Three',
		'2018-10-25 00:00:00',
		'GGGGG',
		'0',
		'2',
		'<p><img class=\"wscnph\" src=\"https://art-share.oss-cn-beijing.aliyuncs.com/5858460bd6f544f7bf206656435e3339.jpg\" width=\"500\" height=\"400\" /></p>\n<p>&nbsp;</p>\n<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\">\n<tbody>\n<tr>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n</tr>\n<tr>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">jjjjj</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n</tr>\n<tr>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n</tr>\n<tr>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n</tr>\n<tr>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n</tr>\n<tr>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n</tr>\n<tr>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n</tr>\n<tr>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n</tr>\n</tbody>\n</table>\n<p>GGGGGGGGGGGGGGGG</p>\n<p>CCCCCCCCCCCCCCCCCCCCCCC</p>\n<p>NNNNNNNNNNNNNNNNNNNNNNN</p>\n<p><img class=\"wscnph\" src=\"https://art-share.oss-cn-beijing.aliyuncs.com/5c86db6b83644442860120ba91cd5dd5.png\" /><img class=\"wscnph\" src=\"https://art-share.oss-cn-beijing.aliyuncs.com/cb7473b0e66441ccb82eff5a6191d267.jpg\" /></p>\n<p>&nbsp;</p>\n<p>DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD</p>\n<p><img class=\"wscnph\" src=\"https://art-share.oss-cn-beijing.aliyuncs.com/620745adf9aa4a16802a6d9b427cb4e8.jpg\" /></p>',
		'https://art-share.oss-cn-beijing.aliyuncs.com/8c121afcf40c4e1991c9f14e08593ed9.jpg',
		'published',
		NULL,
		'1',
		NULL,
		'2018-10-20 08:52:53'
	);

INSERT INTO `elegant_essay_0`
VALUES
	(
		'4',
		'Sharding-Master DB One',
		'2018-10-23 00:00:00',
		'slave 1 Four',
		'0',
		'4',
		'<p>EEEEEEEEEEEEEEEEEEEEEEEEEEE</p>\n<p>RRRRRRRRRRRRRRRRRRRRRRRR</p>\n<p><img class=\"wscnph\" src=\"https://elegant-essay.oss-cn-beijing.aliyuncs.com/619575303c67487e986aedf931e59bd8.jpg\" /><img class=\"wscnph\" src=\"https://elegant-essay.oss-cn-beijing.aliyuncs.com/95a8b8f2f80b4ccebbdcc6510cee9b3c.png\" width=\"600\" height=\"400\" /><img class=\"wscnph\" src=\"https://elegant-essay.oss-cn-beijing.aliyuncs.com/a9774b2601984ff49ee580ce3e54d81c.jpg\" /></p>\n<p>YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY</p>\n<p>UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU</p>\n<p>&nbsp;</p>',
		'https://elegant-essay.oss-cn-beijing.aliyuncs.com/c3a273c327424672b89ae637d51dce23.jpg',
		'published',
		NULL,
		'1',
		'2018-10-31 16:39:46',
		'2018-10-31 16:39:46'
	);

INSERT INTO `elegant_essay_0`
VALUES
	(
		'5',
		'Sharding-Master DB One',
		'2018-10-16 00:00:00',
		'slave 1 Five',
		'1',
		'4',
		'<p><strong><em><span style=\"text-decoration: underline;\">WWWWWWWWWWWWWWWWWW</span></em></strong></p>\n<p><!-- pagebreak --><!-- pagebreak --></p>\n<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\">\n<tbody>\n<tr>\n<td style=\"width: 33.3333%;\"><img src=\"static/tinymce4.7.5/plugins/emoticons/img/smiley-laughing.gif\" alt=\"laughing\" /><span style=\"background-color: #99cc00;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <br /></span></td>\n<td style=\"width: 33.3333%;\">&nbsp;</td>\n<td style=\"width: 33.3333%;\">&nbsp;</td>\n</tr>\n<tr>\n<td style=\"width: 33.3333%;\">&nbsp;</td>\n<td style=\"width: 33.3333%;\">&nbsp;</td>\n<td style=\"width: 33.3333%;\">&nbsp;</td>\n</tr>\n</tbody>\n</table>\n<p>RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR</p>\n<p>TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT<img class=\"wscnph\" src=\"https://elegant-essay.oss-cn-beijing.aliyuncs.com/43030623292e4300ac4266597571da9f.jpg\" width=\"600\" height=\"400\" /></p>\n<p>&nbsp;</p>\n<p>FJJFJFJFJFFJFJFJFGGGGGGGGGGGGGKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK</p>\n<p>&nbsp;</p>\n<p><img class=\"wscnph\" src=\"https://elegant-essay.oss-cn-beijing.aliyuncs.com/b7890dc4c4784a2b836b1eaea98274de.jpg\" /></p>',
		'https://elegant-essay.oss-cn-beijing.aliyuncs.com/22ef1c47423f46c585a2625898671c89.jpg',
		'published',
		NULL,
		'1',
		'2018-10-31 17:41:24',
		'2018-10-31 17:41:24'
	);

INSERT INTO `elegant_essay_1`
VALUES
	(
		'1',
		'Sharding-Slave 1 DB One',
		'2018-10-26 00:00:00',
		'slave 1 One',
		'0',
		'4',
		'<p>1.AAAAAAAAAAAAAAAAAAAAAAA</p>\n<p>2.BBBBBBBBBBBBBBBBBBBBBBB</p>\n<p><img class=\"wscnph\" src=\"https://art-share.oss-cn-beijing.aliyuncs.com/255c2ab561ec48708861663a17a22d0b.jpg\" width=\"3001\" height=\"300\" /></p>\n<p>3.CCCCCCCCCCCCCCCCCCCCCCC</p>\n<p>&nbsp;</p>\n<p>&nbsp;</p>',
		'https://art-share.oss-cn-beijing.aliyuncs.com/62b9b89059554759a7f1dda1666d6f4d.jpg',
		'published',
		NULL,
		'1',
		'2018-11-09 13:23:12',
		'2018-11-09 13:23:12'
	);

INSERT INTO `elegant_essay_1`
VALUES
	(
		'2',
		'Sharding-Master  DB Two',
		'2018-10-25 00:00:00',
		'文章摘要测试',
		'0',
		'4',
		'<p>1.PPPPPPPPPPPPPPPPPPPPPPPPPPPP</p>\n<p>2.WWWWWWWWWWWWWWWWWWWWW</p>\n<p>HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH</p>\n<p><img class=\"wscnph\" src=\"https://art-share.oss-cn-beijing.aliyuncs.com/efdc31026e584b74bd23df21e617cf19.jpg\" width=\"400\" height=\"300\" /></p>',
		'https://art-share.oss-cn-beijing.aliyuncs.com/af48dd241bed4ec8a34244baca0889c0.png',
		'published',
		NULL,
		'1',
		NULL,
		'2018-10-17 22:30:57'
	);

INSERT INTO `elegant_essay_1`
VALUES
	(
		'3',
		'Sharding-Master  DB Three',
		'2018-10-25 00:00:00',
		'GGGGG',
		'0',
		'2',
		'<p><img class=\"wscnph\" src=\"https://art-share.oss-cn-beijing.aliyuncs.com/5858460bd6f544f7bf206656435e3339.jpg\" width=\"500\" height=\"400\" /></p>\n<p>&nbsp;</p>\n<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\">\n<tbody>\n<tr>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n</tr>\n<tr>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">jjjjj</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n</tr>\n<tr>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n</tr>\n<tr>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n</tr>\n<tr>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n</tr>\n<tr>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n</tr>\n<tr>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n</tr>\n<tr>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n<td style=\"width: 12.5%;\">&nbsp;</td>\n</tr>\n</tbody>\n</table>\n<p>GGGGGGGGGGGGGGGG</p>\n<p>CCCCCCCCCCCCCCCCCCCCCCC</p>\n<p>NNNNNNNNNNNNNNNNNNNNNNN</p>\n<p><img class=\"wscnph\" src=\"https://art-share.oss-cn-beijing.aliyuncs.com/5c86db6b83644442860120ba91cd5dd5.png\" /><img class=\"wscnph\" src=\"https://art-share.oss-cn-beijing.aliyuncs.com/cb7473b0e66441ccb82eff5a6191d267.jpg\" /></p>\n<p>&nbsp;</p>\n<p>DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD</p>\n<p><img class=\"wscnph\" src=\"https://art-share.oss-cn-beijing.aliyuncs.com/620745adf9aa4a16802a6d9b427cb4e8.jpg\" /></p>',
		'https://art-share.oss-cn-beijing.aliyuncs.com/8c121afcf40c4e1991c9f14e08593ed9.jpg',
		'published',
		NULL,
		'1',
		NULL,
		'2018-10-20 08:52:53'
	);

INSERT INTO `elegant_essay_1`
VALUES
	(
		'4',
		'Sharding-Master DB One',
		'2018-10-23 00:00:00',
		'slave 1 Four',
		'0',
		'4',
		'<p>EEEEEEEEEEEEEEEEEEEEEEEEEEE</p>\n<p>RRRRRRRRRRRRRRRRRRRRRRRR</p>\n<p><img class=\"wscnph\" src=\"https://elegant-essay.oss-cn-beijing.aliyuncs.com/619575303c67487e986aedf931e59bd8.jpg\" /><img class=\"wscnph\" src=\"https://elegant-essay.oss-cn-beijing.aliyuncs.com/95a8b8f2f80b4ccebbdcc6510cee9b3c.png\" width=\"600\" height=\"400\" /><img class=\"wscnph\" src=\"https://elegant-essay.oss-cn-beijing.aliyuncs.com/a9774b2601984ff49ee580ce3e54d81c.jpg\" /></p>\n<p>YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY</p>\n<p>UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU</p>\n<p>&nbsp;</p>',
		'https://elegant-essay.oss-cn-beijing.aliyuncs.com/c3a273c327424672b89ae637d51dce23.jpg',
		'published',
		NULL,
		'1',
		'2018-10-31 16:39:46',
		'2018-10-31 16:39:46'
	);

INSERT INTO `elegant_essay_1`
VALUES
	(
		'5',
		'Sharding-Master DB One',
		'2018-10-16 00:00:00',
		'slave 1 Five',
		'1',
		'4',
		'<p><strong><em><span style=\"text-decoration: underline;\">WWWWWWWWWWWWWWWWWW</span></em></strong></p>\n<p><!-- pagebreak --><!-- pagebreak --></p>\n<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\">\n<tbody>\n<tr>\n<td style=\"width: 33.3333%;\"><img src=\"static/tinymce4.7.5/plugins/emoticons/img/smiley-laughing.gif\" alt=\"laughing\" /><span style=\"background-color: #99cc00;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <br /></span></td>\n<td style=\"width: 33.3333%;\">&nbsp;</td>\n<td style=\"width: 33.3333%;\">&nbsp;</td>\n</tr>\n<tr>\n<td style=\"width: 33.3333%;\">&nbsp;</td>\n<td style=\"width: 33.3333%;\">&nbsp;</td>\n<td style=\"width: 33.3333%;\">&nbsp;</td>\n</tr>\n</tbody>\n</table>\n<p>RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR</p>\n<p>TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT<img class=\"wscnph\" src=\"https://elegant-essay.oss-cn-beijing.aliyuncs.com/43030623292e4300ac4266597571da9f.jpg\" width=\"600\" height=\"400\" /></p>\n<p>&nbsp;</p>\n<p>FJJFJFJFJFFJFJFJFGGGGGGGGGGGGGKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK</p>\n<p>&nbsp;</p>\n<p><img class=\"wscnph\" src=\"https://elegant-essay.oss-cn-beijing.aliyuncs.com/b7890dc4c4784a2b836b1eaea98274de.jpg\" /></p>',
		'https://elegant-essay.oss-cn-beijing.aliyuncs.com/22ef1c47423f46c585a2625898671c89.jpg',
		'published',
		NULL,
		'1',
		'2018-10-31 17:41:24',
		'2018-10-31 17:41:24'
	);

INSERT INTO `elegant_user`
VALUES
	(
		'1',
		'admin',
		'123456',
		'超级管理员',
		'16602116671',
		NULL,
		'0',
		'0',
		'2018-11-17 11:18:49',
		'2018-10-21 11:08:22'
	);

INSERT INTO `elegant_user`
VALUES
	(
		'2',
		'normal',
		'111111',
		'聂晓旭',
		'18602116672',
		NULL,
		'1',
		'0',
		'2018-10-20 20:57:48',
		NULL
	);

INSERT INTO `elegant_user`
VALUES
	(
		'3',
		'editor',
		'111111',
		'掌小萌',
		'18602116673',
		NULL,
		'1',
		'0',
		'2018-10-20 20:59:58',
		NULL
	);

INSERT INTO `elegant_user`
VALUES
	(
		'4',
		'HelloKitty',
		'111111',
		'HelloKitty',
		'18602116674',
		NULL,
		'1',
		'0',
		'2018-10-30 20:12:32',
		NULL
	);

INSERT INTO `shipping_address`
VALUES
	(
		'1',
		'1',
		'1',
		'1',
		'100001',
		'超级管理员地址',
		'16602116671',
		'超级管理员',
		'0',
		'2018-11-17 11:22:02',
		NULL
	);

INSERT INTO `shipping_address`
VALUES
	(
		'2',
		'2',
		'2',
		'2',
		'100002',
		'amanxu收货地址',
		'16602116672',
		'聂晓旭',
		'0',
		'2018-11-17 11:22:42',
		NULL
	);

INSERT INTO `shipping_address`
VALUES
	(
		'3',
		'3',
		'3',
		'3',
		'100003',
		'金融街海伦中心',
		'16602116672',
		'掌小萌',
		'0',
		'2018-11-17 11:23:21',
		NULL
	);

INSERT INTO `shipping_address`
VALUES
	(
		'4',
		'4',
		'4',
		'4',
		'100004',
		'Kitty收货地址，海伦路地铁站D栋',
		'16602116674',
		'HelloKitty',
		'0',
		'2018-11-17 11:24:14',
		NULL
	);

INSERT INTO `ds_app_biz_uid` (
	`app_name`,
	`biz_name`,
	`biz_uid`,
	`create_time`,
	`update_time`
)
VALUES
	(
		'elegant-essay',
		'GOODS_ORDER_ID',
		'1',
		'2018-11-20 14:28:18',
		'2018-11-20 14:28:20'
	);

INSERT INTO `ds_app_biz_uid` (
	`app_name`,
	`biz_name`,
	`biz_uid`,
	`create_time`,
	`update_time`
)
VALUES
	(
		'elegant-essay',
		'GOODS_ORDER_ITEM_ID',
		'1',
		'2018-11-20 14:29:35',
		'2018-11-20 14:29:39'
	);

