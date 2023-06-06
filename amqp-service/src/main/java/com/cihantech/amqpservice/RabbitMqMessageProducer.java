package com.cihantech.amqpservice;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class RabbitMqMessageProducer {
    private final AmqpTemplate amqpTemplate;
    public void publish(Object payload,String exchange,String rountingKey){
        log.info("Publishing to {} using routingKey {} , Payload : {}",exchange,rountingKey,payload);
        amqpTemplate.convertAndSend(exchange,rountingKey,payload);
        log.info("Published to {} using routingKey {} , Payload : {}",exchange,rountingKey,payload);
    }
}
