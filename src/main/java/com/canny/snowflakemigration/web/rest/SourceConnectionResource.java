package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.service.SourceConnectionService;
import com.canny.snowflakemigration.web.rest.errors.BadRequestAlertException;
import com.canny.snowflakemigration.service.dto.SourceConnectionDTO;
import com.canny.snowflakemigration.service.dto.SourceConnectionCriteria;
import com.canny.snowflakemigration.service.SourceConnectionQueryService;

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
 * REST controller for managing {@link com.canny.snowflakemigration.domain.SourceConnection}.
 */
@RestController
@RequestMapping("/api")
public class SourceConnectionResource {

    private final Logger log = LoggerFactory.getLogger(SourceConnectionResource.class);

    private static final String ENTITY_NAME = "sourceConnection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SourceConnectionService sourceConnectionService;

    private final SourceConnectionQueryService sourceConnectionQueryService;

    public SourceConnectionResource(SourceConnectionService sourceConnectionService, SourceConnectionQueryService sourceConnectionQueryService) {
        this.sourceConnectionService = sourceConnectionService;
        this.sourceConnectionQueryService = sourceConnectionQueryService;
    }

    /**
     * {@code POST  /source-connections} : Create a new sourceConnection.
     *
     * @param sourceConnectionDTO the sourceConnectionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sourceConnectionDTO, or with status {@code 400 (Bad Request)} if the sourceConnection has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/source-connections")
    public ResponseEntity<SourceConnectionDTO> createSourceConnection(@Valid @RequestBody SourceConnectionDTO sourceConnectionDTO) throws URISyntaxException {
        log.debug("REST request to save SourceConnection : {}", sourceConnectionDTO);
        if (sourceConnectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new sourceConnection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SourceConnectionDTO result = sourceConnectionService.save(sourceConnectionDTO);
        return ResponseEntity.created(new URI("/api/source-connections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /source-connections} : Updates an existing sourceConnection.
     *
     * @param sourceConnectionDTO the sourceConnectionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sourceConnectionDTO,
     * or with status {@code 400 (Bad Request)} if the sourceConnectionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sourceConnectionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/source-connections")
    public ResponseEntity<SourceConnectionDTO> updateSourceConnection(@Valid @RequestBody SourceConnectionDTO sourceConnectionDTO) throws URISyntaxException {
        log.debug("REST request to update SourceConnection : {}", sourceConnectionDTO);
        if (sourceConnectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SourceConnectionDTO result = sourceConnectionService.save(sourceConnectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sourceConnectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /source-connections} : get all the sourceConnections.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sourceConnections in body.
     */
    @GetMapping("/source-connections")
    public ResponseEntity<List<SourceConnectionDTO>> getAllSourceConnections(SourceConnectionCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get SourceConnections by criteria: {}", criteria);
        Page<SourceConnectionDTO> page = sourceConnectionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /source-connections/count} : count all the sourceConnections.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/source-connections/count")
    public ResponseEntity<Long> countSourceConnections(SourceConnectionCriteria criteria) {
        log.debug("REST request to count SourceConnections by criteria: {}", criteria);
        return ResponseEntity.ok().body(sourceConnectionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /source-connections/:id} : get the "id" sourceConnection.
     *
     * @param id the id of the sourceConnectionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sourceConnectionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/source-connections/{id}")
    public ResponseEntity<SourceConnectionDTO> getSourceConnection(@PathVariable Long id) {
        log.debug("REST request to get SourceConnection : {}", id);
        Optional<SourceConnectionDTO> sourceConnectionDTO = sourceConnectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sourceConnectionDTO);
    }

    /**
     * {@code DELETE  /source-connections/:id} : delete the "id" sourceConnection.
     *
     * @param id the id of the sourceConnectionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/source-connections/{id}")
    public ResponseEntity<Void> deleteSourceConnection(@PathVariable Long id) {
        log.debug("REST request to delete SourceConnection : {}", id);
        sourceConnectionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
