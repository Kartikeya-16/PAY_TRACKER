package com.paytracker.subscription_service.Dto;

import com.paytracker.subscription_service.Entity.PriceHistory;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PriceHistoryResponse {
    private Long id;
    private BigDecimal price;
    private LocalDate effectiveFrom;

    public static PriceHistoryResponse fromEntity(PriceHistory p) {
        return PriceHistoryResponse.builder()
                .id(p.getId())
                .price(p.getPrice())
                .effectiveFrom(p.getEffectiveFrom())
                .build();
    }
}