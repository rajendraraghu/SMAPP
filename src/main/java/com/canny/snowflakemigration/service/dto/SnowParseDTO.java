package com.canny.snowflakemigration.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.canny.snowflakemigration.domain.SnowParse} entity.
 */
public class SnowParseDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @Size(max = 650)
    private String description;

    private String sourceType;

    private String sourcePath;

    private String lastStatus;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Long sourceConnectionId;

    private String sourceConnectionName;
    
    private String sourceSystem;

    private String sourceConnectionUrl;

    private String sourceConnectionUsername;

    private String sourceConnectionPassword;

    private String sourceConnectionDatabase;

    private String sourceConnectionSchema;

    private Long snowflakeConnectionId;

    private String snowflakeConnectionName;

    private String snowflakeConnectionUrl;

    private String snowflakeConnectionUsername;

    private String snowflakeConnectionPassword;

    private String snowflakeConnectionAcct;

    private String snowflakeConnectionWarehouse;

    private String snowflakeConnectionDatabase;

    private String snowflakeConnectionSchema;

    private String runBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getSourceConnectionId() {
        return sourceConnectionId;
    }

    public void setSourceConnectionId(Long sourceConnectionId) {
        this.sourceConnectionId = sourceConnectionId;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setsourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }
    public String getSourceConnectionName() {
        return sourceConnectionName;
    }

    public void setSourceConnectionName(String sourceConnectionName) {
        this.sourceConnectionName = sourceConnectionName;
    }

    public String getSourceConnectionUrl() {
        return sourceConnectionUrl;
    }

    public void setSourceConnectionUrl(String sourceConnectionUrl) {
        this.sourceConnectionUrl = sourceConnectionUrl;
    }

    public String getSourceConnectionUsername() {
        return sourceConnectionUsername;
    }

    public void setSourceConnectionUsername(String sourceConnectionUsername) {
        this.sourceConnectionUsername = sourceConnectionUsername;
    }

    public String getSourceConnectionPassword() {
        return sourceConnectionPassword;
    }

    public void setSourceConnectionPassword(String sourceConnectionPassword) {
        this.sourceConnectionPassword = sourceConnectionPassword;
    }

    public String getSourceConnectionDatabase() {
        return sourceConnectionDatabase;
    }

    public void setSourceConnectionDatabase(String sourceConnectionDatabase) {
        this.sourceConnectionDatabase = sourceConnectionDatabase;
    }

    public String getSourceConnectionSchema() {
        return sourceConnectionSchema;
    }

    public void setSourceConnectionSchema(String sourceConnectionSchema) {
        this.sourceConnectionSchema = sourceConnectionSchema;
    }

    public Long getSnowflakeConnectionId() {
        return snowflakeConnectionId;
    }

    public void setSnowflakeConnectionId(Long snowflakeConnectionId) {
        this.snowflakeConnectionId = snowflakeConnectionId;
    }

    public String getSnowflakeConnectionName() {
        return snowflakeConnectionName;
    }

    public void setSnowflakeConnectionName(String snowflakeConnectionName) {
        this.snowflakeConnectionName = snowflakeConnectionName;
    }

    public String getSnowflakeConnectionUrl() {
        return snowflakeConnectionUrl;
    }

    public void setSnowflakeConnectionUrl(String snowflakeConnectionUrl) {
        this.snowflakeConnectionUrl = snowflakeConnectionUrl;
    }

    public String getSnowflakeConnectionUsername() {
        return snowflakeConnectionUsername;
    }

    public void setSnowflakeConnectionUsername(String snowflakeConnectionUsername) {
        this.snowflakeConnectionUsername = snowflakeConnectionUsername;
    }

    public String getSnowflakeConnectionPassword() {
        return snowflakeConnectionPassword;
    }

    public void setSnowflakeConnectionPassword(String snowflakeConnectionPassword) {
        this.snowflakeConnectionPassword = snowflakeConnectionPassword;
    }

    public String getSnowflakeConnectionAcct() {
        return snowflakeConnectionAcct;
    }

    public void setSnowflakeConnectionAcct(String snowflakeConnectionAcct) {
        this.snowflakeConnectionAcct = snowflakeConnectionAcct;
    }

    public String getSnowflakeConnectionWarehouse() {
        return snowflakeConnectionWarehouse;
    }

    public void setSnowflakeConnectionWarehouse(String snowflakeConnectionWarehouse) {
        this.snowflakeConnectionWarehouse = snowflakeConnectionWarehouse;
    }

    public String getSnowflakeConnectionDatabase() {
        return snowflakeConnectionDatabase;
    }

    public void setSnowflakeConnectionDatabase(String snowflakeConnectionDatabase) {
        this.snowflakeConnectionDatabase = snowflakeConnectionDatabase;
    }

    public String getSnowflakeConnectionSchema() {
        return snowflakeConnectionSchema;
    }

    public void setSnowflakeConnectionSchema(String snowflakeConnectionSchema) {
        this.snowflakeConnectionSchema = snowflakeConnectionSchema;
    }

    public String getRunBy() {
        return runBy;
    }

    public void setRunBy(String runBy) {
        this.runBy = runBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SnowParseDTO SnowParseDTO = (SnowParseDTO) o;
        if (SnowParseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), SnowParseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SnowParseDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", sourceType='" + getSourceType() + "'" +
            ", lastStatus='" + getLastStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", sourceConnectionId=" + getSourceConnectionId() +
            ", sourceConnectionName='" + getSourceConnectionName() + "'" +
            ", snowflakeConnectionId=" + getSnowflakeConnectionId() +
            ", snowflakeConnectionName='" + getSnowflakeConnectionName() + "'" +
            ", runBy='" + getRunBy() + "'" +
            "}";
    }
}
