package com.huangshan.service;

import com.huangshan.dto.ServiceRequest;
import com.huangshan.dto.ServiceResponse;

public abstract class HuangshanService {
    protected String serviceId;
    protected String serviceName;

    public HuangshanService(String serviceId, String serviceName) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
    }

    public abstract ServiceResponse process(ServiceRequest request) throws Exception;

    public String getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    /**
     * 将Object类型的值转换为Integer
     * 支持Number子类(Integer, Long, Double, Float, Short, Byte, BigDecimal)和String类型的转换
     * @param value 待转换的值
     * @return 转换后的Integer，如果无法转换则返回null
     */
    protected Integer getIntValue(Object value) {
        if (value == null) return null;
        if (value instanceof Integer) return (Integer) value;
        if (value instanceof Number) {
            double doubleValue = ((Number) value).doubleValue();
            if (doubleValue % 1 != 0) {
                return null;
            }
            if (doubleValue > Integer.MAX_VALUE || doubleValue < Integer.MIN_VALUE) {
                return null;
            }
            return (int) doubleValue;
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}
