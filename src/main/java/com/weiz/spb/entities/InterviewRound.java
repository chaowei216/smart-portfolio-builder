package com.weiz.spb.entities;

import com.weiz.spb.entities.base.AbstractEntity;
import com.weiz.spb.entities.enums.InterviewJourneyStatus;
import com.weiz.spb.entities.enums.InterviewType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "interview_rounds")
public class InterviewRound extends AbstractEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "interview_round_id")
    private String id;

    @Column(name = "round_number")
    private Integer roundNumber;

    @Column(name = "interview_date")
    private LocalDateTime interviewDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "interview_type", length = 50)
    private InterviewType interviewType;

    @Column(name = "interviewer_name", length = 100)
    private String interviewerName;

    @Column(name = "interviewer_role", length = 100)
    private String interviewerRole;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private InterviewJourneyStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journey_id", nullable = false)
    private InterviewJourney interviewJourney;

    @OneToOne(mappedBy = "interviewRound", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AIEvaluation aiEvaluation;
}
