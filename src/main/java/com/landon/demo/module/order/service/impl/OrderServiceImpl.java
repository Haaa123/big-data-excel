package com.landon.demo.module.order.service.impl;

import com.landon.demo.module.order.entity.OrderEntity;
import com.landon.demo.module.order.repository.OrderRepo;
import com.landon.demo.module.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author SSP
 * @date 2021/2/25 0025 11:23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;

    public OrderServiceImpl(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }


    @Override
    public List<OrderEntity> findAllBySubsectionSearch(boolean isDeleted, Long lastMaxId, Long limit) {
        return orderRepo.findAllBySubsectionSearch(isDeleted, lastMaxId, limit);
    }
}
