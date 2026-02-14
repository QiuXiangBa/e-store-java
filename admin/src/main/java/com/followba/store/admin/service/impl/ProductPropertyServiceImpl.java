package com.followba.store.admin.service.impl;

import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.admin.convert.ProductPropertyConvert;
import com.followba.store.admin.service.ProductSkuService;
import com.followba.store.admin.service.ProductPropertyService;
import com.followba.store.admin.vo.in.ProductPropertyPageIn;
import com.followba.store.admin.vo.in.ProductPropertySaveIn;
import com.followba.store.admin.vo.out.ProductPropertyRespVO;
import com.followba.store.common.exception.BizException;
import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.biz.BizProductPropertyMapper;
import com.followba.store.dao.biz.BizProductPropertyValueMapper;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductPropertyDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductPropertyServiceImpl implements ProductPropertyService {

    @Resource
    private BizProductPropertyMapper bizProductPropertyMapper;

    @Resource
    private BizProductPropertyValueMapper bizProductPropertyValueMapper;

    @Resource
    private ProductSkuService productSkuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createProperty(ProductPropertySaveIn reqVO) {
        validatePropertyNameUnique(null, reqVO.getName());
        ProductPropertyDTO dto = ProductPropertyConvert.INSTANCE.toDTO(reqVO);
        bizProductPropertyMapper.insert(dto);
        return dto.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProperty(ProductPropertySaveIn reqVO) {
        if (reqVO.getId() == null) {
            throw new BizException(ProductConstants.PROPERTY_NOT_EXISTS);
        }
        validatePropertyExists(reqVO.getId());
        validatePropertyNameUnique(reqVO.getId(), reqVO.getName());
        ProductPropertyDTO exists = bizProductPropertyMapper.selectById(reqVO.getId());
        ProductPropertyDTO dto = ProductPropertyConvert.INSTANCE.toDTO(reqVO);
        bizProductPropertyMapper.updateById(dto);
        if (!exists.getName().equals(reqVO.getName())) {
            productSkuService.updateSkuProperty(reqVO.getId(), reqVO.getName());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProperty(Long id) {
        validatePropertyExists(id);
        if (bizProductPropertyValueMapper.countByPropertyId(id) > 0) {
            throw new BizException(ProductConstants.PROPERTY_HAS_VALUE);
        }
        bizProductPropertyMapper.deleteById(id);
    }

    @Override
    public ProductPropertyRespVO getProperty(Long id) {
        return ProductPropertyConvert.INSTANCE.toVO(bizProductPropertyMapper.selectById(id));
    }

    @Override
    public PageResp<ProductPropertyRespVO> getPropertyPage(ProductPropertyPageIn reqVO) {
        PageDTO<ProductPropertyDTO> page = bizProductPropertyMapper.selectPage(reqVO.getPageNum(), reqVO.getPageSize(), reqVO.getName(), reqVO.getStatus());
        return ProductPropertyConvert.INSTANCE.toVO(page);
    }

    @Override
    public List<ProductPropertyRespVO> getPropertySimpleList() {
        return ProductPropertyConvert.INSTANCE.toVO(bizProductPropertyMapper.selectSimpleList());
    }

    private void validatePropertyExists(Long id) {
        if (bizProductPropertyMapper.selectById(id) == null) {
            throw new BizException(ProductConstants.PROPERTY_NOT_EXISTS);
        }
    }

    private void validatePropertyNameUnique(Long id, String name) {
        ProductPropertyDTO property = bizProductPropertyMapper.selectByName(name);
        if (property == null) {
            return;
        }
        if (id == null || !id.equals(property.getId())) {
            throw new BizException(ProductConstants.PROPERTY_NAME_EXISTS);
        }
    }
}
