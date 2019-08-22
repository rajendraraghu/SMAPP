package com.canny.snowflakemigration.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.canny.snowflakemigration.domain.SnowParse} entity.
 */
public class SnowParseJobStatusDTO implements Serializable {

    private Long jobId;

    @NotNull
    private Long batchId;

    @NotNull
    private String name;

    private Instant startTime;

    private Instant endTime;

    private String status;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
    
    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

        SnowParseJobStatusDTO SnowParseJobStatusDTO = (SnowParseJobStatusDTO) o;
        if (SnowParseJobStatusDTO.getJobId() == null || getJobId() == null) {
            return false;
        }
        return Objects.equals(getJobId(), SnowParseJobStatusDTO.getJobId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getJobId());
    }

    @Override
    public String toString() {
        return "SnowParseJobStatusDTO{" +
            "jobId=" + getJobId() +
            ", batchId=" + getBatchId() +
            ", name='" + getName() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
