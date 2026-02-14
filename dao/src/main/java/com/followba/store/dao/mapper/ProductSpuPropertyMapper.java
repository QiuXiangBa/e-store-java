package com.followba.store.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.followba.store.dao.po.ProductSpuProperty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductSpuPropertyMapper extends BaseMapper<ProductSpuProperty> {

    void insertBatch(@Param("list") List<ProductSpuProperty> list);
}
