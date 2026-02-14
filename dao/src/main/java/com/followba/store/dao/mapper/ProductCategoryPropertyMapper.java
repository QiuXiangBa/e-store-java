package com.followba.store.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.followba.store.dao.po.ProductCategoryProperty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductCategoryPropertyMapper extends BaseMapper<ProductCategoryProperty> {

    void insertBatch(@Param("list") List<ProductCategoryProperty> list);
}
