package com.weiz.spb.entities;

import com.weiz.spb.entities.base.AbstractEntity;
import com.weiz.spb.entities.enums.SkillProficiencyLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "skills")
@Entity
public class Skill extends AbstractEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "skill_id")
    private String id;

    @Column(length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "proficiency_level", length = 50)
    private SkillProficiencyLevel proficiencyLevel;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private SkillCategory category;
}
