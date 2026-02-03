package com.learning.repertoire_manager.model;

import jakarta.persistence.*;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "techniques")
public class Technique {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "techniques")
    private List<Piece> pieces;

    // getters & setters
}
