package com.canny.snowflakemigration.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.canny.snowflakemigration.domain.SnowDDLProcessStatus} entity.
 */
public class SnowDDLProcessStatusDTO implements Serializable {

    private Long batchId;

    @NotNull
    private Long processId;

    @NotNull
    private String name;

    private String runBy;

    private Instant startTime;

    private Instant endTime;

    private Long totalObjects;

    private Long successObjects;

    private Long errorObjects;

    private String status;

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
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

    public void setRunBy(String runBy) {
        this.runBy = runBy;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Long getTotalObjects() {
        return totalObjects;
    }

    public void setTotalObjects(Long totalObjects) {
        this.totalObjects = totalObjects;
    }

    public Long getSuccessObjects() {
        return successObjects;
    }

    public void setSuccessObjects(Long successObjects) {
        this.successObjects = successObjects;
    }

    public Long getErrorObjects() {
        return errorObjects;
    }

    public void setErrorObjects(Long errorObjects) {
        this.errorObjects = errorObjects;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SnowDDLProcessStatusDTO SnowDDLProcessStatusDTO = (SnowDDLProcessStatusDTO) o;
        if (SnowDDLProcessStatusDTO.getBatchId() == null || getBatchId() == null) {
            return false;
        }
        return Objects.equals(getBatchId(), SnowDDLProcessStatusDTO.getBatchId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getBatchId());
    }

    @Override
    public String toString() {
        return "SnowDDLProcessStatusDTO{" +
            "batchId=" + getBatchId() +
            ", processId=" + getProcessId() +
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
