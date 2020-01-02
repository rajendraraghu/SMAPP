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
 * Criteria class for the {@link com.canny.snowflakemigration.domain.DeltaProcessStatus} entity. This class is used
 * in {@link com.canny.snowflakemigration.web.rest.DeltaProcessStatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /delta-processes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DeltaProcessStatusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter jobId;

    private LongFilter processId;

    private StringFilter name;

    private StringFilter runBy;

    private InstantFilter jobStartTime;

    private InstantFilter jobEndTime;

    private LongFilter tableCount;

    private LongFilter successCount;

    private LongFilter failureCount;

    private StringFilter jobStatus;

    public DeltaProcessStatusCriteria(){
    }

    public DeltaProcessStatusCriteria(DeltaProcessStatusCriteria other){
        this.jobId = other.jobId == null ? null : other.jobId.copy();
        this.processId = other.processId == null ? null : other.processId.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.runBy = other.runBy == null ? null : other.runBy.copy();
        this.jobStartTime = other.jobStartTime == null ? null : other.jobStartTime.copy();
        this.jobEndTime = other.jobEndTime == null ? null : other.jobEndTime.copy();
        this.tableCount = other.tableCount == null ? null : other.tableCount.copy();
        this.successCount = other.successCount == null ? null : other.successCount.copy();
        this.failureCount = other.failureCount == null ? null : other.failureCount.copy();
        this.jobStatus = other.jobStatus == null ? null : other.jobStatus.copy();
    }

    @Override
    public DeltaProcessStatusCriteria copy() {
        return new DeltaProcessStatusCriteria(this);
    }

    public LongFilter getJobId() {
        return jobId;
    }

    public void setJobId(LongFilter jobId) {
        this.jobId = jobId;
    }

    public LongFilter getProcessId() {
        return processId;
    }

    public void setProcessId(LongFilter processId) {
        this.processId = processId;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getRunBy() {
        return runBy;
    }

    public void setRunBy(StringFilter runBy) {
        this.runBy = runBy;
    }

    public InstantFilter getJobStartTime() {
        return jobStartTime;
    }

    public void setJobStartTime(InstantFilter jobStartTime) {
        this.jobStartTime = jobStartTime;
    }

    public InstantFilter getJobEndTime() {
        return jobEndTime;
    }

    public void setJobEndTime(InstantFilter jobEndTime) {
        this.jobEndTime = jobEndTime;
    }

    public LongFilter getTableCount() {
        return tableCount;
    }

    public void setTableCount(LongFilter tableCount) {
        this.tableCount = tableCount;
    }

    public LongFilter getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(LongFilter successCount) {
        this.successCount = successCount;
    }

    public LongFilter getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(LongFilter failureCount) {
        this.failureCount = failureCount;
    }

    public StringFilter getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(StringFilter jobStatus) {
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
        final DeltaProcessStatusCriteria that = (DeltaProcessStatusCriteria) o;
        return
            Objects.equals(jobId, that.jobId) &&
            Objects.equals(processId, that.processId) &&
            Objects.equals(name, that.name) &&
            Objects.equals(runBy, that.runBy) &&
            Objects.equals(jobStartTime, that.jobStartTime) &&
            Objects.equals(jobEndTime, that.jobEndTime) &&
            Objects.equals(tableCount, that.tableCount) &&
            Objects.equals(successCount, that.successCount) &&
            Objects.equals(failureCount, that.failureCount) &&
            Objects.equals(jobStatus, that.jobStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        jobId,
        processId,
        name,
        runBy,
        jobStartTime,
        jobEndTime,
        tableCount,
        successCount,
        failureCount,
        jobStatus
        );
    }

    @Override
    public String toString() {
        return "DeltaProcessStatusCriteria{" +
                (jobId != null ? "id=" + jobId + ", " : "") +
                (processId != null ? "id=" + processId + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (runBy != null ? "runBy=" + runBy + ", " : "") +
                (jobStartTime != null ? "startTime=" + jobStartTime + ", " : "") +
                (jobEndTime != null ? "endTime=" + jobEndTime + ", " : "") +
                (tableCount != null ? "totalObjects=" + tableCount + ", " : "") +
                (successCount != null ? "successObjects=" + successCount + ", " : "") +
                (failureCount != null ? "errorObjects=" + failureCount + ", " : "") +
                (jobStatus != null ? "status=" + jobStatus  + ", " : "") +
            "}";
    }

}
