package com.canny.snowflakemigration.service.impl;

import com.canny.snowflakemigration.service.SnowDDLProcessStatusService;
import com.canny.snowflakemigration.domain.SnowDDLProcessStatus;
import com.canny.snowflakemigration.repository.SnowDDLProcessStatusRepository;
import com.canny.snowflakemigration.service.dto.SnowDDLProcessStatusDTO;
import com.canny.snowflakemigration.service.mapper.SnowDDLProcessStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

/**
 * Service Implementation for managing {@link SnowDDLProcessStatus}.
 */
@Service
@Transactional
public class SnowDDLProcessStatusServiceImpl implements SnowDDLProcessStatusService {

    private final Logger log = LoggerFactory.getLogger(SnowDDLProcessStatusServiceImpl.class);

    private final SnowDDLProcessStatusRepository SnowDDLProcessStatusRepository;

    private final SnowDDLProcessStatusMapper SnowDDLProcessStatusMapper;

    public SnowDDLProcessStatusServiceImpl(SnowDDLProcessStatusRepository SnowDDLProcessStatusRepository, SnowDDLProcessStatusMapper SnowDDLProcessStatusMapper) {
        this.SnowDDLProcessStatusRepository = SnowDDLProcessStatusRepository;
        this.SnowDDLProcessStatusMapper = SnowDDLProcessStatusMapper;
    }

    /**
     * Save a SnowDDLProcessStatus.
     *
     * @param SnowDDLProcessStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SnowDDLProcessStatusDTO save(SnowDDLProcessStatusDTO SnowDDLProcessStatusDTO) {
        log.debug("Request to save SnowDDLProcessStatus : {}", SnowDDLProcessStatusDTO);
        SnowDDLProcessStatus SnowDDLProcessStatus = SnowDDLProcessStatusMapper.toEntity(SnowDDLProcessStatusDTO);
        SnowDDLProcessStatus = SnowDDLProcessStatusRepository.save(SnowDDLProcessStatus);
        return SnowDDLProcessStatusMapper.toDto(SnowDDLProcessStatus);
    }

    /**
     * Get all the SnowDDLProcessStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SnowDDLProcessStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SnowDDLProcessStatuses");
        return SnowDDLProcessStatusRepository.findAll(pageable)
            .map(SnowDDLProcessStatusMapper::toDto);
    }


    /**
     * Get one SnowDDLProcessStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SnowDDLProcessStatusDTO> findOne(Long id) {
        log.debug("Request to get SnowDDLProcessStatus : {}", id);
        return SnowDDLProcessStatusRepository.findById(id)
            .map(SnowDDLProcessStatusMapper::toDto);
    }

    /**
     * Delete the SnowDDLProcessStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SnowDDLProcessStatus : {}", id);
        SnowDDLProcessStatusRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SnowDDLProcessStatus> findAllByProcessId(Long id) {
        log.debug("Request to get SnowDDLProcessStatus : {}", id);
        return SnowDDLProcessStatusRepository.findAllByProcessId(id);//.map(SnowDDLProcessStatusMapper::toDto);
    }
}
 