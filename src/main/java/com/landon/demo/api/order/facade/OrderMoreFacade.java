package com.landon.demo.api.order.facade;


/**
 * @author SSP
 * @date 2021/3/3 0003 10:55
 */
public interface OrderMoreFacade {

    /**
     * 多线程导出订单表所有的数据
     * @return 文件所在服务器地址
     */
    String exportMore();
}
