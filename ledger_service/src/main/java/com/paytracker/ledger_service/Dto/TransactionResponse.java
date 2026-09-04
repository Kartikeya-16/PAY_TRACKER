package com.paytracker.ledger_service.Dto;

import com.paytracker.ledger_service.Entity.Transaction;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TransactionResponse {
    private Long id;
    private Long userId;
    private String type;
    private BigDecimal amount;
    private String category;
    private String paymentMethod;
    private String description;
    private String tags;
    private LocalDate transactionDate;
    private LocalDateTime createdAt;

    public static TransactionResponse fromEntity(Transaction t) {
        return TransactionResponse.builder()
                .id(t.getId())
                .userId(t.getUserId())
                .type(t.getType().name())
                .amount(t.getAmount())
                .category(t.getCategory())
                .paymentMethod(t.getPaymentMethod())
                .description(t.getDescription())
                .tags(t.getTags())
                .transactionDate(t.getTransactionDate())
                .createdAt(t.getCreatedAt())
                .build();
    }
}