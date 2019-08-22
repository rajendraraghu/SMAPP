package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.service.SnowHistoryJobStatusService;
import com.canny.snowflakemigration.web.rest.errors.BadRequestAlertException;
import com.canny.snowflakemigration.service.dto.SnowHistoryJobStatusDTO;
import com.canny.snowflakemigration.service.dto.SnowHistoryJobStatusCriteria;
import com.canny.snowflakemigration.service.SnowHistoryJobStatusQueryService;
import com.canny.snowflakemigration.domain.SnowHistoryJobStatus;

import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.io.*;

import java.util.List;

import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//import org.json.JSONArray;
//import org.json.JSONObject;


/**
 * REST controller for managing {@link com.canny.snowflakemigration.domain.SnowHistoryJobStatus}.
 */
@RestController
@RequestMapping("/api")
public class SnowHistoryJobStatusResource {

    private final Logger log = LoggerFactory.getLogger(SnowHistoryJobStatusResource.class);

    private static final String ENTITY_NAME = "SnowHistoryJobStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SnowHistoryJobStatusService snowHistoryJobStatusService;

    private final SnowHistoryJobStatusQueryService snowHistoryJobStatusQueryService;

    public SnowHistoryJobStatusResource(SnowHistoryJobStatusService snowHistoryJobStatusService, SnowHistoryJobStatusQueryService snowHistoryJobStatusQueryService) {
        this.snowHistoryJobStatusService = snowHistoryJobStatusService;
        this.snowHistoryJobStatusQueryService = snowHistoryJobStatusQueryService;
    }

    /**
     * {@code GET  /snow-ddl/Reports/:id} : get the "id" snowHistoryJobStatus.
     *
     * @param id the id of the migrationProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the snowHistoryJobStatusDTO, or with status {@code 404 (Not Found)}.
     */

    
    @GetMapping(value = "/snow-histories/jobStatus/{id}")
    public ResponseEntity<List<SnowHistoryJobStatus>> Reports(@PathVariable Long id)throws SQLException,ClassNotFoundException  {      
        log.debug("REST request to get SnowHistoryJobStatus : {}", id);
        List<SnowHistoryJobStatus> snowHistoryJobStatus = snowHistoryJobStatusService.findAllByBatchId(id);
        log.debug("REST request Done");
        return ResponseEntity.ok().body(snowHistoryJobStatus);
    }
}