package com.learning.repertoire_manager.works.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "sheet_pages")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SheetPage {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sheet_id", nullable = false)
    private Sheet sheet;

    @Column(nullable = false)
    private int pageOrder;

    @Column(nullable = false)
    private String imagePath;

    private String originalFilename;
    private String contentType;
}
