package GachaCounter.repository;

import GachaCounter.domain.entity.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PickupRepository extends JpaRepository<Pickup, Long> {
    List<Pickup> findByStartDate(Date startDate);

    Optional<Pickup> findFirstByOrderByStartDateAsc();
    List<Pickup> findByEndDateBefore(LocalDateTime endDate);
}
