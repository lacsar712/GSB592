package com.huangshan.service;

import com.huangshan.config.PriceConstants;
import com.huangshan.dto.ServiceRequest;
import com.huangshan.dto.ServiceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CableCarServiceTest {

    private CableCarService cableCarService;

    @BeforeEach
    void setUp() {
        cableCarService = new CableCarService();
    }

    @Test
    void testUpCablePriceCalculation() throws Exception {
        ServiceRequest request = createCableCarRequest("up", 2);
        ServiceResponse response = cableCarService.process(request);

        assertEquals(new BigDecimal("160"), response.getTotalPrice());
        assertEquals("成功", response.getStatus());
        assertTrue(response.getDetail().contains("上行"));
    }

    @Test
    void testDownCablePriceCalculation() throws Exception {
        ServiceRequest request = createCableCarRequest("down", 3);
        ServiceResponse response = cableCarService.process(request);

        assertEquals(new BigDecimal("210"), response.getTotalPrice());
        assertEquals("成功", response.getStatus());
        assertTrue(response.getDetail().contains("下行"));
    }

    @Test
    void testInvalidQuantityValidation() {
        ServiceRequest request = createCableCarRequest("up", 0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cableCarService.process(request);
        });

        assertTrue(exception.getMessage().contains("数量无效"));
    }

    @Test
    void testNullParametersValidation() {
        ServiceRequest request = new ServiceRequest();
        Map<String, Object> params = new HashMap<>();
        params.put("cableType", null);
        params.put("quantity", 1);
        request.setRequestParam(params);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cableCarService.process(request);
        });

        assertTrue(exception.getMessage().contains("参数不完整"));
    }

    @Test
    void testInvalidCableType() {
        ServiceRequest request = createCableCarRequest("invalid", 1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cableCarService.process(request);
        });

        assertTrue(exception.getMessage().contains("无效的索道类型"));
    }

    @Test
    void testPriceConstantsUsed() {
        assertEquals(new BigDecimal("80"), PriceConstants.CABLE_UP_PRICE);
        assertEquals(new BigDecimal("70"), PriceConstants.CABLE_DOWN_PRICE);
    }

    private ServiceRequest createCableCarRequest(String cableType, int quantity) {
        ServiceRequest request = new ServiceRequest();
        Map<String, Object> params = new HashMap<>();
        params.put("cableType", cableType);
        params.put("quantity", quantity);
        request.setRequestParam(params);
        return request;
    }
}
