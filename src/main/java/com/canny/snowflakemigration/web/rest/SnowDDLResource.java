package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.service.SnowDDLService;
import com.canny.snowflakemigration.web.rest.errors.BadRequestAlertException;
import com.canny.snowflakemigration.service.dto.SnowDDLDTO;
import com.canny.snowflakemigration.service.dto.SnowDDLProcessStatusDTO;
import com.canny.snowflakemigration.service.dto.SnowDDLCriteria;
// import com.canny.snowflakemigration.domain.DDLConversionProcessor;
import com.canny.snowflakemigration.service.SnowDDLQueryService;
import com.canny.snowflakemigration.service.SnowDDLJobStatusService;
import com.canny.snowflakemigration.service.SnowDDLProcessStatusService;

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

import static com.canny.snowflakemigration.service.util.listTables.listTable;
import static com.canny.snowflakemigration.service.util.ConvertDDL.convertToSnowDDL;


import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//import org.json.JSONArray;
//import org.json.JSONObject;


/**
 * REST controller for managing {@link com.canny.snowflakemigration.domain.SnowDDL}.
 */
@RestController
@RequestMapping("/api")
public class SnowDDLResource {

    private final Logger log = LoggerFactory.getLogger(SnowDDLResource.class);

    private static final String ENTITY_NAME = "snowDDL";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SnowDDLService SnowDDLService;

    private final SnowDDLQueryService SnowDDLQueryService;

    private final SnowDDLProcessStatusService snowDDLProcessStatusService;

    private final SnowDDLJobStatusService snowDDLJobStatusService;

    public SnowDDLResource(SnowDDLService SnowDDLService, SnowDDLQueryService SnowDDLQueryService,SnowDDLProcessStatusService snowDDLProcessStatusService,SnowDDLJobStatusService snowDDLJobStatusService) {
        this.SnowDDLService = SnowDDLService;
        this.SnowDDLQueryService = SnowDDLQueryService;
        this.snowDDLProcessStatusService = snowDDLProcessStatusService;
        this.snowDDLJobStatusService = snowDDLJobStatusService;
    }

    /**
     * {@code POST  /snow-ddl} : Create a new SnowDDL.
     *
     * @param SnowDDLDTO the SnowDDLDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new SnowDDLDTO, or with status {@code 400 (Bad Request)} if the SnowDDL has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/snow-ddl")
    public ResponseEntity<SnowDDLDTO> createSnowDDL(@Valid @RequestBody SnowDDLDTO SnowDDLDTO) throws URISyntaxException {
        log.debug("REST request to save SnowDDL : {}", SnowDDLDTO);
        if (SnowDDLDTO.getId() != null) {
            throw new BadRequestAlertException("A new SnowDDL cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SnowDDLDTO result = SnowDDLService.save(SnowDDLDTO);
        return ResponseEntity.created(new URI("/api/snow-ddl/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /snow-ddl} : Updates an existing SnowDDL.
     *
     * @param SnowDDLDTO the SnowDDLDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated SnowDDLDTO,
     * or with status {@code 400 (Bad Request)} if the SnowDDLDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the SnowDDLDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/snow-ddl")
    public ResponseEntity<SnowDDLDTO> updateSnowDDL(@Valid @RequestBody SnowDDLDTO SnowDDLDTO) throws URISyntaxException {
        log.debug("REST request to update SnowDDL : {}", SnowDDLDTO);
        if (SnowDDLDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SnowDDLDTO result = SnowDDLService.save(SnowDDLDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, SnowDDLDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /snow-ddl} : get all the SnowDDLes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of SnowDDLes in body.
     */
    @GetMapping("/snow-ddl")
    public ResponseEntity<List<SnowDDLDTO>> getAllSnowDDLes(SnowDDLCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get SnowDDLes by criteria: {}", criteria);
        Page<SnowDDLDTO> page = SnowDDLQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /snow-ddl/count} : count all the SnowDDLes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/snow-ddl/count")
    public ResponseEntity<Long> countSnowDDLes(SnowDDLCriteria criteria) {
        log.debug("REST request to count SnowDDLes by criteria: {}", criteria);
        return ResponseEntity.ok().body(SnowDDLQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /snow-ddl/:id} : get the "id" SnowDDL.
     *
     * @param id the id of the SnowDDLDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SnowDDLDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/snow-ddl/{id}")
    public ResponseEntity<SnowDDLDTO> getSnowDDL(@PathVariable Long id) {
        log.debug("REST request to get SnowDDL : {}", id);
        Optional<SnowDDLDTO> SnowDDLDTO = SnowDDLService.findOne(id);
        return ResponseUtil.wrapOrNotFound(SnowDDLDTO);
    }

    /**
     * {@code DELETE  /snow-ddl/:id} : delete the "id" SnowDDL.
     *
     * @param id the id of the SnowDDLDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/snow-ddl/{id}")
    public ResponseEntity<Void> deleteSnowDDL(@PathVariable Long id) {
        log.debug("REST request to delete SnowDDL : {}", id);
        SnowDDLService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    @PostMapping(value = "/snow-ddl/sendDDLtoConvert")
    public @ResponseBody String sendDDL(@Valid @RequestBody  SnowDDLDTO snowDDLDTO)throws IOException  {
        //Optional<SnowDDLDTO>  SnowDDLDTO = SnowDDLService.findOne(processid);
        // DDLConversionProcessor processor = new DDLConversionProcessor();
    	String result = convertToSnowDDL(snowDDLDTO,snowDDLProcessStatusService,snowDDLJobStatusService);    
        return result;
    }
}