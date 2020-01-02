package com.canny.snowflakemigration.service;

import com.canny.snowflakemigration.service.dto.SnowHistoryProcessStatusDTO;
import com.canny.snowflakemigration.domain.SnowHistoryProcessStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

/**
 * Service Interface for managing {@link com.canny.snowflakemigration.domain.SnowHistoryProcessStatus}.
 */
public interface SnowHistoryProcessStatusService {

    /**
     * Save a SnowHistoryProcessStatus.
     *
     * @param SnowHistoryProcessStatusDTO the entity to save.
     * @return the persisted entity.
     */
    SnowHistoryProcessStatusDTO save(SnowHistoryProcessStatusDTO SnowHistoryProcessStatusDTO);

    /**
     * Get all the SnowHistoryProcessStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SnowHistoryProcessStatusDTO> findAll(Pageable pageable);


    /**
     * Get the "id" SnowHistoryProcessStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SnowHistoryProcessStatusDTO> findOne(Long id);

    List<SnowHistoryProcessStatus> findAllByProcessId(Long id);

    /**
     * Delete the "id" SnowHistoryProcessStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
