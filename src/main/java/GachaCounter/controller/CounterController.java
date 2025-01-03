package GachaCounter.controller;

import GachaCounter.config.PrincipalDetails;
import GachaCounter.domain.dto.CounterRequest;
import GachaCounter.domain.entity.User;
import GachaCounter.service.CounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/counter")
public class CounterController {

    private final CounterService counterService;

    @PostMapping("/set")
    public String setCounter(@RequestBody CounterRequest counterRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        counterService.setCounter(user, counterRequest);
        return "redirect:/";
    }
}
