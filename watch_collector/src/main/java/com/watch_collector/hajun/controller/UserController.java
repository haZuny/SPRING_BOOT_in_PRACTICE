package com.watch_collector.hajun.controller;

import com.watch_collector.hajun.domain.User;
import com.watch_collector.hajun.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/user/new/")
    public String addUser(User user){
        boolean result = userService.join(user);
        if (result){
            return "redirect:/";
        }
        else{
            // 에러 처리 코드 추가
            return "redirect:/";
        }
    }
}
