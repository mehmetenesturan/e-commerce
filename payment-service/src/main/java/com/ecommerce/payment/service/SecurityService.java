package com.ecommerce.payment.service;

import com.ecommerce.payment.model.Payment;
import com.ecommerce.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final PaymentRepository paymentRepository;

    public boolean isPaymentOwner(Long paymentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        
        return paymentRepository.findById(paymentId)
                .map(payment -> payment.getUserId().toString().equals(currentUserId))
                .orElse(false);
    }

    public boolean isOrderOwner(Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        
        return paymentRepository.findByOrderId(orderId).stream()
                .anyMatch(payment -> payment.getUserId().toString().equals(currentUserId));
    }

    public boolean isUserSelf(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        
        return userId.toString().equals(currentUserId);
    }
} 