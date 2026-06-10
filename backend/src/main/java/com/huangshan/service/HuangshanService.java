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
     * 支持所有Number子类（Integer、Long、Double、Float、BigDecimal等，
     * 覆盖Jackson将JSON数字反序列化为Map&lt;String, Object&gt;时可能产生的各种数值类型）
     * 以及可解析为整数的String
     * @param value 待转换的值
     * @return 转换后的Integer，如果无法转换则返回null
     */
    protected Integer getIntValue(Object value) {
        if (value == null) return null;
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String) {
            String str = ((String) value).trim();
            if (str.isEmpty()) return null;
            try {
                // 先按整数解析；失败则按小数解析后取整，兼容前端传入 "2.0" 等字符串
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                try {
                    return (int) Double.parseDouble(str);
                } catch (NumberFormatException ex) {
                    return null;
                }
            }
        }
        return null;
    }
}
