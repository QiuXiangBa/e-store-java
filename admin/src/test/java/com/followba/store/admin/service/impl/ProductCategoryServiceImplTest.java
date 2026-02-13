package com.followba.store.admin.service.impl;

import com.followba.store.common.exception.BizException;
import com.followba.store.dao.biz.BizProductCategoryMapper;
import com.followba.store.dao.biz.BizProductSpuMapper;
import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.dao.dto.ProductCategoryDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductCategoryServiceImplTest {

    @InjectMocks
    private ProductCategoryServiceImpl service;

    @Mock
    private BizProductCategoryMapper bizProductCategoryMapper;

    @Mock
    private BizProductSpuMapper bizProductSpuMapper;

    @Test
    void updateCategorySortBatch_shouldThrowWhenItemsEmpty() {
        BizException ex = assertThrows(BizException.class, () -> service.updateCategorySortBatch(List.of()));
        assertEquals(ProductConstants.CATEGORY_SORT_BATCH_EMPTY.code(), ex.getCode());
        verify(bizProductCategoryMapper, never()).updateSortBatch(anyList());
    }

    @Test
    void updateCategorySortBatch_shouldThrowWhenDuplicateId() {
        ProductCategoryDTO item1 = createItem(1L, 10);
        ProductCategoryDTO item2 = createItem(1L, 20);

        BizException ex = assertThrows(BizException.class,
                () -> service.updateCategorySortBatch(List.of(item1, item2)));
        assertEquals(ProductConstants.CATEGORY_SORT_ID_DUPLICATE.code(), ex.getCode());
        verify(bizProductCategoryMapper, never()).updateSortBatch(anyList());
    }

    @Test
    void updateCategorySortBatch_shouldThrowWhenBatchSizeExceed() {
        List<ProductCategoryDTO> items = new ArrayList<>();
        for (int i = 0; i <= ProductConstants.CATEGORY_SORT_BATCH_MAX_SIZE; i++) {
            items.add(createItem((long) i + 1, i));
        }

        BizException ex = assertThrows(BizException.class, () -> service.updateCategorySortBatch(items));
        assertEquals(ProductConstants.CATEGORY_SORT_BATCH_SIZE_EXCEED.code(), ex.getCode());
        verify(bizProductCategoryMapper, never()).updateSortBatch(anyList());
    }

    @Test
    void updateCategorySortBatch_shouldThrowWhenCategoryNotExists() {
        ProductCategoryDTO item1 = createItem(1L, 10);
        ProductCategoryDTO item2 = createItem(2L, 20);

        when(bizProductCategoryMapper.selectByIds(List.of(1L, 2L))).thenReturn(List.of(item1));

        BizException ex = assertThrows(BizException.class,
                () -> service.updateCategorySortBatch(List.of(item1, item2)));
        assertEquals(ProductConstants.CATEGORY_NOT_EXISTS.code(), ex.getCode());
        verify(bizProductCategoryMapper, never()).updateSortBatch(anyList());
    }

    @Test
    void updateCategorySortBatch_shouldSuccess() {
        ProductCategoryDTO item1 = createItem(1L, 10);
        ProductCategoryDTO item2 = createItem(2L, 20);
        List<ProductCategoryDTO> items = List.of(item1, item2);

        when(bizProductCategoryMapper.selectByIds(List.of(1L, 2L))).thenReturn(items);

        service.updateCategorySortBatch(items);

        verify(bizProductCategoryMapper).updateSortBatch(items);
    }

    private ProductCategoryDTO createItem(Long id, Integer sort) {
        ProductCategoryDTO item = new ProductCategoryDTO();
        item.setId(id);
        item.setSort(sort);
        return item;
    }
}
