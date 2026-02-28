package com.followba.store.product.convert;

import com.followba.store.product.dto.CheckoutCreateDTO;
import com.followba.store.product.vo.in.CheckoutCreateIn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MallCheckoutConvertTest {

    @Test
    void toCheckoutCreateDTO_shouldMapRemark() {
        CheckoutCreateIn in = new CheckoutCreateIn();
        in.setRemark("hello");

        CheckoutCreateDTO dto = MallCheckoutConvert.INSTANCE.toCheckoutCreateDTO(in);

        Assertions.assertEquals("hello", dto.getRemark());
    }
}
