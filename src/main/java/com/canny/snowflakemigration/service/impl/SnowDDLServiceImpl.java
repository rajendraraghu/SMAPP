package com.canny.snowflakemigration.service.impl;

import com.canny.snowflakemigration.service.SnowDDLService;
import com.canny.snowflakemigration.domain.SnowDDL;
import com.canny.snowflakemigration.repository.SnowDDLRepository;
import com.canny.snowflakemigration.service.dto.SnowDDLDTO;
import com.canny.snowflakemigration.service.mapper.SnowDDLMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SnowDDL}.
 */
@Service
@Transactional
public class SnowDDLServiceImpl implements SnowDDLService {

    private final Logger log = LoggerFactory.getLogger(SnowDDLServiceImpl.class);

    private final SnowDDLRepository SnowDDLRepository;

    private final SnowDDLMapper SnowDDLMapper;

    public SnowDDLServiceImpl(SnowDDLRepository SnowDDLRepository, SnowDDLMapper SnowDDLMapper) {
        this.SnowDDLRepository = SnowDDLRepository;
        this.SnowDDLMapper = SnowDDLMapper;
    }

    /**
     * Save a SnowDDL.
     *
     * @param SnowDDLDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SnowDDLDTO save(SnowDDLDTO SnowDDLDTO) {
        log.debug("Request to save SnowDDL : {}", SnowDDLDTO);
        SnowDDL SnowDDL = SnowDDLMapper.toEntity(SnowDDLDTO);
        SnowDDL = SnowDDLRepository.save(SnowDDL);
        return SnowDDLMapper.toDto(SnowDDL);
    }

    /**
     * Get all the SnowDDLes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SnowDDLDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SnowDDLes");
        return SnowDDLRepository.findAll(pageable)
            .map(SnowDDLMapper::toDto);
    }


    /**
     * Get one SnowDDL by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SnowDDLDTO> findOne(Long id) {
        log.debug("Request to get SnowDDL : {}", id);
        return SnowDDLRepository.findById(id)
            .map(SnowDDLMapper::toDto);
    }

    /**
     * Delete the SnowDDL by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SnowDDL : {}", id);
        SnowDDLRepository.deleteById(id);
    }
}
 