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

import com.canny.snowflakemigration.domain.DeltaProcessJobStatus;
import com.canny.snowflakemigration.domain.*; // for static metamodels
import com.canny.snowflakemigration.repository.DeltaProcessJobStatusRepository;
import com.canny.snowflakemigration.service.dto.DeltaProcessJobStatusCriteria;
import com.canny.snowflakemigration.service.dto.DeltaProcessJobStatusDTO;
import com.canny.snowflakemigration.service.mapper.DeltaProcessJobStatusMapper;

/**
 * Service for executing complex queries for {@link SnowDDLJobStatus} entities in the database.
 * The main input is a {@link DeltaJobStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeltaJobStatusDTO} or a {@link Page} of {@link DeltaJobStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeltaProcessJobStatusQueryService extends QueryService<DeltaProcessJobStatus> {

    private final Logger log = LoggerFactory.getLogger(DeltaProcessJobStatusQueryService.class);

    private final DeltaProcessJobStatusRepository DeltaProcessJobStatusRepository;

    private final DeltaProcessJobStatusMapper DeltaProcessJobStatusMapper;

    public DeltaProcessJobStatusQueryService(DeltaProcessJobStatusRepository DeltaProcessJobStatusRepository, DeltaProcessJobStatusMapper DeltaProcessJobStatusMapper) {
        this.DeltaProcessJobStatusRepository = DeltaProcessJobStatusRepository;
        this.DeltaProcessJobStatusMapper = DeltaProcessJobStatusMapper;
    }

    /**
     * Return a {@link List} of {@link DeltaJobStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeltaProcessJobStatusDTO> findByCriteria(DeltaProcessJobStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DeltaProcessJobStatus> specification = createSpecification(criteria);
        return DeltaProcessJobStatusMapper.toDto(DeltaProcessJobStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DeltaJobStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeltaProcessJobStatusDTO> findByCriteria(DeltaProcessJobStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DeltaProcessJobStatus> specification = createSpecification(criteria);
        return DeltaProcessJobStatusRepository.findAll(specification, page)
            .map(DeltaProcessJobStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeltaProcessJobStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DeltaProcessJobStatus> specification = createSpecification(criteria);
        return DeltaProcessJobStatusRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    private Specification<DeltaProcessJobStatus> createSpecification(DeltaProcessJobStatusCriteria criteria) {
        Specification<DeltaProcessJobStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getJobId() != null) {
                specification = specification.and(buildSpecification(criteria.getJobId(), DeltaProcessJobStatus_.jobId));
            }
            if (criteria.getTableLoadId() != null) {
                specification = specification.and(buildSpecification(criteria.getTableLoadId(), DeltaProcessJobStatus_.tableLoadId));
            }
            if (criteria.getTableName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTableName(), DeltaProcessJobStatus_.tableName));
            }
            if (criteria.getTableLoadStartTime() != null) {
                specification = specification.and(buildSpecification(criteria.getTableLoadStartTime(), DeltaProcessJobStatus_.tableLoadStartTime));
            }
            if (criteria.getTableLoadEndTime() != null) {
                specification = specification.and(buildSpecification(criteria.getTableLoadEndTime(), DeltaProcessJobStatus_.tableLoadEndTime));
            }
            if (criteria.getTableLoadStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTableLoadStatus(), DeltaProcessJobStatus_.tableLoadStatus));
            }
            if (criteria.getInsertCount() != null) {
                specification = specification.and(buildSpecification(criteria.getInsertCount(), DeltaProcessJobStatus_.insertCount));
            }
            if (criteria.getUpdateCount() != null) {
                specification = specification.and(buildSpecification(criteria.getUpdateCount(), DeltaProcessJobStatus_.updateCount));
            }
            if (criteria.getDeleteCount() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleteCount(), DeltaProcessJobStatus_.deleteCount));
            }
            if (criteria.getRunType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRunType(), DeltaProcessJobStatus_.runType));
            }
            if (criteria.getProcessId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessId(), DeltaProcessJobStatus_.processId));
            }
            if (criteria.getProcessName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProcessName(), DeltaProcessJobStatus_.processName));
            }
            // if (criteria.getSourceName() != null) {
            //     specification = specification.and(buildStringSpecification(criteria.getSourceName(), DeltaProcessJobStatus_.sourceName));
            // }
            // if (criteria.getDestName() != null) {
            //     specification = specification.and(buildStringSpecification(criteria.getDestName(), DeltaProcessJobStatus_.destName));
            // }
        }
        return specification;
    }
}
