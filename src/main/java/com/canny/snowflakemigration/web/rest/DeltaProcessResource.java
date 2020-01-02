package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.service.DeltaProcessService;
import com.canny.snowflakemigration.web.rest.errors.BadRequestAlertException;
import com.canny.snowflakemigration.service.dto.DeltaProcessDTO;
import com.canny.snowflakemigration.service.dto.SourceConnectionDTO;
import com.canny.snowflakemigration.service.dto.SnowflakeConnectionDTO;
import com.canny.snowflakemigration.service.dto.DeltaProcessCriteria;
import com.canny.snowflakemigration.service.DeltaProcessQueryService;
import com.canny.snowflakemigration.service.DeltaProcessJobStatusService;
import com.canny.snowflakemigration.service.DeltaProcessStatusService;

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
import java.util.Properties;
import java.util.stream.StreamSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.canny.snowflakemigration.service.util.DeltaListTables.listTable;
import static com.canny.snowflakemigration.service.util.DeltaSendTableList.sendSelectedTables;


import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//import org.json.JSONArray;
//import org.json.JSONObject;


/**
 * REST controller for managing {@link com.canny.snowflakemigration.domain.DeltaProcess}.
 */
@RestController
@RequestMapping("/api")
public class DeltaProcessResource {

    private final Logger log = LoggerFactory.getLogger(DeltaProcessResource.class);

    private static final String ENTITY_NAME = "deltaProcess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeltaProcessService deltaProcessService;

    private final DeltaProcessQueryService deltaProcessQueryService;
	
	private final DeltaProcessStatusService deltaProcessStatusService;

    private final DeltaProcessJobStatusService deltaProcessJobStatusService;

    public DeltaProcessResource(DeltaProcessService deltaProcessService, DeltaProcessQueryService deltaProcessQueryService,DeltaProcessStatusService deltaProcessStatusService,DeltaProcessJobStatusService deltaProcessJobStatusService) {
        this.deltaProcessService = deltaProcessService;
        this.deltaProcessQueryService = deltaProcessQueryService;
		this.deltaProcessStatusService = deltaProcessStatusService;
        this.deltaProcessJobStatusService = deltaProcessJobStatusService;
    }

    /**
     * {@code POST  /delta-processes} : Create a new deltaProcess.
     *
     * @param deltaProcessDTO the deltaProcessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deltaProcessDTO, or with status {@code 400 (Bad Request)} if the deltaProcess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delta-processes")
    public ResponseEntity<DeltaProcessDTO> createDeltaProcess(@Valid @RequestBody DeltaProcessDTO deltaProcessDTO) throws URISyntaxException {
        log.debug("REST request to save DeltaProcess : {}", deltaProcessDTO);
        if (deltaProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new deltaProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeltaProcessDTO result = deltaProcessService.save(deltaProcessDTO);
        return ResponseEntity.created(new URI("/api/delta-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delta-processes} : Updates an existing deltaProcess.
     *
     * @param deltaProcessDTO the deltaProcessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deltaProcessDTO,
     * or with status {@code 400 (Bad Request)} if the deltaProcessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deltaProcessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delta-processes")
    public ResponseEntity<DeltaProcessDTO> updateDeltaProcess(@Valid @RequestBody DeltaProcessDTO deltaProcessDTO) throws URISyntaxException {
        log.debug("REST request to update DeltaProcess : {}", deltaProcessDTO);
        if (deltaProcessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeltaProcessDTO result = deltaProcessService.save(deltaProcessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deltaProcessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /delta-processes} : get all the deltaProcesses.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deltaProcesses in body.
     */
    @GetMapping("/delta-processes")
    public ResponseEntity<List<DeltaProcessDTO>> getAllDeltaProcesses(DeltaProcessCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get DeltaProcesses by criteria: {}", criteria);
        Page<DeltaProcessDTO> page = deltaProcessQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /delta-processes/count} : count all the deltaProcesses.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/delta-processes/count")
    public ResponseEntity<Long> countDeltaProcesses(DeltaProcessCriteria criteria) {
        log.debug("REST request to count DeltaProcesses by criteria: {}", criteria);
        return ResponseEntity.ok().body(deltaProcessQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /delta-processes/:id} : get the "id" deltaProcess.
     *
     * @param id the id of the deltaProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deltaProcessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delta-processes/{id}")
    public ResponseEntity<DeltaProcessDTO> getDeltaProcess(@PathVariable Long id) {
        log.debug("REST request to get DeltaProcess : {}", id);
        Optional<DeltaProcessDTO> deltaProcessDTO = deltaProcessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deltaProcessDTO);
    }

