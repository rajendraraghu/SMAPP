package com.canny.snowflakemigration.service;

import com.canny.snowflakemigration.service.dto.MigrationProcessDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.canny.snowflakemigration.domain.MigrationProcess}.
 */
public interface MigrationProcessService {

    /**
     * Save a migrationProcess.
     *
     * @param migrationProcessDTO the entity to save.
     * @return the persisted entity.
     */
    MigrationProcessDTO save(MigrationProcessDTO migrationProcessDTO);

    /**
     * Get all the migrationProcesses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MigrationProcessDTO> findAll(Pageable pageable);


    /**
     * Get the "id" migrationProcess.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MigrationProcessDTO> findOne(Long id);

    /**
     * Delete the "id" migrationProcess.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
