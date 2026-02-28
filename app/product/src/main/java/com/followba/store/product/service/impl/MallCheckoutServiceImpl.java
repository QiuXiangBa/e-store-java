package com.followba.store.product.service.impl;

import com.followba.store.common.context.RequestContext;
import com.followba.store.common.exception.BizException;
import com.followba.store.dao.biz.BizProductSkuMapper;
import com.followba.store.dao.biz.BizProductSpuMapper;
import com.followba.store.dao.biz.BizTradeCartMapper;
import com.followba.store.dao.biz.BizTradeCheckoutItemMapper;
import com.followba.store.dao.biz.BizTradeCheckoutOrderMapper;
import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.dao.dto.ProductSkuDTO;
import com.followba.store.dao.dto.ProductSpuDTO;
import com.followba.store.dao.dto.TradeCartDTO;
import com.followba.store.dao.dto.TradeCheckoutItemDTO;
import com.followba.store.dao.dto.TradeCheckoutOrderDTO;
import com.followba.store.dao.enums.ProductSpuStatusEnum;
import com.followba.store.dao.enums.TradeCartSelectedEnum;
import com.followba.store.dao.enums.TradeCheckoutOrderStatusEnum;
import com.followba.store.product.dto.CheckoutCreateDTO;
import com.followba.store.product.dto.CheckoutItemDTO;
import com.followba.store.product.dto.CheckoutResultDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class MallCheckoutServiceImpl implements com.followba.store.product.service.MallCheckoutService {

    @Resource
    private BizTradeCartMapper bizTradeCartMapper;

    @Resource
    private BizProductSkuMapper bizProductSkuMapper;

    @Resource
    private BizProductSpuMapper bizProductSpuMapper;

    @Resource
    private BizTradeCheckoutOrderMapper bizTradeCheckoutOrderMapper;

    @Resource
    private BizTradeCheckoutItemMapper bizTradeCheckoutItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CheckoutResultDTO create(CheckoutCreateDTO dto) {
        Long userId = getCurrentUserId();
        List<TradeCartDTO> selectedCartList = bizTradeCartMapper
                .selectListByUserIdAndSelected(userId, TradeCartSelectedEnum.SELECTED.getCode());
        if (selectedCartList.isEmpty()) {
            throw new BizException(ProductConstants.CHECKOUT_EMPTY_SELECTION);
        }

        Set<Long> skuIds = selectedCartList.stream().map(TradeCartDTO::getSkuId).collect(Collectors.toSet());
        Set<Long> spuIds = selectedCartList.stream().map(TradeCartDTO::getSpuId).collect(Collectors.toSet());

        Map<Long, ProductSkuDTO> skuMap = bizProductSkuMapper.selectListByIds(skuIds).stream()
                .collect(Collectors.toMap(ProductSkuDTO::getId, value -> value));
        Map<Long, ProductSpuDTO> spuMap = bizProductSpuMapper.selectBatchIds(spuIds).stream()
                .collect(Collectors.toMap(ProductSpuDTO::getId, value -> value));

        int totalAmount = ProductConstants.DEFAULT_ZERO;
        List<TradeCheckoutItemDTO> itemDTOList = selectedCartList.stream()
                .map(cart -> buildCheckoutItem(cart, skuMap, spuMap, userId))
                .toList();

        for (TradeCheckoutItemDTO itemDTO : itemDTOList) {
            totalAmount += itemDTO.getLineAmount();
        }

        TradeCheckoutOrderDTO orderDTO = new TradeCheckoutOrderDTO();
        orderDTO.setOrderNo(buildOrderNo(userId));
        orderDTO.setUserId(userId);
        orderDTO.setStatus(TradeCheckoutOrderStatusEnum.CREATED.getCode());
        orderDTO.setItemCount(itemDTOList.size());
        orderDTO.setTotalAmount(totalAmount);
        orderDTO.setPayAmount(totalAmount);
        orderDTO.setRemark(dto.getRemark());
        bizTradeCheckoutOrderMapper.insert(orderDTO);

        itemDTOList.forEach(item -> item.setCheckoutOrderId(orderDTO.getId()));
        bizTradeCheckoutItemMapper.insertBatch(itemDTOList);
        bizTradeCartMapper.deleteByIds(selectedCartList.stream().map(TradeCartDTO::getId).collect(Collectors.toSet()));

        CheckoutResultDTO resultDTO = new CheckoutResultDTO();
        resultDTO.setCheckoutOrderId(orderDTO.getId());
        resultDTO.setOrderNo(orderDTO.getOrderNo());
        resultDTO.setItemCount(orderDTO.getItemCount());
        resultDTO.setTotalAmount(orderDTO.getTotalAmount());
        resultDTO.setPayAmount(orderDTO.getPayAmount());
        resultDTO.setItems(itemDTOList.stream().map(this::toCheckoutItemDTO).toList());
        return resultDTO;
    }

    private TradeCheckoutItemDTO buildCheckoutItem(TradeCartDTO cart,
                                                   Map<Long, ProductSkuDTO> skuMap,
                                                   Map<Long, ProductSpuDTO> spuMap,
                                                   Long userId) {
        ProductSkuDTO skuDTO = skuMap.get(cart.getSkuId());
        if (skuDTO == null) {
            throw new BizException(ProductConstants.CART_SKU_NOT_EXISTS);
        }
        if (skuDTO.getStock() == null || skuDTO.getStock() < cart.getQuantity()) {
            throw new BizException(ProductConstants.CHECKOUT_SKU_STOCK_NOT_ENOUGH);
        }
        ProductSpuDTO spuDTO = spuMap.get(cart.getSpuId());
        if (spuDTO == null || !Objects.equals(spuDTO.getStatus(), ProductSpuStatusEnum.ENABLE.getCode())) {
            throw new BizException(ProductConstants.CART_SKU_NOT_SALE);
        }
        if (!Objects.equals(cart.getSkuPrice(), skuDTO.getPrice())) {
            throw new BizException(ProductConstants.CHECKOUT_SKU_PRICE_CHANGED);
        }

        TradeCheckoutItemDTO itemDTO = new TradeCheckoutItemDTO();
        itemDTO.setUserId(userId);
        itemDTO.setSpuId(spuDTO.getId());
        itemDTO.setSkuId(skuDTO.getId());
        itemDTO.setSpuName(spuDTO.getName());
        itemDTO.setSkuPicUrl(skuDTO.getPicUrl());
        itemDTO.setSkuProperties(cart.getSkuProperties());
        itemDTO.setPrice(skuDTO.getPrice());
        itemDTO.setQuantity(cart.getQuantity());
        itemDTO.setLineAmount(skuDTO.getPrice() * cart.getQuantity());
        return itemDTO;
    }

    private CheckoutItemDTO toCheckoutItemDTO(TradeCheckoutItemDTO dto) {
        CheckoutItemDTO out = new CheckoutItemDTO();
        out.setSkuId(dto.getSkuId());
        out.setSpuId(dto.getSpuId());
        out.setSpuName(dto.getSpuName());
        out.setSkuPicUrl(dto.getSkuPicUrl());
        out.setSkuProperties(dto.getSkuProperties());
        out.setPrice(dto.getPrice());
        out.setQuantity(dto.getQuantity());
        out.setLineAmount(dto.getLineAmount());
        return out;
    }

    private String buildOrderNo(Long userId) {
        int random = ThreadLocalRandom.current().nextInt(1000, 10000);
        return ProductConstants.CHECKOUT_ORDER_NO_PREFIX + System.currentTimeMillis() + userId + random;
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
