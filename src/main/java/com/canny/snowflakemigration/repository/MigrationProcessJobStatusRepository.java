package com.canny.snowflakemigration.repository;

import com.canny.snowflakemigration.domain.MigrationProcessJobStatus;
import com.canny.snowflakemigration.service.dto.MigrationProcessJobStatusDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;


/**
 * Spring Data  repository for the SnowDDLJobStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MigrationProcessJobStatusRepository extends JpaRepository<MigrationProcessJobStatus, Long>, JpaSpecificationExecutor<MigrationProcessJobStatus> {
    @Query("SELECT t FROM MigrationProcessJobStatus t where t.jobId = :id") 
    List<MigrationProcessJobStatus> findAllByJobId(@Param("id") Long id);
}
