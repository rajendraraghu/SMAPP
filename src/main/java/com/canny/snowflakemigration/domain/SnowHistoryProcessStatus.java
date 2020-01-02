package com.canny.snowflakemigration.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A SnowHistoryProcessStatus.
 */
@Entity
@Table(name = "snow_history_process_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SnowHistoryProcessStatus implements Serializable {

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

    @Column(name = "total_tables")
    private Long totalTables;

    @Column(name = "success_tables")
    private Long successTables;

    @Column(name = "error_tables")
    private Long errorTables;

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

    public SnowHistoryProcessStatus processId(Long processId) {
        this.processId = processId;
        return this;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getName() {
        return name;
    }

    public SnowHistoryProcessStatus name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRunBy() {
        return runBy;
    }

    public SnowHistoryProcessStatus runBy(String runBy) {
        this.runBy = runBy;
        return this;
    }

    public void setRunBy(String runBy) {
        this.runBy = runBy;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public SnowHistoryProcessStatus startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }
    
    public Instant getEndTime() {
        return endTime;
    }

    public SnowHistoryProcessStatus endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Long getTotalTables() {
        return totalTables;
    }

    public SnowHistoryProcessStatus totalTables(Long totalTables) {
        this.totalTables = totalTables;
        return this;
    }

    public void setTotalTables(Long totalTables) {
        this.totalTables = totalTables;
    }

    public Long getSuccessTables() {
        return successTables;
    }

    public SnowHistoryProcessStatus successTables(Long successTables) {
        this.successTables = successTables;
        return this;
    }

    public void setSuccessTables(Long successTables) {
        this.successTables = successTables;
    }

    public Long getErrorTables() {
        return errorTables;
    }

    public SnowHistoryProcessStatus errorTables(Long errorTables) {
        this.errorTables = errorTables;
        return this;
    }

    public void setErrorTables(Long errorTables) {
        this.errorTables = errorTables;
    }

    public String getStatus() {
        return status;
    }

    public SnowHistoryProcessStatus status(String status) {
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
        if (!(o instanceof SnowHistoryProcessStatus)) {
            return false;
        }
        return batchId != null && batchId.equals(((SnowHistoryProcessStatus) o).batchId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SnowHistoryProcessStatus{" +
            "batchId=" + getBatchId() +
            ", processId='" + getProcessId() + "'" +
            ", name='" + getName() + "'" +
            ", runBy='" + getRunBy() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", totalTables='" + getTotalTables() + "'" +
            ", successTables='" + getSuccessTables() + "'" +
            ", errorTables='" + getErrorTables() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
