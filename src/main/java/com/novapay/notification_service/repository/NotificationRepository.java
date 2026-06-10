package com.novapay.notification_service.repository;

import com.novapay.notification_service.entity.Notification;
import com.novapay.notification_service.entity.enums.NotificationStatus;
import com.novapay.notification_service.entity.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByUserId(Long userId, Pageable pageable);

    Page<Notification> findByStatus(NotificationStatus status, Pageable pageable);

    Page<Notification> findByNotificationType(NotificationType notificationType, Pageable pageable);
}
