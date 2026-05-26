package com.nxhu.ecommercebazar.modules.notification.service;

import com.nxhu.ecommercebazar.modules.notification.dto.req.CreateNotificationRequest;
import com.nxhu.ecommercebazar.modules.notification.dto.res.NotificationResponse;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    List<NotificationResponse> findByUserId(UUID userId);
    List<NotificationResponse> findUnreadByUserId(UUID userId);
    long countUnreadByUserId(UUID userId);
    NotificationResponse create(CreateNotificationRequest request);
    void markAsRead(UUID id);
    void markAllAsRead(UUID userId);
}
