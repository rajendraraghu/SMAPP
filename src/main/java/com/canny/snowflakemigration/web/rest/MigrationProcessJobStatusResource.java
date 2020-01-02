package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.service.MigrationProcessJobStatusService;
import com.canny.snowflakemigration.web.rest.errors.BadRequestAlertException;
import com.canny.snowflakemigration.service.dto.MigrationProcessJobStatusDTO;
import com.canny.snowflakemigration.service.dto.MigrationProcessJobStatusCriteria;
import com.canny.snowflakemigration.service.MigrationProcessJobStatusQueryService;
import com.canny.snowflakemigration.domain.MigrationProcessJobStatus;

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
public class MigrationProcessJobStatusResource {

    private final Logger log = LoggerFactory.getLogger(MigrationProcessJobStatusResource.class);

    private static final String ENTITY_NAME = "migrationProcessJobStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MigrationProcessJobStatusService migrationProcessJobStatusService;

    private final MigrationProcessJobStatusQueryService migrationProcessJobStatusQueryService;

    public MigrationProcessJobStatusResource(MigrationProcessJobStatusService migrationProcessJobStatusService,MigrationProcessJobStatusQueryService migrationProcessJobStatusQueryService) {
        this.migrationProcessJobStatusService = migrationProcessJobStatusService;
        this.migrationProcessJobStatusQueryService = migrationProcessJobStatusQueryService;
    }

    /**
     * {@code GET  /snow-ddl/Reports/:id} : get the "id" snowDDLJobStatus.
     *
     * @param id the id of the migrationProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the snowDDLJobStatusDTO, or with status {@code 404 (Not Found)}.
     */

    
    @GetMapping(value = "/migration-processes/jobStatus/{id}")
    public ResponseEntity<List<MigrationProcessJobStatus>> Reports(@PathVariable Long id)throws SQLException,ClassNotFoundException  {      
        log.debug("REST request to get MigrationProcessJobStatus : {}", id);
        List<MigrationProcessJobStatus> migrationProcessJobStatus = migrationProcessJobStatusService.findAllByJobId(id);
        log.debug("REST request Done");
        return ResponseEntity.ok().body(migrationProcessJobStatus);
    }
}