package com.paytracker.analytics_service.Controller;

import com.paytracker.analytics_service.Dto.*;
import com.paytracker.analytics_service.Service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics", description = "Spending trends, budget status, and velocity")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/user/{userId}/budget-status")
    @Operation(summary = "Get status of every budget for a user (spent vs. limit)")
    public ResponseEntity<List<BudgetStatusResponse>> getAllBudgetStatuses(@PathVariable Long userId) {
        return ResponseEntity.ok(analyticsService.getAllBudgetStatuses(userId));
    }

    @GetMapping("/user/{userId}/budget-status/{category}")
    @Operation(summary = "Get status of one specific budget category")
    public ResponseEntity<BudgetStatusResponse> getBudgetStatus(@PathVariable Long userId, @PathVariable String category) {
        return ResponseEntity.ok(analyticsService.getBudgetStatus(userId, category));
    }

    @GetMapping("/user/{userId}/trend")
    @Operation(summary = "Get spending trend for the last 6 months")
    public ResponseEntity<List<MonthlySpendingResponse>> getTrend(@PathVariable Long userId) {
        return ResponseEntity.ok(analyticsService.getLast6MonthsTrend(userId));
    }

    @GetMapping("/user/{userId}/subscription-cost")
    @Operation(summary = "Get total monthly cost of all active subscriptions")
    public ResponseEntity<BigDecimal> getSubscriptionCost(@PathVariable Long userId) {
        return ResponseEntity.ok(analyticsService.getMonthlySubscriptionCost(userId));
    }

    @GetMapping("/user/{userId}/dashboard")
    @Operation(summary = "Get the full dashboard summary in one call")
    public ResponseEntity<DashboardSummaryResponse> getDashboard(@PathVariable Long userId) {
        return ResponseEntity.ok(analyticsService.getDashboardSummary(userId));
    }
}