package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.service.SnowHistoryService;
import com.canny.snowflakemigration.service.SnowHistoryProcessStatusService;
import com.canny.snowflakemigration.web.rest.errors.BadRequestAlertException;
import com.canny.snowflakemigration.service.dto.SnowHistoryDTO;
import com.canny.snowflakemigration.service.dto.SourceConnectionDTO;
import com.canny.snowflakemigration.service.util.HistoryListTables;
import com.canny.snowflakemigration.service.dto.SnowflakeConnectionDTO;
import com.canny.snowflakemigration.service.dto.SnowHistoryCriteria;
import com.canny.snowflakemigration.service.SnowHistoryJobStatusService;
import com.canny.snowflakemigration.service.SnowHistoryQueryService;

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

import static com.canny.snowflakemigration.service.util.HistoryListTables.historyListTable;
import static com.canny.snowflakemigration.service.util.HistorySendTableList.sendSelectedTables;


import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//import org.json.JSONArray;
//import org.json.JSONObject;


/**
 * REST controller for managing {@link com.canny.snowflakemigration.domain.SnowHistory}.
 */
@RestController
@RequestMapping("/api")
public class SnowHistoryResource {

    private final Logger log = LoggerFactory.getLogger(SnowHistoryResource.class);

    private static final String ENTITY_NAME = "snowHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SnowHistoryService snowHistoryService;

    private final SnowHistoryProcessStatusService snowHistoryProcessStatusService;

    private final SnowHistoryJobStatusService snowHistoryJobStatusService;

    private final SnowHistoryQueryService snowHistoryQueryService;

    public SnowHistoryResource(SnowHistoryService snowHistoryService, SnowHistoryQueryService snowHistoryQueryService, SnowHistoryProcessStatusService snowHistoryProcessStatusService, SnowHistoryJobStatusService snowHistoryJobStatusService) {
        this.snowHistoryService = snowHistoryService;
        this.snowHistoryQueryService = snowHistoryQueryService;
        this.snowHistoryProcessStatusService = snowHistoryProcessStatusService;
        this.snowHistoryJobStatusService = snowHistoryJobStatusService;
    }

