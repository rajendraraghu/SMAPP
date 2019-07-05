package com.canny.snowflakemigration.service.impl;

import com.canny.snowflakemigration.service.SourceConnectionService;
import com.canny.snowflakemigration.domain.SourceConnection;
import com.canny.snowflakemigration.repository.SourceConnectionRepository;
import com.canny.snowflakemigration.service.dto.SourceConnectionDTO;
import com.canny.snowflakemigration.service.mapper.SourceConnectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SourceConnection}.
 */
@Service
@Transactional
public class SourceConnectionServiceImpl implements SourceConnectionService {

    private final Logger log = LoggerFactory.getLogger(SourceConnectionServiceImpl.class);

    private final SourceConnectionRepository sourceConnectionRepository;

    private final SourceConnectionMapper sourceConnectionMapper;

    public SourceConnectionServiceImpl(SourceConnectionRepository sourceConnectionRepository, SourceConnectionMapper sourceConnectionMapper) {
        this.sourceConnectionRepository = sourceConnectionRepository;
        this.sourceConnectionMapper = sourceConnectionMapper;
    }

    /**
     * Save a sourceConnection.
     *
     * @param sourceConnectionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SourceConnectionDTO save(SourceConnectionDTO sourceConnectionDTO) {
        log.debug("Request to save SourceConnection : {}", sourceConnectionDTO);
        SourceConnection sourceConnection = sourceConnectionMapper.toEntity(sourceConnectionDTO);
        sourceConnection = sourceConnectionRepository.save(sourceConnection);
        return sourceConnectionMapper.toDto(sourceConnection);
    }

    /**
     * Get all the sourceConnections.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SourceConnectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SourceConnections");
        return sourceConnectionRepository.findAll(pageable)
            .map(sourceConnectionMapper::toDto);
    }


    /**
     * Get one sourceConnection by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SourceConnectionDTO> findOne(Long id) {
        log.debug("Request to get SourceConnection : {}", id);
        return sourceConnectionRepository.findById(id)
            .map(sourceConnectionMapper::toDto);
    }

    /**
     * Delete the sourceConnection by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SourceConnection : {}", id);
        sourceConnectionRepository.deleteById(id);
    }
}
