package com.canny.snowflakemigration.service;

import com.canny.snowflakemigration.service.dto.SnowParseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.canny.snowflakemigration.domain.SnowParse}.
 */
public interface SnowParseService {

    /**
     * Save a SnowParse.
     *
     * @param SnowParseDTO the entity to save.
     * @return the persisted entity.
     */
    SnowParseDTO save(SnowParseDTO SnowParseDTO);

    /**
     * Get all the SnowParsees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SnowParseDTO> findAll(Pageable pageable);
    /**
     * Get the "id" SnowParse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SnowParseDTO> findOne(Long id);
    /**
     * Delete the "id" SnowParse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
