package GachaCounter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/simulator")
public class SimulationController {

    @GetMapping
    public String simulatorPage() {
        return "simulator";
    }
}
