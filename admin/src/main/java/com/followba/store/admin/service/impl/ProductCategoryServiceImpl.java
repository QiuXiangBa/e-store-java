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
        dto.setIsLeaf(Boolean.TRUE);
        bizProductCategoryMapper.insert(dto);
        String path = buildCategoryPath(dto.getParentId(), dto.getId());
        bizProductCategoryMapper.updatePathById(dto.getId(), path);
        dto.setPath(path);
        refreshParentLeafFlag(dto.getParentId());
        return dto.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(ProductCategorySaveIn reqVO) {
        if (reqVO.getId() == null) {
            throw new BizException(ProductConstants.CATEGORY_NOT_EXISTS);
        }
        ProductCategoryDTO existing = bizProductCategoryMapper.selectById(reqVO.getId());
        if (existing == null) {
            throw new BizException(ProductConstants.CATEGORY_NOT_EXISTS);
        }
        validateParentProductCategory(reqVO.getParentId());
        // 当前类目有子类目时，不允许改父级，避免产生三层结构与路径脏数据
        // Forbid parent change when this node has children to avoid 3-level hierarchy and path dirty data.
        if (!existing.getParentId().equals(reqVO.getParentId())
                && bizProductCategoryMapper.countByParentId(reqVO.getId()) > ProductConstants.DEFAULT_ZERO) {
            throw new BizException(ProductConstants.CATEGORY_EXISTS_CHILDREN);
        }
        ProductCategoryDTO dto = ProductCategoryConvert.INSTANCE.toDTO(reqVO);
        dto.setIsLeaf(existing.getIsLeaf());
        dto.setPath(existing.getPath());
        bizProductCategoryMapper.updateById(dto);
        if (!existing.getParentId().equals(reqVO.getParentId())) {
            String path = buildCategoryPath(reqVO.getParentId(), reqVO.getId());
            bizProductCategoryMapper.updatePathById(reqVO.getId(), path);
            refreshParentLeafFlag(existing.getParentId());
            refreshParentLeafFlag(reqVO.getParentId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Long id) {
        ProductCategoryDTO existing = bizProductCategoryMapper.selectById(id);
        if (existing == null) {
            throw new BizException(ProductConstants.CATEGORY_NOT_EXISTS);
        }
        if (bizProductCategoryMapper.countByParentId(id) > 0) {
            throw new BizException(ProductConstants.CATEGORY_EXISTS_CHILDREN);
        }
        if (bizProductSpuMapper.countByCategoryId(id) > 0) {
            throw new BizException(ProductConstants.CATEGORY_HAVE_BIND_SPU);
        }
        bizProductCategoryMapper.deleteById(id);
        refreshParentLeafFlag(existing.getParentId());
    }

    @Override
    public ProductCategoryRespVO getCategory(Long id) {
        return ProductCategoryConvert.INSTANCE.toVO(bizProductCategoryMapper.selectById(id));
    }

    @Override
    public List<ProductCategoryRespVO> getCategoryList(ProductCategoryListIn reqVO) {
        List<ProductCategoryDTO> categoryList = bizProductCategoryMapper.selectList(reqVO.getName(), reqVO.getStatus(), reqVO.getParentId());
        return ProductCategoryConvert.INSTANCE.toVO(categoryList);
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
    public void validatePublishCategory(Long id) {
        ProductCategoryDTO category = bizProductCategoryMapper.selectById(id);
        if (category == null) {
            throw new BizException(ProductConstants.CATEGORY_NOT_EXISTS);
        }
        if (category.getStatus() != null && category.getStatus().equals(StatusEnums.DISABLED.getCode())) {
            throw new BizException(ProductConstants.CATEGORY_DISABLED);
        }
        if (!Boolean.TRUE.equals(category.getIsLeaf())) {
            throw new BizException(ProductConstants.CATEGORY_NOT_LEAF);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategorySortBatch(List<ProductCategoryDTO> items) {
        validateCategorySortBatch(items);
        List<Long> ids = items.stream().map(ProductCategoryDTO::getId).toList();
        // 先全量校验分类是否存在，避免出现部分成功、部分失败。 / Pre-check all category IDs to avoid partial updates.
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
        int parentLevel = resolveCategoryLevel(category);
        if (parentLevel >= ProductConstants.CATEGORY_MAX_LEVEL) {
            throw new BizException(ProductConstants.CATEGORY_PARENT_LEVEL_EXCEEDED);
        }
    }

    /**
     * 解析分类层级（根级=1）/ Resolve category level (root level = 1).
     */
    private int resolveCategoryLevel(ProductCategoryDTO category) {
        if (category == null) {
            return ProductConstants.CATEGORY_ROOT_LEVEL;
        }
        String path = category.getPath();
        if (path == null || path.isBlank()) {
            return ROOT_PARENT_ID.equals(category.getParentId()) ? ProductConstants.DEFAULT_ONE : ProductConstants.DEFAULT_ZERO;
        }
        return (int) path.chars().filter(ch -> ch == '/').count() - ProductConstants.DEFAULT_ONE;
    }

    private void validateCategorySortBatch(List<ProductCategoryDTO> items) {
        if (items == null || items.isEmpty()) {
            throw new BizException(ProductConstants.CATEGORY_SORT_BATCH_EMPTY);
        }
        if (items.size() > ProductConstants.CATEGORY_SORT_BATCH_MAX_SIZE) {
            throw new BizException(ProductConstants.CATEGORY_SORT_BATCH_SIZE_EXCEED);
        }
        // 使用 Set 在单次遍历中做 O(1) 重复 ID 检测。 / Use a Set for O(1) duplicate-ID detection in one pass.
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

    private String buildCategoryPath(Long parentId, Long id) {
        if (id == null) {
            return null;
        }
        if (parentId == null || ROOT_PARENT_ID.equals(parentId)) {
            return "/" + id + "/";
        }
        ProductCategoryDTO parent = bizProductCategoryMapper.selectById(parentId);
        if (parent == null || parent.getPath() == null || parent.getPath().isBlank()) {
            return "/" + parentId + "/" + id + "/";
        }
        return parent.getPath() + id + "/";
    }

    private void refreshParentLeafFlag(Long parentId) {
        if (parentId == null || ROOT_PARENT_ID.equals(parentId)) {
            return;
        }
        boolean hasChildren = bizProductCategoryMapper.countByParentId(parentId) > ProductConstants.DEFAULT_ZERO;
        bizProductCategoryMapper.updateLeafById(parentId, !hasChildren);
    }
}
