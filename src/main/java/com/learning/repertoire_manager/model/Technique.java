package com.learning.repertoire_manager.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "techniques")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Technique {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "techniques")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Piece> pieces;
}
