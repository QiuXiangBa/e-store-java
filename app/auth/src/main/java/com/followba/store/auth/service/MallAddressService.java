package com.followba.store.auth.service;

import com.followba.store.auth.dto.TradeUserAddressCreateDTO;
import com.followba.store.auth.dto.TradeUserAddressDTO;
import com.followba.store.auth.dto.TradeUserAddressUpdateDTO;

import java.util.List;

public interface MallAddressService {

    List<TradeUserAddressDTO> list();

    Long create(TradeUserAddressCreateDTO dto);

    void update(TradeUserAddressUpdateDTO dto);

    void delete(Long id);

    void setDefault(Long id);
}