    /**
     * {@code DELETE  /delta-processes/:id} : delete the "id" deltaProcess.
     *
     * @param id the id of the deltaProcessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delta-processes/{id}")
    public ResponseEntity<Void> deleteDeltaProcess(@PathVariable Long id) {
        log.debug("REST request to delete DeltaProcess : {}", id);
        deltaProcessService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping(value = "/delta-processes/retrieveTableList")
    public @ResponseBody String listTables(@Valid @RequestBody DeltaProcessDTO deltaProcessDTO) throws SQLException,ClassNotFoundException {
        String tableName = listTable(deltaProcessDTO);
        return tableName;
    } 

    @PostMapping(value = "/delta-processes/sendTableListforHistProcess")
    public @ResponseBody String sendTableList(@Valid @RequestBody  DeltaProcessDTO deltaProcessDTO)throws SQLException,ClassNotFoundException  {
    	//Optional<DeltaProcessDTO>  deltaProcessDTO = deltaProcessService.findOne(processid);
    	String result = sendSelectedTables(deltaProcessDTO,deltaProcessStatusService,deltaProcessJobStatusService);
        return result;
    } 
    @PostMapping(value="/delta-processes/TestConnectionSource")
    public @ResponseBody boolean TestingConnection(@RequestBody SourceConnectionDTO connectionDTO)throws SQLException,ClassNotFoundException  {
        boolean result = false;
		try {
    	Connection con1 = DriverManager.getConnection(connectionDTO.getUrl(), connectionDTO.getUsername(), connectionDTO.getPassword());
        result = con1.isValid(10);
        }
        catch(Exception e)
        {
        	result = false;
        }
        finally { return result;}
    }

    @PostMapping(value="/delta-processes/TestConnectionDest")
    public @ResponseBody boolean TestingConnection(@RequestBody SnowflakeConnectionDTO connectionDTO)throws SQLException,ClassNotFoundException  {
        boolean result  = false;
		try {
    	Connection con1 = DriverManager.getConnection(connectionDTO.getUrl(), connectionDTO.getUsername(), connectionDTO.getPassword());
        result = con1.isValid(10);
        }
        catch(Exception e)
        {
        	result = false;
        }
        finally { return result;}
    }

     @PostMapping(value = "/delta-processes/Reports")
    public @ResponseBody String Reports(@Valid @RequestBody DeltaProcessDTO deltaProcessDTO)throws SQLException,ClassNotFoundException  {
        /*Properties properties = new Properties();
		properties.put("user", deltaProcessDTO.getSnowflakeConnectionUsername());
		properties.put("password", deltaProcessDTO.getSnowflakeConnectionPassword());
		properties.put("account", deltaProcessDTO.getSnowflakeConnectionAcct());
        properties.put("warehouse",deltaProcessDTO.getSnowflakeConnectionWarehouse());
		properties.put("db",deltaProcessDTO.getSnowflakeConnectionDatabase());
	    properties.put("schema",deltaProcessDTO.getSnowflakeConnectionSchema());
		Connection con2=DriverManager.getConnection(deltaProcessDTO.getSnowflakeConnectionUrl(),properties);
        Statement stmt0=con2.createStatement(); */
        Connection con3 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/smapp","postgres","password");
	    Statement st0 = con3.createStatement();
        ResultSet rs0 = st0.executeQuery("SELECT * FROM sah_tableLoadStatus WHERE processid ="+deltaProcessDTO.getId() +" order by tableloadstarttime desc");

