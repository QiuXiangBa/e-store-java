package com.followba.store.dao.convert;

import com.followba.store.dao.dto.MemberUsersDTO;
import com.followba.store.dao.po.MemberUsers;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MemberUsersConvert {

    MemberUsersConvert INSTANCE = Mappers.getMapper(MemberUsersConvert.class);

    MemberUsersDTO toDTO(MemberUsers po);

    List<MemberUsersDTO> toDTO(List<MemberUsers> po);

    MemberUsers toPO(MemberUsersDTO dto);
}
