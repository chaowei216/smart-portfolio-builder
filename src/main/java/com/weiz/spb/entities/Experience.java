package com.weiz.spb.entities;

import com.weiz.spb.entities.base.AbstractEntity;
import com.weiz.spb.entities.enums.EmploymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "experiences")
@Entity
public class Experience extends AbstractEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "experience_id")
    private String id;

    @Column(name = "company_name", length = 255)
    private String companyName;

    @Column(name = "job_title", length = 100)
    private String jobTitle;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_current")
    private Boolean isCurrent = false;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 255)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", length = 50)
    private EmploymentType employmentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;
}
