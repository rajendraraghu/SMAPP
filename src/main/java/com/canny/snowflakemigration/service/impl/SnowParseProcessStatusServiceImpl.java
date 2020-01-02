package com.canny.snowflakemigration.service.impl;

import com.canny.snowflakemigration.service.SnowParseProcessStatusService;
import com.canny.snowflakemigration.domain.SnowParseProcessStatus;
import com.canny.snowflakemigration.repository.SnowParseProcessStatusRepository;
import com.canny.snowflakemigration.service.dto.SnowParseProcessStatusDTO;
import com.canny.snowflakemigration.service.mapper.SnowParseProcessStatusMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

/**
 * Service Implementation for managing {@link SnowParseProcessStatus}.
 */
@Service
@Transactional
public class SnowParseProcessStatusServiceImpl implements SnowParseProcessStatusService {

    private final Logger log = LoggerFactory.getLogger(SnowParseProcessStatusServiceImpl.class);

    private final SnowParseProcessStatusRepository SnowParseProcessStatusRepository;

    private final SnowParseProcessStatusMapper SnowParseProcessStatusMapper;

    public SnowParseProcessStatusServiceImpl(SnowParseProcessStatusRepository SnowParseProcessStatusRepository, SnowParseProcessStatusMapper SnowParseProcessStatusMapper) {
        this.SnowParseProcessStatusRepository = SnowParseProcessStatusRepository;
        this.SnowParseProcessStatusMapper = SnowParseProcessStatusMapper;
    }

    /**
     * Save a SnowParseProcessStatus.
     *
     * @param SnowParseProcessStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SnowParseProcessStatusDTO save(SnowParseProcessStatusDTO SnowParseProcessStatusDTO) {
        log.debug("Request to save SnowParseProcessStatus : {}", SnowParseProcessStatusDTO);
        SnowParseProcessStatus SnowParseProcessStatus = SnowParseProcessStatusMapper.toEntity(SnowParseProcessStatusDTO);
        SnowParseProcessStatus = SnowParseProcessStatusRepository.save(SnowParseProcessStatus);
        return SnowParseProcessStatusMapper.toDto(SnowParseProcessStatus);
    }

    /**
     * Get all the SnowParseProcessStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SnowParseProcessStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SnowParseProcessStatuses");
        return SnowParseProcessStatusRepository.findAll(pageable)
            .map(SnowParseProcessStatusMapper::toDto);
    }


    /**
     * Get one SnowParseProcessStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SnowParseProcessStatusDTO> findOne(Long id) {
        log.debug("Request to get SnowParseProcessStatus : {}", id);
        return SnowParseProcessStatusRepository.findById(id)
            .map(SnowParseProcessStatusMapper::toDto);
    }

    /**
     * Delete the SnowParseProcessStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SnowParseProcessStatus : {}", id);
        SnowParseProcessStatusRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SnowParseProcessStatus> findAllByProcessId(Long id) {
        log.debug("Request to get SnowParseProcessStatus : {}", id);
        return SnowParseProcessStatusRepository.findAllByProcessId(id);//.map(SnowParseProcessStatusMapper::toDto);
    }
}
 