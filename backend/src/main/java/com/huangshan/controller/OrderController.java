package com.huangshan.controller;

import com.huangshan.dto.ApiResponse;
import com.huangshan.model.HsOrder;
import com.huangshan.repository.HsOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/huangshan/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private HsOrderRepository orderRepository;

    @GetMapping("/query")
    public ApiResponse<Map<String, Object>> queryOrder(@RequestParam String orderNo) {
        try {
            if (orderNo == null || orderNo.trim().isEmpty()) {
                return ApiResponse.error(400, "订单号不能为空");
            }

            Optional<HsOrder> orderOpt = orderRepository.findByOrderNo(orderNo.trim());
            if (!orderOpt.isPresent()) {
                return ApiResponse.error(404, "订单不存在");
            }

            HsOrder order = orderOpt.get();
            Map<String, Object> result = new HashMap<>();
            result.put("orderNo", order.getOrderNo());
            result.put("serviceType", order.getServiceType());
            result.put("serviceName", order.getServiceName());
            result.put("detail", order.getDetail());
            result.put("totalPrice", order.getTotalPrice());
            result.put("status", order.getStatus());
            result.put("createTime", order.getCreateTime().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            return ApiResponse.success(result);

        } catch (Exception e) {
            logger.error("Failed to query order: {}", orderNo, e);
            return ApiResponse.error(500, "查询订单失败，请稍后重试");
        }
    }
}
