package com.weiz.spb.entities;

import com.weiz.spb.entities.base.AbstractEntity;
import com.weiz.spb.entities.enums.AchievementType;
import com.weiz.spb.entities.enums.VisibilityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "achievements")
public class Achievement extends AbstractEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "achievement_id")
    private String id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_achieved")
    private LocalDate dateAchieved;

    @Enumerated(EnumType.STRING)
    @Column(name = "achievement_type", length = 100)
    private AchievementType achievementType;

    @Column(name = "issuing_organization", length = 255)
    private String issuingOrganization;

    @Column(name = "impact_description", columnDefinition = "TEXT")
    private String impactDescription;

    @ElementCollection
    @CollectionTable(name = "achievement_skills", joinColumns = @JoinColumn(name = "achievement_id"))
    @Column(name = "skill")
    private List<String> relatedSkills;

    @Column(name = "proof_link", columnDefinition = "TEXT")
    private String proofLink;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility_status", length = 50)
    private VisibilityStatus visibilityStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;
}
