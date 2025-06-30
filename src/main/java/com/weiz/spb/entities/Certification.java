package com.weiz.spb.entities;

import com.weiz.spb.entities.base.AbstractEntity;
import com.weiz.spb.entities.enums.CertificationVerificationStatus;
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
@Table(name = "certifications")
public class Certification extends AbstractEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "certification_id")
    private String id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "issuing_organization", length = 255)
    private String issuingOrganization;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "credential_id", length = 100)
    private String credentialId;

    @Column(name = "credential_url", columnDefinition = "TEXT")
    private String credentialUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    @CollectionTable(name = "certification_skills", joinColumns = @JoinColumn(name = "certification_id"))
    @Column(name = "skill")
    private List<String> skillsCovered;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", length = 50)
    private CertificationVerificationStatus verificationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;
}
