package com.canny.snowflakemigration.service.mapper;

import com.canny.snowflakemigration.domain.*;
import com.canny.snowflakemigration.service.dto.SnowParseProcessStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SnowDDLProcessStatus} and its DTO {@link SnowDDLProcessStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface SnowParseProcessStatusMapper extends EntityMapper<SnowParseProcessStatusDTO, SnowParseProcessStatus> {

    SnowParseProcessStatusDTO toDto(SnowParseProcessStatus snowParseProcessStatus);   

    SnowParseProcessStatus toEntity(SnowParseProcessStatusDTO snowParseProcessStatusDTO);

    default SnowParseProcessStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        SnowParseProcessStatus snowParseProcessStatus = new SnowParseProcessStatus();
        snowParseProcessStatus.setBatchId(id);
        return snowParseProcessStatus;
    }
}
