package com.paytracker.ledger_service.Dto;

import com.paytracker.ledger_service.Entity.Transaction;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TransactionRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Transaction.TransactionType type; // "INCOME" or "EXPENSE"

    @NotNull @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    @NotBlank
    private String category;

    private String paymentMethod;

    private String description;

    private String tags;

    private LocalDate transactionDate;
}