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

import com.canny.snowflakemigration.domain.SnowParse;
import com.canny.snowflakemigration.domain.*; // for static metamodels
import com.canny.snowflakemigration.repository.SnowParseRepository;
import com.canny.snowflakemigration.service.dto.SnowParseCriteria;
import com.canny.snowflakemigration.service.dto.SnowParseDTO;
import com.canny.snowflakemigration.service.mapper.SnowParseMapper;

/**
 * Service for executing complex queries for {@link SnowParse} entities in the database.
 * The main input is a {@link SnowParseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SnowParseDTO} or a {@link Page} of {@link SnowParseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SnowParseQueryService extends QueryService<SnowParse> {

    private final Logger log = LoggerFactory.getLogger(SnowParseQueryService.class);

    private final SnowParseRepository SnowParseRepository;

    private final SnowParseMapper SnowParseMapper;

    public SnowParseQueryService(SnowParseRepository SnowParseRepository, SnowParseMapper SnowParseMapper) {
        this.SnowParseRepository = SnowParseRepository;
        this.SnowParseMapper = SnowParseMapper;
    }

    /**
     * Return a {@link List} of {@link SnowParseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SnowParseDTO> findByCriteria(SnowParseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SnowParse> specification = createSpecification(criteria);
        return SnowParseMapper.toDto(SnowParseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SnowParseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SnowParseDTO> findByCriteria(SnowParseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SnowParse> specification = createSpecification(criteria);
        return SnowParseRepository.findAll(specification, page)
            .map(SnowParseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SnowParseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SnowParse> specification = createSpecification(criteria);
        return SnowParseRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<SnowParse> createSpecification(SnowParseCriteria criteria) {
        Specification<SnowParse> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SnowParse_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SnowParse_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SnowParse_.description));
            }
            if (criteria.getSourceType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSourceType(), SnowParse_.sourceType));
            }
            if (criteria.getLastStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastStatus(), SnowParse_.lastStatus));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SnowParse_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), SnowParse_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SnowParse_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), SnowParse_.lastModifiedDate));
            }
            if (criteria.getSourceConnectionId() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceConnectionId(),
                    root -> root.join(SnowParse_.sourceConnection, JoinType.LEFT).get(SourceConnection_.id)));
            }
            if (criteria.getSnowflakeConnectionId() != null) {
                specification = specification.and(buildSpecification(criteria.getSnowflakeConnectionId(),
                    root -> root.join(SnowParse_.snowflakeConnection, JoinType.LEFT).get(SnowflakeConnection_.id)));
            }
        }
        return specification;
    }
}
