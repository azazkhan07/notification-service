package com.novapay.notification_service.dto.request;

import com.novapay.notification_service.entity.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {

    @NotNull(message = "User id is required")
    private Long userId;
    @NotBlank(message = "Recipient is required")
    private String recipient;
    @NotNull(message = "Notification type is required")
    private NotificationType notificationType;
    @NotBlank(message = "Subject is required")
    private String subject;
    @NotBlank(message = "Message is required")
    private String message;
}