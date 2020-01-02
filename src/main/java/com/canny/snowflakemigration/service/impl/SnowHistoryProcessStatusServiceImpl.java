package com.canny.snowflakemigration.service.impl;

import com.canny.snowflakemigration.service.SnowHistoryProcessStatusService;
import com.canny.snowflakemigration.domain.SnowHistoryProcessStatus;
import com.canny.snowflakemigration.repository.SnowHistoryProcessStatusRepository;
import com.canny.snowflakemigration.service.dto.SnowHistoryProcessStatusDTO;
import com.canny.snowflakemigration.service.mapper.SnowHistoryProcessStatusMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

/**
 * Service Implementation for managing {@link SnowHistoryProcessStatus}.
 */
@Service
@Transactional
public class SnowHistoryProcessStatusServiceImpl implements SnowHistoryProcessStatusService {

    private final Logger log = LoggerFactory.getLogger(SnowHistoryProcessStatusServiceImpl.class);

    private final SnowHistoryProcessStatusRepository snowHistoryProcessStatusRepository;

    private final SnowHistoryProcessStatusMapper snowHistoryProcessStatusMapper;

    public SnowHistoryProcessStatusServiceImpl(SnowHistoryProcessStatusRepository snowHistoryProcessStatusRepository, SnowHistoryProcessStatusMapper snowHistoryProcessStatusMapper) {
        this.snowHistoryProcessStatusRepository = snowHistoryProcessStatusRepository;
        this.snowHistoryProcessStatusMapper = snowHistoryProcessStatusMapper;
    }

    /**
     * Save a SnowHistoryProcessStatus.
     *
     * @param SnowHistoryProcessStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SnowHistoryProcessStatusDTO save(SnowHistoryProcessStatusDTO snowHistoryProcessStatusDTO) {
        log.debug("Request to save SnowHistoryProcessStatus : {}", snowHistoryProcessStatusDTO);
        SnowHistoryProcessStatus snowHistoryProcessStatus = snowHistoryProcessStatusMapper.toEntity(snowHistoryProcessStatusDTO);
        snowHistoryProcessStatus = snowHistoryProcessStatusRepository.save(snowHistoryProcessStatus);
        return snowHistoryProcessStatusMapper.toDto(snowHistoryProcessStatus);
    }

    /**
     * Get all the SnowHistoryProcessStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SnowHistoryProcessStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SnowHistoryProcessStatuses");
        return snowHistoryProcessStatusRepository.findAll(pageable)
            .map(snowHistoryProcessStatusMapper::toDto);
    }


    /**
     * Get one SnowHistoryProcessStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SnowHistoryProcessStatusDTO> findOne(Long id) {
        log.debug("Request to get SnowHistoryProcessStatus : {}", id);
        return snowHistoryProcessStatusRepository.findById(id)
            .map(snowHistoryProcessStatusMapper::toDto);
    }

    /**
     * Delete the SnowHistoryProcessStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SnowHistoryProcessStatus : {}", id);
        snowHistoryProcessStatusRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SnowHistoryProcessStatus> findAllByProcessId(Long id) {
        log.debug("Request to get SnowHistoryProcessStatus : {}", id);
        return snowHistoryProcessStatusRepository.findAllByProcessId(id);//.map(SnowHistoryProcessStatusMapper::toDto);
    }
}
 