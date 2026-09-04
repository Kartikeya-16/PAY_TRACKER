package com.paytracker.subscription_service.Dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PriceUpdateRequest {

    @NotNull @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal newPrice;
}