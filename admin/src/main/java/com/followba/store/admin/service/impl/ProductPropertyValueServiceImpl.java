package com.followba.store.admin.service.impl;

import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.admin.convert.ProductPropertyValueConvert;
import com.followba.store.admin.service.ProductSkuService;
import com.followba.store.admin.service.ProductPropertyValueService;
import com.followba.store.admin.vo.in.ProductPropertyValuePageIn;
import com.followba.store.admin.vo.in.ProductPropertyValueSaveIn;
import com.followba.store.admin.vo.out.ProductPropertyValueRespVO;
import com.followba.store.common.exception.BizException;
import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.biz.BizProductPropertyMapper;
import com.followba.store.dao.biz.BizProductPropertyValueMapper;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductPropertyValueDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductPropertyValueServiceImpl implements ProductPropertyValueService {

    @Resource
    private BizProductPropertyValueMapper bizProductPropertyValueMapper;

    @Resource
    private BizProductPropertyMapper bizProductPropertyMapper;

    @Resource
    private ProductSkuService productSkuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPropertyValue(ProductPropertyValueSaveIn reqVO) {
        validatePropertyExists(reqVO.getPropertyId());
        validatePropertyValueNameUnique(null, reqVO.getPropertyId(), reqVO.getName());
        ProductPropertyValueDTO dto = ProductPropertyValueConvert.INSTANCE.toDTO(reqVO);
        bizProductPropertyValueMapper.insert(dto);
        return dto.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePropertyValue(ProductPropertyValueSaveIn reqVO) {
        if (reqVO.getId() == null) {
            throw new BizException(ProductConstants.PROPERTY_VALUE_NOT_EXISTS);
        }
        ProductPropertyValueDTO exists = validatePropertyValueExists(reqVO.getId());
        validatePropertyExists(reqVO.getPropertyId());
        validatePropertyValueNameUnique(reqVO.getId(), reqVO.getPropertyId(), reqVO.getName());
        ProductPropertyValueDTO dto = ProductPropertyValueConvert.INSTANCE.toDTO(reqVO);
        bizProductPropertyValueMapper.updateById(dto);
        if (!exists.getName().equals(reqVO.getName())) {
            productSkuService.updateSkuPropertyValue(reqVO.getId(), reqVO.getName());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePropertyValue(Long id) {
        validatePropertyValueExists(id);
        bizProductPropertyValueMapper.deleteById(id);
    }

    @Override
    public ProductPropertyValueRespVO getPropertyValue(Long id) {
        return ProductPropertyValueConvert.INSTANCE.toVO(bizProductPropertyValueMapper.selectById(id));
    }

    @Override
    public PageResp<ProductPropertyValueRespVO> getPropertyValuePage(ProductPropertyValuePageIn reqVO) {
        PageDTO<ProductPropertyValueDTO> page = bizProductPropertyValueMapper.selectPage(reqVO.getPageNum(), reqVO.getPageSize(), reqVO.getPropertyId(), reqVO.getName(), reqVO.getStatus());
        return ProductPropertyValueConvert.INSTANCE.toVO(page);
    }

    @Override
    public List<ProductPropertyValueRespVO> getPropertyValueSimpleList(Long propertyId) {
        return ProductPropertyValueConvert.INSTANCE.toVO(bizProductPropertyValueMapper.selectSimpleList(propertyId));
    }

    private void validatePropertyExists(Long propertyId) {
        if (bizProductPropertyMapper.selectById(propertyId) == null) {
            throw new BizException(ProductConstants.PROPERTY_NOT_EXISTS);
        }
    }

    private ProductPropertyValueDTO validatePropertyValueExists(Long id) {
        ProductPropertyValueDTO value = bizProductPropertyValueMapper.selectById(id);
        if (value == null) {
            throw new BizException(ProductConstants.PROPERTY_VALUE_NOT_EXISTS);
        }
        return value;
    }

    private void validatePropertyValueNameUnique(Long id, Long propertyId, String name) {
        ProductPropertyValueDTO value = bizProductPropertyValueMapper.selectByPropertyIdAndName(propertyId, name);
        if (value == null) {
            return;
        }
        if (id == null || !id.equals(value.getId())) {
            throw new BizException(ProductConstants.PROPERTY_VALUE_NAME_EXISTS);
        }
    }
}
