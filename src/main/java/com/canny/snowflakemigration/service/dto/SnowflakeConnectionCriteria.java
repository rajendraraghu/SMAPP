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
 * Criteria class for the {@link com.canny.snowflakemigration.domain.SnowflakeConnection} entity. This class is used
 * in {@link com.canny.snowflakemigration.web.rest.SnowflakeConnectionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /snowflake-connections?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SnowflakeConnectionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter regionId;

    private StringFilter url;

    private StringFilter username;

    private StringFilter password;

    private StringFilter acct;

    private StringFilter warehouse;

    private StringFilter database;

    private StringFilter schema;

    private BooleanFilter valid;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    public SnowflakeConnectionCriteria(){
    }

    public SnowflakeConnectionCriteria(SnowflakeConnectionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.regionId = other.regionId == null ? null : other.regionId.copy();
        this.url = other.url == null ? null : other.url.copy();
        this.username = other.username == null ? null : other.username.copy();
        this.password = other.password == null ? null : other.password.copy();
        this.acct = other.acct == null ? null : other.acct.copy();
        this.warehouse = other.warehouse == null ? null : other.warehouse.copy();
        this.database = other.database == null ? null : other.database.copy();
        this.schema = other.schema == null ? null : other.schema.copy();
        this.valid = other.valid == null ? null : other.valid.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
    }

    @Override
    public SnowflakeConnectionCriteria copy() {
        return new SnowflakeConnectionCriteria(this);
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

    public StringFilter getRegionId() {
        return regionId;
    }

    public void setRegionId(StringFilter regionId) {
        this.regionId = regionId;
    }

    public StringFilter getUrl() {
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public StringFilter getUsername() {
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public StringFilter getPassword() {
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public StringFilter getAcct() {
        return acct;
    }

    public void setAcct(StringFilter acct) {
        this.acct = acct;
    }

    public StringFilter getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(StringFilter warehouse) {
        this.warehouse = warehouse;
    }

    public StringFilter getDatabase() {
        return database;
    }

    public void setDatabase(StringFilter database) {
        this.database = database;
    }

    public StringFilter getSchema() {
        return schema;
    }

    public void setSchema(StringFilter schema) {
        this.schema = schema;
    }

    public BooleanFilter getValid() {
        return valid;
    }

    public void setValid(BooleanFilter valid) {
        this.valid = valid;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SnowflakeConnectionCriteria that = (SnowflakeConnectionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(regionId, that.regionId) &&
            Objects.equals(url, that.url) &&
            Objects.equals(username, that.username) &&
            Objects.equals(password, that.password) &&
            Objects.equals(acct, that.acct) &&
            Objects.equals(warehouse, that.warehouse) &&
            Objects.equals(database, that.database) &&
            Objects.equals(schema, that.schema) &&
            Objects.equals(valid, that.valid) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        regionId,
        url,
        username,
        password,
        acct,
        warehouse,
        database,
        schema,
        valid,
        createdBy,
        createdDate,
        lastModifiedBy,
        lastModifiedDate
        );
    }

    @Override
    public String toString() {
        return "SnowflakeConnectionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (regionId != null ? "regionId=" + regionId + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
                (username != null ? "username=" + username + ", " : "") +
                (password != null ? "password=" + password + ", " : "") +
                (acct != null ? "acct=" + acct + ", " : "") +
                (warehouse != null ? "warehouse=" + warehouse + ", " : "") +
                (database != null ? "database=" + database + ", " : "") +
                (schema != null ? "schema=" + schema + ", " : "") +
                (valid != null ? "valid=" + valid + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            "}";
    }

}
