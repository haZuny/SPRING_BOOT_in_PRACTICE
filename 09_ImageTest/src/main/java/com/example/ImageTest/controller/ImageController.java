package com.example.ImageTest.controller;

import com.example.ImageTest.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
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
            String[] filenames = imgPath.get().split("\\\\");
            model.addAttribute("img", filenames[filenames.length-1]);
        }
        return "view";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource showImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + "C:\\Users\\gkwns\\Hajun\\Spring-Boot-Start\\09_ImageTest\\src\\main\\resources\\static\\imgs\\" + filename);
    }

    @PostMapping("/save")
    public String postSaveFile(@RequestParam MultipartFile image) throws IOException {
        if(!image.isEmpty()){
            imageService.saveImage(image);
        }
        return "redirect:/view";
    }
}
