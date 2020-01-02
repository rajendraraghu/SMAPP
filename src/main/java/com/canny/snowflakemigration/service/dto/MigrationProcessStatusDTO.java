package com.canny.snowflakemigration.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.canny.snowflakemigration.domain.SnowDDL} entity.
 */
public class MigrationProcessStatusDTO implements Serializable {

    private Long jobId;

    @NotNull
    private Long processId;

    @NotNull
    private String name;

    private String runBy;

    private Instant jobStartTime;

    private Instant jobEndTime;

    private Long tableCount;

    private Long successCount;

    private Long failureCount;

    private String jobStatus;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRunBy() {
        return runBy;
    }

    public void setRunby(String runBy) {
        this.runBy = runBy;
    }

    public Instant getJobStartTime() {
        return jobStartTime;
    }

    public void setJobStartTime(Instant jobStartTime) {
        this.jobStartTime = jobStartTime;
    }

    public Instant getJobEndTime() {
        return jobEndTime;
    }

    public void setJobEndTime(Instant jobEndTime) {
        this.jobEndTime = jobEndTime;
    }

    public Long getTableCount() {
        return tableCount;
    }

    public void setTableCount(Long tableCount) {
        this.tableCount = tableCount;
    }

    public Long getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Long successCount) {
        this.successCount = successCount;
    }

    public Long getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(Long failureCount) {
        this.failureCount = failureCount;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MigrationProcessStatusDTO MigrationProcessStatusDTO = (MigrationProcessStatusDTO) o;
        if (MigrationProcessStatusDTO.getJobId() == null || getJobId() == null) {
            return false;
        }
        return Objects.equals(getJobId(), MigrationProcessStatusDTO.getJobId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getJobId());
    }

    @Override
    public String toString() {
        return "MigrationProcessStatusDTO{" +
            "jobId=" + getJobId() +
            ", processId=" + getProcessId() +
            ", name='" + getName() + "'" +
            ", runBy='" + getRunBy() + "'" +
            ", jobStartTime='" + getJobStartTime() + "'" +
            ", jobEndTime='" + getJobEndTime() + "'" +
            ", tableCount='" + getTableCount() + "'" +
            ", successCount='" + getSuccessCount() + "'" +
            ", failureCount='" + getFailureCount() + "'" +
            ", jobStatus='" + getJobStatus() + "'" +
            "}";
    }
}
