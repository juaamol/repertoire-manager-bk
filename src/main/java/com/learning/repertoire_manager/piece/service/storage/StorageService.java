package com.learning.repertoire_manager.piece.service.storage;

import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

public interface StorageService {
    String store(UUID pieceId, MultipartFile file);
    Resource load(String path);
}
