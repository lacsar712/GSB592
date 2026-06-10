package com.huangshan.service;

import com.huangshan.config.PriceConstants;
import com.huangshan.dto.ServiceRequest;
import com.huangshan.dto.ServiceResponse;
import com.huangshan.util.InputSanitizer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class TicketService extends HuangshanService {

    public TicketService() {
        super("ticket", "门票预订");
    }

    @Override
    public ServiceResponse process(ServiceRequest request) throws Exception {
        Map<String, Object> params = request.getRequestParam();

        String visitorType = InputSanitizer.sanitize((String) params.get("visitorType"));
        String playDate = InputSanitizer.sanitize((String) params.get("playDate"));
        Integer quantity = getIntValue(params.get("quantity"));

        // 参数校验
        if (visitorType == null || playDate == null || quantity == null || quantity < 1) {
            throw new IllegalArgumentException("参数不完整或数量无效");
        }

        // 日期校验 - 允许当天预订，只禁止过去的日期
        LocalDate date = LocalDate.parse(playDate, DateTimeFormatter.ISO_LOCAL_DATE);
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("游玩日期不能选择过去时间");
        }

        // 价格计算
        BigDecimal unitPrice;
        String visitorTypeName;
        switch (visitorType) {
            case "adult":
                unitPrice = PriceConstants.ADULT_TICKET_PRICE;
                visitorTypeName = "成人票";
                break;
            case "student":
                unitPrice = PriceConstants.STUDENT_TICKET_PRICE;
                visitorTypeName = "学生票";
                break;
            case "senior":
                unitPrice = PriceConstants.SENIOR_TICKET_PRICE;
                visitorTypeName = "老人票（70岁以上免费）";
                break;
            default:
                throw new IllegalArgumentException("无效的游客类型");
        }

        BigDecimal totalPrice = unitPrice.multiply(new BigDecimal(quantity));
        String detail = String.format("%s %d 张，单价 %s 元，游玩日期 %s",
                visitorTypeName, quantity, unitPrice, playDate);

        ServiceResponse response = new ServiceResponse();
        response.setServiceType("ticket");
        response.setServiceName(serviceName);
        response.setDetail(detail);
        response.setTotalPrice(totalPrice);
        response.setStatus("成功");

        return response;
    }
}
