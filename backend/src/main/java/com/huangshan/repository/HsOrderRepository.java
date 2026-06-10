package com.huangshan.repository;

import com.huangshan.model.HsOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HsOrderRepository extends JpaRepository<HsOrder, Long> {
    Optional<HsOrder> findByOrderNo(String orderNo);
}
