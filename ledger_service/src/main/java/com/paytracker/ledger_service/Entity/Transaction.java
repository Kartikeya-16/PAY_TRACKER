package com.paytracker.ledger_service.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId; // which user this belongs to — just an ID, no direct link to User Service's table

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type; // INCOME or EXPENSE

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String category; // e.g. "Food", "Salary", "Transport"

    private String paymentMethod; // e.g. "UPI", "Cash", "Card"

    private String description;

    private String tags; // simple comma-separated tags for now, e.g. "college,weekend"

    @Column(nullable = false)
    private LocalDate transactionDate; // the date the money moved (user-entered)

    @Column(updatable = false)
    private LocalDateTime createdAt; // when this record was created in our system

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.transactionDate == null) this.transactionDate = LocalDate.now();
    }

    public enum TransactionType {
        INCOME, EXPENSE
    }
}