        JsonObject jsonResponse = new JsonObject();
		JsonArray data = new JsonArray();
		while(rs0.next() )
		{
			System.out.println("Inside while loop:"+rs0.getString(3));
		JsonArray row = new JsonArray();
		row.add(new JsonPrimitive(rs0.getInt(1)));
		row.add(new JsonPrimitive(rs0.getInt(2)));
		row.add(new JsonPrimitive(rs0.getString(3)==null?"":rs0.getString(3)));
		row.add(new JsonPrimitive(rs0.getString(4)==null?"":rs0.getString(4)));
		row.add(new JsonPrimitive(rs0.getString(5)==null?"":rs0.getString(5)));
		row.add(new JsonPrimitive(rs0.getString(6)==null?"":rs0.getString(6)));
		row.add(new JsonPrimitive(rs0.getInt(7)));
		row.add(new JsonPrimitive(rs0.getInt(8)));
		row.add(new JsonPrimitive(rs0.getInt(9)));
		//row.add(new JsonPrimitive(rs0.getString(10)));
		row.add(new JsonPrimitive(rs0.getString(11)==null?"":rs0.getString(11)));
		row.add(new JsonPrimitive(rs0.getString(12)==null?"":rs0.getString(12)));
		row.add(new JsonPrimitive(rs0.getString(13)==null?"":rs0.getString(13)));
		row.add(new JsonPrimitive(rs0.getString(14)==null?"":rs0.getString(14)));

        data.add(row);
        }
        jsonResponse.add("audit_data", data);
        return jsonResponse.toString();
    }

     @PostMapping(value = "/delta-processes/ReportsPerJob")
    public @ResponseBody String ReportsPerJob(@Valid @RequestBody DeltaProcessDTO deltaProcessDTO)throws SQLException,ClassNotFoundException  {
        Properties properties = new Properties();
		properties.put("user", deltaProcessDTO.getSnowflakeConnectionUsername());
		properties.put("password", deltaProcessDTO.getSnowflakeConnectionPassword());
		properties.put("account", deltaProcessDTO.getSnowflakeConnectionAcct());
        properties.put("warehouse",deltaProcessDTO.getSnowflakeConnectionWarehouse());
		properties.put("db",deltaProcessDTO.getSnowflakeConnectionDatabase());
	    properties.put("schema",deltaProcessDTO.getSnowflakeConnectionSchema());
		Connection con2=DriverManager.getConnection(deltaProcessDTO.getSnowflakeConnectionUrl(),properties);
        Statement stmt0=con2.createStatement();
        ResultSet rs0 = stmt0.executeQuery("SELECT MAX(jobid) FROM sah_jobRunStatus");
        rs0.next();
        System.out.println("SELECT * FROM sah_tableLoadStatus WHERE processid ="+deltaProcessDTO.getId() +" AND jobid ="+rs0.getInt(1)+" order by tableloadstarttime desc");
        ResultSet rs1 = stmt0.executeQuery("SELECT * FROM sah_tableLoadStatus WHERE processid ="+deltaProcessDTO.getId() +" AND jobid ="+rs0.getInt(1)+" order by tableloadstarttime desc");

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
     @PostMapping(value = "/delta-processes/retrieveColumnList")
     public @ResponseBody String[] listColumns(@Valid @RequestBody DeltaProcessDTO deltaProcessDTO, String tableName) throws SQLException,ClassNotFoundException {
    	 Properties properties0 = new Properties();
 		properties0.put("user", deltaProcessDTO.getSourceConnectionUsername());
 		properties0.put("password", deltaProcessDTO.getSourceConnectionPassword());
 		properties0.put("db",deltaProcessDTO.getSourceConnectionDatabase());
 	    properties0.put("schema",deltaProcessDTO.getSourceConnectionSchema());
 	    Connection con = DriverManager.getConnection(deltaProcessDTO.getSourceConnectionUrl(),properties0);
         Statement stmt = con.createStatement();
         ResultSet rs1 = stmt.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME ='"+tableName+"' AND TABLE_SCHEMA = '"+deltaProcessDTO.getSourceConnectionSchema()+"';");
         ArrayList tn = new ArrayList();
         while(rs1.next())
         {
             tn.add(rs1.getString("COLUMN_NAME"));
         }
         String[] colNames = (String[])tn.toArray(new String[tn.size()]);
    	 return colNames;
     }
}