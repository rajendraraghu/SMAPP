package com.canny.snowflakemigration.service;

import com.canny.snowflakemigration.service.dto.SourceConnectionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.canny.snowflakemigration.domain.SourceConnection}.
 */
public interface SourceConnectionService {

    /**
     * Save a sourceConnection.
     *
     * @param sourceConnectionDTO the entity to save.
     * @return the persisted entity.
     */
    SourceConnectionDTO save(SourceConnectionDTO sourceConnectionDTO);

    /**
     * Get all the sourceConnections.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SourceConnectionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" sourceConnection.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SourceConnectionDTO> findOne(Long id);

    /**
     * Delete the "id" sourceConnection.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
