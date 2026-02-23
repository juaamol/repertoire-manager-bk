package com.learning.repertoire_manager.works.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "instrumentation")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instrumentation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Instrumentation parent;

    @OneToMany(mappedBy = "parent")
    private List<Instrumentation> children;

    @Column(nullable = false)
    private Integer level;
}