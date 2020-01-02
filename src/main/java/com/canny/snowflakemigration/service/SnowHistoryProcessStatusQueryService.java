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

import com.canny.snowflakemigration.domain.SnowHistoryProcessStatus;
import com.canny.snowflakemigration.domain.*; // for static metamodels
import com.canny.snowflakemigration.repository.SnowHistoryProcessStatusRepository;
import com.canny.snowflakemigration.service.dto.SnowHistoryProcessStatusCriteria;
import com.canny.snowflakemigration.service.dto.SnowHistoryProcessStatusDTO;
import com.canny.snowflakemigration.service.mapper.SnowHistoryProcessStatusMapper;

/**
 * Service for executing complex queries for {@link SnowHistoryProcessStatus} entities in the database.
 * The main input is a {@link SnowHistoryProcessStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SnowHistoryProcessStatusDTO} or a {@link Page} of {@link SnowHistoryProcessStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SnowHistoryProcessStatusQueryService extends QueryService<SnowHistoryProcessStatus> {

    private final Logger log = LoggerFactory.getLogger(SnowHistoryProcessStatusQueryService.class);

    private final SnowHistoryProcessStatusRepository SnowHistoryProcessStatusRepository;

    private final SnowHistoryProcessStatusMapper SnowHistoryProcessStatusMapper;

    public SnowHistoryProcessStatusQueryService(SnowHistoryProcessStatusRepository SnowHistoryProcessStatusRepository, SnowHistoryProcessStatusMapper SnowHistoryProcessStatusMapper) {
        this.SnowHistoryProcessStatusRepository = SnowHistoryProcessStatusRepository;
        this.SnowHistoryProcessStatusMapper = SnowHistoryProcessStatusMapper;
    }

    /**
     * Return a {@link List} of {@link SnowHistoryProcessStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SnowHistoryProcessStatusDTO> findByCriteria(SnowHistoryProcessStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SnowHistoryProcessStatus> specification = createSpecification(criteria);
        return SnowHistoryProcessStatusMapper.toDto(SnowHistoryProcessStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SnowHistoryProcessStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SnowHistoryProcessStatusDTO> findByCriteria(SnowHistoryProcessStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SnowHistoryProcessStatus> specification = createSpecification(criteria);
        return SnowHistoryProcessStatusRepository.findAll(specification, page)
            .map(SnowHistoryProcessStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SnowHistoryProcessStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SnowHistoryProcessStatus> specification = createSpecification(criteria);
        return SnowHistoryProcessStatusRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<SnowHistoryProcessStatus> createSpecification(SnowHistoryProcessStatusCriteria criteria) {
        Specification<SnowHistoryProcessStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getBatchId() != null) {
                specification = specification.and(buildSpecification(criteria.getBatchId(), SnowHistoryProcessStatus_.batchId));
            }
            if (criteria.getProcessId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessId(), SnowHistoryProcessStatus_.processId));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SnowHistoryProcessStatus_.name));
            }
            if (criteria.getRunBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRunBy(), SnowHistoryProcessStatus_.runBy));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildSpecification(criteria.getStartTime(), SnowHistoryProcessStatus_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildSpecification(criteria.getEndTime(), SnowHistoryProcessStatus_.endTime));
            }
            if (criteria.getTotalTables() != null) {
                specification = specification.and(buildSpecification(criteria.getTotalTables(), SnowHistoryProcessStatus_.totalTables));
            }
            if (criteria.getSuccessTables() != null) {
                specification = specification.and(buildSpecification(criteria.getSuccessTables(), SnowHistoryProcessStatus_.successTables));
            }
            if (criteria.getErrorTables() != null) {
                specification = specification.and(buildSpecification(criteria.getErrorTables(), SnowHistoryProcessStatus_.errorTables));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), SnowHistoryProcessStatus_.status));
            }
        }
        return specification;
    }
}
