package com.novapay.notification_service.controller;

import com.novapay.notification_service.dto.request.NotificationRequest;
import com.novapay.notification_service.dto.response.NotificationResponse;
import com.novapay.notification_service.entity.enums.NotificationStatus;
import com.novapay.notification_service.entity.enums.NotificationType;
import com.novapay.notification_service.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Notification APIs", description = "Notification management endpoints")
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

    private final NotificationService notificationService;

    @Operation(summary = "Send notification")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Notification sent successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid request")})
    @PostMapping
    public ResponseEntity<NotificationResponse> sendNotification(@Valid @RequestBody NotificationRequest request) {

        LOGGER.info("Notification request received | userId={} type={}",
                request.getUserId(),
                request.getNotificationType());

        NotificationResponse notificationResponse = notificationService.sendNotification(request);

        return ResponseEntity.status(HttpStatus.OK).body(notificationResponse);
    }

    @Operation(summary = "Get notification by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Notification found"),
    @ApiResponse(responseCode = "404", description = "Notification not found")})
    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable Long notificationId) {

        LOGGER.info("Fetching notification | notificationId={}", notificationId);

        return ResponseEntity.ok(notificationService.getNotificationById(notificationId));
    }

    @Operation(summary = "Get all notifications")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Notifications fetched successfully")})
    @GetMapping
    public ResponseEntity<Page<NotificationResponse>> getAllNotifications(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(
                        Sort.Direction.fromString(sortDirection), sortBy));

        return ResponseEntity.ok(notificationService.getAllNotifications(pageable));
    }

    @Operation(summary = "Get notifications by user id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Notifications fetched successfully"),
    @ApiResponse(responseCode = "404", description = "User notifications not found")})
    @GetMapping("/filter/user/{userId}")
    public ResponseEntity<Page<NotificationResponse>> getNotificationsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(
                        Sort.Direction.fromString(sortDirection), sortBy));

        LOGGER.info("Fetching notifications by userId={} page={} size={}", userId, page, size);

        return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId, pageable));
    }

    @Operation(summary = "Get notifications by status")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Notifications fetched successfully"),
    @ApiResponse(responseCode = "404", description = "Notification status not found")})
    @GetMapping("/filter/status/{status}")
    public ResponseEntity<Page<NotificationResponse>> getNotificationsByStatus(
            @PathVariable NotificationStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(
                        Sort.Direction.fromString(sortDirection), sortBy));

        LOGGER.info("Fetching notifications by status={} page={} size={}", status, page, size);

        return ResponseEntity.ok(notificationService.getNotificationsByStatus(status, pageable));
    }

    @Operation(summary = "Get notifications by type")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Notifications fetched successfully"),
    @ApiResponse(responseCode = "404", description = "Notification type not found")})
    @GetMapping("/filter/type/{type}")
    public ResponseEntity<Page<NotificationResponse>> getNotificationsByType(@PathVariable NotificationType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy));

        LOGGER.info("Fetching notifications by type={} page={} size={}", type, page, size);

        return ResponseEntity.ok(notificationService.getNotificationsByType(type, pageable));
    }
}
