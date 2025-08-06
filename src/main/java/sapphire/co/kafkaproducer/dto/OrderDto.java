package sapphire.co.kafkaproducer.dto;

import lombok.Data;

@Data
public class OrderDto {
    private Long productId;
    private int quantity;
    private Long userId;
    // Getters and setters
}
