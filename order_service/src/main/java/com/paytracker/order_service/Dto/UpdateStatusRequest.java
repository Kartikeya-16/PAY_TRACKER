package com.paytracker.order_service.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateStatusRequest {

    @NotBlank
    private String status;
}