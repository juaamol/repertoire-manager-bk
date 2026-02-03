package com.learning.repertoire_manager.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "pieces")
public class Piece {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String composer;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    @ManyToMany
    @JoinTable(
        name = "piece_techniques",
        joinColumns = @JoinColumn(name = "piece_id"),
        inverseJoinColumns = @JoinColumn(name = "technique_id")
    )
    private List<Technique> techniques;

    // getters & setters
}