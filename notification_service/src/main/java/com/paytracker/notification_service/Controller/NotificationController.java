package com.paytracker.notification_service.Controller;

import com.paytracker.notification_service.Dto.NotificationResponse;
import com.paytracker.notification_service.Repository.NotificationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Service", description = "View sent notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all notifications for a user")
    public ResponseEntity<List<NotificationResponse>> getByUser(@PathVariable Long userId) {
        List<NotificationResponse> result = notificationRepository.findByUserId(userId)
                .stream()
                .map(NotificationResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping
    @Operation(summary = "Get all notifications (for testing/demo)")
    public ResponseEntity<List<NotificationResponse>> getAll() {
        List<NotificationResponse> result = notificationRepository.findAll()
                .stream()
                .map(NotificationResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(result);
    }
}