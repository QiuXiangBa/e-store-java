package com.followba.store.admin.service.impl;

import com.followba.store.admin.dto.OrderFulfillmentDetailDTO;
import com.followba.store.admin.dto.OrderFulfillmentLogisticsNodeDTO;
import com.followba.store.admin.dto.OrderFulfillmentNodeDTO;
import com.followba.store.admin.dto.OrderFulfillmentShipDTO;
import com.followba.store.admin.dto.OrderFulfillmentShipResultDTO;
import com.followba.store.admin.service.OrderFulfillmentService;
import com.followba.store.common.context.RequestContext;
import com.followba.store.common.exception.BizException;
import com.followba.store.dao.biz.BizTradeFulfillmentLogMapper;
import com.followba.store.dao.biz.BizTradeFulfillmentMapper;
import com.followba.store.dao.biz.BizTradeOrderMapper;
import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.dao.dto.TradeFulfillmentDTO;
import com.followba.store.dao.dto.TradeFulfillmentLogDTO;
import com.followba.store.dao.dto.TradeOrderDTO;
import com.followba.store.dao.enums.TradeFulfillmentOperateTypeEnum;
import com.followba.store.dao.enums.TradeFulfillmentOperatorTypeEnum;
import com.followba.store.dao.enums.TradeFulfillmentStatusEnum;
import com.followba.store.dao.enums.TradeOrderStatusEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 管理端履约服务 / Admin fulfillment service.
 */
@Service
public class OrderFulfillmentServiceImpl implements OrderFulfillmentService {

    @Resource
    private BizTradeOrderMapper bizTradeOrderMapper;

    @Resource
    private BizTradeFulfillmentMapper bizTradeFulfillmentMapper;

