package com.learning.repertoire_manager.piece.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

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
}
