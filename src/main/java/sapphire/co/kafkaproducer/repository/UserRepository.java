package sapphire.co.kafkaproducer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sapphire.co.kafkaproducer.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
