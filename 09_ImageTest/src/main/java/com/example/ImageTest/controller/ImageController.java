package com.example.ImageTest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ImageController {
    @GetMapping("/upload")
    public String getUploadPage(){
        return "upload";
    }

    @GetMapping("/view")
    public String getViewPage(){
        return "view";
    }

    @PostMapping("/save")
    public String postSaveFile(){
        return "redirect:/view";
    }
}
