package com.followba.store.product.service.impl;

import com.followba.store.common.context.RequestContext;
import com.followba.store.common.exception.BizException;
import com.followba.store.dao.biz.BizProductSkuMapper;
import com.followba.store.dao.biz.BizProductSpuMapper;
import com.followba.store.dao.biz.BizTradeCartMapper;
import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.dao.dto.ProductSkuDTO;
import com.followba.store.dao.dto.ProductSpuDTO;
import com.followba.store.dao.dto.TradeCartDTO;
import com.followba.store.dao.enums.ProductSpuStatusEnum;
import com.followba.store.dao.enums.TradeCartSelectedEnum;
import com.followba.store.product.dto.CartAddDTO;
import com.followba.store.product.dto.CartItemDTO;
import com.followba.store.product.dto.CartMergeItemDTO;
import com.followba.store.product.dto.CartUpdateQuantityDTO;
import com.followba.store.product.dto.CartUpdateSelectedDTO;
import com.followba.store.product.service.MallCartService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MallCartServiceImpl implements MallCartService {

    @Resource
    private BizTradeCartMapper bizTradeCartMapper;

    @Resource
    private BizProductSkuMapper bizProductSkuMapper;

    @Resource
    private BizProductSpuMapper bizProductSpuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(CartAddDTO dto) {
        validateQuantity(dto.getQuantity());
        Long userId = getCurrentUserId();
        ProductSkuDTO skuDTO = validateSkuForSale(dto.getSkuId());
        ProductSpuDTO spuDTO = validateSpuForSale(skuDTO.getSpuId());

        TradeCartDTO exists = bizTradeCartMapper.selectByUserIdAndSkuId(userId, dto.getSkuId());
        if (exists == null) {
            TradeCartDTO insertDTO = new TradeCartDTO();
            insertDTO.setUserId(userId);
            insertDTO.setSpuId(spuDTO.getId());
            insertDTO.setSkuId(skuDTO.getId());
            insertDTO.setQuantity(dto.getQuantity());
            insertDTO.setSelected(TradeCartSelectedEnum.SELECTED.getCode());
            insertDTO.setSkuPrice(skuDTO.getPrice());
            insertDTO.setSpuName(spuDTO.getName());
            insertDTO.setSkuPicUrl(skuDTO.getPicUrl());
            insertDTO.setSkuProperties(buildSkuPropertiesText(skuDTO));
            bizTradeCartMapper.insert(insertDTO);
            return insertDTO.getId();
        }

        int newQuantity = exists.getQuantity() + dto.getQuantity();
        validateStockEnough(skuDTO, newQuantity, ProductConstants.CART_SKU_STOCK_NOT_ENOUGH);
        TradeCartDTO updateDTO = new TradeCartDTO();
        updateDTO.setId(exists.getId());
        updateDTO.setQuantity(newQuantity);
        updateDTO.setSkuPrice(skuDTO.getPrice());
        updateDTO.setSpuName(spuDTO.getName());
        updateDTO.setSkuPicUrl(skuDTO.getPicUrl());
        updateDTO.setSkuProperties(buildSkuPropertiesText(skuDTO));
        updateDTO.setSelected(TradeCartSelectedEnum.SELECTED.getCode());
        bizTradeCartMapper.updateById(updateDTO);
        return exists.getId();
    }

    @Override
    public List<CartItemDTO> list() {
        Long userId = getCurrentUserId();
        List<TradeCartDTO> cartDTOList = bizTradeCartMapper.selectListByUserId(userId);
        return cartDTOList.stream().map(this::toCartItemDTO).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQuantity(CartUpdateQuantityDTO dto) {
        validateQuantity(dto.getQuantity());
        Long userId = getCurrentUserId();
        TradeCartDTO cartDTO = validateCartBelongsToUser(dto.getCartId(), userId);
        ProductSkuDTO skuDTO = validateSkuForSale(cartDTO.getSkuId());
        validateStockEnough(skuDTO, dto.getQuantity(), ProductConstants.CART_SKU_STOCK_NOT_ENOUGH);

        TradeCartDTO updateDTO = new TradeCartDTO();
        updateDTO.setId(cartDTO.getId());
        updateDTO.setQuantity(dto.getQuantity());
        updateDTO.setSkuPrice(skuDTO.getPrice());
        updateDTO.setSkuPicUrl(skuDTO.getPicUrl());
        updateDTO.setSkuProperties(buildSkuPropertiesText(skuDTO));
        bizTradeCartMapper.updateById(updateDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSelected(CartUpdateSelectedDTO dto) {
        Long userId = getCurrentUserId();
        TradeCartDTO cartDTO = validateCartBelongsToUser(dto.getCartId(), userId);
        TradeCartDTO updateDTO = new TradeCartDTO();
        updateDTO.setId(cartDTO.getId());
        updateDTO.setSelected(Boolean.TRUE.equals(dto.getSelected())
                ? TradeCartSelectedEnum.SELECTED.getCode()
                : TradeCartSelectedEnum.UNSELECTED.getCode());
        bizTradeCartMapper.updateById(updateDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long cartId) {
        Long userId = getCurrentUserId();
        TradeCartDTO cartDTO = validateCartBelongsToUser(cartId, userId);
        bizTradeCartMapper.deleteById(cartDTO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mergeGuestItems(Long userId, List<CartMergeItemDTO> itemDTOList) {
        Long targetUserId = userId == null ? getCurrentUserId() : userId;
        if (itemDTOList == null || itemDTOList.isEmpty()) {
            return;
        }

        Map<Long, Integer> mergedQuantityMap = buildMergedQuantityMap(itemDTOList);
        if (mergedQuantityMap.isEmpty()) {
            return;
        }

        Set<Long> skuIds = mergedQuantityMap.keySet();
        Map<Long, ProductSkuDTO> skuMap = bizProductSkuMapper.selectListByIds(skuIds).stream()
                .collect(Collectors.toMap(ProductSkuDTO::getId, Function.identity(), (a, b) -> a));
        if (skuMap.isEmpty()) {
            return;
        }

        Set<Long> spuIds = skuMap.values().stream().map(ProductSkuDTO::getSpuId).collect(Collectors.toSet());
        Map<Long, ProductSpuDTO> spuMap = bizProductSpuMapper.selectBatchIds(spuIds).stream()
                .collect(Collectors.toMap(ProductSpuDTO::getId, Function.identity(), (a, b) -> a));

        Map<Long, TradeCartDTO> existingCartMap = bizTradeCartMapper.selectListByUserIdAndSkuIds(targetUserId, skuIds).stream()
                .collect(Collectors.toMap(TradeCartDTO::getSkuId, Function.identity(), (a, b) -> a));

        List<TradeCartDTO> insertDTOList = new java.util.ArrayList<>();
        List<TradeCartDTO> updateDTOList = new java.util.ArrayList<>();
        for (Map.Entry<Long, Integer> entry : mergedQuantityMap.entrySet()) {
            Long skuId = entry.getKey();
            ProductSkuDTO skuDTO = skuMap.get(skuId);
            if (skuDTO == null || skuDTO.getStock() == null || skuDTO.getStock() <= ProductConstants.DEFAULT_ZERO) {
                continue;
            }
            ProductSpuDTO spuDTO = spuMap.get(skuDTO.getSpuId());
            if (spuDTO == null || !Objects.equals(spuDTO.getStatus(), ProductSpuStatusEnum.ENABLE.getCode())) {
                continue;
            }
            int quantity = Math.min(skuDTO.getStock(), entry.getValue());
            if (quantity < ProductConstants.CART_MIN_QUANTITY) {
                continue;
            }
            TradeCartDTO exists = existingCartMap.get(skuId);
            if (exists == null) {
                TradeCartDTO insertDTO = new TradeCartDTO();
                insertDTO.setUserId(targetUserId);
                insertDTO.setSpuId(spuDTO.getId());
                insertDTO.setSkuId(skuDTO.getId());
                insertDTO.setQuantity(quantity);
                insertDTO.setSelected(TradeCartSelectedEnum.SELECTED.getCode());
                insertDTO.setSkuPrice(skuDTO.getPrice());
                insertDTO.setSpuName(spuDTO.getName());
                insertDTO.setSkuPicUrl(skuDTO.getPicUrl());
                insertDTO.setSkuProperties(buildSkuPropertiesText(skuDTO));
                updateSnapshotFields(insertDTO, spuDTO, skuDTO);
                insertDTOList.add(insertDTO);
                continue;
            }
            int mergedQuantity = Math.min(skuDTO.getStock(), exists.getQuantity() + quantity);
            TradeCartDTO updateDTO = new TradeCartDTO();
            updateDTO.setId(exists.getId());
            updateDTO.setQuantity(mergedQuantity);
            updateDTO.setSelected(TradeCartSelectedEnum.SELECTED.getCode());
            updateSnapshotFields(updateDTO, spuDTO, skuDTO);
            updateDTOList.add(updateDTO);
        }
        bizTradeCartMapper.insertBatch(insertDTOList);
        bizTradeCartMapper.updateBatch(updateDTOList);
    }

    private CartItemDTO toCartItemDTO(TradeCartDTO dto) {
        CartItemDTO out = new CartItemDTO();
        out.setCartId(dto.getId());
        out.setSpuId(dto.getSpuId());
        out.setSkuId(dto.getSkuId());
        out.setSpuName(dto.getSpuName());
        out.setSkuPicUrl(dto.getSkuPicUrl());
        out.setSkuProperties(dto.getSkuProperties());
        out.setQuantity(dto.getQuantity());
        out.setSelected(dto.getSelected());
        out.setSkuPrice(dto.getSkuPrice());
        out.setLineAmount(dto.getSkuPrice() * dto.getQuantity());
        return out;
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity < ProductConstants.CART_MIN_QUANTITY) {
            throw new BizException(ProductConstants.CART_QUANTITY_INVALID);
        }
    }

    private ProductSkuDTO validateSkuForSale(Long skuId) {
        ProductSkuDTO skuDTO = bizProductSkuMapper.selectById(skuId);
        if (skuDTO == null) {
            throw new BizException(ProductConstants.CART_SKU_NOT_EXISTS);
        }
        if (skuDTO.getStock() == null || skuDTO.getStock() <= ProductConstants.DEFAULT_ZERO) {
            throw new BizException(ProductConstants.CART_SKU_NOT_SALE);
        }
        return skuDTO;
    }

    private ProductSpuDTO validateSpuForSale(Long spuId) {
        ProductSpuDTO spuDTO = bizProductSpuMapper.selectById(spuId);
        if (spuDTO == null) {
            throw new BizException(ProductConstants.SPU_NOT_EXISTS);
        }
        if (!Objects.equals(spuDTO.getStatus(), ProductSpuStatusEnum.ENABLE.getCode())) {
            throw new BizException(ProductConstants.CART_SKU_NOT_SALE);
        }
        return spuDTO;
    }

    private void validateStockEnough(ProductSkuDTO skuDTO, int quantity, ProductConstants errorCode) {
        if (skuDTO.getStock() == null || skuDTO.getStock() < quantity) {
            throw new BizException(errorCode);
        }
    }

    private TradeCartDTO validateCartBelongsToUser(Long cartId, Long userId) {
        TradeCartDTO cartDTO = bizTradeCartMapper.selectById(cartId);
        if (cartDTO == null) {
            throw new BizException(ProductConstants.CART_ITEM_NOT_EXISTS);
        }
        if (!Objects.equals(cartDTO.getUserId(), userId)) {
            throw new BizException(ProductConstants.CART_ITEM_NOT_BELONG_USER);
        }
        return cartDTO;
    }

    private String buildSkuPropertiesText(ProductSkuDTO skuDTO) {
        if (skuDTO.getProperties() == null || skuDTO.getProperties().isEmpty()) {
            return "";
        }
        return skuDTO.getProperties().stream()
                .map(ProductSkuDTO.Property::getValueName)
                .filter(Objects::nonNull)
                .reduce((a, b) -> a + "/" + b)
                .orElse("");
    }

    private Map<Long, Integer> buildMergedQuantityMap(List<CartMergeItemDTO> itemDTOList) {
        Map<Long, Integer> mergedQuantityMap = new java.util.HashMap<>();
        for (CartMergeItemDTO itemDTO : itemDTOList) {
            if (itemDTO == null || itemDTO.getSkuId() == null || itemDTO.getQuantity() == null) {
                continue;
            }
            if (itemDTO.getQuantity() < ProductConstants.CART_MIN_QUANTITY) {
                throw new BizException(ProductConstants.CART_QUANTITY_INVALID);
            }
            mergedQuantityMap.merge(itemDTO.getSkuId(), itemDTO.getQuantity(), Integer::sum);
        }
        return mergedQuantityMap;
    }

    private void updateSnapshotFields(TradeCartDTO target, ProductSpuDTO spuDTO, ProductSkuDTO skuDTO) {
        target.setSkuPrice(skuDTO.getPrice());
        target.setSpuName(spuDTO.getName());
        target.setSkuPicUrl(skuDTO.getPicUrl());
        target.setSkuProperties(buildSkuPropertiesText(skuDTO));
    }

    private Long getCurrentUserId() {
        String userId = RequestContext.getUserId();
        if (userId == null || userId.isBlank() || "visitor".equalsIgnoreCase(userId)) {
            throw new BizException(ProductConstants.CART_USER_NOT_LOGIN);
        }
        try {
            return Long.valueOf(userId);
        } catch (NumberFormatException exception) {
            throw new BizException(ProductConstants.CART_USER_NOT_LOGIN);
        }
    }
}
