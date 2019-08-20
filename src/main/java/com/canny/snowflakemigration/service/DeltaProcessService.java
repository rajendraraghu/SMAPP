package com.canny.snowflakemigration.service;

import com.canny.snowflakemigration.service.dto.DeltaProcessDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.canny.snowflakemigration.domain.DeltaProcess}.
 */
public interface DeltaProcessService {

    /**
     * Save a deltaProcess.
     *
     * @param deltaProcessDTO the entity to save.
     * @return the persisted entity.
     */
    DeltaProcessDTO save(DeltaProcessDTO deltaProcessDTO);

    /**
     * Get all the deltaProcesses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeltaProcessDTO> findAll(Pageable pageable);


    /**
     * Get the "id" deltaProcess.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeltaProcessDTO> findOne(Long id);

    /**
     * Delete the "id" deltaProcess.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
