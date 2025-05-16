package com.ecommerce.notification.service;

import com.ecommerce.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final NotificationRepository notificationRepository;

    public boolean isNotificationOwner(Long notificationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        
        return notificationRepository.findById(notificationId)
                .map(notification -> notification.getUserId().toString().equals(currentUserId))
                .orElse(false);
    }

    public boolean isUserSelf(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        
        return userId.toString().equals(currentUserId);
    }
} 