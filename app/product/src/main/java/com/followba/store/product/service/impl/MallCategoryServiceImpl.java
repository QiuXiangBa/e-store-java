package com.followba.store.product.service.impl;

import com.followba.store.dao.biz.BizProductCategoryMapper;
import com.followba.store.dao.dto.ProductCategoryDTO;
import com.followba.store.dao.enums.StatusEnums;
import com.followba.store.product.dto.ProductAppCategoryDTO;
import com.followba.store.product.service.MallCategoryService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 商城分类服务实现 / Mall category service implementation.
 */
@Service
public class MallCategoryServiceImpl implements MallCategoryService {

    @Resource
    private BizProductCategoryMapper bizProductCategoryMapper;

    @Override
    public List<ProductAppCategoryDTO> listEnabledLeaf() {
        // 读取全量分类后在服务层按业务规则过滤，避免 DAO 暴露 VO 语义
        // Read all categories then filter by business rules in service layer.
        List<ProductCategoryDTO> categoryDTOList = bizProductCategoryMapper.selectList(null, null, null);
        return categoryDTOList.stream()
                .filter(category -> Objects.equals(category.getStatus(), StatusEnums.OPEN.getCode()))
                .filter(category -> Boolean.TRUE.equals(category.getIsLeaf()))
                .sorted(Comparator
                        .comparing(ProductCategoryDTO::getSort, Comparator.nullsLast(Integer::compareTo))
                        .thenComparing(ProductCategoryDTO::getId, Comparator.nullsLast(Long::compareTo)))
                .map(this::toAppCategoryDTO)
                .toList();
    }

    private ProductAppCategoryDTO toAppCategoryDTO(ProductCategoryDTO categoryDTO) {
        ProductAppCategoryDTO dto = new ProductAppCategoryDTO();
        dto.setId(categoryDTO.getId());
        dto.setParentId(categoryDTO.getParentId());
        dto.setName(categoryDTO.getName());
        dto.setSort(categoryDTO.getSort());
        return dto;
    }
}

