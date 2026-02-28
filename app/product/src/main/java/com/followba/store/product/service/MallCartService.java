package com.followba.store.product.service;

import com.followba.store.product.dto.CartAddDTO;
import com.followba.store.product.dto.CartItemDTO;
import com.followba.store.product.dto.CartUpdateQuantityDTO;
import com.followba.store.product.dto.CartUpdateSelectedDTO;

import java.util.List;

public interface MallCartService {

    Long add(CartAddDTO dto);

    List<CartItemDTO> list();

    void updateQuantity(CartUpdateQuantityDTO dto);

    void updateSelected(CartUpdateSelectedDTO dto);

    void delete(Long cartId);
}
