package com.paytracker.analytics_service.Dto;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BudgetStatusResponse {
    private String category;
    private BigDecimal monthlyLimit;
    private BigDecimal spentSoFar;
    private BigDecimal percentageUsed;   // e.g. 65.0 means 65% of budget used
    private double percentageOfMonthPassed; // e.g. 33.3 means 1/3 of the month has gone by
    private boolean isOverspending;      // true if spending is faster than time is passing
}