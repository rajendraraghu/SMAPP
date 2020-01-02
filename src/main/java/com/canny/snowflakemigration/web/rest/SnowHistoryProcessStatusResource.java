package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.service.SnowHistoryProcessStatusService;
import com.canny.snowflakemigration.web.rest.errors.BadRequestAlertException;
import com.canny.snowflakemigration.service.dto.SnowHistoryProcessStatusDTO;
import com.canny.snowflakemigration.service.dto.SnowHistoryProcessStatusCriteria;
import com.canny.snowflakemigration.service.SnowHistoryProcessStatusQueryService;
import com.canny.snowflakemigration.domain.SnowHistoryProcessStatus;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
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
 * REST controller for managing {@link com.canny.snowflakemigration.domain.SnowHistoryProcessStatus}.
 */
@RestController
@RequestMapping("/api")
public class SnowHistoryProcessStatusResource {

    private final Logger log = LoggerFactory.getLogger(SnowHistoryProcessStatusResource.class);

    private static final String ENTITY_NAME = "snowHistoryProcessStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SnowHistoryProcessStatusService snowHistoryProcessStatusService;

    private final SnowHistoryProcessStatusQueryService snowHistoryProcessStatusQueryService;

    public SnowHistoryProcessStatusResource(SnowHistoryProcessStatusService snowHistoryProcessStatusService, SnowHistoryProcessStatusQueryService snowHistoryProcessStatusQueryService) {
        this.snowHistoryProcessStatusService = snowHistoryProcessStatusService;
        this.snowHistoryProcessStatusQueryService = snowHistoryProcessStatusQueryService;
    }

    /**
     * {@code GET  /snow-history/Reports/:id} : get the "id" snowHistoryProcessStatus.
     *
     * @param id the id of the migrationProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the snowHistoryProcessStatusDTO, or with status {@code 404 (Not Found)}.
     */

    
    @GetMapping(value = "/snow-histories/Reports/{id}")
    public ResponseEntity<List<SnowHistoryProcessStatus>> Reports(@PathVariable Long id)throws SQLException,ClassNotFoundException  {      
        log.debug("REST request to get SnowHistoryProcessStatus : {}", id);
        List<SnowHistoryProcessStatus> snowHistoryProcessStatus = snowHistoryProcessStatusService.findAllByProcessId(id);
        log.debug("REST request Done");
        return ResponseEntity.ok().body(snowHistoryProcessStatus);
    }
}