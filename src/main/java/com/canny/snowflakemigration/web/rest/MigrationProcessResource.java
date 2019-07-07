package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.service.MigrationProcessService;
import com.canny.snowflakemigration.web.rest.errors.BadRequestAlertException;
import com.canny.snowflakemigration.service.dto.MigrationProcessDTO;
import com.canny.snowflakemigration.service.dto.MigrationProcessCriteria;
import com.canny.snowflakemigration.service.MigrationProcessQueryService;

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

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.canny.snowflakemigration.domain.MigrationProcess}.
 */
@RestController
@RequestMapping("/api")
public class MigrationProcessResource {

    private final Logger log = LoggerFactory.getLogger(MigrationProcessResource.class);

    private static final String ENTITY_NAME = "migrationProcess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MigrationProcessService migrationProcessService;

    private final MigrationProcessQueryService migrationProcessQueryService;

    public MigrationProcessResource(MigrationProcessService migrationProcessService, MigrationProcessQueryService migrationProcessQueryService) {
        this.migrationProcessService = migrationProcessService;
        this.migrationProcessQueryService = migrationProcessQueryService;
    }

    /**
     * {@code POST  /migration-processes} : Create a new migrationProcess.
     *
     * @param migrationProcessDTO the migrationProcessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new migrationProcessDTO, or with status {@code 400 (Bad Request)} if the migrationProcess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/migration-processes")
    public ResponseEntity<MigrationProcessDTO> createMigrationProcess(@Valid @RequestBody MigrationProcessDTO migrationProcessDTO) throws URISyntaxException {
        log.debug("REST request to save MigrationProcess : {}", migrationProcessDTO);
        if (migrationProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new migrationProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MigrationProcessDTO result = migrationProcessService.save(migrationProcessDTO);
        return ResponseEntity.created(new URI("/api/migration-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /migration-processes} : Updates an existing migrationProcess.
     *
     * @param migrationProcessDTO the migrationProcessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated migrationProcessDTO,
     * or with status {@code 400 (Bad Request)} if the migrationProcessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the migrationProcessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/migration-processes")
    public ResponseEntity<MigrationProcessDTO> updateMigrationProcess(@Valid @RequestBody MigrationProcessDTO migrationProcessDTO) throws URISyntaxException {
        log.debug("REST request to update MigrationProcess : {}", migrationProcessDTO);
        if (migrationProcessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MigrationProcessDTO result = migrationProcessService.save(migrationProcessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, migrationProcessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /migration-processes} : get all the migrationProcesses.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of migrationProcesses in body.
     */
    @GetMapping("/migration-processes")
    public ResponseEntity<List<MigrationProcessDTO>> getAllMigrationProcesses(MigrationProcessCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get MigrationProcesses by criteria: {}", criteria);
        Page<MigrationProcessDTO> page = migrationProcessQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /migration-processes/count} : count all the migrationProcesses.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/migration-processes/count")
    public ResponseEntity<Long> countMigrationProcesses(MigrationProcessCriteria criteria) {
        log.debug("REST request to count MigrationProcesses by criteria: {}", criteria);
        return ResponseEntity.ok().body(migrationProcessQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /migration-processes/:id} : get the "id" migrationProcess.
     *
     * @param id the id of the migrationProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the migrationProcessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/migration-processes/{id}")
    public ResponseEntity<MigrationProcessDTO> getMigrationProcess(@PathVariable Long id) {
        log.debug("REST request to get MigrationProcess : {}", id);
        Optional<MigrationProcessDTO> migrationProcessDTO = migrationProcessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(migrationProcessDTO);
    }

    /**
     * {@code DELETE  /migration-processes/:id} : delete the "id" migrationProcess.
     *
     * @param id the id of the migrationProcessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/migration-processes/{id}")
    public ResponseEntity<Void> deleteMigrationProcess(@PathVariable Long id) {
        log.debug("REST request to delete MigrationProcess : {}", id);
        migrationProcessService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
