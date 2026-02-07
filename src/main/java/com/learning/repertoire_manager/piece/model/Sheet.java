package com.learning.repertoire_manager.piece.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Table(name = "sheets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sheet {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piece_id", nullable = false, unique = true)
    private Piece piece;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SheetType type;

    private String pdfPath;
    private String pdfFilename;
    private String pdfContentType;

    @OneToMany(
        mappedBy = "sheet",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @OrderBy("pageOrder ASC")
    @Builder.Default
    private List<SheetPage> pages = new ArrayList<>();
}
