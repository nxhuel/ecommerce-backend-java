package com.nxhu.ecommercebazar.modules.notification.service.impl;

import com.nxhu.ecommercebazar.modules.notification.dto.req.CreateNotificationRequest;
import com.nxhu.ecommercebazar.modules.notification.dto.res.NotificationResponse;
import com.nxhu.ecommercebazar.modules.notification.persistence.entity.Notification;
import com.nxhu.ecommercebazar.modules.notification.persistence.repository.NotificationRepository;
import com.nxhu.ecommercebazar.modules.notification.service.NotificationService;
import com.nxhu.ecommercebazar.modules.user.persistence.entity.User;
import com.nxhu.ecommercebazar.modules.user.persistence.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> findByUserId(UUID userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> findUnreadByUserId(UUID userId) {
        return notificationRepository.findByUserIdAndReadFalseOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long countUnreadByUserId(UUID userId) {
        return notificationRepository.countByUserIdAndReadFalse(userId);
    }

    @Override
    @Transactional
    public NotificationResponse create(CreateNotificationRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + request.userId()));
        Notification notification = Notification.builder()
                .user(user)
                .title(request.title())
                .message(request.message())
                .read(false)
                .build();
        return toResponse(notificationRepository.save(notification));
    }

    @Override
    @Transactional
    public void markAsRead(UUID id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found: " + id));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void markAllAsRead(UUID userId) {
        List<Notification> unread = notificationRepository.findByUserIdAndReadFalseOrderByCreatedAtDesc(userId);
        unread.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(unread);
    }

    private NotificationResponse toResponse(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getUser().getId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getRead(),
                notification.getCreatedAt(),
                notification.getUpdatedAt()
        );
    }
}
