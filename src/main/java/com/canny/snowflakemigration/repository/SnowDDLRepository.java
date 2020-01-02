package com.canny.snowflakemigration.repository;

import com.canny.snowflakemigration.domain.SnowDDL;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SnowDDL entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SnowDDLRepository extends JpaRepository<SnowDDL, Long>, JpaSpecificationExecutor<SnowDDL> {

}
