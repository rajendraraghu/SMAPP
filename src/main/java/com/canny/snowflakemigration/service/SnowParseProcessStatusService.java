package com.canny.snowflakemigration.service;

import com.canny.snowflakemigration.service.dto.SnowParseProcessStatusDTO;
import com.canny.snowflakemigration.domain.SnowParseProcessStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

/**
 * Service Interface for managing {@link com.canny.snowflakemigration.domain.SnowParseProcessStatus}.
 */
public interface SnowParseProcessStatusService {

    /**
     * Save a SnowParseProcessStatus.
     *
     * @param SnowParseProcessStatusDTO the entity to save.
     * @return the persisted entity.
     */
    SnowParseProcessStatusDTO save(SnowParseProcessStatusDTO SnowParseProcessStatusDTO);

    /**
     * Get all the SnowParseProcessStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SnowParseProcessStatusDTO> findAll(Pageable pageable);


    /**
     * Get the "id" SnowParseProcessStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SnowParseProcessStatusDTO> findOne(Long id);

    List<SnowParseProcessStatus> findAllByProcessId(Long id);

    /**
     * Delete the "id" SnowParseProcessStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
