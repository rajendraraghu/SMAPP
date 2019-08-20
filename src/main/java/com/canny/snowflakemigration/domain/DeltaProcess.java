package com.canny.snowflakemigration.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DeltaProcess.
 */
@Entity
@Table(name = "delta_process")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeltaProcess extends AbstractAuditingEntity implements Serializable {

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

    @Column(name = "type")
    private String type;

    @Size(max = 3200)
    @Column(name = "tables_to_migrate", length = 3200)
    private String tablesToMigrate;

    @Size(max = 3200)
    @Column(name = "pk", length = 3200)
    private String Pk;

    @Column(name = "last_status")
    private String lastStatus;

    @Column(name = "selected_all")
    private Boolean selectedAll;

    // @Column(name = "created_by")
    // private String createdBy;

    // @Column(name = "created_date")
    // private Instant createdDate;

    // @Column(name = "last_modified_by")
    // private String lastModifiedBy;

    // @Column(name = "last_modified_date")
    // private Instant lastModifiedDate;

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

    public DeltaProcess name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public DeltaProcess description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public DeltaProcess type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTablesToMigrate() {
        return tablesToMigrate;
    }

    public DeltaProcess tablesToMigrate(String tablesToMigrate) {
        this.tablesToMigrate = tablesToMigrate;
        return this;
    }

    public void setTablesToMigrate(String tablesToMigrate) {
        this.tablesToMigrate = tablesToMigrate;
    }

    public String getPk() {
        return Pk;
    }

    public DeltaProcess Pk(String Pk) {
        this.Pk = Pk;
        return this;
    }

    public void setPk(String Pk) {
        this.Pk = Pk;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public DeltaProcess lastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
        return this;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    public Boolean getSelectedAll() {
        return selectedAll;
    }

    public DeltaProcess selectedAll(Boolean selectedAll) {
        this.selectedAll = selectedAll;
        return this;
    }

    public void setSelecteAll(Boolean selectedAll) {
        this.selectedAll = selectedAll;
    }

    // public String getCreatedBy() {
    //     return createdBy;
    // }

    // public DeltaProcess createdBy(String createdBy) {
    //     this.createdBy = createdBy;
    //     return this;
    // }

    // public void setCreatedBy(String createdBy) {
    //     this.createdBy = createdBy;
    // }

    // public Instant getCreatedDate() {
    //     return createdDate;
    // }

    // public DeltaProcess createdDate(Instant createdDate) {
    //     this.createdDate = createdDate;
    //     return this;
    // }

    // public void setCreatedDate(Instant createdDate) {
    //     this.createdDate = createdDate;
    // }

    // public String getLastModifiedBy() {
    //     return lastModifiedBy;
    // }

    // public DeltaProcess lastModifiedBy(String lastModifiedBy) {
    //     this.lastModifiedBy = lastModifiedBy;
    //     return this;
    // }

    // public void setLastModifiedBy(String lastModifiedBy) {
    //     this.lastModifiedBy = lastModifiedBy;
    // }

    // public Instant getLastModifiedDate() {
    //     return lastModifiedDate;
    // }

    // public DeltaProcess lastModifiedDate(Instant lastModifiedDate) {
    //     this.lastModifiedDate = lastModifiedDate;
    //     return this;
    // }

    // public void setLastModifiedDate(Instant lastModifiedDate) {
    //     this.lastModifiedDate = lastModifiedDate;
    // }

    public SourceConnection getSourceConnection() {
        return sourceConnection;
    }

    public DeltaProcess sourceConnection(SourceConnection sourceConnection) {
        this.sourceConnection = sourceConnection;
        return this;
    }

    public void setSourceConnection(SourceConnection sourceConnection) {
        this.sourceConnection = sourceConnection;
    }

    public SnowflakeConnection getSnowflakeConnection() {
        return snowflakeConnection;
    }

    public DeltaProcess snowflakeConnection(SnowflakeConnection snowflakeConnection) {
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
        if (!(o instanceof DeltaProcess)) {
            return false;
        }
        return id != null && id.equals(((DeltaProcess) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DeltaProcess{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", tablesToMigrate='" + getTablesToMigrate() + "'" +
            ", Pk='" + getPk() + "'" +
            ", lastStatus='" + getLastStatus() + "'" +
            ", selectedAll='" + getSelectedAll() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
