package com.landon.demo.api.order.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.landon.demo.api.order.converter.BooleanConverter;
import com.landon.demo.api.order.converter.DateConverter;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * @author SSP
 * @date 2021/2/25 0025 15:13
 */
@Data
public class OrderExcelDto {

    @ExcelProperty("创建者")
    private String createBy;

    @ExcelProperty("更新者")
    private String updateBy;

    @ExcelProperty(value = "创建时间", converter = DateConverter.class)
    private LocalDateTime createTime;

    @ExcelProperty(value = "更新时间", converter = DateConverter.class)
    private LocalDateTime updateTime;

    @ExcelProperty(value = "数据状态", converter = BooleanConverter.class)
    private Boolean isDeleted;

    @ExcelProperty(value = "支付时间", converter = DateConverter.class)
    private LocalDateTime payTime;

    @ExcelProperty(value = "发货时间", converter = DateConverter.class)
    private LocalDateTime deliveryTime;

    @ExcelProperty(value = "确认时间", converter = DateConverter.class)
    private LocalDateTime confirmTime;

    @ExcelProperty("订单编号")
    private String code;

    @ExcelProperty("收件人")
    private String receiver;

    @ExcelProperty("手机号")
    private String mobile;

    @ExcelProperty("订单备注")
    private String description;
}
