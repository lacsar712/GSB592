package com.huangshan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hs_order", indexes = {
        @Index(name = "idx_order_no", columnList = "order_no", unique = true),
        @Index(name = "idx_service_type", columnList = "service_type")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HsOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_no", nullable = false, unique = true, length = 50)
    private String orderNo;

    @Column(name = "service_type", nullable = false, length = 20)
    private String serviceType;

    @Column(name = "service_name", nullable = false, length = 50)
    private String serviceName;

    @Column(name = "request_json", columnDefinition = "TEXT")
    private String requestJson;

    @Column(name = "detail", length = 500)
    private String detail;

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }
}
