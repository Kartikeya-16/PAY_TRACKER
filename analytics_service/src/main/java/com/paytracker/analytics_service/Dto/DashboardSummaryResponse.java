package com.paytracker.analytics_service.Dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DashboardSummaryResponse {
    private BigDecimal totalIncomeThisMonth;
    private BigDecimal totalExpenseThisMonth;
    private BigDecimal monthlySubscriptionCost;   // sum of all active subscriptions, normalized to monthly
    private List<BudgetStatusResponse> budgetStatuses;
    private List<MonthlySpendingResponse> last6MonthsTrend;
}