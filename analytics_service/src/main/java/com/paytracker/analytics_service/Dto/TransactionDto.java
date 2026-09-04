package com.paytracker.analytics_service.Dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TransactionDto {
    private Long id;
    private String type; // "INCOME" or "EXPENSE"
    private BigDecimal amount;
    private String category;
    private LocalDate transactionDate;
}