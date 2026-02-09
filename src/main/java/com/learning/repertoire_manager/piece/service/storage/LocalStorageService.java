package com.learning.repertoire_manager.piece.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.MalformedURLException;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LocalStorageService implements StorageService {

    private static final Path ROOT = Path.of("storage");

    @Override
    public String store(UUID pieceId, MultipartFile file) {
        try {
            Path pieceDir = ROOT.resolve(pieceId.toString());
            Files.createDirectories(pieceDir);

            Path target = pieceDir.resolve(file.getOriginalFilename());

            Files.copy(file.getInputStream(), target);

            return target.toString();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to store file", e);
        }
    }

    @Override
    public Resource load(String path) {
        try {
            Path filePath = Paths.get(path);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new IllegalStateException("File not found");
            }

            return resource;
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid file path", e);
        }
    }
}
