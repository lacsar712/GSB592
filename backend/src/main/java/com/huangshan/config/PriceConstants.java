package com.huangshan.config;

import java.math.BigDecimal;

/**
 * 价格常量配置类
 * 集中管理所有服务的价格配置
 */
public class PriceConstants {

    // 门票价格
    public static final BigDecimal ADULT_TICKET_PRICE = new BigDecimal("150");
    public static final BigDecimal STUDENT_TICKET_PRICE = new BigDecimal("75");
    public static final BigDecimal SENIOR_TICKET_PRICE = BigDecimal.ZERO;

    // 酒店价格（每晚每间）
    public static final BigDecimal ECONOMY_HOTEL_PRICE = new BigDecimal("300");
    public static final BigDecimal LUXURY_HOTEL_PRICE = new BigDecimal("800");

    // 索道价格
    public static final BigDecimal CABLE_UP_PRICE = new BigDecimal("80");
    public static final BigDecimal CABLE_DOWN_PRICE = new BigDecimal("70");

    // 私有构造函数，防止实例化
    private PriceConstants() {
        throw new AssertionError("Cannot instantiate constants class");
    }
}
