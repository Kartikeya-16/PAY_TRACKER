package com.paytracker.subscription_service.Repository;

import com.paytracker.subscription_service.Entity.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {
    List<PriceHistory> findBySubscriptionIdOrderByEffectiveFromAsc(Long subscriptionId);
}