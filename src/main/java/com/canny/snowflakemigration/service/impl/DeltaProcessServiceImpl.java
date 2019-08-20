package com.canny.snowflakemigration.service.impl;

import com.canny.snowflakemigration.service.DeltaProcessService;
import com.canny.snowflakemigration.domain.DeltaProcess;
import com.canny.snowflakemigration.repository.DeltaProcessRepository;
import com.canny.snowflakemigration.service.dto.DeltaProcessDTO;
import com.canny.snowflakemigration.service.mapper.DeltaProcessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DeltaProcess}.
 */
@Service
@Transactional
public class DeltaProcessServiceImpl implements DeltaProcessService {

    private final Logger log = LoggerFactory.getLogger(DeltaProcessServiceImpl.class);

    private final DeltaProcessRepository deltaProcessRepository;

    private final DeltaProcessMapper deltaProcessMapper;

    public DeltaProcessServiceImpl(DeltaProcessRepository deltaProcessRepository, DeltaProcessMapper deltaProcessMapper) {
        this.deltaProcessRepository = deltaProcessRepository;
        this.deltaProcessMapper = deltaProcessMapper;
    }

    /**
     * Save a deltaProcess.
     *
     * @param deltaProcessDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DeltaProcessDTO save(DeltaProcessDTO deltaProcessDTO) {
        log.debug("Request to save DeltaProcess : {}", deltaProcessDTO);
        DeltaProcess deltaProcess = deltaProcessMapper.toEntity(deltaProcessDTO);
        deltaProcess = deltaProcessRepository.save(deltaProcess);
        return deltaProcessMapper.toDto(deltaProcess);
    }

    /**
     * Get all the deltaProcesses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeltaProcessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeltaProcesses");
        return deltaProcessRepository.findAll(pageable)
            .map(deltaProcessMapper::toDto);
    }


    /**
     * Get one deltaProcess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DeltaProcessDTO> findOne(Long id) {
        log.debug("Request to get DeltaProcess : {}", id);
        return deltaProcessRepository.findById(id)
            .map(deltaProcessMapper::toDto);
    }

    /**
     * Delete the deltaProcess by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeltaProcess : {}", id);
        deltaProcessRepository.deleteById(id);
    }
}
