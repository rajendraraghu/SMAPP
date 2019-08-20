package com.canny.snowflakemigration.service;

import com.canny.snowflakemigration.service.dto.DeltaProcessStatusDTO;
import com.canny.snowflakemigration.domain.DeltaProcessStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

/**
 * Service Interface for managing {@link com.canny.snowflakemigration.domain.Delta}.
 */
public interface DeltaProcessStatusService {

    /**
     * Save a Delta.
     *
     * @param DeltaProcessStatusDTO the entity to save.
     * @return the persisted entity.
     */
    DeltaProcessStatusDTO save(DeltaProcessStatusDTO DeltaProcessStatusDTO);

    /**
     * Get all the DeltaProcessStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeltaProcessStatusDTO> findAll(Pageable pageable);


    /**
     * Get the "id" DeltaProcessStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeltaProcessStatusDTO> findOne(Long id);

    List<DeltaProcessStatus> findAllByProcessId(Long id);

    /**
     * Delete the "id"DeltaProcessStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
