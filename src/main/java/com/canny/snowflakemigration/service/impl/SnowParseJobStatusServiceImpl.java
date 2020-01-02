package com.canny.snowflakemigration.service.impl;

import com.canny.snowflakemigration.service.SnowParseJobStatusService;
import com.canny.snowflakemigration.domain.SnowParseJobStatus;
import com.canny.snowflakemigration.repository.SnowParseJobStatusRepository;
import com.canny.snowflakemigration.service.dto.SnowParseJobStatusDTO;
import com.canny.snowflakemigration.service.mapper.SnowParseJobStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

/**
 * Service Implementation for managing {@link SnowParseJobStatus}.
 */
@Service
@Transactional
public class SnowParseJobStatusServiceImpl implements SnowParseJobStatusService {

    private final Logger log = LoggerFactory.getLogger(SnowParseJobStatusServiceImpl.class);

    private final SnowParseJobStatusRepository SnowParseJobStatusRepository;

    private final SnowParseJobStatusMapper SnowParseJobStatusMapper;

    public SnowParseJobStatusServiceImpl(SnowParseJobStatusRepository SnowParseJobStatusRepository, SnowParseJobStatusMapper SnowParseJobStatusMapper) {
        this.SnowParseJobStatusRepository = SnowParseJobStatusRepository;
        this.SnowParseJobStatusMapper = SnowParseJobStatusMapper;
    }

    /**
     * Save a SnowParseJobStatus.
     *
     * @param SnowParseJobStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SnowParseJobStatusDTO save(SnowParseJobStatusDTO SnowParseJobStatusDTO) {
        log.debug("Request to save SnowParseJobStatus : {}", SnowParseJobStatusDTO);
        SnowParseJobStatus SnowParseJobStatus = SnowParseJobStatusMapper.toEntity(SnowParseJobStatusDTO);
        SnowParseJobStatus = SnowParseJobStatusRepository.save(SnowParseJobStatus);
        return SnowParseJobStatusMapper.toDto(SnowParseJobStatus);
    }

    /**
     * Get all the SnowParseJobStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SnowParseJobStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SnowParseJobStatuses");
        return SnowParseJobStatusRepository.findAll(pageable)
            .map(SnowParseJobStatusMapper::toDto);
    }


    /**
     * Get one SnowParseJobStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SnowParseJobStatusDTO> findOne(Long id) {
        log.debug("Request to get SnowParseJobStatus : {}", id);
        return SnowParseJobStatusRepository.findById(id)
            .map(SnowParseJobStatusMapper::toDto);
    }

    /**
     * Delete the SnowParseJobStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SnowParseJobStatus : {}", id);
        SnowParseJobStatusRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SnowParseJobStatus> findAllByBatchId(Long id) {
        log.debug("Request to get SnowParseJobStatus : {}", id);
        return SnowParseJobStatusRepository.findAllByBatchId(id);//.map(SnowParseJobStatusMapper::toDto);
    }
}
 