package sapphire.co.kafkaproducer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    PENDING, COMPLETED, CANCELLED
}
