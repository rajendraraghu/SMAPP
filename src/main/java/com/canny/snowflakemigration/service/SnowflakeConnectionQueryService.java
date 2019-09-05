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

import com.canny.snowflakemigration.domain.SnowflakeConnection;
import com.canny.snowflakemigration.domain.*; // for static metamodels
import com.canny.snowflakemigration.repository.SnowflakeConnectionRepository;
import com.canny.snowflakemigration.service.dto.SnowflakeConnectionCriteria;
import com.canny.snowflakemigration.service.dto.SnowflakeConnectionDTO;
import com.canny.snowflakemigration.service.mapper.SnowflakeConnectionMapper;

/**
 * Service for executing complex queries for {@link SnowflakeConnection} entities in the database.
 * The main input is a {@link SnowflakeConnectionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SnowflakeConnectionDTO} or a {@link Page} of {@link SnowflakeConnectionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SnowflakeConnectionQueryService extends QueryService<SnowflakeConnection> {

    private final Logger log = LoggerFactory.getLogger(SnowflakeConnectionQueryService.class);

    private final SnowflakeConnectionRepository snowflakeConnectionRepository;

    private final SnowflakeConnectionMapper snowflakeConnectionMapper;

    public SnowflakeConnectionQueryService(SnowflakeConnectionRepository snowflakeConnectionRepository, SnowflakeConnectionMapper snowflakeConnectionMapper) {
        this.snowflakeConnectionRepository = snowflakeConnectionRepository;
        this.snowflakeConnectionMapper = snowflakeConnectionMapper;
    }

    /**
     * Return a {@link List} of {@link SnowflakeConnectionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SnowflakeConnectionDTO> findByCriteria(SnowflakeConnectionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SnowflakeConnection> specification = createSpecification(criteria);
        return snowflakeConnectionMapper.toDto(snowflakeConnectionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SnowflakeConnectionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SnowflakeConnectionDTO> findByCriteria(SnowflakeConnectionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SnowflakeConnection> specification = createSpecification(criteria);
        return snowflakeConnectionRepository.findAll(specification, page)
            .map(snowflakeConnectionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SnowflakeConnectionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SnowflakeConnection> specification = createSpecification(criteria);
        return snowflakeConnectionRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<SnowflakeConnection> createSpecification(SnowflakeConnectionCriteria criteria) {
        Specification<SnowflakeConnection> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SnowflakeConnection_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SnowflakeConnection_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SnowflakeConnection_.description));
            }
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegionId(), SnowflakeConnection_.regionId));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), SnowflakeConnection_.url));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), SnowflakeConnection_.username));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), SnowflakeConnection_.password));
            }
            if (criteria.getAcct() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcct(), SnowflakeConnection_.acct));
            }
            if (criteria.getWarehouse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWarehouse(), SnowflakeConnection_.warehouse));
            }
            if (criteria.getDatabase() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDatabase(), SnowflakeConnection_.database));
            }
            if (criteria.getSchema() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSchema(), SnowflakeConnection_.schema));
            }
            if (criteria.getValid() != null) {
                specification = specification.and(buildSpecification(criteria.getValid(), SnowflakeConnection_.valid));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SnowflakeConnection_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), SnowflakeConnection_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SnowflakeConnection_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), SnowflakeConnection_.lastModifiedDate));
            }
        }
        return specification;
    }
}
