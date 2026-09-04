package com.paytracker.analytics_service.Dto;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SubscriptionDto {
    private Long id;
    private String name;
    private BigDecimal currentPrice;
    private String billingCycle; // "MONTHLY" or "YEARLY"
    private String status; // "ACTIVE" or "CANCELLED"
}