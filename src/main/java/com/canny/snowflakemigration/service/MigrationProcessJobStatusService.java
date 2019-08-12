package com.canny.snowflakemigration.service;

import com.canny.snowflakemigration.service.dto.MigrationProcessJobStatusDTO;
import com.canny.snowflakemigration.domain.MigrationProcessJobStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

/**
 * Service Interface for managing {@link com.canny.snowflakemigration.domain.SnowDDL}.
 */
public interface MigrationProcessJobStatusService {

    /**
     * Save a SnowDDL.
     *
     * @param MigrationProcessJobStatusDTO the entity to save.
     * @return the persisted entity.
     */
    MigrationProcessJobStatusDTO save(MigrationProcessJobStatusDTO MigrationProcessJobStatusDTO);

    /**
     * Get all the SnowDDLJobStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MigrationProcessJobStatusDTO> findAll(Pageable pageable);


    /**
     * Get the "id" SnowDDLJobStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MigrationProcessJobStatusDTO> findOne(Long id);

    List<MigrationProcessJobStatus> findAllByJobId(Long id);

    /**
     * Delete the "id" SnowDDLJobStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
