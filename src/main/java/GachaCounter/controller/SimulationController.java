package GachaCounter.controller;

import GachaCounter.domain.entity.Character;
import GachaCounter.domain.entity.LightCone;
import GachaCounter.domain.entity.Pickup;
import GachaCounter.repository.CharacterRepository;
import GachaCounter.repository.LightConeRepository;
import GachaCounter.repository.PickupRepository;
import GachaCounter.service.CharacterService;
import GachaCounter.service.LightConeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/simulator")
@RequiredArgsConstructor
@Slf4j
public class SimulationController {

    private final CharacterService characterService;
    private final LightConeService lightConeService;
    private final PickupRepository pickupRepository;


    @GetMapping
    public String simulatorPage(Model model) {
        log.info("시뮬레이터 페이지");
        Optional<Pickup> pickupOpt = pickupRepository.findEarliestPickup();
        if (!pickupOpt.isEmpty()){
            Pickup pickup = pickupOpt.get();
            log.info("start date = {}", pickup.getStartDate().toString());
            List<Character> pickupCharacters = pickup.getPickupCharacters();
            for (Character pickupCharacter : pickupCharacters) {
                log.info(pickupCharacter.getName());
            }
            List<LightCone> pickupLightCones = pickup.getPickupLightCones();
            for (LightCone pickupLightCone : pickupLightCones) {
                log.info(pickupLightCone.getName());
            }
        }

        return "simulator";
    }

}
