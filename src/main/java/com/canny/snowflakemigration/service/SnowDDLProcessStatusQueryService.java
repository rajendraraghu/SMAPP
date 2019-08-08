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

import com.canny.snowflakemigration.domain.SnowDDLProcessStatus;
import com.canny.snowflakemigration.domain.*; // for static metamodels
import com.canny.snowflakemigration.repository.SnowDDLProcessStatusRepository;
import com.canny.snowflakemigration.service.dto.SnowDDLProcessStatusCriteria;
import com.canny.snowflakemigration.service.dto.SnowDDLProcessStatusDTO;
import com.canny.snowflakemigration.service.mapper.SnowDDLProcessStatusMapper;

/**
 * Service for executing complex queries for {@link SnowDDLProcessStatus} entities in the database.
 * The main input is a {@link SnowDDLProcessStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SnowDDLProcessStatusDTO} or a {@link Page} of {@link SnowDDLProcessStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SnowDDLProcessStatusQueryService extends QueryService<SnowDDLProcessStatus> {

    private final Logger log = LoggerFactory.getLogger(SnowDDLProcessStatusQueryService.class);

    private final SnowDDLProcessStatusRepository SnowDDLProcessStatusRepository;

    private final SnowDDLProcessStatusMapper SnowDDLProcessStatusMapper;

    public SnowDDLProcessStatusQueryService(SnowDDLProcessStatusRepository SnowDDLProcessStatusRepository, SnowDDLProcessStatusMapper SnowDDLProcessStatusMapper) {
        this.SnowDDLProcessStatusRepository = SnowDDLProcessStatusRepository;
        this.SnowDDLProcessStatusMapper = SnowDDLProcessStatusMapper;
    }

    /**
     * Return a {@link List} of {@link SnowDDLProcessStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SnowDDLProcessStatusDTO> findByCriteria(SnowDDLProcessStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SnowDDLProcessStatus> specification = createSpecification(criteria);
        return SnowDDLProcessStatusMapper.toDto(SnowDDLProcessStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SnowDDLProcessStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SnowDDLProcessStatusDTO> findByCriteria(SnowDDLProcessStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SnowDDLProcessStatus> specification = createSpecification(criteria);
        return SnowDDLProcessStatusRepository.findAll(specification, page)
            .map(SnowDDLProcessStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SnowDDLProcessStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SnowDDLProcessStatus> specification = createSpecification(criteria);
        return SnowDDLProcessStatusRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<SnowDDLProcessStatus> createSpecification(SnowDDLProcessStatusCriteria criteria) {
        Specification<SnowDDLProcessStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getBatchId() != null) {
                specification = specification.and(buildSpecification(criteria.getBatchId(), SnowDDLProcessStatus_.batchId));
            }
            if (criteria.getProcessId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessId(), SnowDDLProcessStatus_.processId));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SnowDDLProcessStatus_.name));
            }
            if (criteria.getRunBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRunBy(), SnowDDLProcessStatus_.runBy));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildSpecification(criteria.getStartTime(), SnowDDLProcessStatus_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildSpecification(criteria.getEndTime(), SnowDDLProcessStatus_.endTime));
            }
            if (criteria.getTotalObjects() != null) {
                specification = specification.and(buildSpecification(criteria.getTotalObjects(), SnowDDLProcessStatus_.totalObjects));
            }
            if (criteria.getSuccessObjects() != null) {
                specification = specification.and(buildSpecification(criteria.getSuccessObjects(), SnowDDLProcessStatus_.successObjects));
            }
            if (criteria.getErrorObjects() != null) {
                specification = specification.and(buildSpecification(criteria.getErrorObjects(), SnowDDLProcessStatus_.errorObjects));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), SnowDDLProcessStatus_.status));
            }
        }
        return specification;
    }
}
