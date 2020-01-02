package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.service.SnowParseJobStatusService;
import com.canny.snowflakemigration.web.rest.errors.BadRequestAlertException;
import com.canny.snowflakemigration.service.dto.SnowParseJobStatusDTO;
import com.canny.snowflakemigration.service.dto.SnowParseJobStatusCriteria;
import com.canny.snowflakemigration.service.SnowParseJobStatusQueryService;
import com.canny.snowflakemigration.domain.SnowParseJobStatus;

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
 * REST controller for managing {@link com.canny.snowflakemigration.domain.SnowParseJobStatus}.
 */
@RestController
@RequestMapping("/api")
public class SnowParseJobStatusResource {

    private final Logger log = LoggerFactory.getLogger(SnowParseJobStatusResource.class);

    private static final String ENTITY_NAME = "SnowParseJobStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SnowParseJobStatusService SnowParseJobStatusService;

    private final SnowParseJobStatusQueryService SnowParseJobStatusQueryService;

    public SnowParseJobStatusResource(SnowParseJobStatusService SnowParseJobStatusService, SnowParseJobStatusQueryService SnowParseJobStatusQueryService) {
        this.SnowParseJobStatusService = SnowParseJobStatusService;
        this.SnowParseJobStatusQueryService = SnowParseJobStatusQueryService;
    }

    /**
     * {@code GET  /snow-ddl/Reports/:id} : get the "id" SnowParseJobStatus.
     *
     * @param id the id of the migrationProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SnowParseJobStatusDTO, or with status {@code 404 (Not Found)}.
     */

    
    @GetMapping(value = "/snow-parse/jobStatus/{id}")
    public ResponseEntity<List<SnowParseJobStatus>> Reports(@PathVariable Long id)throws SQLException,ClassNotFoundException  {      
        log.debug("REST request to get SnowParseJobStatus : {}", id);
        List<SnowParseJobStatus> SnowParseJobStatus = SnowParseJobStatusService.findAllByBatchId(id);
        log.debug("REST request Done");
        return ResponseEntity.ok().body(SnowParseJobStatus);
    }
}