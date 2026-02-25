package com.learning.repertoire_manager.works.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "instrumentation_alternative")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstrumentationAlternative {
    @EmbeddedId
    private InstrumentationAlternativeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("instrumentationSlotId")
    @JoinColumn(name = "instrumentation_slot_id")
    private InstrumentationSlot slot;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("instrumentationId")
    @JoinColumn(name = "instrumentation_id")
    private Instrumentation instrumentation;
}

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
class InstrumentationAlternativeId implements Serializable {
    private UUID instrumentationSlotId;
    private UUID instrumentationId;
    private Integer quantity;
}