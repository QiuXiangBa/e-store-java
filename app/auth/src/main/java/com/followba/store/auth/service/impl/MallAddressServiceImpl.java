package com.followba.store.auth.service.impl;

import com.followba.store.auth.dto.TradeUserAddressCreateDTO;
import com.followba.store.auth.dto.TradeUserAddressDTO;
import com.followba.store.auth.dto.TradeUserAddressUpdateDTO;
import com.followba.store.auth.service.MallAddressService;
import com.followba.store.common.context.RequestContext;
import com.followba.store.common.exception.BizException;
import com.followba.store.dao.biz.BizTradeUserAddressMapper;
import com.followba.store.dao.constant.AuthConstants;
import com.followba.store.dao.constant.TradeAddressConstants;
import com.followba.store.dao.enums.TradeUserAddressDefaultEnum;
import com.followba.store.dao.enums.TradeUserAddressStatusEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class MallAddressServiceImpl implements MallAddressService {

    @Resource
    private BizTradeUserAddressMapper bizTradeUserAddressMapper;

    @Override
    public List<TradeUserAddressDTO> list() {
        Long userId = getCurrentUserId();
        List<com.followba.store.dao.dto.TradeUserAddressDTO> addressDTOList = bizTradeUserAddressMapper.selectListByUserId(userId);
        return addressDTOList.stream().map(this::toAddressDTO).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(TradeUserAddressCreateDTO dto) {
        Long userId = getCurrentUserId();
        long addressCount = bizTradeUserAddressMapper.countByUserId(userId);
        if (addressCount >= TradeAddressConstants.ADDRESS_MAX_COUNT) {
            throw new BizException(TradeAddressConstants.ADDRESS_COUNT_EXCEEDED);
        }
        boolean firstAddress = addressCount == 0;
        Integer defaultCode = resolveDefaultCode(dto.getIsDefault(), firstAddress);

        if (Objects.equals(defaultCode, TradeUserAddressDefaultEnum.DEFAULT.getCode())) {
            bizTradeUserAddressMapper.clearDefaultByUserId(userId);
        }

        com.followba.store.dao.dto.TradeUserAddressDTO insertDTO = new com.followba.store.dao.dto.TradeUserAddressDTO();
        insertDTO.setUserId(userId);
        fillAddressFields(insertDTO, dto);
        insertDTO.setIsDefault(defaultCode);
        insertDTO.setStatus(TradeUserAddressStatusEnum.ENABLE.getCode());
        insertDTO.setDeleted(AuthConstants.DEFAULT_NOT_DELETED);
        insertDTO.setTenantId((long) AuthConstants.DEFAULT_TENANT_ID);
        bizTradeUserAddressMapper.insert(insertDTO);
        return insertDTO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TradeUserAddressUpdateDTO dto) {
        Long userId = getCurrentUserId();
        com.followba.store.dao.dto.TradeUserAddressDTO exists = validateAddressBelongsToUser(dto.getId(), userId);
        Integer defaultCode = resolveDefaultCode(dto.getIsDefault(), false);
        if (Objects.equals(defaultCode, TradeUserAddressDefaultEnum.DEFAULT.getCode())) {
            bizTradeUserAddressMapper.clearDefaultByUserId(userId);
        }

        com.followba.store.dao.dto.TradeUserAddressDTO updateDTO = new com.followba.store.dao.dto.TradeUserAddressDTO();
        updateDTO.setId(exists.getId());
        fillAddressFields(updateDTO, dto);
        updateDTO.setIsDefault(defaultCode);
        updateDTO.setStatus(exists.getStatus());
        bizTradeUserAddressMapper.updateById(updateDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Long userId = getCurrentUserId();
        com.followba.store.dao.dto.TradeUserAddressDTO exists = validateAddressBelongsToUser(id, userId);
        bizTradeUserAddressMapper.deleteById(exists.getId());
        if (Objects.equals(exists.getIsDefault(), TradeUserAddressDefaultEnum.DEFAULT.getCode())) {
            List<com.followba.store.dao.dto.TradeUserAddressDTO> remainList = bizTradeUserAddressMapper.selectListByUserId(userId);
            if (!remainList.isEmpty()) {
                bizTradeUserAddressMapper.clearDefaultByUserId(userId);
                bizTradeUserAddressMapper.setDefaultById(remainList.get(0).getId());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDefault(Long id) {
        Long userId = getCurrentUserId();
        com.followba.store.dao.dto.TradeUserAddressDTO exists = validateAddressBelongsToUser(id, userId);
        bizTradeUserAddressMapper.clearDefaultByUserId(userId);
        bizTradeUserAddressMapper.setDefaultById(exists.getId());
    }

    private void fillAddressFields(com.followba.store.dao.dto.TradeUserAddressDTO target,
                                   TradeUserAddressCreateDTO source) {
        target.setReceiverName(source.getReceiverName());
        target.setReceiverMobile(source.getReceiverMobile());
        target.setProvinceCode(source.getProvinceCode());
        target.setProvinceName(source.getProvinceName());
        target.setCityCode(source.getCityCode());
        target.setCityName(source.getCityName());
        target.setDistrictCode(emptyToBlank(source.getDistrictCode()));
        target.setDistrictName(emptyToBlank(source.getDistrictName()));
        target.setDetailAddress(source.getDetailAddress());
        target.setPostalCode(emptyToBlank(source.getPostalCode()));
    }

    private void fillAddressFields(com.followba.store.dao.dto.TradeUserAddressDTO target,
                                   TradeUserAddressUpdateDTO source) {
        target.setReceiverName(source.getReceiverName());
        target.setReceiverMobile(source.getReceiverMobile());
        target.setProvinceCode(source.getProvinceCode());
        target.setProvinceName(source.getProvinceName());
        target.setCityCode(source.getCityCode());
        target.setCityName(source.getCityName());
        target.setDistrictCode(emptyToBlank(source.getDistrictCode()));
        target.setDistrictName(emptyToBlank(source.getDistrictName()));
        target.setDetailAddress(source.getDetailAddress());
        target.setPostalCode(emptyToBlank(source.getPostalCode()));
    }

    private Integer resolveDefaultCode(Boolean isDefault, boolean firstAddress) {
        if (firstAddress || Boolean.TRUE.equals(isDefault)) {
            return TradeUserAddressDefaultEnum.DEFAULT.getCode();
        }
        return TradeUserAddressDefaultEnum.NOT_DEFAULT.getCode();
    }

    private com.followba.store.dao.dto.TradeUserAddressDTO validateAddressBelongsToUser(Long id, Long userId) {
        com.followba.store.dao.dto.TradeUserAddressDTO addressDTO = bizTradeUserAddressMapper.selectById(id);
        if (addressDTO == null) {
            throw new BizException(TradeAddressConstants.ADDRESS_NOT_EXISTS);
        }
        if (!Objects.equals(addressDTO.getUserId(), userId)) {
            throw new BizException(TradeAddressConstants.ADDRESS_NOT_BELONG_USER);
        }
        return addressDTO;
    }

    private TradeUserAddressDTO toAddressDTO(com.followba.store.dao.dto.TradeUserAddressDTO source) {
        TradeUserAddressDTO dto = new TradeUserAddressDTO();
        dto.setId(source.getId());
        dto.setReceiverName(source.getReceiverName());
        dto.setReceiverMobile(source.getReceiverMobile());
        dto.setProvinceCode(source.getProvinceCode());
        dto.setProvinceName(source.getProvinceName());
        dto.setCityCode(source.getCityCode());
        dto.setCityName(source.getCityName());
        dto.setDistrictCode(source.getDistrictCode());
        dto.setDistrictName(source.getDistrictName());
        dto.setDetailAddress(source.getDetailAddress());
        dto.setPostalCode(source.getPostalCode());
        dto.setIsDefault(source.getIsDefault());
        dto.setStatus(source.getStatus());
        return dto;
    }

    private Long getCurrentUserId() {
        String userId = RequestContext.getUserId();
        if (userId == null || userId.isBlank() || "visitor".equalsIgnoreCase(userId)) {
            throw new BizException(AuthConstants.AUTH_NOT_LOGIN);
        }
        try {
            return Long.valueOf(userId);
        } catch (NumberFormatException exception) {
            throw new BizException(AuthConstants.AUTH_NOT_LOGIN);
        }
    }

    private String emptyToBlank(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }
}
