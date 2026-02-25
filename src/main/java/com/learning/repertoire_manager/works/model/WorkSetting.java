package com.learning.repertoire_manager.works.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "work_setting")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id")
    private CatalogWork work;

    @OneToMany(mappedBy = "setting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InstrumentationSlot> slots;
}
