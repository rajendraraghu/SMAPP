package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.service.SnowflakeConnectionService;
import com.canny.snowflakemigration.web.rest.errors.BadRequestAlertException;
import com.canny.snowflakemigration.service.dto.SnowflakeConnectionDTO;
import com.canny.snowflakemigration.service.dto.SnowflakeConnectionCriteria;
import com.canny.snowflakemigration.service.SnowflakeConnectionQueryService;
import static com.canny.snowflakemigration.service.util.PasswordProtector.encrypt;
import static com.canny.snowflakemigration.service.util.PasswordProtector.decrypt;
import static com.canny.snowflakemigration.service.util.TestConnection.testConnectionDest;

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
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.canny.snowflakemigration.domain.SnowflakeConnection}.
 */
@RestController
@RequestMapping("/api")
public class SnowflakeConnectionResource {

    private final Logger log = LoggerFactory.getLogger(SnowflakeConnectionResource.class);

    private static final String ENTITY_NAME = "snowflakeConnection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SnowflakeConnectionService snowflakeConnectionService;

    private final SnowflakeConnectionQueryService snowflakeConnectionQueryService;

    public SnowflakeConnectionResource(SnowflakeConnectionService snowflakeConnectionService, SnowflakeConnectionQueryService snowflakeConnectionQueryService) {
        this.snowflakeConnectionService = snowflakeConnectionService;
        this.snowflakeConnectionQueryService = snowflakeConnectionQueryService;
    }

    /**
     * {@code POST  /snowflake-connections} : Create a new snowflakeConnection.
     *
     * @param snowflakeConnectionDTO the snowflakeConnectionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new snowflakeConnectionDTO, or with status {@code 400 (Bad Request)} if the snowflakeConnection has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/snowflake-connections")
    public ResponseEntity<SnowflakeConnectionDTO> createSnowflakeConnection(@Valid @RequestBody SnowflakeConnectionDTO snowflakeConnectionDTO) throws URISyntaxException {
        log.debug("REST request to save SnowflakeConnection : {}", snowflakeConnectionDTO);
        if (snowflakeConnectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new snowflakeConnection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        snowflakeConnectionDTO.setPassword(encrypt(snowflakeConnectionDTO.getPassword()));
        SnowflakeConnectionDTO result = snowflakeConnectionService.save(snowflakeConnectionDTO);
        return ResponseEntity.created(new URI("/api/snowflake-connections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /snowflake-connections} : Updates an existing snowflakeConnection.
     *
     * @param snowflakeConnectionDTO the snowflakeConnectionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated snowflakeConnectionDTO,
     * or with status {@code 400 (Bad Request)} if the snowflakeConnectionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the snowflakeConnectionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/snowflake-connections")
    public ResponseEntity<SnowflakeConnectionDTO> updateSnowflakeConnection(@Valid @RequestBody SnowflakeConnectionDTO snowflakeConnectionDTO) throws URISyntaxException {
        log.debug("REST request to update SnowflakeConnection : {}", snowflakeConnectionDTO);
        if (snowflakeConnectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!(snowflakeConnectionDTO.getPassword().equals(snowflakeConnectionService.findOne(snowflakeConnectionDTO.getId()).get().getPassword()))){
            snowflakeConnectionDTO.setPassword(encrypt(snowflakeConnectionDTO.getPassword()));
        }
        SnowflakeConnectionDTO result = snowflakeConnectionService.save(snowflakeConnectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, snowflakeConnectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /snowflake-connections} : get all the snowflakeConnections.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of snowflakeConnections in body.
     */
    @GetMapping("/snowflake-connections")
    public ResponseEntity<List<SnowflakeConnectionDTO>> getAllSnowflakeConnections(SnowflakeConnectionCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get SnowflakeConnections by criteria: {}", criteria);
        Page<SnowflakeConnectionDTO> page = snowflakeConnectionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /snowflake-connections/count} : count all the snowflakeConnections.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/snowflake-connections/count")
    public ResponseEntity<Long> countSnowflakeConnections(SnowflakeConnectionCriteria criteria) {
        log.debug("REST request to count SnowflakeConnections by criteria: {}", criteria);
        return ResponseEntity.ok().body(snowflakeConnectionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /snowflake-connections/:id} : get the "id" snowflakeConnection.
     *
     * @param id the id of the snowflakeConnectionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the snowflakeConnectionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/snowflake-connections/{id}")
    public ResponseEntity<SnowflakeConnectionDTO> getSnowflakeConnection(@PathVariable Long id) {
        log.debug("REST request to get SnowflakeConnection : {}", id);
        Optional<SnowflakeConnectionDTO> snowflakeConnectionDTO = snowflakeConnectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(snowflakeConnectionDTO);
    }

    /**
     * {@code DELETE  /snowflake-connections/:id} : delete the "id" snowflakeConnection.
     *
     * @param id the id of the snowflakeConnectionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/snowflake-connections/{id}")
    public ResponseEntity<Void> deleteSnowflakeConnection(@PathVariable Long id) {
        log.debug("REST request to delete SnowflakeConnection : {}", id);
        snowflakeConnectionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping(value = "/snowflake-connections/TestConnection")
    public @ResponseBody boolean TestingConnection(@RequestBody SnowflakeConnectionDTO connectionDTO)
            throws SQLException, ClassNotFoundException {
        boolean result;
        if (connectionDTO.getId() != null) {
            if (connectionDTO.getPassword()
                    .equals(snowflakeConnectionService.findOne(connectionDTO.getId()).get().getPassword())) {
                connectionDTO.setPassword(decrypt(connectionDTO.getPassword()));
                result = testConnectionDest(connectionDTO);
            } else {
                result = testConnectionDest(connectionDTO);
            }
        } else {
            result = testConnectionDest(connectionDTO);
        }
        return result;

    }
}
