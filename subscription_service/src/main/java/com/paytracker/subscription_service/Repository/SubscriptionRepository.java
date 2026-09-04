package com.paytracker.subscription_service.Repository;

import com.paytracker.subscription_service.Entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserId(Long userId);
    List<Subscription> findByUserIdAndStatus(Long userId, Subscription.SubscriptionStatus status);
    List<Subscription> findByNextRenewalDateBetween(LocalDate start, LocalDate end); // used later to find "renewing soon"
}