package com.paytracker.analytics_service.Service;

import com.paytracker.analytics_service.Client.LedgerClient;
import com.paytracker.analytics_service.Client.SubscriptionClient;
import com.paytracker.analytics_service.Dto.*;
import com.paytracker.analytics_service.Entity.Budget;
import com.paytracker.analytics_service.Repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final BudgetRepository budgetRepository;
    private final LedgerClient ledgerClient;
    private final SubscriptionClient subscriptionClient;
    private final SpendingCalculator calculator; // the math helper

    // How is one budget category doing this month?
    public BudgetStatusResponse getBudgetStatus(Long userId, String category) {
        Budget budget = budgetRepository.findByUserIdAndCategory(userId, category)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No budget set for this category"));

        LocalDate today = LocalDate.now();
        LocalDate start = today.withDayOfMonth(1);
        LocalDate end = today.withDayOfMonth(today.lengthOfMonth());

        List<TransactionDto> transactions = ledgerClient.getTransactionsByDateRange(userId, start, end);

        BigDecimal spent = calculator.totalExpenseInCategory(transactions, category);
        BigDecimal percentUsed = calculator.percentageUsed(spent, budget.getMonthlyLimit());
        double percentMonthPassed = calculator.percentageOfMonthPassed(today);
        boolean overspending = calculator.isOverspending(percentUsed, percentMonthPassed);

        return BudgetStatusResponse.builder()
                .category(category)
                .monthlyLimit(budget.getMonthlyLimit())
                .spentSoFar(spent)
                .percentageUsed(percentUsed)
                .percentageOfMonthPassed(percentMonthPassed)
                .isOverspending(overspending)
                .build();
    }

    // Same as above, but for every category the user has a budget for
    public List<BudgetStatusResponse> getAllBudgetStatuses(Long userId) {
        List<Budget> budgets = budgetRepository.findByUserId(userId);
        List<BudgetStatusResponse> result = new ArrayList<>();
        for (Budget b : budgets) {
            result.add(getBudgetStatus(userId, b.getCategory()));
        }
        return result;
    }

    // Spending total for each of the last 6 months (for a trend chart)
    public List<MonthlySpendingResponse> getLast6MonthsTrend(Long userId) {
        List<MonthlySpendingResponse> trend = new ArrayList<>();

        for (int i = 5; i >= 0; i--) {
            YearMonth targetMonth = YearMonth.now().minusMonths(i);
            LocalDate start = targetMonth.atDay(1);
            LocalDate end = targetMonth.atEndOfMonth();

            List<TransactionDto> transactions = ledgerClient.getTransactionsByDateRange(userId, start, end);
            BigDecimal total = calculator.totalExpense(transactions);

            trend.add(MonthlySpendingResponse.builder()
                    .month(targetMonth.toString())
                    .totalSpent(total)
                    .build());
        }

        return trend;
    }

    // Adds up active subscriptions, converting yearly ones into their monthly equivalent
    public BigDecimal getMonthlySubscriptionCost(Long userId) {
        List<SubscriptionDto> subscriptions = subscriptionClient.getActiveSubscriptions(userId);

        BigDecimal total = BigDecimal.ZERO;
        for (SubscriptionDto sub : subscriptions) {
            if (sub.getBillingCycle().equals("MONTHLY")) {
                total = total.add(sub.getCurrentPrice());
            } else {
                BigDecimal monthlyEquivalent = sub.getCurrentPrice().divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
                total = total.add(monthlyEquivalent);
            }
        }
        return total;
    }

    // Everything combined — one call for the whole dashboard
    public DashboardSummaryResponse getDashboardSummary(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDate start = today.withDayOfMonth(1);
        LocalDate end = today.withDayOfMonth(today.lengthOfMonth());

        List<TransactionDto> thisMonthTransactions = ledgerClient.getTransactionsByDateRange(userId, start, end);

        BigDecimal income = calculator.totalIncome(thisMonthTransactions);
        BigDecimal expense = calculator.totalExpense(thisMonthTransactions);

        return DashboardSummaryResponse.builder()
                .totalIncomeThisMonth(income)
                .totalExpenseThisMonth(expense)
                .monthlySubscriptionCost(getMonthlySubscriptionCost(userId))
                .budgetStatuses(getAllBudgetStatuses(userId))
                .last6MonthsTrend(getLast6MonthsTrend(userId))
                .build();
    }
}