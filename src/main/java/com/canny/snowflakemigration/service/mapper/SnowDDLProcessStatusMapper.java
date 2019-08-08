package com.canny.snowflakemigration.service.mapper;

import com.canny.snowflakemigration.domain.*;
import com.canny.snowflakemigration.service.dto.SnowDDLProcessStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SnowDDLProcessStatus} and its DTO {@link SnowDDLProcessStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface SnowDDLProcessStatusMapper extends EntityMapper<SnowDDLProcessStatusDTO, SnowDDLProcessStatus> {

    SnowDDLProcessStatusDTO toDto(SnowDDLProcessStatus SnowDDLProcessStatus);   

    SnowDDLProcessStatus toEntity(SnowDDLProcessStatusDTO SnowDDLProcessStatusDTO);

    default SnowDDLProcessStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        SnowDDLProcessStatus SnowDDLProcessStatus = new SnowDDLProcessStatus();
        SnowDDLProcessStatus.setBatchId(id);
        return SnowDDLProcessStatus;
    }
}
