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

import com.canny.snowflakemigration.domain.DeltaProcess;
import com.canny.snowflakemigration.domain.*; // for static metamodels
import com.canny.snowflakemigration.repository.DeltaProcessRepository;
import com.canny.snowflakemigration.service.dto.DeltaProcessCriteria;
import com.canny.snowflakemigration.service.dto.DeltaProcessDTO;
import com.canny.snowflakemigration.service.mapper.DeltaProcessMapper;

/**
 * Service for executing complex queries for {@link DeltaProcess} entities in the database.
 * The main input is a {@link DeltaProcessCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeltaProcessDTO} or a {@link Page} of {@link DeltaProcessDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeltaProcessQueryService extends QueryService<DeltaProcess> {

    private final Logger log = LoggerFactory.getLogger(DeltaProcessQueryService.class);

    private final DeltaProcessRepository deltaProcessRepository;

    private final DeltaProcessMapper deltaProcessMapper;

    public DeltaProcessQueryService(DeltaProcessRepository deltaProcessRepository, DeltaProcessMapper deltaProcessMapper) {
        this.deltaProcessRepository = deltaProcessRepository;
        this.deltaProcessMapper = deltaProcessMapper;
    }

    /**
     * Return a {@link List} of {@link DeltaProcessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeltaProcessDTO> findByCriteria(DeltaProcessCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DeltaProcess> specification = createSpecification(criteria);
        return deltaProcessMapper.toDto(deltaProcessRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DeltaProcessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeltaProcessDTO> findByCriteria(DeltaProcessCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DeltaProcess> specification = createSpecification(criteria);
        return deltaProcessRepository.findAll(specification, page)
            .map(deltaProcessMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeltaProcessCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DeltaProcess> specification = createSpecification(criteria);
        return deltaProcessRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    private Specification<DeltaProcess> createSpecification(DeltaProcessCriteria criteria) {
        Specification<DeltaProcess> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DeltaProcess_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DeltaProcess_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), DeltaProcess_.description));
            }
            if (criteria.getTablesList() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTablesList(), DeltaProcess_.tablesList));
            }
            if (criteria.getPk() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPk(), DeltaProcess_.pk));
            }
            if (criteria.getLastStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastStatus(), DeltaProcess_.lastStatus));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), DeltaProcess_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), DeltaProcess_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), DeltaProcess_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), DeltaProcess_.lastModifiedDate));
            }
            if (criteria.getSourceConnectionId() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceConnectionId(),
                    root -> root.join(DeltaProcess_.sourceConnection, JoinType.LEFT).get(SourceConnection_.id)));
            }
            if (criteria.getSnowflakeConnectionId() != null) {
                specification = specification.and(buildSpecification(criteria.getSnowflakeConnectionId(),
                    root -> root.join(DeltaProcess_.snowflakeConnection, JoinType.LEFT).get(SnowflakeConnection_.id)));
            }
        }
        return specification;
    }
}
