package com.learning.repertoire_manager.works.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "catalog_composers")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogComposer implements Composer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;
    private String shortName;
    private String epoch;
    private LocalDate birth;
    private LocalDate death;
}