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

import com.canny.snowflakemigration.domain.MigrationProcessJobStatus;
import com.canny.snowflakemigration.domain.*; // for static metamodels
import com.canny.snowflakemigration.repository.MigrationProcessJobStatusRepository;
import com.canny.snowflakemigration.service.dto.MigrationProcessJobStatusCriteria;
import com.canny.snowflakemigration.service.dto.MigrationProcessJobStatusDTO;
import com.canny.snowflakemigration.service.mapper.MigrationProcessJobStatusMapper;

/**
 * Service for executing complex queries for {@link SnowDDLJobStatus} entities in the database.
 * The main input is a {@link SnowDDLJobStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SnowDDLJobStatusDTO} or a {@link Page} of {@link SnowDDLJobStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MigrationProcessJobStatusQueryService extends QueryService<MigrationProcessJobStatus> {

    private final Logger log = LoggerFactory.getLogger(MigrationProcessJobStatusQueryService.class);

    private final MigrationProcessJobStatusRepository MigrationProcessJobStatusRepository;

    private final MigrationProcessJobStatusMapper MigrationProcessJobStatusMapper;

    public MigrationProcessJobStatusQueryService(MigrationProcessJobStatusRepository MigrationProcessJobStatusRepository, MigrationProcessJobStatusMapper MigrationProcessJobStatusMapper) {
        this.MigrationProcessJobStatusRepository = MigrationProcessJobStatusRepository;
        this.MigrationProcessJobStatusMapper = MigrationProcessJobStatusMapper;
    }

    /**
     * Return a {@link List} of {@link SnowDDLJobStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MigrationProcessJobStatusDTO> findByCriteria(MigrationProcessJobStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MigrationProcessJobStatus> specification = createSpecification(criteria);
        return MigrationProcessJobStatusMapper.toDto(MigrationProcessJobStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SnowDDLJobStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MigrationProcessJobStatusDTO> findByCriteria(MigrationProcessJobStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MigrationProcessJobStatus> specification = createSpecification(criteria);
        return MigrationProcessJobStatusRepository.findAll(specification, page)
            .map(MigrationProcessJobStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MigrationProcessJobStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MigrationProcessJobStatus> specification = createSpecification(criteria);
        return MigrationProcessJobStatusRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<MigrationProcessJobStatus> createSpecification(MigrationProcessJobStatusCriteria criteria) {
        Specification<MigrationProcessJobStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getJobId() != null) {
                specification = specification.and(buildSpecification(criteria.getJobId(), MigrationProcessJobStatus_.jobId));
            }
            if (criteria.getTableLoadId() != null) {
                specification = specification.and(buildSpecification(criteria.getTableLoadId(), MigrationProcessJobStatus_.tableLoadId));
            }
            if (criteria.getTableName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTableName(), MigrationProcessJobStatus_.tableName));
            }
            if (criteria.getTableLoadStartTime() != null) {
                specification = specification.and(buildSpecification(criteria.getTableLoadStartTime(), MigrationProcessJobStatus_.tableLoadStartTime));
            }
            if (criteria.getTableLoadEndTime() != null) {
                specification = specification.and(buildSpecification(criteria.getTableLoadEndTime(), MigrationProcessJobStatus_.tableLoadEndTime));
            }
            if (criteria.getTableLoadStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTableLoadStatus(), MigrationProcessJobStatus_.tableLoadStatus));
            }
            if (criteria.getInsertCount() != null) {
                specification = specification.and(buildSpecification(criteria.getInsertCount(), MigrationProcessJobStatus_.insertCount));
            }
            if (criteria.getUpdateCount() != null) {
                specification = specification.and(buildSpecification(criteria.getUpdateCount(), MigrationProcessJobStatus_.updateCount));
            }
            if (criteria.getDeleteCount() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleteCount(), MigrationProcessJobStatus_.deleteCount));
            }
            if (criteria.getRunType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRunType(), MigrationProcessJobStatus_.runType));
            }
            if (criteria.getProcessId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessId(), MigrationProcessJobStatus_.processId));
            }
            if (criteria.getProcessName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProcessName(), MigrationProcessJobStatus_.processName));
            }
            if (criteria.getSourceName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSourceName(), MigrationProcessJobStatus_.sourceName));
            }
            if (criteria.getDestName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDestName(), MigrationProcessJobStatus_.destName));
            }
        }
        return specification;
    }
}
