package com.followba.store.admin.service.impl;

import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.admin.convert.ProductCategoryConvert;
import com.followba.store.admin.service.ProductCategoryService;
import com.followba.store.admin.vo.in.ProductCategoryListIn;
import com.followba.store.admin.vo.in.ProductCategorySaveIn;
import com.followba.store.admin.vo.out.ProductCategoryRespVO;
import com.followba.store.common.exception.BizException;
import com.followba.store.dao.biz.BizProductCategoryMapper;
import com.followba.store.dao.biz.BizProductSpuMapper;
import com.followba.store.dao.dto.ProductCategoryDTO;
import com.followba.store.dao.enums.StatusEnums;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private static final Long ROOT_PARENT_ID = 0L;

    @Resource
    private BizProductCategoryMapper bizProductCategoryMapper;

    @Resource
    private BizProductSpuMapper bizProductSpuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCategory(ProductCategorySaveIn reqVO) {
        validateParentProductCategory(reqVO.getParentId());
        ProductCategoryDTO dto = ProductCategoryConvert.INSTANCE.toDTO(reqVO);
        bizProductCategoryMapper.insert(dto);
        return dto.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(ProductCategorySaveIn reqVO) {
        if (reqVO.getId() == null) {
            throw new BizException(ProductConstants.CATEGORY_NOT_EXISTS);
        }
        validateProductCategoryExists(reqVO.getId());
        validateParentProductCategory(reqVO.getParentId());
        ProductCategoryDTO dto = ProductCategoryConvert.INSTANCE.toDTO(reqVO);
        bizProductCategoryMapper.updateById(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Long id) {
        validateProductCategoryExists(id);
        if (bizProductCategoryMapper.countByParentId(id) > 0) {
            throw new BizException(ProductConstants.CATEGORY_EXISTS_CHILDREN);
        }
        if (bizProductSpuMapper.countByCategoryId(id) > 0) {
            throw new BizException(ProductConstants.CATEGORY_HAVE_BIND_SPU);
        }
        bizProductCategoryMapper.deleteById(id);
    }

    @Override
    public ProductCategoryRespVO getCategory(Long id) {
        return ProductCategoryConvert.INSTANCE.toVO(bizProductCategoryMapper.selectById(id));
    }

    @Override
    public List<ProductCategoryRespVO> getCategoryList(ProductCategoryListIn reqVO) {
        return ProductCategoryConvert.INSTANCE.toVO(
                bizProductCategoryMapper.selectList(reqVO.getName(), reqVO.getStatus(), reqVO.getParentId())
        );
    }

    @Override
    public void validateCategory(Long id) {
        ProductCategoryDTO category = bizProductCategoryMapper.selectById(id);
        if (category == null) {
            throw new BizException(ProductConstants.CATEGORY_NOT_EXISTS);
        }
        if (category.getStatus() != null && category.getStatus().equals(StatusEnums.DISABLED.getCode())) {
            throw new BizException(ProductConstants.CATEGORY_DISABLED);
        }
    }

    @Override
    public int getCategoryLevel(Long id) {
        if (id == null || ROOT_PARENT_ID.equals(id)) {
            return ProductConstants.DEFAULT_ZERO;
        }
        int level = 1;
        Long currentId = id;
        for (int i = 0; i < Byte.MAX_VALUE; i++) {
            ProductCategoryDTO category = bizProductCategoryMapper.selectById(currentId);
            if (category == null || ROOT_PARENT_ID.equals(category.getParentId())) {
                break;
            }
            level++;
            currentId = category.getParentId();
        }
        return level;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategorySortBatch(List<ProductCategoryDTO> items) {
        validateCategorySortBatch(items);
        List<Long> ids = items.stream().map(ProductCategoryDTO::getId).toList();
        List<ProductCategoryDTO> existingCategories = bizProductCategoryMapper.selectByIds(ids);
        if (existingCategories.size() != ids.size()) {
            Set<Long> existingIdSet = existingCategories.stream().map(ProductCategoryDTO::getId).collect(Collectors.toSet());
            Long missingId = ids.stream().filter(id -> !existingIdSet.contains(id)).findFirst().orElse(null);
            throw new BizException(ProductConstants.CATEGORY_NOT_EXISTS,
                    missingId == null ? ProductConstants.CATEGORY_NOT_EXISTS.msg() : "分类不存在: " + missingId);
        }
        bizProductCategoryMapper.updateSortBatch(items);
    }

    private void validateParentProductCategory(Long id) {
        if (id == null || ROOT_PARENT_ID.equals(id)) {
            return;
        }
        ProductCategoryDTO category = bizProductCategoryMapper.selectById(id);
        if (category == null) {
            throw new BizException(ProductConstants.CATEGORY_PARENT_NOT_EXISTS);
        }
        if (!ROOT_PARENT_ID.equals(category.getParentId())) {
            throw new BizException(ProductConstants.CATEGORY_PARENT_NOT_FIRST_LEVEL);
        }
    }

    private void validateProductCategoryExists(Long id) {
        if (bizProductCategoryMapper.selectById(id) == null) {
            throw new BizException(ProductConstants.CATEGORY_NOT_EXISTS);
        }
    }

    private void validateCategorySortBatch(List<ProductCategoryDTO> items) {
        if (items == null || items.isEmpty()) {
            throw new BizException(ProductConstants.CATEGORY_SORT_BATCH_EMPTY);
        }
        if (items.size() > ProductConstants.CATEGORY_SORT_BATCH_MAX_SIZE) {
            throw new BizException(ProductConstants.CATEGORY_SORT_BATCH_SIZE_EXCEED);
        }
        Set<Long> idSet = new HashSet<>();
        for (ProductCategoryDTO item : items) {
            if (item.getId() == null || item.getSort() == null || item.getSort() < ProductConstants.DEFAULT_ZERO) {
                throw new BizException(ProductConstants.CATEGORY_SORT_INVALID);
            }
            if (!idSet.add(item.getId())) {
                throw new BizException(ProductConstants.CATEGORY_SORT_ID_DUPLICATE);
            }
        }
    }
}