    @Resource
    private BizTradeFulfillmentLogMapper bizTradeFulfillmentLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderFulfillmentShipResultDTO ship(OrderFulfillmentShipDTO dto) {
        validateLogisticsRequired(dto.getLogisticsCompanyCode(), dto.getLogisticsCompanyName(), dto.getTrackingNo(), dto.getNodeDesc());

        TradeOrderDTO orderDTO = validateOrderExists(dto.getOrderId());
        if (!TradeOrderStatusEnum.PAID.getCode().equals(orderDTO.getStatus())) {
            throw new BizException(ProductConstants.ORDER_FULFILLMENT_ORDER_STATUS_INVALID);
        }

        TradeFulfillmentDTO fulfillmentDTO = validateFulfillmentExists(dto.getOrderId());
        if (!TradeFulfillmentStatusEnum.canShip(fulfillmentDTO.getStatus())) {
            throw new BizException(ProductConstants.ORDER_FULFILLMENT_SHIP_STATUS_INVALID);
        }

        Date now = new Date();
        int rows = bizTradeFulfillmentMapper.updateShipByOrderIdAndFromStatus(
                dto.getOrderId(),
                TradeFulfillmentStatusEnum.WAIT_SHIP.getCode(),
                TradeFulfillmentStatusEnum.SHIPPED.getCode(),
                dto.getLogisticsCompanyCode(),
                dto.getLogisticsCompanyName(),
                dto.getTrackingNo(),
                dto.getNodeDesc(),
                now
        );
        if (rows != ProductConstants.DEFAULT_ONE) {
            throw new BizException(ProductConstants.ORDER_FULFILLMENT_SHIP_STATUS_INVALID);
        }

        writeFulfillmentLog(fulfillmentDTO.getId(), orderDTO.getId(), fulfillmentDTO.getStatus(),
                TradeFulfillmentStatusEnum.SHIPPED.getCode(),
                TradeFulfillmentOperateTypeEnum.SHIP,
                dto.getNodeDesc(),
                now);

        OrderFulfillmentShipResultDTO resultDTO = new OrderFulfillmentShipResultDTO();
        resultDTO.setFulfillmentId(fulfillmentDTO.getId());
        resultDTO.setStatus(TradeFulfillmentStatusEnum.SHIPPED.getCode());
        resultDTO.setStatusText(TradeFulfillmentStatusEnum.SHIPPED.getDesc());
        return resultDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void appendLogisticsNode(OrderFulfillmentLogisticsNodeDTO dto) {
        validateLogisticsNodeStatus(dto.getToStatus());
        if (dto.getNodeDesc() == null || dto.getNodeDesc().isBlank()) {
            throw new BizException(ProductConstants.ORDER_FULFILLMENT_LOGISTICS_NODE_REQUIRED);
        }

        TradeOrderDTO orderDTO = validateOrderExists(dto.getOrderId());
        if (!TradeOrderStatusEnum.PAID.getCode().equals(orderDTO.getStatus())) {
            throw new BizException(ProductConstants.ORDER_FULFILLMENT_ORDER_STATUS_INVALID);
        }

        TradeFulfillmentDTO fulfillmentDTO = validateFulfillmentExists(dto.getOrderId());
        if (!TradeFulfillmentStatusEnum.canAppendLogisticsNode(fulfillmentDTO.getStatus())) {
            throw new BizException(ProductConstants.ORDER_FULFILLMENT_LOGISTICS_STATUS_INVALID);
        }
        if (dto.getToStatus() <= fulfillmentDTO.getStatus()) {
            throw new BizException(ProductConstants.ORDER_FULFILLMENT_LOGISTICS_STATUS_INVALID);
        }

        Date nodeTime = dto.getNodeTime() == null ? new Date() : dto.getNodeTime();
        int rows = bizTradeFulfillmentMapper.updateStatusByOrderIdAndFromStatus(
                dto.getOrderId(),
                fulfillmentDTO.getStatus(),
                dto.getToStatus(),
                dto.getNodeDesc(),
                null,
                null
        );
        if (rows != ProductConstants.DEFAULT_ONE) {
            throw new BizException(ProductConstants.ORDER_FULFILLMENT_LOGISTICS_STATUS_INVALID);
        }

        writeFulfillmentLog(fulfillmentDTO.getId(), orderDTO.getId(), fulfillmentDTO.getStatus(),
                dto.getToStatus(),
                TradeFulfillmentOperateTypeEnum.LOGISTICS_NODE,
                dto.getNodeDesc(),
                nodeTime);
    }

    @Override
    public OrderFulfillmentDetailDTO detail(Long orderId) {
        TradeOrderDTO orderDTO = validateOrderExists(orderId);
        TradeFulfillmentDTO fulfillmentDTO = validateFulfillmentExists(orderId);

        OrderFulfillmentDetailDTO detailDTO = new OrderFulfillmentDetailDTO();
        detailDTO.setFulfillmentId(fulfillmentDTO.getId());
        detailDTO.setOrderId(orderDTO.getId());
        detailDTO.setOrderNo(orderDTO.getOrderNo());
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

    private void validateLogisticsRequired(String logisticsCompanyCode,
                                           String logisticsCompanyName,
                                           String trackingNo,
                                           String nodeDesc) {
        if (logisticsCompanyCode == null || logisticsCompanyCode.isBlank()
                || logisticsCompanyName == null || logisticsCompanyName.isBlank()
                || trackingNo == null || trackingNo.isBlank()
                || nodeDesc == null || nodeDesc.isBlank()) {
            throw new BizException(ProductConstants.ORDER_FULFILLMENT_LOGISTICS_REQUIRED);
        }
    }

    private void validateLogisticsNodeStatus(Integer toStatus) {
        if (toStatus == null
                || (!TradeFulfillmentStatusEnum.IN_TRANSIT.getCode().equals(toStatus)
                && !TradeFulfillmentStatusEnum.OUT_FOR_DELIVERY.getCode().equals(toStatus))) {
            throw new BizException(ProductConstants.ORDER_FULFILLMENT_LOGISTICS_STATUS_INVALID);
        }
    }

    private TradeOrderDTO validateOrderExists(Long orderId) {
        TradeOrderDTO orderDTO = bizTradeOrderMapper.selectById(orderId);
        if (orderDTO == null) {
            throw new BizException(ProductConstants.ORDER_NOT_EXISTS);
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
        nodeDTO.setOperatorType(logDTO.getOperatorType());
        nodeDTO.setOperatorId(logDTO.getOperatorId());
        nodeDTO.setNodeDesc(logDTO.getNodeDesc());
        nodeDTO.setNodeTime(logDTO.getNodeTime());
        return nodeDTO;
    }

    private void writeFulfillmentLog(Long fulfillmentId,
                                     Long orderId,
                                     Integer fromStatus,
                                     Integer toStatus,
                                     TradeFulfillmentOperateTypeEnum operateType,
                                     String nodeDesc,
                                     Date nodeTime) {
        TradeFulfillmentLogDTO logDTO = new TradeFulfillmentLogDTO();
        logDTO.setFulfillmentId(fulfillmentId);
        logDTO.setOrderId(orderId);
        logDTO.setFromStatus(fromStatus);
        logDTO.setToStatus(toStatus);
        logDTO.setOperateType(operateType.getCode());
        logDTO.setOperatorType(TradeFulfillmentOperatorTypeEnum.ADMIN.getCode());
        logDTO.setOperatorId(getCurrentAdminId());
        logDTO.setNodeDesc(nodeDesc);
        logDTO.setNodeTime(nodeTime);
        bizTradeFulfillmentLogMapper.insert(logDTO);
    }

    private String getOrderStatusText(Integer status) {
        TradeOrderStatusEnum statusEnum = TradeOrderStatusEnum.of(status);
        return statusEnum == null ? "" : statusEnum.getDesc();
    }

    private String getFulfillmentStatusText(Integer status) {
        TradeFulfillmentStatusEnum statusEnum = TradeFulfillmentStatusEnum.of(status);
        return statusEnum == null ? "" : statusEnum.getDesc();
    }

    private Long getCurrentAdminId() {
        String userId = RequestContext.getUserId();
        if (userId == null || userId.isBlank()) {
            return ProductConstants.DEFAULT_REPLY_USER_ID;
        }
        try {
            return Long.valueOf(userId);
        } catch (NumberFormatException ignored) {
            return ProductConstants.DEFAULT_REPLY_USER_ID;
        }
    }
}
