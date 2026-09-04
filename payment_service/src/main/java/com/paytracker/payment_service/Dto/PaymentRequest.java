package com.paytracker.payment_service.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PaymentRequest {

    @NotNull
    private Long orderId;

    @NotBlank
    private String method; // UPI, CARD, NETBANKING, WALLET, CASH
}