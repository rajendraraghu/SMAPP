package com.canny.snowflakemigration.service.mapper;

import com.canny.snowflakemigration.domain.*;
import com.canny.snowflakemigration.service.dto.DeltaProcessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeltaProcess} and its DTO {@link DeltaProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = {SourceConnectionMapper.class, SnowflakeConnectionMapper.class})
public interface DeltaProcessMapper extends EntityMapper<DeltaProcessDTO, DeltaProcess> {

    @Mapping(source = "sourceConnection.id", target = "sourceConnectionId")
    @Mapping(source = "sourceConnection.name", target = "sourceConnectionName")
    @Mapping(source = "sourceConnection.url", target = "sourceConnectionUrl")
    @Mapping(source = "sourceConnection.username", target = "sourceConnectionUsername")
    @Mapping(source = "sourceConnection.password", target = "sourceConnectionPassword")
    @Mapping(source = "sourceConnection.database", target = "sourceConnectionDatabase")
    @Mapping(source = "sourceConnection.schema", target = "sourceConnectionSchema")
	@Mapping(source = "sourceConnection.sourceType", target = "sourceType")
    @Mapping(source = "snowflakeConnection.id", target = "snowflakeConnectionId")
    @Mapping(source = "snowflakeConnection.name", target = "snowflakeConnectionName")
    @Mapping(source = "snowflakeConnection.url", target = "snowflakeConnectionUrl")
    @Mapping(source = "snowflakeConnection.username", target = "snowflakeConnectionUsername")
    @Mapping(source = "snowflakeConnection.password", target = "snowflakeConnectionPassword")
    @Mapping(source = "snowflakeConnection.acct", target = "snowflakeConnectionAcct")
    @Mapping(source = "snowflakeConnection.warehouse", target = "snowflakeConnectionWarehouse")
    @Mapping(source = "snowflakeConnection.database", target = "snowflakeConnectionDatabase")
    @Mapping(source = "snowflakeConnection.schema", target = "snowflakeConnectionSchema")
    DeltaProcessDTO toDto(DeltaProcess deltaProcess);

    @Mapping(source = "sourceConnectionId", target = "sourceConnection")
    @Mapping(source = "snowflakeConnectionId", target = "snowflakeConnection")
    DeltaProcess toEntity(DeltaProcessDTO deltaProcessDTO);

    default DeltaProcess fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeltaProcess deltaProcess = new DeltaProcess();
        deltaProcess.setId(id);
        return deltaProcess;
    }
}
