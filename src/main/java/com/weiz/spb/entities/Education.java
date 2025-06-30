package com.weiz.spb.entities;

import com.weiz.spb.entities.base.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "education")
@Entity
public class Education extends AbstractEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "education_id")
    private String id;

    @Column(name = "institution_name", length = 255)
    private String institutionName;

    @Column(length = 100)
    private String degree;

    @Column(name = "field_of_study", length = 100)
    private String fieldOfStudy;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_current")
    private Boolean isCurrent = false;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(precision = 3, scale = 2)
    private BigDecimal gpa;

    @Column(length = 255)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;
}
