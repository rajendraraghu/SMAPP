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
 * Criteria class for the {@link com.canny.snowflakemigration.domain.SnowHistoryProcessStatus} entity. This class is used
 * in {@link com.canny.snowflakemigration.web.rest.SnowHistoryProcessStatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /migration-processes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SnowHistoryProcessStatusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter batchId;

    private LongFilter processId;

    private StringFilter name;

    private StringFilter runBy;

    private InstantFilter startTime;

    private InstantFilter endTime;

    private LongFilter totalTables;

    private LongFilter successTables;

    private LongFilter errorTables;

    private StringFilter status;

    public SnowHistoryProcessStatusCriteria(){
    }

    public SnowHistoryProcessStatusCriteria(SnowHistoryProcessStatusCriteria other){
        this.batchId = other.batchId == null ? null : other.batchId.copy();
        this.processId = other.processId == null ? null : other.processId.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.runBy = other.runBy == null ? null : other.runBy.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.totalTables = other.totalTables == null ? null : other.totalTables.copy();
        this.successTables = other.successTables == null ? null : other.successTables.copy();
        this.errorTables = other.errorTables == null ? null : other.errorTables.copy();
        this.status = other.status == null ? null : other.status.copy();
    }

    @Override
    public SnowHistoryProcessStatusCriteria copy() {
        return new SnowHistoryProcessStatusCriteria(this);
    }

    public LongFilter getBatchId() {
        return batchId;
    }

    public void setBatchId(LongFilter batchId) {
        this.batchId = batchId;
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

    public LongFilter getTotalTables() {
        return totalTables;
    }

    public void setTotalTables(LongFilter totalTables) {
        this.totalTables = totalTables;
    }

    public LongFilter getSuccessTables() {
        return successTables;
    }

    public void setSuccessTables(LongFilter successTables) {
        this.successTables = successTables;
    }

    public LongFilter getErrorTables() {
        return errorTables;
    }

    public void setErrorTables(LongFilter errorTables) {
        this.errorTables = errorTables;
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
        final SnowHistoryProcessStatusCriteria that = (SnowHistoryProcessStatusCriteria) o;
        return
            Objects.equals(batchId, that.batchId) &&
            Objects.equals(processId, that.processId) &&
            Objects.equals(name, that.name) &&
            Objects.equals(runBy, that.runBy) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(totalTables, that.totalTables) &&
            Objects.equals(successTables, that.successTables) &&
            Objects.equals(errorTables, that.errorTables) &&
            Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        batchId,
        processId,
        name,
        runBy,
        startTime,
        endTime,
        totalTables,
        successTables,
        errorTables,
        status
        );
    }

    @Override
    public String toString() {
        return "SnowHistoryProcessStatusCriteria{" +
                (batchId != null ? "batchId=" + batchId + ", " : "") +
                (processId != null ? "processId=" + processId + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (runBy != null ? "runBy=" + runBy + ", " : "") +
                (startTime != null ? "startTime=" + startTime + ", " : "") +
                (endTime != null ? "endTime=" + endTime + ", " : "") +
                (totalTables != null ? "totalTables=" + totalTables + ", " : "") +
                (successTables != null ? "successTables=" + successTables + ", " : "") +
                (errorTables != null ? "errorTables=" + errorTables + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
            "}";
    }

}
