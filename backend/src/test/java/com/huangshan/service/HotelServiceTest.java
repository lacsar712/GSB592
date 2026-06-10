package com.huangshan.service;

import com.huangshan.config.PriceConstants;
import com.huangshan.dto.ServiceRequest;
import com.huangshan.dto.ServiceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HotelServiceTest {

    private HotelService hotelService;
    private LocalDate futureCheckIn;

    @BeforeEach
    void setUp() {
        hotelService = new HotelService();
        futureCheckIn = LocalDate.now().plusDays(1);
    }

    @Test
    void testEconomyHotelPriceCalculation() throws Exception {
        ServiceRequest request = createHotelRequest("economy", futureCheckIn.toString(), futureCheckIn.plusDays(2).toString(), 2);
        ServiceResponse response = hotelService.process(request);

        // 2 nights * 2 rooms * 300 = 1200
        assertEquals(new BigDecimal("1200"), response.getTotalPrice());
        assertEquals("成功", response.getStatus());
        assertTrue(response.getDetail().contains("经济型"));
    }

    @Test
    void testLuxuryHotelPriceCalculation() throws Exception {
        ServiceRequest request = createHotelRequest("luxury", futureCheckIn.toString(), futureCheckIn.plusDays(3).toString(), 1);
        ServiceResponse response = hotelService.process(request);

        // 3 nights * 1 room * 800 = 2400
        assertEquals(new BigDecimal("2400"), response.getTotalPrice());
        assertEquals("成功", response.getStatus());
        assertTrue(response.getDetail().contains("豪华型"));
    }

    @Test
    void testNightsCalculation() throws Exception {
        ServiceRequest request = createHotelRequest("economy", futureCheckIn.toString(), futureCheckIn.plusDays(5).toString(), 1);
        ServiceResponse response = hotelService.process(request);

        // 5 nights * 1 room * 300 = 1500
        assertEquals(new BigDecimal("1500"), response.getTotalPrice());
        assertTrue(response.getDetail().contains("5 晚"));
    }

    @Test
    void testPastCheckInDateValidation() {
        String pastDate = LocalDate.now().minusDays(1).toString();
        ServiceRequest request = createHotelRequest("economy", pastDate, futureCheckIn.plusDays(2).toString(), 1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            hotelService.process(request);
        });

        assertTrue(exception.getMessage().contains("过去时间"));
    }

    @Test
    void testCheckOutBeforeCheckInValidation() {
        ServiceRequest request = createHotelRequest("economy", futureCheckIn.plusDays(5).toString(), futureCheckIn.plusDays(3).toString(), 1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            hotelService.process(request);
        });

        assertTrue(exception.getMessage().contains("离店日期必须大于入住日期"));
    }

    @Test
    void testSameDayCheckInCheckOutValidation() {
        ServiceRequest request = createHotelRequest("economy", futureCheckIn.toString(), futureCheckIn.toString(), 1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            hotelService.process(request);
        });

        assertTrue(exception.getMessage().contains("离店日期必须大于入住日期"));
    }

    @Test
    void testInvalidRoomsValidation() {
        ServiceRequest request = createHotelRequest("economy", futureCheckIn.toString(), futureCheckIn.plusDays(2).toString(), 0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            hotelService.process(request);
        });

        assertTrue(exception.getMessage().contains("房间数无效"));
    }

    @Test
    void testInvalidHotelType() {
        ServiceRequest request = createHotelRequest("invalid", futureCheckIn.toString(), futureCheckIn.plusDays(2).toString(), 1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            hotelService.process(request);
        });

        assertTrue(exception.getMessage().contains("无效的酒店类型"));
    }

    @Test
    void testPriceConstantsUsed() {
        assertEquals(new BigDecimal("300"), PriceConstants.ECONOMY_HOTEL_PRICE);
        assertEquals(new BigDecimal("800"), PriceConstants.LUXURY_HOTEL_PRICE);
    }

    private ServiceRequest createHotelRequest(String hotelType, String checkInDate, String checkOutDate, int rooms) {
        ServiceRequest request = new ServiceRequest();
        Map<String, Object> params = new HashMap<>();
        params.put("hotelType", hotelType);
        params.put("checkInDate", checkInDate);
        params.put("checkOutDate", checkOutDate);
        params.put("rooms", rooms);
        request.setRequestParam(params);
        return request;
    }
}
