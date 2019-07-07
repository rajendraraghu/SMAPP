package com.canny.snowflakemigration.service.mapper;

import com.canny.snowflakemigration.domain.*;
import com.canny.snowflakemigration.service.dto.SnowflakeConnectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SnowflakeConnection} and its DTO {@link SnowflakeConnectionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SnowflakeConnectionMapper extends EntityMapper<SnowflakeConnectionDTO, SnowflakeConnection> {



    default SnowflakeConnection fromId(Long id) {
        if (id == null) {
            return null;
        }
        SnowflakeConnection snowflakeConnection = new SnowflakeConnection();
        snowflakeConnection.setId(id);
        return snowflakeConnection;
    }
}
