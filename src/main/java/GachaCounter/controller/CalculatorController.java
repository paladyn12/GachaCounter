package GachaCounter.controller;

import GachaCounter.config.PrincipalDetails;
import GachaCounter.domain.dto.CalculateRequest;
import GachaCounter.domain.entity.User;
import GachaCounter.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/calculator")
@Slf4j
public class CalculatorController {

    private final CalculatorService calculatorService;

    @GetMapping
    public String calculatorPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal())) {
            PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
            User user = details.getUser();
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("characterCount", user.getCharacterCount());
            model.addAttribute("characterIsFull", user.isCharacterIsFull());
            model.addAttribute("lightConeCount", user.getLightConeCount());
            model.addAttribute("lightConeIsFull", user.isLightConeIsFull());
        } else {
            model.addAttribute("isLoggedIn", false);

        }
        return "calculator";
    }

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@RequestBody CalculateRequest request) {

        log.info("성옥 = {}", request.getSungok());
        log.info("티켓 = {}", request.getTicket());
        log.info("캐릭터 뽑는 수 = {}", request.getCharacters());
        log.info("광추 뽑는 수 = {}", request.getLightCones());
        log.info("캐릭터 스택 = {}", request.getCharacterCount());
        log.info("캐릭터 천장 = {}", request.isCharacterIsFull());
        log.info("광추 스택 = {}", request.getLightConeCount());
        log.info("광추 천장 = {}", request.isLightConeIsFull());

        int result = calculatorService.calculate(request);
        log.info("기대값={}", result);
        int sungok = request.getSungok();
        sungok += request.getTicket()*160;

        log.info("계산된 성옥 값 = {}", sungok);
        log.info("뽑을만 하냐 = {}", sungok >= result*160);
        log.info("성옥 차이 = {}", sungok - result*160);

        Map<String, Object> response = new HashMap<>();
        response.put("result", result);
        response.put("isPossible", sungok >= result*160);
        response.put("diffSungok", Math.abs(sungok - result*160));
        return ResponseEntity.ok(response);
    }
}
