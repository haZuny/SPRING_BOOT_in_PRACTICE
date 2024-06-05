package com.example.ImageTest.controller;

import com.example.ImageTest.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class ImageController {
    @Autowired
    ImageService imageService;

    @GetMapping("/upload")
    public String getUploadPage(){
        return "upload";
    }

    @GetMapping("/view")
    public String getViewPage(Model model){
        Optional<String> imgPath = imageService.findOne();
        if (imgPath.isPresent()){
            model.addAttribute("img", imgPath.get());
        }
        return "view";
    }

    @PostMapping("/save")
    public String postSaveFile(@RequestParam MultipartFile image) throws IOException {
        if(!image.isEmpty()){
            imageService.saveImage(image);
        }
        return "redirect:/view";
    }
}
