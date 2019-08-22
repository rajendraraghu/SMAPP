package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.service.SnowParseService;
import com.canny.snowflakemigration.web.rest.errors.BadRequestAlertException;
import com.canny.snowflakemigration.service.dto.SnowParseDTO;
import com.canny.snowflakemigration.service.dto.SnowParseProcessStatusDTO;
import com.canny.snowflakemigration.service.dto.SnowParseCriteria;
// import com.canny.snowflakemigration.domain.DDLConversionProcessor;
import com.canny.snowflakemigration.service.SnowParseQueryService;
import com.canny.snowflakemigration.service.SnowParseJobStatusService;
import com.canny.snowflakemigration.service.SnowParseProcessStatusService;

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
// import static com.canny.snowflakemigration.service.util.ConvertParse.convertToSnowParse;


import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//import org.json.JSONArray;
//import org.json.JSONObject;


/**
 * REST controller for managing {@link com.canny.snowflakemigration.domain.SnowParse}.
 */
@RestController
@RequestMapping("/api")
public class SnowParseResource {

    private final Logger log = LoggerFactory.getLogger(SnowParseResource.class);

    private static final String ENTITY_NAME = "SnowParse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SnowParseService SnowParseService;

    private final SnowParseQueryService SnowParseQueryService;

    private final SnowParseProcessStatusService SnowParseProcessStatusService;

    private final SnowParseJobStatusService SnowParseJobStatusService;

    public SnowParseResource(SnowParseService SnowParseService, SnowParseQueryService SnowParseQueryService,SnowParseProcessStatusService SnowParseProcessStatusService,SnowParseJobStatusService SnowParseJobStatusService) {
        this.SnowParseService = SnowParseService;
        this.SnowParseQueryService = SnowParseQueryService;
        this.SnowParseProcessStatusService = SnowParseProcessStatusService;
        this.SnowParseJobStatusService = SnowParseJobStatusService;
    }

