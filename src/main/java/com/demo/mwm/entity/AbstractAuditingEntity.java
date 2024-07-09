package com.demo.mwm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

/**
 * Abstract class to automatically record the creation and update timestamps of entities.
 * @param <T> The type of the entity's id.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Abstract method to get the id of the entity.
     * @return The id of the entity.
     */
    public abstract T getId();

    /**
     * The creation timestamp of the entity.
     * Automatically set when the entity is created.
     */
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    /**
     * The last update timestamp of the entity.
     * Automatically set when the entity is updated.
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt = Instant.now();

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
