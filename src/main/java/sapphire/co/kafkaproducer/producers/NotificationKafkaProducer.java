package sapphire.co.kafkaproducer.producers;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import sapphire.co.kafkaproducer.events.NotificationEvent;

import java.util.concurrent.CompletableFuture;

@Component
@Getter
@Setter
@Slf4j
public class NotificationKafkaProducer {

    @Value("${my.kafka.producer.topics.notification}")
    private String notificationTopic;

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public NotificationKafkaProducer(KafkaTemplate<String, NotificationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(NotificationEvent event) {
        CompletableFuture<SendResult<String, NotificationEvent>> notificationSend = kafkaTemplate.send(notificationTopic, event);

        notificationSend.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("✅ Producer: Notification sent successfully: {}", result.getRecordMetadata());
            } else {
                log.error("❌ Failed to send notification: {}", ex.getMessage(), ex);
            }
        });
    }

}
