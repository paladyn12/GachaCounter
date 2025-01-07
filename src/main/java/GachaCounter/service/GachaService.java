package GachaCounter.service;

import GachaCounter.domain.Star;
import GachaCounter.domain.dto.CharacterSimulateRequest;
import GachaCounter.domain.entity.Character;
import GachaCounter.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class GachaService {

    private final CharacterRepository characterRepository;

    public Character[] simulateCharacter(CharacterSimulateRequest request) {

        int test_case = 10;
        boolean isFOUR = false;
        Character[] result = new Character[10];

        for (int i = 0; i < 10; i++) {
            log.info("연산 수행={}", request.getCharacterCount());
            if (i == 9 && !isFOUR) {
                result[i] = gachaFOUR(request);
            } else {
                Character character = gacha(request);
                if (character != null && character.getStar() == Star.FOUR) isFOUR = true;
                result[i] = character;
            }
        }
        return result;
    }

    public Character gacha(CharacterSimulateRequest request) {
        Random random = new Random();
        double randomDouble = random.nextDouble();
        double adj = adjstPrbbl(request.getCharacterCount());

        // 5성 획득
        if (randomDouble < 0.006 + adj) {
            return tryGetFiveStar(request);
        } // 4성 획득
        else if (randomDouble < 0.057) {
            request.setCharacterCount(request.getCharacterCount() + 1);
            return characterRepository.findRandomFourStarCharacter();
        } // 3성 획득
        else {
            request.setCharacterCount(request.getCharacterCount() + 1);
            return null;
        }
    }

    // 4성이 안 나왔을 때 가챠
    public Character gachaFOUR(CharacterSimulateRequest request) { //gachaFOUR도 수정
        Random random = new Random();
        double randomDouble = random.nextDouble();
        double adj = adjstPrbbl(request.getCharacterCount());

        // 5성 획득
        if (randomDouble < 0.006 + adj) {
            return tryGetFiveStar(request);
        } // 4성 획득
        else {
            request.setCharacterCount(request.getCharacterCount() + 1);
            return characterRepository.findRandomFourStarCharacter();
        }
    }

    // 5성 획득 시 count, full 값 조정
    private Character tryGetFiveStar(CharacterSimulateRequest request) {
        Random random = new Random();
        if (request.isCharacterIsFull()) {
            Optional<Character> pickupCharacter = characterRepository.findByName(request.getImageName());
            request.setCharacterCount(0);
            request.setCharacterIsFull(false);
            return pickupCharacter.orElse(null);
        } else if (random.nextDouble() < 0.5) {
            Optional<Character> alwaysCharacter = characterRepository.findByName(request.getImageName());
            request.setCharacterCount(0);
            return alwaysCharacter.orElse(null);
        } else {
            request.setCharacterIsFull(true);
            request.setCharacterCount(0);
            return characterRepository.findRandomFiveStarCharacter();
        }
    }

    private double adjstPrbbl(int count) {
        if (count <= 73) return 0.0;
        else return Math.min((count - 73) * 0.06, 1.0); // 6%씩 증가, 최대 100% 제한
    }
}
