package com.canny.snowflakemigration.repository;

import com.canny.snowflakemigration.domain.SnowParse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SnowParse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SnowParseRepository extends JpaRepository<SnowParse, Long>, JpaSpecificationExecutor<SnowParse> {

}
