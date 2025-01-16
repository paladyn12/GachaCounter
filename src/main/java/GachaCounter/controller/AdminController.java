package GachaCounter.controller;

import GachaCounter.config.PrincipalDetails;
import GachaCounter.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String adminPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal())) {
            PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
            User user = details.getUser();

            if ("ROLE_ADMIN".equalsIgnoreCase(user.getRole())) { // 대소문자 무시 비교
                return "admin";
            }
        }
        return "redirect:/"; // 권한이 없거나 로그인하지 않은 경우 리다이렉트
    }
}
