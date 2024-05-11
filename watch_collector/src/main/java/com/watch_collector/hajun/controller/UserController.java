package com.watch_collector.hajun.controller;

import com.watch_collector.hajun.domain.User;
import com.watch_collector.hajun.domain.Watch;
import com.watch_collector.hajun.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
            /**
             * 에러 처리 코드 추가하기
             */
            return "redirect:/";
        }
    }

    @GetMapping("/user/{userId}/watches/")
    public String userWatchList(@PathVariable String userId, Model model){
        User user = userService.findUser(userId).orElse(null);
        List<Watch> watchList = userService.watchesByUser(user);
        model.addAttribute("watchList", watchList);
        return "watch_list.html";
    }


}
