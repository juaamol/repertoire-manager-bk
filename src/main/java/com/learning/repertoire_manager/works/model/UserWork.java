package com.learning.repertoire_manager.works.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.learning.repertoire_manager.user.model.User;

@Entity
@Table(name = "user_work")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWork {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_composer_id")
    private CatalogComposer catalogComposer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_composer_id")
    private UserComposer userComposer;

    @Column(nullable = false)
    private String title;
    private String classification;
    private String notes;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Instant createdAt;
    private Instant updatedAt;

    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserWorkInstrumentation> instrumentations;

    @ManyToMany
    @JoinTable(name = "user_work_techniques", joinColumns = @JoinColumn(name = "work_id"), inverseJoinColumns = @JoinColumn(name = "technique_id"))
    private List<Technique> techniques;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}