package com.canny.snowflakemigration.service.mapper;

import com.canny.snowflakemigration.domain.*;
import com.canny.snowflakemigration.service.dto.SnowDDLDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SnowDDL} and its DTO {@link SnowDDLDTO}.
 */
@Mapper(componentModel = "spring", uses = {SourceConnectionMapper.class, SnowflakeConnectionMapper.class})
public interface SnowDDLMapper extends EntityMapper<SnowDDLDTO, SnowDDL> {

    @Mapping(source = "sourceConnection.id", target = "sourceConnectionId")
    @Mapping(source = "sourceConnection.name", target = "sourceConnectionName")
    @Mapping(source = "sourceConnection.url", target = "sourceConnectionUrl")
    @Mapping(source = "sourceConnection.username", target = "sourceConnectionUsername")
    @Mapping(source = "sourceConnection.password", target = "sourceConnectionPassword")
    @Mapping(source = "sourceConnection.database", target = "sourceConnectionDatabase")
    @Mapping(source = "sourceConnection.schema", target = "sourceConnectionSchema")
    @Mapping(source = "snowflakeConnection.id", target = "snowflakeConnectionId")
    @Mapping(source = "snowflakeConnection.name", target = "snowflakeConnectionName")
    @Mapping(source = "snowflakeConnection.url", target = "snowflakeConnectionUrl")
    @Mapping(source = "snowflakeConnection.username", target = "snowflakeConnectionUsername")
    @Mapping(source = "snowflakeConnection.password", target = "snowflakeConnectionPassword")
    @Mapping(source = "snowflakeConnection.acct", target = "snowflakeConnectionAcct")
    @Mapping(source = "snowflakeConnection.warehouse", target = "snowflakeConnectionWarehouse")
    @Mapping(source = "snowflakeConnection.database", target = "snowflakeConnectionDatabase")
    @Mapping(source = "snowflakeConnection.schema", target = "snowflakeConnectionSchema")
    SnowDDLDTO toDto(SnowDDL SnowDDL);   

    @Mapping(source = "sourceConnectionId", target = "sourceConnection")
    @Mapping(source = "snowflakeConnectionId", target = "snowflakeConnection")
    SnowDDL toEntity(SnowDDLDTO SnowDDLDTO);

    default SnowDDL fromId(Long id) {
        if (id == null) {
            return null;
        }
        SnowDDL SnowDDL = new SnowDDL();
        SnowDDL.setId(id);
        return SnowDDL;
    }
}
