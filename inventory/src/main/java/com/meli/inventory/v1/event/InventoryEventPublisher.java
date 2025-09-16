package com.meli.inventory.v1.event;

import com.meli.shared.events.InventoryUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.meli.inventory.config.RabbitConfig.INVENTORY_EXCHANGE;
import static com.meli.inventory.config.RabbitConfig.INVENTORY_ROUTING_KEY;

@Service
public class InventoryEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(InventoryEventPublisher.class);
    private final RabbitTemplate rabbitTemplate;

    public InventoryEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishInventoryUpdatedEvent(InventoryUpdatedEvent event) {
        try {
            rabbitTemplate.convertAndSend(INVENTORY_EXCHANGE, INVENTORY_ROUTING_KEY, event);
            logger.info("Published inventory updated event: {}", event);
        } catch (Exception e) {
            logger.error("Failed to publish inventory updated event: {}", event, e);
        }
    }
}
