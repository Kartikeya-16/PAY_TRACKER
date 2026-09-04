package com.paytracker.subscription_service.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "subscriptions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String name; // e.g. "Netflix", "Spotify"

    @Column(nullable = false)
    private BigDecimal currentPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillingCycle billingCycle; // MONTHLY or YEARLY

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate nextRenewalDate;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PriceHistory> priceHistory;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = SubscriptionStatus.ACTIVE;
    }

    public enum BillingCycle {
        MONTHLY, YEARLY
    }

    public enum SubscriptionStatus {
        ACTIVE, CANCELLED
    }
}