package com.paytracker.subscription_service.Service;

import com.paytracker.subscription_service.Dto.*;
import com.paytracker.subscription_service.Entity.PriceHistory;
import com.paytracker.subscription_service.Entity.Subscription;
import com.paytracker.subscription_service.Repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {




    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionResponse createSubscription(SubscriptionRequest request) {
        Subscription subscription = Subscription.builder()
                .userId(request.getUserId())
                .name(request.getName())
                .currentPrice(request.getCurrentPrice())
                .billingCycle(request.getBillingCycle())
                .startDate(request.getStartDate())
                .nextRenewalDate(request.getNextRenewalDate())
                .build();

        // record the starting price as the first price history entry
        PriceHistory firstPrice = PriceHistory.builder()
                .subscription(subscription)
                .price(request.getCurrentPrice())
                .effectiveFrom(request.getStartDate())
                .build();
        subscription.setPriceHistory(List.of(firstPrice));

        return SubscriptionResponse.fromEntity(subscriptionRepository.save(subscription));
    }

    public SubscriptionResponse getSubscriptionById(Long id) {
        Subscription s = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found"));
        return SubscriptionResponse.fromEntity(s);
    }

    public List<SubscriptionResponse> getSubscriptionsByUser(Long userId) {
        return subscriptionRepository.findByUserId(userId).stream()
                .map(SubscriptionResponse::fromEntity)
                .toList();
    }

    public List<SubscriptionResponse> getActiveSubscriptionsByUser(Long userId) {
        return subscriptionRepository.findByUserIdAndStatus(userId, Subscription.SubscriptionStatus.ACTIVE).stream()
                .map(SubscriptionResponse::fromEntity)
                .toList();
    }

    // used later by Analytics/Notification services to find subscriptions renewing soon
    public List<SubscriptionResponse> getSubscriptionsRenewingBetween(LocalDate start, LocalDate end) {
        return subscriptionRepository.findByNextRenewalDateBetween(start, end).stream()
                .map(SubscriptionResponse::fromEntity)
                .toList();
    }

    public SubscriptionResponse updatePrice(Long id, PriceUpdateRequest request) {
        Subscription s = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found"));

        s.setCurrentPrice(request.getNewPrice());

        PriceHistory newEntry = PriceHistory.builder()
                .subscription(s)
                .price(request.getNewPrice())
                .effectiveFrom(LocalDate.now())
                .build();
        s.getPriceHistory().add(newEntry);

        return SubscriptionResponse.fromEntity(subscriptionRepository.save(s));
    }

    public SubscriptionResponse renewSubscription(Long id) {
        Subscription s = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found"));

        LocalDate next = s.getBillingCycle() == Subscription.BillingCycle.MONTHLY
                ? s.getNextRenewalDate().plusMonths(1)
                : s.getNextRenewalDate().plusYears(1);
        s.setNextRenewalDate(next);

        return SubscriptionResponse.fromEntity(subscriptionRepository.save(s));
    }

    public SubscriptionResponse cancelSubscription(Long id) {
        Subscription s = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found"));
        s.setStatus(Subscription.SubscriptionStatus.CANCELLED);
        return SubscriptionResponse.fromEntity(subscriptionRepository.save(s));
    }

    public void deleteSubscription(Long id) {
        if (!subscriptionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found");
        }
        subscriptionRepository.deleteById(id);
    }
}