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

import com.canny.snowflakemigration.domain.SnowHistory;
import com.canny.snowflakemigration.domain.*; // for static metamodels
import com.canny.snowflakemigration.repository.SnowHistoryRepository;
import com.canny.snowflakemigration.service.dto.SnowHistoryCriteria;
import com.canny.snowflakemigration.service.dto.SnowHistoryDTO;
import com.canny.snowflakemigration.service.mapper.SnowHistoryMapper;

/**
 * Service for executing complex queries for {@link SnowHistory} entities in the database.
 * The main input is a {@link SnowHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SnowHistoryDTO} or a {@link Page} of {@link SnowHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SnowHistoryQueryService extends QueryService<SnowHistory> {

    private final Logger log = LoggerFactory.getLogger(SnowHistoryQueryService.class);

    private final SnowHistoryRepository snowHistoryRepository;

    private final SnowHistoryMapper snowHistoryMapper;

    public SnowHistoryQueryService(SnowHistoryRepository snowHistoryRepository, SnowHistoryMapper snowHistoryMapper) {
        this.snowHistoryRepository = snowHistoryRepository;
        this.snowHistoryMapper = snowHistoryMapper;
    }

    /**
     * Return a {@link List} of {@link SnowHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SnowHistoryDTO> findByCriteria(SnowHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SnowHistory> specification = createSpecification(criteria);
        return snowHistoryMapper.toDto(snowHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SnowHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SnowHistoryDTO> findByCriteria(SnowHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SnowHistory> specification = createSpecification(criteria);
        return snowHistoryRepository.findAll(specification, page)
            .map(snowHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SnowHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SnowHistory> specification = createSpecification(criteria);
        return snowHistoryRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<SnowHistory> createSpecification(SnowHistoryCriteria criteria) {
        Specification<SnowHistory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SnowHistory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SnowHistory_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SnowHistory_.description));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), SnowHistory_.type));
            }
            if (criteria.getTablesToMigrate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTablesToMigrate(), SnowHistory_.tablesToMigrate));
            }
            if (criteria.getLastStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastStatus(), SnowHistory_.lastStatus));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SnowHistory_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), SnowHistory_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SnowHistory_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), SnowHistory_.lastModifiedDate));
            }
            if (criteria.getSourceConnectionId() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceConnectionId(),
                    root -> root.join(SnowHistory_.sourceConnection, JoinType.LEFT).get(SourceConnection_.id)));
            }
            if (criteria.getSnowflakeConnectionId() != null) {
                specification = specification.and(buildSpecification(criteria.getSnowflakeConnectionId(),
                    root -> root.join(SnowHistory_.snowflakeConnection, JoinType.LEFT).get(SnowflakeConnection_.id)));
            }
        }
        return specification;
    }
}
