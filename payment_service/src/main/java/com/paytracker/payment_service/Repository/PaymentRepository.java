package com.paytracker.payment_service.Repository;

import com.paytracker.payment_service.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(Long userId);
    Optional<Payment> findByOrderId(Long orderId);
}