package com.learning.repertoire_manager.works.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "catalog_work_instrumentation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CatalogWorkInstrumentation {
    @EmbeddedId
    private CatalogWorkInstrumentationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("workId")
    @JoinColumn(name = "work_id")
    private CatalogWork work;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("instrumentationId")
    @JoinColumn(name = "instrumentation_id")
    private Instrumentation instrumentation;
    private Integer quantity;
}

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
class CatalogWorkInstrumentationId implements Serializable {
    private UUID workId;
    private UUID instrumentationId;
    private String rank;
}