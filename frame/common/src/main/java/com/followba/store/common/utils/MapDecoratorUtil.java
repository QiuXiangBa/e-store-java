package com.followba.store.common.utils;

import java.util.*;

/**
 * @author 祥霸
 * @since 1.0.0
 */
public class MapDecoratorUtil<K, V> {
    private final Map<K,V> map;

    public MapDecoratorUtil(Map<K, V> map) {
        this.map = map;
    }

    public static <K, V> MapDecoratorUtil<K, V> valueOf(Map<K, V> map) {
        return new MapDecoratorUtil<>(map);
    }


    public Set<V> getAll(List<K> keys) {
        Set<V> set = new HashSet<>();
        for (K key : keys) {
            V v = map.get(key);
            if(Objects.isNull(v)) {
                continue;
            }
            set.add(v);
        }

        return set;
    }
}
