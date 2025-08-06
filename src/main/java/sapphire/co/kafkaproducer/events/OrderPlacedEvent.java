package sapphire.co.kafkaproducer.events;

import lombok.Data;
import lombok.NoArgsConstructor;
import sapphire.co.kafkaproducer.enums.OrderStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderPlacedEvent {
    private Long orderId;
    private Long userId;
    private Long productId;
    private int quantity;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;

    public OrderPlacedEvent(Long orderId, Long userId, Long productId, int quantity, OrderStatus orderStatus, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }
}
