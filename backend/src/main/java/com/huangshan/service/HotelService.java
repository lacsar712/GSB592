package com.huangshan.service;

import com.huangshan.config.PriceConstants;
import com.huangshan.dto.ServiceRequest;
import com.huangshan.dto.ServiceResponse;
import com.huangshan.util.InputSanitizer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class HotelService extends HuangshanService {

    public HotelService() {
        super("hotel", "酒店预约");
    }

    @Override
    public ServiceResponse process(ServiceRequest request) throws Exception {
        Map<String, Object> params = request.getRequestParam();

        String hotelType = InputSanitizer.sanitize((String) params.get("hotelType"));
        String checkInDate = InputSanitizer.sanitize((String) params.get("checkInDate"));
        String checkOutDate = InputSanitizer.sanitize((String) params.get("checkOutDate"));
        Integer rooms = getIntValue(params.get("rooms"));

        // 参数校验
        if (hotelType == null || checkInDate == null || checkOutDate == null || rooms == null || rooms < 1) {
            throw new IllegalArgumentException("参数不完整或房间数无效");
        }

        // 日期校验 - 允许当天入住，只禁止过去的日期
        LocalDate checkIn = LocalDate.parse(checkInDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate checkOut = LocalDate.parse(checkOutDate, DateTimeFormatter.ISO_LOCAL_DATE);

        if (checkIn.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("入住日期不能选择过去时间");
        }

        if (!checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("离店日期必须大于入住日期");
        }

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (nights < 1) {
            throw new IllegalArgumentException("至少需要入住1晚");
        }

        // 价格计算
        BigDecimal pricePerNight;
        String hotelTypeName;
        switch (hotelType) {
            case "economy":
                pricePerNight = PriceConstants.ECONOMY_HOTEL_PRICE;
                hotelTypeName = "经济型";
                break;
            case "luxury":
                pricePerNight = PriceConstants.LUXURY_HOTEL_PRICE;
                hotelTypeName = "豪华型";
                break;
            default:
                throw new IllegalArgumentException("无效的酒店类型");
        }

        BigDecimal totalPrice = pricePerNight.multiply(new BigDecimal(nights)).multiply(new BigDecimal(rooms));
        String detail = String.format("%s酒店 %d 间，入住 %d 晚，单价 %s 元/晚/间，入住日期 %s，离店日期 %s",
                hotelTypeName, rooms, nights, pricePerNight, checkInDate, checkOutDate);

        ServiceResponse response = new ServiceResponse();
        response.setServiceType("hotel");
        response.setServiceName(serviceName);
        response.setDetail(detail);
        response.setTotalPrice(totalPrice);
        response.setStatus("成功");

        return response;
    }
}
