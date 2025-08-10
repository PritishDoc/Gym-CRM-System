package com.gymcrm.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/about")
    public String showAboutPage(){
        return "about";
    }

    @GetMapping("/features")
        public String showFeatuesPage(){
            return "features";
        }
}