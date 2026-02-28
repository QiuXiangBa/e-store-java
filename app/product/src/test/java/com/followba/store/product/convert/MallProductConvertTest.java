package com.followba.store.product.convert;

import com.followba.store.product.dto.ProductPageQueryDTO;
import com.followba.store.product.vo.in.ProductPageIn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MallProductConvertTest {

    @Test
    void toProductPageQueryDTO_shouldMapFields() {
        ProductPageIn in = new ProductPageIn();
        in.setPageNum(2);
        in.setPageSize(30);
        in.setKeyword("iphone");
        in.setCategoryId(100L);

        ProductPageQueryDTO dto = MallProductConvert.INSTANCE.toProductPageQueryDTO(in);

        Assertions.assertEquals(2, dto.getPageNum());
        Assertions.assertEquals(30, dto.getPageSize());
        Assertions.assertEquals("iphone", dto.getKeyword());
        Assertions.assertEquals(100L, dto.getCategoryId());
    }
}
