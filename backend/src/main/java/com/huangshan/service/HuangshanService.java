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
     * 支持Integer、Long、Double、Number和String类型的转换
     * @param value 待转换的值
     * @return 转换后的Integer，如果无法转换则返回null
     */
    protected Integer getIntValue(Object value) {
        if (value == null) return null;
        if (value instanceof Integer) return (Integer) value;
        if (value instanceof Number) {
            Number num = (Number) value;
            if (value instanceof Double || value instanceof Float) {
                double d = num.doubleValue();
                if (d != Math.floor(d) || d > Integer.MAX_VALUE || d < Integer.MIN_VALUE) {
                    return null;
                }
            }
            long l = num.longValue();
            if (l > Integer.MAX_VALUE || l < Integer.MIN_VALUE) {
                return null;
            }
            return num.intValue();
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
