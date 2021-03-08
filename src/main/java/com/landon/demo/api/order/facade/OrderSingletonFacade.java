package com.landon.demo.api.order.facade;


import javax.servlet.http.HttpServletResponse;

/**
 * @author SSP
 * @date 2021/3/3 0003 10:40
 */
public interface OrderSingletonFacade {

    /**
     * 单线程导出订单表所有的数据
     * @param response response
     */
    void exportSingleton(HttpServletResponse response);
}
