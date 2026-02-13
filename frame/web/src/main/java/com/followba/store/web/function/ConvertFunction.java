package com.followba.store.web.function;

/**
 * @author 祥霸
 * @since 1.0.0
 */
@FunctionalInterface
public interface ConvertFunction<T, S> {

    /**
     * pojo转换
     *
     * @param s 源对象
     * @return 目标对象
     */
    T sToT(S s);
}
