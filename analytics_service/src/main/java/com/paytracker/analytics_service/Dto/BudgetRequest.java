package com.paytracker.analytics_service.Dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BudgetRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String category;

    @NotNull @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal monthlyLimit;
}