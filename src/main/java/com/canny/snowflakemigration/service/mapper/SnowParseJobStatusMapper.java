package com.canny.snowflakemigration.service.mapper;

import com.canny.snowflakemigration.domain.*;
import com.canny.snowflakemigration.service.dto.SnowParseJobStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SnowParseJobStatus} and its DTO {@link SnowParseJobStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface SnowParseJobStatusMapper extends EntityMapper<SnowParseJobStatusDTO, SnowParseJobStatus> {

    SnowParseJobStatusDTO toDto(SnowParseJobStatus SnowParseJobStatus);   

    SnowParseJobStatus toEntity(SnowParseJobStatusDTO SnowParseJobStatusDTO);

    default SnowParseJobStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        SnowParseJobStatus SnowParseJobStatus = new SnowParseJobStatus();
        SnowParseJobStatus.setBatchId(id);
        return SnowParseJobStatus;
    }
}