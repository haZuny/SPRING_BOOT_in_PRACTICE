package com.example.jwt.controller;

import com.example.jwt.dto.JoinDto;
import com.example.jwt.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    JoinService joinService;

    @Autowired
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @GetMapping("/join")
    public String joinPage(){
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDto joinDto){
        joinService.joinProcess(joinDto);
        return "redirect:/login";
    }
}
