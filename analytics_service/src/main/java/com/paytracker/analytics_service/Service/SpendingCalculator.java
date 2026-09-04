package com.paytracker.analytics_service.Service;

import com.paytracker.analytics_service.Dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

// This class does ONLY math — no database, no API calls.
// It takes numbers/lists in, and gives numbers out. Easy to test, easy to explain.
@Component
public class SpendingCalculator {

    // Adds up all EXPENSE transactions that belong to one category.
    public BigDecimal totalExpenseInCategory(List<TransactionDto> transactions, String category) {
        BigDecimal total = BigDecimal.ZERO;
        for (TransactionDto t : transactions) {
            if (t.getType().equals("EXPENSE") && t.getCategory().equalsIgnoreCase(category)) {
                total = total.add(t.getAmount());
            }
        }
        return total;
    }

    // Adds up ALL expense transactions (any category).
    public BigDecimal totalExpense(List<TransactionDto> transactions) {
        BigDecimal total = BigDecimal.ZERO;
        for (TransactionDto t : transactions) {
            if (t.getType().equals("EXPENSE")) {
                total = total.add(t.getAmount());
            }
        }
        return total;
    }

    // Adds up ALL income transactions.
    public BigDecimal totalIncome(List<TransactionDto> transactions) {
        BigDecimal total = BigDecimal.ZERO;
        for (TransactionDto t : transactions) {
            if (t.getType().equals("INCOME")) {
                total = total.add(t.getAmount());
            }
        }
        return total;
    }

    // What % of the budget has been spent? e.g. spent 4000 out of 8000 = 50%
    public BigDecimal percentageUsed(BigDecimal spent, BigDecimal limit) {
        if (limit.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return spent.divide(limit, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
    }

    // What % of the month has gone by? e.g. today is the 10th of a 30-day month = 33.3%
    public double percentageOfMonthPassed(LocalDate today) {
        int dayOfMonth = today.getDayOfMonth();
        int totalDaysInMonth = today.lengthOfMonth();
        return (dayOfMonth * 100.0) / totalDaysInMonth;
    }

    // The actual "spending velocity" idea: are we spending faster than time is passing?
    public boolean isOverspending(BigDecimal percentageUsed, double percentageOfMonthPassed) {
        return percentageUsed.doubleValue() > percentageOfMonthPassed;
    }
}