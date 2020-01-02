package com.canny.snowflakemigration.service.mapper;

import com.canny.snowflakemigration.domain.*;
import com.canny.snowflakemigration.service.dto.DeltaProcessStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeltaProcessStatus} and its DTO {@link DeltaProcessStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface DeltaProcessStatusMapper extends EntityMapper<DeltaProcessStatusDTO, DeltaProcessStatus> {

    DeltaProcessStatusDTO toDto(DeltaProcessStatus DeltaProcessStatus);

    DeltaProcessStatus toEntity(DeltaProcessStatusDTO DeltaProcessStatusDTO);

    default DeltaProcessStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeltaProcessStatus DeltaProcessStatus = new DeltaProcessStatus();
        DeltaProcessStatus.setJobId(id);
        return DeltaProcessStatus;
    }
}
