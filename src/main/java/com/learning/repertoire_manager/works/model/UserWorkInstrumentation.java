package com.learning.repertoire_manager.works.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "user_work_instrumentation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWorkInstrumentation {
    @EmbeddedId
    private UserWorkInstrumentationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userWorkId")
    @JoinColumn(name = "user_work_id")
    private UserWork userWork;

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
class UserWorkInstrumentationId implements Serializable {
    private UUID userWorkId;
    private UUID instrumentationId;
    private String rank;
}