package com.followba.store.common.utils;

import cn.hutool.core.collection.CollectionUtil;

import java.util.*;

/**
 * @author 祥霸
 * @since 1.0.0
 */
public class CollectionOperationsUtil {

    public static <T> Set<T> intersection(Set<Set<T>> lists) {
        if(CollectionUtil.isEmpty(lists)) {
            return new HashSet<>();
        }
        List<Collection<T>> l = new ArrayList<>(lists);

        Set<T> result = new HashSet<>(l.get(0));
        for (Collection<T> list : lists) {
            result.retainAll(list);
        }
        return result;
    }

    public static <T> Set<T> union(Set<Set<T>> lists) {
        if(Objects.isNull(lists)) {
            return new HashSet<>();
        }
        Set<T> result = new HashSet<>();
        for (Collection<T> list : lists) {
            result.addAll(list);
        }
        return result;
    }
}
