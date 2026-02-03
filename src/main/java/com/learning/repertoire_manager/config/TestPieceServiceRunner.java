package com.learning.repertoire_manager.config;

import com.learning.repertoire_manager.model.User;
import com.learning.repertoire_manager.repository.UserRepository;
import com.learning.repertoire_manager.service.PieceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestPieceServiceRunner {

    @Bean
    CommandLineRunner testPieceService(PieceService pieceService, UserRepository userRepo) {
        return args -> {
            User user = userRepo.findAll().get(0);

            pieceService.createPiece(
                    user.getId(),
                    "Violin Concerto in D",
                    "Mozart",
                    "INTERMEDIATE",
                    "LEARNING",
                    java.util.List.of("Vibrato", "Spiccato")
            );

            System.out.println("Test piece created for user: " + user.getEmail());
        };
    }
}

