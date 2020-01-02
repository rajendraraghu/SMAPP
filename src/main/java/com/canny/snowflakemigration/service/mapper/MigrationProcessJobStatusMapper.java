package com.canny.snowflakemigration.service.mapper;

import com.canny.snowflakemigration.domain.*;
import com.canny.snowflakemigration.service.dto.MigrationProcessJobStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SnowDDLJobStatus} and its DTO {@link SnowDDLJobStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface MigrationProcessJobStatusMapper extends EntityMapper<MigrationProcessJobStatusDTO, MigrationProcessJobStatus> {

    MigrationProcessJobStatusDTO toDto(MigrationProcessJobStatus MigrationProcessJobStatus);   

    MigrationProcessJobStatus toEntity(MigrationProcessJobStatusDTO MigrationProcessJobStatusDTO);

    // default MigrationProcessJobStatus fromId(Long id) {
    //     if (id == null) {
    //         return null;
    //     }
    //    MigrationProcessJobStatus MigrationProcessJobStatus = new MigrationProcessJobStatus();
    //     MigrationProcessJobStatus.setTableLoadId(id);
    //     return MigrationProcessJobStatus;
    // }
}
