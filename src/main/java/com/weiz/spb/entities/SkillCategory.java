package com.weiz.spb.entities;

import com.weiz.spb.entities.base.AbstractEntity;
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
@Table(name = "skill_categories")
@Entity
public class SkillCategory extends AbstractEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "skill_category_id")
    private String id;

    @Column(length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Skill> skills;
}
