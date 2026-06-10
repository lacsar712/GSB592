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

class TicketServiceTest {

    private TicketService ticketService;
    private String futureDate;

    @BeforeEach
    void setUp() {
        ticketService = new TicketService();
        futureDate = LocalDate.now().plusDays(1).toString();
    }

    @Test
    void testAdultTicketPriceCalculation() throws Exception {
        ServiceRequest request = createTicketRequest("adult", futureDate, 2);
        ServiceResponse response = ticketService.process(request);

        assertEquals(new BigDecimal("300"), response.getTotalPrice());
        assertEquals("成功", response.getStatus());
        assertTrue(response.getDetail().contains("成人票"));
    }

    @Test
    void testStudentTicketPriceCalculation() throws Exception {
        ServiceRequest request = createTicketRequest("student", futureDate, 3);
        ServiceResponse response = ticketService.process(request);

        assertEquals(new BigDecimal("225"), response.getTotalPrice());
        assertEquals("成功", response.getStatus());
        assertTrue(response.getDetail().contains("学生票"));
    }

    @Test
    void testSeniorTicketIsFree() throws Exception {
        ServiceRequest request = createTicketRequest("senior", futureDate, 2);
        ServiceResponse response = ticketService.process(request);

        assertEquals(BigDecimal.ZERO, response.getTotalPrice());
        assertEquals("成功", response.getStatus());
        assertTrue(response.getDetail().contains("老人票"));
    }

    @Test
    void testPastDateValidation() {
        String pastDate = LocalDate.now().minusDays(1).toString();
        ServiceRequest request = createTicketRequest("adult", pastDate, 1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticketService.process(request);
        });

        assertTrue(exception.getMessage().contains("过去时间"));
    }

    @Test
    void testInvalidQuantityValidation() {
        ServiceRequest request = createTicketRequest("adult", futureDate, 0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticketService.process(request);
        });

        assertTrue(exception.getMessage().contains("数量无效"));
    }

    @Test
    void testNullParametersValidation() {
        ServiceRequest request = new ServiceRequest();
        Map<String, Object> params = new HashMap<>();
        params.put("visitorType", null);
        params.put("playDate", futureDate);
        params.put("quantity", 1);
        request.setRequestParam(params);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticketService.process(request);
        });

        assertTrue(exception.getMessage().contains("参数不完整"));
    }

    @Test
    void testInvalidVisitorType() {
        ServiceRequest request = createTicketRequest("invalid", futureDate, 1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticketService.process(request);
        });

        assertTrue(exception.getMessage().contains("无效的游客类型"));
    }

    @Test
    void testPriceConstantsUsed() {
        // Verify that price constants are used correctly
        assertEquals(new BigDecimal("150"), PriceConstants.ADULT_TICKET_PRICE);
        assertEquals(new BigDecimal("75"), PriceConstants.STUDENT_TICKET_PRICE);
        assertEquals(BigDecimal.ZERO, PriceConstants.SENIOR_TICKET_PRICE);
    }

    private ServiceRequest createTicketRequest(String visitorType, String playDate, int quantity) {
        ServiceRequest request = new ServiceRequest();
        Map<String, Object> params = new HashMap<>();
        params.put("visitorType", visitorType);
        params.put("playDate", playDate);
        params.put("quantity", quantity);
        request.setRequestParam(params);
        return request;
    }
}
