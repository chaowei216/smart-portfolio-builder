package com.weiz.spb.entities;

import com.weiz.spb.entities.base.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ai_evaluations")
public class AIEvaluation extends AbstractEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "evaluation_id")
    private String id;

    @Column(name = "match_percentage", precision = 5, scale = 2)
    private BigDecimal matchPercentage;

    @Column(name = "strength_analysis", columnDefinition = "TEXT")
    private String strengthAnalysis;

    @Column(name = "improvement_suggestions", columnDefinition = "TEXT")
    private String improvementSuggestions;

    @Column(name = "skill_gap_analysis", columnDefinition = "TEXT")
    private String skillGapAnalysis;

    @Column(name = "overall_recommendation", length = 50)
    private String overallRecommendation;

    @Column(name = "evaluated_at")
    private LocalDateTime evaluatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id")
    private InterviewRound interviewRound;
}
