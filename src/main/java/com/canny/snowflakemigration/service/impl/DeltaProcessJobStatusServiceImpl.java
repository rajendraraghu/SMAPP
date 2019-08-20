package com.canny.snowflakemigration.service.impl;

import com.canny.snowflakemigration.service.DeltaProcessJobStatusService;
import com.canny.snowflakemigration.domain.DeltaProcessJobStatus;
import com.canny.snowflakemigration.repository.DeltaProcessJobStatusRepository;
import com.canny.snowflakemigration.service.dto.DeltaProcessJobStatusDTO;
import com.canny.snowflakemigration.service.mapper.DeltaProcessJobStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

/**
 * Service Implementation for managing {@link DeltaProcessJobStatus}.
 */
@Service
@Transactional
public class DeltaProcessJobStatusServiceImpl implements DeltaProcessJobStatusService {

    private final Logger log = LoggerFactory.getLogger(DeltaProcessJobStatusServiceImpl.class);

    private final DeltaProcessJobStatusRepository DeltaProcessJobStatusRepository;

    private final DeltaProcessJobStatusMapper DeltaProcessJobStatusMapper;

    public DeltaProcessJobStatusServiceImpl(DeltaProcessJobStatusRepository DeltaProcessJobStatusRepository, DeltaProcessJobStatusMapper DeltaProcessJobStatusMapper) {
        this.DeltaProcessJobStatusRepository = DeltaProcessJobStatusRepository;
        this.DeltaProcessJobStatusMapper = DeltaProcessJobStatusMapper;
    }

    /**
     * Save a DeltaProcessJobStatus.
     *
     * @param DeltaProcessJobStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DeltaProcessJobStatusDTO save(DeltaProcessJobStatusDTO DeltaProcessJobStatusDTO) {
        log.debug("Request to save DeltaProcessJobStatus : {}", DeltaProcessJobStatusDTO);
        DeltaProcessJobStatus DeltaProcessJobStatus = DeltaProcessJobStatusMapper.toEntity(DeltaProcessJobStatusDTO);
        DeltaProcessJobStatus = DeltaProcessJobStatusRepository.save(DeltaProcessJobStatus);
        return DeltaProcessJobStatusMapper.toDto(DeltaProcessJobStatus);
    }

    /**
     * Get all the DeltaProcessJobStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeltaProcessJobStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeltaProcessJobStatuses");
        return DeltaProcessJobStatusRepository.findAll(pageable)
            .map(DeltaProcessJobStatusMapper::toDto);
    }


    /**
     * Get one DeltaProcessJobStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DeltaProcessJobStatusDTO> findOne(Long id) {
        log.debug("Request to get DeltaProcessJobStatus : {}", id);
        return DeltaProcessJobStatusRepository.findById(id)
            .map(DeltaProcessJobStatusMapper::toDto);
    }

    /**
     * Delete the DeltaProcessJobStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeltaProcessJobStatus : {}", id);
        DeltaProcessJobStatusRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeltaProcessJobStatus> findAllByJobId(Long id) {
        log.debug("Request to get DeltaProcessJobStatus : {}", id);
        return DeltaProcessJobStatusRepository.findAllByJobId(id);//.map(DeltaProcessJobStatusMapper::toDto);
    }
}
