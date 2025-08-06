package sapphire.co.kafkaproducer.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import sapphire.co.kafkaproducer.events.InventoryEvent;

@Component
public class InventoryKafkaProducer {

    @Value("${my.kafka.producer.topics.inventory-update}")
    private String inventoryTopic;

    @Autowired
    private KafkaTemplate<String, InventoryEvent> kafkaTemplate;

    public void sendInventoryUpdate(InventoryEvent event) {
        kafkaTemplate.send(inventoryTopic, event);
    }

}
