package com.ecommerce.notificationservice;

import com.ecommerce.amqpservice.RabbitMqMessageProducer;
import com.ecommerce.notificationservice.config.NotificationConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
        scanBasePackages ={
                "com.cihantech.notificationservice",
                "com.cihantech.amqpservice"
}
)
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(
            RabbitMqMessageProducer producer,
            NotificationConfig notificationConfig
    ){
        return args -> {
            producer.publish(
                    "foo",
                    notificationConfig.getInternalExchange(),
                    notificationConfig.getInternalNotificationRoutingKey()
            );
        };
    }
}

