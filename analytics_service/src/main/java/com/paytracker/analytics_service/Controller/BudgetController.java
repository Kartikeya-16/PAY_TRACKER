package com.paytracker.analytics_service.Controller;

import com.paytracker.analytics_service.Dto.BudgetRequest;
import com.paytracker.analytics_service.Dto.BudgetResponse;
import com.paytracker.analytics_service.Service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
@Tag(name = "Budget", description = "Create and manage category budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    @Operation(summary = "Create or update a budget for a category")
    public ResponseEntity<BudgetResponse> createOrUpdate(@Valid @RequestBody BudgetRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(budgetService.createOrUpdateBudget(request));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all budgets for a user")
    public ResponseEntity<List<BudgetResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(budgetService.getBudgetsByUser(userId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a budget")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}