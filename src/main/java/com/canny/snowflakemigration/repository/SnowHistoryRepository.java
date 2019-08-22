package com.canny.snowflakemigration.repository;

import com.canny.snowflakemigration.domain.SnowHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SnowHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SnowHistoryRepository extends JpaRepository<SnowHistory, Long>, JpaSpecificationExecutor<SnowHistory> {

}
