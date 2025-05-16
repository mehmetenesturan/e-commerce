package com.ecommerce.notification.controller;

import com.ecommerce.notification.model.NotificationPreference;
import com.ecommerce.notification.service.NotificationPreferenceService;
import com.ecommerce.notification.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification-preferences")
@RequiredArgsConstructor
public class NotificationPreferenceController {
    private final NotificationPreferenceService notificationPreferenceService;
    private final SecurityService securityService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<NotificationPreference> getMyPreferences() {
        Long userId = securityService.getCurrentUserId();
        return ResponseEntity.ok(notificationPreferenceService.getPreferenceByUserId(userId));
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<NotificationPreference> updateMyPreferences(@RequestBody NotificationPreference preference) {
        Long userId = securityService.getCurrentUserId();
        return ResponseEntity.ok(notificationPreferenceService.updatePreference(userId, preference));
    }

    @DeleteMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteMyPreferences() {
        Long userId = securityService.getCurrentUserId();
        notificationPreferenceService.deletePreference(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NotificationPreference> getUserPreferences(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationPreferenceService.getPreferenceByUserId(userId));
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NotificationPreference> updateUserPreferences(
            @PathVariable Long userId,
            @RequestBody NotificationPreference preference) {
        return ResponseEntity.ok(notificationPreferenceService.updatePreference(userId, preference));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUserPreferences(@PathVariable Long userId) {
        notificationPreferenceService.deletePreference(userId);
        return ResponseEntity.noContent().build();
    }
} 