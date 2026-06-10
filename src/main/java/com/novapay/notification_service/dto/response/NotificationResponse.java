package com.novapay.notification_service.dto.response;

import com.novapay.notification_service.entity.enums.NotificationStatus;
import com.novapay.notification_service.entity.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Notification response")
public record NotificationResponse(

        @Schema(example = "1")
        Long id,
        @Schema(example = "1001")
        Long userId,
        @Schema(example = "azaz@gmail.com")
        String recipient,
        @Schema(example = "EMAIL")
        NotificationType notificationType,
        @Schema(example = "Payment Successful")
        String subject,
        @Schema(example = "Your payment of ₹500 was successful")
        String message,
        @Schema(example = "SENT")
        NotificationStatus status,
        @Schema(example = "2026-06-10T10:30:45")
        LocalDateTime createdAt
) {}
