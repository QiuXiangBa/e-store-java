package com.followba.store.common.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * 通用分页响应
 *
 * @param <T> 数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResp<T> {

    private Long total;

    private List<T> list;

    public static <T> PageResp<T> of(Long total, List<T> list) {
        return new PageResp<>(total, list);
    }

    public static <T> PageResp<T> empty() {
        return new PageResp<>(0L, Collections.emptyList());
    }
}
