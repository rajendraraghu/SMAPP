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

import com.canny.snowflakemigration.domain.MigrationProcess;
import com.canny.snowflakemigration.domain.*; // for static metamodels
import com.canny.snowflakemigration.repository.MigrationProcessRepository;
import com.canny.snowflakemigration.service.dto.MigrationProcessCriteria;
import com.canny.snowflakemigration.service.dto.MigrationProcessDTO;
import com.canny.snowflakemigration.service.mapper.MigrationProcessMapper;

/**
 * Service for executing complex queries for {@link MigrationProcess} entities in the database.
 * The main input is a {@link MigrationProcessCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MigrationProcessDTO} or a {@link Page} of {@link MigrationProcessDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MigrationProcessQueryService extends QueryService<MigrationProcess> {

    private final Logger log = LoggerFactory.getLogger(MigrationProcessQueryService.class);

    private final MigrationProcessRepository migrationProcessRepository;

    private final MigrationProcessMapper migrationProcessMapper;

    public MigrationProcessQueryService(MigrationProcessRepository migrationProcessRepository, MigrationProcessMapper migrationProcessMapper) {
        this.migrationProcessRepository = migrationProcessRepository;
        this.migrationProcessMapper = migrationProcessMapper;
    }

    /**
     * Return a {@link List} of {@link MigrationProcessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MigrationProcessDTO> findByCriteria(MigrationProcessCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MigrationProcess> specification = createSpecification(criteria);
        return migrationProcessMapper.toDto(migrationProcessRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MigrationProcessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MigrationProcessDTO> findByCriteria(MigrationProcessCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MigrationProcess> specification = createSpecification(criteria);
        return migrationProcessRepository.findAll(specification, page)
            .map(migrationProcessMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MigrationProcessCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MigrationProcess> specification = createSpecification(criteria);
        return migrationProcessRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<MigrationProcess> createSpecification(MigrationProcessCriteria criteria) {
        Specification<MigrationProcess> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MigrationProcess_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MigrationProcess_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MigrationProcess_.description));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), MigrationProcess_.type));
            }
            if (criteria.getTablesToMigrate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTablesToMigrate(), MigrationProcess_.tablesToMigrate));
            }
            if (criteria.getSelectedColumns() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSelectedColumns(), MigrationProcess_.selectedColumns));
            }
            if (criteria.getLastStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastStatus(), MigrationProcess_.lastStatus));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), MigrationProcess_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), MigrationProcess_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), MigrationProcess_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), MigrationProcess_.lastModifiedDate));
            }
            if (criteria.getSourceConnectionId() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceConnectionId(),
                    root -> root.join(MigrationProcess_.sourceConnection, JoinType.LEFT).get(SourceConnection_.id)));
            }
            if (criteria.getSnowflakeConnectionId() != null) {
                specification = specification.and(buildSpecification(criteria.getSnowflakeConnectionId(),
                    root -> root.join(MigrationProcess_.snowflakeConnection, JoinType.LEFT).get(SnowflakeConnection_.id)));
            }
        }
        return specification;
    }
}
