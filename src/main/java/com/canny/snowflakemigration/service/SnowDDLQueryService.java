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

import com.canny.snowflakemigration.domain.SnowDDL;
import com.canny.snowflakemigration.domain.*; // for static metamodels
import com.canny.snowflakemigration.repository.SnowDDLRepository;
import com.canny.snowflakemigration.service.dto.SnowDDLCriteria;
import com.canny.snowflakemigration.service.dto.SnowDDLDTO;
import com.canny.snowflakemigration.service.mapper.SnowDDLMapper;

/**
 * Service for executing complex queries for {@link SnowDDL} entities in the database.
 * The main input is a {@link SnowDDLCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SnowDDLDTO} or a {@link Page} of {@link SnowDDLDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SnowDDLQueryService extends QueryService<SnowDDL> {

    private final Logger log = LoggerFactory.getLogger(SnowDDLQueryService.class);

    private final SnowDDLRepository SnowDDLRepository;

    private final SnowDDLMapper SnowDDLMapper;

    public SnowDDLQueryService(SnowDDLRepository SnowDDLRepository, SnowDDLMapper SnowDDLMapper) {
        this.SnowDDLRepository = SnowDDLRepository;
        this.SnowDDLMapper = SnowDDLMapper;
    }

    /**
     * Return a {@link List} of {@link SnowDDLDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SnowDDLDTO> findByCriteria(SnowDDLCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SnowDDL> specification = createSpecification(criteria);
        return SnowDDLMapper.toDto(SnowDDLRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SnowDDLDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SnowDDLDTO> findByCriteria(SnowDDLCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SnowDDL> specification = createSpecification(criteria);
        return SnowDDLRepository.findAll(specification, page)
            .map(SnowDDLMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SnowDDLCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SnowDDL> specification = createSpecification(criteria);
        return SnowDDLRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<SnowDDL> createSpecification(SnowDDLCriteria criteria) {
        Specification<SnowDDL> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SnowDDL_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SnowDDL_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SnowDDL_.description));
            }
            if (criteria.getSourceType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSourceType(), SnowDDL_.sourceType));
            }
            if (criteria.getLastStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastStatus(), SnowDDL_.lastStatus));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SnowDDL_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), SnowDDL_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SnowDDL_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), SnowDDL_.lastModifiedDate));
            }
            if (criteria.getSourceConnectionId() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceConnectionId(),
                    root -> root.join(SnowDDL_.sourceConnection, JoinType.LEFT).get(SourceConnection_.id)));
            }
            if (criteria.getSnowflakeConnectionId() != null) {
                specification = specification.and(buildSpecification(criteria.getSnowflakeConnectionId(),
                    root -> root.join(SnowDDL_.snowflakeConnection, JoinType.LEFT).get(SnowflakeConnection_.id)));
            }
        }
        return specification;
    }
}
