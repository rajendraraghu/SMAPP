package com.canny.snowflakemigration.service;

import com.canny.snowflakemigration.service.dto.MigrationProcessStatusDTO;
import com.canny.snowflakemigration.domain.MigrationProcessStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

/**
 * Service Interface for managing {@link com.canny.snowflakemigration.domain.Migration}.
 */
public interface MigrationProcessStatusService {

    /**
     * Save a Migration.
     *
     * @param MigrationProcessStatusDTO the entity to save.
     * @return the persisted entity.
     */
    MigrationProcessStatusDTO save(MigrationProcessStatusDTO MigrationProcessStatusDTO);

    /**
     * Get all the MigrationProcessStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MigrationProcessStatusDTO> findAll(Pageable pageable);


    /**
     * Get the "id" MigrationProcessStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MigrationProcessStatusDTO> findOne(Long id);

    List<MigrationProcessStatus> findAllByProcessId(Long id);

    /**
     * Delete the "id"MigrationProcessStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
