package com.canny.snowflakemigration.service;

import com.canny.snowflakemigration.service.dto.SnowDDLDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.canny.snowflakemigration.domain.SnowDDL}.
 */
public interface SnowDDLService {

    /**
     * Save a SnowDDL.
     *
     * @param SnowDDLDTO the entity to save.
     * @return the persisted entity.
     */
    SnowDDLDTO save(SnowDDLDTO SnowDDLDTO);

    /**
     * Get all the SnowDDLes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SnowDDLDTO> findAll(Pageable pageable);
    /**
     * Get the "id" SnowDDL.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SnowDDLDTO> findOne(Long id);
    /**
     * Delete the "id" SnowDDL.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
