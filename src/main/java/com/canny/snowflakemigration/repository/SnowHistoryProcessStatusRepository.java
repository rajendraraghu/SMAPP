package com.canny.snowflakemigration.repository;

import com.canny.snowflakemigration.domain.SnowHistoryProcessStatus;
import com.canny.snowflakemigration.service.dto.SnowHistoryProcessStatusDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;


/**
 * Spring Data  repository for the SnowHistoryProcessStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SnowHistoryProcessStatusRepository extends JpaRepository<SnowHistoryProcessStatus, Long>, JpaSpecificationExecutor<SnowHistoryProcessStatus> {
    @Query("SELECT t FROM SnowHistoryProcessStatus t where t.processId = :id") 
    List<SnowHistoryProcessStatus> findAllByProcessId(@Param("id") Long id);
}
