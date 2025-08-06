package sapphire.co.kafkaproducer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sapphire.co.kafkaproducer.events.NotificationEvent;
import sapphire.co.kafkaproducer.events.OrderPlacedEvent;
import sapphire.co.kafkaproducer.producers.NotificationKafkaProducer;
import sapphire.co.kafkaproducer.repository.ProductRepository;

import java.time.LocalDateTime;

@Component
@Slf4j
public class OrderPlacedEventConsumer {

    private final ProductRepository productRepository;
    private final NotificationKafkaProducer notificationKafkaProducer;

    public OrderPlacedEventConsumer(ProductRepository productRepository,
                                    NotificationKafkaProducer notificationKafkaProducer) {
        this.productRepository = productRepository;
        this.notificationKafkaProducer = notificationKafkaProducer;
    }

    @KafkaListener(topics = "${my.kafka.consumer.topics.placed-order}", groupId = "order-group")
    public void consumeOrderPlacedEvent(OrderPlacedEvent event) {
        log.info("📥Received OrderPlacedEvent: {}", event);

        String productName = productRepository.getProductNameByProductId(event.getProductId())
                .orElse("Unknown Product");

        NotificationEvent notification = new NotificationEvent();
        notification.setMessage("Dear customer, your order for " + productName + " has been placed successfully.");
        notification.setDateTime(LocalDateTime.now());
        notification.setUserId(event.getUserId());

        notificationKafkaProducer.sendNotification(notification);
    }
}
