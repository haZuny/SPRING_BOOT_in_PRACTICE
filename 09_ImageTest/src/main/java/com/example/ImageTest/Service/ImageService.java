package com.example.ImageTest.Service;

import com.example.ImageTest.ImageHandler.ImageHandler;
import com.example.ImageTest.entity.Image;
import com.example.ImageTest.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    ImageHandler imageHandler = new ImageHandler();

    @Autowired
    ImageRepository imageRepository;

    public boolean saveImage(MultipartFile image) throws IOException {
        String path = imageHandler.save(image);
        Image imageEntity = new Image();
        imageEntity.setPath(path);
        imageRepository.save(imageEntity);
        return true;
    }

    public Optional<String> findOne(){
        List<Image> list = imageRepository.findAll();
        if (list.isEmpty()) return Optional.empty();
        return Optional.of(list.getFirst().getPath());
    }
}
