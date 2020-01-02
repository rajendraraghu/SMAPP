package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.service.SnowDDLJobStatusService;
import com.canny.snowflakemigration.web.rest.errors.BadRequestAlertException;
import com.canny.snowflakemigration.service.dto.SnowDDLJobStatusDTO;
import com.canny.snowflakemigration.service.dto.SnowDDLJobStatusCriteria;
import com.canny.snowflakemigration.service.SnowDDLJobStatusQueryService;
import com.canny.snowflakemigration.domain.SnowDDLJobStatus;

import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.*;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.StreamSupport;
import java.util.ArrayList;
import java.util.Arrays;    
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//import org.json.JSONArray;
//import org.json.JSONObject;


/**
 * REST controller for managing {@link com.canny.snowflakemigration.domain.SnowDDLJobStatus}.
 */
@RestController
@RequestMapping("/api")
public class SnowDDLJobStatusResource {

    private final Logger log = LoggerFactory.getLogger(SnowDDLJobStatusResource.class);

    private static final String ENTITY_NAME = "snowDDLJobStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SnowDDLJobStatusService snowDDLJobStatusService;

    private final SnowDDLJobStatusQueryService snowDDLJobStatusQueryService;

    public SnowDDLJobStatusResource(SnowDDLJobStatusService snowDDLJobStatusService, SnowDDLJobStatusQueryService snowDDLJobStatusQueryService) {
        this.snowDDLJobStatusService = snowDDLJobStatusService;
        this.snowDDLJobStatusQueryService = snowDDLJobStatusQueryService;
    }

    /**
     * {@code GET  /snow-ddl/Reports/:id} : get the "id" snowDDLJobStatus.
     *
     * @param id the id of the migrationProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the snowDDLJobStatusDTO, or with status {@code 404 (Not Found)}.
     */

    
    @GetMapping(value = "/snow-ddl/jobStatus/{id}")
    public ResponseEntity<List<SnowDDLJobStatus>> Reports(@PathVariable Long id)throws SQLException,ClassNotFoundException  {      
        log.debug("REST request to get SnowDDLJobStatus : {}", id);
        List<SnowDDLJobStatus> snowDDLJobStatus = snowDDLJobStatusService.findAllByBatchId(id);
        log.debug("REST request Done");
        return ResponseEntity.ok().body(snowDDLJobStatus);
    }
}