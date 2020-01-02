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

import com.canny.snowflakemigration.domain.SnowParseJobStatus;
import com.canny.snowflakemigration.domain.*; // for static metamodels
import com.canny.snowflakemigration.repository.SnowParseJobStatusRepository;
import com.canny.snowflakemigration.service.dto.SnowParseJobStatusCriteria;
import com.canny.snowflakemigration.service.dto.SnowParseJobStatusDTO;
import com.canny.snowflakemigration.service.mapper.SnowParseJobStatusMapper;

/**
 * Service for executing complex queries for {@link SnowParseJobStatus} entities in the database.
 * The main input is a {@link SnowParseJobStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SnowParseJobStatusDTO} or a {@link Page} of {@link SnowParseJobStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SnowParseJobStatusQueryService extends QueryService<SnowParseJobStatus> {

    private final Logger log = LoggerFactory.getLogger(SnowParseJobStatusQueryService.class);

    private final SnowParseJobStatusRepository SnowParseJobStatusRepository;

    private final SnowParseJobStatusMapper SnowParseJobStatusMapper;

    public SnowParseJobStatusQueryService(SnowParseJobStatusRepository SnowParseJobStatusRepository, SnowParseJobStatusMapper SnowParseJobStatusMapper) {
        this.SnowParseJobStatusRepository = SnowParseJobStatusRepository;
        this.SnowParseJobStatusMapper = SnowParseJobStatusMapper;
    }

    /**
     * Return a {@link List} of {@link SnowParseJobStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SnowParseJobStatusDTO> findByCriteria(SnowParseJobStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SnowParseJobStatus> specification = createSpecification(criteria);
        return SnowParseJobStatusMapper.toDto(SnowParseJobStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SnowParseJobStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SnowParseJobStatusDTO> findByCriteria(SnowParseJobStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SnowParseJobStatus> specification = createSpecification(criteria);
        return SnowParseJobStatusRepository.findAll(specification, page)
            .map(SnowParseJobStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SnowParseJobStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SnowParseJobStatus> specification = createSpecification(criteria);
        return SnowParseJobStatusRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<SnowParseJobStatus> createSpecification(SnowParseJobStatusCriteria criteria) {
        Specification<SnowParseJobStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getJobId() != null) {
                specification = specification.and(buildSpecification(criteria.getJobId(), SnowParseJobStatus_.jobId));
            }
            if (criteria.getBatchId() != null) {
                specification = specification.and(buildSpecification(criteria.getBatchId(), SnowParseJobStatus_.batchId));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SnowParseJobStatus_.name));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildSpecification(criteria.getStartTime(), SnowParseJobStatus_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildSpecification(criteria.getEndTime(), SnowParseJobStatus_.endTime));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), SnowParseJobStatus_.status));
            }
        }
        return specification;
    }
}
