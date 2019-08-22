package com.canny.snowflakemigration.service.impl;

import com.canny.snowflakemigration.service.SnowHistoryService;
import com.canny.snowflakemigration.domain.SnowHistory;
import com.canny.snowflakemigration.repository.SnowHistoryRepository;
import com.canny.snowflakemigration.service.dto.SnowHistoryDTO;
import com.canny.snowflakemigration.service.mapper.SnowHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SnowHistory}.
 */
@Service
@Transactional
public class SnowHistoryServiceImpl implements SnowHistoryService {

    private final Logger log = LoggerFactory.getLogger(SnowHistoryServiceImpl.class);

    private final SnowHistoryRepository snowHistoryRepository;

    private final SnowHistoryMapper snowHistoryMapper;

    public SnowHistoryServiceImpl(SnowHistoryRepository snowHistoryRepository, SnowHistoryMapper snowHistoryMapper) {
        this.snowHistoryRepository = snowHistoryRepository;
        this.snowHistoryMapper = snowHistoryMapper;
    }

    /**
     * Save a snowHistory.
     *
     * @param snowHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SnowHistoryDTO save(SnowHistoryDTO snowHistoryDTO) {
        log.debug("Request to save SnowHistory : {}", snowHistoryDTO);
        SnowHistory snowHistory = snowHistoryMapper.toEntity(snowHistoryDTO);
        snowHistory = snowHistoryRepository.save(snowHistory);
        return snowHistoryMapper.toDto(snowHistory);
    }

    /**
     * Get all the snowHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SnowHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SnowHistories");
        return snowHistoryRepository.findAll(pageable)
            .map(snowHistoryMapper::toDto);
    }


    /**
     * Get one snowHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SnowHistoryDTO> findOne(Long id) {
        log.debug("Request to get SnowHistory : {}", id);
        return snowHistoryRepository.findById(id)
            .map(snowHistoryMapper::toDto);
    }

    /**
     * Delete the snowHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SnowHistory : {}", id);
        snowHistoryRepository.deleteById(id);
    }
}
