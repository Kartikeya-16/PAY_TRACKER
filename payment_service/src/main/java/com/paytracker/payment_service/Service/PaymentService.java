package com.paytracker.payment_service.Service;

import com.paytracker.payment_service.Client.OrderClient;
import com.paytracker.payment_service.Dto.*;
import com.paytracker.payment_service.Entity.Payment;
import com.paytracker.payment_service.Repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;

    public PaymentResponse processPayment(PaymentRequest request) {
        if (paymentRepository.findByOrderId(request.getOrderId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Payment already exists for this order");
        }

        OrderResponse order;
        try {
            order = orderClient.getOrderById(request.getOrderId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Order not found with id: " + request.getOrderId());
        }

        Payment.PaymentMethod method;
        try {
            method = Payment.PaymentMethod.valueOf(request.getMethod().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid payment method");
        }

        Payment payment = Payment.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .amount(order.getTotalAmount())
                .method(method)
                .status(Payment.PaymentStatus.SUCCESS) // simulated — always succeeds
                .build();

        return PaymentResponse.fromEntity(paymentRepository.save(payment));
    }

    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));
        return PaymentResponse.fromEntity(payment);
    }

    public PaymentResponse getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found for this order"));
        return PaymentResponse.fromEntity(payment);
    }

    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(PaymentResponse::fromEntity)
                .toList();
    }

    public List<PaymentResponse> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(PaymentResponse::fromEntity)
                .toList();
    }

    public PaymentResponse refundPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));

        if (payment.getStatus() != Payment.PaymentStatus.SUCCESS) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only successful payments can be refunded");
        }

        payment.setStatus(Payment.PaymentStatus.REFUNDED);
        return PaymentResponse.fromEntity(paymentRepository.save(payment));
    }
}