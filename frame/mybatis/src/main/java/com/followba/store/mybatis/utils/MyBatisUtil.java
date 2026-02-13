package com.followba.store.mybatis.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author 祥霸
 * @version 1.1.0
 */
public class MyBatisUtil {

    public static <T> Page<T> buildPage(Integer pageNum, Integer pageSize) {
        return new Page<>(pageNum, pageSize);
    }
}
