package com.canny.snowflakemigration.service.impl;

import com.canny.snowflakemigration.service.MigrationProcessStatusService;
import com.canny.snowflakemigration.domain.MigrationProcessStatus;
import com.canny.snowflakemigration.repository.MigrationProcessStatusRepository;
import com.canny.snowflakemigration.service.dto.MigrationProcessStatusDTO;
import com.canny.snowflakemigration.service.mapper.MigrationProcessStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

/**
 * Service Implementation for managing {@link MigrationProcessStatus}.
 */
@Service
@Transactional
public class MigrationProcessStatusServiceImpl implements MigrationProcessStatusService {

    private final Logger log = LoggerFactory.getLogger(MigrationProcessStatusServiceImpl.class);

    private final MigrationProcessStatusRepository MigrationProcessStatusRepository;

    private final MigrationProcessStatusMapper MigrationProcessStatusMapper;

    public MigrationProcessStatusServiceImpl(MigrationProcessStatusRepository MigrationProcessStatusRepository, MigrationProcessStatusMapper MigrationProcessStatusMapper) {
        this.MigrationProcessStatusRepository = MigrationProcessStatusRepository;
        this.MigrationProcessStatusMapper = MigrationProcessStatusMapper;
    }

    /**
     * Save a MigrationProcessStatus.
     *
     * @param MigrationProcessStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MigrationProcessStatusDTO save(MigrationProcessStatusDTO MigrationProcessStatusDTO) {
        log.debug("Request to save MigrationProcessStatus : {}", MigrationProcessStatusDTO);
        MigrationProcessStatus MigrationProcessStatus = MigrationProcessStatusMapper.toEntity(MigrationProcessStatusDTO);
        MigrationProcessStatus = MigrationProcessStatusRepository.save(MigrationProcessStatus);
        return MigrationProcessStatusMapper.toDto(MigrationProcessStatus);
    }

    /**
     * Get all the MigrationProcessStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MigrationProcessStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MigrationProcessStatuses");
        return MigrationProcessStatusRepository.findAll(pageable)
            .map(MigrationProcessStatusMapper::toDto);
    }


    /**
     * Get one MigrationProcessStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MigrationProcessStatusDTO> findOne(Long id) {
        log.debug("Request to get MigrationProcessStatus : {}", id);
        return MigrationProcessStatusRepository.findById(id)
            .map(MigrationProcessStatusMapper::toDto);
    }

    /**
     * Delete the MigrationProcessStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MigrationProcessStatus : {}", id);
        MigrationProcessStatusRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MigrationProcessStatus> findAllByProcessId(Long id) {
        log.debug("Request to get MigrationProcessStatus : {}", id);
        return MigrationProcessStatusRepository.findAllByProcessId(id);//.map(MigrationProcessStatusMapper::toDto);
    }
}
 