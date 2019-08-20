package com.canny.snowflakemigration.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.canny.snowflakemigration.domain.Delta} entity.
 */
public class DeltaProcessJobStatusDTO implements Serializable {

    @NotNull
    private Long jobId;

    @NotNull
    private Long tableLoadId;

    @NotNull
    private String tableName;

    private Instant tableLoadStartTime;

    private Instant tableLoadEndTime;

    private String tableLoadStatus;

    private Long insertCount;

    private Long updateCount;

    private Long deleteCount;

    private String runType;

    private Long processId;

    private String processName;

    private String sourceName;

    private String destName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getTableLoadId() {
        return tableLoadId;
    }

    public void setTableLoadId(Long tableLoadId) {
        this.tableLoadId = tableLoadId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Instant getTableLoadStartTime() {
        return tableLoadStartTime;
    }

    public void setTableLoadStartTime(Instant tableLoadStartTime) {
        this.tableLoadStartTime = tableLoadStartTime;
    }

    public Instant getTableLoadEndTime() {
        return tableLoadEndTime;
    }

    public void setTableLoadEndTime(Instant tableLoadEndTime) {
        this.tableLoadEndTime = tableLoadEndTime;
    }

    public String getTableLoadStatus() {
        return tableLoadStatus;
    }

    public void setTableLoadStatus(String tableLoadStatus) {
        this.tableLoadStatus = tableLoadStatus;
    }

    public Long getInsertCount() {
        return insertCount;
    }

    public void setInsertCount(Long insertCount) {
        this.insertCount = insertCount;
    }

    public Long getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(Long updateCount) {
        this.updateCount = updateCount;
    }

    public Long getDeleteCount() {
        return deleteCount;
    }

    public void setDeleteCount(Long deleteCount) {
        this.deleteCount = deleteCount;
    }

    public String getRunType() {
        return runType;
    }

    public void setRunType(String runType) {
        this.runType = runType;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeltaProcessJobStatusDTO)) {
            return false;
        }
        return jobId != null && jobId.equals(((DeltaProcessJobStatusDTO) o).jobId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DeltaProcessJobStatusDTO{" +
            "jobId=" + getJobId() +
            ", tableLoadId=" + getTableLoadId() +
            ", tableName='" + getTableName() + "'" +
            ", tableLoadStartTime='" + getTableLoadStartTime() + "'" +
            ", tableLoadEndTime='" + getTableLoadEndTime() + "'" +
            ", tableLoadStatus='" + getTableLoadStatus() + "'" +
            ", insertCount='" + getInsertCount() + "'" +
            ", updateCount='" + getUpdateCount() + "'" +
            ", deleteCount='" + getDeleteCount() + "'" +
            ", runType='" + getRunType() + "'" +
            ", processId='" + getProcessId() + "'" +
            ", processName='" + getProcessName() + "'" +
            ", sourceName='" + getSourceName() + "'" +
            ", destName='" + getDestName() + "'" +
            "}";
    }
}
