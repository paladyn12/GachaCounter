package GachaCounter.controller;

import GachaCounter.config.PrincipalDetails;
import GachaCounter.domain.Star;
import GachaCounter.domain.dto.CharacterSimulateRequest;
import GachaCounter.domain.dto.LightConeSimulateRequest;
import GachaCounter.domain.dto.SimulateResponse;
import GachaCounter.domain.dto.TrackerResponse;
import GachaCounter.domain.entity.Character;
import GachaCounter.domain.entity.LightCone;
import GachaCounter.domain.entity.Pickup;
import GachaCounter.domain.entity.User;
import GachaCounter.repository.LightConeRepository;
import GachaCounter.repository.PickupRepository;
import GachaCounter.service.GachaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
@RequestMapping("/simulator")
@RequiredArgsConstructor
@Slf4j
public class SimulationController {

    private final PickupRepository pickupRepository;
    private final LightConeRepository lightConeRepository;
    private final GachaService gachaService;

    @GetMapping
    public String simulatorPage(Model model) {

        int characterCount = 0;
        int lightConeCount = 0;
        boolean isFull = false;
        model.addAttribute("characterCount", characterCount);
        model.addAttribute("lightConeCount", lightConeCount);
        model.addAttribute("isFull", isFull);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal())) {
            model.addAttribute("isLoggedIn", true);
        } else {
            model.addAttribute("isLoggedIn", false);
        }

        Optional<Pickup> pickupOpt = pickupRepository.findFirstByOrderByStartDateAsc();
        if (!pickupOpt.isEmpty()){
            Pickup pickup = pickupOpt.get();

            List<Character> pickupCharacters = pickup.getPickupCharacters();
            List<LightCone> pickupLightCones = pickup.getPickupLightCones();

            model.addAttribute("pickupCharacters", pickupCharacters);
            model.addAttribute("pickupLightCones", pickupLightCones);
        }

        return "simulator";
    }
    @GetMapping("/userinfo")
    public ResponseEntity<Map<String, Object>> getUserInfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
        Map<String, Object> userInfo = new HashMap<>();
        User user = details.getUser();
        userInfo.put("characterCount", user.getCharacterCount());
        userInfo.put("characterIsFull", user.isCharacterIsFull());
        userInfo.put("lightConeCount", user.getLightConeCount());
        userInfo.put("lightConeIsFull", user.isLightConeIsFull());

        return ResponseEntity.ok(userInfo);
    }
    @GetMapping("/clearCount")
    public ResponseEntity<Map<String, Object>> clearCount() {

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("characterCount", 0);
        userInfo.put("characterIsFull", false);
        userInfo.put("lightConeCount", 0);
        userInfo.put("lightConeIsFull", false);

        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/simulateCharacter")
    @ResponseBody
    public ResponseEntity<?> simulateCharacter(@RequestBody CharacterSimulateRequest request) {
        int firstStack = request.getCharacterCount();

        String name = URLDecoder.decode(request.getImageName(), StandardCharsets.UTF_8);
        name = name.replace(".webp", "");
        request.setImageName(name);

        Character[] characters = gachaService.simulateCharacter(request);
        SimulateResponse[] items = new SimulateResponse[10];
        TrackerResponse[] fiveItems = new TrackerResponse[10];
        for (int i = 0; i < 10; i++) {
            firstStack++;
            if (characters[i] != null) {
                items[i] = new SimulateResponse(characters[i]);
                if (characters[i].getStar().equals(Star.FIVE)) {
                    fiveItems[i] = new TrackerResponse(items[i].getImagePath(), firstStack);
                    firstStack = 0;
                }
            } else {
                items[i] = new SimulateResponse(lightConeRepository.findRandomThreeStarLightCone());
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("characterCount", request.getCharacterCount());
        response.put("characterIsFull", request.isCharacterIsFull());
        response.put("items", items);
        response.put("fiveItems", fiveItems);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/simulateLightCone")
    @ResponseBody
    public ResponseEntity<?> simulateLightCone(@RequestBody LightConeSimulateRequest request) {
        int firstStack = request.getLightConeCount();

        String name = URLDecoder.decode(request.getImageName(), StandardCharsets.UTF_8);
        name = name.replace(".png", "");
        request.setImageName(name);

        LightCone[] lightCones = gachaService.simulateLightCone(request);
        SimulateResponse[] items = new SimulateResponse[10];
        TrackerResponse[] fiveItems = new TrackerResponse[10];
        for (int i = 0; i < 10; i++) {
            firstStack++;
            if (lightCones[i] != null) {
                items[i] = new SimulateResponse(lightCones[i]);
                if (lightCones[i].getStar().equals(Star.FIVE)) {
                    fiveItems[i] = new TrackerResponse(items[i].getImagePath(), firstStack);
                    firstStack = 0;
                }
            } else {
                items[i] = new SimulateResponse(lightConeRepository.findRandomThreeStarLightCone());
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("lightConeCount", request.getLightConeCount());
        response.put("lightConeIsFull", request.isLightConeIsFull());
        response.put("items", items);
        response.put("fiveItems", fiveItems);

        return ResponseEntity.ok(response);
    }
}