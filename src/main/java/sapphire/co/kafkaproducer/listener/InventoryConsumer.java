package sapphire.co.kafkaproducer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryConsumer {
    Logger log = LoggerFactory.getLogger(InventoryConsumer.class);

    @KafkaListener(topics = "${my.kafka.consumer.topics.inventory-update}", groupId = "inventory-group")
    public void updateInventoryStock(String message) {
        System.out.println("InventoryService: Processing Order -> " + message);
        log.info("InventoryService: Processing Order -> {} ", message);
        // Simulate stock reduction
    }

}
