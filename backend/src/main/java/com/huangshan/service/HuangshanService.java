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
     * 支持Integer和String类型的转换
     * @param value 待转换的值
     * @return 转换后的Integer，如果无法转换则返回null
     */
    protected Integer getIntValue(Object value) {
        if (value == null) return null;
        if (value instanceof Number) {
            int intValue = ((Number) value).intValue();
            if (value instanceof Double || value instanceof Float) {
                if (Math.abs(((Number) value).doubleValue() - intValue) > 1e-9) {
                    return null;
                }
            }
            return intValue;
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                try {
                    double d = Double.parseDouble((String) value);
                    int intValue = (int) d;
                    if (Math.abs(d - intValue) > 1e-9) {
                        return null;
                    }
                    return intValue;
                } catch (NumberFormatException e2) {
                    return null;
                }
            }
        }
        return null;
    }
}
