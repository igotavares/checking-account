package br.com.ibeans.checkingaccount.domain.model.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AggregateRoot<T extends Identity> {

    @EmbeddedId
    private T id;
    @Version
    private Long version;
    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;
    @Column(name = "UPDATED_ON")
    private LocalDateTime updatedOn;

    public AggregateRoot(T id) {
        this.id = id;
    }

    @PrePersist
    protected void onCreate() {
        createdOn = LocalDateTime.now();
        updatedOn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedOn = LocalDateTime.now();
    }

    public String id() {
        return id(id);
    }

    protected String id(Identity identity) {
        if (identity != null) {
            return identity.getId();
        }
        return null;
    }

}
