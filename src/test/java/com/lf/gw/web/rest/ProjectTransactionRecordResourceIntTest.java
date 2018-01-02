package com.lf.gw.web.rest;

import com.lf.gw.LfgwApp;

import com.lf.gw.domain.ProjectTransactionRecord;
import com.lf.gw.repository.ProjectTransactionRecordRepository;
import com.lf.gw.service.ProjectTransactionRecordService;
import com.lf.gw.service.dto.ProjectTransactionRecordDTO;
import com.lf.gw.service.mapper.ProjectTransactionRecordMapper;
import com.lf.gw.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.lf.gw.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProjectTransactionRecordResource REST controller.
 *
 * @see ProjectTransactionRecordResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LfgwApp.class)
public class ProjectTransactionRecordResourceIntTest {

    private static final Long DEFAULT_RECORD_ID = 1L;
    private static final Long UPDATED_RECORD_ID = 2L;

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long UPDATED_PROJECT_ID = 2L;

    private static final Integer DEFAULT_TRANSACTION_YEAR = 1;
    private static final Integer UPDATED_TRANSACTION_YEAR = 2;

    @Autowired
    private ProjectTransactionRecordRepository projectTransactionRecordRepository;

    @Autowired
    private ProjectTransactionRecordMapper projectTransactionRecordMapper;

    @Autowired
    private ProjectTransactionRecordService projectTransactionRecordService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjectTransactionRecordMockMvc;

