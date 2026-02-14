package com.followba.store.admin.service.impl;

import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.admin.convert.ProductSkuConvert;
import com.followba.store.admin.convert.ProductSpuConvert;
import com.followba.store.admin.service.ProductBrandService;
import com.followba.store.admin.service.ProductCategoryService;
import com.followba.store.admin.service.ProductSkuService;
import com.followba.store.admin.service.ProductSpuService;
import com.followba.store.admin.vo.in.ProductSkuSaveIn;
import com.followba.store.admin.vo.in.ProductSpuPageIn;
import com.followba.store.admin.vo.in.ProductSpuSaveIn;
import com.followba.store.admin.vo.in.ProductSpuUpdateStatusIn;
import com.followba.store.admin.vo.out.ProductSpuCountRespVO;
import com.followba.store.admin.vo.out.ProductSpuRespVO;
import com.followba.store.admin.vo.out.ProductSpuSimpleRespVO;
import com.followba.store.common.exception.BizException;
import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.biz.BizProductSpuMapper;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductSkuDTO;
import com.followba.store.dao.dto.ProductSpuDTO;
import com.followba.store.dao.enums.ProductSpuStatusEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductSpuServiceImpl implements ProductSpuService {

    @Resource
    private BizProductSpuMapper bizProductSpuMapper;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private ProductBrandService productBrandService;

    @Resource
    private ProductCategoryService productCategoryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSpu(ProductSpuSaveIn reqVO) {
        validateSpuRefs(reqVO);
        List<ProductSkuSaveIn> skus = reqVO.getSkus();
        productSkuService.validateSkuList(skus, reqVO.getSpecType());

        ProductSpuDTO spu = ProductSpuConvert.INSTANCE.toDTO(reqVO);
        initSpuSkuSummary(spu, skus);
        spu.setStatus(ProductSpuStatusEnum.ENABLE.getCode());
        spu.setSalesCount(ProductConstants.DEFAULT_ZERO);
        spu.setBrowseCount(ProductConstants.DEFAULT_ZERO);
        bizProductSpuMapper.insert(spu);

        productSkuService.createSkuList(spu.getId(), skus);
        return spu.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSpu(ProductSpuSaveIn reqVO) {
        if (reqVO.getId() == null) {
            throw new BizException(ProductConstants.SPU_NOT_EXISTS);
        }
        ProductSpuDTO exists = validateSpuExists(reqVO.getId());
        validateSpuRefs(reqVO);
        List<ProductSkuSaveIn> skus = reqVO.getSkus();
        productSkuService.validateSkuList(skus, reqVO.getSpecType());

        ProductSpuDTO update = ProductSpuConvert.INSTANCE.toDTO(reqVO);
        initSpuSkuSummary(update, skus);
        update.setStatus(exists.getStatus());
        update.setSalesCount(exists.getSalesCount());
        update.setBrowseCount(exists.getBrowseCount());
        bizProductSpuMapper.updateById(update);

        productSkuService.updateSkuList(update.getId(), skus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSpuStatus(ProductSpuUpdateStatusIn reqVO) {
        validateSpuExists(reqVO.getId());
        int statusCode = reqVO.getStatus().intValue();
        if (!ProductSpuStatusEnum.contains(statusCode)) {
            throw new BizException(ProductConstants.SPU_STATUS_INVALID);
        }
        ProductSpuDTO update = new ProductSpuDTO();
        update.setId(reqVO.getId());
        update.setStatus(statusCode);
        bizProductSpuMapper.updateById(update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSpu(Long id) {
        ProductSpuDTO spu = validateSpuExists(id);
        if (spu.getStatus() == null || !spu.getStatus().equals(ProductSpuStatusEnum.RECYCLE.getCode())) {
            throw new BizException(ProductConstants.SPU_NOT_RECYCLE);
        }
        bizProductSpuMapper.deleteById(id);
        productSkuService.deleteSkuBySpuId(id);
    }

    @Override
    public ProductSpuRespVO getSpuDetail(Long id) {
        ProductSpuDTO spu = bizProductSpuMapper.selectById(id);
        if (spu == null) {
            return null;
        }
        ProductSpuRespVO resp = ProductSpuConvert.INSTANCE.toVO(spu);
        Map<Long, List<ProductSkuDTO>> skuMap = productSkuService.getSkuMapBySpuIds(Set.of(spu.getId()));
        resp.setSkus(ProductSkuConvert.INSTANCE.toVO(skuMap.getOrDefault(spu.getId(), List.of())));
        return resp;
    }

    @Override
    public List<ProductSpuRespVO> getSpuList(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        List<ProductSpuDTO> list = bizProductSpuMapper.selectBatchIds(ids);
        return buildSpuRespList(list);
    }

    @Override
    public List<ProductSpuSimpleRespVO> getSpuListByStatus(Integer status) {
        return ProductSpuConvert.INSTANCE.toSimpleVO(bizProductSpuMapper.selectListByStatus(status));
    }

    @Override
    public PageResp<ProductSpuRespVO> getSpuPage(ProductSpuPageIn reqVO) {
        PageDTO<ProductSpuDTO> page = bizProductSpuMapper.selectPage(reqVO.getPageNum(), reqVO.getPageSize(), reqVO.getName(),
                reqVO.getTabType(), reqVO.getCategoryId(), reqVO.getBrandId());
        return PageResp.of(page.getTotal(), buildSpuRespList(page.getList()));
    }

    @Override
    public ProductSpuCountRespVO getTabsCount() {
        ProductSpuCountRespVO resp = new ProductSpuCountRespVO();
        // Tab 0: 出售中 / For sale
        resp.setEnableCount(countByStatus(ProductSpuStatusEnum.ENABLE.getCode()));
        // Tab 1: 仓库中 / In warehouse
        resp.setDisableCount(countByStatus(ProductSpuStatusEnum.DISABLE.getCode()));
        // Tab 2: 已售罄 / Sold out
        resp.setSoldOutCount(bizProductSpuMapper.countByStock(ProductConstants.DEFAULT_ZERO));
        // Tab 3: 警戒库存 / Alert stock
        resp.setAlertStockCount(bizProductSpuMapper.countAlertStock());
        // Tab 4: 回收站 / Recycle bin
        resp.setRecycleCount(countByStatus(ProductSpuStatusEnum.RECYCLE.getCode()));
        return resp;
    }

    @Override
    public Long getSpuCountByCategoryId(Long categoryId) {
        return bizProductSpuMapper.countByCategoryId(categoryId);
    }

    private Long countByStatus(Integer status) {
        return bizProductSpuMapper.countByStatus(status);
    }

    private void validateSpuRefs(ProductSpuSaveIn reqVO) {
        productCategoryService.validateCategory(reqVO.getCategoryId());
        if (productCategoryService.getCategoryLevel(reqVO.getCategoryId()) < ProductConstants.CATEGORY_MIN_LEVEL) {
            throw new BizException(ProductConstants.CATEGORY_LEVEL_ERROR);
        }
        productBrandService.validateProductBrand(reqVO.getBrandId());
    }

    private ProductSpuDTO validateSpuExists(Long id) {
        ProductSpuDTO spu = bizProductSpuMapper.selectById(id);
        if (spu == null) {
            throw new BizException(ProductConstants.SPU_NOT_EXISTS);
        }
        return spu;
    }

    private void initSpuSkuSummary(ProductSpuDTO spu, List<ProductSkuSaveIn> skus) {
        int minPrice = Integer.MAX_VALUE;
        int minMarketPrice = Integer.MAX_VALUE;
        int minCostPrice = Integer.MAX_VALUE;
        int totalStock = ProductConstants.DEFAULT_ZERO;
        for (ProductSkuSaveIn sku : skus) {
            minPrice = Math.min(minPrice, sku.getPrice());
            minMarketPrice = Math.min(minMarketPrice, sku.getMarketPrice());
            minCostPrice = Math.min(minCostPrice, sku.getCostPrice());
            totalStock += sku.getStock();
        }
        spu.setPrice(minPrice == Integer.MAX_VALUE ? ProductConstants.DEFAULT_ZERO : minPrice);
        spu.setMarketPrice(minMarketPrice == Integer.MAX_VALUE ? ProductConstants.DEFAULT_ZERO : minMarketPrice);
        spu.setCostPrice(minCostPrice == Integer.MAX_VALUE ? ProductConstants.DEFAULT_ZERO : minCostPrice);
        spu.setStock(totalStock);
    }

    private ProductSpuRespVO toSpuResp(ProductSpuDTO spu) {
        return ProductSpuConvert.INSTANCE.toVO(spu);
    }

    private List<ProductSpuRespVO> buildSpuRespList(List<ProductSpuDTO> spuList) {
        if (spuList == null || spuList.isEmpty()) {
            return List.of();
        }
        Set<Long> spuIds = spuList.stream().map(ProductSpuDTO::getId).collect(Collectors.toSet());
        Map<Long, List<ProductSkuDTO>> skuMap = productSkuService.getSkuMapBySpuIds(spuIds);
        return spuList.stream().map(spu -> {
            ProductSpuRespVO resp = toSpuResp(spu);
            resp.setSkus(ProductSkuConvert.INSTANCE.toVO(skuMap.getOrDefault(spu.getId(), List.of())));
            return resp;
        }).toList();
    }
}
