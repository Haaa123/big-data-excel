package com.landon.demo.base.utils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author SSP
 * @date 2021/2/25 0025 17:24
 */
public class ListUtils<T> {

    public static <T, R> List<R> map(List<T> list, Function<? super T, ? extends R> mapper) {
        return list.stream().map(mapper).collect(Collectors.toList());
    }
}
