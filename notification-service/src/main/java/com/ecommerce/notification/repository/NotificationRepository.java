package com.ecommerce.notification.repository;

import com.ecommerce.notification.model.Notification;
import com.ecommerce.notification.model.NotificationStatus;
import com.ecommerce.notification.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    List<Notification> findByStatus(NotificationStatus status);
    List<Notification> findByType(NotificationType type);
    List<Notification> findByUserIdAndStatus(Long userId, NotificationStatus status);
    List<Notification> findByRecipientEmail(String email);
} 