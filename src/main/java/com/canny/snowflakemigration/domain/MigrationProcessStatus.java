package com.canny.snowflakemigration.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "sah_jobrunstatus")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MigrationProcessStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "jobid")
    private Long jobId;

    @NotNull
    @Column(name = "processid", nullable = false)
    private Long processId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "runby")
    private String runBy;

    @Column(name = "jobstarttime")
    private Instant jobStartTime;

    @Column(name = "jobendtime")
    private Instant jobEndTime;

    @Column(name = "tablecount")
    private Long tableCount;

    @Column(name = "successcount")
    private Long successCount;

    @Column(name = "failurecount")
    private Long failureCount;

    @Column(name = "jobstatus")
    private String jobStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getProcessId() {
        return processId;
    }

    public MigrationProcessStatus processId(Long processId) {
        this.processId = processId;
        return this;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getName() {
        return name;
    }

    public MigrationProcessStatus name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRunBy() {
        return runBy;
    }

    public MigrationProcessStatus runBy(String runBy) {
        this.runBy = runBy;
        return this;
    }

    public void setRunBy(String runBy) {
        this.runBy = runBy;
    }

    public Instant getJobStartTime() {
        return jobStartTime;
    }

    public MigrationProcessStatus jobStartTime(Instant jobStartTime) {
        this.jobStartTime = jobStartTime;
        return this;
    }

    public void setJobStartTime(Instant jobStartTime) {
        this.jobStartTime = jobStartTime;
    }
    
    public Instant getJobEndTime() {
        return jobEndTime;
    }

    public MigrationProcessStatus jobEndTime(Instant jobEndTime) {
        this.jobEndTime = jobEndTime;
        return this;
    }

    public void setJobEndTime(Instant jobEndTime) {
        this.jobEndTime = jobEndTime;
    }

    public Long getTableCount() {
        return tableCount;
    }

    public MigrationProcessStatus tableCount(Long tableCount) {
        this.tableCount = tableCount;
        return this;
    }

    public void setTableCount(Long tableCount) {
        this.tableCount = tableCount;
    }

    public Long getSuccessCount() {
        return successCount;
    }

    public MigrationProcessStatus successCount(Long successCount) {
        this.successCount = successCount;
        return this;
    }

    public void setSuccessCount(Long successCount) {
        this.successCount = successCount;
    }

    public Long getFailureCount() {
        return failureCount;
    }

    public MigrationProcessStatus failureCount(Long failureCount) {
        this.failureCount = failureCount;
        return this;
    }

    public void setFailureCount(Long failureCount) {
        this.failureCount = failureCount;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public MigrationProcessStatus jobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
        return this;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MigrationProcessStatus)) {
            return false;
        }
        return jobId != null && jobId.equals(((MigrationProcessStatus) o).jobId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MigrationProcessStatus{" +
            "jobId=" + getJobId() +
            ", processId='" + getProcessId() + "'" +
            ", name='" + getName() + "'" +
            ", runBy='" + getRunBy() + "'" +
            ", jobstartTime='" + getJobStartTime() + "'" +
            ", jobEndTime='" + getJobEndTime() + "'" +
            ", tableCount='" + getTableCount() + "'" +
            ", successCount='" + getSuccessCount() + "'" +
            ", failureCount='" + getFailureCount() + "'" +
            ", jobStatus='" + getJobStatus() + "'" +
            "}";
    }
}
