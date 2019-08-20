package com.canny.snowflakemigration.service.impl;

import com.canny.snowflakemigration.service.DeltaProcessStatusService;
import com.canny.snowflakemigration.domain.DeltaProcessStatus;
import com.canny.snowflakemigration.repository.DeltaProcessStatusRepository;
import com.canny.snowflakemigration.service.dto.DeltaProcessStatusDTO;
import com.canny.snowflakemigration.service.mapper.DeltaProcessStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

/**
 * Service Implementation for managing {@link DeltaProcessStatus}.
 */
@Service
@Transactional
public class DeltaProcessStatusServiceImpl implements DeltaProcessStatusService {

    private final Logger log = LoggerFactory.getLogger(DeltaProcessStatusServiceImpl.class);

    private final DeltaProcessStatusRepository DeltaProcessStatusRepository;

    private final DeltaProcessStatusMapper DeltaProcessStatusMapper;

    public DeltaProcessStatusServiceImpl(DeltaProcessStatusRepository DeltaProcessStatusRepository, DeltaProcessStatusMapper DeltaProcessStatusMapper) {
        this.DeltaProcessStatusRepository = DeltaProcessStatusRepository;
        this.DeltaProcessStatusMapper = DeltaProcessStatusMapper;
    }

    /**
     * Save a DeltaProcessStatus.
     *
     * @param DeltaProcessStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DeltaProcessStatusDTO save(DeltaProcessStatusDTO DeltaProcessStatusDTO) {
        log.debug("Request to save DeltaProcessStatus : {}", DeltaProcessStatusDTO);
        DeltaProcessStatus DeltaProcessStatus = DeltaProcessStatusMapper.toEntity(DeltaProcessStatusDTO);
        DeltaProcessStatus = DeltaProcessStatusRepository.save(DeltaProcessStatus);
        return DeltaProcessStatusMapper.toDto(DeltaProcessStatus);
    }

    /**
     * Get all the DeltaProcessStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeltaProcessStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeltaProcessStatuses");
        return DeltaProcessStatusRepository.findAll(pageable)
            .map(DeltaProcessStatusMapper::toDto);
    }


    /**
     * Get one DeltaProcessStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DeltaProcessStatusDTO> findOne(Long id) {
        log.debug("Request to get DeltaProcessStatus : {}", id);
        return DeltaProcessStatusRepository.findById(id)
            .map(DeltaProcessStatusMapper::toDto);
    }

    /**
     * Delete the DeltaProcessStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeltaProcessStatus : {}", id);
        DeltaProcessStatusRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeltaProcessStatus> findAllByProcessId(Long id) {
        log.debug("Request to get DeltaProcessStatus : {}", id);
        return DeltaProcessStatusRepository.findAllByProcessId(id);//.map(DeltaProcessStatusMapper::toDto);
    }
}
