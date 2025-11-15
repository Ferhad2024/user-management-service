package org.example.usermanagmentservice.Service.RabbitMqService;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMqPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendImageProcessingMessage(Long txtId) {
        rabbitTemplate.convertAndSend("txt.exchange", "txt.routingKey", txtId);
    }
}