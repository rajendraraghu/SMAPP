package com.canny.snowflakemigration.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.canny.snowflakemigration.domain.SnowHistoryJobStatus;
import com.canny.snowflakemigration.domain.*; // for static metamodels
import com.canny.snowflakemigration.repository.SnowHistoryJobStatusRepository;
import com.canny.snowflakemigration.service.dto.SnowHistoryJobStatusCriteria;
import com.canny.snowflakemigration.service.dto.SnowHistoryJobStatusDTO;
import com.canny.snowflakemigration.service.mapper.SnowHistoryJobStatusMapper;

/**
 * Service for executing complex queries for {@link SnowHistoryJobStatus} entities in the database.
 * The main input is a {@link SnowHistoryJobStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SnowHistoryJobStatusDTO} or a {@link Page} of {@link SnowHistoryJobStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SnowHistoryJobStatusQueryService extends QueryService<SnowHistoryJobStatus> {

    private final Logger log = LoggerFactory.getLogger(SnowHistoryJobStatusQueryService.class);

    private final SnowHistoryJobStatusRepository SnowHistoryJobStatusRepository;

    private final SnowHistoryJobStatusMapper SnowHistoryJobStatusMapper;

    public SnowHistoryJobStatusQueryService(SnowHistoryJobStatusRepository SnowHistoryJobStatusRepository, SnowHistoryJobStatusMapper SnowHistoryJobStatusMapper) {
        this.SnowHistoryJobStatusRepository = SnowHistoryJobStatusRepository;
        this.SnowHistoryJobStatusMapper = SnowHistoryJobStatusMapper;
    }

    /**
     * Return a {@link List} of {@link SnowHistoryJobStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SnowHistoryJobStatusDTO> findByCriteria(SnowHistoryJobStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SnowHistoryJobStatus> specification = createSpecification(criteria);
        return SnowHistoryJobStatusMapper.toDto(SnowHistoryJobStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SnowHistoryJobStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SnowHistoryJobStatusDTO> findByCriteria(SnowHistoryJobStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SnowHistoryJobStatus> specification = createSpecification(criteria);
        return SnowHistoryJobStatusRepository.findAll(specification, page)
            .map(SnowHistoryJobStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SnowHistoryJobStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SnowHistoryJobStatus> specification = createSpecification(criteria);
        return SnowHistoryJobStatusRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<SnowHistoryJobStatus> createSpecification(SnowHistoryJobStatusCriteria criteria) {
        Specification<SnowHistoryJobStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getJobId() != null) {
                specification = specification.and(buildSpecification(criteria.getJobId(), SnowHistoryJobStatus_.jobId));
            }
            if (criteria.getBatchId() != null) {
                specification = specification.and(buildSpecification(criteria.getBatchId(), SnowHistoryJobStatus_.batchId));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SnowHistoryJobStatus_.name));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildSpecification(criteria.getStartTime(), SnowHistoryJobStatus_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildSpecification(criteria.getEndTime(), SnowHistoryJobStatus_.endTime));
            }
            if (criteria.getSourceCount() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceCount(), SnowHistoryJobStatus_.sourceCount));
            }
            if (criteria.getInsertCount() != null) {
                specification = specification.and(buildSpecification(criteria.getInsertCount(), SnowHistoryJobStatus_.insertCount));
            }
            if (criteria.getDeleteCount() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleteCount(), SnowHistoryJobStatus_.deleteCount));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), SnowHistoryJobStatus_.status));
            }
        }
        return specification;
    }
}
