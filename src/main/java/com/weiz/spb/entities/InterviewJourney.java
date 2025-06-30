package com.weiz.spb.entities;

import com.weiz.spb.entities.base.AbstractEntity;
import com.weiz.spb.entities.enums.InterviewJourneyStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "interview_journey")
public class InterviewJourney extends AbstractEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "interview_journey_id")
    private String id;

    @Column(name = "job_title", length = 100)
    private String jobTitle;

    @Column(name = "company_name", length = 255)
    private String companyName;

    @Column(name = "application_date")
    private LocalDate applicationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_status", length = 50)
    private InterviewJourneyStatus currentStatus;

    @Column(name = "total_interview_rounds")
    private Integer totalInterviewRounds = 0;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "interviewJourney", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InterviewRound> interviewRounds;
}
