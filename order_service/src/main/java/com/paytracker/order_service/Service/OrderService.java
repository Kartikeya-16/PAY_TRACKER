package com.paytracker.order_service.Service;

import com.paytracker.order_service.Client.ProductClient;
import com.paytracker.order_service.Dto.*;
import com.paytracker.order_service.Entity.Order;
import com.paytracker.order_service.Entity.OrderItem;
import com.paytracker.order_service.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public OrderResponse createOrder(OrderRequest request) {
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.getItems()) {
            ProductResponse product;
            try {
                product = productClient.getProductById(itemReq.getProductId());
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Product not found with id: " + itemReq.getProductId());
            }

            if (product.getStockQuantity() < itemReq.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Insufficient stock for product: " + product.getName());
            }

            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            total = total.add(lineTotal);

            OrderItem orderItem = OrderItem.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .quantity(itemReq.getQuantity())
                    .priceAtPurchase(product.getPrice())
                    .build();

            orderItems.add(orderItem);
        }

        Order order = Order.builder()
                .userId(request.getUserId())
                .totalAmount(total)
                .items(orderItems)
                .build();

        orderItems.forEach(item -> item.setOrder(order));

        return OrderResponse.fromEntity(orderRepository.save(order));
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        return OrderResponse.fromEntity(order);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::fromEntity)
                .toList();
    }

    public List<OrderResponse> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderResponse::fromEntity)
                .toList();
    }

    public OrderResponse updateOrderStatus(Long id, UpdateStatusRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        try {
            order.setStatus(Order.OrderStatus.valueOf(request.getStatus().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status value");
        }

        return OrderResponse.fromEntity(orderRepository.save(order));
    }

    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        orderRepository.deleteById(id);
    }
}