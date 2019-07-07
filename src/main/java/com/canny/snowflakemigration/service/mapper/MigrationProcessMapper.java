package com.canny.snowflakemigration.service.mapper;

import com.canny.snowflakemigration.domain.*;
import com.canny.snowflakemigration.service.dto.MigrationProcessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MigrationProcess} and its DTO {@link MigrationProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = {SourceConnectionMapper.class, SnowflakeConnectionMapper.class})
public interface MigrationProcessMapper extends EntityMapper<MigrationProcessDTO, MigrationProcess> {

    @Mapping(source = "sourceConnection.id", target = "sourceConnectionId")
    @Mapping(source = "sourceConnection.name", target = "sourceConnectionName")
    @Mapping(source = "snowflakeConnection.id", target = "snowflakeConnectionId")
    @Mapping(source = "snowflakeConnection.name", target = "snowflakeConnectionName")
    MigrationProcessDTO toDto(MigrationProcess migrationProcess);

    @Mapping(source = "sourceConnectionId", target = "sourceConnection")
    @Mapping(source = "snowflakeConnectionId", target = "snowflakeConnection")
    MigrationProcess toEntity(MigrationProcessDTO migrationProcessDTO);

    default MigrationProcess fromId(Long id) {
        if (id == null) {
            return null;
        }
        MigrationProcess migrationProcess = new MigrationProcess();
        migrationProcess.setId(id);
        return migrationProcess;
    }
}
