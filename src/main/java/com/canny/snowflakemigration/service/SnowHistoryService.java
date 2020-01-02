package com.canny.snowflakemigration.service;

import com.canny.snowflakemigration.service.dto.SnowHistoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.canny.snowflakemigration.domain.SnowHistory}.
 */
public interface SnowHistoryService {

    /**
     * Save a snowHistory.
     *
     * @param snowHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    SnowHistoryDTO save(SnowHistoryDTO snowHistoryDTO);

    /**
     * Get all the snowHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SnowHistoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" snowHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SnowHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" snowHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
