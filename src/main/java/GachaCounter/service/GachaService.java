package GachaCounter.service;

import GachaCounter.domain.Star;
import GachaCounter.domain.dto.CharacterSimulateRequest;
import GachaCounter.domain.dto.LightConeSimulateRequest;
import GachaCounter.domain.entity.Character;
import GachaCounter.domain.entity.LightCone;
import GachaCounter.repository.CharacterRepository;
import GachaCounter.repository.LightConeRepository;
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
    private final LightConeRepository lightConeRepository;

    public Character[] simulateCharacter(CharacterSimulateRequest request) {

        int test_case = 10;
        boolean isFOUR = false;
        Character[] result = new Character[10];

        for (int i = 0; i < 10; i++) {
            log.info("연산 수행={}", request.getCharacterCount());
            if (i == 9 && !isFOUR) {
                result[i] = gachaFOUR_character(request);
            } else {
                Character character = gacha_character(request);
                if (character != null && character.getStar() == Star.FOUR) isFOUR = true;
                result[i] = character;
            }
        }
        return result;
    }

    public Character gacha_character(CharacterSimulateRequest request) {
        Random random = new Random();
        double randomDouble = random.nextDouble();
        double adj = adjstPrbbl(request.getCharacterCount());

        // 5성 획득
        if (randomDouble < 0.006 + adj) {
            return tryGetFiveStar_character(request);
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
    public Character gachaFOUR_character(CharacterSimulateRequest request) { //gachaFOUR도 수정
        Random random = new Random();
        double randomDouble = random.nextDouble();
        double adj = adjstPrbbl(request.getCharacterCount());

        // 5성 획득
        if (randomDouble < 0.006 + adj) {
            return tryGetFiveStar_character(request);
        } // 4성 획득
        else {
            request.setCharacterCount(request.getCharacterCount() + 1);
            return characterRepository.findRandomFourStarCharacter();
        }
    }

    // 5성 획득 시 count, full 값 조정
    private Character tryGetFiveStar_character(CharacterSimulateRequest request) {
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

    public LightCone[] simulateLightCone(LightConeSimulateRequest request) {

        int test_case = 10;
        boolean isFOUR = false;
        LightCone[] result = new LightCone[10];

        for (int i = 0; i < 10; i++) {
            log.info("연산 수행={}", request.getLightConeCount());
            if (i == 9 && !isFOUR) {
                result[i] = gachaFOUR_lightCone(request);
            } else {
                LightCone lightCone = gacha_lightCone(request);
                if (lightCone != null && lightCone.getStar() == Star.FOUR) isFOUR = true;
                result[i] = lightCone;
            }
        }
        return result;
    }

    public LightCone gacha_lightCone(LightConeSimulateRequest request) {
        Random random = new Random();
        double randomDouble = random.nextDouble();
        double adj = adjstPrbbl(request.getLightConeCount());

        // 5성 획득
        if (randomDouble < 0.006 + adj) {
            return tryGetFiveStar_lightCone(request);
        } // 4성 획득
        else if (randomDouble < 0.057) {
            request.setLightConeCount(request.getLightConeCount() + 1);
            return lightConeRepository.findRandomFourStarLightCone();
        } // 3성 획득
        else {
            request.setLightConeCount(request.getLightConeCount() + 1);
            return null;
        }
    }

    // 4성이 안 나왔을 때 가챠
    public LightCone gachaFOUR_lightCone(LightConeSimulateRequest request) { //gachaFOUR도 수정
        Random random = new Random();
        double randomDouble = random.nextDouble();
        double adj = adjstPrbbl(request.getLightConeCount());

        // 5성 획득
        if (randomDouble < 0.006 + adj) {
            return tryGetFiveStar_lightCone(request);
        } // 4성 획득
        else {
            request.setLightConeCount(request.getLightConeCount() + 1);
            return lightConeRepository.findRandomFourStarLightCone();
        }
    }

    // 5성 획득 시 count, full 값 조정
    private LightCone tryGetFiveStar_lightCone(LightConeSimulateRequest request) {
        Random random = new Random();
        if (request.isLightConeIsFull()) {
            Optional<LightCone> pickupLightCone = lightConeRepository.findByName(request.getImageName());
            request.setLightConeCount(0);
            request.setLightConeIsFull(false);
            return pickupLightCone.orElse(null);
        } else if (random.nextDouble() < 0.5) {
            Optional<LightCone> alwaysLightCone = lightConeRepository.findByName(request.getImageName());
            request.setLightConeCount(0);
            return alwaysLightCone.orElse(null);
        } else {
            request.setLightConeIsFull(true);
            request.setLightConeCount(0);
            return lightConeRepository.findRandomFiveStarLightCone();
        }
    }
}
