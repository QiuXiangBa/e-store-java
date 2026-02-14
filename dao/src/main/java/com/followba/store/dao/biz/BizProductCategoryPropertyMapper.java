package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.followba.store.dao.convert.ProductCategoryPropertyConvert;
import com.followba.store.dao.dto.ProductCategoryPropertyDTO;
import com.followba.store.dao.mapper.ProductCategoryPropertyMapper;
import com.followba.store.dao.po.ProductCategoryProperty;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BizProductCategoryPropertyMapper {

    @Resource
    private ProductCategoryPropertyMapper mapper;

    public List<ProductCategoryPropertyDTO> selectListByCategoryId(Long categoryId) {
        LambdaQueryWrapper<ProductCategoryProperty> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductCategoryProperty::getCategoryId, categoryId);
        wrapper.orderByAsc(ProductCategoryProperty::getSort).orderByAsc(ProductCategoryProperty::getId);
        return ProductCategoryPropertyConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }

    public List<ProductCategoryPropertyDTO> selectEnabledRequiredList(Long categoryId) {
        LambdaQueryWrapper<ProductCategoryProperty> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductCategoryProperty::getCategoryId, categoryId);
        wrapper.eq(ProductCategoryProperty::getEnabled, true);
        wrapper.eq(ProductCategoryProperty::getRequired, true);
        return ProductCategoryPropertyConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }

    public void replaceByCategoryId(Long categoryId, List<ProductCategoryPropertyDTO> list) {
        LambdaQueryWrapper<ProductCategoryProperty> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductCategoryProperty::getCategoryId, categoryId);
        mapper.delete(wrapper);
        if (list == null || list.isEmpty()) {
            return;
        }
        mapper.insertBatch(ProductCategoryPropertyConvert.INSTANCE.toPO(list));
    }
}
