package com.followba.store.product.service.impl;

import com.followba.store.common.context.RequestContext;
import com.followba.store.common.exception.BizException;
import com.followba.store.dao.biz.BizTradeFulfillmentLogMapper;
import com.followba.store.dao.biz.BizTradeFulfillmentMapper;
import com.followba.store.dao.biz.BizTradeOrderMapper;
import com.followba.store.dao.biz.BizTradeOrderOperateLogMapper;
import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.dao.dto.TradeFulfillmentDTO;
import com.followba.store.dao.dto.TradeFulfillmentLogDTO;
import com.followba.store.dao.dto.TradeOrderDTO;
import com.followba.store.dao.dto.TradeOrderOperateLogDTO;
import com.followba.store.dao.enums.TradeFulfillmentOperateTypeEnum;
import com.followba.store.dao.enums.TradeFulfillmentOperatorTypeEnum;
import com.followba.store.dao.enums.TradeFulfillmentStatusEnum;
import com.followba.store.dao.enums.TradeOrderOperateTypeEnum;
import com.followba.store.dao.enums.TradeOrderOperatorTypeEnum;
import com.followba.store.dao.enums.TradeOrderStatusEnum;
import com.followba.store.product.dto.OrderFulfillmentDetailDTO;
import com.followba.store.product.dto.OrderFulfillmentNodeDTO;
import com.followba.store.product.dto.OrderReceiveDTO;
import com.followba.store.product.service.MallFulfillmentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * C 端履约服务 / Mall fulfillment service.
 */
@Service
public class MallFulfillmentServiceImpl implements MallFulfillmentService {

    @Resource
    private BizTradeOrderMapper bizTradeOrderMapper;

    @Resource
    private BizTradeOrderOperateLogMapper bizTradeOrderOperateLogMapper;

    @Resource
    private BizTradeFulfillmentMapper bizTradeFulfillmentMapper;

    @Resource
    private BizTradeFulfillmentLogMapper bizTradeFulfillmentLogMapper;

