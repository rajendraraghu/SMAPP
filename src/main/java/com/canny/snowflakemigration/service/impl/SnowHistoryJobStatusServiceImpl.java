package com.canny.snowflakemigration.service.impl;

import com.canny.snowflakemigration.service.SnowHistoryJobStatusService;
import com.canny.snowflakemigration.domain.SnowHistoryJobStatus;
import com.canny.snowflakemigration.repository.SnowHistoryJobStatusRepository;
import com.canny.snowflakemigration.service.dto.SnowHistoryJobStatusDTO;
import com.canny.snowflakemigration.service.mapper.SnowHistoryJobStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

/**
 * Service Implementation for managing {@link SnowHistoryJobStatus}.
 */
@Service
@Transactional
public class SnowHistoryJobStatusServiceImpl implements SnowHistoryJobStatusService {

    private final Logger log = LoggerFactory.getLogger(SnowHistoryJobStatusServiceImpl.class);

    private final SnowHistoryJobStatusRepository SnowHistoryJobStatusRepository;

    private final SnowHistoryJobStatusMapper SnowHistoryJobStatusMapper;

    public SnowHistoryJobStatusServiceImpl(SnowHistoryJobStatusRepository SnowHistoryJobStatusRepository, SnowHistoryJobStatusMapper SnowHistoryJobStatusMapper) {
        this.SnowHistoryJobStatusRepository = SnowHistoryJobStatusRepository;
        this.SnowHistoryJobStatusMapper = SnowHistoryJobStatusMapper;
    }

    /**
     * Save a SnowHistoryJobStatus.
     *
     * @param SnowHistoryJobStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SnowHistoryJobStatusDTO save(SnowHistoryJobStatusDTO SnowHistoryJobStatusDTO) {
        log.debug("Request to save SnowHistoryJobStatus : {}", SnowHistoryJobStatusDTO);
        SnowHistoryJobStatus SnowHistoryJobStatus = SnowHistoryJobStatusMapper.toEntity(SnowHistoryJobStatusDTO);
        SnowHistoryJobStatus = SnowHistoryJobStatusRepository.save(SnowHistoryJobStatus);
        return SnowHistoryJobStatusMapper.toDto(SnowHistoryJobStatus);
    }

    /**
     * Get all the SnowHistoryJobStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SnowHistoryJobStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SnowHistoryJobStatuses");
        return SnowHistoryJobStatusRepository.findAll(pageable)
            .map(SnowHistoryJobStatusMapper::toDto);
    }


    /**
     * Get one SnowHistoryJobStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SnowHistoryJobStatusDTO> findOne(Long id) {
        log.debug("Request to get SnowHistoryJobStatus : {}", id);
        return SnowHistoryJobStatusRepository.findById(id)
            .map(SnowHistoryJobStatusMapper::toDto);
    }

    /**
     * Delete the SnowHistoryJobStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SnowHistoryJobStatus : {}", id);
        SnowHistoryJobStatusRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SnowHistoryJobStatus> findAllByBatchId(Long id) {
        log.debug("Request to get SnowHistoryJobStatus : {}", id);
        return SnowHistoryJobStatusRepository.findAllByBatchId(id);//.map(SnowHistoryJobStatusMapper::toDto);
    }
}
 