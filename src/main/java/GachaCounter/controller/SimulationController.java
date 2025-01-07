package GachaCounter.controller;

import GachaCounter.config.PrincipalDetails;
import GachaCounter.domain.dto.CharacterSimulateRequest;
import GachaCounter.domain.entity.Character;
import GachaCounter.domain.entity.LightCone;
import GachaCounter.domain.entity.Pickup;
import GachaCounter.domain.entity.User;
import GachaCounter.repository.PickupRepository;
import GachaCounter.service.GachaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final GachaService gachaService;

    @GetMapping
    public String simulatorPage(Model model) {
        log.info("시뮬레이터 페이지");

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
        log.info("Received request: {}", request); // 로그 추가

        String name = URLDecoder.decode(request.getImageName(), StandardCharsets.UTF_8);
        name = name.replace(".webp","");
        log.info("name={}", name);
        request.setImageName(name);

        /*
        입력 : 카운트, 천장(O/X) (CharacterSimulateRequest)
        연산 : 10번의 가챠 진행
         - 각 가챠에서 카운트에 의해 나오는 등급이 다름
         - 가챠 진행 시 카운트, 천장 값 조정
        출력 : List<Character> 10개, CharacterSimulateRequest의 값은 연산에서 조정됨
         */
        Character[] characters = gachaService.simulateCharacter(request);
        for (Character character : characters) {
            if (character != null)
                log.info("나온 캐릭터={}", character.getName());
            else
                log.info("3성");
        }
        log.info("{}",request.getCharacterCount());


        Map<String, Object> response = new HashMap<>();
        response.put("characterCount", request.getCharacterCount());
        response.put("characterIsFull", request.isCharacterIsFull());

        return ResponseEntity.ok(response);
    }
}