// Placez ce code dans un nouveau fichier dans votre package de services
package fr.pelliculum.restapi.user;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file, String username) throws IOException {

        String fileName = username + ".jpeg";

        Path dirPath = Paths.get(uploadDir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath); // Crée le dossier s'il n'existe pas
        }

        String fullPath = dirPath.resolve(fileName).toString();

        Thumbnails.of(file.getInputStream())
                .size(200, 200)
                .outputQuality(0.8)
                .toFile(new File(fullPath));

        return fileName;
    }
}
