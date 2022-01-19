package com.brunopbrito31.apilivros.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload/file")
public class UploadController {

    private static final String DIR_TO_UPLOAD = "C:\\spring\\uploads\\";

    @PostMapping
    public String uploadToDirectory(@RequestParam MultipartFile file) throws IOException {

        byte[] bytes = file.getBytes();
        Path path = Paths.get(DIR_TO_UPLOAD+file.getOriginalFilename());
        Files.write(path, bytes);

        return "File, bytes";
    }
    
}
