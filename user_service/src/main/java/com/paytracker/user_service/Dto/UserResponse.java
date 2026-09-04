package com.paytracker.user_service.Dto;

import com.paytracker.user_service.Entity.User;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String currency;
    private LocalDateTime createdAt;

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .currency(user.getCurrency())
                .createdAt(user.getCreatedAt())
                .build();
    }
}