package com.huangshan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huangshan.dto.ApiResponse;
import com.huangshan.dto.ServiceRequest;
import com.huangshan.dto.ServiceResponse;
import com.huangshan.model.HsOrder;
import com.huangshan.repository.HsOrderRepository;
import com.huangshan.service.CableCarService;
import com.huangshan.service.HotelService;
import com.huangshan.service.HuangshanService;
import com.huangshan.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
@RequestMapping("/api/huangshan")
public class ServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Autowired
    private HsOrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/service")
    public ApiResponse<ServiceResponse> processService(@RequestBody ServiceRequest request) {
        try {
            if (request == null) {
                return ApiResponse.error(400, "请求体不能为空");
            }

            String serviceType = request.getServiceType();
            if (serviceType == null || serviceType.isEmpty()) {
                return ApiResponse.error(400, "服务类型不能为空");
            }

            if (request.getRequestParam() == null) {
                return ApiResponse.error(400, "请求参数不能为空");
            }

            // 多态体现：根据serviceType创建不同的子类实例，使用父类引用
            HuangshanService service;
            switch (serviceType) {
                case "ticket":
                    service = new TicketService();
                    break;
                case "hotel":
                    service = new HotelService();
                    break;
                case "cableCar":
                    service = new CableCarService();
                    break;
                default:
                    return ApiResponse.error(400, "不支持的服务类型");
            }

            // 统一调用父类的process方法
            ServiceResponse response = service.process(request);

            // 生成订单号
            String orderNo = generateOrderNo();
            response.setOrderNo(orderNo);

            // 保存订单到数据库
            HsOrder order = new HsOrder();
            order.setOrderNo(orderNo);
            order.setServiceType(serviceType);
            order.setServiceName(response.getServiceName());
            order.setRequestJson(objectMapper.writeValueAsString(request.getRequestParam()));
            order.setDetail(response.getDetail());
            order.setTotalPrice(response.getTotalPrice());
            order.setStatus(response.getStatus());

            orderRepository.save(order);

            return ApiResponse.success(response);

        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request parameters: {}", e.getMessage());
            return ApiResponse.error(400, e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to process service request", e);
            return ApiResponse.error(500, "服务处理失败，请稍后重试");
        }
    }

    /**
     * 生成唯一订单号
     * 格式：HS + yyyyMMddHHmmss + 6位随机数
     */
    private String generateOrderNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int randomNumber = SECURE_RANDOM.nextInt(1_000_000);
        String randomSuffix = String.format(Locale.ROOT, "%06d", randomNumber);
        return "HS" + timestamp + randomSuffix;
    }
}
