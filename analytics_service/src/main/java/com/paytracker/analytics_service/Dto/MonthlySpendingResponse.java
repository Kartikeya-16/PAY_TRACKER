package com.paytracker.analytics_service.Dto;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MonthlySpendingResponse {
    private String month; // e.g. "2026-08"
    private BigDecimal totalSpent;
}