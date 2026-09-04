package com.paytracker.ledger_service.Controller;

import com.paytracker.ledger_service.Dto.*;
import com.paytracker.ledger_service.Entity.Transaction;
import com.paytracker.ledger_service.Service.LedgerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Ledger Service", description = "Income and expense transaction tracking")
public class LedgerController {

    private final LedgerService ledgerService;

    @PostMapping
    @Operation(summary = "Create a new transaction")
    public ResponseEntity<TransactionResponse> create(@Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ledgerService.createTransaction(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by ID")
    public ResponseEntity<TransactionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ledgerService.getTransactionById(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all transactions for a user")
    public ResponseEntity<List<TransactionResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ledgerService.getTransactionsByUser(userId));
    }

    @GetMapping("/user/{userId}/type/{type}")
    @Operation(summary = "Get transactions by type (INCOME or EXPENSE)")
    public ResponseEntity<List<TransactionResponse>> getByType(
            @PathVariable Long userId, @PathVariable Transaction.TransactionType type) {
        return ResponseEntity.ok(ledgerService.getTransactionsByUserAndType(userId, type));
    }

    @GetMapping("/user/{userId}/category/{category}")
    @Operation(summary = "Get transactions by category")
    public ResponseEntity<List<TransactionResponse>> getByCategory(
            @PathVariable Long userId, @PathVariable String category) {
        return ResponseEntity.ok(ledgerService.getTransactionsByUserAndCategory(userId, category));
    }

    @GetMapping("/user/{userId}/range")
    @Operation(summary = "Get transactions within a date range")
    public ResponseEntity<List<TransactionResponse>> getByDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(ledgerService.getTransactionsByDateRange(userId, start, end));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a transaction")
    public ResponseEntity<TransactionResponse> update(@PathVariable Long id, @Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(ledgerService.updateTransaction(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a transaction")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ledgerService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}