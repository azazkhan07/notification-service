package com.novapay.notification_service.service.impl;

import com.novapay.notification_service.dto.request.NotificationRequest;
import com.novapay.notification_service.dto.response.NotificationResponse;
import com.novapay.notification_service.entity.Notification;
import com.novapay.notification_service.entity.enums.NotificationStatus;
import com.novapay.notification_service.entity.enums.NotificationType;
import com.novapay.notification_service.exception.ResourceNotFoundException;
import com.novapay.notification_service.mapper.NotificationMapper;
import com.novapay.notification_service.repository.NotificationRepository;
import com.novapay.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    @Transactional
    public NotificationResponse sendNotification(NotificationRequest request) {

        LOGGER.info("Notification request received | userId={} type={} recipient={}",
                request.getUserId(),
                request.getNotificationType(),
                request.getRecipient());

        Notification notification = Notification.builder()
                .userId(request.getUserId())
                .recipient(request.getRecipient())
                .notificationType(request.getNotificationType())
                .subject(request.getSubject())
                .message(request.getMessage())
                .status(NotificationStatus.SENT)
                .build();

        Notification savedNotification = notificationRepository.save(notification);

        LOGGER.info("Notification sent successfully | notificationId={}", savedNotification.getId());

        return notificationMapper.toNotificationResponse(savedNotification);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationResponse getNotificationById(Long notificationId) {

        LOGGER.info("Fetching notification | notificationId={}", notificationId);

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));

        LOGGER.info("Notification fetched successfully | notificationId={}", notification.getId());

        return notificationMapper.toNotificationResponse(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponse> getAllNotifications(Pageable pageable) {

        LOGGER.info("Fetching all notifications | page={} size={}",
                pageable.getPageNumber(),
                pageable.getPageSize());

        Page<Notification> notifications = notificationRepository.findAll(pageable);

        LOGGER.info("Notifications fetched successfully | totalRecords={}", notifications.getTotalElements());

        return notifications.map(notificationMapper::toNotificationResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponse> getNotificationsByUserId(Long userId, Pageable pageable) {

        LOGGER.info("Fetching notifications by userId={} page={} size={}",
                userId,
                pageable.getPageNumber(),
                pageable.getPageSize());

        Page<Notification> notifications = notificationRepository.findByUserId(userId, pageable);

        LOGGER.info("Notifications fetched successfully | userId={} totalRecords={}", userId, notifications.getTotalElements());

        return notifications.map(notificationMapper::toNotificationResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponse> getNotificationsByStatus(NotificationStatus status, Pageable pageable) {

        LOGGER.info("Fetching notifications by status={} page={} size={}",
                status,
                pageable.getPageNumber(),
                pageable.getPageSize());

        Page<Notification> notifications = notificationRepository.findByStatus(status, pageable);

        LOGGER.info("Notifications fetched successfully | status={} totalRecords={}", status, notifications.getTotalElements());

        return notifications.map(notificationMapper::toNotificationResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponse> getNotificationsByType(NotificationType notificationType, Pageable pageable) {

        LOGGER.info("Fetching notifications by type={} page={} size={}",
                notificationType,
                pageable.getPageNumber(),
                pageable.getPageSize());

        Page<Notification> notifications = notificationRepository.findByNotificationType(notificationType, pageable);

        LOGGER.info("Notifications fetched successfully | type={} totalRecords={}", notificationType, notifications.getTotalElements());

        return notifications.map(notificationMapper::toNotificationResponse);
    }
}
