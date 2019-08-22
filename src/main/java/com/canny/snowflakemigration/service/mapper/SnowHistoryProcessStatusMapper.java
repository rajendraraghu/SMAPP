package com.canny.snowflakemigration.service.mapper;

import com.canny.snowflakemigration.domain.*;
import com.canny.snowflakemigration.service.dto.SnowHistoryProcessStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@linkSnowHistoryProcessStatus} and its DTO {@link SnowHistoryProcessStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface SnowHistoryProcessStatusMapper extends EntityMapper<SnowHistoryProcessStatusDTO, SnowHistoryProcessStatus> {

    SnowHistoryProcessStatusDTO toDto(SnowHistoryProcessStatus snowHistoryProcessStatus);   

    SnowHistoryProcessStatus toEntity(SnowHistoryProcessStatusDTO snowHistoryProcessStatusDTO);

    default SnowHistoryProcessStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        SnowHistoryProcessStatus snowHistoryProcessStatus = new SnowHistoryProcessStatus();
        snowHistoryProcessStatus.setBatchId(id);
        return snowHistoryProcessStatus;
    }
}
