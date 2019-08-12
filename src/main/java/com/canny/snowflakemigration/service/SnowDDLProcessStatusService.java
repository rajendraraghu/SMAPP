package com.canny.snowflakemigration.service;

import com.canny.snowflakemigration.service.dto.SnowDDLProcessStatusDTO;
import com.canny.snowflakemigration.domain.SnowDDLProcessStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

/**
 * Service Interface for managing {@link com.canny.snowflakemigration.domain.SnowDDLProcessStatus}.
 */
public interface SnowDDLProcessStatusService {

    /**
     * Save a SnowDDLProcessStatus.
     *
     * @param SnowDDLProcessStatusDTO the entity to save.
     * @return the persisted entity.
     */
    SnowDDLProcessStatusDTO save(SnowDDLProcessStatusDTO SnowDDLProcessStatusDTO);

    /**
     * Get all the SnowDDLProcessStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SnowDDLProcessStatusDTO> findAll(Pageable pageable);


    /**
     * Get the "id" SnowDDLProcessStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SnowDDLProcessStatusDTO> findOne(Long id);

    List<SnowDDLProcessStatus> findAllByProcessId(Long id);

    /**
     * Delete the "id" SnowDDLProcessStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
