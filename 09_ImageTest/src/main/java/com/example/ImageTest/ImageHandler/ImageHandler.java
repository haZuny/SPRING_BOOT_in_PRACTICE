package com.example.ImageTest.ImageHandler;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class ImageHandler {
    public String save(MultipartFile image) throws IOException {
        String fileName = getOriginName(image);
        String fullPathName = "C:\\Users\\gkwns\\hajunFolder\\SpringBoot\\Spring-Boot-Start\\" +
                "09_ImageTest\\src\\main\\resources\\static\\imgs\\" + fileName;
        String savedName = "imgs\\"+fileName;
        image.transferTo(new File(fullPathName));
        return savedName;
    }

    private String getOriginName(MultipartFile image){
        return image.getOriginalFilename();
    }
    
}
