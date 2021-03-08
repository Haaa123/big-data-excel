package com.landon.demo.module.order.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author SSP
 * @date 2021/2/25 0025 11:10
 */

@Data
@Entity(name = "order")
@Table(name = "o_order_item")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String createBy;

    private String updateBy;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDeleted = false;

    private LocalDateTime payTime;

    private LocalDateTime deliveryTime;

    private LocalDateTime confirmTime;

    private String code;

    private String receiver;

    private String mobile;

    private String description;
}
