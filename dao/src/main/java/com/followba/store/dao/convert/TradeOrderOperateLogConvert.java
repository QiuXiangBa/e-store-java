package com.followba.store.dao.convert;

import com.followba.store.dao.dto.TradeOrderOperateLogDTO;
import com.followba.store.dao.po.TradeOrderOperateLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TradeOrderOperateLogConvert {

    TradeOrderOperateLogConvert INSTANCE = Mappers.getMapper(TradeOrderOperateLogConvert.class);

    TradeOrderOperateLog toPO(TradeOrderOperateLogDTO dto);
}
