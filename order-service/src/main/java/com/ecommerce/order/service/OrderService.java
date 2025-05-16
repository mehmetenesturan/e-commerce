package com.ecommerce.order.service;

import com.ecommerce.order.model.Order;
import com.ecommerce.order.model.OrderItem;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.order.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Transactional
    public Order createOrder(Order order) {
        // Order ve OrderItem'lar birlikte kaydedilir
        Order savedOrder = orderRepository.save(order);
        kafkaTemplate.send("order-created", savedOrder);
        return savedOrder;
    }

    @Transactional
    public Optional<Order> updateOrderStatus(Long id, String status) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(status);
                    Order updatedOrder = orderRepository.save(order);
                    kafkaTemplate.send("order-updated", updatedOrder);
                    return updatedOrder;
                });
    }

    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.findById(id).ifPresent(order -> {
            orderRepository.delete(order);
            kafkaTemplate.send("order-deleted", order);
        });
    }
} 