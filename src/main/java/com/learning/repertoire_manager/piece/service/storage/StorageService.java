package com.learning.repertoire_manager.piece.service.storage;

import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String store(UUID pieceId, MultipartFile file);
}
