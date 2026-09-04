package com.paytracker.subscription_service.Dto;

import com.paytracker.subscription_service.Entity.Subscription;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SubscriptionRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String name;

    @NotNull @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal currentPrice;

    @NotNull
    private Subscription.BillingCycle billingCycle;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate nextRenewalDate;
}