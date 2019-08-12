package com.canny.snowflakemigration.service.impl;

import com.canny.snowflakemigration.service.MigrationProcessJobStatusService;
import com.canny.snowflakemigration.domain.MigrationProcessJobStatus;
import com.canny.snowflakemigration.repository.MigrationProcessJobStatusRepository;
import com.canny.snowflakemigration.service.dto.MigrationProcessJobStatusDTO;
import com.canny.snowflakemigration.service.mapper.MigrationProcessJobStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

/**
 * Service Implementation for managing {@link MigrationProcessJobStatus}.
 */
@Service
@Transactional
public class MigrationProcessJobStatusServiceImpl implements MigrationProcessJobStatusService {

    private final Logger log = LoggerFactory.getLogger(MigrationProcessJobStatusServiceImpl.class);

    private final MigrationProcessJobStatusRepository MigrationProcessJobStatusRepository;

    private final MigrationProcessJobStatusMapper MigrationProcessJobStatusMapper;

    public MigrationProcessJobStatusServiceImpl(MigrationProcessJobStatusRepository MigrationProcessJobStatusRepository, MigrationProcessJobStatusMapper MigrationProcessJobStatusMapper) {
        this.MigrationProcessJobStatusRepository = MigrationProcessJobStatusRepository;
        this.MigrationProcessJobStatusMapper = MigrationProcessJobStatusMapper;
    }

    /**
     * Save a MigrationProcessJobStatus.
     *
     * @param MigrationProcessJobStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MigrationProcessJobStatusDTO save(MigrationProcessJobStatusDTO MigrationProcessJobStatusDTO) {
        log.debug("Request to save MigrationProcessJobStatus : {}", MigrationProcessJobStatusDTO);
        MigrationProcessJobStatus MigrationProcessJobStatus = MigrationProcessJobStatusMapper.toEntity(MigrationProcessJobStatusDTO);
        MigrationProcessJobStatus = MigrationProcessJobStatusRepository.save(MigrationProcessJobStatus);
        return MigrationProcessJobStatusMapper.toDto(MigrationProcessJobStatus);
    }

    /**
     * Get all the MigrationProcessJobStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MigrationProcessJobStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MigrationProcessJobStatuses");
        return MigrationProcessJobStatusRepository.findAll(pageable)
            .map(MigrationProcessJobStatusMapper::toDto);
    }


    /**
     * Get one MigrationProcessJobStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MigrationProcessJobStatusDTO> findOne(Long id) {
        log.debug("Request to get MigrationProcessJobStatus : {}", id);
        return MigrationProcessJobStatusRepository.findById(id)
            .map(MigrationProcessJobStatusMapper::toDto);
    }

    /**
     * Delete the MigrationProcessJobStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MigrationProcessJobStatus : {}", id);
        MigrationProcessJobStatusRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MigrationProcessJobStatus> findAllByJobId(Long id) {
        log.debug("Request to get MigrationProcessJobStatus : {}", id);
        return MigrationProcessJobStatusRepository.findAllByJobId(id);//.map(MigrationProcessJobStatusMapper::toDto);
    }
}
 