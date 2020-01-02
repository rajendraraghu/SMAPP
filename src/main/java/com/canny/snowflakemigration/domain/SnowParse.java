package com.canny.snowflakemigration.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A SnowParse.
 */
@Entity
@Table(name = "snow_parse")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SnowParse extends AbstractAuditingEntity implements Serializable {

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

    @Column(name = "source_type")
    private String sourceType;

    @Column(name = "source_path")
    private String sourcePath;

    @Column(name = "last_status")
    private String lastStatus;

    @ManyToOne(optional = false)
    @NotNull
    private SourceConnection sourceConnection;

    @ManyToOne(optional = false)
    @NotNull
    private SnowflakeConnection snowflakeConnection;

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

    public SnowParse name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public SnowParse description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceType() {
        return sourceType;
    }

    public SnowParse sourceType(String sourceType) {
        this.sourceType = sourceType;
        return this;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public SnowParse sourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
        return this;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }
    
    public String getLastStatus() {
        return lastStatus;
    }

    public SnowParse lastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
        return this;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    public SourceConnection getSourceConnection() {
        return sourceConnection;
    }

    public SnowParse sourceConnection(SourceConnection sourceConnection) {
        this.sourceConnection = sourceConnection;
        return this;
    }

    public void setSourceConnection(SourceConnection sourceConnection) {
        this.sourceConnection = sourceConnection;
    }

    public SnowflakeConnection getSnowflakeConnection() {
        return snowflakeConnection;
    }

    public SnowParse snowflakeConnection(SnowflakeConnection snowflakeConnection) {
        this.snowflakeConnection = snowflakeConnection;
        return this;
    }

    public void setSnowflakeConnection(SnowflakeConnection snowflakeConnection) {
        this.snowflakeConnection = snowflakeConnection;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SnowParse)) {
            return false;
        }
        return id != null && id.equals(((SnowParse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SnowParse{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", sourceType='" + getSourceType() + "'" +
            ", lastStatus='" + getLastStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
