package com.paytracker.notification_service.Dto;

import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

// IMPORTANT: field names and types here must exactly match
// Subscription Service's RenewalEvent — that's how RabbitMQ knows how to
// turn the message bytes back into an object we can use.
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RenewalEvent implements Serializable {
    private Long userId;
    private String subscriptionName;
    private BigDecimal price;
    private LocalDate renewalDate;
}