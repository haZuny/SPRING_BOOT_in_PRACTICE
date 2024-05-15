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
        model.addAttribute("state", "ADD");

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

    // 시계 삭제
    @PostMapping("/{userId}/watch/{watchId}/delete/")
    public String deleteWatch(@PathVariable String userId, @PathVariable int watchId, Model mvc_model, @RequestParam String id, @RequestParam String pw){
        Optional<Watch> watch = watchService.findById(watchId);
        if (userService.checkIdPw(id, pw) && watch.isPresent()){
            watchService.deleteWatch(watch.get());
            System.out.println("삭제됨");
        }
        else{

        }
        return "redirect:/user/"+userId+"/watches/";
    }

    // 시계 수정
    @GetMapping("/{userId}watch/{watchId}/update/")
    public String updateWatch_get(@PathVariable String userId, @PathVariable int watchId, Model mvc_moded, @RequestParam String id, @RequestParam String pw){
        Optional<Watch> watch = watchService.findById(watchId);
        if (watch.isPresent() && userId.equals(id) && userService.checkIdPw(id, pw)){
            mvc_moded.addAttribute("watch", watch.get());
            mvc_moded.addAttribute("state", "UPDATE");
            return "watch_createForm.html";
        }
        return "redirect:/user/"+userId+"/watches/";
    }
    @PostMapping("/{userId}watch/{watchId}/update/")
    public String updateWatch_post(@PathVariable String userId, @PathVariable int watchId,
                                @RequestParam String model, @RequestParam int caseSize, @RequestParam String movement,
                                @RequestParam int lugToLug, @RequestParam String glass){

        Optional<Watch> watch = watchService.findById(watchId);

        if (watch.isPresent()){
            Watch watchObj = watch.get();
            watchObj.setModel(model);
            watchObj.setCaseSize(caseSize);
            watchObj.setMovement(movement);
            watchObj.setLugToLug(lugToLug);
            watchObj.setGlass(glass);
            watchService.updateWatch(watchObj);
            return "redirect:/user/"+userId+"/watches/";
        }
        return "redirect:/user/"+userId+"/watches/";
    }


}
