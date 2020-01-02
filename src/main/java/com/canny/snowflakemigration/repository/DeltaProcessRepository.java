package com.canny.snowflakemigration.repository;

import com.canny.snowflakemigration.domain.DeltaProcess;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DeltaProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeltaProcessRepository extends JpaRepository<DeltaProcess, Long>, JpaSpecificationExecutor<DeltaProcess> {

}
