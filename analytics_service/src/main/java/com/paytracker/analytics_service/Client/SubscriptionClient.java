package com.paytracker.analytics_service.Client;

import com.paytracker.analytics_service.Dto.SubscriptionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "subscription-service")
public interface SubscriptionClient {

    @GetMapping("/api/subscriptions/user/{userId}/active")
    List<SubscriptionDto> getActiveSubscriptions(@PathVariable("userId") Long userId);
}