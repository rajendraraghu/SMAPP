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
    @Query("SELECT t FROM MigrationProcessStatus t where t.processId = :id") 
    List<MigrationProcessStatus> findAllByProcessId(@Param("id") Long id);
}