package sapphire.co.kafkaproducer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sapphire.co.kafkaproducer.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
