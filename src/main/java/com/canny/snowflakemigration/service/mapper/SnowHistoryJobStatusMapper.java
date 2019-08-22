package com.canny.snowflakemigration.service.mapper;

import com.canny.snowflakemigration.domain.*;
import com.canny.snowflakemigration.service.dto.SnowHistoryJobStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SnowHistoryJobStatus} and its DTO {@link SnowHistoryJobStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface SnowHistoryJobStatusMapper extends EntityMapper<SnowHistoryJobStatusDTO, SnowHistoryJobStatus> {

    SnowHistoryJobStatusDTO toDto(SnowHistoryJobStatus SnowHistoryJobStatus);   

    SnowHistoryJobStatus toEntity(SnowHistoryJobStatusDTO SnowHistoryJobStatusDTO);

    default SnowHistoryJobStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        SnowHistoryJobStatus SnowHistoryJobStatus = new SnowHistoryJobStatus();
        SnowHistoryJobStatus.setBatchId(id);
        return SnowHistoryJobStatus;
    }
}
