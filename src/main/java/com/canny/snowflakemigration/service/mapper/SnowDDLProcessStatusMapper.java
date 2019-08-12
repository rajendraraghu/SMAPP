package com.canny.snowflakemigration.service.mapper;

import com.canny.snowflakemigration.domain.*;
import com.canny.snowflakemigration.service.dto.SnowDDLProcessStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SnowDDLProcessStatus} and its DTO {@link SnowDDLProcessStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface SnowDDLProcessStatusMapper extends EntityMapper<SnowDDLProcessStatusDTO, SnowDDLProcessStatus> {

    SnowDDLProcessStatusDTO toDto(SnowDDLProcessStatus snowDDLProcessStatus);   

    SnowDDLProcessStatus toEntity(SnowDDLProcessStatusDTO snowDDLProcessStatusDTO);

    default SnowDDLProcessStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        SnowDDLProcessStatus snowDDLProcessStatus = new SnowDDLProcessStatus();
        snowDDLProcessStatus.setBatchId(id);
        return snowDDLProcessStatus;
    }
}
