package com.paytracker.subscription_service.Dto;

import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

// This must be Serializable — RabbitMQ needs to convert it to bytes to send it.
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RenewalEvent implements Serializable {
    private Long userId;
    private String subscriptionName;
    private BigDecimal price;
    private LocalDate renewalDate;
}