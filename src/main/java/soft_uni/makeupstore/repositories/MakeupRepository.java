package soft_uni.makeupstore.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import soft_uni.makeupstore.entities.Makeup;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface MakeupRepository extends JpaRepository<Makeup, Integer> {

    Optional<Makeup> findByBrand(String brand);

    @Transactional()
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Makeup m SET m.price = :price, m.quantity = :quantity WHERE m.id = :id")
    void updatePriceAndQuantityById(@Param("price") BigDecimal price, @Param("quantity") int quantity, @Param("id") int id);
}
