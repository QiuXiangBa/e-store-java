package com.followba.store.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.followba.store.dao.po.TradeCheckoutItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TradeCheckoutItemMapper extends BaseMapper<TradeCheckoutItem> {

    int batchInsert(@Param("list") List<TradeCheckoutItem> list);
}
