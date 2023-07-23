package com.ecommerce.clientsservice.notification;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequest{
       private Long userId;
       private String toUserEmail;
       private  String message;

       public NotificationRequest(String message) {
              this.message = message;
       }
}