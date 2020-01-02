package com.canny.snowflakemigration.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A SnowHistoryJobStatus.
 */
@Entity
@Table(name = "snow_history_job_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SnowHistoryJobStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long jobId;

    @NotNull
    @Column(name = "batch_id", nullable = false)
    private Long batchId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "source_count", nullable = false)
    private Long sourceCount;

    @Column(name = "insert_count", nullable = false)
    private Long insertCount;

    @Column(name = "delete_count", nullable = false)
    private Long deleteCount;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
    
    public Long getBatchId() {
        return batchId;
    }

    public SnowHistoryJobStatus batchId(Long batchId) {
        this.batchId = batchId;
        return this;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getName() {
        return name;
    }

    public SnowHistoryJobStatus name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public SnowHistoryJobStatus startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }
    
    public Instant getEndTime() {
        return endTime;
    }

    public SnowHistoryJobStatus endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Long getSourceCount() {
        return sourceCount;
    }

    public SnowHistoryJobStatus sourceCount(Long sourceCount) {
        this.sourceCount = sourceCount;
        return this;
    }

    public void setSourceCount(Long sourceCount) {
        this.sourceCount = sourceCount;
    }

    public Long getInsertCount() {
        return insertCount;
    }

    public SnowHistoryJobStatus insertCount(Long insertCount) {
        this.insertCount = insertCount;
        return this;
    }

    public void setInsertCount(Long insertCount) {
        this.insertCount = insertCount;
    }

public Long getDeleteCount() {
        return deleteCount;
    }

    public SnowHistoryJobStatus deleteCount(Long deleteCount) {
        this.deleteCount = deleteCount;
        return this;
    }

    public void setDeleteCount(Long deleteCount) {
        this.deleteCount = deleteCount;
    }

    public String getStatus() {
        return status;
    }

    public SnowHistoryJobStatus status(String status) {
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
        if (!(o instanceof SnowHistoryJobStatus)) {
            return false;
        }
        return jobId != null && jobId.equals(((SnowHistoryJobStatus) o).jobId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SnowHistoryJobStatus{" +
            "jobId=" + getJobId() +
            ", batchId=" + getBatchId() +
            ", name='" + getName() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", sourceCount='" + getSourceCount() + "'" +
            ", insertCount='" + getInsertCount() + "'" +
            ", deleteCount='" + getDeleteCount() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
