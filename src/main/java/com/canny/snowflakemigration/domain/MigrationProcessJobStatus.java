package com.canny.snowflakemigration.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A MigrationProcessJobStatus.
 */
@Entity
@Table(name = "sah_tableloadstatus")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MigrationProcessJobStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "tableloadid", nullable = false)
    private Long tableLoadId;

    @NotNull
    @Column(name = "jobid", nullable = false)
    private Long jobId;

    @NotNull
    @Column(name = "tablename", nullable = false)
    private String tableName;

    @Column(name = "tableloadstarttime")
    private Instant tableLoadStartTime;

    @Column(name = "tableloadendtime")
    private Instant tableLoadEndTime;

    @Column(name = "tableloadstatus")
    private String tableLoadStatus;

    @Column(name = "insertcount", nullable = false)
    private Long insertCount;

    @Column(name = "updatecount", nullable = false)
    private Long updateCount;

    @Column(name = "deletecount", nullable = false)
    private Long deleteCount;

    @Column(name = "runtype")
    private String runType;

    @Column(name = "processid")
    private Long processId;

    @Column(name = "processname")
    private String processName;

    @Column(name = "sourcename")
    private String sourceName;

    @Column(name = "destname")
    private String destName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getJobId() {
        return jobId;
    }

    public MigrationProcessJobStatus jobId(Long jobId) {
        this.jobId = jobId;
        return this;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
    
    public Long getTableLoadId() {
        return tableLoadId;
    }

    // public MigrationProcessJobStatus tableLoadId(Long tableLoadId) {
    //     this.tableLoadId = tableLoadId;
    //     return this;
    // }

    public void setTableLoadId(Long tableLoadId) {
        this.tableLoadId = tableLoadId;
    }

    public String getTableName() {
        return tableName;
    }

    public MigrationProcessJobStatus tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Instant getTableLoadStartTime() {
        return tableLoadStartTime;
    }

    public MigrationProcessJobStatus tableLoadStartTime(Instant tableLoadStartTime) {
        this.tableLoadStartTime = tableLoadStartTime;
        return this;
    }

    public void setTableLoadStartTime(Instant tableLoadStartTime) {
        this.tableLoadStartTime = tableLoadStartTime;
    }
    
    public Instant getTableLoadEndTime() {
        return tableLoadEndTime;
    }

    public MigrationProcessJobStatus tableLoadEndTime(Instant tableLoadEndTime) {
        this.tableLoadEndTime = tableLoadEndTime;
        return this;
    }

    public void setTableLoadEndTime(Instant tableLoadEndTime) {
        this.tableLoadEndTime = tableLoadEndTime;
    }

    public String getTableLoadStatus() {
        return tableLoadStatus;
    }

    public MigrationProcessJobStatus tableLoadStatus(String tableLoadStatus) {
        this.tableLoadStatus = tableLoadStatus;
        return this;
    }

    public void setTableLoadStatus(String tableLoadStatus) {
        this.tableLoadStatus = tableLoadStatus;
    }

    public Long getInsertCount() {
        return insertCount;
    }

    public MigrationProcessJobStatus insertCount(Long insertCount) {
        this.insertCount = insertCount;
        return this;
    }

    public void setInsertCount(Long insertCount) {
        this.insertCount = insertCount;
    }

    public Long getUpdateCount() {
        return updateCount;
    }

    public MigrationProcessJobStatus updateCount(Long updateCount) {
        this.updateCount = updateCount;
        return this;
    }

    public void setUpdateCount(Long updateCount) {
        this.updateCount = updateCount;
    }

    public Long getDeleteCount() {
        return deleteCount;
    }

    public MigrationProcessJobStatus deleteCount(Long deleteCount) {
        this.deleteCount = deleteCount;
        return this;
    }

    public void setDeleteCount(Long deleteCount) {
        this.deleteCount = deleteCount;
    }

    public String getRunType() {
        return runType;
    }

    public MigrationProcessJobStatus runType(String runType) {
        this.runType = runType;
        return this;
    }

    public void setRunType(String runType) {
        this.runType = runType;
    }

    public Long getProcessId() {
        return processId;
    }

    public MigrationProcessJobStatus processId(Long processId) {
        this.processId = processId;
        return this;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public MigrationProcessJobStatus processName(String processName) {
        this.processName = processName;
        return this;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public MigrationProcessJobStatus sourceName(String sourceName) {
        this.sourceName = sourceName;
        return this;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDestName() {
        return destName;
    }

    public MigrationProcessJobStatus destName(String destName) {
        this.destName = destName;
        return this;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MigrationProcessJobStatus)) {
            return false;
        }
        return jobId != null && jobId.equals(((MigrationProcessJobStatus) o).jobId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MigrationProcessJobStatus{" +
            "jobId=" + getJobId() +
            ", tableLoadId=" + getTableLoadId() +
            ", tableName='" + getTableName() + "'" +
            ", tableLoadStartTime='" + getTableLoadStartTime() + "'" +
            ", tableLoadEndTime='" + getTableLoadEndTime() + "'" +
            ", tableLoadStatus='" + getTableLoadStatus() + "'" +
            ", insertCount='" + getInsertCount() + "'" +
            ", updateCount='" + getUpdateCount() + "'" +
            ", deleteCount='" + getDeleteCount() + "'" +
            ", runType='" + getRunType() + "'" +
            ", processId='" + getProcessId() + "'" +
            ", processName='" + getProcessName() + "'" +
            ", sourceName='" + getSourceName() + "'" +
            ", destName='" + getDestName() + "'" +
            "}";
    }
}
