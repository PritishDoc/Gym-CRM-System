package com.gymcrm.backend.controller;

import com.gymcrm.backend.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        // Dummy user for now (or fetch from DB if logged in)
        User dummyUser = User.builder()
                .fullname("John Doe")
                .email("john@example.com")
                .build();

        model.addAttribute("user", dummyUser); // Makes ${user} available in Thymeleaf
        return "dashboard"; // Maps to dashboard.html
    }
}