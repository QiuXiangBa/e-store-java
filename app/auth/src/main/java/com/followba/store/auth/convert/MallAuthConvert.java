package com.followba.store.auth.convert;

import com.followba.store.auth.dto.AuthGuestCartItemDTO;
import com.followba.store.auth.dto.AuthLoginDTO;
import com.followba.store.auth.dto.AuthLoginResultDTO;
import com.followba.store.auth.dto.AuthRegisterDTO;
import com.followba.store.auth.dto.AuthUserDTO;
import com.followba.store.auth.dto.TradeUserAddressCreateDTO;
import com.followba.store.auth.dto.TradeUserAddressDTO;
import com.followba.store.auth.dto.TradeUserAddressUpdateDTO;
import com.followba.store.auth.vo.in.AddressCreateIn;
import com.followba.store.auth.vo.in.AddressUpdateIn;
import com.followba.store.auth.vo.in.AuthGuestCartItemIn;
import com.followba.store.auth.vo.in.AuthLoginIn;
import com.followba.store.auth.vo.in.AuthRegisterIn;
import com.followba.store.auth.vo.out.AddressOut;
import com.followba.store.auth.vo.out.AuthLoginOut;
import com.followba.store.auth.vo.out.AuthUserOut;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MallAuthConvert {

    MallAuthConvert INSTANCE = Mappers.getMapper(MallAuthConvert.class);

    AuthLoginDTO toAuthLoginDTO(AuthLoginIn in);

    AuthRegisterDTO toAuthRegisterDTO(AuthRegisterIn in);

    AuthGuestCartItemDTO toAuthGuestCartItemDTO(AuthGuestCartItemIn in);

    List<AuthGuestCartItemDTO> toAuthGuestCartItemDTOList(List<AuthGuestCartItemIn> inList);

    AuthLoginOut toAuthLoginOut(AuthLoginResultDTO dto);

    AuthUserOut toAuthUserOut(AuthUserDTO dto);

    TradeUserAddressCreateDTO toTradeUserAddressCreateDTO(AddressCreateIn in);

    TradeUserAddressUpdateDTO toTradeUserAddressUpdateDTO(AddressUpdateIn in);

    AddressOut toAddressOut(TradeUserAddressDTO dto);

    List<AddressOut> toAddressOutList(List<TradeUserAddressDTO> dtoList);
}
