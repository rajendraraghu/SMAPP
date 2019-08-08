package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.service.SnowDDLService;
import com.canny.snowflakemigration.web.rest.errors.BadRequestAlertException;
import com.canny.snowflakemigration.service.dto.SnowDDLDTO;
import com.canny.snowflakemigration.service.dto.SnowDDLProcessStatusDTO;
import com.canny.snowflakemigration.service.dto.SnowDDLCriteria;
import com.canny.snowflakemigration.service.SnowDDLQueryService;

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

import static com.canny.snowflakemigration.service.util.listTables.listTable;
import static com.canny.snowflakemigration.service.util.convertDDL.convertToSnowDDL;


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

    public SnowDDLResource(SnowDDLService SnowDDLService, SnowDDLQueryService SnowDDLQueryService) {
        this.SnowDDLService = SnowDDLService;
        this.SnowDDLQueryService = SnowDDLQueryService;
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
    public @ResponseBody String sendDDL(@Valid @RequestBody  SnowDDLDTO SnowDDLDTO)throws IOException  {
    	//Optional<SnowDDLDTO>  SnowDDLDTO = SnowDDLService.findOne(processid);
    	String result = convertToSnowDDL(SnowDDLDTO);    
        return result;
    }
  /*  @PostMapping(value="/snow-ddl/TestConnection")
    public @ResponseBody boolean TestingConnection(ConnectionDTO connectionDTO)throws SQLException,ClassNotFoundException  {
        Connection con1 = DriverManager.getConnection(connectionDTO.getUrl(), connectionDTO.getUsername(), connectionDTO.getPassword());
        boolean result = con1.isValid(10);
        return result;
    }*/
    
    // @GetMapping(value = "/snow-ddl/Reports/{id}")
    // public ResponseEntity<String> Reports(@PathVariable Long id)throws SQLException,ClassNotFoundException  {      
    //     log.debug("REST request to get SnowDDLProcessStatus : {}", id);
    //     Optional<SnowDDLProcessStatusDTO> SnowDDLProcessStatusDTO = SnowDDLService.findOne(id);
    //     return ResponseEntity.ok();
    // }

     @PostMapping(value = "/snow-ddl/ReportsPerJob")
    public @ResponseBody String ReportsPerJob(@Valid @RequestBody SnowDDLDTO SnowDDLDTO)throws SQLException,ClassNotFoundException  {
        Properties properties = new Properties();
		properties.put("user", SnowDDLDTO.getSnowflakeConnectionUsername());
		properties.put("password", SnowDDLDTO.getSnowflakeConnectionPassword());
		properties.put("account", SnowDDLDTO.getSnowflakeConnectionAcct());
        properties.put("warehouse",SnowDDLDTO.getSnowflakeConnectionWarehouse());
		properties.put("db",SnowDDLDTO.getSnowflakeConnectionDatabase());
	    properties.put("schema",SnowDDLDTO.getSnowflakeConnectionSchema());
		Connection con2=DriverManager.getConnection(SnowDDLDTO.getSnowflakeConnectionUrl(),properties);
        Statement stmt0=con2.createStatement(); 
        ResultSet rs0 = stmt0.executeQuery("SELECT MAX(jobid) FROM jobRunStatus");
        rs0.next();
        System.out.println("SELECT * FROM tableLoadStatus WHERE processid ="+SnowDDLDTO.getId() +" AND jobid ="+rs0.getInt(1)+" order by tableloadstarttime desc");
        ResultSet rs1 = stmt0.executeQuery("SELECT * FROM tableLoadStatus WHERE processid ="+SnowDDLDTO.getId() +" AND jobid ="+rs0.getInt(1)+" order by tableloadstarttime desc");
        
        JsonObject jsonResponse = new JsonObject();	
		JsonArray data = new JsonArray();
		while(rs1.next() ) 
		{
			System.out.println("Inside while loop:"+rs1.getString(3));
		JsonArray row = new JsonArray();
		row.add(new JsonPrimitive(rs1.getInt(1)));
		row.add(new JsonPrimitive(rs1.getInt(2)));
		row.add(new JsonPrimitive(rs1.getString(3)==null?"":rs1.getString(3)));
		row.add(new JsonPrimitive(rs1.getString(4)==null?"":rs1.getString(4)));
		row.add(new JsonPrimitive(rs1.getString(5)==null?"":rs1.getString(5)));
		row.add(new JsonPrimitive(rs1.getString(6)==null?"":rs1.getString(6)));
		row.add(new JsonPrimitive(rs1.getInt(7)));
		row.add(new JsonPrimitive(rs1.getInt(8)));
		row.add(new JsonPrimitive(rs1.getInt(9)));
		//row.add(new JsonPrimitive(rs0.getString(10)));
		row.add(new JsonPrimitive(rs1.getString(11)==null?"":rs1.getString(11)));
		row.add(new JsonPrimitive(rs1.getString(12)==null?"":rs1.getString(12)));
		row.add(new JsonPrimitive(rs1.getString(13)==null?"":rs1.getString(13)));
		row.add(new JsonPrimitive(rs1.getString(14)==null?"":rs1.getString(14)));
		
        data.add(row);
        }
        jsonResponse.add("audit_data", data);
        return jsonResponse.toString();
    }
     @PostMapping(value = "/snow-ddl/retrieveColumnList")
     public @ResponseBody String[] listColumns(@Valid @RequestBody SnowDDLDTO SnowDDLDTO, String tableName) throws SQLException,ClassNotFoundException {
    	 Properties properties0 = new Properties();
 		properties0.put("user", SnowDDLDTO.getSourceConnectionUsername());
 		properties0.put("password", SnowDDLDTO.getSourceConnectionPassword());
 		properties0.put("db",SnowDDLDTO.getSourceConnectionDatabase());
 	    properties0.put("schema",SnowDDLDTO.getSourceConnectionSchema());	
 	    Connection con = DriverManager.getConnection(SnowDDLDTO.getSourceConnectionUrl(),properties0);
         Statement stmt = con.createStatement();
         ResultSet rs1 = stmt.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME ='"+tableName+"' AND TABLE_SCHEMA = '"+SnowDDLDTO.getSourceConnectionSchema()+"';");
         ArrayList tn = new ArrayList();
         while(rs1.next())
         {
             tn.add(rs1.getString("COLUMN_NAME"));
         }
         String[] colNames = (String[])tn.toArray(new String[tn.size()]);
    	 return colNames;
     }
}