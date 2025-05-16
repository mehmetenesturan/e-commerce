package com.ecommerce.notification.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderEvent {
    private Long orderId;
    private Long userId;
    private String userEmail;
    private String orderStatus;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private List<OrderItem> items;
    private String shippingAddress;
    private String trackingNumber;

    @Data
    public static class OrderItem {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal unitPrice;
    }
} 