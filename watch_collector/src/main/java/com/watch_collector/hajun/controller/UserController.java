package com.watch_collector.hajun.controller;

import com.watch_collector.hajun.domain.User;
import com.watch_collector.hajun.domain.Watch;
import com.watch_collector.hajun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    // 가입
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

    // 시계 목록 반환
    @GetMapping("/user/{userId}/watches/")
    public String userWatchList(@PathVariable String userId, Model model){
        User user = userService.findUser(userId).orElse(null);
        List<Watch> watchList = userService.watchesByUser(user);
        model.addAttribute("watchList", watchList);
        return "watch_list.html";
    }

    // 탈퇴
    @PostMapping("/user/{userId}/delete/")
    public String withdraw(@PathVariable String userId, @RequestParam String id, @RequestParam String pw){
        Optional<User> user = userService.findUser(userId);
        if (user.isPresent() && userId.equals(id) && userService.checkIdPw(id, pw)){
            userService.withdraw(user.get());
            return "redirect:/";
        }
        return "redirect:/user/"+userId+"/watches/";
    }




}
