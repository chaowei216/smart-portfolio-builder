package com.weiz.spb.entities.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "lastModifiedAt", "createdBy", "lastModifiedBy"},
        allowGetters = true
)
@MappedSuperclass
public abstract class AbstractEntity<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by", length = 36, updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 36)
    private String lastModifiedBy;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public abstract T getId();

    public void softDelete() {
        this.isDeleted = true;
    }
}
