package com.canny.snowflakemigration.service;

import com.canny.snowflakemigration.service.dto.SnowHistoryJobStatusDTO;
import com.canny.snowflakemigration.domain.SnowHistoryJobStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

/**
 * Service Interface for managing {@link com.canny.snowflakemigration.domain.SnowHistory}.
 */
public interface SnowHistoryJobStatusService {

    /**
     * Save a SnowHistory.
     *
     * @param SnowHistoryJobStatusDTO the entity to save.
     * @return the persisted entity.
     */
    SnowHistoryJobStatusDTO save(SnowHistoryJobStatusDTO SnowHistoryJobStatusDTO);

    /**
     * Get all the SnowHistoryJobStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SnowHistoryJobStatusDTO> findAll(Pageable pageable);


    /**
     * Get the "id"SnowHistoryJobStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SnowHistoryJobStatusDTO> findOne(Long id);

    List<SnowHistoryJobStatus> findAllByBatchId(Long id);

    /**
     * Delete the "id" SnowHistoryJobStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
