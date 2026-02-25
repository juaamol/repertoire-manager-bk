package com.learning.repertoire_manager.works.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "catalog_number")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CatalogNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id")
    private CatalogWork work;
    private String displayValue;
}