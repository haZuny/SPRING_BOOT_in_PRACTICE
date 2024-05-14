package com.watch_collector.hajun.controller;


import com.watch_collector.hajun.domain.User;
import com.watch_collector.hajun.domain.Watch;
import com.watch_collector.hajun.service.UserService;
import com.watch_collector.hajun.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class WatchController {
    WatchService watchService;
    UserService userService;

    @Autowired
    public WatchController(WatchService watchService, UserService userService){
        this.watchService = watchService;
        this.userService = userService;
    }

    /**
     * 시계 추가
     * @param userId
     * @param model
     * @param id
     * @param pw
     * @return
     */
    @GetMapping("/{userId}/watch/new/")
    public String addWatch_get(@PathVariable String userId, Model model, @RequestParam String id, @RequestParam String pw){
        model.addAttribute("userId", userId);

        if (userService.checkIdPw(id, pw))
            return "watch_createForm.html";
        else return "redirect:/user/"+userId+"/watches/";
    }

    @PostMapping("/{userId}/watch/new/")
    public String addWatch_post(@PathVariable String userId, Model mvc_model,
                                @RequestParam String model, @RequestParam int caseSize, @RequestParam String movement,
                                @RequestParam int lugToLug, @RequestParam String glass){
        Watch watch = new Watch(userId, model, caseSize, movement, lugToLug, glass, new ArrayList<>());
        Optional<User> user = userService.findUser(userId);
        if (user.isPresent()){
            watchService.addWatch(watch, user.get());
            return "redirect:/user/"+userId+"/watches/";
        }
        return "redirect:/user/"+userId+"/watches/";
    }

}
