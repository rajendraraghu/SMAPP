package com.canny.snowflakemigration.repository;

import com.canny.snowflakemigration.domain.SnowHistoryJobStatus;
import com.canny.snowflakemigration.service.dto.SnowHistoryJobStatusDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;


/**
 * Spring Data  repository for the SnowHistoryJobStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SnowHistoryJobStatusRepository extends JpaRepository<SnowHistoryJobStatus, Long>, JpaSpecificationExecutor<SnowHistoryJobStatus> {
    @Query("SELECT t FROM SnowHistoryJobStatus t where t.batchId = :id") 
    List<SnowHistoryJobStatus> findAllByBatchId(@Param("id") Long id);
}
