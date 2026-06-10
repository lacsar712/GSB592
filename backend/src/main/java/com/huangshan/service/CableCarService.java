package com.huangshan.service;

import com.huangshan.config.PriceConstants;
import com.huangshan.dto.ServiceRequest;
import com.huangshan.dto.ServiceResponse;
import com.huangshan.util.InputSanitizer;

import java.math.BigDecimal;
import java.util.Map;

public class CableCarService extends HuangshanService {

    public CableCarService() {
        super("cableCar", "索道购票");
    }

    @Override
    public ServiceResponse process(ServiceRequest request) throws Exception {
        Map<String, Object> params = request.getRequestParam();

        String cableType = InputSanitizer.sanitize((String) params.get("cableType"));
        Integer quantity = getIntValue(params.get("quantity"));

        // 参数校验
        if (cableType == null || quantity == null || quantity < 1) {
            throw new IllegalArgumentException("参数不完整或数量无效");
        }

        // 价格计算
        BigDecimal unitPrice;
        String cableTypeName;
        switch (cableType) {
            case "up":
                unitPrice = PriceConstants.CABLE_UP_PRICE;
                cableTypeName = "上行";
                break;
            case "down":
                unitPrice = PriceConstants.CABLE_DOWN_PRICE;
                cableTypeName = "下行";
                break;
            default:
                throw new IllegalArgumentException("无效的索道类型");
        }

        BigDecimal totalPrice = unitPrice.multiply(new BigDecimal(quantity));
        String detail = String.format("索道%s票 %d 张，单价 %s 元",
                cableTypeName, quantity, unitPrice);

        ServiceResponse response = new ServiceResponse();
        response.setServiceType("cableCar");
        response.setServiceName(serviceName);
        response.setDetail(detail);
        response.setTotalPrice(totalPrice);
        response.setStatus("成功");

        return response;
    }
}
