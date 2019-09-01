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
 * Criteria class for the {@link com.canny.snowflakemigration.domain.SourceConnection} entity. This class is used
 * in {@link com.canny.snowflakemigration.web.rest.SourceConnectionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /source-connections?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SourceConnectionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter sourceType;

    private StringFilter url;

    private StringFilter username;

    private StringFilter password;

    private StringFilter database;

    private StringFilter host;

    private StringFilter portnumber;

    private StringFilter schema;

    private BooleanFilter valid;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    public SourceConnectionCriteria(){
    }

    public SourceConnectionCriteria(SourceConnectionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.sourceType = other.sourceType == null ? null : other.sourceType.copy();
        this.url = other.url == null ? null : other.url.copy();
        this.username = other.username == null ? null : other.username.copy();
        this.password = other.password == null ? null : other.password.copy();
        this.database = other.database == null ? null : other.database.copy();
        this.host = other.host == null ? null : other.host.copy();
        this.portnumber = other.portnumber == null ? null : other.portnumber.copy();
        this.schema = other.schema == null ? null : other.schema.copy();
        this.valid = other.valid == null ? null : other.valid.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
    }

    @Override
    public SourceConnectionCriteria copy() {
        return new SourceConnectionCriteria(this);
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

    public StringFilter getSourceType() {
        return sourceType;
    }

    public void setSourceType(StringFilter sourceType) {
        this.sourceType = sourceType;
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

    public StringFilter getDatabase() {
        return database;
    }

    public void setDatabase(StringFilter database) {
        this.database = database;
    }

    public StringFilter getHost() {
        return host;
    }

    public void setHost(StringFilter host) {
        this.host = host;
    }

    public StringFilter getPortnumber() {
        return portnumber;
    }

    public void setPortnumber(StringFilter portnumber) {
        this.portnumber = portnumber;
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
        final SourceConnectionCriteria that = (SourceConnectionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(sourceType, that.sourceType) &&
            Objects.equals(url, that.url) &&
            Objects.equals(username, that.username) &&
            Objects.equals(password, that.password) &&
            Objects.equals(database, that.database) &&
            Objects.equals(host, that.host) &&
            Objects.equals(portnumber, that.portnumber) &&
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
        sourceType,
        url,
        username,
        password,
        database,
        host,
        portnumber,
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
        return "SourceConnectionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (sourceType != null ? "sourceType=" + sourceType + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
                (username != null ? "username=" + username + ", " : "") +
                (password != null ? "password=" + password + ", " : "") +
                (database != null ? "database=" + database + ", " : "") +
                (host != null ? "host=" + host + ", " : "") +
                (portnumber != null ? "portnumber=" + portnumber + ", " : "") +
                (schema != null ? "schema=" + schema + ", " : "") +
                (valid != null ? "valid=" + valid + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            "}";
    }

}
