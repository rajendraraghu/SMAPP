package com.canny.snowflakemigration.service.mapper;

import com.canny.snowflakemigration.domain.*;
import com.canny.snowflakemigration.service.dto.SourceConnectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SourceConnection} and its DTO {@link SourceConnectionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SourceConnectionMapper extends EntityMapper<SourceConnectionDTO, SourceConnection> {



    default SourceConnection fromId(Long id) {
        if (id == null) {
            return null;
        }
        SourceConnection sourceConnection = new SourceConnection();
        sourceConnection.setId(id);
        return sourceConnection;
    }
}
