package com.learning.repertoire_manager.works.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "instrumentation_slot")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstrumentationSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_setting_id")
    private WorkSetting setting;

    @OneToMany(mappedBy = "slot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InstrumentationAlternative> alternatives;
}