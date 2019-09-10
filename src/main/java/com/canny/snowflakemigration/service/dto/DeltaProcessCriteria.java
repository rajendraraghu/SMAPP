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
 * Criteria class for the {@link com.canny.snowflakemigration.domain.DeltaProcess} entity. This class is used
 * in {@link com.canny.snowflakemigration.web.rest.DeltaProcessResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /delta-processes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DeltaProcessCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter tablesList;

    private StringFilter selectedColumns;

    private StringFilter pk;

    private StringFilter lastStatus;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter sourceConnectionId;

    private LongFilter snowflakeConnectionId;

    public DeltaProcessCriteria(){
    }

    public DeltaProcessCriteria(DeltaProcessCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.tablesList = other.tablesList == null ? null : other.tablesList.copy();
        this.selectedColumns = other.selectedColumns == null ? null : other.selectedColumns.copy();
        this.pk = other.pk == null ? null : other.pk.copy();
        this.lastStatus = other.lastStatus == null ? null : other.lastStatus.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.sourceConnectionId = other.sourceConnectionId == null ? null : other.sourceConnectionId.copy();
        this.snowflakeConnectionId = other.snowflakeConnectionId == null ? null : other.snowflakeConnectionId.copy();
    }

    @Override
    public DeltaProcessCriteria copy() {
        return new DeltaProcessCriteria(this);
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

    public StringFilter getTablesList() {
        return tablesList;
    }

    public void setTablesList(StringFilter tablesList) {
        this.tablesList = tablesList;
    }

    public StringFilter getSelectedColumns() {
        return selectedColumns;
    }

    public void setSelectedColumns(StringFilter selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    public StringFilter getPk() {
        return pk;
    }

    public void setpk(StringFilter pk) {
        this.pk = pk;
    }

    public StringFilter getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(StringFilter lastStatus) {
        this.lastStatus = lastStatus;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DeltaProcessCriteria that = (DeltaProcessCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(tablesList, that.tablesList) &&
            Objects.equals(selectedColumns, that.selectedColumns) &&
            Objects.equals(pk, that.pk) &&
            Objects.equals(lastStatus, that.lastStatus) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(sourceConnectionId, that.sourceConnectionId) &&
            Objects.equals(snowflakeConnectionId, that.snowflakeConnectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        tablesList,
        selectedColumns,
        pk,
        lastStatus,
        createdBy,
        createdDate,
        lastModifiedBy,
        lastModifiedDate,
        sourceConnectionId,
        snowflakeConnectionId
        );
    }

    @Override
    public String toString() {
        return "DeltaProcessCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (tablesList != null ? "tablesList=" + tablesList + ", " : "") +
                (selectedColumns != null ? "selectedColumns=" + selectedColumns+ ", " : "") +
                (pk != null ? "pk=" + pk + ", " : "") +
                (lastStatus != null ? "lastStatus=" + lastStatus + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (sourceConnectionId != null ? "sourceConnectionId=" + sourceConnectionId + ", " : "") +
                (snowflakeConnectionId != null ? "snowflakeConnectionId=" + snowflakeConnectionId + ", " : "") +
            "}";
    }

}
