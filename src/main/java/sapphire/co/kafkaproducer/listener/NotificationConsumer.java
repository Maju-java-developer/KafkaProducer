package sapphire.co.kafkaproducer.listener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import sapphire.co.kafkaproducer.events.NotificationEvent;
import sapphire.co.kafkaproducer.repository.UserRepository;
import sapphire.co.kafkaproducer.services.EmailService;

@Component
@Slf4j
@AllArgsConstructor
public class NotificationConsumer {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "${my.kafka.consumer.topics.notification}", groupId = "notification-group")
    public void sendNotification(NotificationEvent notificationEvent, Acknowledgment ack) {
        userRepository.findById(notificationEvent.getUserId()).ifPresentOrElse(userEntity -> {
            try {
                emailService.sendEmail(userEntity.getEmail(), "Order Placed Confirmation", notificationEvent.getMessage());
                ack.acknowledge();
            } catch (Exception e) {
                log.error("❌ Failed to send email for user ID {}: {}", notificationEvent.getUserId(), e.getMessage());
                throw new RuntimeException("Email send failed", e); // Important: rethrow
            }
        }, () -> {
            log.warn("⚠️ Notification failed: user with ID {} not found", notificationEvent.getUserId());
            throw new RuntimeException("User not found: " + notificationEvent.getUserId());
        });
    }

    @KafkaListener(topics = "${my.kafka.consumer.topics.notification-dlt}", groupId = "dlt-group")
    public void handleDLT(NotificationEvent notificationEvent, Acknowledgment ack) {
        log.info("📥 Received DLT message: {}", notificationEvent);
        userRepository.findById(notificationEvent.getUserId()).ifPresentOrElse(userEntity -> {
            try {
                emailService.sendEmail(userEntity.getEmail(), "Order Placed Confirmation", notificationEvent.getMessage());
                ack.acknowledge();
            } catch (Exception e) {
                log.error("❌ Still failing. Will try again later. Reason: {}", e.getMessage());
                throw new RuntimeException("Email send failed", e); // Important: rethrow
            }
        }, () -> {
            log.error("❌ Still failing. Will try again later. Reason:");
            throw new RuntimeException("User not found: " + notificationEvent.getUserId());
        });
    }

}
