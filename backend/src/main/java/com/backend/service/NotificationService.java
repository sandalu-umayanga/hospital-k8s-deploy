package com.backend.service;

import com.backend.dto.NotificationDto;
import com.backend.entity.Notification;
import com.backend.repo.NotificationRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;
    @Autowired
    private ModelMapper modelMapper;

    public void createNotification(int userId, String role, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setUserRole(role);
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());
        notification.setRead(false);
        notificationRepo.save(notification);
    }

    public List<NotificationDto> getNotifications(int userId, String role) {
        return notificationRepo.findByUserIdAndRole(userId, role).stream()
                .map(n -> modelMapper.map(n, NotificationDto.class))
                .collect(Collectors.toList());
    }

    public void markAsRead(int notificationId) {
        Notification n = notificationRepo.findById(notificationId).orElse(null);
        if (n != null) {
            n.setRead(true);
            notificationRepo.save(n);
        }
    }
}
