package com.followba.store.admin.service.impl;

import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.admin.convert.ProductBrandConvert;
import com.followba.store.admin.service.ProductBrandService;
import com.followba.store.admin.vo.in.ProductBrandCreateIn;
import com.followba.store.admin.vo.in.ProductBrandListIn;
import com.followba.store.admin.vo.in.ProductBrandPageIn;
import com.followba.store.admin.vo.in.ProductBrandUpdateIn;
import com.followba.store.admin.vo.out.ProductBrandRespVO;
import com.followba.store.admin.vo.out.ProductBrandSimpleRespVO;
import com.followba.store.common.exception.BizException;
import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.biz.BizProductBrandMapper;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductBrandDTO;
import com.followba.store.dao.enums.StatusEnums;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductBrandServiceImpl implements ProductBrandService {

    @Resource
    private BizProductBrandMapper bizProductBrandMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createBrand(ProductBrandCreateIn reqVO) {
        validateBrandNameUnique(null, reqVO.getName());
        ProductBrandDTO brand = ProductBrandConvert.INSTANCE.toDTO(reqVO);
        bizProductBrandMapper.insert(brand);
        return brand.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBrand(ProductBrandUpdateIn reqVO) {
        validateBrandExists(reqVO.getId());
        validateBrandNameUnique(reqVO.getId(), reqVO.getName());
        ProductBrandDTO update = ProductBrandConvert.INSTANCE.toDTO(reqVO);
        bizProductBrandMapper.updateById(update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBrand(Long id) {
        validateBrandExists(id);
        bizProductBrandMapper.deleteById(id);
    }

    @Override
    public ProductBrandRespVO getBrand(Long id) {
        return ProductBrandConvert.INSTANCE.toVO(bizProductBrandMapper.selectById(id));
    }

    @Override
    public List<ProductBrandRespVO> getBrandList(ProductBrandListIn reqVO) {
        return ProductBrandConvert.INSTANCE.toVO(bizProductBrandMapper.selectList(reqVO.getName(), reqVO.getStatus()));
    }

    @Override
    public List<ProductBrandSimpleRespVO> getBrandListByStatus(Integer status) {
        return ProductBrandConvert.INSTANCE.toSimpleVO(bizProductBrandMapper.selectListByStatus(status));
    }

    @Override
    public PageResp<ProductBrandRespVO> getBrandPage(ProductBrandPageIn reqVO) {
        PageDTO<ProductBrandDTO> result = bizProductBrandMapper.selectPage(reqVO.getPageNum(), reqVO.getPageSize(), reqVO.getName(), reqVO.getStatus());
        return ProductBrandConvert.INSTANCE.toVO(result);
    }

    @Override
    public void validateProductBrand(Long id) {
        ProductBrandDTO dto = bizProductBrandMapper.selectById(id);
        if (dto == null) {
            throw new BizException(ProductConstants.BRAND_NOT_EXISTS);
        }
        if (StatusEnums.DISABLED.getCode().equals(dto.getStatus())) {
            throw new BizException(ProductConstants.BRAND_DISABLED);
        }
    }

    private void validateBrandExists(Long id) {
        if (bizProductBrandMapper.selectById(id) == null) {
            throw new BizException(ProductConstants.BRAND_NOT_EXISTS);
        }
    }

    private void validateBrandNameUnique(Long id, String name) {
        ProductBrandDTO brand = bizProductBrandMapper.selectByName(name);
        if (brand == null) {
            return;
        }
        if (id == null || !id.equals(brand.getId())) {
            throw new BizException(ProductConstants.BRAND_NAME_EXISTS);
        }
    }
}
