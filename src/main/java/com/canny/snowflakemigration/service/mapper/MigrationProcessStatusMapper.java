package com.canny.snowflakemigration.service.mapper;

import com.canny.snowflakemigration.domain.*;
import com.canny.snowflakemigration.service.dto.MigrationProcessStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SnowDDLProcessStatus} and its DTO {@link SnowDDLProcessStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface MigrationProcessStatusMapper extends EntityMapper<MigrationProcessStatusDTO, MigrationProcessStatus> {

    MigrationProcessStatusDTO toDto(MigrationProcessStatus MigrationProcessStatus);   

    MigrationProcessStatus toEntity(MigrationProcessStatusDTO MigrationProcessStatusDTO);

    default MigrationProcessStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        MigrationProcessStatus MigrationProcessStatus = new MigrationProcessStatus();
        MigrationProcessStatus.setJobId(id);
        return MigrationProcessStatus;
    }
}
