package com.learning.repertoire_manager.works.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "instrumentation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instrumentation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(unique = true, nullable = false)
    private String name;
}