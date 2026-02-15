package com.learning.repertoire_manager.works.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_work_instrumentation")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWorkInstrumentation {
    @EmbeddedId
    private UserWorkInstrumentationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("workId")
    @JoinColumn(name = "work_id")
    private UserWork work;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("instrumentationId")
    @JoinColumn(name = "instrumentation_id")
    private Instrumentation instrumentation;
    private Integer quantity;
}
