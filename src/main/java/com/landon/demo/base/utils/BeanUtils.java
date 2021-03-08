package com.landon.demo.base.utils;

import java.util.List;

/**
 * @author SSP
 * @date 2021/2/25 0025 17:17
 */
public class BeanUtils {

    public BeanUtils() { }


    /**
     * 对象与对象之间的转换
     * @param source 源对象
     * @param constructor 目标对象构造器
     * @param <T> 确定的Java类型
     * @param <E> 集合中的元素
     * @return 转换后的对象
     */
    public static <T, E> T convert(E source, Constructable<T> constructor) {
        T target = constructor.construct();
        org.springframework.beans.BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 对象与对象之间的转换（忽略属性值）
     * @param source 源对象
     * @param constructor 目标对象构造器
     * @param ignoredProperties 需要忽略的属性
     * @param <T> 确定的Java类型
     * @param <E> 集合中的元素
     * @return 转换后的对象
     */
    public static <T, E> T convert(E source, Constructable<T> constructor, String... ignoredProperties) {
        T target = constructor.construct();
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoredProperties);
        return target;
    }

    /**
     * 集合里的对象批量转换
     * @param source 源集合
     * @param constructor 目标对象构造器
     * @param <T> 确定的Java类型
     * @param <E> 集合中的元素
     * @return 转换成功后的集合
     */
    public static <T, E> List<T> convert(List<E> source, Constructable<T> constructor) {
        return ListUtils.map(source, (sourceObj) -> convert(sourceObj, constructor));
    }

    /**
     * 集合对象之间的转换时（忽略属性值）
     * @param source 源集合
     * @param constructor 目标对象构造器
     * @param ignoredProperties 需要忽略的属性
     * @param <T> 确定的Java类型
     * @param <E> 集合中的元素
     * @return 转换成功后的集合
     */
    public static <T, E> List<T> convert(List<E> source, Constructable<T> constructor, String... ignoredProperties) {
        return ListUtils.map(source, (sourceObj) -> convert(sourceObj, constructor, ignoredProperties));
    }
}
