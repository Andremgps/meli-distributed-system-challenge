package com.meli.store.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {

    public static final String INVENTORY_EXCHANGE = "inventory.exchange";
    public static final String STORE_INVENTORY_QUEUE = "store.inventory.queue";
    public static final String INVENTORY_ROUTING_KEY = "inventory.updated";

    @Bean
    public TopicExchange inventoryExchange() {
        return new TopicExchange(INVENTORY_EXCHANGE);
    }

    @Bean
    public Queue storeInventoryQueue() {
        return QueueBuilder.durable(STORE_INVENTORY_QUEUE).build();
    }

    @Bean
    public Binding storeInventoryBinding() {
        return BindingBuilder
                .bind(storeInventoryQueue())
                .to(inventoryExchange())
                .with(INVENTORY_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}