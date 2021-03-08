package com.landon.demo.module.order.repository;

import com.landon.demo.module.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author SSP
 * @date 2021/2/25 0025 11:20
 */
public interface OrderRepo extends JpaRepository<OrderEntity, Long> {


    /**
     * 滚动查询数据
     * @param lastMaxId 结束id
     * @param isDeleted 数据状态
     * @param limit 偏移量
     * @return 这次查询出来的结果
     */
    @Query(value = "SELECT * FROM o_order_item WHERE is_deleted = ?1 AND id > ?2 ORDER BY id ASC LIMIT ?3", nativeQuery = true)
    List<OrderEntity> findAllBySubsectionSearch(boolean isDeleted, Long lastMaxId, Long limit);
}
