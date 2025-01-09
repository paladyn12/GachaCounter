package GachaCounter.controller;

import GachaCounter.config.PrincipalDetails;
import GachaCounter.domain.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calculator")
public class CalculatorController {

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
}
