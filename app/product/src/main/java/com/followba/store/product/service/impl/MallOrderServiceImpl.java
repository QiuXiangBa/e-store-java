package com.followba.store.product.service.impl;

import com.followba.store.common.context.RequestContext;
import com.followba.store.common.exception.BizException;
import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.biz.BizTradeCheckoutItemMapper;
import com.followba.store.dao.biz.BizTradeCheckoutOrderMapper;
import com.followba.store.dao.biz.BizTradeOrderItemMapper;
import com.followba.store.dao.biz.BizTradeOrderMapper;
import com.followba.store.dao.biz.BizTradeOrderOperateLogMapper;
import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.TradeCheckoutItemDTO;
import com.followba.store.dao.dto.TradeCheckoutOrderDTO;
import com.followba.store.dao.dto.TradeOrderDTO;
import com.followba.store.dao.dto.TradeOrderItemDTO;
import com.followba.store.dao.dto.TradeOrderOperateLogDTO;
import com.followba.store.dao.enums.TradeCheckoutOrderStatusEnum;
import com.followba.store.dao.enums.TradeOrderOperateTypeEnum;
import com.followba.store.dao.enums.TradeOrderOperatorTypeEnum;
import com.followba.store.dao.enums.TradeOrderStatusEnum;
import com.followba.store.product.dto.OrderCancelDTO;
import com.followba.store.product.dto.OrderCreateDTO;
import com.followba.store.product.dto.OrderCreateResultDTO;
import com.followba.store.product.dto.OrderDetailDTO;
import com.followba.store.product.dto.OrderItemDTO;
import com.followba.store.product.dto.OrderPageItemDTO;
import com.followba.store.product.dto.OrderPageQueryDTO;
import com.followba.store.product.dto.OrderPaySuccessDTO;
import com.followba.store.product.dto.OrderSimpleItemDTO;
import com.followba.store.product.dto.OrderStatusDTO;
import com.followba.store.product.service.MallOrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class MallOrderServiceImpl implements MallOrderService {

    @Resource
    private BizTradeCheckoutOrderMapper bizTradeCheckoutOrderMapper;

    @Resource
    private BizTradeCheckoutItemMapper bizTradeCheckoutItemMapper;

    @Resource
    private BizTradeOrderMapper bizTradeOrderMapper;

    @Resource
    private BizTradeOrderItemMapper bizTradeOrderItemMapper;

    @Resource
    private BizTradeOrderOperateLogMapper bizTradeOrderOperateLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderCreateResultDTO create(OrderCreateDTO dto) {
        Long userId = getCurrentUserId();
        validateRequestId(dto.getRequestId());

        TradeOrderDTO existByRequestId = bizTradeOrderMapper.selectByUserIdAndRequestId(userId, dto.getRequestId());
        if (existByRequestId != null) {
            return toOrderCreateResultDTO(existByRequestId);
        }

        TradeCheckoutOrderDTO checkoutOrderDTO = findCheckoutOrder(dto.getCheckoutOrderId(), userId);
        if (!Objects.equals(checkoutOrderDTO.getStatus(), TradeCheckoutOrderStatusEnum.CREATED.getCode())) {
            throw new BizException(ProductConstants.ORDER_STATUS_INVALID);
        }

        TradeOrderDTO existByCheckout = bizTradeOrderMapper
                .selectByCheckoutOrderIdAndUserId(checkoutOrderDTO.getId(), userId);
        if (existByCheckout != null) {
            return toOrderCreateResultDTO(existByCheckout);
        }

        List<TradeCheckoutItemDTO> checkoutItems = bizTradeCheckoutItemMapper
                .selectListByCheckoutOrderId(checkoutOrderDTO.getId());
        if (checkoutItems.isEmpty()) {
            throw new BizException(ProductConstants.ORDER_CHECKOUT_ITEM_EMPTY);
        }

        TradeOrderDTO orderDTO = new TradeOrderDTO();
        orderDTO.setOrderNo(buildOrderNo(userId));
        orderDTO.setRequestId(dto.getRequestId());
        orderDTO.setUserId(userId);
        orderDTO.setCheckoutOrderId(checkoutOrderDTO.getId());
        orderDTO.setStatus(TradeOrderStatusEnum.CREATED.getCode());
        orderDTO.setItemCount(checkoutOrderDTO.getItemCount());
        orderDTO.setTotalAmount(checkoutOrderDTO.getTotalAmount());
        orderDTO.setPayAmount(checkoutOrderDTO.getPayAmount());
        orderDTO.setRemark(dto.getRemark());
        bizTradeOrderMapper.insert(orderDTO);

        List<TradeOrderItemDTO> orderItems = checkoutItems.stream()
                .map(item -> toTradeOrderItemDTO(item, orderDTO.getId(), userId))
                .toList();
        bizTradeOrderItemMapper.insertBatch(orderItems);

        writeOperateLog(orderDTO.getId(), null, orderDTO.getStatus(), TradeOrderOperateTypeEnum.CREATE,
                TradeOrderOperatorTypeEnum.USER, userId, "create by checkout");
        return toOrderCreateResultDTO(orderDTO);
    }

    @Override
    public PageResp<OrderPageItemDTO> page(OrderPageQueryDTO dto) {
        Long userId = getCurrentUserId();
        PageDTO<TradeOrderDTO> pageDTO = bizTradeOrderMapper
                .selectPageByUser(userId, dto.getPageNum(), dto.getPageSize(), dto.getStatus());
        if (pageDTO.getList().isEmpty()) {
            return PageResp.empty();
        }

        Set<Long> orderIds = pageDTO.getList().stream().map(TradeOrderDTO::getId).collect(Collectors.toSet());
        Map<Long, List<TradeOrderItemDTO>> orderItemMap = bizTradeOrderItemMapper.selectListByOrderIds(orderIds)
                .stream()
                .collect(Collectors.groupingBy(TradeOrderItemDTO::getOrderId));

        List<OrderPageItemDTO> outList = new ArrayList<>();
        for (TradeOrderDTO orderDTO : pageDTO.getList()) {
            OrderPageItemDTO itemDTO = new OrderPageItemDTO();
            itemDTO.setOrderId(orderDTO.getId());
            itemDTO.setOrderNo(orderDTO.getOrderNo());
            itemDTO.setStatus(orderDTO.getStatus());
            itemDTO.setStatusText(getStatusText(orderDTO.getStatus()));
            itemDTO.setPayAmount(orderDTO.getPayAmount());
            itemDTO.setItemCount(orderDTO.getItemCount());
            itemDTO.setCreateTime(orderDTO.getCreateTime());

            List<TradeOrderItemDTO> orderItems = orderItemMap.get(orderDTO.getId());
            if (orderItems != null && !orderItems.isEmpty()) {
                TradeOrderItemDTO firstItem = orderItems.get(ProductConstants.DEFAULT_ZERO);
                OrderSimpleItemDTO simpleItemDTO = new OrderSimpleItemDTO();
                simpleItemDTO.setSkuId(firstItem.getSkuId());
                simpleItemDTO.setSpuName(firstItem.getSpuName());
                simpleItemDTO.setSkuPicUrl(firstItem.getSkuPicUrl());
                simpleItemDTO.setQuantity(firstItem.getQuantity());
                itemDTO.setFirstItem(simpleItemDTO);
            }
            outList.add(itemDTO);
        }
        return PageResp.of(pageDTO.getTotal(), outList);
    }

    @Override
    public OrderDetailDTO detail(Long id) {
        Long userId = getCurrentUserId();
        TradeOrderDTO orderDTO = validateUserOrder(id, userId);
        List<TradeOrderItemDTO> orderItems = bizTradeOrderItemMapper.selectListByOrderId(orderDTO.getId());

        OrderDetailDTO out = new OrderDetailDTO();
        out.setOrderId(orderDTO.getId());
        out.setOrderNo(orderDTO.getOrderNo());
        out.setStatus(orderDTO.getStatus());
        out.setStatusText(getStatusText(orderDTO.getStatus()));
        out.setItemCount(orderDTO.getItemCount());
        out.setTotalAmount(orderDTO.getTotalAmount());
        out.setPayAmount(orderDTO.getPayAmount());
        out.setRemark(orderDTO.getRemark());
        out.setCancelReason(orderDTO.getCancelReason());
        out.setCreateTime(orderDTO.getCreateTime());
        out.setPaidTime(orderDTO.getPaidTime());
        out.setCancelTime(orderDTO.getCancelTime());
        out.setUpdateTime(orderDTO.getUpdateTime());
        out.setCanCancel(TradeOrderStatusEnum.canCancel(orderDTO.getStatus()));
        out.setCanPay(TradeOrderStatusEnum.canPay(orderDTO.getStatus()));
        out.setItems(orderItems.stream().map(this::toOrderItemDTO).toList());
        return out;
    }

    @Override
    public OrderStatusDTO status(Long id) {
        Long userId = getCurrentUserId();
        TradeOrderDTO orderDTO = validateUserOrder(id, userId);
        OrderStatusDTO out = new OrderStatusDTO();
        out.setOrderId(orderDTO.getId());
        out.setStatus(orderDTO.getStatus());
        out.setStatusText(getStatusText(orderDTO.getStatus()));
        out.setUpdateTime(orderDTO.getUpdateTime());
        return out;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(OrderCancelDTO dto) {
        Long userId = getCurrentUserId();
        TradeOrderDTO orderDTO = validateUserOrder(dto.getOrderId(), userId);
        if (!TradeOrderStatusEnum.canCancel(orderDTO.getStatus())) {
            throw new BizException(ProductConstants.ORDER_CANCEL_STATUS_INVALID);
        }

        TradeOrderDTO updateDTO = new TradeOrderDTO();
        updateDTO.setId(orderDTO.getId());
        updateDTO.setStatus(TradeOrderStatusEnum.CANCELLED.getCode());
        updateDTO.setCancelReason(dto.getReason());
        updateDTO.setCancelTime(new Date());
        bizTradeOrderMapper.updateById(updateDTO);

        writeOperateLog(orderDTO.getId(), orderDTO.getStatus(), TradeOrderStatusEnum.CANCELLED.getCode(),
                TradeOrderOperateTypeEnum.CANCEL, TradeOrderOperatorTypeEnum.USER, userId, dto.getReason());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void paySuccess(OrderPaySuccessDTO dto) {
        TradeOrderDTO orderDTO = bizTradeOrderMapper.selectByOrderNo(dto.getOrderNo());
        if (orderDTO == null) {
            throw new BizException(ProductConstants.ORDER_NOT_EXISTS);
        }
        if (Objects.equals(orderDTO.getStatus(), TradeOrderStatusEnum.PAID.getCode())) {
            return;
        }
        if (!Objects.equals(orderDTO.getStatus(), TradeOrderStatusEnum.CREATED.getCode())) {
            throw new BizException(ProductConstants.ORDER_PAY_STATUS_INVALID);
        }

        TradeOrderDTO updateDTO = new TradeOrderDTO();
        updateDTO.setId(orderDTO.getId());
        updateDTO.setStatus(TradeOrderStatusEnum.PAID.getCode());
        updateDTO.setPaidTime(new Date());
        bizTradeOrderMapper.updateById(updateDTO);

        writeOperateLog(orderDTO.getId(), orderDTO.getStatus(), TradeOrderStatusEnum.PAID.getCode(),
                TradeOrderOperateTypeEnum.PAY_SUCCESS, TradeOrderOperatorTypeEnum.SYSTEM,
                ProductConstants.DEFAULT_REPLY_USER_ID, dto.getPayTxnNo());
    }

    private TradeCheckoutOrderDTO findCheckoutOrder(Long checkoutOrderId, Long userId) {
        TradeCheckoutOrderDTO checkoutOrderDTO;
        if (checkoutOrderId == null) {
            checkoutOrderDTO = bizTradeCheckoutOrderMapper.selectLatestByUserId(userId);
        } else {
            checkoutOrderDTO = bizTradeCheckoutOrderMapper.selectById(checkoutOrderId);
        }
        if (checkoutOrderDTO == null) {
            throw new BizException(ProductConstants.CHECKOUT_ORDER_NOT_EXISTS);
        }
        if (!Objects.equals(checkoutOrderDTO.getUserId(), userId)) {
            throw new BizException(ProductConstants.ORDER_CHECKOUT_USER_MISMATCH);
        }
        return checkoutOrderDTO;
    }

    private TradeOrderDTO validateUserOrder(Long orderId, Long userId) {
        TradeOrderDTO orderDTO = bizTradeOrderMapper.selectById(orderId);
        if (orderDTO == null) {
            throw new BizException(ProductConstants.ORDER_NOT_EXISTS);
        }
        if (!Objects.equals(orderDTO.getUserId(), userId)) {
            throw new BizException(ProductConstants.ORDER_NOT_BELONG_USER);
        }
        return orderDTO;
    }

    private TradeOrderItemDTO toTradeOrderItemDTO(TradeCheckoutItemDTO item, Long orderId, Long userId) {
        TradeOrderItemDTO orderItemDTO = new TradeOrderItemDTO();
        orderItemDTO.setOrderId(orderId);
        orderItemDTO.setUserId(userId);
        orderItemDTO.setSpuId(item.getSpuId());
        orderItemDTO.setSkuId(item.getSkuId());
        orderItemDTO.setSpuName(item.getSpuName());
        orderItemDTO.setSkuPicUrl(item.getSkuPicUrl());
        orderItemDTO.setSkuProperties(item.getSkuProperties());
        orderItemDTO.setPrice(item.getPrice());
        orderItemDTO.setQuantity(item.getQuantity());
        orderItemDTO.setLineAmount(item.getLineAmount());
        return orderItemDTO;
    }

    private OrderItemDTO toOrderItemDTO(TradeOrderItemDTO dto) {
        OrderItemDTO out = new OrderItemDTO();
        out.setSpuId(dto.getSpuId());
        out.setSkuId(dto.getSkuId());
        out.setSpuName(dto.getSpuName());
        out.setSkuPicUrl(dto.getSkuPicUrl());
        out.setSkuProperties(dto.getSkuProperties());
        out.setPrice(dto.getPrice());
        out.setQuantity(dto.getQuantity());
        out.setLineAmount(dto.getLineAmount());
        return out;
    }

    private OrderCreateResultDTO toOrderCreateResultDTO(TradeOrderDTO dto) {
        OrderCreateResultDTO out = new OrderCreateResultDTO();
        out.setOrderId(dto.getId());
        out.setOrderNo(dto.getOrderNo());
        out.setStatus(dto.getStatus());
        out.setItemCount(dto.getItemCount());
        out.setTotalAmount(dto.getTotalAmount());
        out.setPayAmount(dto.getPayAmount());
        return out;
    }

    private void writeOperateLog(Long orderId,
                                 Integer fromStatus,
                                 Integer toStatus,
                                 TradeOrderOperateTypeEnum operateType,
                                 TradeOrderOperatorTypeEnum operatorType,
                                 Long operatorId,
                                 String reason) {
        TradeOrderOperateLogDTO logDTO = new TradeOrderOperateLogDTO();
        logDTO.setOrderId(orderId);
        logDTO.setFromStatus(fromStatus);
        logDTO.setToStatus(toStatus);
        logDTO.setOperateType(operateType.getCode());
        logDTO.setOperatorType(operatorType.getCode());
        logDTO.setOperatorId(operatorId);
        logDTO.setReason(reason);
        bizTradeOrderOperateLogMapper.insert(logDTO);
    }

    private String getStatusText(Integer status) {
        TradeOrderStatusEnum statusEnum = TradeOrderStatusEnum.of(status);
        return statusEnum == null ? "" : statusEnum.getDesc();
    }

    private void validateRequestId(String requestId) {
        if (requestId == null || requestId.isBlank()) {
            throw new BizException(ProductConstants.ORDER_REQUEST_ID_REQUIRED);
        }
    }

    private String buildOrderNo(Long userId) {
        int random = ThreadLocalRandom.current().nextInt(1000, 10000);
        return ProductConstants.ORDER_NO_PREFIX + System.currentTimeMillis() + userId + random;
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
