package com.novapay.notification_service.service;

import com.novapay.notification_service.dto.request.NotificationRequest;
import com.novapay.notification_service.dto.response.NotificationResponse;
import com.novapay.notification_service.entity.enums.NotificationStatus;
import com.novapay.notification_service.entity.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    NotificationResponse sendNotification(NotificationRequest request);

    NotificationResponse getNotificationById(Long notificationId);

    Page<NotificationResponse> getAllNotifications(Pageable pageable);

    Page<NotificationResponse> getNotificationsByUserId(Long userId, Pageable pageable);

    Page<NotificationResponse> getNotificationsByStatus(NotificationStatus status, Pageable pageable);

    Page<NotificationResponse> getNotificationsByType(NotificationType notificationType, Pageable pageable);
}
