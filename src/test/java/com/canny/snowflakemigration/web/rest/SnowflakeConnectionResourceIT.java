package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.SnowpoleApp;
import com.canny.snowflakemigration.domain.SnowflakeConnection;
import com.canny.snowflakemigration.repository.SnowflakeConnectionRepository;
import com.canny.snowflakemigration.service.SnowflakeConnectionService;
import com.canny.snowflakemigration.service.dto.SnowflakeConnectionDTO;
import com.canny.snowflakemigration.service.mapper.SnowflakeConnectionMapper;
import com.canny.snowflakemigration.web.rest.errors.ExceptionTranslator;
import com.canny.snowflakemigration.service.dto.SnowflakeConnectionCriteria;
import com.canny.snowflakemigration.service.SnowflakeConnectionQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.canny.snowflakemigration.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link SnowflakeConnectionResource} REST controller.
 */
@SpringBootTest(classes = SnowpoleApp.class)
public class SnowflakeConnectionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_ACCT = "AAAAAAAAAA";
    private static final String UPDATED_ACCT = "BBBBBBBBBB";

    private static final String DEFAULT_WAREHOUSE = "AAAAAAAAAA";
    private static final String UPDATED_WAREHOUSE = "BBBBBBBBBB";

    private static final String DEFAULT_DATABASE = "AAAAAAAAAA";
    private static final String UPDATED_DATABASE = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEMA = "AAAAAAAAAA";
    private static final String UPDATED_SCHEMA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VALID = false;
    private static final Boolean UPDATED_VALID = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SnowflakeConnectionRepository snowflakeConnectionRepository;

    @Autowired
    private SnowflakeConnectionMapper snowflakeConnectionMapper;

    @Autowired
    private SnowflakeConnectionService snowflakeConnectionService;

    @Autowired
    private SnowflakeConnectionQueryService snowflakeConnectionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSnowflakeConnectionMockMvc;

    private SnowflakeConnection snowflakeConnection;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SnowflakeConnectionResource snowflakeConnectionResource = new SnowflakeConnectionResource(snowflakeConnectionService, snowflakeConnectionQueryService);
        this.restSnowflakeConnectionMockMvc = MockMvcBuilders.standaloneSetup(snowflakeConnectionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SnowflakeConnection createEntity(EntityManager em) {
        SnowflakeConnection snowflakeConnection = new SnowflakeConnection()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .url(DEFAULT_URL)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .acct(DEFAULT_ACCT)
            .warehouse(DEFAULT_WAREHOUSE)
            .database(DEFAULT_DATABASE)
            .schema(DEFAULT_SCHEMA)
            .valid(DEFAULT_VALID);
            // .createdBy(DEFAULT_CREATED_BY)
            // .createdDate(DEFAULT_CREATED_DATE)
            // .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            // .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return snowflakeConnection;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SnowflakeConnection createUpdatedEntity(EntityManager em) {
        SnowflakeConnection snowflakeConnection = new SnowflakeConnection()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .url(UPDATED_URL)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .acct(UPDATED_ACCT)
            .warehouse(UPDATED_WAREHOUSE)
            .database(UPDATED_DATABASE)
            .schema(UPDATED_SCHEMA)
            .valid(UPDATED_VALID);
            // .createdBy(UPDATED_CREATED_BY)
            // .createdDate(UPDATED_CREATED_DATE)
            // .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            // .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return snowflakeConnection;
    }

    @BeforeEach
    public void initTest() {
        snowflakeConnection = createEntity(em);
    }

    @Test
    @Transactional
    public void createSnowflakeConnection() throws Exception {
        int databaseSizeBeforeCreate = snowflakeConnectionRepository.findAll().size();

        // Create the SnowflakeConnection
        SnowflakeConnectionDTO snowflakeConnectionDTO = snowflakeConnectionMapper.toDto(snowflakeConnection);
        restSnowflakeConnectionMockMvc.perform(post("/api/snowflake-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(snowflakeConnectionDTO)))
            .andExpect(status().isCreated());

        // Validate the SnowflakeConnection in the database
        List<SnowflakeConnection> snowflakeConnectionList = snowflakeConnectionRepository.findAll();
        assertThat(snowflakeConnectionList).hasSize(databaseSizeBeforeCreate + 1);
        SnowflakeConnection testSnowflakeConnection = snowflakeConnectionList.get(snowflakeConnectionList.size() - 1);
        assertThat(testSnowflakeConnection.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSnowflakeConnection.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSnowflakeConnection.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testSnowflakeConnection.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testSnowflakeConnection.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testSnowflakeConnection.getAcct()).isEqualTo(DEFAULT_ACCT);
        assertThat(testSnowflakeConnection.getWarehouse()).isEqualTo(DEFAULT_WAREHOUSE);
        assertThat(testSnowflakeConnection.getDatabase()).isEqualTo(DEFAULT_DATABASE);
        assertThat(testSnowflakeConnection.getSchema()).isEqualTo(DEFAULT_SCHEMA);
        assertThat(testSnowflakeConnection.isValid()).isEqualTo(DEFAULT_VALID);
        // assertThat(testSnowflakeConnection.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        // assertThat(testSnowflakeConnection.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        // assertThat(testSnowflakeConnection.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        // assertThat(testSnowflakeConnection.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createSnowflakeConnectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = snowflakeConnectionRepository.findAll().size();

        // Create the SnowflakeConnection with an existing ID
        snowflakeConnection.setId(1L);
        SnowflakeConnectionDTO snowflakeConnectionDTO = snowflakeConnectionMapper.toDto(snowflakeConnection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSnowflakeConnectionMockMvc.perform(post("/api/snowflake-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(snowflakeConnectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SnowflakeConnection in the database
        List<SnowflakeConnection> snowflakeConnectionList = snowflakeConnectionRepository.findAll();
        assertThat(snowflakeConnectionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = snowflakeConnectionRepository.findAll().size();
        // set the field null
        snowflakeConnection.setName(null);

        // Create the SnowflakeConnection, which fails.
        SnowflakeConnectionDTO snowflakeConnectionDTO = snowflakeConnectionMapper.toDto(snowflakeConnection);

        restSnowflakeConnectionMockMvc.perform(post("/api/snowflake-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(snowflakeConnectionDTO)))
            .andExpect(status().isBadRequest());

        List<SnowflakeConnection> snowflakeConnectionList = snowflakeConnectionRepository.findAll();
        assertThat(snowflakeConnectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = snowflakeConnectionRepository.findAll().size();
        // set the field null
        snowflakeConnection.setUrl(null);

        // Create the SnowflakeConnection, which fails.
        SnowflakeConnectionDTO snowflakeConnectionDTO = snowflakeConnectionMapper.toDto(snowflakeConnection);

        restSnowflakeConnectionMockMvc.perform(post("/api/snowflake-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(snowflakeConnectionDTO)))
            .andExpect(status().isBadRequest());

        List<SnowflakeConnection> snowflakeConnectionList = snowflakeConnectionRepository.findAll();
        assertThat(snowflakeConnectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = snowflakeConnectionRepository.findAll().size();
        // set the field null
        snowflakeConnection.setUsername(null);

        // Create the SnowflakeConnection, which fails.
        SnowflakeConnectionDTO snowflakeConnectionDTO = snowflakeConnectionMapper.toDto(snowflakeConnection);

        restSnowflakeConnectionMockMvc.perform(post("/api/snowflake-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(snowflakeConnectionDTO)))
            .andExpect(status().isBadRequest());

        List<SnowflakeConnection> snowflakeConnectionList = snowflakeConnectionRepository.findAll();
        assertThat(snowflakeConnectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = snowflakeConnectionRepository.findAll().size();
        // set the field null
        snowflakeConnection.setPassword(null);

        // Create the SnowflakeConnection, which fails.
        SnowflakeConnectionDTO snowflakeConnectionDTO = snowflakeConnectionMapper.toDto(snowflakeConnection);

        restSnowflakeConnectionMockMvc.perform(post("/api/snowflake-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(snowflakeConnectionDTO)))
            .andExpect(status().isBadRequest());

        List<SnowflakeConnection> snowflakeConnectionList = snowflakeConnectionRepository.findAll();
        assertThat(snowflakeConnectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatabaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = snowflakeConnectionRepository.findAll().size();
        // set the field null
        snowflakeConnection.setDatabase(null);

        // Create the SnowflakeConnection, which fails.
        SnowflakeConnectionDTO snowflakeConnectionDTO = snowflakeConnectionMapper.toDto(snowflakeConnection);

        restSnowflakeConnectionMockMvc.perform(post("/api/snowflake-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(snowflakeConnectionDTO)))
            .andExpect(status().isBadRequest());

        List<SnowflakeConnection> snowflakeConnectionList = snowflakeConnectionRepository.findAll();
        assertThat(snowflakeConnectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnections() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList
        restSnowflakeConnectionMockMvc.perform(get("/api/snowflake-connections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(snowflakeConnection.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].acct").value(hasItem(DEFAULT_ACCT.toString())))
            .andExpect(jsonPath("$.[*].warehouse").value(hasItem(DEFAULT_WAREHOUSE.toString())))
            .andExpect(jsonPath("$.[*].database").value(hasItem(DEFAULT_DATABASE.toString())))
            .andExpect(jsonPath("$.[*].schema").value(hasItem(DEFAULT_SCHEMA.toString())))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())));
            // .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            // .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            // .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            // .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getSnowflakeConnection() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get the snowflakeConnection
        restSnowflakeConnectionMockMvc.perform(get("/api/snowflake-connections/{id}", snowflakeConnection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(snowflakeConnection.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.acct").value(DEFAULT_ACCT.toString()))
            .andExpect(jsonPath("$.warehouse").value(DEFAULT_WAREHOUSE.toString()))
            .andExpect(jsonPath("$.database").value(DEFAULT_DATABASE.toString()))
            .andExpect(jsonPath("$.schema").value(DEFAULT_SCHEMA.toString()))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.booleanValue()));
            // .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            // .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            // .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            // .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where name equals to DEFAULT_NAME
        defaultSnowflakeConnectionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the snowflakeConnectionList where name equals to UPDATED_NAME
        defaultSnowflakeConnectionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSnowflakeConnectionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the snowflakeConnectionList where name equals to UPDATED_NAME
        defaultSnowflakeConnectionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where name is not null
        defaultSnowflakeConnectionShouldBeFound("name.specified=true");

        // Get all the snowflakeConnectionList where name is null
        defaultSnowflakeConnectionShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where description equals to DEFAULT_DESCRIPTION
        defaultSnowflakeConnectionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the snowflakeConnectionList where description equals to UPDATED_DESCRIPTION
        defaultSnowflakeConnectionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSnowflakeConnectionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the snowflakeConnectionList where description equals to UPDATED_DESCRIPTION
        defaultSnowflakeConnectionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where description is not null
        defaultSnowflakeConnectionShouldBeFound("description.specified=true");

        // Get all the snowflakeConnectionList where description is null
        defaultSnowflakeConnectionShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where url equals to DEFAULT_URL
        defaultSnowflakeConnectionShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the snowflakeConnectionList where url equals to UPDATED_URL
        defaultSnowflakeConnectionShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where url in DEFAULT_URL or UPDATED_URL
        defaultSnowflakeConnectionShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the snowflakeConnectionList where url equals to UPDATED_URL
        defaultSnowflakeConnectionShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where url is not null
        defaultSnowflakeConnectionShouldBeFound("url.specified=true");

        // Get all the snowflakeConnectionList where url is null
        defaultSnowflakeConnectionShouldNotBeFound("url.specified=false");
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where username equals to DEFAULT_USERNAME
        defaultSnowflakeConnectionShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the snowflakeConnectionList where username equals to UPDATED_USERNAME
        defaultSnowflakeConnectionShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultSnowflakeConnectionShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the snowflakeConnectionList where username equals to UPDATED_USERNAME
        defaultSnowflakeConnectionShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where username is not null
        defaultSnowflakeConnectionShouldBeFound("username.specified=true");

        // Get all the snowflakeConnectionList where username is null
        defaultSnowflakeConnectionShouldNotBeFound("username.specified=false");
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where password equals to DEFAULT_PASSWORD
        defaultSnowflakeConnectionShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the snowflakeConnectionList where password equals to UPDATED_PASSWORD
        defaultSnowflakeConnectionShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultSnowflakeConnectionShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the snowflakeConnectionList where password equals to UPDATED_PASSWORD
        defaultSnowflakeConnectionShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where password is not null
        defaultSnowflakeConnectionShouldBeFound("password.specified=true");

        // Get all the snowflakeConnectionList where password is null
        defaultSnowflakeConnectionShouldNotBeFound("password.specified=false");
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByAcctIsEqualToSomething() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where acct equals to DEFAULT_ACCT
        defaultSnowflakeConnectionShouldBeFound("acct.equals=" + DEFAULT_ACCT);

        // Get all the snowflakeConnectionList where acct equals to UPDATED_ACCT
        defaultSnowflakeConnectionShouldNotBeFound("acct.equals=" + UPDATED_ACCT);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByAcctIsInShouldWork() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where acct in DEFAULT_ACCT or UPDATED_ACCT
        defaultSnowflakeConnectionShouldBeFound("acct.in=" + DEFAULT_ACCT + "," + UPDATED_ACCT);

        // Get all the snowflakeConnectionList where acct equals to UPDATED_ACCT
        defaultSnowflakeConnectionShouldNotBeFound("acct.in=" + UPDATED_ACCT);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByAcctIsNullOrNotNull() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where acct is not null
        defaultSnowflakeConnectionShouldBeFound("acct.specified=true");

        // Get all the snowflakeConnectionList where acct is null
        defaultSnowflakeConnectionShouldNotBeFound("acct.specified=false");
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByWarehouseIsEqualToSomething() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where warehouse equals to DEFAULT_WAREHOUSE
        defaultSnowflakeConnectionShouldBeFound("warehouse.equals=" + DEFAULT_WAREHOUSE);

        // Get all the snowflakeConnectionList where warehouse equals to UPDATED_WAREHOUSE
        defaultSnowflakeConnectionShouldNotBeFound("warehouse.equals=" + UPDATED_WAREHOUSE);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByWarehouseIsInShouldWork() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where warehouse in DEFAULT_WAREHOUSE or UPDATED_WAREHOUSE
        defaultSnowflakeConnectionShouldBeFound("warehouse.in=" + DEFAULT_WAREHOUSE + "," + UPDATED_WAREHOUSE);

        // Get all the snowflakeConnectionList where warehouse equals to UPDATED_WAREHOUSE
        defaultSnowflakeConnectionShouldNotBeFound("warehouse.in=" + UPDATED_WAREHOUSE);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByWarehouseIsNullOrNotNull() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where warehouse is not null
        defaultSnowflakeConnectionShouldBeFound("warehouse.specified=true");

        // Get all the snowflakeConnectionList where warehouse is null
        defaultSnowflakeConnectionShouldNotBeFound("warehouse.specified=false");
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByDatabaseIsEqualToSomething() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where database equals to DEFAULT_DATABASE
        defaultSnowflakeConnectionShouldBeFound("database.equals=" + DEFAULT_DATABASE);

        // Get all the snowflakeConnectionList where database equals to UPDATED_DATABASE
        defaultSnowflakeConnectionShouldNotBeFound("database.equals=" + UPDATED_DATABASE);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByDatabaseIsInShouldWork() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where database in DEFAULT_DATABASE or UPDATED_DATABASE
        defaultSnowflakeConnectionShouldBeFound("database.in=" + DEFAULT_DATABASE + "," + UPDATED_DATABASE);

        // Get all the snowflakeConnectionList where database equals to UPDATED_DATABASE
        defaultSnowflakeConnectionShouldNotBeFound("database.in=" + UPDATED_DATABASE);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByDatabaseIsNullOrNotNull() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where database is not null
        defaultSnowflakeConnectionShouldBeFound("database.specified=true");

        // Get all the snowflakeConnectionList where database is null
        defaultSnowflakeConnectionShouldNotBeFound("database.specified=false");
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsBySchemaIsEqualToSomething() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where schema equals to DEFAULT_SCHEMA
        defaultSnowflakeConnectionShouldBeFound("schema.equals=" + DEFAULT_SCHEMA);

        // Get all the snowflakeConnectionList where schema equals to UPDATED_SCHEMA
        defaultSnowflakeConnectionShouldNotBeFound("schema.equals=" + UPDATED_SCHEMA);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsBySchemaIsInShouldWork() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where schema in DEFAULT_SCHEMA or UPDATED_SCHEMA
        defaultSnowflakeConnectionShouldBeFound("schema.in=" + DEFAULT_SCHEMA + "," + UPDATED_SCHEMA);

        // Get all the snowflakeConnectionList where schema equals to UPDATED_SCHEMA
        defaultSnowflakeConnectionShouldNotBeFound("schema.in=" + UPDATED_SCHEMA);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsBySchemaIsNullOrNotNull() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where schema is not null
        defaultSnowflakeConnectionShouldBeFound("schema.specified=true");

        // Get all the snowflakeConnectionList where schema is null
        defaultSnowflakeConnectionShouldNotBeFound("schema.specified=false");
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByValidIsEqualToSomething() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where valid equals to DEFAULT_VALID
        defaultSnowflakeConnectionShouldBeFound("valid.equals=" + DEFAULT_VALID);

        // Get all the snowflakeConnectionList where valid equals to UPDATED_VALID
        defaultSnowflakeConnectionShouldNotBeFound("valid.equals=" + UPDATED_VALID);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByValidIsInShouldWork() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where valid in DEFAULT_VALID or UPDATED_VALID
        defaultSnowflakeConnectionShouldBeFound("valid.in=" + DEFAULT_VALID + "," + UPDATED_VALID);

        // Get all the snowflakeConnectionList where valid equals to UPDATED_VALID
        defaultSnowflakeConnectionShouldNotBeFound("valid.in=" + UPDATED_VALID);
    }

    @Test
    @Transactional
    public void getAllSnowflakeConnectionsByValidIsNullOrNotNull() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        // Get all the snowflakeConnectionList where valid is not null
        defaultSnowflakeConnectionShouldBeFound("valid.specified=true");

        // Get all the snowflakeConnectionList where valid is null
        defaultSnowflakeConnectionShouldNotBeFound("valid.specified=false");
    }

    // @Test
    // @Transactional
    // public void getAllSnowflakeConnectionsByCreatedByIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

    //     // Get all the snowflakeConnectionList where createdBy equals to DEFAULT_CREATED_BY
    //     defaultSnowflakeConnectionShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

    //     // Get all the snowflakeConnectionList where createdBy equals to UPDATED_CREATED_BY
    //     defaultSnowflakeConnectionShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowflakeConnectionsByCreatedByIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

    //     // Get all the snowflakeConnectionList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
    //     defaultSnowflakeConnectionShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

    //     // Get all the snowflakeConnectionList where createdBy equals to UPDATED_CREATED_BY
    //     defaultSnowflakeConnectionShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowflakeConnectionsByCreatedByIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

    //     // Get all the snowflakeConnectionList where createdBy is not null
    //     defaultSnowflakeConnectionShouldBeFound("createdBy.specified=true");

    //     // Get all the snowflakeConnectionList where createdBy is null
    //     defaultSnowflakeConnectionShouldNotBeFound("createdBy.specified=false");
    // }

    // @Test
    // @Transactional
    // public void getAllSnowflakeConnectionsByCreatedDateIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

    //     // Get all the snowflakeConnectionList where createdDate equals to DEFAULT_CREATED_DATE
    //     defaultSnowflakeConnectionShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

    //     // Get all the snowflakeConnectionList where createdDate equals to UPDATED_CREATED_DATE
    //     defaultSnowflakeConnectionShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowflakeConnectionsByCreatedDateIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

    //     // Get all the snowflakeConnectionList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
    //     defaultSnowflakeConnectionShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

    //     // Get all the snowflakeConnectionList where createdDate equals to UPDATED_CREATED_DATE
    //     defaultSnowflakeConnectionShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowflakeConnectionsByCreatedDateIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

    //     // Get all the snowflakeConnectionList where createdDate is not null
    //     defaultSnowflakeConnectionShouldBeFound("createdDate.specified=true");

    //     // Get all the snowflakeConnectionList where createdDate is null
    //     defaultSnowflakeConnectionShouldNotBeFound("createdDate.specified=false");
    // }

    // @Test
    // @Transactional
    // public void getAllSnowflakeConnectionsByLastModifiedByIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

    //     // Get all the snowflakeConnectionList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
    //     defaultSnowflakeConnectionShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

    //     // Get all the snowflakeConnectionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
    //     defaultSnowflakeConnectionShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowflakeConnectionsByLastModifiedByIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

    //     // Get all the snowflakeConnectionList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
    //     defaultSnowflakeConnectionShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

    //     // Get all the snowflakeConnectionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
    //     defaultSnowflakeConnectionShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowflakeConnectionsByLastModifiedByIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

    //     // Get all the snowflakeConnectionList where lastModifiedBy is not null
    //     defaultSnowflakeConnectionShouldBeFound("lastModifiedBy.specified=true");

    //     // Get all the snowflakeConnectionList where lastModifiedBy is null
    //     defaultSnowflakeConnectionShouldNotBeFound("lastModifiedBy.specified=false");
    // }

    // @Test
    // @Transactional
    // public void getAllSnowflakeConnectionsByLastModifiedDateIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

    //     // Get all the snowflakeConnectionList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
    //     defaultSnowflakeConnectionShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

    //     // Get all the snowflakeConnectionList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
    //     defaultSnowflakeConnectionShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowflakeConnectionsByLastModifiedDateIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

    //     // Get all the snowflakeConnectionList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
    //     defaultSnowflakeConnectionShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

    //     // Get all the snowflakeConnectionList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
    //     defaultSnowflakeConnectionShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowflakeConnectionsByLastModifiedDateIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

    //     // Get all the snowflakeConnectionList where lastModifiedDate is not null
    //     defaultSnowflakeConnectionShouldBeFound("lastModifiedDate.specified=true");

    //     // Get all the snowflakeConnectionList where lastModifiedDate is null
    //     defaultSnowflakeConnectionShouldNotBeFound("lastModifiedDate.specified=false");
    // }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSnowflakeConnectionShouldBeFound(String filter) throws Exception {
        restSnowflakeConnectionMockMvc.perform(get("/api/snowflake-connections?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(snowflakeConnection.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].acct").value(hasItem(DEFAULT_ACCT)))
            .andExpect(jsonPath("$.[*].warehouse").value(hasItem(DEFAULT_WAREHOUSE)))
            .andExpect(jsonPath("$.[*].database").value(hasItem(DEFAULT_DATABASE)))
            .andExpect(jsonPath("$.[*].schema").value(hasItem(DEFAULT_SCHEMA)))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())));
            // .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            // .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            // .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            // .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restSnowflakeConnectionMockMvc.perform(get("/api/snowflake-connections/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSnowflakeConnectionShouldNotBeFound(String filter) throws Exception {
        restSnowflakeConnectionMockMvc.perform(get("/api/snowflake-connections?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSnowflakeConnectionMockMvc.perform(get("/api/snowflake-connections/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSnowflakeConnection() throws Exception {
        // Get the snowflakeConnection
        restSnowflakeConnectionMockMvc.perform(get("/api/snowflake-connections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSnowflakeConnection() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        int databaseSizeBeforeUpdate = snowflakeConnectionRepository.findAll().size();

        // Update the snowflakeConnection
        SnowflakeConnection updatedSnowflakeConnection = snowflakeConnectionRepository.findById(snowflakeConnection.getId()).get();
        // Disconnect from session so that the updates on updatedSnowflakeConnection are not directly saved in db
        em.detach(updatedSnowflakeConnection);
        updatedSnowflakeConnection
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .url(UPDATED_URL)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .acct(UPDATED_ACCT)
            .warehouse(UPDATED_WAREHOUSE)
            .database(UPDATED_DATABASE)
            .schema(UPDATED_SCHEMA)
            .valid(UPDATED_VALID);
            // .createdBy(UPDATED_CREATED_BY)
            // .createdDate(UPDATED_CREATED_DATE)
            // .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            // .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        SnowflakeConnectionDTO snowflakeConnectionDTO = snowflakeConnectionMapper.toDto(updatedSnowflakeConnection);

        restSnowflakeConnectionMockMvc.perform(put("/api/snowflake-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(snowflakeConnectionDTO)))
            .andExpect(status().isOk());

        // Validate the SnowflakeConnection in the database
        List<SnowflakeConnection> snowflakeConnectionList = snowflakeConnectionRepository.findAll();
        assertThat(snowflakeConnectionList).hasSize(databaseSizeBeforeUpdate);
        SnowflakeConnection testSnowflakeConnection = snowflakeConnectionList.get(snowflakeConnectionList.size() - 1);
        assertThat(testSnowflakeConnection.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSnowflakeConnection.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSnowflakeConnection.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testSnowflakeConnection.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testSnowflakeConnection.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testSnowflakeConnection.getAcct()).isEqualTo(UPDATED_ACCT);
        assertThat(testSnowflakeConnection.getWarehouse()).isEqualTo(UPDATED_WAREHOUSE);
        assertThat(testSnowflakeConnection.getDatabase()).isEqualTo(UPDATED_DATABASE);
        assertThat(testSnowflakeConnection.getSchema()).isEqualTo(UPDATED_SCHEMA);
        assertThat(testSnowflakeConnection.isValid()).isEqualTo(UPDATED_VALID);
        // assertThat(testSnowflakeConnection.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        // assertThat(testSnowflakeConnection.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        // assertThat(testSnowflakeConnection.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        // assertThat(testSnowflakeConnection.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSnowflakeConnection() throws Exception {
        int databaseSizeBeforeUpdate = snowflakeConnectionRepository.findAll().size();

        // Create the SnowflakeConnection
        SnowflakeConnectionDTO snowflakeConnectionDTO = snowflakeConnectionMapper.toDto(snowflakeConnection);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSnowflakeConnectionMockMvc.perform(put("/api/snowflake-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(snowflakeConnectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SnowflakeConnection in the database
        List<SnowflakeConnection> snowflakeConnectionList = snowflakeConnectionRepository.findAll();
        assertThat(snowflakeConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSnowflakeConnection() throws Exception {
        // Initialize the database
        snowflakeConnectionRepository.saveAndFlush(snowflakeConnection);

        int databaseSizeBeforeDelete = snowflakeConnectionRepository.findAll().size();

        // Delete the snowflakeConnection
        restSnowflakeConnectionMockMvc.perform(delete("/api/snowflake-connections/{id}", snowflakeConnection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SnowflakeConnection> snowflakeConnectionList = snowflakeConnectionRepository.findAll();
        assertThat(snowflakeConnectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SnowflakeConnection.class);
        SnowflakeConnection snowflakeConnection1 = new SnowflakeConnection();
        snowflakeConnection1.setId(1L);
        SnowflakeConnection snowflakeConnection2 = new SnowflakeConnection();
        snowflakeConnection2.setId(snowflakeConnection1.getId());
        assertThat(snowflakeConnection1).isEqualTo(snowflakeConnection2);
        snowflakeConnection2.setId(2L);
        assertThat(snowflakeConnection1).isNotEqualTo(snowflakeConnection2);
        snowflakeConnection1.setId(null);
        assertThat(snowflakeConnection1).isNotEqualTo(snowflakeConnection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SnowflakeConnectionDTO.class);
        SnowflakeConnectionDTO snowflakeConnectionDTO1 = new SnowflakeConnectionDTO();
        snowflakeConnectionDTO1.setId(1L);
        SnowflakeConnectionDTO snowflakeConnectionDTO2 = new SnowflakeConnectionDTO();
        assertThat(snowflakeConnectionDTO1).isNotEqualTo(snowflakeConnectionDTO2);
        snowflakeConnectionDTO2.setId(snowflakeConnectionDTO1.getId());
        assertThat(snowflakeConnectionDTO1).isEqualTo(snowflakeConnectionDTO2);
        snowflakeConnectionDTO2.setId(2L);
        assertThat(snowflakeConnectionDTO1).isNotEqualTo(snowflakeConnectionDTO2);
        snowflakeConnectionDTO1.setId(null);
        assertThat(snowflakeConnectionDTO1).isNotEqualTo(snowflakeConnectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(snowflakeConnectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(snowflakeConnectionMapper.fromId(null)).isNull();
    }
}
