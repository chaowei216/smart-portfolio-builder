package com.weiz.spb.entities;

import com.weiz.spb.entities.base.AbstractEntity;
import com.weiz.spb.entities.enums.VisibilityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "portfolios")
@Entity
public class Portfolio extends AbstractEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "portfolio_id")
    private String id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "career_objective", columnDefinition = "TEXT")
    private String careerObjective;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(name = "visibility_status", length = 20)
    @Enumerated(EnumType.STRING)
    private VisibilityStatus visibilityStatus = VisibilityStatus.PRIVATE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Skill> skills;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Education> educations;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Language> languages;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Certification> certifications;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Project> projects;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Achievement> achievements;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PreInterviewSkillAssessment> preInterviewAssessments;
}
