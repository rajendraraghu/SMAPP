package com.canny.snowflakemigration.service.mapper;

import com.canny.snowflakemigration.domain.*;
import com.canny.snowflakemigration.service.dto.SnowDDLJobStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SnowDDLJobStatus} and its DTO {@link SnowDDLJobStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface SnowDDLJobStatusMapper extends EntityMapper<SnowDDLJobStatusDTO, SnowDDLJobStatus> {

    SnowDDLJobStatusDTO toDto(SnowDDLJobStatus SnowDDLJobStatus);   

    SnowDDLJobStatus toEntity(SnowDDLJobStatusDTO SnowDDLJobStatusDTO);

    default SnowDDLJobStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        SnowDDLJobStatus SnowDDLJobStatus = new SnowDDLJobStatus();
        SnowDDLJobStatus.setBatchId(id);
        return SnowDDLJobStatus;
    }
}
