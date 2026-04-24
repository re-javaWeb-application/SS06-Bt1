package com.re.bgvn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/course/list";
    }

    @GetMapping("/consultation")
    public String consultation() {
        return "consultation";
    }
}
