package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.SnowpoleApp;
import com.canny.snowflakemigration.domain.SourceConnection;
import com.canny.snowflakemigration.repository.SourceConnectionRepository;
import com.canny.snowflakemigration.service.SourceConnectionService;
import com.canny.snowflakemigration.service.dto.SourceConnectionDTO;
import com.canny.snowflakemigration.service.mapper.SourceConnectionMapper;
import com.canny.snowflakemigration.web.rest.errors.ExceptionTranslator;
import com.canny.snowflakemigration.service.dto.SourceConnectionCriteria;
import com.canny.snowflakemigration.service.SourceConnectionQueryService;

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
 * Integration tests for the {@Link SourceConnectionResource} REST controller.
 */
@SpringBootTest(classes = SnowpoleApp.class)
public class SourceConnectionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

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
    private SourceConnectionRepository sourceConnectionRepository;

    @Autowired
    private SourceConnectionMapper sourceConnectionMapper;

    @Autowired
    private SourceConnectionService sourceConnectionService;

    @Autowired
    private SourceConnectionQueryService sourceConnectionQueryService;

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

    private MockMvc restSourceConnectionMockMvc;

    private SourceConnection sourceConnection;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SourceConnectionResource sourceConnectionResource = new SourceConnectionResource(sourceConnectionService, sourceConnectionQueryService);
        this.restSourceConnectionMockMvc = MockMvcBuilders.standaloneSetup(sourceConnectionResource)
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
    public static SourceConnection createEntity(EntityManager em) {
        SourceConnection sourceConnection = new SourceConnection()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .system(DEFAULT_SYSTEM)
            .url(DEFAULT_URL)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .database(DEFAULT_DATABASE)
            .schema(DEFAULT_SCHEMA)
            .valid(DEFAULT_VALID)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return sourceConnection;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SourceConnection createUpdatedEntity(EntityManager em) {
        SourceConnection sourceConnection = new SourceConnection()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .system(UPDATED_SYSTEM)
            .url(UPDATED_URL)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .database(UPDATED_DATABASE)
            .schema(UPDATED_SCHEMA)
            .valid(UPDATED_VALID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return sourceConnection;
    }

    @BeforeEach
    public void initTest() {
        sourceConnection = createEntity(em);
    }

    @Test
    @Transactional
    public void createSourceConnection() throws Exception {
        int databaseSizeBeforeCreate = sourceConnectionRepository.findAll().size();

        // Create the SourceConnection
        SourceConnectionDTO sourceConnectionDTO = sourceConnectionMapper.toDto(sourceConnection);
        restSourceConnectionMockMvc.perform(post("/api/source-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceConnectionDTO)))
            .andExpect(status().isCreated());

        // Validate the SourceConnection in the database
        List<SourceConnection> sourceConnectionList = sourceConnectionRepository.findAll();
        assertThat(sourceConnectionList).hasSize(databaseSizeBeforeCreate + 1);
        SourceConnection testSourceConnection = sourceConnectionList.get(sourceConnectionList.size() - 1);
        assertThat(testSourceConnection.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSourceConnection.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSourceConnection.getSystem()).isEqualTo(DEFAULT_SYSTEM);
        assertThat(testSourceConnection.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testSourceConnection.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testSourceConnection.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testSourceConnection.getDatabase()).isEqualTo(DEFAULT_DATABASE);
        assertThat(testSourceConnection.getSchema()).isEqualTo(DEFAULT_SCHEMA);
        assertThat(testSourceConnection.isValid()).isEqualTo(DEFAULT_VALID);
        assertThat(testSourceConnection.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSourceConnection.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSourceConnection.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSourceConnection.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createSourceConnectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sourceConnectionRepository.findAll().size();

        // Create the SourceConnection with an existing ID
        sourceConnection.setId(1L);
        SourceConnectionDTO sourceConnectionDTO = sourceConnectionMapper.toDto(sourceConnection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSourceConnectionMockMvc.perform(post("/api/source-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceConnectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SourceConnection in the database
        List<SourceConnection> sourceConnectionList = sourceConnectionRepository.findAll();
        assertThat(sourceConnectionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceConnectionRepository.findAll().size();
        // set the field null
        sourceConnection.setName(null);

        // Create the SourceConnection, which fails.
        SourceConnectionDTO sourceConnectionDTO = sourceConnectionMapper.toDto(sourceConnection);

        restSourceConnectionMockMvc.perform(post("/api/source-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceConnectionDTO)))
            .andExpect(status().isBadRequest());

        List<SourceConnection> sourceConnectionList = sourceConnectionRepository.findAll();
        assertThat(sourceConnectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSystemIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceConnectionRepository.findAll().size();
        // set the field null
        sourceConnection.setSystem(null);

        // Create the SourceConnection, which fails.
        SourceConnectionDTO sourceConnectionDTO = sourceConnectionMapper.toDto(sourceConnection);

        restSourceConnectionMockMvc.perform(post("/api/source-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceConnectionDTO)))
            .andExpect(status().isBadRequest());

        List<SourceConnection> sourceConnectionList = sourceConnectionRepository.findAll();
        assertThat(sourceConnectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceConnectionRepository.findAll().size();
        // set the field null
        sourceConnection.setUrl(null);

        // Create the SourceConnection, which fails.
        SourceConnectionDTO sourceConnectionDTO = sourceConnectionMapper.toDto(sourceConnection);

        restSourceConnectionMockMvc.perform(post("/api/source-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceConnectionDTO)))
            .andExpect(status().isBadRequest());

        List<SourceConnection> sourceConnectionList = sourceConnectionRepository.findAll();
        assertThat(sourceConnectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceConnectionRepository.findAll().size();
        // set the field null
        sourceConnection.setUsername(null);

        // Create the SourceConnection, which fails.
        SourceConnectionDTO sourceConnectionDTO = sourceConnectionMapper.toDto(sourceConnection);

        restSourceConnectionMockMvc.perform(post("/api/source-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceConnectionDTO)))
            .andExpect(status().isBadRequest());

        List<SourceConnection> sourceConnectionList = sourceConnectionRepository.findAll();
        assertThat(sourceConnectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceConnectionRepository.findAll().size();
        // set the field null
        sourceConnection.setPassword(null);

        // Create the SourceConnection, which fails.
        SourceConnectionDTO sourceConnectionDTO = sourceConnectionMapper.toDto(sourceConnection);

        restSourceConnectionMockMvc.perform(post("/api/source-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceConnectionDTO)))
            .andExpect(status().isBadRequest());

        List<SourceConnection> sourceConnectionList = sourceConnectionRepository.findAll();
        assertThat(sourceConnectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatabaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceConnectionRepository.findAll().size();
        // set the field null
        sourceConnection.setDatabase(null);

        // Create the SourceConnection, which fails.
        SourceConnectionDTO sourceConnectionDTO = sourceConnectionMapper.toDto(sourceConnection);

        restSourceConnectionMockMvc.perform(post("/api/source-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceConnectionDTO)))
            .andExpect(status().isBadRequest());

        List<SourceConnection> sourceConnectionList = sourceConnectionRepository.findAll();
        assertThat(sourceConnectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSourceConnections() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList
        restSourceConnectionMockMvc.perform(get("/api/source-connections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sourceConnection.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].database").value(hasItem(DEFAULT_DATABASE.toString())))
            .andExpect(jsonPath("$.[*].schema").value(hasItem(DEFAULT_SCHEMA.toString())))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getSourceConnection() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get the sourceConnection
        restSourceConnectionMockMvc.perform(get("/api/source-connections/{id}", sourceConnection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sourceConnection.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.system").value(DEFAULT_SYSTEM.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.database").value(DEFAULT_DATABASE.toString()))
            .andExpect(jsonPath("$.schema").value(DEFAULT_SCHEMA.toString()))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where name equals to DEFAULT_NAME
        defaultSourceConnectionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the sourceConnectionList where name equals to UPDATED_NAME
        defaultSourceConnectionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSourceConnectionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the sourceConnectionList where name equals to UPDATED_NAME
        defaultSourceConnectionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where name is not null
        defaultSourceConnectionShouldBeFound("name.specified=true");

        // Get all the sourceConnectionList where name is null
        defaultSourceConnectionShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where description equals to DEFAULT_DESCRIPTION
        defaultSourceConnectionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the sourceConnectionList where description equals to UPDATED_DESCRIPTION
        defaultSourceConnectionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSourceConnectionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the sourceConnectionList where description equals to UPDATED_DESCRIPTION
        defaultSourceConnectionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where description is not null
        defaultSourceConnectionShouldBeFound("description.specified=true");

        // Get all the sourceConnectionList where description is null
        defaultSourceConnectionShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsBySystemIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where system equals to DEFAULT_SYSTEM
        defaultSourceConnectionShouldBeFound("system.equals=" + DEFAULT_SYSTEM);

        // Get all the sourceConnectionList where system equals to UPDATED_SYSTEM
        defaultSourceConnectionShouldNotBeFound("system.equals=" + UPDATED_SYSTEM);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsBySystemIsInShouldWork() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where system in DEFAULT_SYSTEM or UPDATED_SYSTEM
        defaultSourceConnectionShouldBeFound("system.in=" + DEFAULT_SYSTEM + "," + UPDATED_SYSTEM);

        // Get all the sourceConnectionList where system equals to UPDATED_SYSTEM
        defaultSourceConnectionShouldNotBeFound("system.in=" + UPDATED_SYSTEM);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsBySystemIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where system is not null
        defaultSourceConnectionShouldBeFound("system.specified=true");

        // Get all the sourceConnectionList where system is null
        defaultSourceConnectionShouldNotBeFound("system.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where url equals to DEFAULT_URL
        defaultSourceConnectionShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the sourceConnectionList where url equals to UPDATED_URL
        defaultSourceConnectionShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where url in DEFAULT_URL or UPDATED_URL
        defaultSourceConnectionShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the sourceConnectionList where url equals to UPDATED_URL
        defaultSourceConnectionShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where url is not null
        defaultSourceConnectionShouldBeFound("url.specified=true");

        // Get all the sourceConnectionList where url is null
        defaultSourceConnectionShouldNotBeFound("url.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where username equals to DEFAULT_USERNAME
        defaultSourceConnectionShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the sourceConnectionList where username equals to UPDATED_USERNAME
        defaultSourceConnectionShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultSourceConnectionShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the sourceConnectionList where username equals to UPDATED_USERNAME
        defaultSourceConnectionShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where username is not null
        defaultSourceConnectionShouldBeFound("username.specified=true");

        // Get all the sourceConnectionList where username is null
        defaultSourceConnectionShouldNotBeFound("username.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where password equals to DEFAULT_PASSWORD
        defaultSourceConnectionShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the sourceConnectionList where password equals to UPDATED_PASSWORD
        defaultSourceConnectionShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultSourceConnectionShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the sourceConnectionList where password equals to UPDATED_PASSWORD
        defaultSourceConnectionShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where password is not null
        defaultSourceConnectionShouldBeFound("password.specified=true");

        // Get all the sourceConnectionList where password is null
        defaultSourceConnectionShouldNotBeFound("password.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByDatabaseIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where database equals to DEFAULT_DATABASE
        defaultSourceConnectionShouldBeFound("database.equals=" + DEFAULT_DATABASE);

        // Get all the sourceConnectionList where database equals to UPDATED_DATABASE
        defaultSourceConnectionShouldNotBeFound("database.equals=" + UPDATED_DATABASE);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByDatabaseIsInShouldWork() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where database in DEFAULT_DATABASE or UPDATED_DATABASE
        defaultSourceConnectionShouldBeFound("database.in=" + DEFAULT_DATABASE + "," + UPDATED_DATABASE);

        // Get all the sourceConnectionList where database equals to UPDATED_DATABASE
        defaultSourceConnectionShouldNotBeFound("database.in=" + UPDATED_DATABASE);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByDatabaseIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where database is not null
        defaultSourceConnectionShouldBeFound("database.specified=true");

        // Get all the sourceConnectionList where database is null
        defaultSourceConnectionShouldNotBeFound("database.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsBySchemaIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where schema equals to DEFAULT_SCHEMA
        defaultSourceConnectionShouldBeFound("schema.equals=" + DEFAULT_SCHEMA);

        // Get all the sourceConnectionList where schema equals to UPDATED_SCHEMA
        defaultSourceConnectionShouldNotBeFound("schema.equals=" + UPDATED_SCHEMA);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsBySchemaIsInShouldWork() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where schema in DEFAULT_SCHEMA or UPDATED_SCHEMA
        defaultSourceConnectionShouldBeFound("schema.in=" + DEFAULT_SCHEMA + "," + UPDATED_SCHEMA);

        // Get all the sourceConnectionList where schema equals to UPDATED_SCHEMA
        defaultSourceConnectionShouldNotBeFound("schema.in=" + UPDATED_SCHEMA);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsBySchemaIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where schema is not null
        defaultSourceConnectionShouldBeFound("schema.specified=true");

        // Get all the sourceConnectionList where schema is null
        defaultSourceConnectionShouldNotBeFound("schema.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByValidIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where valid equals to DEFAULT_VALID
        defaultSourceConnectionShouldBeFound("valid.equals=" + DEFAULT_VALID);

        // Get all the sourceConnectionList where valid equals to UPDATED_VALID
        defaultSourceConnectionShouldNotBeFound("valid.equals=" + UPDATED_VALID);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByValidIsInShouldWork() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where valid in DEFAULT_VALID or UPDATED_VALID
        defaultSourceConnectionShouldBeFound("valid.in=" + DEFAULT_VALID + "," + UPDATED_VALID);

        // Get all the sourceConnectionList where valid equals to UPDATED_VALID
        defaultSourceConnectionShouldNotBeFound("valid.in=" + UPDATED_VALID);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByValidIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where valid is not null
        defaultSourceConnectionShouldBeFound("valid.specified=true");

        // Get all the sourceConnectionList where valid is null
        defaultSourceConnectionShouldNotBeFound("valid.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where createdBy equals to DEFAULT_CREATED_BY
        defaultSourceConnectionShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the sourceConnectionList where createdBy equals to UPDATED_CREATED_BY
        defaultSourceConnectionShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSourceConnectionShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the sourceConnectionList where createdBy equals to UPDATED_CREATED_BY
        defaultSourceConnectionShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where createdBy is not null
        defaultSourceConnectionShouldBeFound("createdBy.specified=true");

        // Get all the sourceConnectionList where createdBy is null
        defaultSourceConnectionShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where createdDate equals to DEFAULT_CREATED_DATE
        defaultSourceConnectionShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the sourceConnectionList where createdDate equals to UPDATED_CREATED_DATE
        defaultSourceConnectionShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultSourceConnectionShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the sourceConnectionList where createdDate equals to UPDATED_CREATED_DATE
        defaultSourceConnectionShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where createdDate is not null
        defaultSourceConnectionShouldBeFound("createdDate.specified=true");

        // Get all the sourceConnectionList where createdDate is null
        defaultSourceConnectionShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSourceConnectionShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the sourceConnectionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSourceConnectionShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSourceConnectionShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the sourceConnectionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSourceConnectionShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where lastModifiedBy is not null
        defaultSourceConnectionShouldBeFound("lastModifiedBy.specified=true");

        // Get all the sourceConnectionList where lastModifiedBy is null
        defaultSourceConnectionShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultSourceConnectionShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the sourceConnectionList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultSourceConnectionShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultSourceConnectionShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the sourceConnectionList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultSourceConnectionShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSourceConnectionsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        // Get all the sourceConnectionList where lastModifiedDate is not null
        defaultSourceConnectionShouldBeFound("lastModifiedDate.specified=true");

        // Get all the sourceConnectionList where lastModifiedDate is null
        defaultSourceConnectionShouldNotBeFound("lastModifiedDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSourceConnectionShouldBeFound(String filter) throws Exception {
        restSourceConnectionMockMvc.perform(get("/api/source-connections?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sourceConnection.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].database").value(hasItem(DEFAULT_DATABASE)))
            .andExpect(jsonPath("$.[*].schema").value(hasItem(DEFAULT_SCHEMA)))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restSourceConnectionMockMvc.perform(get("/api/source-connections/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSourceConnectionShouldNotBeFound(String filter) throws Exception {
        restSourceConnectionMockMvc.perform(get("/api/source-connections?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSourceConnectionMockMvc.perform(get("/api/source-connections/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSourceConnection() throws Exception {
        // Get the sourceConnection
        restSourceConnectionMockMvc.perform(get("/api/source-connections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSourceConnection() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        int databaseSizeBeforeUpdate = sourceConnectionRepository.findAll().size();

        // Update the sourceConnection
        SourceConnection updatedSourceConnection = sourceConnectionRepository.findById(sourceConnection.getId()).get();
        // Disconnect from session so that the updates on updatedSourceConnection are not directly saved in db
        em.detach(updatedSourceConnection);
        updatedSourceConnection
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .system(UPDATED_SYSTEM)
            .url(UPDATED_URL)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .database(UPDATED_DATABASE)
            .schema(UPDATED_SCHEMA)
            .valid(UPDATED_VALID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        SourceConnectionDTO sourceConnectionDTO = sourceConnectionMapper.toDto(updatedSourceConnection);

        restSourceConnectionMockMvc.perform(put("/api/source-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceConnectionDTO)))
            .andExpect(status().isOk());

        // Validate the SourceConnection in the database
        List<SourceConnection> sourceConnectionList = sourceConnectionRepository.findAll();
        assertThat(sourceConnectionList).hasSize(databaseSizeBeforeUpdate);
        SourceConnection testSourceConnection = sourceConnectionList.get(sourceConnectionList.size() - 1);
        assertThat(testSourceConnection.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSourceConnection.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSourceConnection.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testSourceConnection.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testSourceConnection.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testSourceConnection.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testSourceConnection.getDatabase()).isEqualTo(UPDATED_DATABASE);
        assertThat(testSourceConnection.getSchema()).isEqualTo(UPDATED_SCHEMA);
        assertThat(testSourceConnection.isValid()).isEqualTo(UPDATED_VALID);
        assertThat(testSourceConnection.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSourceConnection.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSourceConnection.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSourceConnection.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSourceConnection() throws Exception {
        int databaseSizeBeforeUpdate = sourceConnectionRepository.findAll().size();

        // Create the SourceConnection
        SourceConnectionDTO sourceConnectionDTO = sourceConnectionMapper.toDto(sourceConnection);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSourceConnectionMockMvc.perform(put("/api/source-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceConnectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SourceConnection in the database
        List<SourceConnection> sourceConnectionList = sourceConnectionRepository.findAll();
        assertThat(sourceConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSourceConnection() throws Exception {
        // Initialize the database
        sourceConnectionRepository.saveAndFlush(sourceConnection);

        int databaseSizeBeforeDelete = sourceConnectionRepository.findAll().size();

        // Delete the sourceConnection
        restSourceConnectionMockMvc.perform(delete("/api/source-connections/{id}", sourceConnection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SourceConnection> sourceConnectionList = sourceConnectionRepository.findAll();
        assertThat(sourceConnectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SourceConnection.class);
        SourceConnection sourceConnection1 = new SourceConnection();
        sourceConnection1.setId(1L);
        SourceConnection sourceConnection2 = new SourceConnection();
        sourceConnection2.setId(sourceConnection1.getId());
        assertThat(sourceConnection1).isEqualTo(sourceConnection2);
        sourceConnection2.setId(2L);
        assertThat(sourceConnection1).isNotEqualTo(sourceConnection2);
        sourceConnection1.setId(null);
        assertThat(sourceConnection1).isNotEqualTo(sourceConnection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SourceConnectionDTO.class);
        SourceConnectionDTO sourceConnectionDTO1 = new SourceConnectionDTO();
        sourceConnectionDTO1.setId(1L);
        SourceConnectionDTO sourceConnectionDTO2 = new SourceConnectionDTO();
        assertThat(sourceConnectionDTO1).isNotEqualTo(sourceConnectionDTO2);
        sourceConnectionDTO2.setId(sourceConnectionDTO1.getId());
        assertThat(sourceConnectionDTO1).isEqualTo(sourceConnectionDTO2);
        sourceConnectionDTO2.setId(2L);
        assertThat(sourceConnectionDTO1).isNotEqualTo(sourceConnectionDTO2);
        sourceConnectionDTO1.setId(null);
        assertThat(sourceConnectionDTO1).isNotEqualTo(sourceConnectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sourceConnectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sourceConnectionMapper.fromId(null)).isNull();
    }
}
