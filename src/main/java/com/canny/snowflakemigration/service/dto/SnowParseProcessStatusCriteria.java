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
 * Criteria class for the {@link com.canny.snowflakemigration.domain.SnowParseProcessStatus} entity. This class is used
 * in {@link com.canny.snowflakemigration.web.rest.SnowParseProcessStatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /migration-processes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SnowParseProcessStatusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter batchId;

    private LongFilter processId;

    private StringFilter name;

    private StringFilter runBy;

    private InstantFilter startTime;

    private InstantFilter endTime;

    private LongFilter totalObjects;

    private LongFilter successObjects;

    private LongFilter errorObjects;

    private StringFilter status;

    public SnowParseProcessStatusCriteria(){
    }

    public SnowParseProcessStatusCriteria(SnowParseProcessStatusCriteria other){
        this.batchId = other.batchId == null ? null : other.batchId.copy();
        this.processId = other.processId == null ? null : other.processId.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.runBy = other.runBy == null ? null : other.runBy.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.totalObjects = other.totalObjects == null ? null : other.totalObjects.copy();
        this.successObjects = other.successObjects == null ? null : other.successObjects.copy();
        this.errorObjects = other.errorObjects == null ? null : other.errorObjects.copy();
        this.status = other.status == null ? null : other.status.copy();
    }

    @Override
    public SnowParseProcessStatusCriteria copy() {
        return new SnowParseProcessStatusCriteria(this);
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

    public LongFilter getTotalObjects() {
        return totalObjects;
    }

    public void setTotalObjects(LongFilter totalObjects) {
        this.totalObjects = totalObjects;
    }

    public LongFilter getSuccessObjects() {
        return successObjects;
    }

    public void setSuccessObjects(LongFilter successObjects) {
        this.successObjects = successObjects;
    }

    public LongFilter getErrorObjects() {
        return errorObjects;
    }

    public void setErrorObjects(LongFilter errorObjects) {
        this.errorObjects = errorObjects;
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
        final SnowParseProcessStatusCriteria that = (SnowParseProcessStatusCriteria) o;
        return
            Objects.equals(batchId, that.batchId) &&
            Objects.equals(processId, that.processId) &&
            Objects.equals(name, that.name) &&
            Objects.equals(runBy, that.runBy) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(totalObjects, that.totalObjects) &&
            Objects.equals(successObjects, that.successObjects) &&
            Objects.equals(errorObjects, that.errorObjects) &&
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
        totalObjects,
        successObjects,
        errorObjects,
        status
        );
    }

    @Override
    public String toString() {
        return "SnowParseProcessStatusCriteria{" +
                (batchId != null ? "batchId=" + batchId + ", " : "") +
                (processId != null ? "processId=" + processId + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (runBy != null ? "runBy=" + runBy + ", " : "") +
                (startTime != null ? "startTime=" + startTime + ", " : "") +
                (endTime != null ? "endTime=" + endTime + ", " : "") +
                (totalObjects != null ? "totalObjects=" + totalObjects + ", " : "") +
                (successObjects != null ? "successObjects=" + successObjects + ", " : "") +
                (errorObjects != null ? "errorObjects=" + errorObjects + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
            "}";
    }

}
