package com.meli.store.v1.listener;

import com.meli.store.v1.storeinventory.StoreInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.meli.store.config.RabbitConfig.STORE_INVENTORY_QUEUE;

@Component
public class StoreEventListener {

    private static final Logger logger = LoggerFactory.getLogger(StoreEventListener.class);
    private final StoreInventoryService storeInventoryService;

    public StoreEventListener(StoreInventoryService storeInventoryService) {
        this.storeInventoryService = storeInventoryService;
    }

    @RabbitListener(queues = STORE_INVENTORY_QUEUE)
    public void handleInventoryUpdate(InventoryUpdatedEvent event) {
        logger.info("Store service received inventory update via RabbitMQ: {}", event);

        try {
            storeInventoryService.updateLocalInventoryCache(event);
            logger.info("Successfully updated local cache for store {} product {}",
                    event.getStoreId(), event.getProductId());
        } catch (Exception e) {
            logger.error("Error updating local cache for inventory event: {}", event, e);
        }
    }
}
