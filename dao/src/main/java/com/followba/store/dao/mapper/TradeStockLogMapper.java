package com.followba.store.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.followba.store.dao.po.TradeStockLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TradeStockLogMapper extends BaseMapper<TradeStockLog> {

    int batchInsert(@Param("list") List<TradeStockLog> list);
}
