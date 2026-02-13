package com.followba.store.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.followba.store.dao.po.ProductCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

    int updateSortBatch(@Param("list") List<ProductCategory> list);
}
