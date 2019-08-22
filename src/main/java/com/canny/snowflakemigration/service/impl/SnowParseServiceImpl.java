package com.canny.snowflakemigration.service.impl;

import com.canny.snowflakemigration.service.SnowParseService;
import com.canny.snowflakemigration.domain.SnowParse;
import com.canny.snowflakemigration.repository.SnowParseRepository;
import com.canny.snowflakemigration.service.dto.SnowParseDTO;
import com.canny.snowflakemigration.service.mapper.SnowParseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SnowParse}.
 */
@Service
@Transactional
public class SnowParseServiceImpl implements SnowParseService {

    private final Logger log = LoggerFactory.getLogger(SnowParseServiceImpl.class);

    private final SnowParseRepository SnowParseRepository;

    private final SnowParseMapper SnowParseMapper;

    public SnowParseServiceImpl(SnowParseRepository SnowParseRepository, SnowParseMapper SnowParseMapper) {
        this.SnowParseRepository = SnowParseRepository;
        this.SnowParseMapper = SnowParseMapper;
    }

    /**
     * Save a SnowParse.
     *
     * @param SnowParseDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SnowParseDTO save(SnowParseDTO SnowParseDTO) {
        log.debug("Request to save SnowParse : {}", SnowParseDTO);
        SnowParse SnowParse = SnowParseMapper.toEntity(SnowParseDTO);
        SnowParse = SnowParseRepository.save(SnowParse);
        return SnowParseMapper.toDto(SnowParse);
    }

    /**
     * Get all the SnowParsees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SnowParseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SnowParsees");
        return SnowParseRepository.findAll(pageable)
            .map(SnowParseMapper::toDto);
    }


    /**
     * Get one SnowParse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SnowParseDTO> findOne(Long id) {
        log.debug("Request to get SnowParse : {}", id);
        return SnowParseRepository.findById(id)
            .map(SnowParseMapper::toDto);
    }

    /**
     * Delete the SnowParse by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SnowParse : {}", id);
        SnowParseRepository.deleteById(id);
    }
}
 