package com.followba.store.dao.convert;

import com.followba.store.dao.dto.SystemOauth2AccessTokenDTO;
import com.followba.store.dao.po.SystemOauth2AccessToken;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SystemOauth2AccessTokenConvert {

    SystemOauth2AccessTokenConvert INSTANCE = Mappers.getMapper(SystemOauth2AccessTokenConvert.class);

    SystemOauth2AccessTokenDTO toDTO(SystemOauth2AccessToken po);

    List<SystemOauth2AccessTokenDTO> toDTO(List<SystemOauth2AccessToken> po);

    SystemOauth2AccessToken toPO(SystemOauth2AccessTokenDTO dto);
}
