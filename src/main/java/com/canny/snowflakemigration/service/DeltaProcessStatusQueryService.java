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

import com.canny.snowflakemigration.domain.DeltaProcessStatus;
import com.canny.snowflakemigration.domain.*; // for static metamodels
import com.canny.snowflakemigration.repository.DeltaProcessStatusRepository;
import com.canny.snowflakemigration.service.dto.DeltaProcessStatusCriteria;
import com.canny.snowflakemigration.service.dto.DeltaProcessStatusDTO;
import com.canny.snowflakemigration.service.mapper.DeltaProcessStatusMapper;

/**
 * Service for executing complex queries for {@link DeltaProcessStatus} entities in the database.
 * The main input is a {@link DeltaProcessStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeltaProcessStatusDTO} or a {@link Page} of {@link DeltaProcessStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeltaProcessStatusQueryService extends QueryService<DeltaProcessStatus> {

    private final Logger log = LoggerFactory.getLogger(DeltaProcessStatusQueryService.class);

    private final DeltaProcessStatusRepository DeltaProcessStatusRepository;

    private final DeltaProcessStatusMapper DeltaProcessStatusMapper;

    public DeltaProcessStatusQueryService(DeltaProcessStatusRepository DeltaProcessStatusRepository, DeltaProcessStatusMapper DeltaProcessStatusMapper) {
        this.DeltaProcessStatusRepository = DeltaProcessStatusRepository;
        this.DeltaProcessStatusMapper = DeltaProcessStatusMapper;
    }

    /**
     * Return a {@link List} of {@link DeltaProcessStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeltaProcessStatusDTO> findByCriteria(DeltaProcessStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DeltaProcessStatus> specification = createSpecification(criteria);
        return DeltaProcessStatusMapper.toDto(DeltaProcessStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DeltaProcessStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeltaProcessStatusDTO> findByCriteria(DeltaProcessStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DeltaProcessStatus> specification = createSpecification(criteria);
        return DeltaProcessStatusRepository.findAll(specification, page)
            .map(DeltaProcessStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeltaProcessStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DeltaProcessStatus> specification = createSpecification(criteria);
        return DeltaProcessStatusRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    private Specification<DeltaProcessStatus> createSpecification(DeltaProcessStatusCriteria criteria) {
        Specification<DeltaProcessStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getJobId() != null) {
                specification = specification.and(buildSpecification(criteria.getJobId(), DeltaProcessStatus_.jobId));
            }
            if (criteria.getProcessId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessId(), DeltaProcessStatus_.processId));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(),DeltaProcessStatus_.name));
            }
            if (criteria.getRunBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRunBy(), DeltaProcessStatus_.runBy));
            }
            if (criteria.getJobStartTime() != null) {
                specification = specification.and(buildSpecification(criteria.getJobStartTime(), DeltaProcessStatus_.jobStartTime));
            }
            if (criteria.getJobEndTime() != null) {
                specification = specification.and(buildSpecification(criteria.getJobEndTime(), DeltaProcessStatus_.jobEndTime));
            }
            if (criteria.getTableCount() != null) {
                specification = specification.and(buildSpecification(criteria.getTableCount(), DeltaProcessStatus_.tableCount));
            }
            if (criteria.getSuccessCount() != null) {
                specification = specification.and(buildSpecification(criteria.getSuccessCount(), DeltaProcessStatus_.successCount));
            }
            if (criteria.getFailureCount() != null) {
                specification = specification.and(buildSpecification(criteria.getFailureCount(), DeltaProcessStatus_.failureCount));
            }
            if (criteria.getJobStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJobStatus(), DeltaProcessStatus_.jobStatus));
            }
        }
        return specification;
    }
}
