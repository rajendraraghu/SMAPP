package com.canny.snowflakemigration.service;

import com.canny.snowflakemigration.service.dto.SnowDDLJobStatusDTO;
import com.canny.snowflakemigration.domain.SnowDDLJobStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

/**
 * Service Interface for managing {@link com.canny.snowflakemigration.domain.SnowDDL}.
 */
public interface SnowDDLJobStatusService {

    /**
     * Save a SnowDDL.
     *
     * @param SnowDDLJobStatusDTO the entity to save.
     * @return the persisted entity.
     */
    SnowDDLJobStatusDTO save(SnowDDLJobStatusDTO SnowDDLJobStatusDTO);

    /**
     * Get all the SnowDDLJobStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SnowDDLJobStatusDTO> findAll(Pageable pageable);


    /**
     * Get the "id" SnowDDLJobStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SnowDDLJobStatusDTO> findOne(Long id);

    List<SnowDDLJobStatus> findAllByBatchId(Long id);

    /**
     * Delete the "id" SnowDDLJobStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
