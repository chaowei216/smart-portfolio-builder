package com.weiz.spb.entities;

import com.weiz.spb.entities.base.AbstractEntity;
import com.weiz.spb.entities.enums.ProjectType;
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
@Table(name = "projects")
public class Project extends AbstractEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "project_id")
    private String id;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_current")
    private Boolean isCurrent = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_type", length = 100)
    private ProjectType projectType;

    @Column(length = 100)
    private String role;

    @ElementCollection
    @CollectionTable(name = "project_technologies", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "technology")
    private List<String> technologies;

    @Column(name = "project_link", columnDefinition = "TEXT")
    private String projectLink;

    @Column(name = "source_code_link", columnDefinition = "TEXT")
    private String sourceCodeLink;

    @ElementCollection
    @CollectionTable(name = "project_achievements", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "achievement")
    private List<String> keyAchievements;

    @ElementCollection
    @CollectionTable(name = "project_skills", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "skill")
    private List<String> skillsDemonstrated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;
}
