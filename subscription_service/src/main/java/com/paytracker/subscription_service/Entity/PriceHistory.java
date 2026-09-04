package com.paytracker.subscription_service.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "price_history")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    @JsonBackReference
    private Subscription subscription;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDate effectiveFrom; // the date this price started applying
}