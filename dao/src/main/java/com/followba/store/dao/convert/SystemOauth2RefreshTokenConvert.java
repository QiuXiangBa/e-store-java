package com.followba.store.dao.convert;

import com.followba.store.dao.dto.SystemOauth2RefreshTokenDTO;
import com.followba.store.dao.po.SystemOauth2RefreshToken;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SystemOauth2RefreshTokenConvert {

    SystemOauth2RefreshTokenConvert INSTANCE = Mappers.getMapper(SystemOauth2RefreshTokenConvert.class);

    SystemOauth2RefreshTokenDTO toDTO(SystemOauth2RefreshToken po);

    List<SystemOauth2RefreshTokenDTO> toDTO(List<SystemOauth2RefreshToken> po);

    SystemOauth2RefreshToken toPO(SystemOauth2RefreshTokenDTO dto);
}
