package com.canny.snowflakemigration.repository;

import com.canny.snowflakemigration.domain.SnowParseJobStatus;
import com.canny.snowflakemigration.service.dto.SnowParseJobStatusDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;


/**
 * Spring Data  repository for the SnowParseJobStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SnowParseJobStatusRepository extends JpaRepository<SnowParseJobStatus, Long>, JpaSpecificationExecutor<SnowParseJobStatus> {
    @Query("SELECT t FROM SnowParseJobStatus t where t.batchId = :id ORDER BY t.startTime DESC") 
    List<SnowParseJobStatus> findAllByBatchId(@Param("id") Long id);
}
