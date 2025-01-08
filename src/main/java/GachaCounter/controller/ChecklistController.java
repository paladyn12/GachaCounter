package GachaCounter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/checklist")
public class ChecklistController {

    @GetMapping
    public String checklistPage() {
        return "checklist";
    }
}
