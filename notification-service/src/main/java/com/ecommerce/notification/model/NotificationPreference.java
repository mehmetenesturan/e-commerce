package com.ecommerce.notification.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notification_preferences")
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private boolean emailEnabled;

    @Column(nullable = false)
    private boolean smsEnabled;

    @Column(nullable = false)
    private boolean pushEnabled;

    @Column(nullable = false)
    private boolean orderNotificationsEnabled;

    @Column(nullable = false)
    private boolean paymentNotificationsEnabled;

    @Column(nullable = false)
    private boolean marketingNotificationsEnabled;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 