package com.cihantech.clientsservice.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("notification-service")
public interface NotificationClient {

    @PostMapping("api/notification")
    void sendNotification(NotificationRequest notificationRequest);
}