package com.learning.repertoire_manager.works.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

import com.learning.repertoire_manager.user.model.User;

@Entity
@Table(name = "user_composers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserComposer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;
    private String shortName;
    private String epoch;
    private LocalDate birth;
    private LocalDate death;
}