package com.followba.store.product.service;

import com.followba.store.product.dto.CheckoutCreateDTO;
import com.followba.store.product.dto.CheckoutResultDTO;

public interface MallCheckoutService {

    CheckoutResultDTO create(CheckoutCreateDTO dto);
}
