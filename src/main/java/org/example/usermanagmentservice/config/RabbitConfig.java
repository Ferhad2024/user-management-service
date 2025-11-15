package org.example.usermanagmentservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RabbitConfig {

    public static final String IMAGE_QUEUE = "txt.queue";
    public static final String IMAGE_DLQ = "txt.queue.dlq";

    @Bean
    Queue imageQueue() {
        return QueueBuilder.durable(IMAGE_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", IMAGE_DLQ)
                .build();
    }

    @Bean
    Queue imageDeadLetterQueue() {
        return QueueBuilder.durable(IMAGE_DLQ).build();
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("txt.exchange");
    }

    @Bean
    Binding binding(Queue imageQueue, DirectExchange exchange) {
        return BindingBuilder.bind(imageQueue).to(exchange).with("txt.routingKey");
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate template = new RetryTemplate();

        FixedBackOffPolicy backOff = new FixedBackOffPolicy();
        backOff.setBackOffPeriod(2000);
        template.setBackOffPolicy(backOff);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(5);
        template.setRetryPolicy(retryPolicy);

        return template;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, RetryTemplate retryTemplate) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setRetryTemplate(retryTemplate);
        return template;
    }
}