    /**
     * {@code POST  /snow-parse} : Create a new SnowParse.
     *
     * @param SnowParseDTO the SnowParseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new SnowParseDTO, or with status {@code 400 (Bad Request)} if the SnowParse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/snow-parse")
    public ResponseEntity<SnowParseDTO> createSnowParse(@Valid @RequestBody SnowParseDTO SnowParseDTO) throws URISyntaxException {
        log.debug("REST request to save SnowParse : {}", SnowParseDTO);
        if (SnowParseDTO.getId() != null) {
            throw new BadRequestAlertException("A new SnowParse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SnowParseDTO result = SnowParseService.save(SnowParseDTO);
        return ResponseEntity.created(new URI("/api/snow-parse/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /snow-parse} : Updates an existing SnowParse.
     *
     * @param SnowParseDTO the SnowParseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated SnowParseDTO,
     * or with status {@code 400 (Bad Request)} if the SnowParseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the SnowParseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/snow-parse")
    public ResponseEntity<SnowParseDTO> updateSnowParse(@Valid @RequestBody SnowParseDTO SnowParseDTO) throws URISyntaxException {
        log.debug("REST request to update SnowParse : {}", SnowParseDTO);
        if (SnowParseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SnowParseDTO result = SnowParseService.save(SnowParseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, SnowParseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /snow-parse} : get all the SnowParsees.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of SnowParsees in body.
     */
    @GetMapping("/snow-parse")
    public ResponseEntity<List<SnowParseDTO>> getAllSnowParsees(SnowParseCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get SnowParsees by criteria: {}", criteria);
        Page<SnowParseDTO> page = SnowParseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /snow-parse/count} : count all the SnowParsees.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/snow-parse/count")
    public ResponseEntity<Long> countSnowParsees(SnowParseCriteria criteria) {
        log.debug("REST request to count SnowParsees by criteria: {}", criteria);
        return ResponseEntity.ok().body(SnowParseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /snow-parse/:id} : get the "id" SnowParse.
     *
     * @param id the id of the SnowParseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SnowParseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/snow-parse/{id}")
    public ResponseEntity<SnowParseDTO> getSnowParse(@PathVariable Long id) {
        log.debug("REST request to get SnowParse : {}", id);
        Optional<SnowParseDTO> SnowParseDTO = SnowParseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(SnowParseDTO);
    }

    /**
     * {@code DELETE  /snow-parse/:id} : delete the "id" SnowParse.
     *
     * @param id the id of the SnowParseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/snow-parse/{id}")
    public ResponseEntity<Void> deleteSnowParse(@PathVariable Long id) {
        log.debug("REST request to delete SnowParse : {}", id);
        SnowParseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    // @PostMapping(value = "/snow-parse/sendDDLtoConvert")
    // public @ResponseBody String sendDDL(@Valid @RequestBody  SnowParseDTO SnowParseDTO)throws IOException  {
    //     //Optional<SnowParseDTO>  SnowParseDTO = SnowParseService.findOne(processid);
    //     // DDLConversionProcessor processor = new DDLConversionProcessor();
    // 	String result = convertToSnowParse(SnowParseDTO,SnowParseProcessStatusService,SnowParseJobStatusService);    
    //     return result;
    // }
  /*  @PostMapping(value="/snow-parse/TestConnection")
    public @ResponseBody boolean TestingConnection(ConnectionDTO connectionDTO)throws SQLException,ClassNotFoundException  {
        Connection con1 = DriverManager.getConnection(connectionDTO.getUrl(), connectionDTO.getUsername(), connectionDTO.getPassword());
        boolean result = con1.isValid(10);
        return result;
    }*/
    
    // @GetMapping(value = "/snow-parse/Reports/{id}")
    // public ResponseEntity<String> Reports(@PathVariable Long id)throws SQLException,ClassNotFoundException  {      
    //     log.debug("REST request to get SnowParseProcessStatus : {}", id);
    //     Optional<SnowParseProcessStatusDTO> SnowParseProcessStatusDTO = SnowParseService.findOne(id);
    //     return ResponseEntity.ok();
    // }

    //  @PostMapping(value = "/snow-parse/ReportsPerJob")
    // public @ResponseBody String ReportsPerJob(@Valid @RequestBody SnowParseDTO SnowParseDTO)throws SQLException,ClassNotFoundException  {
    //     Properties properties = new Properties();
	// 	properties.put("user", SnowParseDTO.getSnowflakeConnectionUsername());
	// 	properties.put("password", SnowParseDTO.getSnowflakeConnectionPassword());
	// 	properties.put("account", SnowParseDTO.getSnowflakeConnectionAcct());
    //     properties.put("warehouse",SnowParseDTO.getSnowflakeConnectionWarehouse());
	// 	properties.put("db",SnowParseDTO.getSnowflakeConnectionDatabase());
	//     properties.put("schema",SnowParseDTO.getSnowflakeConnectionSchema());
	// 	Connection con2=DriverManager.getConnection(SnowParseDTO.getSnowflakeConnectionUrl(),properties);
    //     Statement stmt0=con2.createStatement(); 
    //     ResultSet rs0 = stmt0.executeQuery("SELECT MAX(jobid) FROM jobRunStatus");
    //     rs0.next();
    //     System.out.println("SELECT * FROM tableLoadStatus WHERE processid ="+SnowParseDTO.getId() +" AND jobid ="+rs0.getInt(1)+" order by tableloadstarttime desc");
    //     ResultSet rs1 = stmt0.executeQuery("SELECT * FROM tableLoadStatus WHERE processid ="+SnowParseDTO.getId() +" AND jobid ="+rs0.getInt(1)+" order by tableloadstarttime desc");
        
    //     JsonObject jsonResponse = new JsonObject();	
	// 	JsonArray data = new JsonArray();
	// 	while(rs1.next() ) 
	// 	{
	// 		System.out.println("Inside while loop:"+rs1.getString(3));
	// 	JsonArray row = new JsonArray();
	// 	row.add(new JsonPrimitive(rs1.getInt(1)));
	// 	row.add(new JsonPrimitive(rs1.getInt(2)));
	// 	row.add(new JsonPrimitive(rs1.getString(3)==null?"":rs1.getString(3)));
	// 	row.add(new JsonPrimitive(rs1.getString(4)==null?"":rs1.getString(4)));
	// 	row.add(new JsonPrimitive(rs1.getString(5)==null?"":rs1.getString(5)));
	// 	row.add(new JsonPrimitive(rs1.getString(6)==null?"":rs1.getString(6)));
	// 	row.add(new JsonPrimitive(rs1.getInt(7)));
	// 	row.add(new JsonPrimitive(rs1.getInt(8)));
	// 	row.add(new JsonPrimitive(rs1.getInt(9)));
	// 	//row.add(new JsonPrimitive(rs0.getString(10)));
	// 	row.add(new JsonPrimitive(rs1.getString(11)==null?"":rs1.getString(11)));
	// 	row.add(new JsonPrimitive(rs1.getString(12)==null?"":rs1.getString(12)));
	// 	row.add(new JsonPrimitive(rs1.getString(13)==null?"":rs1.getString(13)));
	// 	row.add(new JsonPrimitive(rs1.getString(14)==null?"":rs1.getString(14)));
		
    //     data.add(row);
    //     }
    //     jsonResponse.add("audit_data", data);
    //     return jsonResponse.toString();
    // }
     @PostMapping(value = "/snow-parse/retrieveColumnList")
     public @ResponseBody String[] listColumns(@Valid @RequestBody SnowParseDTO SnowParseDTO, String tableName) throws SQLException,ClassNotFoundException {
    	 Properties properties0 = new Properties();
 		properties0.put("user", SnowParseDTO.getSourceConnectionUsername());
 		properties0.put("password", SnowParseDTO.getSourceConnectionPassword());
 		properties0.put("db",SnowParseDTO.getSourceConnectionDatabase());
 	    properties0.put("schema",SnowParseDTO.getSourceConnectionSchema());	
 	    Connection con = DriverManager.getConnection(SnowParseDTO.getSourceConnectionUrl(),properties0);
         Statement stmt = con.createStatement();
         ResultSet rs1 = stmt.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME ='"+tableName+"' AND TABLE_SCHEMA = '"+SnowParseDTO.getSourceConnectionSchema()+"';");
         ArrayList tn = new ArrayList();
         while(rs1.next())
         {
             tn.add(rs1.getString("COLUMN_NAME"));
         }
         String[] colNames = (String[])tn.toArray(new String[tn.size()]);
    	 return colNames;
     }
}