package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.service.DeltaProcessJobStatusService;
import com.canny.snowflakemigration.web.rest.errors.BadRequestAlertException;
import com.canny.snowflakemigration.service.dto.DeltaProcessJobStatusDTO;
import com.canny.snowflakemigration.service.dto.DeltaProcessJobStatusCriteria;
import com.canny.snowflakemigration.service.DeltaProcessJobStatusQueryService;
import com.canny.snowflakemigration.domain.DeltaProcessJobStatus;

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
public class DeltaProcessJobStatusResource {

    private final Logger log = LoggerFactory.getLogger(DeltaProcessJobStatusResource.class);

    private static final String ENTITY_NAME = "deltaProcessJobStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeltaProcessJobStatusService deltaProcessJobStatusService;

    private final DeltaProcessJobStatusQueryService deltaProcessJobStatusQueryService;

    public DeltaProcessJobStatusResource(DeltaProcessJobStatusService deltaProcessJobStatusService,DeltaProcessJobStatusQueryService deltaProcessJobStatusQueryService) {
        this.deltaProcessJobStatusService = deltaProcessJobStatusService;
        this.deltaProcessJobStatusQueryService = deltaProcessJobStatusQueryService;
    }

    /**
     * {@code GET  /snow-ddl/Reports/:id} : get the "id" snowDDLJobStatus.
     *
     * @param id the id of the deltaProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the snowDDLJobStatusDTO, or with status {@code 404 (Not Found)}.
     */


    @GetMapping(value = "/delta-processes/jobStatus/{id}")
    public ResponseEntity<List<DeltaProcessJobStatus>> Reports(@PathVariable Long id)throws SQLException,ClassNotFoundException  {
        log.debug("REST request to get DeltaProcessJobStatus : {}", id);
        List<DeltaProcessJobStatus> deltaProcessJobStatus = deltaProcessJobStatusService.findAllByJobId(id);
        log.debug("REST request Done");
        return ResponseEntity.ok().body(deltaProcessJobStatus);
    }
}