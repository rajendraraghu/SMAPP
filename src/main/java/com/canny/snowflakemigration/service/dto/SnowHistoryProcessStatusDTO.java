package com.canny.snowflakemigration.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.canny.snowflakemigration.domain.SnowHistoryProcessStatus} entity.
 */
public class SnowHistoryProcessStatusDTO implements Serializable {

    private Long batchId;

    @NotNull
    private Long processId;

    @NotNull
    private String name;

    private String runBy;

    private Instant startTime;

    private Instant endTime;

    private Long totalTables;

    private Long successTables;

    private Long errorTables;

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

    public Long getTotalTables() {
        return totalTables;
    }

    public void setTotalTables(Long totalTables) {
        this.totalTables = totalTables;
    }

    public Long getSuccessTables() {
        return successTables;
    }

    public void setSuccessTables(Long successTables) {
        this.successTables = successTables;
    }

    public Long getErrorTables() {
        return errorTables;
    }

    public void setErrorTables(Long errorTables) {
        this.errorTables = errorTables;
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

       SnowHistoryProcessStatusDTO SnowHistoryProcessStatusDTO = (SnowHistoryProcessStatusDTO) o;
        if (SnowHistoryProcessStatusDTO.getBatchId() == null || getBatchId() == null) {
            return false;
        }
        return Objects.equals(getBatchId(), SnowHistoryProcessStatusDTO.getBatchId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getBatchId());
    }

    @Override
    public String toString() {
        return "SnowHistoryProcessStatusDTO{" +
            "batchId=" + getBatchId() +
            ", processId=" + getProcessId() +
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
