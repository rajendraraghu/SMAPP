package com.canny.snowflakemigration.service.impl;

import com.canny.snowflakemigration.service.SnowflakeConnectionService;
import com.canny.snowflakemigration.domain.SnowflakeConnection;
import com.canny.snowflakemigration.repository.SnowflakeConnectionRepository;
import com.canny.snowflakemigration.service.dto.SnowflakeConnectionDTO;
import com.canny.snowflakemigration.service.mapper.SnowflakeConnectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SnowflakeConnection}.
 */
@Service
@Transactional
public class SnowflakeConnectionServiceImpl implements SnowflakeConnectionService {

    private final Logger log = LoggerFactory.getLogger(SnowflakeConnectionServiceImpl.class);

    private final SnowflakeConnectionRepository snowflakeConnectionRepository;

    private final SnowflakeConnectionMapper snowflakeConnectionMapper;

    public SnowflakeConnectionServiceImpl(SnowflakeConnectionRepository snowflakeConnectionRepository, SnowflakeConnectionMapper snowflakeConnectionMapper) {
        this.snowflakeConnectionRepository = snowflakeConnectionRepository;
        this.snowflakeConnectionMapper = snowflakeConnectionMapper;
    }

    /**
     * Save a snowflakeConnection.
     *
     * @param snowflakeConnectionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SnowflakeConnectionDTO save(SnowflakeConnectionDTO snowflakeConnectionDTO) {
        log.debug("Request to save SnowflakeConnection : {}", snowflakeConnectionDTO);
        SnowflakeConnection snowflakeConnection = snowflakeConnectionMapper.toEntity(snowflakeConnectionDTO);
        snowflakeConnection = snowflakeConnectionRepository.save(snowflakeConnection);
        return snowflakeConnectionMapper.toDto(snowflakeConnection);
    }

    /**
     * Get all the snowflakeConnections.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SnowflakeConnectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SnowflakeConnections");
        return snowflakeConnectionRepository.findAll(pageable)
            .map(snowflakeConnectionMapper::toDto);
    }


    /**
     * Get one snowflakeConnection by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SnowflakeConnectionDTO> findOne(Long id) {
        log.debug("Request to get SnowflakeConnection : {}", id);
        return snowflakeConnectionRepository.findById(id)
            .map(snowflakeConnectionMapper::toDto);
    }

    /**
     * Delete the snowflakeConnection by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SnowflakeConnection : {}", id);
        snowflakeConnectionRepository.deleteById(id);
    }
}
