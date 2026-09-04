package com.paytracker.product_service.Dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    private String category;

    @NotNull @Min(0)
    private Integer stockQuantity;
}