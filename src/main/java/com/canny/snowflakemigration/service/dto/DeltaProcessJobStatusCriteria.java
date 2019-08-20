package com.canny.snowflakemigration.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.canny.snowflakemigration.domain.DeltaJobStatus} entity. This class is used
 * in {@link com.canny.snowflakemigration.web.rest.DeltaJobStatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /delta-processes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DeltaProcessJobStatusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter jobId;

    private LongFilter tableLoadId;

    private StringFilter tableName;

    private InstantFilter tableLoadStartTime;

    private InstantFilter tableLoadEndTime;

    private StringFilter tableLoadStatus;

    private LongFilter insertCount;

    private LongFilter updateCount;

    private LongFilter deleteCount;

    private StringFilter runType;

    private LongFilter processId;

    private StringFilter processName;

    private StringFilter sourceName;

    private StringFilter destName;

    public DeltaProcessJobStatusCriteria(){
    }

    public DeltaProcessJobStatusCriteria(DeltaProcessJobStatusCriteria other){
        this.jobId = other.jobId == null ? null : other.jobId.copy();
        this.tableLoadId = other.tableLoadId == null ? null : other.tableLoadId.copy();
        this.tableName = other.tableName == null ? null : other.tableName.copy();
        this.tableLoadStartTime = other.tableLoadStartTime == null ? null : other.tableLoadStartTime.copy();
        this.tableLoadEndTime = other.tableLoadEndTime == null ? null : other.tableLoadEndTime.copy();
        this.tableLoadStatus = other.tableLoadStatus == null ? null : other.tableLoadStatus.copy();
        this.insertCount = other.insertCount == null ? null : other.insertCount.copy();
        this.updateCount = other.updateCount == null ? null : other.updateCount.copy();
        this.deleteCount = other.deleteCount == null ? null : other.deleteCount.copy();
        this.runType = other.runType == null ? null : other.runType.copy();
        this.processId = other.processId == null ? null : other.processId.copy();
        this.processName = other.processName == null ? null : other.processName.copy();
        this.sourceName = other.sourceName == null ? null : other.sourceName.copy();
        this.destName = other.destName == null ? null : other.destName.copy();
    }

    @Override
    public DeltaProcessJobStatusCriteria copy() {
        return new DeltaProcessJobStatusCriteria(this);
    }

    public LongFilter getJobId() {
        return jobId;
    }

    public void setJobId(LongFilter jobId) {
        this.jobId = jobId;
    }

    public LongFilter getTableLoadId() {
        return tableLoadId;
    }

    public void setTableLoadId(LongFilter tableLoadId) {
        this.tableLoadId = tableLoadId;
    }

    public StringFilter getTableName() {
        return tableName;
    }

    public void setTableName(StringFilter tableName) {
        this.tableName = tableName;
    }

    public InstantFilter getTableLoadStartTime() {
        return tableLoadStartTime;
    }

    public void setTableLoadStartTime(InstantFilter tableLoadStartTime) {
        this.tableLoadStartTime = tableLoadStartTime;
    }

    public InstantFilter getTableLoadEndTime() {
        return tableLoadEndTime;
    }

    public void setTableLoadEndTime(InstantFilter tableLoadEndTime) {
        this.tableLoadEndTime = tableLoadEndTime;
    }

    public StringFilter getTableLoadStatus() {
        return tableLoadStatus;
    }

    public void setTableLoadStatus(StringFilter tableLoadStatus) {
        this.tableLoadStatus = tableLoadStatus;
    }

    public LongFilter getInsertCount() {
        return insertCount;
    }

    public void setInsertCount(LongFilter insertCount) {
        this.insertCount = insertCount;
    }

    public LongFilter getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(LongFilter updateCount) {
        this.updateCount = updateCount;
    }

    public LongFilter getDeleteCount() {
        return deleteCount;
    }

    public void setDeleteCount(LongFilter deleteCount) {
        this.deleteCount = deleteCount;
    }

    public StringFilter getRunType() {
        return runType;
    }

    public void setRunType(StringFilter runType) {
        this.runType = runType;
    }

    public LongFilter getProcessId() {
        return processId;
    }

    public void setProcessId(LongFilter processId) {
        this.processId = processId;
    }

    public StringFilter getProcessName() {
        return processName;
    }

    public void setProcessName(StringFilter processName) {
        this.processName = processName;
    }

    public StringFilter getSourceName() {
        return sourceName;
    }

    public void setSourceName(StringFilter sourceName) {
        this.sourceName = sourceName;
    }

    public StringFilter getDestName() {
        return destName;
    }

    public void setDestName(StringFilter destName) {
        this.destName = destName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DeltaProcessJobStatusCriteria that = (DeltaProcessJobStatusCriteria) o;
        return
            Objects.equals(jobId, that.jobId) &&
            Objects.equals(tableLoadId, that.tableLoadId) &&
            Objects.equals(tableName, that.tableName) &&
            Objects.equals(tableLoadStartTime, that.tableLoadStartTime) &&
            Objects.equals(tableLoadEndTime, that.tableLoadEndTime) &&
            Objects.equals(tableLoadStatus, that.tableLoadStatus) &&
            Objects.equals(insertCount, that.insertCount) &&
            Objects.equals(updateCount, that.updateCount) &&
            Objects.equals(deleteCount, that.deleteCount) &&
            Objects.equals(runType, that.runType) &&
            Objects.equals(processId, that.processId) &&
            Objects.equals(processName, that.processName) &&
            Objects.equals(sourceName, that.sourceName) &&
            Objects.equals(destName, that.destName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        jobId,
        tableLoadId,
        tableName,
        tableLoadStartTime,
        tableLoadEndTime,
        tableLoadStatus,
        insertCount,
        updateCount,
        deleteCount,
        runType,
        processId,
        processName,
        sourceName,
        destName
        );
    }

    @Override
    public String toString() {
        return "DeltaProcessJobStatusCriteria{" +
                (jobId != null ? "jobId=" + jobId + ", " : "") +
                (tableLoadId != null ? "tableLoadId=" + tableLoadId + ", " : "") +
                (tableName != null ? "tableName=" + tableName + ", " : "") +
                (tableLoadStartTime != null ? "tableLoadStartTime=" + tableLoadStartTime + ", " : "") +
                (tableLoadEndTime != null ? "tableLoadEndTime=" + tableLoadEndTime + ", " : "") +
                (tableLoadStatus != null ? "tableLoadStatus=" + tableLoadStatus + ", " : "") +
                (insertCount != null ? "insertCount=" + insertCount + ", " : "") +
                (updateCount != null ? "updateCount=" + updateCount + ", " : "") +
                (deleteCount != null ? "deleteCount=" + deleteCount + ", " : "") +
                (runType != null ? "runType=" + runType + ", " : "") +
                (processId != null ? "processId=" + processId + ", " : "") +
                (processName != null ? "processName=" + processName + ", " : "") +
                (sourceName != null ? "sourceName=" + sourceName + ", " : "") +
                (destName != null ? "destName=" + destName + ", " : "") +
            "}";
    }

}
