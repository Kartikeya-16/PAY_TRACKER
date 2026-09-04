package com.paytracker.subscription_service.Dto;

import com.paytracker.subscription_service.Entity.Subscription;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SubscriptionResponse {
    private Long id;
    private Long userId;
    private String name;
    private BigDecimal currentPrice;
    private String billingCycle;
    private LocalDate startDate;
    private LocalDate nextRenewalDate;
    private String status;
    private List<PriceHistoryResponse> priceHistory;
    private LocalDateTime createdAt;

    public static SubscriptionResponse fromEntity(Subscription s) {
        return SubscriptionResponse.builder()
                .id(s.getId())
                .userId(s.getUserId())
                .name(s.getName())
                .currentPrice(s.getCurrentPrice())
                .billingCycle(s.getBillingCycle().name())
                .startDate(s.getStartDate())
                .nextRenewalDate(s.getNextRenewalDate())
                .status(s.getStatus().name())
                .priceHistory(s.getPriceHistory() == null ? List.of() :
                        s.getPriceHistory().stream().map(PriceHistoryResponse::fromEntity).toList())
                .createdAt(s.getCreatedAt())
                .build();
    }
}