package com.canny.snowflakemigration.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A SourceConnection.
 */
@Entity
@Table(name = "source_connection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SourceConnection extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 650)
    @Column(name = "description", length = 650)
    private String description;

    @NotNull
    @Column(name = "system", nullable = false)
    private String system;

    @NotNull
    @Size(max = 1200)
    @Column(name = "url", length = 1200, nullable = false)
    private String url;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "database", nullable = false)
    private String database;

    @Column(name = "schema")
    private String schema;

    @Column(name = "valid")
    private Boolean valid;

    // @Column(name = "created_by")
    // private String createdBy;

    // @Column(name = "created_date")
    // private Instant createdDate;

    // @Column(name = "last_modified_by")
    // private String lastModifiedBy;

    // @Column(name = "last_modified_date")
    // private Instant lastModifiedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SourceConnection name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public SourceConnection description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSystem() {
        return system;
    }

    public SourceConnection system(String system) {
        this.system = system;
        return this;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getUrl() {
        return url;
    }

    public SourceConnection url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public SourceConnection username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public SourceConnection password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public SourceConnection database(String database) {
        this.database = database;
        return this;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getSchema() {
        return schema;
    }

    public SourceConnection schema(String schema) {
        this.schema = schema;
        return this;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public Boolean isValid() {
        return valid;
    }

    public SourceConnection valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    // public String getCreatedBy() {
    //     return createdBy;
    // }

    // public SourceConnection createdBy(String createdBy) {
    //     this.createdBy = createdBy;
    //     return this;
    // }

    // public void setCreatedBy(String createdBy) {
    //     this.createdBy = createdBy;
    // }

    // public Instant getCreatedDate() {
    //     return createdDate;
    // }

    // public SourceConnection createdDate(Instant createdDate) {
    //     this.createdDate = createdDate;
    //     return this;
    // }

    // public void setCreatedDate(Instant createdDate) {
    //     this.createdDate = createdDate;
    // }

    // public String getLastModifiedBy() {
    //     return lastModifiedBy;
    // }

    // public SourceConnection lastModifiedBy(String lastModifiedBy) {
    //     this.lastModifiedBy = lastModifiedBy;
    //     return this;
    // }

    // public void setLastModifiedBy(String lastModifiedBy) {
    //     this.lastModifiedBy = lastModifiedBy;
    // }

    // public Instant getLastModifiedDate() {
    //     return lastModifiedDate;
    // }

    // public SourceConnection lastModifiedDate(Instant lastModifiedDate) {
    //     this.lastModifiedDate = lastModifiedDate;
    //     return this;
    // }

    // public void setLastModifiedDate(Instant lastModifiedDate) {
    //     this.lastModifiedDate = lastModifiedDate;
    // }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SourceConnection)) {
            return false;
        }
        return id != null && id.equals(((SourceConnection) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SourceConnection{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", system='" + getSystem() + "'" +
            ", url='" + getUrl() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", database='" + getDatabase() + "'" +
            ", schema='" + getSchema() + "'" +
            ", valid='" + isValid() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
