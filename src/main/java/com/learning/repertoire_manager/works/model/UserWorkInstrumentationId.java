package com.learning.repertoire_manager.works.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWorkInstrumentationId implements Serializable {
    private UUID workId;
    private UUID instrumentationId;
}