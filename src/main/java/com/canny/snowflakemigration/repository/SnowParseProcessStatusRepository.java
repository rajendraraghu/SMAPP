package com.canny.snowflakemigration.repository;

import com.canny.snowflakemigration.domain.SnowParseProcessStatus;
import com.canny.snowflakemigration.service.dto.SnowParseProcessStatusDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;


/**
 * Spring Data  repository for the SnowParseProcessStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SnowParseProcessStatusRepository extends JpaRepository<SnowParseProcessStatus, Long>, JpaSpecificationExecutor<SnowParseProcessStatus> {
    @Query("SELECT t FROM SnowParseProcessStatus t where t.processId = :id") 
    List<SnowParseProcessStatus> findAllByProcessId(@Param("id") Long id);
}
