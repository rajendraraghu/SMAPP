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

import com.canny.snowflakemigration.domain.SnowDDLJobStatus;
import com.canny.snowflakemigration.domain.*; // for static metamodels
import com.canny.snowflakemigration.repository.SnowDDLJobStatusRepository;
import com.canny.snowflakemigration.service.dto.SnowDDLJobStatusCriteria;
import com.canny.snowflakemigration.service.dto.SnowDDLJobStatusDTO;
import com.canny.snowflakemigration.service.mapper.SnowDDLJobStatusMapper;

/**
 * Service for executing complex queries for {@link SnowDDLJobStatus} entities in the database.
 * The main input is a {@link SnowDDLJobStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SnowDDLJobStatusDTO} or a {@link Page} of {@link SnowDDLJobStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SnowDDLJobStatusQueryService extends QueryService<SnowDDLJobStatus> {

    private final Logger log = LoggerFactory.getLogger(SnowDDLJobStatusQueryService.class);

    private final SnowDDLJobStatusRepository SnowDDLJobStatusRepository;

    private final SnowDDLJobStatusMapper SnowDDLJobStatusMapper;

    public SnowDDLJobStatusQueryService(SnowDDLJobStatusRepository SnowDDLJobStatusRepository, SnowDDLJobStatusMapper SnowDDLJobStatusMapper) {
        this.SnowDDLJobStatusRepository = SnowDDLJobStatusRepository;
        this.SnowDDLJobStatusMapper = SnowDDLJobStatusMapper;
    }

    /**
     * Return a {@link List} of {@link SnowDDLJobStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SnowDDLJobStatusDTO> findByCriteria(SnowDDLJobStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SnowDDLJobStatus> specification = createSpecification(criteria);
        return SnowDDLJobStatusMapper.toDto(SnowDDLJobStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SnowDDLJobStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SnowDDLJobStatusDTO> findByCriteria(SnowDDLJobStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SnowDDLJobStatus> specification = createSpecification(criteria);
        return SnowDDLJobStatusRepository.findAll(specification, page)
            .map(SnowDDLJobStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SnowDDLJobStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SnowDDLJobStatus> specification = createSpecification(criteria);
        return SnowDDLJobStatusRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<SnowDDLJobStatus> createSpecification(SnowDDLJobStatusCriteria criteria) {
        Specification<SnowDDLJobStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getJobId() != null) {
                specification = specification.and(buildSpecification(criteria.getJobId(), SnowDDLJobStatus_.jobId));
            }
            if (criteria.getBatchId() != null) {
                specification = specification.and(buildSpecification(criteria.getBatchId(), SnowDDLJobStatus_.batchId));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SnowDDLJobStatus_.name));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildSpecification(criteria.getStartTime(), SnowDDLJobStatus_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildSpecification(criteria.getEndTime(), SnowDDLJobStatus_.endTime));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), SnowDDLJobStatus_.status));
            }
        }
        return specification;
    }
}
