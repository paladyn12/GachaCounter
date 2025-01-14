package GachaCounter.repository;

import GachaCounter.domain.entity.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PickupRepository extends JpaRepository<Pickup, Long> {
    @Query("SELECT p FROM Pickup p ORDER BY p.startDate ASC")
    Optional<Pickup> findEarliestPickup();
    List<Pickup> findByEndDateBefore(LocalDateTime endDate);
}
