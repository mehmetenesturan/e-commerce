package com.ecommerce.notification.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentEvent {
    private Long paymentId;
    private Long orderId;
    private Long userId;
    private String userEmail;
    private String paymentStatus;
    private BigDecimal amount;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private String errorMessage;
    private String transactionId;
} 