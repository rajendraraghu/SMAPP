package com.canny.snowflakemigration.repository;

import com.canny.snowflakemigration.domain.SnowDDLJobStatus;
import com.canny.snowflakemigration.service.dto.SnowDDLJobStatusDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;


/**
 * Spring Data  repository for the SnowDDLJobStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SnowDDLJobStatusRepository extends JpaRepository<SnowDDLJobStatus, Long>, JpaSpecificationExecutor<SnowDDLJobStatus> {
    @Query("SELECT t FROM SnowDDLJobStatus t where t.batchId = :id ORDER BY t.startTime DESC") 
    List<SnowDDLJobStatus> findAllByBatchId(@Param("id") Long id);
}
