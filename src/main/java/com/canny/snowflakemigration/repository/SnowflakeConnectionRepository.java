package com.canny.snowflakemigration.repository;

import com.canny.snowflakemigration.domain.SnowflakeConnection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SnowflakeConnection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SnowflakeConnectionRepository extends JpaRepository<SnowflakeConnection, Long>, JpaSpecificationExecutor<SnowflakeConnection> {

}
