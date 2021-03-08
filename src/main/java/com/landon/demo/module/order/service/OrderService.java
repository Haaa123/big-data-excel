package com.landon.demo.module.order.service;


import com.landon.demo.module.order.entity.OrderEntity;

import java.util.List;

/**
 * @author SSP
 * @date 2021/2/25 0025 11:23
 */
public interface OrderService {

    /**
     * 滚动查询
     * @param isDeleted 数据状态
     * @param lastMaxId 起始ID
     * @param limit 偏移量
     * @return 分块数据集合
     */
    List<OrderEntity>  findAllBySubsectionSearch(boolean isDeleted, Long lastMaxId, Long limit);
}
