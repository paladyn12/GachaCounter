package GachaCounter.controller;


import GachaCounter.domain.entity.Character;
import GachaCounter.domain.entity.LightCone;
import GachaCounter.domain.entity.Pickup;
import GachaCounter.repository.CharacterRepository;
import GachaCounter.repository.LightConeRepository;
import GachaCounter.repository.PickupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pickup")
@RequiredArgsConstructor
@Slf4j
public class PickupController {

    private final PickupRepository pickupRepository;
    private final CharacterRepository characterRepository;
    private final LightConeRepository lightConeRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public Pickup createPickup(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                               String characters,
                               String lightCones) {

        Date endDate = getEndDate(startDate);


        Pickup pickup = new Pickup();
        pickup.setStartDate(startDate);
        pickup.setEndDate(endDate);

        List<Character> characterList = new ArrayList<>();
        List<LightCone> lightConeList = new ArrayList<>();

        String[] splitCharacters = characters.split(",");
        for (String characterName : splitCharacters) {
            log.info(characterName);
            Character character = characterRepository.findByName(characterName)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found: " + characterName));
            characterList.add(character);
        }

        String[] splitLightCones = lightCones.split(",");
        for (String lightConeName : splitLightCones) {
            log.info(lightConeName);
            LightCone lightCone = lightConeRepository.findByName(lightConeName)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "LightCone not found: " + lightConeName));
            lightConeList.add(lightCone);
        }

        pickup.setPickupCharacters(characterList);
        pickup.setPickupLightCones(lightConeList);

        return pickupRepository.save(pickup);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePickup(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {

        List<Pickup> pickupsToDelete = pickupRepository.findByStartDate(startDate);

        pickupRepository.deleteAll(pickupsToDelete);
        return new ResponseEntity<>("Pickup(s) deleted successfully.", HttpStatus.OK);
    }

    private Date getEndDate(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.WEEK_OF_YEAR, 3);
        return calendar.getTime();
    }
}