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
    @Mapping(source = "sourceConnection.url", target = "sourceConnectionUrl")
    @Mapping(source = "sourceConnection.username", target = "sourceConnectionUsername")
    @Mapping(source = "sourceConnection.password", target = "sourceConnectionPassword")
    @Mapping(source = "sourceConnection.database", target = "sourceConnectionDatabase")
    @Mapping(source = "sourceConnection.host", target = "sourceConnectionHost")
    @Mapping(source = "sourceConnection.sourceType", target = "sourceType")
    @Mapping(source = "sourceConnection.portNumber", target = "sourceConnectionPortNumber")
    @Mapping(source = "sourceConnection.schema", target = "sourceConnectionSchema")
    @Mapping(source = "snowflakeConnection.id", target = "snowflakeConnectionId")
    @Mapping(source = "snowflakeConnection.name", target = "snowflakeConnectionName")
    // @Mapping(source = "snowflakeConnection.regionId", target = "snowflakeConnectionRegionId")
    @Mapping(source = "snowflakeConnection.url", target = "snowflakeConnectionUrl")
    @Mapping(source = "snowflakeConnection.username", target = "snowflakeConnectionUsername")
    @Mapping(source = "snowflakeConnection.password", target = "snowflakeConnectionPassword")
    @Mapping(source = "snowflakeConnection.acct", target = "snowflakeConnectionAcct")
    @Mapping(source = "snowflakeConnection.warehouse", target = "snowflakeConnectionWarehouse")
    @Mapping(source = "snowflakeConnection.database", target = "snowflakeConnectionDatabase")
    @Mapping(source = "snowflakeConnection.schema", target = "snowflakeConnectionSchema")
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
