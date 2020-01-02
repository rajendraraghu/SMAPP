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
 * Criteria class for the {@link com.canny.snowflakemigration.domain.SnowHistoryJobStatus} entity. This class is used
 * in {@link com.canny.snowflakemigration.web.rest.SnowHistoryJobStatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /migration-processes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SnowHistoryJobStatusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter jobId;

    private LongFilter batchId;

    private StringFilter name;

    private InstantFilter startTime;

    private InstantFilter endTime;

    private LongFilter sourceCount;

    private LongFilter insertCount;

    private LongFilter deleteCount;

    private StringFilter status;

    public SnowHistoryJobStatusCriteria(){
    }

    public SnowHistoryJobStatusCriteria(SnowHistoryJobStatusCriteria other){
        this.jobId = other.jobId == null ? null : other.jobId.copy();
        this.batchId = other.batchId == null ? null : other.batchId.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.sourceCount = other.sourceCount == null ? null : other.sourceCount.copy();
        this.insertCount = other.insertCount == null ? null : other.insertCount.copy();
        this.deleteCount = other.deleteCount == null ? null : other.deleteCount.copy();
        this.status = other.status == null ? null : other.status.copy();
    }

    @Override
    public SnowHistoryJobStatusCriteria copy() {
        return new SnowHistoryJobStatusCriteria(this);
    }

    public LongFilter getJobId() {
        return jobId;
    }

    public void setJobId(LongFilter jobId) {
        this.jobId = jobId;
    }

    public LongFilter getBatchId() {
        return batchId;
    }

    public void setBatchId(LongFilter batchId) {
        this.batchId = batchId;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public InstantFilter getStartTime() {
        return startTime;
    }

    public void setStartTime(InstantFilter startTime) {
        this.startTime = startTime;
    }

    public InstantFilter getEndTime() {
        return endTime;
    }

    public void setEndTime(InstantFilter endTime) {
        this.endTime = endTime;
    }

    public LongFilter getSourceCount() {
        return sourceCount;
    }

    public void setSourceCount(LongFilter sourceCount) {
        this.sourceCount = sourceCount;
    }

    public LongFilter getInsertCount() {
        return insertCount;
    }

    public void setInsertCount(LongFilter insertCount) {
        this.insertCount = insertCount;
    }

public LongFilter getDeleteCount() {
        return deleteCount;
    }

    public void setDeleteCount(LongFilter deleteCount) {
        this.deleteCount = deleteCount;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
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
        final SnowHistoryJobStatusCriteria that = (SnowHistoryJobStatusCriteria) o;
        return
            Objects.equals(jobId, that.jobId) &&
            Objects.equals(batchId, that.batchId) &&
            Objects.equals(name, that.name) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(sourceCount, that.sourceCount) &&
            Objects.equals(insertCount, that.insertCount) &&
            Objects.equals(deleteCount, that.deleteCount) &&
            Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        jobId,
        batchId,
        name,
        startTime,
        endTime,
        sourceCount,
        insertCount,
        deleteCount,
        status
        );
    }

    @Override
    public String toString() {
        return "SnowHistoryJobStatusCriteria{" +
                (jobId != null ? "jobId=" + jobId + ", " : "") +
                (batchId != null ? "batchId=" + batchId + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (startTime != null ? "startTime=" + startTime + ", " : "") +
                (endTime != null ? "endTime=" + endTime + ", " : "") +
                (sourceCount != null ? "sourceCount=" + sourceCount + ", " : "") +
                (insertCount != null ? "insertCount=" + insertCount + ", " : "") +
                (deleteCount != null ? "deleteCount=" + deleteCount + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
            "}";
    }

}
