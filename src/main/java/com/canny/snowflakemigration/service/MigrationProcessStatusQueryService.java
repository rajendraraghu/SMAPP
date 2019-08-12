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

import com.canny.snowflakemigration.domain.MigrationProcessStatus;
import com.canny.snowflakemigration.domain.*; // for static metamodels
import com.canny.snowflakemigration.repository.MigrationProcessStatusRepository;
import com.canny.snowflakemigration.service.dto.MigrationProcessStatusCriteria;
import com.canny.snowflakemigration.service.dto.MigrationProcessStatusDTO;
import com.canny.snowflakemigration.service.mapper.MigrationProcessStatusMapper;

/**
 * Service for executing complex queries for {@link SnowDDLProcessStatus} entities in the database.
 * The main input is a {@link SnowDDLProcessStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SnowDDLProcessStatusDTO} or a {@link Page} of {@link SnowDDLProcessStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MigrationProcessStatusQueryService extends QueryService<MigrationProcessStatus> {

    private final Logger log = LoggerFactory.getLogger(MigrationProcessStatusQueryService.class);

    private final MigrationProcessStatusRepository MigrationProcessStatusRepository;

    private final MigrationProcessStatusMapper MigrationProcessStatusMapper;

    public MigrationProcessStatusQueryService(MigrationProcessStatusRepository MigrationProcessStatusRepository, MigrationProcessStatusMapper MigrationProcessStatusMapper) {
        this.MigrationProcessStatusRepository = MigrationProcessStatusRepository;
        this.MigrationProcessStatusMapper = MigrationProcessStatusMapper;
    }

    /**
     * Return a {@link List} of {@link SnowDDLProcessStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MigrationProcessStatusDTO> findByCriteria(MigrationProcessStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MigrationProcessStatus> specification = createSpecification(criteria);
        return MigrationProcessStatusMapper.toDto(MigrationProcessStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SnowDDLProcessStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MigrationProcessStatusDTO> findByCriteria(MigrationProcessStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MigrationProcessStatus> specification = createSpecification(criteria);
        return MigrationProcessStatusRepository.findAll(specification, page)
            .map(MigrationProcessStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MigrationProcessStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MigrationProcessStatus> specification = createSpecification(criteria);
        return MigrationProcessStatusRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<MigrationProcessStatus> createSpecification(MigrationProcessStatusCriteria criteria) {
        Specification<MigrationProcessStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getJobId() != null) {
                specification = specification.and(buildSpecification(criteria.getJobId(), MigrationProcessStatus_.jobId));
            }
            if (criteria.getProcessId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessId(), MigrationProcessStatus_.processId));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(),MigrationProcessStatus_.name));
            }
            if (criteria.getRunBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRunBy(), MigrationProcessStatus_.runBy));
            }
            if (criteria.getJobStartTime() != null) {
                specification = specification.and(buildSpecification(criteria.getJobStartTime(), MigrationProcessStatus_.jobStartTime));
            }
            if (criteria.getJobEndTime() != null) {
                specification = specification.and(buildSpecification(criteria.getJobEndTime(), MigrationProcessStatus_.jobEndTime));
            }
            if (criteria.getTableCount() != null) {
                specification = specification.and(buildSpecification(criteria.getTableCount(), MigrationProcessStatus_.tableCount));
            }
            if (criteria.getSuccessCount() != null) {
                specification = specification.and(buildSpecification(criteria.getSuccessCount(), MigrationProcessStatus_.successCount));
            }
            if (criteria.getFailureCount() != null) {
                specification = specification.and(buildSpecification(criteria.getFailureCount(), MigrationProcessStatus_.failureCount));
            }
            if (criteria.getJobStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJobStatus(), MigrationProcessStatus_.jobStatus));
            }
        }
        return specification;
    }
}
