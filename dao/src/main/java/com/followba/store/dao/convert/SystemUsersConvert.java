package com.followba.store.dao.convert;

import com.followba.store.dao.dto.SystemUsersDTO;
import com.followba.store.dao.po.SystemUsers;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SystemUsersConvert {

    SystemUsersConvert INSTANCE = Mappers.getMapper(SystemUsersConvert.class);

    SystemUsersDTO toDTO(SystemUsers po);

    List<SystemUsersDTO> toDTO(List<SystemUsers> po);

    SystemUsers toPO(SystemUsersDTO dto);
}
