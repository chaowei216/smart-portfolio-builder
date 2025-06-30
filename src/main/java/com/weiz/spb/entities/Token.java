package com.weiz.spb.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.weiz.spb.entities.base.AbstractEntity;
import com.weiz.spb.entities.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token extends AbstractEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "token_id")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", nullable = false)
    private TokenType tokenType;

    @Column(name = "token_type_description", length = 100, nullable = false)
    private String tokenTypeDescription;

    @Column(name = "token", length = 255, nullable = false, unique = true)
    private String tokenValue;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "is_revoked", nullable = false)
    private boolean isRevoked;

    @Column(name = "revoked_at")
    private LocalDateTime revokedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
}
