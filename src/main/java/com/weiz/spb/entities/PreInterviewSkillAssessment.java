package com.weiz.spb.entities;

import com.weiz.spb.entities.base.AbstractEntity;
import com.weiz.spb.entities.enums.CareerStageMatch;
import com.weiz.spb.entities.enums.ConfidenceLevel;
import com.weiz.spb.entities.enums.InterviewPerformancePotential;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pre_interview_skill_assessments")
public class PreInterviewSkillAssessment extends AbstractEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "assessment_id")
    private String id;

    @Column(name = "job_title", length = 255, nullable = false)
    private String jobTitle;

    @Column(name = "job_description", columnDefinition = "TEXT")
    private String jobDescription;

    @Column(name = "assessment_date")
    private LocalDateTime assessmentDate;

    @Column(name = "overall_match_percentage", precision = 5, scale = 2)
    private BigDecimal overallMatchPercentage;

    @Enumerated(EnumType.STRING)
    @Column(name = "career_stage_match", length = 50)
    private CareerStageMatch careerStageMatch;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "required_skills", columnDefinition = "jsonb")
    private Map<String, Object> requiredSkills;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "portfolio_skills", columnDefinition = "jsonb")
    private Map<String, Object> portfolioSkills;

    @ElementCollection
    @CollectionTable(name = "critical_skill_gaps", joinColumns = @JoinColumn(name = "assessment_id"))
    @Column(name = "skill_gap")
    private List<String> criticalSkillGaps;

    @ElementCollection
    @CollectionTable(name = "recommended_skill_improvements", joinColumns = @JoinColumn(name = "assessment_id"))
    @Column(name = "improvement")
    private List<String> recommendedSkillImprovements;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "learning_resources", columnDefinition = "jsonb")
    private Map<String, Object> learningResources;

    @Column(name = "industry_average_match", precision = 5, scale = 2)
    private BigDecimal industryAverageMatch;

    @Column(name = "peer_comparison_percentile", precision = 5, scale = 2)
    private BigDecimal peerComparisonPercentile;

    @Enumerated(EnumType.STRING)
    @Column(name = "confidence_level", length = 50)
    private ConfidenceLevel confidenceLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "potential_interview_performance", length = 50)
    private InterviewPerformancePotential potentialInterviewPerformance;

    @Column(name = "strengths_summary", columnDefinition = "TEXT")
    private String strengthsSummary;

    @Column(name = "improvement_areas", columnDefinition = "TEXT")
    private String improvementAreas;

    @Column(name = "interview_preparation_advice", columnDefinition = "TEXT")
    private String interviewPreparationAdvice;

    @Column(name = "ai_model_version", length = 50)
    private String aiModelVersion;

    @Column(name = "confidence_score", precision = 5, scale = 2)
    private BigDecimal confidenceScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;
}
