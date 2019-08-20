package com.canny.snowflakemigration.repository;

import com.canny.snowflakemigration.domain.DeltaProcessStatus;
import com.canny.snowflakemigration.service.dto.DeltaProcessStatusDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;


/**
 * Spring Data  repository for the DeltaProcessStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeltaProcessStatusRepository extends JpaRepository<DeltaProcessStatus, Long>, JpaSpecificationExecutor<DeltaProcessStatus> {
    @Query("SELECT t FROM DeltaProcessStatus t where t.processId = :id")
    List<DeltaProcessStatus> findAllByProcessId(@Param("id") Long id);
}
