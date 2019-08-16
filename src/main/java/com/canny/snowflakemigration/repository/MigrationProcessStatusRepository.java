package com.canny.snowflakemigration.repository;

import com.canny.snowflakemigration.domain.MigrationProcessStatus;
import com.canny.snowflakemigration.service.dto.MigrationProcessStatusDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;


/**
 * Spring Data  repository for the SnowDDLProcessStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MigrationProcessStatusRepository extends JpaRepository<MigrationProcessStatus, Long>, JpaSpecificationExecutor<MigrationProcessStatus> {
    @Query("SELECT t FROM MigrationProcessStatus t WHERE t.processId = :id") 
    List<MigrationProcessStatus> findAllByProcessId(@Param("id") Long id);

    @Query("SELECT CAST(t.jobStartTime as text) FROM MigrationProcessStatus t WHERE t.jobId = (SELECT MAX(m.jobId) FROM MigrationProcessStatus m) AND t.processId = :processId")
    String findLastUpdateTime(@Param("processId") Long processId);
}
