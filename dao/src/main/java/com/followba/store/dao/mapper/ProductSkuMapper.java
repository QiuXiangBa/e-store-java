package com.followba.store.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.followba.store.dao.dto.ProductSkuStockChangeDTO;
import com.followba.store.dao.po.ProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

    int batchInsert(@Param("list") List<ProductSku> list);

    int batchReduceStock(@Param("list") List<ProductSkuStockChangeDTO> list);

    int batchIncreaseStock(@Param("list") List<ProductSkuStockChangeDTO> list);
}
