package GachaCounter.repository;

import GachaCounter.domain.Star;
import GachaCounter.domain.entity.LightCone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public interface LightConeRepository extends JpaRepository<LightCone, Long> {
    Optional<LightCone> findByName(String name);

    @Query("SELECT l FROM LightCone l WHERE l.star = :star AND LOWER(l.special) = 'always'")
    List<LightCone> findAllAlwaysFiveLightCone(@Param("star") Star star);

    @Query("SELECT l FROM LightCone l WHERE l.star = :star")
    List<LightCone> findAllByStar(@Param("star") Star star);

    default LightCone findRandomFiveStarLightCone() {
        List<LightCone> fiveStarLightCones = findAllAlwaysFiveLightCone(Star.FIVE);

        if (fiveStarLightCones.isEmpty()) {
            return null; // 또는 적절한 예외처리
        }

        Random random = new Random();
        int randomIndex = random.nextInt(fiveStarLightCones.size());

        return fiveStarLightCones.get(randomIndex);
    }

    default LightCone findRandomFourStarLightCone() {
        List<LightCone> fourStarLightCones = findAllByStar(Star.FOUR);

        if (fourStarLightCones.isEmpty()) {
            return null; // 또는 적절한 예외처리
        }

        Random random = new Random();
        int randomIndex = random.nextInt(fourStarLightCones.size());

        return fourStarLightCones.get(randomIndex);
    }

    // 랜덤으로 THREE star 라이트콘 하나를 선택하는 메서드
    default LightCone findRandomThreeStarLightCone() {
        List<LightCone> threeStarLightCones = findAllByStar(Star.THREE);

        if (threeStarLightCones.isEmpty()) {
            return null; // 또는 적절한 예외처리
        }

        Random random = new Random();
        int randomIndex = random.nextInt(threeStarLightCones.size());

        return threeStarLightCones.get(randomIndex);
    }
}
