package com.learning.repertoire_manager.config;

import com.learning.repertoire_manager.model.*;
import com.learning.repertoire_manager.repositories.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(
            UserRepository userRepo,
            TechniqueRepository techniqueRepo,
            PieceRepository pieceRepo
    ) {
        return args -> {
            // User
            User user = new User();
            user.setEmail("violin@test.com");
            user.setPasswordHash("fake-hash");
            user = userRepo.save(user);

            // Techniques
            Technique vibrato = new Technique();
            vibrato.setName("Vibrato");

            Technique spiccato = new Technique();
            spiccato.setName("Spiccato");

            techniqueRepo.saveAll(List.of(vibrato, spiccato));

            // Piece
            Piece piece = new Piece();
            piece.setUser(user);
            piece.setTitle("Violin Concerto in A minor");
            piece.setComposer("Bach");
            piece.setDifficulty(Difficulty.INTERMEDIATE);
            piece.setStatus(Status.LEARNING);
            piece.setTechniques(List.of(vibrato, spiccato));

            pieceRepo.save(piece);
        };
    }
}