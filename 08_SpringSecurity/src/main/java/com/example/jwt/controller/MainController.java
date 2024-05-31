package com.example.jwt.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String painPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String id = authentication.getName();
        String role = authentication.getAuthorities().stream().findAny().get().getAuthority();

        model.addAttribute("id", id);
        model.addAttribute("role", role);

        return "main";
    }
}
