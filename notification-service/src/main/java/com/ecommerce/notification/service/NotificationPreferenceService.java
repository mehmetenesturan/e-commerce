package com.ecommerce.notification.service;

import com.ecommerce.notification.model.NotificationPreference;
import com.ecommerce.notification.repository.NotificationPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationPreferenceService {
    private final NotificationPreferenceRepository notificationPreferenceRepository;

    @Transactional(readOnly = true)
    public NotificationPreference getPreferenceByUserId(Long userId) {
        return notificationPreferenceRepository.findByUserId(userId)
                .orElseGet(() -> createDefaultPreference(userId));
    }

    @Transactional
    public NotificationPreference updatePreference(Long userId, NotificationPreference preference) {
        NotificationPreference existingPreference = notificationPreferenceRepository.findByUserId(userId)
                .orElseGet(() -> createDefaultPreference(userId));

        existingPreference.setEmailEnabled(preference.isEmailEnabled());
        existingPreference.setSmsEnabled(preference.isSmsEnabled());
        existingPreference.setPushEnabled(preference.isPushEnabled());
        existingPreference.setOrderNotificationsEnabled(preference.isOrderNotificationsEnabled());
        existingPreference.setPaymentNotificationsEnabled(preference.isPaymentNotificationsEnabled());
        existingPreference.setMarketingNotificationsEnabled(preference.isMarketingNotificationsEnabled());

        return notificationPreferenceRepository.save(existingPreference);
    }

    @Transactional
    public NotificationPreference createDefaultPreference(Long userId) {
        NotificationPreference preference = new NotificationPreference();
        preference.setUserId(userId);
        preference.setEmailEnabled(true);
        preference.setSmsEnabled(false);
        preference.setPushEnabled(false);
        preference.setOrderNotificationsEnabled(true);
        preference.setPaymentNotificationsEnabled(true);
        preference.setMarketingNotificationsEnabled(false);
        return notificationPreferenceRepository.save(preference);
    }

    @Transactional
    public void deletePreference(Long userId) {
        notificationPreferenceRepository.findByUserId(userId)
                .ifPresent(notificationPreferenceRepository::delete);
    }
} 