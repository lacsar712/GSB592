package com.huangshan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse {
    private String serviceType;
    private String serviceName;
    private String orderNo;
    private String detail;
    private BigDecimal totalPrice;
    private String status;
}
