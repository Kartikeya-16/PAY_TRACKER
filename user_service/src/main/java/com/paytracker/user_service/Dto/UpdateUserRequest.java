package com.paytracker.user_service.Dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateUserRequest {
    private String email;
    private String currency;
}