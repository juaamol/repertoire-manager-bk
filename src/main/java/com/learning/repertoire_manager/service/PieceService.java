package com.learning.repertoire_manager.service;

import com.learning.repertoire_manager.model.Piece;
import com.learning.repertoire_manager.model.Technique;
import com.learning.repertoire_manager.model.User;
import com.learning.repertoire_manager.repository.PieceRepository;
import com.learning.repertoire_manager.repository.TechniqueRepository;
import com.learning.repertoire_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PieceService {
    private final PieceRepository pieceRepository;
    private final UserRepository userRepository;
    private final TechniqueRepository techniqueRepository;

    @Transactional
    public Piece createPiece(UUID userId, String title, String composer,
                             String difficulty, String status, List<String> techniqueNames) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Technique> techniques = techniqueNames.stream()
                .map(name -> techniqueRepository.findByName(name)
                        .orElseGet(() -> techniqueRepository.save(new Technique(null, name, null))))
                .toList();

        Piece piece = new Piece();
        piece.setUser(user);
        piece.setTitle(title);
        piece.setComposer(composer);
        piece.setDifficulty(Enum.valueOf(com.learning.repertoire_manager.model.Difficulty.class, difficulty));
        piece.setStatus(Enum.valueOf(com.learning.repertoire_manager.model.Status.class, status));
        piece.setTechniques(techniques);

        return pieceRepository.save(piece);
    }

    public List<Piece> getPiecesByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return pieceRepository.findByUser(user);
    }
}
