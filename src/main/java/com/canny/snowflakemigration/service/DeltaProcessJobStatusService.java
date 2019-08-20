package com.canny.snowflakemigration.service;

import com.canny.snowflakemigration.service.dto.DeltaProcessJobStatusDTO;
import com.canny.snowflakemigration.domain.DeltaProcessJobStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

/**
 * Service Interface for managing {@link com.canny.snowflakemigration.domain.SnowDDL}.
 */
public interface DeltaProcessJobStatusService {

    /**
     * Save a Delta Process.
     *
     * @param DeltaProcessJobStatusDTO the entity to save.
     * @return the persisted entity.
     */
    DeltaProcessJobStatusDTO save(DeltaProcessJobStatusDTO DeltaProcessJobStatusDTO);

    /**
     * Get all the DeltaJobStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeltaProcessJobStatusDTO> findAll(Pageable pageable);


    /**
     * Get the "id" DeltaJobStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeltaProcessJobStatusDTO> findOne(Long id);

    List<DeltaProcessJobStatus> findAllByJobId(Long id);

    /**
     * Delete the "id" SnowDDLJobStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
