package sapphire.co.kafkaproducer.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sapphire.co.kafkaproducer.entity.OrderEntity;
import sapphire.co.kafkaproducer.enums.OrderStatus;
import sapphire.co.kafkaproducer.events.OrderPlacedEvent;
import sapphire.co.kafkaproducer.producers.OrderPlacedKafkaProducer;
import sapphire.co.kafkaproducer.repository.InventoryRepository;
import sapphire.co.kafkaproducer.repository.OrderRepository;
import sapphire.co.kafkaproducer.repository.ProductRepository;
import sapphire.co.kafkaproducer.repository.UserRepository;
import sapphire.co.kafkaproducer.utils.GenericException;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final OrderPlacedKafkaProducer orderPlacedKafkaProducer;

    public OrderEntity placeOrder(Long userId, Long productId, int quantity) {

        OrderEntity order = new OrderEntity();
        // check user exist
        userRepository.findById(userId).ifPresentOrElse(order::setUser, () -> {
            throw new GenericException("User not found with given Id." + userId);
        });

        // check product in inventory and qty as well
        inventoryRepository.findByProductProductId(productId).ifPresentOrElse(inventoryEntity -> {
            if (inventoryEntity.getAvailableQuantity() < quantity)
                throw new IllegalArgumentException("Insufficient stock.");
        }, () -> {
            throw new GenericException("Product not found with given Id." + productId);
        });

        // check user exist
        productRepository.findById(productId).ifPresentOrElse(order::setProduct, () -> {
            throw new GenericException("No Product Define with given Id." + productId);
        });

        order.setQuantity(quantity);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());

        OrderEntity savedOrder = orderRepository.save(order);

        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(
                savedOrder.getOrderId(),
                userId,
                productId,
                savedOrder.getQuantity(),
                savedOrder.getStatus(),
                savedOrder.getOrderDate()
        );

        orderPlacedKafkaProducer.sendOrderPlacedEvent(orderPlacedEvent);
        return order;
    }

}
