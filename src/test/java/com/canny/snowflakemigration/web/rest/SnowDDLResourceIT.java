package com.canny.snowflakemigration.web.rest;

import com.canny.snowflakemigration.SnowpoleApp;
import com.canny.snowflakemigration.domain.SnowDDL;
import com.canny.snowflakemigration.domain.SourceConnection;
import com.canny.snowflakemigration.domain.SnowflakeConnection;
import com.canny.snowflakemigration.repository.SnowDDLRepository;
import com.canny.snowflakemigration.service.SnowDDLService;
import com.canny.snowflakemigration.service.dto.SnowDDLDTO;
import com.canny.snowflakemigration.service.mapper.SnowDDLMapper;
import com.canny.snowflakemigration.web.rest.errors.ExceptionTranslator;
import com.canny.snowflakemigration.service.dto.SnowDDLCriteria;
import com.canny.snowflakemigration.service.SnowDDLJobStatusService;
import com.canny.snowflakemigration.service.SnowDDLProcessStatusService;
import com.canny.snowflakemigration.service.SnowDDLQueryService;

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
 * Integration tests for the {@Link SnowDDLResource} REST controller.
 */
@SpringBootTest(classes = SnowpoleApp.class)
public class SnowDDLResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_PATH = "BBBBBBBBBB";

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
    private SnowDDLRepository SnowDDLRepository;

    @Autowired
    private SnowDDLMapper SnowDDLMapper;

    @Autowired
    private SnowDDLService SnowDDLService;

    @Autowired
    private SnowDDLQueryService SnowDDLQueryService;

    @Autowired
    private SnowDDLProcessStatusService snowDDLProcessStatusService;

    @Autowired
    private SnowDDLJobStatusService snowDDLJobStatusService;

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

    private MockMvc restSnowDDLMockMvc;

    private SnowDDL SnowDDL;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SnowDDLResource SnowDDLResource = new SnowDDLResource(SnowDDLService, SnowDDLQueryService, snowDDLProcessStatusService, snowDDLJobStatusService);
        this.restSnowDDLMockMvc = MockMvcBuilders.standaloneSetup(SnowDDLResource)
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
    public static SnowDDL createEntity(EntityManager em) {
        SnowDDL SnowDDL = new SnowDDL()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .lastStatus(DEFAULT_LAST_STATUS)
            .sourcePath(DEFAULT_SOURCE_PATH);
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
        SnowDDL.setSourceConnection(sourceConnection);
        // Add required entity
        SnowflakeConnection snowflakeConnection;
        if (TestUtil.findAll(em, SnowflakeConnection.class).isEmpty()) {
            snowflakeConnection = SnowflakeConnectionResourceIT.createEntity(em);
            em.persist(snowflakeConnection);
            em.flush();
        } else {
            snowflakeConnection = TestUtil.findAll(em, SnowflakeConnection.class).get(0);
        }
        SnowDDL.setSnowflakeConnection(snowflakeConnection);
        return SnowDDL;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SnowDDL createUpdatedEntity(EntityManager em) {
        SnowDDL SnowDDL = new SnowDDL()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .lastStatus(UPDATED_LAST_STATUS)
            .sourcePath(UPDATED_SOURCE_PATH);
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
        SnowDDL.setSourceConnection(sourceConnection);
        // Add required entity
        SnowflakeConnection snowflakeConnection;
        if (TestUtil.findAll(em, SnowflakeConnection.class).isEmpty()) {
            snowflakeConnection = SnowflakeConnectionResourceIT.createUpdatedEntity(em);
            em.persist(snowflakeConnection);
            em.flush();
        } else {
            snowflakeConnection = TestUtil.findAll(em, SnowflakeConnection.class).get(0);
        }
        SnowDDL.setSnowflakeConnection(snowflakeConnection);
        return SnowDDL;
    }

    @BeforeEach
    public void initTest() {
        SnowDDL = createEntity(em);
    }

    @Test
    @Transactional
    public void createSnowDDL() throws Exception {
        int databaseSizeBeforeCreate = SnowDDLRepository.findAll().size();

        // Create the SnowDDL
        SnowDDLDTO SnowDDLDTO = SnowDDLMapper.toDto(SnowDDL);
        restSnowDDLMockMvc.perform(post("/api/snow-ddl")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(SnowDDLDTO)))
            .andExpect(status().isCreated());

        // Validate the SnowDDL in the database
        List<SnowDDL> SnowDDLList = SnowDDLRepository.findAll();
        assertThat(SnowDDLList).hasSize(databaseSizeBeforeCreate + 1);
        SnowDDL testSnowDDL = SnowDDLList.get(SnowDDLList.size() - 1);
        assertThat(testSnowDDL.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSnowDDL.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSnowDDL.getSourcePath()).isEqualTo(DEFAULT_SOURCE_PATH);
        assertThat(testSnowDDL.getLastStatus()).isEqualTo(DEFAULT_LAST_STATUS);
        // assertThat(testSnowDDL.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        // assertThat(testSnowDDL.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        // assertThat(testSnowDDL.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        // assertThat(testSnowDDL.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createSnowDDLWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = SnowDDLRepository.findAll().size();

        // Create the SnowDDL with an existing ID
        SnowDDL.setId(1L);
        SnowDDLDTO SnowDDLDTO = SnowDDLMapper.toDto(SnowDDL);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSnowDDLMockMvc.perform(post("/api/snow-ddl")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(SnowDDLDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SnowDDL in the database
        List<SnowDDL> SnowDDLList = SnowDDLRepository.findAll();
        assertThat(SnowDDLList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = SnowDDLRepository.findAll().size();
        // set the field null
        SnowDDL.setName(null);

        // Create the SnowDDL, which fails.
        SnowDDLDTO SnowDDLDTO = SnowDDLMapper.toDto(SnowDDL);

        restSnowDDLMockMvc.perform(post("/api/snow-ddl")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(SnowDDLDTO)))
            .andExpect(status().isBadRequest());

        List<SnowDDL> SnowDDLList = SnowDDLRepository.findAll();
        assertThat(SnowDDLList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSnowDDLes() throws Exception {
        // Initialize the database
        SnowDDLRepository.saveAndFlush(SnowDDL);

        // Get all the SnowDDLList
        restSnowDDLMockMvc.perform(get("/api/snow-ddl?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(SnowDDL.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].lastStatus").value(hasItem(DEFAULT_LAST_STATUS.toString())))
            .andExpect(jsonPath("$.[*].sourcePath").value(hasItem(DEFAULT_SOURCE_PATH.toString())));
            // .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            // .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            // .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getSnowDDL() throws Exception {
        // Initialize the database
        SnowDDLRepository.saveAndFlush(SnowDDL);

        // Get the SnowDDL
        restSnowDDLMockMvc.perform(get("/api/snow-ddl/{id}", SnowDDL.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(SnowDDL.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.lastStatus").value(DEFAULT_LAST_STATUS.toString()))
            .andExpect(jsonPath("$.sourcePath").value(DEFAULT_SOURCE_PATH.toString()));
            // .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            // .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            // .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllSnowDDLesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        SnowDDLRepository.saveAndFlush(SnowDDL);

        // Get all the SnowDDLList where name equals to DEFAULT_NAME
        defaultSnowDDLShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the SnowDDLList where name equals to UPDATED_NAME
        defaultSnowDDLShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSnowDDLesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        SnowDDLRepository.saveAndFlush(SnowDDL);

        // Get all the SnowDDLList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSnowDDLShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the SnowDDLList where name equals to UPDATED_NAME
        defaultSnowDDLShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSnowDDLesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        SnowDDLRepository.saveAndFlush(SnowDDL);

        // Get all the SnowDDLList where name is not null
        defaultSnowDDLShouldBeFound("name.specified=true");

        // Get all the SnowDDLList where name is null
        defaultSnowDDLShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllSnowDDLesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        SnowDDLRepository.saveAndFlush(SnowDDL);

        // Get all the SnowDDLList where description equals to DEFAULT_DESCRIPTION
        defaultSnowDDLShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the SnowDDLList where description equals to UPDATED_DESCRIPTION
        defaultSnowDDLShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSnowDDLesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        SnowDDLRepository.saveAndFlush(SnowDDL);

        // Get all the SnowDDLList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSnowDDLShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the SnowDDLList where description equals to UPDATED_DESCRIPTION
        defaultSnowDDLShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSnowDDLesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        SnowDDLRepository.saveAndFlush(SnowDDL);

        // Get all the SnowDDLList where description is not null
        defaultSnowDDLShouldBeFound("description.specified=true");

        // Get all the SnowDDLList where description is null
        defaultSnowDDLShouldNotBeFound("description.specified=false");
    }
    
    // @Test
    // @Transactional
    // public void getAllSnowDDLesBySourcePathIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     SnowDDLRepository.saveAndFlush(SnowDDL);

    //     // Get all the SnowDDLList where sourcePath equals to DEFAULT_SOURCE_PATH
    //     defaultSnowDDLShouldBeFound("sourcePath.equals=" + DEFAULT_SOURCE_PATH);

    //     // Get all the SnowDDLList where sourcePath equals to UPDATED_SOURCE_PATH
    //     defaultSnowDDLShouldNotBeFound("sourcePath.equals=" + UPDATED_SOURCE_PATH);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowDDLesBySourcePathIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     SnowDDLRepository.saveAndFlush(SnowDDL);

    //     // Get all the SnowDDLList where sourcePath in DEFAULT_SOURCE_PATH or UPDATED_SOURCE_PATH
    //     defaultSnowDDLShouldBeFound("sourcePath.in=" + DEFAULT_SOURCE_PATH + "," + UPDATED_SOURCE_PATH);

    //     // Get all the SnowDDLList where sourcePath equals to UPDATED_SOURCE_PATH
    //     defaultSnowDDLShouldNotBeFound("sourcePath.in=" + UPDATED_SOURCE_PATH);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowDDLesBySourcePathIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     SnowDDLRepository.saveAndFlush(SnowDDL);

    //     // Get all the SnowDDLList where sourcePath is not null
    //     defaultSnowDDLShouldBeFound("sourcePath.specified=true");

    //     // Get all the SnowDDLList where sourcePath is null
    //     defaultSnowDDLShouldNotBeFound("sourcePath.specified=false");
    // }

    @Test
    @Transactional
    public void getAllSnowDDLesByLastStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        SnowDDLRepository.saveAndFlush(SnowDDL);

        // Get all the SnowDDLList where lastStatus equals to DEFAULT_LAST_STATUS
        defaultSnowDDLShouldBeFound("lastStatus.equals=" + DEFAULT_LAST_STATUS);

        // Get all the SnowDDLList where lastStatus equals to UPDATED_LAST_STATUS
        defaultSnowDDLShouldNotBeFound("lastStatus.equals=" + UPDATED_LAST_STATUS);
    }

    @Test
    @Transactional
    public void getAllSnowDDLesByLastStatusIsInShouldWork() throws Exception {
        // Initialize the database
        SnowDDLRepository.saveAndFlush(SnowDDL);

        // Get all the SnowDDLList where lastStatus in DEFAULT_LAST_STATUS or UPDATED_LAST_STATUS
        defaultSnowDDLShouldBeFound("lastStatus.in=" + DEFAULT_LAST_STATUS + "," + UPDATED_LAST_STATUS);

        // Get all the SnowDDLList where lastStatus equals to UPDATED_LAST_STATUS
        defaultSnowDDLShouldNotBeFound("lastStatus.in=" + UPDATED_LAST_STATUS);
    }

    @Test
    @Transactional
    public void getAllSnowDDLesByLastStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        SnowDDLRepository.saveAndFlush(SnowDDL);

        // Get all the SnowDDLList where lastStatus is not null
        defaultSnowDDLShouldBeFound("lastStatus.specified=true");

        // Get all the SnowDDLList where lastStatus is null
        defaultSnowDDLShouldNotBeFound("lastStatus.specified=false");
    }

    // @Test
    // @Transactional
    // public void getAllSnowDDLesByCreatedByIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     SnowDDLRepository.saveAndFlush(SnowDDL);

    //     // Get all the SnowDDLList where createdBy equals to DEFAULT_CREATED_BY
    //     defaultSnowDDLShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

    //     // Get all the SnowDDLList where createdBy equals to UPDATED_CREATED_BY
    //     defaultSnowDDLShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowDDLesByCreatedByIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     SnowDDLRepository.saveAndFlush(SnowDDL);

    //     // Get all the SnowDDLList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
    //     defaultSnowDDLShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

    //     // Get all the SnowDDLList where createdBy equals to UPDATED_CREATED_BY
    //     defaultSnowDDLShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowDDLesByCreatedByIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     SnowDDLRepository.saveAndFlush(SnowDDL);

    //     // Get all the SnowDDLList where createdBy is not null
    //     defaultSnowDDLShouldBeFound("createdBy.specified=true");

    //     // Get all the SnowDDLList where createdBy is null
    //     defaultSnowDDLShouldNotBeFound("createdBy.specified=false");
    // }

    // @Test
    // @Transactional
    // public void getAllSnowDDLesByCreatedDateIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     SnowDDLRepository.saveAndFlush(SnowDDL);

    //     // Get all the SnowDDLList where createdDate equals to DEFAULT_CREATED_DATE
    //     defaultSnowDDLShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

    //     // Get all the SnowDDLList where createdDate equals to UPDATED_CREATED_DATE
    //     defaultSnowDDLShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowDDLesByCreatedDateIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     SnowDDLRepository.saveAndFlush(SnowDDL);

    //     // Get all the SnowDDLList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
    //     defaultSnowDDLShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

    //     // Get all the SnowDDLList where createdDate equals to UPDATED_CREATED_DATE
    //     defaultSnowDDLShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowDDLesByCreatedDateIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     SnowDDLRepository.saveAndFlush(SnowDDL);

    //     // Get all the SnowDDLList where createdDate is not null
    //     defaultSnowDDLShouldBeFound("createdDate.specified=true");

    //     // Get all the SnowDDLList where createdDate is null
    //     defaultSnowDDLShouldNotBeFound("createdDate.specified=false");
    // }

    // @Test
    // @Transactional
    // public void getAllSnowDDLesByLastModifiedByIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     SnowDDLRepository.saveAndFlush(SnowDDL);

    //     // Get all the SnowDDLList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
    //     defaultSnowDDLShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

    //     // Get all the SnowDDLList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
    //     defaultSnowDDLShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowDDLesByLastModifiedByIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     SnowDDLRepository.saveAndFlush(SnowDDL);

    //     // Get all the SnowDDLList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
    //     defaultSnowDDLShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

    //     // Get all the SnowDDLList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
    //     defaultSnowDDLShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowDDLesByLastModifiedByIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     SnowDDLRepository.saveAndFlush(SnowDDL);

    //     // Get all the SnowDDLList where lastModifiedBy is not null
    //     defaultSnowDDLShouldBeFound("lastModifiedBy.specified=true");

    //     // Get all the SnowDDLList where lastModifiedBy is null
    //     defaultSnowDDLShouldNotBeFound("lastModifiedBy.specified=false");
    // }

    // @Test
    // @Transactional
    // public void getAllSnowDDLesByLastModifiedDateIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     SnowDDLRepository.saveAndFlush(SnowDDL);

    //     // Get all the SnowDDLList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
    //     defaultSnowDDLShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

    //     // Get all the SnowDDLList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
    //     defaultSnowDDLShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowDDLesByLastModifiedDateIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     SnowDDLRepository.saveAndFlush(SnowDDL);

    //     // Get all the SnowDDLList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
    //     defaultSnowDDLShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

    //     // Get all the SnowDDLList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
    //     defaultSnowDDLShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    // }

    // @Test
    // @Transactional
    // public void getAllSnowDDLesByLastModifiedDateIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     SnowDDLRepository.saveAndFlush(SnowDDL);

    //     // Get all the SnowDDLList where lastModifiedDate is not null
    //     defaultSnowDDLShouldBeFound("lastModifiedDate.specified=true");

    //     // Get all the SnowDDLList where lastModifiedDate is null
    //     defaultSnowDDLShouldNotBeFound("lastModifiedDate.specified=false");
    // }

    @Test
    @Transactional
    public void getAllSnowDDLesBySourceConnectionIsEqualToSomething() throws Exception {
        // Get already existing entity
        SourceConnection sourceConnection = SnowDDL.getSourceConnection();
        SnowDDLRepository.saveAndFlush(SnowDDL);
        Long sourceConnectionId = sourceConnection.getId();

        // Get all the SnowDDLList where sourceConnection equals to sourceConnectionId
        defaultSnowDDLShouldBeFound("sourceConnectionId.equals=" + sourceConnectionId);

        // Get all the SnowDDLList where sourceConnection equals to sourceConnectionId + 1
        defaultSnowDDLShouldNotBeFound("sourceConnectionId.equals=" + (sourceConnectionId + 1));
    }


    @Test
    @Transactional
    public void getAllSnowDDLesBySnowflakeConnectionIsEqualToSomething() throws Exception {
        // Get already existing entity
        SnowflakeConnection snowflakeConnection = SnowDDL.getSnowflakeConnection();
        SnowDDLRepository.saveAndFlush(SnowDDL);
        Long snowflakeConnectionId = snowflakeConnection.getId();

        // Get all the SnowDDLList where snowflakeConnection equals to snowflakeConnectionId
        defaultSnowDDLShouldBeFound("snowflakeConnectionId.equals=" + snowflakeConnectionId);

        // Get all the SnowDDLList where snowflakeConnection equals to snowflakeConnectionId + 1
        defaultSnowDDLShouldNotBeFound("snowflakeConnectionId.equals=" + (snowflakeConnectionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSnowDDLShouldBeFound(String filter) throws Exception {
        restSnowDDLMockMvc.perform(get("/api/snow-ddl?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(SnowDDL.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastStatus").value(hasItem(DEFAULT_LAST_STATUS)))
            .andExpect(jsonPath("$.[*].sourcePath").value(hasItem(DEFAULT_SOURCE_PATH)));
            // .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            // .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            // .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restSnowDDLMockMvc.perform(get("/api/snow-ddl/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSnowDDLShouldNotBeFound(String filter) throws Exception {
        restSnowDDLMockMvc.perform(get("/api/snow-ddl?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSnowDDLMockMvc.perform(get("/api/snow-ddl/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSnowDDL() throws Exception {
        // Get the SnowDDL
        restSnowDDLMockMvc.perform(get("/api/snow-ddl/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSnowDDL() throws Exception {
        // Initialize the database
        SnowDDLRepository.saveAndFlush(SnowDDL);

        int databaseSizeBeforeUpdate = SnowDDLRepository.findAll().size();

        // Update the SnowDDL
        SnowDDL updatedSnowDDL = SnowDDLRepository.findById(SnowDDL.getId()).get();
        // Disconnect from session so that the updates on updatedSnowDDL are not directly saved in db
        em.detach(updatedSnowDDL);
        updatedSnowDDL
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .lastStatus(UPDATED_LAST_STATUS)
            .sourcePath(UPDATED_SOURCE_PATH);
            // .createdDate(UPDATED_CREATED_DATE)
            // .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            // .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        SnowDDLDTO SnowDDLDTO = SnowDDLMapper.toDto(updatedSnowDDL);

        restSnowDDLMockMvc.perform(put("/api/snow-ddl")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(SnowDDLDTO)))
            .andExpect(status().isOk());

        // Validate the SnowDDL in the database
        List<SnowDDL> SnowDDLList = SnowDDLRepository.findAll();
        assertThat(SnowDDLList).hasSize(databaseSizeBeforeUpdate);
        SnowDDL testSnowDDL = SnowDDLList.get(SnowDDLList.size() - 1);
        assertThat(testSnowDDL.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSnowDDL.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSnowDDL.getSourcePath()).isEqualTo(UPDATED_SOURCE_PATH);
        assertThat(testSnowDDL.getLastStatus()).isEqualTo(UPDATED_LAST_STATUS);
        // assertThat(testSnowDDL.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        // assertThat(testSnowDDL.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        // assertThat(testSnowDDL.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        // assertThat(testSnowDDL.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSnowDDL() throws Exception {
        int databaseSizeBeforeUpdate = SnowDDLRepository.findAll().size();

        // Create the SnowDDL
        SnowDDLDTO SnowDDLDTO = SnowDDLMapper.toDto(SnowDDL);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSnowDDLMockMvc.perform(put("/api/snow-ddl")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(SnowDDLDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SnowDDL in the database
        List<SnowDDL> SnowDDLList = SnowDDLRepository.findAll();
        assertThat(SnowDDLList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSnowDDL() throws Exception {
        // Initialize the database
        SnowDDLRepository.saveAndFlush(SnowDDL);

        int databaseSizeBeforeDelete = SnowDDLRepository.findAll().size();

        // Delete the SnowDDL
        restSnowDDLMockMvc.perform(delete("/api/snow-ddl/{id}", SnowDDL.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SnowDDL> SnowDDLList = SnowDDLRepository.findAll();
        assertThat(SnowDDLList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SnowDDL.class);
        SnowDDL SnowDDL1 = new SnowDDL();
        SnowDDL1.setId(1L);
        SnowDDL SnowDDL2 = new SnowDDL();
        SnowDDL2.setId(SnowDDL1.getId());
        assertThat(SnowDDL1).isEqualTo(SnowDDL2);
        SnowDDL2.setId(2L);
        assertThat(SnowDDL1).isNotEqualTo(SnowDDL2);
        SnowDDL1.setId(null);
        assertThat(SnowDDL1).isNotEqualTo(SnowDDL2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SnowDDLDTO.class);
        SnowDDLDTO SnowDDLDTO1 = new SnowDDLDTO();
        SnowDDLDTO1.setId(1L);
        SnowDDLDTO SnowDDLDTO2 = new SnowDDLDTO();
        assertThat(SnowDDLDTO1).isNotEqualTo(SnowDDLDTO2);
        SnowDDLDTO2.setId(SnowDDLDTO1.getId());
        assertThat(SnowDDLDTO1).isEqualTo(SnowDDLDTO2);
        SnowDDLDTO2.setId(2L);
        assertThat(SnowDDLDTO1).isNotEqualTo(SnowDDLDTO2);
        SnowDDLDTO1.setId(null);
        assertThat(SnowDDLDTO1).isNotEqualTo(SnowDDLDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(SnowDDLMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(SnowDDLMapper.fromId(null)).isNull();
    }
}
