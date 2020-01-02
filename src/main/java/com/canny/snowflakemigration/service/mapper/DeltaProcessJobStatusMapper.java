package com.canny.snowflakemigration.service.mapper;

import com.canny.snowflakemigration.domain.*;
import com.canny.snowflakemigration.service.dto.DeltaProcessJobStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeltaJobStatus} and its DTO {@link DeltaJobStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface DeltaProcessJobStatusMapper extends EntityMapper<DeltaProcessJobStatusDTO, DeltaProcessJobStatus> {

    DeltaProcessJobStatusDTO toDto(DeltaProcessJobStatus DeltaProcessJobStatus);

    DeltaProcessJobStatus toEntity(DeltaProcessJobStatusDTO DeltaProcessJobStatusDTO);

    // default DeltaProcessJobStatus fromId(Long id) {
    //     if (id == null) {
    //         return null;
    //     }
    //    DeltaProcessJobStatus DeltaProcessJobStatus = new DeltaProcessJobStatus();
    //     DeltaProcessJobStatus.setTableLoadId(id);
    //     return DeltaProcessJobStatus;
    // }
}
