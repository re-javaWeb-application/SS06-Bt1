package com.re.bgvn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/course/list";
    }

    @GetMapping("/consultation")
    public String consultation(Model model) {
        model.addAttribute("activeMenu", "consultation");
        model.addAttribute("pageTitle", "Dang ky tu van");
        return "consultation";
    }
}
