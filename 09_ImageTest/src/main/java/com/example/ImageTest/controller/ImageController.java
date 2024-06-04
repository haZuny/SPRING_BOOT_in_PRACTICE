package com.example.ImageTest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImageController {
    @GetMapping("/upload")
    public String getUploadPage(){
        return "upload";
    }
}
