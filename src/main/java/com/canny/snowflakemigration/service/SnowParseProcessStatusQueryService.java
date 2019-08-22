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

import com.canny.snowflakemigration.domain.SnowParseProcessStatus;
import com.canny.snowflakemigration.domain.*; // for static metamodels
import com.canny.snowflakemigration.repository.SnowParseProcessStatusRepository;
import com.canny.snowflakemigration.service.dto.SnowParseProcessStatusCriteria;
import com.canny.snowflakemigration.service.dto.SnowParseProcessStatusDTO;
import com.canny.snowflakemigration.service.mapper.SnowParseProcessStatusMapper;

/**
 * Service for executing complex queries for {@link SnowParseProcessStatus} entities in the database.
 * The main input is a {@link SnowParseProcessStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SnowParseProcessStatusDTO} or a {@link Page} of {@link SnowParseProcessStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SnowParseProcessStatusQueryService extends QueryService<SnowParseProcessStatus> {

    private final Logger log = LoggerFactory.getLogger(SnowParseProcessStatusQueryService.class);

    private final SnowParseProcessStatusRepository SnowParseProcessStatusRepository;

    private final SnowParseProcessStatusMapper SnowParseProcessStatusMapper;

    public SnowParseProcessStatusQueryService(SnowParseProcessStatusRepository SnowParseProcessStatusRepository, SnowParseProcessStatusMapper SnowParseProcessStatusMapper) {
        this.SnowParseProcessStatusRepository = SnowParseProcessStatusRepository;
        this.SnowParseProcessStatusMapper = SnowParseProcessStatusMapper;
    }

    /**
     * Return a {@link List} of {@link SnowParseProcessStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SnowParseProcessStatusDTO> findByCriteria(SnowParseProcessStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SnowParseProcessStatus> specification = createSpecification(criteria);
        return SnowParseProcessStatusMapper.toDto(SnowParseProcessStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SnowParseProcessStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SnowParseProcessStatusDTO> findByCriteria(SnowParseProcessStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SnowParseProcessStatus> specification = createSpecification(criteria);
        return SnowParseProcessStatusRepository.findAll(specification, page)
            .map(SnowParseProcessStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SnowParseProcessStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SnowParseProcessStatus> specification = createSpecification(criteria);
        return SnowParseProcessStatusRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<SnowParseProcessStatus> createSpecification(SnowParseProcessStatusCriteria criteria) {
        Specification<SnowParseProcessStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getBatchId() != null) {
                specification = specification.and(buildSpecification(criteria.getBatchId(), SnowParseProcessStatus_.batchId));
            }
            if (criteria.getProcessId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessId(), SnowParseProcessStatus_.processId));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SnowParseProcessStatus_.name));
            }
            if (criteria.getRunBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRunBy(), SnowParseProcessStatus_.runBy));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildSpecification(criteria.getStartTime(), SnowParseProcessStatus_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildSpecification(criteria.getEndTime(), SnowParseProcessStatus_.endTime));
            }
            if (criteria.getTotalObjects() != null) {
                specification = specification.and(buildSpecification(criteria.getTotalObjects(), SnowParseProcessStatus_.totalObjects));
            }
            if (criteria.getSuccessObjects() != null) {
                specification = specification.and(buildSpecification(criteria.getSuccessObjects(), SnowParseProcessStatus_.successObjects));
            }
            if (criteria.getErrorObjects() != null) {
                specification = specification.and(buildSpecification(criteria.getErrorObjects(), SnowParseProcessStatus_.errorObjects));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), SnowParseProcessStatus_.status));
            }
        }
        return specification;
    }
}
