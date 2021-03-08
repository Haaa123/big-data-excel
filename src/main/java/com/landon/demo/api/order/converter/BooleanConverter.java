package com.landon.demo.api.order.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @author SSP
 * @date 2021/2/25 0025 16:15
 */
public class BooleanConverter implements Converter<Boolean> {

    @Override
    public Class<Boolean> supportJavaTypeKey() {
        return Boolean.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Boolean convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
        return null;
    }

    @Override
    public CellData<String> convertToExcelData(Boolean aBoolean, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
        return new CellData<>(aBoolean.equals(true) ? "是" : "否");
    }
}
