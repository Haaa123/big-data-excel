/**
  订单表
 */
CREATE TABLE o_order (
                         `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id主键',
                         `create_by` varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
                         `update_by` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
                         `create_time` datetime NOT NULL COMMENT '创建时间',
                         `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                         `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '数据状态',
                         `pay_time` datetime NOT NULL COMMENT '支付日期',
                         `delivery_time` datetime DEFAULT NULL COMMENT '发货日期',
                         `confirm_time` datetime DEFAULT NULL COMMENT '确认日期',
                         `code` varchar(255) NOT NULL COMMENT '订单号',
                         `receiver` varchar(255) NOT NULL COMMENT '收件人',
                         `mobile` varchar(255) NOT NULL COMMENT '手机号码',
                         `description` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='订单表';

/**
  订单项表
 */
CREATE TABLE o_order_item (
                         `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id主键',
                         `create_by` varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
                         `update_by` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
                         `create_time` datetime NOT NULL COMMENT '创建时间',
                         `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                         `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '数据状态',
                         `pay_time` datetime NOT NULL COMMENT '支付日期',
                         `delivery_time` datetime DEFAULT NULL COMMENT '发货日期',
                         `confirm_time` datetime DEFAULT NULL COMMENT '确认日期',
                         `code` varchar(255) NOT NULL COMMENT '订单号',
                         `receiver` varchar(255) NOT NULL COMMENT '收件人',
                         `mobile` varchar(255) NOT NULL COMMENT '手机号码',
                         `description` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='订单项表';

/**
  订单表定义存储过程
 */
DROP PROCEDURE
    IF
    EXISTS `my_insert`;

DELIMITER;;
CREATE PROCEDURE `my_insert` () BEGIN
	DECLARE
i INT DEFAULT 1;
	WHILE
i <= 1000000 DO#插入语句
			INSERT INTO `o_order` ( `id`, `create_by`, `update_by`, `create_time`, `update_time`, `is_deleted`, `pay_time`, `delivery_time`, `confirm_time`, `code`, `receiver`, `mobile`, `description` )
		VALUES
			(
				NULL,
				'123@163.com',
				'456@163.com',
				now(),
				now(),
				FALSE,
				now(),
				now(),
				now(),
				substring( RAND()* 1000000000000, 1, 11 ),
				'石头',
				substring( RAND()* 1000000000000, 1, 11 ),
				'没啥备注'
			);

		SET i = i + 1;

END WHILE;
COMMIT;

END;;
DELIMITER;

/**
订单表调用存储过程
 */
CALL my_insert();



/**
  订单项表定义存储过程
 */
DROP PROCEDURE
    IF
    EXISTS `my_insert_item`;

DELIMITER;;
CREATE PROCEDURE `my_insert_item` () BEGIN
	DECLARE
i INT DEFAULT 1;
	WHILE
i <= 1000 DO#插入语句
			INSERT INTO `o_order` ( `id`, `create_by`, `update_by`, `create_time`, `update_time`, `is_deleted`, `pay_time`, `delivery_time`, `confirm_time`, `code`, `receiver`, `mobile`, `description` )
		VALUES
			(
				NULL,
				'123@163.com',
				'456@163.com',
				now(),
				now(),
				FALSE,
				now(),
				now(),
				now(),
				substring( RAND()* 1000000000000, 1, 11 ),
				'石头',
				substring( RAND()* 1000000000000, 1, 11 ),
				'没啥备注'
			);

		SET i = i + 1;

END WHILE;
COMMIT;

END;;
DELIMITER;

/**
订单项表调用存储过程
 */
CALL my_insert_item();