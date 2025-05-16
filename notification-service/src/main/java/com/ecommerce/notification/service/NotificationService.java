package com.ecommerce.notification.service;

import com.ecommerce.notification.dto.OrderEvent;
import com.ecommerce.notification.dto.PaymentEvent;
import com.ecommerce.notification.model.Notification;
import com.ecommerce.notification.model.NotificationPreference;
import com.ecommerce.notification.model.NotificationStatus;
import com.ecommerce.notification.model.NotificationType;
import com.ecommerce.notification.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;
    private final EmailTemplateService emailTemplateService;
    private final NotificationPreferenceService notificationPreferenceService;
    private final EmailService emailService;
    private final HtmlEmailTemplateService htmlEmailTemplateService;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
    }

    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getNotificationsByStatus(NotificationStatus status) {
        return notificationRepository.findByStatus(status);
    }

    public List<Notification> getNotificationsByType(NotificationType type) {
        return notificationRepository.findByType(type);
    }

    @Transactional
    public Notification createNotification(Notification notification) {
        NotificationPreference preference = notificationPreferenceService.getPreferenceByUserId(notification.getUserId());
        
        // Check if notification type is enabled for the user
        if (!isNotificationTypeEnabled(notification.getType(), preference)) {
            notification.setStatus(NotificationStatus.DISABLED);
            return notificationRepository.save(notification);
        }

        // Send notification based on user preferences
        if (preference.isEmailEnabled()) {
            sendEmailNotification(notification);
        }
        if (preference.isSmsEnabled()) {
            sendSmsNotification(notification);
        }
        if (preference.isPushEnabled()) {
            sendPushNotification(notification);
        }

        notification.setStatus(NotificationStatus.SENT);
        return notificationRepository.save(notification);
    }

    private boolean isNotificationTypeEnabled(NotificationType type, NotificationPreference preference) {
        return switch (type) {
            case ORDER_CONFIRMATION, ORDER_STATUS_UPDATE -> preference.isOrderNotificationsEnabled();
            case PAYMENT_CONFIRMATION, PAYMENT_FAILURE -> preference.isPaymentNotificationsEnabled();
            case MARKETING -> preference.isMarketingNotificationsEnabled();
            default -> true;
        };
    }

    private void sendEmailNotification(Notification notification) {
        String htmlContent = switch (notification.getType()) {
            case ORDER_CONFIRMATION -> htmlEmailTemplateService.generateOrderConfirmationEmail(notification);
            case ORDER_STATUS_UPDATE -> htmlEmailTemplateService.generateOrderStatusUpdateEmail(notification);
            case PAYMENT_CONFIRMATION -> htmlEmailTemplateService.generatePaymentConfirmationEmail(notification);
            case PAYMENT_FAILURE -> htmlEmailTemplateService.generatePaymentFailureEmail(notification);
            default -> notification.getContent();
        };
        emailService.sendHtmlEmail(notification.getRecipient(), notification.getSubject(), htmlContent);
    }

    private void sendSmsNotification(Notification notification) {
        // TODO: Implement SMS sending logic
    }

    private void sendPushNotification(Notification notification) {
        // TODO: Implement push notification logic
    }

    @Transactional
    public Notification updateNotificationStatus(Long id, NotificationStatus status) {
        Notification notification = getNotificationById(id);
        notification.setStatus(status);
        return notificationRepository.save(notification);
    }

    @Transactional
    public void deleteNotification(Long id) {
        Notification notification = getNotificationById(id);
        notificationRepository.delete(notification);
    }

    @KafkaListener(topics = "order-events", groupId = "notification-service")
    public void handleOrderEvent(String event) {
        // TODO: Implement order event handling
    }

    @KafkaListener(topics = "payment-events", groupId = "notification-service")
    public void handlePaymentEvent(String event) {
        // TODO: Implement payment event handling
    }
} 