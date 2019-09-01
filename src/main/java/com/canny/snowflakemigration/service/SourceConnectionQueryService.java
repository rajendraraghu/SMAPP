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

import com.canny.snowflakemigration.domain.SourceConnection;
import com.canny.snowflakemigration.domain.*; // for static metamodels
import com.canny.snowflakemigration.repository.SourceConnectionRepository;
import com.canny.snowflakemigration.service.dto.SourceConnectionCriteria;
import com.canny.snowflakemigration.service.dto.SourceConnectionDTO;
import com.canny.snowflakemigration.service.mapper.SourceConnectionMapper;

/**
 * Service for executing complex queries for {@link SourceConnection} entities in the database.
 * The main input is a {@link SourceConnectionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SourceConnectionDTO} or a {@link Page} of {@link SourceConnectionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SourceConnectionQueryService extends QueryService<SourceConnection> {

    private final Logger log = LoggerFactory.getLogger(SourceConnectionQueryService.class);

    private final SourceConnectionRepository sourceConnectionRepository;

    private final SourceConnectionMapper sourceConnectionMapper;

    public SourceConnectionQueryService(SourceConnectionRepository sourceConnectionRepository, SourceConnectionMapper sourceConnectionMapper) {
        this.sourceConnectionRepository = sourceConnectionRepository;
        this.sourceConnectionMapper = sourceConnectionMapper;
    }

    /**
     * Return a {@link List} of {@link SourceConnectionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SourceConnectionDTO> findByCriteria(SourceConnectionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SourceConnection> specification = createSpecification(criteria);
        return sourceConnectionMapper.toDto(sourceConnectionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SourceConnectionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SourceConnectionDTO> findByCriteria(SourceConnectionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SourceConnection> specification = createSpecification(criteria);
        return sourceConnectionRepository.findAll(specification, page)
            .map(sourceConnectionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SourceConnectionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SourceConnection> specification = createSpecification(criteria);
        return sourceConnectionRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<SourceConnection> createSpecification(SourceConnectionCriteria criteria) {
        Specification<SourceConnection> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SourceConnection_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SourceConnection_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SourceConnection_.description));
            }
            if (criteria.getSourceType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSourceType(), SourceConnection_.sourceType));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), SourceConnection_.url));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), SourceConnection_.username));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), SourceConnection_.password));
            }
            if (criteria.getDatabase() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDatabase(), SourceConnection_.database));
            }
            if (criteria.getHost() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHost(), SourceConnection_.host));
            }
            if (criteria.getPortnumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPortnumber(), SourceConnection_.portnumber));
            }
            if (criteria.getSchema() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSchema(), SourceConnection_.schema));
            }
            if (criteria.getValid() != null) {
                specification = specification.and(buildSpecification(criteria.getValid(), SourceConnection_.valid));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SourceConnection_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), SourceConnection_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SourceConnection_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), SourceConnection_.lastModifiedDate));
            }
        }
        return specification;
    }
}
