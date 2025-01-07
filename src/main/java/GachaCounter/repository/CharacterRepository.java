package GachaCounter.repository;

import GachaCounter.domain.Star;
import GachaCounter.domain.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    Optional<Character> findByName(String name);


    @Query("SELECT c FROM Character c WHERE c.star = :star AND LOWER(c.special) = 'always'")
    List<Character> findAllAlwaysFiveCharacter(@Param("star") Star star);

    @Query("SELECT c FROM Character c WHERE c.star = :star")
    List<Character> findAllByStar(@Param("star") Star star);

    default Character findRandomFiveStarCharacter() {
        List<Character> fiveStarCharacters = findAllAlwaysFiveCharacter(Star.FIVE);

        if (fiveStarCharacters.isEmpty()) {
            return null; // 또는 적절한 예외처리
        }

        Random random = new Random();
        int randomIndex = random.nextInt(fiveStarCharacters.size());

        return fiveStarCharacters.get(randomIndex);
    }

    default Character findRandomFourStarCharacter() {
        List<Character> fourStarCharacters = findAllByStar(Star.FOUR);

        if (fourStarCharacters.isEmpty()) {
            return null; // 또는 적절한 예외처리
        }

        Random random = new Random();
        int randomIndex = random.nextInt(fourStarCharacters.size());

        return fourStarCharacters.get(randomIndex);
    }
}
