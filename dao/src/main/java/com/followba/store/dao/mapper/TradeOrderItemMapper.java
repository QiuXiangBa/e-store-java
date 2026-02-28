package com.followba.store.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.followba.store.dao.po.TradeOrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TradeOrderItemMapper extends BaseMapper<TradeOrderItem> {

    int batchInsert(@Param("list") List<TradeOrderItem> list);
}
