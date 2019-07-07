package com.canny.snowflakemigration.service;

import com.canny.snowflakemigration.service.dto.SnowflakeConnectionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.canny.snowflakemigration.domain.SnowflakeConnection}.
 */
public interface SnowflakeConnectionService {

    /**
     * Save a snowflakeConnection.
     *
     * @param snowflakeConnectionDTO the entity to save.
     * @return the persisted entity.
     */
    SnowflakeConnectionDTO save(SnowflakeConnectionDTO snowflakeConnectionDTO);

    /**
     * Get all the snowflakeConnections.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SnowflakeConnectionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" snowflakeConnection.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SnowflakeConnectionDTO> findOne(Long id);

    /**
     * Delete the "id" snowflakeConnection.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
