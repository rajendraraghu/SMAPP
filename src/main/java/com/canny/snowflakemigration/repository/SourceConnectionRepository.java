package com.canny.snowflakemigration.repository;

import com.canny.snowflakemigration.domain.SourceConnection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SourceConnection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SourceConnectionRepository extends JpaRepository<SourceConnection, Long>, JpaSpecificationExecutor<SourceConnection> {

}