    private ProjectTransactionRecord projectTransactionRecord;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectTransactionRecordResource projectTransactionRecordResource = new ProjectTransactionRecordResource(projectTransactionRecordService);
        this.restProjectTransactionRecordMockMvc = MockMvcBuilders.standaloneSetup(projectTransactionRecordResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectTransactionRecord createEntity(EntityManager em) {
        ProjectTransactionRecord projectTransactionRecord = new ProjectTransactionRecord()
            .recordId(DEFAULT_RECORD_ID)
            .projectId(DEFAULT_PROJECT_ID)
            .transactionYear(DEFAULT_TRANSACTION_YEAR);
        return projectTransactionRecord;
    }

    @Before
    public void initTest() {
        projectTransactionRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectTransactionRecord() throws Exception {
        int databaseSizeBeforeCreate = projectTransactionRecordRepository.findAll().size();

        // Create the ProjectTransactionRecord
        ProjectTransactionRecordDTO projectTransactionRecordDTO = projectTransactionRecordMapper.toDto(projectTransactionRecord);
        restProjectTransactionRecordMockMvc.perform(post("/api/project-transaction-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTransactionRecordDTO)))
            .andExpect(status().isCreated());

        // Validate the ProjectTransactionRecord in the database
        List<ProjectTransactionRecord> projectTransactionRecordList = projectTransactionRecordRepository.findAll();
        assertThat(projectTransactionRecordList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectTransactionRecord testProjectTransactionRecord = projectTransactionRecordList.get(projectTransactionRecordList.size() - 1);
        assertThat(testProjectTransactionRecord.getRecordId()).isEqualTo(DEFAULT_RECORD_ID);
        assertThat(testProjectTransactionRecord.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testProjectTransactionRecord.getTransactionYear()).isEqualTo(DEFAULT_TRANSACTION_YEAR);
    }

    @Test
    @Transactional
    public void createProjectTransactionRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectTransactionRecordRepository.findAll().size();

        // Create the ProjectTransactionRecord with an existing ID
        projectTransactionRecord.setId(1L);
        ProjectTransactionRecordDTO projectTransactionRecordDTO = projectTransactionRecordMapper.toDto(projectTransactionRecord);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectTransactionRecordMockMvc.perform(post("/api/project-transaction-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTransactionRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectTransactionRecord in the database
        List<ProjectTransactionRecord> projectTransactionRecordList = projectTransactionRecordRepository.findAll();
        assertThat(projectTransactionRecordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRecordIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectTransactionRecordRepository.findAll().size();
        // set the field null
        projectTransactionRecord.setRecordId(null);

        // Create the ProjectTransactionRecord, which fails.
        ProjectTransactionRecordDTO projectTransactionRecordDTO = projectTransactionRecordMapper.toDto(projectTransactionRecord);

        restProjectTransactionRecordMockMvc.perform(post("/api/project-transaction-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTransactionRecordDTO)))
            .andExpect(status().isBadRequest());

        List<ProjectTransactionRecord> projectTransactionRecordList = projectTransactionRecordRepository.findAll();
        assertThat(projectTransactionRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectTransactionRecordRepository.findAll().size();
        // set the field null
        projectTransactionRecord.setProjectId(null);

        // Create the ProjectTransactionRecord, which fails.
        ProjectTransactionRecordDTO projectTransactionRecordDTO = projectTransactionRecordMapper.toDto(projectTransactionRecord);

        restProjectTransactionRecordMockMvc.perform(post("/api/project-transaction-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTransactionRecordDTO)))
            .andExpect(status().isBadRequest());

        List<ProjectTransactionRecord> projectTransactionRecordList = projectTransactionRecordRepository.findAll();
        assertThat(projectTransactionRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectTransactionRecordRepository.findAll().size();
        // set the field null
        projectTransactionRecord.setTransactionYear(null);

        // Create the ProjectTransactionRecord, which fails.
        ProjectTransactionRecordDTO projectTransactionRecordDTO = projectTransactionRecordMapper.toDto(projectTransactionRecord);

        restProjectTransactionRecordMockMvc.perform(post("/api/project-transaction-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTransactionRecordDTO)))
            .andExpect(status().isBadRequest());

        List<ProjectTransactionRecord> projectTransactionRecordList = projectTransactionRecordRepository.findAll();
        assertThat(projectTransactionRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjectTransactionRecords() throws Exception {
        // Initialize the database
        projectTransactionRecordRepository.saveAndFlush(projectTransactionRecord);

        // Get all the projectTransactionRecordList
        restProjectTransactionRecordMockMvc.perform(get("/api/project-transaction-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectTransactionRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].recordId").value(hasItem(DEFAULT_RECORD_ID.intValue())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].transactionYear").value(hasItem(DEFAULT_TRANSACTION_YEAR)));
    }

    @Test
    @Transactional
    public void getProjectTransactionRecord() throws Exception {
        // Initialize the database
        projectTransactionRecordRepository.saveAndFlush(projectTransactionRecord);

        // Get the projectTransactionRecord
        restProjectTransactionRecordMockMvc.perform(get("/api/project-transaction-records/{id}", projectTransactionRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectTransactionRecord.getId().intValue()))
            .andExpect(jsonPath("$.recordId").value(DEFAULT_RECORD_ID.intValue()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.intValue()))
            .andExpect(jsonPath("$.transactionYear").value(DEFAULT_TRANSACTION_YEAR));
    }

    @Test
    @Transactional
    public void getNonExistingProjectTransactionRecord() throws Exception {
        // Get the projectTransactionRecord
        restProjectTransactionRecordMockMvc.perform(get("/api/project-transaction-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectTransactionRecord() throws Exception {
        // Initialize the database
        projectTransactionRecordRepository.saveAndFlush(projectTransactionRecord);
        int databaseSizeBeforeUpdate = projectTransactionRecordRepository.findAll().size();

        // Update the projectTransactionRecord
        ProjectTransactionRecord updatedProjectTransactionRecord = projectTransactionRecordRepository.findOne(projectTransactionRecord.getId());
        // Disconnect from session so that the updates on updatedProjectTransactionRecord are not directly saved in db
        em.detach(updatedProjectTransactionRecord);
        updatedProjectTransactionRecord
            .recordId(UPDATED_RECORD_ID)
            .projectId(UPDATED_PROJECT_ID)
            .transactionYear(UPDATED_TRANSACTION_YEAR);
        ProjectTransactionRecordDTO projectTransactionRecordDTO = projectTransactionRecordMapper.toDto(updatedProjectTransactionRecord);

        restProjectTransactionRecordMockMvc.perform(put("/api/project-transaction-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTransactionRecordDTO)))
            .andExpect(status().isOk());

        // Validate the ProjectTransactionRecord in the database
        List<ProjectTransactionRecord> projectTransactionRecordList = projectTransactionRecordRepository.findAll();
        assertThat(projectTransactionRecordList).hasSize(databaseSizeBeforeUpdate);
        ProjectTransactionRecord testProjectTransactionRecord = projectTransactionRecordList.get(projectTransactionRecordList.size() - 1);
        assertThat(testProjectTransactionRecord.getRecordId()).isEqualTo(UPDATED_RECORD_ID);
        assertThat(testProjectTransactionRecord.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testProjectTransactionRecord.getTransactionYear()).isEqualTo(UPDATED_TRANSACTION_YEAR);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectTransactionRecord() throws Exception {
        int databaseSizeBeforeUpdate = projectTransactionRecordRepository.findAll().size();

        // Create the ProjectTransactionRecord
        ProjectTransactionRecordDTO projectTransactionRecordDTO = projectTransactionRecordMapper.toDto(projectTransactionRecord);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjectTransactionRecordMockMvc.perform(put("/api/project-transaction-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTransactionRecordDTO)))
            .andExpect(status().isCreated());

        // Validate the ProjectTransactionRecord in the database
        List<ProjectTransactionRecord> projectTransactionRecordList = projectTransactionRecordRepository.findAll();
        assertThat(projectTransactionRecordList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProjectTransactionRecord() throws Exception {
        // Initialize the database
        projectTransactionRecordRepository.saveAndFlush(projectTransactionRecord);
        int databaseSizeBeforeDelete = projectTransactionRecordRepository.findAll().size();

        // Get the projectTransactionRecord
        restProjectTransactionRecordMockMvc.perform(delete("/api/project-transaction-records/{id}", projectTransactionRecord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjectTransactionRecord> projectTransactionRecordList = projectTransactionRecordRepository.findAll();
        assertThat(projectTransactionRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectTransactionRecord.class);
        ProjectTransactionRecord projectTransactionRecord1 = new ProjectTransactionRecord();
        projectTransactionRecord1.setId(1L);
        ProjectTransactionRecord projectTransactionRecord2 = new ProjectTransactionRecord();
        projectTransactionRecord2.setId(projectTransactionRecord1.getId());
        assertThat(projectTransactionRecord1).isEqualTo(projectTransactionRecord2);
        projectTransactionRecord2.setId(2L);
        assertThat(projectTransactionRecord1).isNotEqualTo(projectTransactionRecord2);
        projectTransactionRecord1.setId(null);
        assertThat(projectTransactionRecord1).isNotEqualTo(projectTransactionRecord2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectTransactionRecordDTO.class);
        ProjectTransactionRecordDTO projectTransactionRecordDTO1 = new ProjectTransactionRecordDTO();
        projectTransactionRecordDTO1.setId(1L);
        ProjectTransactionRecordDTO projectTransactionRecordDTO2 = new ProjectTransactionRecordDTO();
        assertThat(projectTransactionRecordDTO1).isNotEqualTo(projectTransactionRecordDTO2);
        projectTransactionRecordDTO2.setId(projectTransactionRecordDTO1.getId());
        assertThat(projectTransactionRecordDTO1).isEqualTo(projectTransactionRecordDTO2);
        projectTransactionRecordDTO2.setId(2L);
        assertThat(projectTransactionRecordDTO1).isNotEqualTo(projectTransactionRecordDTO2);
        projectTransactionRecordDTO1.setId(null);
        assertThat(projectTransactionRecordDTO1).isNotEqualTo(projectTransactionRecordDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(projectTransactionRecordMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(projectTransactionRecordMapper.fromId(null)).isNull();
    }
}
