package com.watch_collector.hajun.controller;

import com.watch_collector.hajun.domain.User;
import com.watch_collector.hajun.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model){
        List<User> userList = userService.findAllUser();
        model.addAttribute("userList", userList);
        return "home.html";
    }
}