    /**
     * {@code POST  /snow-histories} : Create a new snowHistory.
     *
     * @param snowHistoryDTO the snowHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new snowHistoryDTO, or with status {@code 400 (Bad Request)} if the snowHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/snow-histories")
    public ResponseEntity<SnowHistoryDTO> createSnowHistory(@Valid @RequestBody SnowHistoryDTO snowHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save SnowHistory : {}", snowHistoryDTO);
        if (snowHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new snowHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SnowHistoryDTO result = snowHistoryService.save(snowHistoryDTO);
        return ResponseEntity.created(new URI("/api/snow-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /snow-histories} : Updates an existing snowHistory.
     *
     * @param snowHistoryDTO the snowHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated snowHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the snowHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the snowHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/snow-histories")
    public ResponseEntity<SnowHistoryDTO> updateSnowHistory(@Valid @RequestBody SnowHistoryDTO snowHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update SnowHistory : {}", snowHistoryDTO);
        if (snowHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SnowHistoryDTO result = snowHistoryService.save(snowHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, snowHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /snow-histories} : get all the snowHistories.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of snowHistories in body.
     */
    @GetMapping("/snow-histories")
    public ResponseEntity<List<SnowHistoryDTO>> getAllSnowHistories(SnowHistoryCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get SnowHistories by criteria: {}", criteria);
        Page<SnowHistoryDTO> page = snowHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /snow-histories/count} : count all the snowHistories.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/snow-histories/count")
    public ResponseEntity<Long> countSnowHistories(SnowHistoryCriteria criteria) {
        log.debug("REST request to count SnowHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(snowHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /snow-histories/:id} : get the "id" snowHistory.
     *
     * @param id the id of the snowHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the snowHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/snow-histories/{id}")
    public ResponseEntity<SnowHistoryDTO> getSnowHistory(@PathVariable Long id) {
        log.debug("REST request to get SnowHistory : {}", id);
        Optional<SnowHistoryDTO> snowHistoryDTO = snowHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(snowHistoryDTO);
    }

    /**
     * {@code DELETE  /snow-histories/:id} : delete the "id" snowHistory.
     *
     * @param id the id of the snowHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/snow-histories/{id}")
    public ResponseEntity<Void> deleteSnowHistory(@PathVariable Long id) {
        log.debug("REST request to delete SnowHistory : {}", id);
        snowHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

     @PostMapping(value = "/snow-histories/retrieveTableList")
    public @ResponseBody String historyListTables(@Valid @RequestBody SnowHistoryDTO snowHistoryDTO) throws SQLException,ClassNotFoundException {
        String tableName = HistoryListTable(snowHistoryDTO);
        return tableName;
    }
    
    @PostMapping(value = "/snow-histories/HistorySendTableListforHistProcess")
    public @ResponseBody String HistorySendTableList(@Valid @RequestBody  SnowHistoryDTO snowHistoryDTO)throws SQLException,ClassNotFoundException,InterruptedException  {
    	//Optional<SnowHistoryDTO>  snowHistoryDTO = snowHistoryService.findOne(processid);
    	String result = sendSelectedTables(snowHistoryDTO,snowHistoryProcessStatusService,snowHistoryJobStatusService);    
        return result;
    }
    @PostMapping(value="/snow-histories/TestConnectionSource")
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
    
    @PostMapping(value="/snow-histories/TestConnectionDest")
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
    
     @PostMapping(value = "/snow-histories/Reports")
    public @ResponseBody String Reports(@Valid @RequestBody SnowHistoryDTO snowHistoryDTO)throws SQLException,ClassNotFoundException  {
        Properties properties = new Properties();
		properties.put("user", snowHistoryDTO.getSnowflakeConnectionUsername());
		properties.put("password", snowHistoryDTO.getSnowflakeConnectionPassword());
		properties.put("account", snowHistoryDTO.getSnowflakeConnectionAcct());
        properties.put("warehouse",snowHistoryDTO.getSnowflakeConnectionWarehouse());
		properties.put("db",snowHistoryDTO.getSnowflakeConnectionDatabase());
	    properties.put("schema",snowHistoryDTO.getSnowflakeConnectionSchema());
		Connection con2=DriverManager.getConnection(snowHistoryDTO.getSnowflakeConnectionUrl(),properties);
        Statement stmt0=con2.createStatement(); 

        Connection con3 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/smapp","postgres","super");
	    Statement st0 = con3.createStatement();
        ResultSet rs0 = st0.executeQuery("SELECT * FROM snow_history_job_status");// WHERE processid ="+snowHistoryDTO.getId() +" order by tableloadstarttime desc");
        
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

     @PostMapping(value = "/snow-histories/ReportsPerJob")
    public @ResponseBody String ReportsPerJob(@Valid @RequestBody SnowHistoryDTO snowHistoryDTO)throws SQLException,ClassNotFoundException  {
        Properties properties = new Properties();
		properties.put("user", snowHistoryDTO.getSnowflakeConnectionUsername());
		properties.put("password", snowHistoryDTO.getSnowflakeConnectionPassword());
		properties.put("account", snowHistoryDTO.getSnowflakeConnectionAcct());
        properties.put("warehouse",snowHistoryDTO.getSnowflakeConnectionWarehouse());
		properties.put("db",snowHistoryDTO.getSnowflakeConnectionDatabase());
	    properties.put("schema",snowHistoryDTO.getSnowflakeConnectionSchema());
		Connection con2=DriverManager.getConnection(snowHistoryDTO.getSnowflakeConnectionUrl(),properties);
        Statement stmt0=con2.createStatement(); 
        ResultSet rs0 = stmt0.executeQuery("SELECT MAX(jobid) FROM sah_jobRunStatus");
        rs0.next();
        System.out.println("SELECT * FROM sah_tableLoadStatus WHERE processid ="+snowHistoryDTO.getId() +" AND jobid ="+rs0.getInt(1)+" order by tableloadstarttime desc");
        ResultSet rs1 = stmt0.executeQuery("SELECT * FROM sah_tableLoadStatus WHERE processid ="+snowHistoryDTO.getId() +" AND jobid ="+rs0.getInt(1)+" order by tableloadstarttime desc");
        
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
     @PostMapping(value = "/snow-histories/retrieveColumnList")
     public @ResponseBody String[] listColumns(@Valid @RequestBody SnowHistoryDTO snowHistoryDTO, String tableName) throws SQLException,ClassNotFoundException {
    	 Properties properties0 = new Properties();
 		properties0.put("user", snowHistoryDTO.getSourceConnectionUsername());
 		properties0.put("password", snowHistoryDTO.getSourceConnectionPassword());
 		properties0.put("db",snowHistoryDTO.getSourceConnectionDatabase());
 	    properties0.put("schema",snowHistoryDTO.getSourceConnectionSchema());	
 	    Connection con = DriverManager.getConnection(snowHistoryDTO.getSourceConnectionUrl(),properties0);
         Statement stmt = con.createStatement();
         ResultSet rs1 = stmt.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME ='"+tableName+"' AND TABLE_SCHEMA = '"+snowHistoryDTO.getSourceConnectionSchema()+"';");
         ArrayList tn = new ArrayList();
         while(rs1.next())
         {
             tn.add(rs1.getString("COLUMN_NAME"));
         }
         String[] colNames = (String[])tn.toArray(new String[tn.size()]);
    	 return colNames;
     }
}