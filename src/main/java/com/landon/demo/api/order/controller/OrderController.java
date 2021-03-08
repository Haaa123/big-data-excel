package com.landon.demo.api.order.controller;

import com.landon.demo.api.order.facade.OrderMoreFacade;
import com.landon.demo.api.order.facade.OrderSingletonFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


/**
 * @author SSP
 * @date 2021/2/25 0025 11:24
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderSingletonFacade orderSingletonFacade;
    private final OrderMoreFacade orderMoreFacade;

    public OrderController(OrderSingletonFacade orderSingletonFacade, OrderMoreFacade orderMoreFacade) {
        this.orderSingletonFacade = orderSingletonFacade;
        this.orderMoreFacade = orderMoreFacade;
    }

    @GetMapping("/export-singleton")
    public void exportSingleton(HttpServletResponse response) {
         this.orderSingletonFacade.exportSingleton(response);
    }

    @GetMapping("/export-more")
    public String exportMore() {
        return orderMoreFacade.exportMore();
    }
}
