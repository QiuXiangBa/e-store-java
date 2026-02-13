package com.followba.store.dao.dto;

import com.followba.store.common.resp.PageResp;
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
public class PageDTO<T> {


    private Long total;

    private List<T> list;

    public static <T> PageDTO<T> of(Long total, List<T> list) {
        return new PageDTO<>(total, list);
    }

    public static <T> PageDTO<T> empty() {
        return new PageDTO<>(0L, Collections.emptyList());
    }
}
