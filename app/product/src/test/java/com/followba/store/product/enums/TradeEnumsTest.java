package com.followba.store.product.enums;

import com.followba.store.dao.enums.TradeCartSelectedEnum;
import com.followba.store.dao.enums.TradeCheckoutOrderStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TradeEnumsTest {

    @Test
    void contains_shouldReturnTrue_whenTradeCartSelectedCodeExists() {
        Assertions.assertTrue(TradeCartSelectedEnum.contains(0));
        Assertions.assertTrue(TradeCartSelectedEnum.contains(1));
    }

    @Test
    void contains_shouldReturnFalse_whenTradeCartSelectedCodeNotExists() {
        Assertions.assertFalse(TradeCartSelectedEnum.contains(2));
    }

    @Test
    void contains_shouldReturnTrue_whenCheckoutStatusCodeExists() {
        Assertions.assertTrue(TradeCheckoutOrderStatusEnum.contains(0));
    }

    @Test
    void contains_shouldReturnFalse_whenCheckoutStatusCodeNotExists() {
        Assertions.assertFalse(TradeCheckoutOrderStatusEnum.contains(9));
    }
}
