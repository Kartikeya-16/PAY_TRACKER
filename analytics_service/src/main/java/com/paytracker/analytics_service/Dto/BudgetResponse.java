package com.paytracker.analytics_service.Dto;

import com.paytracker.analytics_service.Entity.Budget;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BudgetResponse {
    private Long id;
    private Long userId;
    private String category;
    private BigDecimal monthlyLimit;

    public static BudgetResponse fromEntity(Budget b) {
        return BudgetResponse.builder()
                .id(b.getId())
                .userId(b.getUserId())
                .category(b.getCategory())
                .monthlyLimit(b.getMonthlyLimit())
                .build();
    }
}