package com.canny.snowflakemigration.repository;

import com.canny.snowflakemigration.domain.SnowDDLProcessStatus;
import com.canny.snowflakemigration.service.dto.SnowDDLProcessStatusDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;


/**
 * Spring Data  repository for the SnowDDLProcessStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SnowDDLProcessStatusRepository extends JpaRepository<SnowDDLProcessStatus, Long>, JpaSpecificationExecutor<SnowDDLProcessStatus> {
    @Query("SELECT t FROM SnowDDLProcessStatus t where t.processId = :id ORDER BY t.startTime DESC") 
    List<SnowDDLProcessStatus> findAllByProcessId(@Param("id") Long id);
}
