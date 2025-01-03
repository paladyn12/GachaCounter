package GachaCounter.controller;

import GachaCounter.config.PrincipalDetails;
import GachaCounter.domain.dto.CounterRequest;
import GachaCounter.domain.entity.User;
import GachaCounter.service.CounterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
@RequestMapping("/counter")
public class CounterController {

    private final CounterService counterService;

    @PostMapping("/set")
    public String setCounter(@Valid @RequestBody CounterRequest counterRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 잘못된 개수 입력
            String errorMessage = "[잘못된 개수 입력]\n캐릭터 : (0-89)\n광추 : (0-79)";
            String encodedMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);

            return "redirect:/counter/error?message=" + encodedMessage;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        counterService.setCounter(user, counterRequest);
        return "redirect:/";
    }

    @GetMapping("/error")
    public String counterError(@RequestParam String message, Model model) {
        model.addAttribute("message", message);
        model.addAttribute("nextUrl", "/");
        System.out.println("Redirecting with message: " + message); // 디버깅 로그

        return "printMessage";
    }
}
