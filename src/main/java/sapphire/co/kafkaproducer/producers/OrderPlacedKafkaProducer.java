package sapphire.co.kafkaproducer.producers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import sapphire.co.kafkaproducer.events.OrderPlacedEvent;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class OrderPlacedKafkaProducer {

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Value("${my.kafka.producer.topics.placed-order}")
    private String placedOrderTopic;

    public OrderPlacedKafkaProducer(KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderPlacedEvent(OrderPlacedEvent event) {
        CompletableFuture<SendResult<String, OrderPlacedEvent>> future = kafkaTemplate.send(placedOrderTopic, event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("✅ OrderPlacedEvent sent successfully: {}", result.getRecordMetadata());
            } else {
                log.error("❌ Failed to send OrderPlacedEvent: {}", ex.getMessage());
            }
        });
    }
}
