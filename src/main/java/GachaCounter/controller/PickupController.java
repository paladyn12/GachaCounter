package GachaCounter.controller;


import GachaCounter.domain.entity.Character;
import GachaCounter.domain.entity.LightCone;
import GachaCounter.domain.entity.Pickup;
import GachaCounter.repository.CharacterRepository;
import GachaCounter.repository.LightConeRepository;
import GachaCounter.repository.PickupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/pickup")
@RequiredArgsConstructor
public class PickupController {

    private final PickupRepository pickupRepository;
    private final CharacterRepository characterRepository;
    private final LightConeRepository lightConeRepository;

    @GetMapping("/create")
    public Pickup createPickup(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                               String characters,
                               String lightCones) {

        Date endDate = getEndDate(startDate);


        Pickup pickup = new Pickup();
        pickup.setStartDate(startDate);
        pickup.setEndDate(endDate);

        ArrayList<Character> characterList = (ArrayList<Character>) pickup.getPickupCharacters();
        ArrayList<LightCone> lightConeList = (ArrayList<LightCone>) pickup.getPickupLightCones();
        String[] split = characters.split(",");
        for (String s : split) {
            Character character = characterRepository.getReferenceById(characterRepository.findByName(s).get().getId());
            characterList.add(character);
        }
        split = lightCones.split(",");
        for (String s : split) {
            LightCone lightCone = lightConeRepository.getReferenceById(lightConeRepository.findByName(s).get().getId());
            lightConeList.add(lightCone);
        }

        pickup.setPickupCharacters(characterList);
        pickup.setPickupLightCones(lightConeList);

        return pickupRepository.save(pickup);
    }

    private Date getEndDate(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.WEEK_OF_YEAR, 3);
        return calendar.getTime();
    }
}