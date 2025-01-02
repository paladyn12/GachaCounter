package GachaCounter.controller;

import GachaCounter.config.PrincipalDetails;
import GachaCounter.domain.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 로그인 했을 때
        if (authentication != null && authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal())) {

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            model.addAttribute("isLoggedIn", true);
            User user = principalDetails.getUser();

            String character = String.valueOf(user.getCharacterCount());
            if (user.isCharacterIsFull())
                character += " (천장 O)";
            else character += " (천장 X)";
            String lightCone = String.valueOf(user.getLightConeCount());
            if (user.isLightConeIsFull())
                lightCone += " (천장 O)";
            else lightCone += " (천장 X)";
            model.addAttribute("character", character);
            model.addAttribute("lightCone", lightCone);
        } else {
            model.addAttribute("isLoggedIn", false);
        }
        return "home";
    }
}
