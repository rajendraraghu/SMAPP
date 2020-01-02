package com.canny.snowflakemigration.service.impl;

import com.canny.snowflakemigration.service.SnowDDLJobStatusService;
import com.canny.snowflakemigration.domain.SnowDDLJobStatus;
import com.canny.snowflakemigration.repository.SnowDDLJobStatusRepository;
import com.canny.snowflakemigration.service.dto.SnowDDLJobStatusDTO;
import com.canny.snowflakemigration.service.mapper.SnowDDLJobStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

/**
 * Service Implementation for managing {@link SnowDDLJobStatus}.
 */
@Service
@Transactional
public class SnowDDLJobStatusServiceImpl implements SnowDDLJobStatusService {

    private final Logger log = LoggerFactory.getLogger(SnowDDLJobStatusServiceImpl.class);

    private final SnowDDLJobStatusRepository SnowDDLJobStatusRepository;

    private final SnowDDLJobStatusMapper SnowDDLJobStatusMapper;

    public SnowDDLJobStatusServiceImpl(SnowDDLJobStatusRepository SnowDDLJobStatusRepository, SnowDDLJobStatusMapper SnowDDLJobStatusMapper) {
        this.SnowDDLJobStatusRepository = SnowDDLJobStatusRepository;
        this.SnowDDLJobStatusMapper = SnowDDLJobStatusMapper;
    }

    /**
     * Save a SnowDDLJobStatus.
     *
     * @param SnowDDLJobStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SnowDDLJobStatusDTO save(SnowDDLJobStatusDTO SnowDDLJobStatusDTO) {
        log.debug("Request to save SnowDDLJobStatus : {}", SnowDDLJobStatusDTO);
        SnowDDLJobStatus SnowDDLJobStatus = SnowDDLJobStatusMapper.toEntity(SnowDDLJobStatusDTO);
        SnowDDLJobStatus = SnowDDLJobStatusRepository.save(SnowDDLJobStatus);
        return SnowDDLJobStatusMapper.toDto(SnowDDLJobStatus);
    }

    /**
     * Get all the SnowDDLJobStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SnowDDLJobStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SnowDDLJobStatuses");
        return SnowDDLJobStatusRepository.findAll(pageable)
            .map(SnowDDLJobStatusMapper::toDto);
    }


    /**
     * Get one SnowDDLJobStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SnowDDLJobStatusDTO> findOne(Long id) {
        log.debug("Request to get SnowDDLJobStatus : {}", id);
        return SnowDDLJobStatusRepository.findById(id)
            .map(SnowDDLJobStatusMapper::toDto);
    }

    /**
     * Delete the SnowDDLJobStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SnowDDLJobStatus : {}", id);
        SnowDDLJobStatusRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SnowDDLJobStatus> findAllByBatchId(Long id) {
        log.debug("Request to get SnowDDLJobStatus : {}", id);
        return SnowDDLJobStatusRepository.findAllByBatchId(id);//.map(SnowDDLJobStatusMapper::toDto);
    }
}
 