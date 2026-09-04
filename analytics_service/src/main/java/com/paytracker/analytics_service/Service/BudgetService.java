package com.paytracker.analytics_service.Service;

import com.paytracker.analytics_service.Dto.BudgetRequest;
import com.paytracker.analytics_service.Dto.BudgetResponse;
import com.paytracker.analytics_service.Entity.Budget;
import com.paytracker.analytics_service.Repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetResponse createOrUpdateBudget(BudgetRequest request) {
        Budget budget = budgetRepository
                .findByUserIdAndCategory(request.getUserId(), request.getCategory())
                .orElse(new Budget());

        budget.setUserId(request.getUserId());
        budget.setCategory(request.getCategory());
        budget.setMonthlyLimit(request.getMonthlyLimit());

        Budget saved = budgetRepository.save(budget);
        return BudgetResponse.fromEntity(saved);
    }

    public List<BudgetResponse> getBudgetsByUser(Long userId) {
        List<Budget> budgets = budgetRepository.findByUserId(userId);
        List<BudgetResponse> result = new ArrayList<>();
        for (Budget b : budgets) {
            result.add(BudgetResponse.fromEntity(b));
        }
        return result;
    }

    public void deleteBudget(Long id) {
        if (!budgetRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Budget not found");
        }
        budgetRepository.deleteById(id);
    }
}