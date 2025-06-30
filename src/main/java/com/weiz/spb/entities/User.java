package com.weiz.spb.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.weiz.spb.entities.base.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_email", columnList = "email", unique = true)
        }
)
@Entity
public class User extends AbstractEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "user_id")
    private String id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "is_verified")
    private boolean isVerified = false;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Portfolio> portfolios;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InterviewJourney> interviewJourneys;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Token> tokens;
}
