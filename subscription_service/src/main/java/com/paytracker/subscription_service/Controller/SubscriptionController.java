package com.paytracker.subscription_service.Controller;

import com.paytracker.subscription_service.Dto.*;
import com.paytracker.subscription_service.Service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscription Service", description = "Recurring subscription and billing cycle tracking")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;



    @PostMapping
    @Operation(summary = "Add a new subscription")
    public ResponseEntity<SubscriptionResponse> create(@Valid @RequestBody SubscriptionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionService.createSubscription(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get subscription by ID")
    public ResponseEntity<SubscriptionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionById(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all subscriptions for a user")
    public ResponseEntity<List<SubscriptionResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionsByUser(userId));
    }

    @GetMapping("/user/{userId}/active")
    @Operation(summary = "Get active subscriptions for a user")
    public ResponseEntity<List<SubscriptionResponse>> getActiveByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionService.getActiveSubscriptionsByUser(userId));
    }

    @GetMapping("/renewing")
    @Operation(summary = "Get subscriptions renewing within a date range (used by Analytics/Notification)")
    public ResponseEntity<List<SubscriptionResponse>> getRenewingBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionsRenewingBetween(start, end));
    }

    @PutMapping("/{id}/price")
    @Operation(summary = "Update a subscription's price (logs price history)")
    public ResponseEntity<SubscriptionResponse> updatePrice(@PathVariable Long id, @Valid @RequestBody PriceUpdateRequest request) {
        return ResponseEntity.ok(subscriptionService.updatePrice(id, request));
    }

    @PutMapping("/{id}/renew")
    @Operation(summary = "Advance the subscription to its next renewal date")
    public ResponseEntity<SubscriptionResponse> renew(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.renewSubscription(id));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel a subscription")
    public ResponseEntity<SubscriptionResponse> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.cancelSubscription(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a subscription")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }
}