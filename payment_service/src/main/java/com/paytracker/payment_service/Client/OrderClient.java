package com.paytracker.payment_service.Client;

import com.paytracker.payment_service.Dto.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service")
public interface OrderClient {

    @GetMapping("/api/orders/{id}")
    OrderResponse getOrderById(@PathVariable("id") Long id);
}