package com.canny.snowflakemigration.service.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;
import liquibase.datatype.core.DateTimeType;
import net.snowflake.client.jdbc.internal.joda.time.DateTime;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the
 * {@link com.canny.snowflakemigration.domain.MigrationProcess} entity. This
 * class is used in
 * {@link com.canny.snowflakemigration.web.rest.MigrationProcessResource} to
 * receive all the possible filtering options from the Http GET request
 * parameters. For example the following could be a valid request:
 * {@code /migration-processes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific
 * {@link Filter} class are used, we need to use fix type specific filters.
 */
public class MigrationProcessCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter type;

    private StringFilter tablesToMigrate;

    private StringFilter selectedColumns;

    private StringFilter lastStatus;

    private BooleanFilter valid;

    private BooleanFilter isRunning;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter sourceConnectionId;

    private LongFilter snowflakeConnectionId;

    private ZonedDateTimeFilter lastRunTime;

    public MigrationProcessCriteria(){
    }

    public MigrationProcessCriteria(MigrationProcessCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.tablesToMigrate = other.tablesToMigrate == null ? null : other.tablesToMigrate.copy();
        this.selectedColumns = other.selectedColumns == null ? null : other.selectedColumns.copy();
        this.lastStatus = other.lastStatus == null ? null : other.lastStatus.copy();
        this.valid = other.valid == null ? null : other.valid.copy();
        this.isRunning = other.isRunning == null ? null : other.isRunning.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.sourceConnectionId = other.sourceConnectionId == null ? null : other.sourceConnectionId.copy();
        this.snowflakeConnectionId = other.snowflakeConnectionId == null ? null : other.snowflakeConnectionId.copy();
        this.lastRunTime = other.lastRunTime == null ? null : other.lastRunTime.copy();
        
    }

    @Override
    public MigrationProcessCriteria copy() {
        return new MigrationProcessCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getTablesToMigrate() {
        return tablesToMigrate;
    }

    public void setTablesToMigrate(StringFilter tablesToMigrate) {
        this.tablesToMigrate = tablesToMigrate;
    }

    public StringFilter getSelectedColumns() {
        return selectedColumns;
    }

    public void setSelectedColumns(StringFilter selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    public StringFilter getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(StringFilter lastStatus) {
        this.lastStatus = lastStatus;
    }

    public BooleanFilter getValid() {
        return valid;
    }

    public void setvalid(BooleanFilter valid) {
        this.valid = valid;
    }

    public BooleanFilter getIsRunning() {
        return isRunning;
    }

    public void setLastStatus(BooleanFilter isRunning) {
        this.isRunning = isRunning;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LongFilter getSourceConnectionId() {
        return sourceConnectionId;
    }

    public void setSourceConnectionId(LongFilter sourceConnectionId) {
        this.sourceConnectionId = sourceConnectionId;
    }

    public LongFilter getSnowflakeConnectionId() {
        return snowflakeConnectionId;
    }

    public void setSnowflakeConnectionId(LongFilter snowflakeConnectionId) {
        this.snowflakeConnectionId = snowflakeConnectionId;
    }

    public ZonedDateTimeFilter getLastRunTime() {
        return lastRunTime;
    }

    public void setLastRunTime(ZonedDateTimeFilter lastRunTime) {
        this.lastRunTime = lastRunTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MigrationProcessCriteria that = (MigrationProcessCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(type, that.type) &&
            Objects.equals(tablesToMigrate, that.tablesToMigrate) &&
            Objects.equals(selectedColumns, that.selectedColumns) &&
            Objects.equals(lastStatus, that.lastStatus) &&
            Objects.equals(valid, that.valid) &&
            Objects.equals(isRunning, that.isRunning) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(sourceConnectionId, that.sourceConnectionId) &&
            Objects.equals(snowflakeConnectionId, that.snowflakeConnectionId) &&
            Objects.equals(lastRunTime, that.lastRunTime);

    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        type,
        tablesToMigrate,
        selectedColumns,
        lastStatus,
        valid,
        isRunning,
        createdBy,
        createdDate,
        lastModifiedBy,
        lastModifiedDate,
        sourceConnectionId,
        snowflakeConnectionId,
        lastRunTime
        );
    }

    @Override
    public String toString() {
        return "MigrationProcessCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (tablesToMigrate != null ? "tablesToMigrate=" + tablesToMigrate + ", " : "") +
                (selectedColumns != null ? "selectedColumns=" + selectedColumns+ ", " : "") +
                (lastStatus != null ? "lastStatus=" + lastStatus + ", " : "") +
                (valid != null ? "valid=" + valid + ", " : "") +
                (isRunning != null ? "isRunning=" + isRunning + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (sourceConnectionId != null ? "sourceConnectionId=" + sourceConnectionId + ", " : "") +
                (snowflakeConnectionId != null ? "snowflakeConnectionId=" + snowflakeConnectionId + ", " : "") +
                (lastRunTime != null ? "lastRunTime=" + lastRunTime + ", " : "") +
            "}";
    }

}
