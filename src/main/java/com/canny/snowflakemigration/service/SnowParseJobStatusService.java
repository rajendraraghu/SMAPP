package com.canny.snowflakemigration.service;

import com.canny.snowflakemigration.service.dto.SnowParseJobStatusDTO;
import com.canny.snowflakemigration.domain.SnowParseJobStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

/**
 * Service Interface for managing {@link com.canny.snowflakemigration.domain.SnowParse}.
 */
public interface SnowParseJobStatusService {

    /**
     * Save a SnowParse.
     *
     * @param SnowParseJobStatusDTO the entity to save.
     * @return the persisted entity.
     */
    SnowParseJobStatusDTO save(SnowParseJobStatusDTO SnowParseJobStatusDTO);

    /**
     * Get all the SnowParseJobStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SnowParseJobStatusDTO> findAll(Pageable pageable);


    /**
     * Get the "id" SnowParseJobStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SnowParseJobStatusDTO> findOne(Long id);

    List<SnowParseJobStatus> findAllByBatchId(Long id);

    /**
     * Delete the "id" SnowParseJobStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
