package com.example.jwt_self.controller;

import com.example.jwt_self.dto.JoinDto;
import com.example.jwt_self.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MainController {
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @GetMapping("/")
    public String getMain(){
        return "main page";
    }

    @PostMapping("/signin")
    public String postLogin(){
        return "success";
    }

    @PostMapping("/join")
    public String postJoin(@RequestBody JoinDto joinDto){
        if (customUserDetailsService.joinProcess(joinDto))
            return "success";
        else
            return "fail";
    }

    @GetMapping("/onlyuser")
    public String getOnlyuser(){
        return "success";
    }

}
