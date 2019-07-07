package com.canny.snowflakemigration.repository;

import com.canny.snowflakemigration.domain.MigrationProcess;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MigrationProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MigrationProcessRepository extends JpaRepository<MigrationProcess, Long>, JpaSpecificationExecutor<MigrationProcess> {

}
