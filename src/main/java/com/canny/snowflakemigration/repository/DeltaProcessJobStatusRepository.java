package com.canny.snowflakemigration.repository;

import com.canny.snowflakemigration.domain.DeltaProcessJobStatus;
import com.canny.snowflakemigration.service.dto.DeltaProcessJobStatusDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;


/**
 * Spring Data  repository for the DeltaJobStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeltaProcessJobStatusRepository extends JpaRepository<DeltaProcessJobStatus, Long>, JpaSpecificationExecutor<DeltaProcessJobStatus> {
    @Query("SELECT t FROM DeltaProcessJobStatus t where t.jobId = :id ORDER BY t.tableLoadStartTime DESC")
    List<DeltaProcessJobStatus> findAllByJobId(@Param("id") Long id);
}
