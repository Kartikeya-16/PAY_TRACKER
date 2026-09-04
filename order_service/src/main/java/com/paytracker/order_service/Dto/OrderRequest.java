package com.paytracker.order_service.Dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderRequest {

    @NotNull
    private Long userId;

    @NotEmpty
    @Valid
    private List<OrderItemRequest> items;
}