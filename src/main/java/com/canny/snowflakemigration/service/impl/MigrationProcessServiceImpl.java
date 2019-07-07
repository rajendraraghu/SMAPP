package com.canny.snowflakemigration.service.impl;

import com.canny.snowflakemigration.service.MigrationProcessService;
import com.canny.snowflakemigration.domain.MigrationProcess;
import com.canny.snowflakemigration.repository.MigrationProcessRepository;
import com.canny.snowflakemigration.service.dto.MigrationProcessDTO;
import com.canny.snowflakemigration.service.mapper.MigrationProcessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MigrationProcess}.
 */
@Service
@Transactional
public class MigrationProcessServiceImpl implements MigrationProcessService {

    private final Logger log = LoggerFactory.getLogger(MigrationProcessServiceImpl.class);

    private final MigrationProcessRepository migrationProcessRepository;

    private final MigrationProcessMapper migrationProcessMapper;

    public MigrationProcessServiceImpl(MigrationProcessRepository migrationProcessRepository, MigrationProcessMapper migrationProcessMapper) {
        this.migrationProcessRepository = migrationProcessRepository;
        this.migrationProcessMapper = migrationProcessMapper;
    }

    /**
     * Save a migrationProcess.
     *
     * @param migrationProcessDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MigrationProcessDTO save(MigrationProcessDTO migrationProcessDTO) {
        log.debug("Request to save MigrationProcess : {}", migrationProcessDTO);
        MigrationProcess migrationProcess = migrationProcessMapper.toEntity(migrationProcessDTO);
        migrationProcess = migrationProcessRepository.save(migrationProcess);
        return migrationProcessMapper.toDto(migrationProcess);
    }

    /**
     * Get all the migrationProcesses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MigrationProcessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MigrationProcesses");
        return migrationProcessRepository.findAll(pageable)
            .map(migrationProcessMapper::toDto);
    }


    /**
     * Get one migrationProcess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MigrationProcessDTO> findOne(Long id) {
        log.debug("Request to get MigrationProcess : {}", id);
        return migrationProcessRepository.findById(id)
            .map(migrationProcessMapper::toDto);
    }

    /**
     * Delete the migrationProcess by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MigrationProcess : {}", id);
        migrationProcessRepository.deleteById(id);
    }
}
