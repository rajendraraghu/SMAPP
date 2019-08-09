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
 * Criteria class for the {@link com.canny.snowflakemigration.domain.SnowDDLJobStatus} entity. This class is used
 * in {@link com.canny.snowflakemigration.web.rest.SnowDDLJobStatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /migration-processes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SnowDDLJobStatusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter jobId;

    private LongFilter batchId;

    private StringFilter name;

    private InstantFilter startTime;

    private InstantFilter endTime;

    private StringFilter status;

    public SnowDDLJobStatusCriteria(){
    }

    public SnowDDLJobStatusCriteria(SnowDDLJobStatusCriteria other){
        this.jobId = other.jobId == null ? null : other.jobId.copy();
        this.batchId = other.batchId == null ? null : other.batchId.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.status = other.status == null ? null : other.status.copy();
    }

    @Override
    public SnowDDLJobStatusCriteria copy() {
        return new SnowDDLJobStatusCriteria(this);
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
        final SnowDDLJobStatusCriteria that = (SnowDDLJobStatusCriteria) o;
        return
            Objects.equals(jobId, that.jobId) &&
            Objects.equals(batchId, that.batchId) &&
            Objects.equals(name, that.name) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
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
        status
        );
    }

    @Override
    public String toString() {
        return "SnowDDLJobStatusCriteria{" +
                (jobId != null ? "jobId=" + jobId + ", " : "") +
                (batchId != null ? "batchId=" + batchId + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (startTime != null ? "startTime=" + startTime + ", " : "") +
                (endTime != null ? "endTime=" + endTime + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
            "}";
    }

}
