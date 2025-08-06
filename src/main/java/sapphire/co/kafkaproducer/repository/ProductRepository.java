package sapphire.co.kafkaproducer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sapphire.co.kafkaproducer.entity.ProductEntity;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT pe.name FROM ProductEntity pe where pe.productId =:productId")
    Optional<String> getProductNameByProductId(@Param("productId") long productId);

}
