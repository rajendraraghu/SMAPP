package com.canny.snowflakemigration.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A SnowDDLProcessStatus.
 */
@Entity
@Table(name = "snow_ddl_process_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SnowDDLProcessStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long batchId;

    @NotNull
    @Column(name = "process_id", nullable = false)
    private Long processId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "run_by")
    private String runBy;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "total_objects")
    private Long totalObjects;

    @Column(name = "success_objects")
    private Long successObjects;

    @Column(name = "error_objects")
    private Long errorObjects;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Long getProcessId() {
        return processId;
    }

    public SnowDDLProcessStatus processId(Long processId) {
        this.processId = processId;
        return this;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getName() {
        return name;
    }

    public SnowDDLProcessStatus name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRunBy() {
        return runBy;
    }

    public SnowDDLProcessStatus runBy(String runBy) {
        this.runBy = runBy;
        return this;
    }

    public void setRunBy(String runBy) {
        this.runBy = runBy;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public SnowDDLProcessStatus startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }
    
    public Instant getEndTime() {
        return endTime;
    }

    public SnowDDLProcessStatus endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Long getTotalObjects() {
        return totalObjects;
    }

    public SnowDDLProcessStatus totalObjects(Long totalObjects) {
        this.totalObjects = totalObjects;
        return this;
    }

    public void setTotalObjects(Long totalObjects) {
        this.totalObjects = totalObjects;
    }

    public Long getSuccessObjects() {
        return successObjects;
    }

    public SnowDDLProcessStatus successObjects(Long successObjects) {
        this.successObjects = successObjects;
        return this;
    }

    public void setSuccessObjects(Long successObjects) {
        this.successObjects = successObjects;
    }

    public Long getErrorObjects() {
        return errorObjects;
    }

    public SnowDDLProcessStatus errorObjects(Long errorObjects) {
        this.errorObjects = errorObjects;
        return this;
    }

    public void setErrorObjects(Long errorObjects) {
        this.errorObjects = errorObjects;
    }

    public String getStatus() {
        return status;
    }

    public SnowDDLProcessStatus status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SnowDDLProcessStatus)) {
            return false;
        }
        return batchId != null && batchId.equals(((SnowDDLProcessStatus) o).batchId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SnowDDLProcessStatus{" +
            "batchId=" + getBatchId() +
            ", processId='" + getProcessId() + "'" +
            ", name='" + getName() + "'" +
            ", runBy='" + getRunBy() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", totalObjects='" + getTotalObjects() + "'" +
            ", successObjects='" + getSuccessObjects() + "'" +
            ", errorObjects='" + getErrorObjects() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
