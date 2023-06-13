package com.ecommerce.notificationservice.service;

 import com.ecommerce.clientsservice.notification.NotificationRequest;
 import com.ecommerce.notificationservice.model.Notification;
 import com.ecommerce.notificationservice.repository.NotificationRepository;
 import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void send(NotificationRequest notificationRequest) {
        notificationRepository.save(
                Notification.builder()
                        .userId(notificationRequest.getUserId())
                        .toUserEmail(notificationRequest.getToUserEmail())
                        .sender("Cihan Tech")
                        .message(notificationRequest.getMessage())
                        .sentAt(LocalDateTime.now())
                        .build()
        );
    }
}