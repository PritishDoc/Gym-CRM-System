package com.gymcrm.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // This will resolve to src/main/resources/templates/login.html
    }
}