package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.SnowpoleApp;
import com.canny.snowflakemigration.domain.MigrationProcess;
import com.canny.snowflakemigration.domain.SourceConnection;
import com.canny.snowflakemigration.domain.SnowflakeConnection;
import com.canny.snowflakemigration.repository.MigrationProcessRepository;
import com.canny.snowflakemigration.service.MigrationProcessService;
import com.canny.snowflakemigration.service.dto.MigrationProcessDTO;
import com.canny.snowflakemigration.service.mapper.MigrationProcessMapper;
import com.canny.snowflakemigration.web.rest.errors.ExceptionTranslator;
import com.canny.snowflakemigration.service.dto.MigrationProcessCriteria;
import com.canny.snowflakemigration.service.MigrationProcessQueryService;

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
 * Integration tests for the {@Link MigrationProcessResource} REST controller.
 */
@SpringBootTest(classes = SnowpoleApp.class)
public class MigrationProcessResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TABLES_TO_MIGRATE = "AAAAAAAAAA";
    private static final String UPDATED_TABLES_TO_MIGRATE = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_LAST_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MigrationProcessRepository migrationProcessRepository;

    @Autowired
    private MigrationProcessMapper migrationProcessMapper;

    @Autowired
    private MigrationProcessService migrationProcessService;

    @Autowired
    private MigrationProcessQueryService migrationProcessQueryService;

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

    private MockMvc restMigrationProcessMockMvc;

    private MigrationProcess migrationProcess;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MigrationProcessResource migrationProcessResource = new MigrationProcessResource(migrationProcessService, migrationProcessQueryService);
        this.restMigrationProcessMockMvc = MockMvcBuilders.standaloneSetup(migrationProcessResource)
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
    public static MigrationProcess createEntity(EntityManager em) {
        MigrationProcess migrationProcess = new MigrationProcess()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .tablesToMigrate(DEFAULT_TABLES_TO_MIGRATE)
            .lastStatus(DEFAULT_LAST_STATUS);
            // .createdBy(DEFAULT_CREATED_BY)
            // .createdDate(DEFAULT_CREATED_DATE)
            // .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            // .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        // Add required entity
        SourceConnection sourceConnection;
        if (TestUtil.findAll(em, SourceConnection.class).isEmpty()) {
            sourceConnection = SourceConnectionResourceIT.createEntity(em);
            em.persist(sourceConnection);
            em.flush();
        } else {
            sourceConnection = TestUtil.findAll(em, SourceConnection.class).get(0);
        }
        migrationProcess.setSourceConnection(sourceConnection);
        // Add required entity
        SnowflakeConnection snowflakeConnection;
        if (TestUtil.findAll(em, SnowflakeConnection.class).isEmpty()) {
            snowflakeConnection = SnowflakeConnectionResourceIT.createEntity(em);
            em.persist(snowflakeConnection);
            em.flush();
        } else {
            snowflakeConnection = TestUtil.findAll(em, SnowflakeConnection.class).get(0);
        }
        migrationProcess.setSnowflakeConnection(snowflakeConnection);
        return migrationProcess;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MigrationProcess createUpdatedEntity(EntityManager em) {
        MigrationProcess migrationProcess = new MigrationProcess()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .tablesToMigrate(UPDATED_TABLES_TO_MIGRATE)
            .lastStatus(UPDATED_LAST_STATUS);
            // .createdBy(UPDATED_CREATED_BY)
            // .createdDate(UPDATED_CREATED_DATE)
            // .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            // .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        // Add required entity
        SourceConnection sourceConnection;
        if (TestUtil.findAll(em, SourceConnection.class).isEmpty()) {
            sourceConnection = SourceConnectionResourceIT.createUpdatedEntity(em);
            em.persist(sourceConnection);
            em.flush();
        } else {
            sourceConnection = TestUtil.findAll(em, SourceConnection.class).get(0);
        }
        migrationProcess.setSourceConnection(sourceConnection);
        // Add required entity
        SnowflakeConnection snowflakeConnection;
        if (TestUtil.findAll(em, SnowflakeConnection.class).isEmpty()) {
            snowflakeConnection = SnowflakeConnectionResourceIT.createUpdatedEntity(em);
            em.persist(snowflakeConnection);
            em.flush();
        } else {
            snowflakeConnection = TestUtil.findAll(em, SnowflakeConnection.class).get(0);
        }
        migrationProcess.setSnowflakeConnection(snowflakeConnection);
        return migrationProcess;
    }

    @BeforeEach
    public void initTest() {
        migrationProcess = createEntity(em);
    }

    @Test
    @Transactional
    public void createMigrationProcess() throws Exception {
        int databaseSizeBeforeCreate = migrationProcessRepository.findAll().size();

        // Create the MigrationProcess
        MigrationProcessDTO migrationProcessDTO = migrationProcessMapper.toDto(migrationProcess);
        restMigrationProcessMockMvc.perform(post("/api/migration-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(migrationProcessDTO)))
            .andExpect(status().isCreated());

        // Validate the MigrationProcess in the database
        List<MigrationProcess> migrationProcessList = migrationProcessRepository.findAll();
        assertThat(migrationProcessList).hasSize(databaseSizeBeforeCreate + 1);
        MigrationProcess testMigrationProcess = migrationProcessList.get(migrationProcessList.size() - 1);
        assertThat(testMigrationProcess.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMigrationProcess.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMigrationProcess.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMigrationProcess.getTablesToMigrate()).isEqualTo(DEFAULT_TABLES_TO_MIGRATE);
        assertThat(testMigrationProcess.getLastStatus()).isEqualTo(DEFAULT_LAST_STATUS);
        // assertThat(testMigrationProcess.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        // assertThat(testMigrationProcess.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        // assertThat(testMigrationProcess.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        // assertThat(testMigrationProcess.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createMigrationProcessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = migrationProcessRepository.findAll().size();

        // Create the MigrationProcess with an existing ID
        migrationProcess.setId(1L);
        MigrationProcessDTO migrationProcessDTO = migrationProcessMapper.toDto(migrationProcess);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMigrationProcessMockMvc.perform(post("/api/migration-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(migrationProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MigrationProcess in the database
        List<MigrationProcess> migrationProcessList = migrationProcessRepository.findAll();
        assertThat(migrationProcessList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = migrationProcessRepository.findAll().size();
        // set the field null
        migrationProcess.setName(null);

        // Create the MigrationProcess, which fails.
        MigrationProcessDTO migrationProcessDTO = migrationProcessMapper.toDto(migrationProcess);

        restMigrationProcessMockMvc.perform(post("/api/migration-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(migrationProcessDTO)))
            .andExpect(status().isBadRequest());

        List<MigrationProcess> migrationProcessList = migrationProcessRepository.findAll();
        assertThat(migrationProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMigrationProcesses() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        // Get all the migrationProcessList
        restMigrationProcessMockMvc.perform(get("/api/migration-processes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(migrationProcess.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].tablesToMigrate").value(hasItem(DEFAULT_TABLES_TO_MIGRATE.toString())))
            .andExpect(jsonPath("$.[*].lastStatus").value(hasItem(DEFAULT_LAST_STATUS.toString())));
            // .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            // .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            // .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            // .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getMigrationProcess() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        // Get the migrationProcess
        restMigrationProcessMockMvc.perform(get("/api/migration-processes/{id}", migrationProcess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(migrationProcess.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.tablesToMigrate").value(DEFAULT_TABLES_TO_MIGRATE.toString()))
            .andExpect(jsonPath("$.lastStatus").value(DEFAULT_LAST_STATUS.toString()));
            // .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            // .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            // .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            // .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllMigrationProcessesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        // Get all the migrationProcessList where name equals to DEFAULT_NAME
        defaultMigrationProcessShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the migrationProcessList where name equals to UPDATED_NAME
        defaultMigrationProcessShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMigrationProcessesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        // Get all the migrationProcessList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMigrationProcessShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the migrationProcessList where name equals to UPDATED_NAME
        defaultMigrationProcessShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMigrationProcessesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        // Get all the migrationProcessList where name is not null
        defaultMigrationProcessShouldBeFound("name.specified=true");

        // Get all the migrationProcessList where name is null
        defaultMigrationProcessShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllMigrationProcessesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        // Get all the migrationProcessList where description equals to DEFAULT_DESCRIPTION
        defaultMigrationProcessShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the migrationProcessList where description equals to UPDATED_DESCRIPTION
        defaultMigrationProcessShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMigrationProcessesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        // Get all the migrationProcessList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMigrationProcessShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the migrationProcessList where description equals to UPDATED_DESCRIPTION
        defaultMigrationProcessShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMigrationProcessesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        // Get all the migrationProcessList where description is not null
        defaultMigrationProcessShouldBeFound("description.specified=true");

        // Get all the migrationProcessList where description is null
        defaultMigrationProcessShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllMigrationProcessesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        // Get all the migrationProcessList where type equals to DEFAULT_TYPE
        defaultMigrationProcessShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the migrationProcessList where type equals to UPDATED_TYPE
        defaultMigrationProcessShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMigrationProcessesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        // Get all the migrationProcessList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultMigrationProcessShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the migrationProcessList where type equals to UPDATED_TYPE
        defaultMigrationProcessShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMigrationProcessesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        // Get all the migrationProcessList where type is not null
        defaultMigrationProcessShouldBeFound("type.specified=true");

        // Get all the migrationProcessList where type is null
        defaultMigrationProcessShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllMigrationProcessesByTablesToMigrateIsEqualToSomething() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        // Get all the migrationProcessList where tablesToMigrate equals to DEFAULT_TABLES_TO_MIGRATE
        defaultMigrationProcessShouldBeFound("tablesToMigrate.equals=" + DEFAULT_TABLES_TO_MIGRATE);

        // Get all the migrationProcessList where tablesToMigrate equals to UPDATED_TABLES_TO_MIGRATE
        defaultMigrationProcessShouldNotBeFound("tablesToMigrate.equals=" + UPDATED_TABLES_TO_MIGRATE);
    }

    @Test
    @Transactional
    public void getAllMigrationProcessesByTablesToMigrateIsInShouldWork() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        // Get all the migrationProcessList where tablesToMigrate in DEFAULT_TABLES_TO_MIGRATE or UPDATED_TABLES_TO_MIGRATE
        defaultMigrationProcessShouldBeFound("tablesToMigrate.in=" + DEFAULT_TABLES_TO_MIGRATE + "," + UPDATED_TABLES_TO_MIGRATE);

        // Get all the migrationProcessList where tablesToMigrate equals to UPDATED_TABLES_TO_MIGRATE
        defaultMigrationProcessShouldNotBeFound("tablesToMigrate.in=" + UPDATED_TABLES_TO_MIGRATE);
    }

    @Test
    @Transactional
    public void getAllMigrationProcessesByTablesToMigrateIsNullOrNotNull() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        // Get all the migrationProcessList where tablesToMigrate is not null
        defaultMigrationProcessShouldBeFound("tablesToMigrate.specified=true");

        // Get all the migrationProcessList where tablesToMigrate is null
        defaultMigrationProcessShouldNotBeFound("tablesToMigrate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMigrationProcessesByLastStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        // Get all the migrationProcessList where lastStatus equals to DEFAULT_LAST_STATUS
        defaultMigrationProcessShouldBeFound("lastStatus.equals=" + DEFAULT_LAST_STATUS);

        // Get all the migrationProcessList where lastStatus equals to UPDATED_LAST_STATUS
        defaultMigrationProcessShouldNotBeFound("lastStatus.equals=" + UPDATED_LAST_STATUS);
    }

    @Test
    @Transactional
    public void getAllMigrationProcessesByLastStatusIsInShouldWork() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        // Get all the migrationProcessList where lastStatus in DEFAULT_LAST_STATUS or UPDATED_LAST_STATUS
        defaultMigrationProcessShouldBeFound("lastStatus.in=" + DEFAULT_LAST_STATUS + "," + UPDATED_LAST_STATUS);

        // Get all the migrationProcessList where lastStatus equals to UPDATED_LAST_STATUS
        defaultMigrationProcessShouldNotBeFound("lastStatus.in=" + UPDATED_LAST_STATUS);
    }

    @Test
    @Transactional
    public void getAllMigrationProcessesByLastStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        // Get all the migrationProcessList where lastStatus is not null
        defaultMigrationProcessShouldBeFound("lastStatus.specified=true");

        // Get all the migrationProcessList where lastStatus is null
        defaultMigrationProcessShouldNotBeFound("lastStatus.specified=false");
    }

    // @Test
    // @Transactional
    // public void getAllMigrationProcessesByCreatedByIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     migrationProcessRepository.saveAndFlush(migrationProcess);

    //     // Get all the migrationProcessList where createdBy equals to DEFAULT_CREATED_BY
    //     defaultMigrationProcessShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

    //     // Get all the migrationProcessList where createdBy equals to UPDATED_CREATED_BY
    //     defaultMigrationProcessShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    // }

    // @Test
    // @Transactional
    // public void getAllMigrationProcessesByCreatedByIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     migrationProcessRepository.saveAndFlush(migrationProcess);

    //     // Get all the migrationProcessList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
    //     defaultMigrationProcessShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

    //     // Get all the migrationProcessList where createdBy equals to UPDATED_CREATED_BY
    //     defaultMigrationProcessShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    // }

    // @Test
    // @Transactional
    // public void getAllMigrationProcessesByCreatedByIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     migrationProcessRepository.saveAndFlush(migrationProcess);

    //     // Get all the migrationProcessList where createdBy is not null
    //     defaultMigrationProcessShouldBeFound("createdBy.specified=true");

    //     // Get all the migrationProcessList where createdBy is null
    //     defaultMigrationProcessShouldNotBeFound("createdBy.specified=false");
    // }

    // @Test
    // @Transactional
    // public void getAllMigrationProcessesByCreatedDateIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     migrationProcessRepository.saveAndFlush(migrationProcess);

    //     // Get all the migrationProcessList where createdDate equals to DEFAULT_CREATED_DATE
    //     defaultMigrationProcessShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

    //     // Get all the migrationProcessList where createdDate equals to UPDATED_CREATED_DATE
    //     defaultMigrationProcessShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    // }

    // @Test
    // @Transactional
    // public void getAllMigrationProcessesByCreatedDateIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     migrationProcessRepository.saveAndFlush(migrationProcess);

    //     // Get all the migrationProcessList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
    //     defaultMigrationProcessShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

    //     // Get all the migrationProcessList where createdDate equals to UPDATED_CREATED_DATE
    //     defaultMigrationProcessShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    // }

    // @Test
    // @Transactional
    // public void getAllMigrationProcessesByCreatedDateIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     migrationProcessRepository.saveAndFlush(migrationProcess);

    //     // Get all the migrationProcessList where createdDate is not null
    //     defaultMigrationProcessShouldBeFound("createdDate.specified=true");

    //     // Get all the migrationProcessList where createdDate is null
    //     defaultMigrationProcessShouldNotBeFound("createdDate.specified=false");
    // }

    // @Test
    // @Transactional
    // public void getAllMigrationProcessesByLastModifiedByIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     migrationProcessRepository.saveAndFlush(migrationProcess);

    //     // Get all the migrationProcessList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
    //     defaultMigrationProcessShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

    //     // Get all the migrationProcessList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
    //     defaultMigrationProcessShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    // }

    // @Test
    // @Transactional
    // public void getAllMigrationProcessesByLastModifiedByIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     migrationProcessRepository.saveAndFlush(migrationProcess);

    //     // Get all the migrationProcessList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
    //     defaultMigrationProcessShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

    //     // Get all the migrationProcessList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
    //     defaultMigrationProcessShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    // }

    // @Test
    // @Transactional
    // public void getAllMigrationProcessesByLastModifiedByIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     migrationProcessRepository.saveAndFlush(migrationProcess);

    //     // Get all the migrationProcessList where lastModifiedBy is not null
    //     defaultMigrationProcessShouldBeFound("lastModifiedBy.specified=true");

    //     // Get all the migrationProcessList where lastModifiedBy is null
    //     defaultMigrationProcessShouldNotBeFound("lastModifiedBy.specified=false");
    // }

    // @Test
    // @Transactional
    // public void getAllMigrationProcessesByLastModifiedDateIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     migrationProcessRepository.saveAndFlush(migrationProcess);

    //     // Get all the migrationProcessList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
    //     defaultMigrationProcessShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

    //     // Get all the migrationProcessList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
    //     defaultMigrationProcessShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    // }

    // @Test
    // @Transactional
    // public void getAllMigrationProcessesByLastModifiedDateIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     migrationProcessRepository.saveAndFlush(migrationProcess);

    //     // Get all the migrationProcessList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
    //     defaultMigrationProcessShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

    //     // Get all the migrationProcessList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
    //     defaultMigrationProcessShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    // }

    // @Test
    // @Transactional
    // public void getAllMigrationProcessesByLastModifiedDateIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     migrationProcessRepository.saveAndFlush(migrationProcess);

    //     // Get all the migrationProcessList where lastModifiedDate is not null
    //     defaultMigrationProcessShouldBeFound("lastModifiedDate.specified=true");

    //     // Get all the migrationProcessList where lastModifiedDate is null
    //     defaultMigrationProcessShouldNotBeFound("lastModifiedDate.specified=false");
    // }

    @Test
    @Transactional
    public void getAllMigrationProcessesBySourceConnectionIsEqualToSomething() throws Exception {
        // Get already existing entity
        SourceConnection sourceConnection = migrationProcess.getSourceConnection();
        migrationProcessRepository.saveAndFlush(migrationProcess);
        Long sourceConnectionId = sourceConnection.getId();

        // Get all the migrationProcessList where sourceConnection equals to sourceConnectionId
        defaultMigrationProcessShouldBeFound("sourceConnectionId.equals=" + sourceConnectionId);

        // Get all the migrationProcessList where sourceConnection equals to sourceConnectionId + 1
        defaultMigrationProcessShouldNotBeFound("sourceConnectionId.equals=" + (sourceConnectionId + 1));
    }


    @Test
    @Transactional
    public void getAllMigrationProcessesBySnowflakeConnectionIsEqualToSomething() throws Exception {
        // Get already existing entity
        SnowflakeConnection snowflakeConnection = migrationProcess.getSnowflakeConnection();
        migrationProcessRepository.saveAndFlush(migrationProcess);
        Long snowflakeConnectionId = snowflakeConnection.getId();

        // Get all the migrationProcessList where snowflakeConnection equals to snowflakeConnectionId
        defaultMigrationProcessShouldBeFound("snowflakeConnectionId.equals=" + snowflakeConnectionId);

        // Get all the migrationProcessList where snowflakeConnection equals to snowflakeConnectionId + 1
        defaultMigrationProcessShouldNotBeFound("snowflakeConnectionId.equals=" + (snowflakeConnectionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMigrationProcessShouldBeFound(String filter) throws Exception {
        restMigrationProcessMockMvc.perform(get("/api/migration-processes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(migrationProcess.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].tablesToMigrate").value(hasItem(DEFAULT_TABLES_TO_MIGRATE)))
            .andExpect(jsonPath("$.[*].lastStatus").value(hasItem(DEFAULT_LAST_STATUS)));
            // .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            // .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            // .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            // .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restMigrationProcessMockMvc.perform(get("/api/migration-processes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMigrationProcessShouldNotBeFound(String filter) throws Exception {
        restMigrationProcessMockMvc.perform(get("/api/migration-processes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMigrationProcessMockMvc.perform(get("/api/migration-processes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMigrationProcess() throws Exception {
        // Get the migrationProcess
        restMigrationProcessMockMvc.perform(get("/api/migration-processes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMigrationProcess() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        int databaseSizeBeforeUpdate = migrationProcessRepository.findAll().size();

        // Update the migrationProcess
        MigrationProcess updatedMigrationProcess = migrationProcessRepository.findById(migrationProcess.getId()).get();
        // Disconnect from session so that the updates on updatedMigrationProcess are not directly saved in db
        em.detach(updatedMigrationProcess);
        updatedMigrationProcess
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .tablesToMigrate(UPDATED_TABLES_TO_MIGRATE)
            .lastStatus(UPDATED_LAST_STATUS);
            // .createdBy(UPDATED_CREATED_BY)
            // .createdDate(UPDATED_CREATED_DATE)
            // .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            // .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        MigrationProcessDTO migrationProcessDTO = migrationProcessMapper.toDto(updatedMigrationProcess);

        restMigrationProcessMockMvc.perform(put("/api/migration-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(migrationProcessDTO)))
            .andExpect(status().isOk());

        // Validate the MigrationProcess in the database
        List<MigrationProcess> migrationProcessList = migrationProcessRepository.findAll();
        assertThat(migrationProcessList).hasSize(databaseSizeBeforeUpdate);
        MigrationProcess testMigrationProcess = migrationProcessList.get(migrationProcessList.size() - 1);
        assertThat(testMigrationProcess.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMigrationProcess.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMigrationProcess.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMigrationProcess.getTablesToMigrate()).isEqualTo(UPDATED_TABLES_TO_MIGRATE);
        assertThat(testMigrationProcess.getLastStatus()).isEqualTo(UPDATED_LAST_STATUS);
        // assertThat(testMigrationProcess.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        // assertThat(testMigrationProcess.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        // assertThat(testMigrationProcess.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        // assertThat(testMigrationProcess.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMigrationProcess() throws Exception {
        int databaseSizeBeforeUpdate = migrationProcessRepository.findAll().size();

        // Create the MigrationProcess
        MigrationProcessDTO migrationProcessDTO = migrationProcessMapper.toDto(migrationProcess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMigrationProcessMockMvc.perform(put("/api/migration-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(migrationProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MigrationProcess in the database
        List<MigrationProcess> migrationProcessList = migrationProcessRepository.findAll();
        assertThat(migrationProcessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMigrationProcess() throws Exception {
        // Initialize the database
        migrationProcessRepository.saveAndFlush(migrationProcess);

        int databaseSizeBeforeDelete = migrationProcessRepository.findAll().size();

        // Delete the migrationProcess
        restMigrationProcessMockMvc.perform(delete("/api/migration-processes/{id}", migrationProcess.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MigrationProcess> migrationProcessList = migrationProcessRepository.findAll();
        assertThat(migrationProcessList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MigrationProcess.class);
        MigrationProcess migrationProcess1 = new MigrationProcess();
        migrationProcess1.setId(1L);
        MigrationProcess migrationProcess2 = new MigrationProcess();
        migrationProcess2.setId(migrationProcess1.getId());
        assertThat(migrationProcess1).isEqualTo(migrationProcess2);
        migrationProcess2.setId(2L);
        assertThat(migrationProcess1).isNotEqualTo(migrationProcess2);
        migrationProcess1.setId(null);
        assertThat(migrationProcess1).isNotEqualTo(migrationProcess2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MigrationProcessDTO.class);
        MigrationProcessDTO migrationProcessDTO1 = new MigrationProcessDTO();
        migrationProcessDTO1.setId(1L);
        MigrationProcessDTO migrationProcessDTO2 = new MigrationProcessDTO();
        assertThat(migrationProcessDTO1).isNotEqualTo(migrationProcessDTO2);
        migrationProcessDTO2.setId(migrationProcessDTO1.getId());
        assertThat(migrationProcessDTO1).isEqualTo(migrationProcessDTO2);
        migrationProcessDTO2.setId(2L);
        assertThat(migrationProcessDTO1).isNotEqualTo(migrationProcessDTO2);
        migrationProcessDTO1.setId(null);
        assertThat(migrationProcessDTO1).isNotEqualTo(migrationProcessDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(migrationProcessMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(migrationProcessMapper.fromId(null)).isNull();
    }
}