    @Override
    public OrderFulfillmentDetailDTO detail(Long orderId) {
        Long userId = getCurrentUserId();
        TradeOrderDTO orderDTO = validateUserOrder(orderId, userId);
        TradeFulfillmentDTO fulfillmentDTO = validateFulfillmentExists(orderId);

        OrderFulfillmentDetailDTO detailDTO = new OrderFulfillmentDetailDTO();
        detailDTO.setOrderId(orderDTO.getId());
        detailDTO.setOrderStatus(orderDTO.getStatus());
        detailDTO.setOrderStatusText(getOrderStatusText(orderDTO.getStatus()));
        detailDTO.setFulfillmentStatus(fulfillmentDTO.getStatus());
        detailDTO.setFulfillmentStatusText(getFulfillmentStatusText(fulfillmentDTO.getStatus()));
        detailDTO.setLogisticsCompanyCode(fulfillmentDTO.getLogisticsCompanyCode());
        detailDTO.setLogisticsCompanyName(fulfillmentDTO.getLogisticsCompanyName());
        detailDTO.setTrackingNo(fulfillmentDTO.getTrackingNo());
        detailDTO.setLatestNode(fulfillmentDTO.getLatestNode());
        detailDTO.setShippedTime(fulfillmentDTO.getShippedTime());
        detailDTO.setSignedTime(fulfillmentDTO.getSignedTime());
        detailDTO.setClosedTime(fulfillmentDTO.getClosedTime());
        detailDTO.setNodes(bizTradeFulfillmentLogMapper.selectListByFulfillmentId(fulfillmentDTO.getId())
                .stream()
                .map(this::toOrderFulfillmentNodeDTO)
                .toList());
        return detailDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receive(OrderReceiveDTO dto) {
        Long userId = getCurrentUserId();
        TradeOrderDTO orderDTO = validateUserOrder(dto.getOrderId(), userId);
        if (!Objects.equals(orderDTO.getStatus(), TradeOrderStatusEnum.PAID.getCode())) {
            throw new BizException(ProductConstants.ORDER_FULFILLMENT_ORDER_STATUS_INVALID);
        }

        TradeFulfillmentDTO fulfillmentDTO = validateFulfillmentExists(orderDTO.getId());
        if (!TradeFulfillmentStatusEnum.canReceive(fulfillmentDTO.getStatus())) {
            throw new BizException(ProductConstants.ORDER_FULFILLMENT_RECEIVE_STATUS_INVALID);
        }

        Date now = new Date();
        String nodeDesc = resolveReceiveNodeDesc(dto.getRemark());
        int fulfillmentRows = bizTradeFulfillmentMapper.updateStatusByOrderIdAndFromStatuses(
                orderDTO.getId(),
                Set.of(
                        TradeFulfillmentStatusEnum.SHIPPED.getCode(),
                        TradeFulfillmentStatusEnum.IN_TRANSIT.getCode(),
                        TradeFulfillmentStatusEnum.OUT_FOR_DELIVERY.getCode()
                ),
                TradeFulfillmentStatusEnum.SIGNED.getCode(),
                nodeDesc,
                now,
                null
        );
        if (fulfillmentRows != ProductConstants.DEFAULT_ONE) {
            throw new BizException(ProductConstants.ORDER_FULFILLMENT_RECEIVE_STATUS_INVALID);
        }

        int orderRows = bizTradeOrderMapper.updateStatusByIdAndFromStatus(
                orderDTO.getId(),
                TradeOrderStatusEnum.PAID.getCode(),
                TradeOrderStatusEnum.COMPLETED.getCode()
        );
        if (orderRows != ProductConstants.DEFAULT_ONE) {
            throw new BizException(ProductConstants.ORDER_FULFILLMENT_ORDER_STATUS_INVALID);
        }

        writeFulfillmentLog(fulfillmentDTO.getId(), orderDTO.getId(), fulfillmentDTO.getStatus(),
                TradeFulfillmentStatusEnum.SIGNED.getCode(),
                TradeFulfillmentOperateTypeEnum.RECEIVE,
                TradeFulfillmentOperatorTypeEnum.USER,
                userId,
                nodeDesc,
                now);
        writeOrderOperateLog(orderDTO.getId(), TradeOrderStatusEnum.PAID.getCode(), TradeOrderStatusEnum.COMPLETED.getCode(),
                TradeOrderOperateTypeEnum.RECEIVE,
                TradeOrderOperatorTypeEnum.USER,
                userId,
                nodeDesc);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createForPaidOrder(TradeOrderDTO orderDTO) {
        if (orderDTO == null || !Objects.equals(orderDTO.getStatus(), TradeOrderStatusEnum.PAID.getCode())) {
            return;
        }
        TradeFulfillmentDTO exist = bizTradeFulfillmentMapper.selectByOrderId(orderDTO.getId());
        if (exist != null) {
            return;
        }

        TradeFulfillmentDTO fulfillmentDTO = new TradeFulfillmentDTO();
        fulfillmentDTO.setOrderId(orderDTO.getId());
        fulfillmentDTO.setOrderNo(orderDTO.getOrderNo());
        fulfillmentDTO.setUserId(orderDTO.getUserId());
        fulfillmentDTO.setStatus(TradeFulfillmentStatusEnum.WAIT_SHIP.getCode());
        fulfillmentDTO.setLogisticsCompanyCode("");
        fulfillmentDTO.setLogisticsCompanyName("");
        fulfillmentDTO.setTrackingNo("");
        fulfillmentDTO.setLatestNode(ProductConstants.FULFILLMENT_INIT_NODE_DESC);
        fulfillmentDTO.setVersion(ProductConstants.DEFAULT_ZERO);
        bizTradeFulfillmentMapper.insert(fulfillmentDTO);

        writeFulfillmentLog(fulfillmentDTO.getId(), orderDTO.getId(), null,
                TradeFulfillmentStatusEnum.WAIT_SHIP.getCode(),
                TradeFulfillmentOperateTypeEnum.CREATE,
                TradeFulfillmentOperatorTypeEnum.SYSTEM,
                ProductConstants.DEFAULT_REPLY_USER_ID,
                ProductConstants.FULFILLMENT_INIT_NODE_DESC,
                new Date());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void closeForOrder(TradeOrderDTO orderDTO) {
        if (orderDTO == null) {
            return;
        }
        TradeFulfillmentDTO fulfillmentDTO = bizTradeFulfillmentMapper.selectByOrderId(orderDTO.getId());
        if (fulfillmentDTO == null || TradeFulfillmentStatusEnum.CLOSED.getCode().equals(fulfillmentDTO.getStatus())) {
            return;
        }
        if (TradeFulfillmentStatusEnum.SIGNED.getCode().equals(fulfillmentDTO.getStatus())) {
            return;
        }

        Date now = new Date();
        int rows = bizTradeFulfillmentMapper.updateStatusByOrderIdAndFromStatus(
                orderDTO.getId(),
                fulfillmentDTO.getStatus(),
                TradeFulfillmentStatusEnum.CLOSED.getCode(),
                ProductConstants.FULFILLMENT_CLOSED_NODE_DESC,
                null,
                now
        );
        if (rows != ProductConstants.DEFAULT_ONE) {
            throw new BizException(ProductConstants.ORDER_FULFILLMENT_ORDER_STATUS_INVALID);
        }

        writeFulfillmentLog(fulfillmentDTO.getId(), orderDTO.getId(), fulfillmentDTO.getStatus(),
                TradeFulfillmentStatusEnum.CLOSED.getCode(),
                TradeFulfillmentOperateTypeEnum.CLOSE,
                TradeFulfillmentOperatorTypeEnum.SYSTEM,
                ProductConstants.DEFAULT_REPLY_USER_ID,
                ProductConstants.FULFILLMENT_CLOSED_NODE_DESC,
                now);
    }

    private String resolveReceiveNodeDesc(String remark) {
        if (remark == null || remark.isBlank()) {
            return ProductConstants.FULFILLMENT_SIGNED_NODE_DESC;
        }
        return remark;
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

    private TradeFulfillmentDTO validateFulfillmentExists(Long orderId) {
        TradeFulfillmentDTO fulfillmentDTO = bizTradeFulfillmentMapper.selectByOrderId(orderId);
        if (fulfillmentDTO == null) {
            throw new BizException(ProductConstants.ORDER_FULFILLMENT_NOT_EXISTS);
        }
        return fulfillmentDTO;
    }

    private OrderFulfillmentNodeDTO toOrderFulfillmentNodeDTO(TradeFulfillmentLogDTO logDTO) {
        OrderFulfillmentNodeDTO nodeDTO = new OrderFulfillmentNodeDTO();
        nodeDTO.setFromStatus(logDTO.getFromStatus());
        nodeDTO.setToStatus(logDTO.getToStatus());
        nodeDTO.setOperateType(logDTO.getOperateType());
        nodeDTO.setNodeDesc(logDTO.getNodeDesc());
        nodeDTO.setNodeTime(logDTO.getNodeTime());
        return nodeDTO;
    }

    private void writeFulfillmentLog(Long fulfillmentId,
                                     Long orderId,
                                     Integer fromStatus,
                                     Integer toStatus,
                                     TradeFulfillmentOperateTypeEnum operateType,
                                     TradeFulfillmentOperatorTypeEnum operatorType,
                                     Long operatorId,
                                     String nodeDesc,
                                     Date nodeTime) {
        TradeFulfillmentLogDTO logDTO = new TradeFulfillmentLogDTO();
        logDTO.setFulfillmentId(fulfillmentId);
        logDTO.setOrderId(orderId);
        logDTO.setFromStatus(fromStatus);
        logDTO.setToStatus(toStatus);
        logDTO.setOperateType(operateType.getCode());
        logDTO.setOperatorType(operatorType.getCode());
        logDTO.setOperatorId(operatorId);
        logDTO.setNodeDesc(nodeDesc);
        logDTO.setNodeTime(nodeTime);
        bizTradeFulfillmentLogMapper.insert(logDTO);
    }

    private void writeOrderOperateLog(Long orderId,
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

    private String getOrderStatusText(Integer status) {
        TradeOrderStatusEnum statusEnum = TradeOrderStatusEnum.of(status);
        return statusEnum == null ? "" : statusEnum.getDesc();
    }

    private String getFulfillmentStatusText(Integer status) {
        TradeFulfillmentStatusEnum statusEnum = TradeFulfillmentStatusEnum.of(status);
        return statusEnum == null ? "" : statusEnum.getDesc();
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
