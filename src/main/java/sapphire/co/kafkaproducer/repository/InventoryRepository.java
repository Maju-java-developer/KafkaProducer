package sapphire.co.kafkaproducer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sapphire.co.kafkaproducer.entity.InventoryEntity;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    Optional<InventoryEntity> findByProductProductId(Long product);
}
