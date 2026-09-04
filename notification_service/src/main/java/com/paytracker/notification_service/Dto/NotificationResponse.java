package com.paytracker.notification_service.Dto;

import com.paytracker.notification_service.Entity.Notification;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NotificationResponse {
    private Long id;
    private Long userId;
    private String message;
    private String subscriptionName;
    private BigDecimal amount;
    private LocalDate relevantDate;
    private LocalDateTime sentAt;

    public static NotificationResponse fromEntity(Notification n) {
        return NotificationResponse.builder()
                .id(n.getId())
                .userId(n.getUserId())
                .message(n.getMessage())
                .subscriptionName(n.getSubscriptionName())
                .amount(n.getAmount())
                .relevantDate(n.getRelevantDate())
                .sentAt(n.getSentAt())
                .build();
    }
}