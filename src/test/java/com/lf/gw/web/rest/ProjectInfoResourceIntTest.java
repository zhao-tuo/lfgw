package com.lf.gw.web.rest;

import com.lf.gw.LfgwApp;

import com.lf.gw.domain.ProjectInfo;
import com.lf.gw.repository.ProjectInfoRepository;
import com.lf.gw.service.ProjectInfoService;
import com.lf.gw.service.dto.ProjectInfoDTO;
import com.lf.gw.service.mapper.ProjectInfoMapper;
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
 * Test class for the ProjectInfoResource REST controller.
 *
 * @see ProjectInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LfgwApp.class)
public class ProjectInfoResourceIntTest {

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long UPDATED_PROJECT_ID = 2L;

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    @Autowired
    private ProjectInfoRepository projectInfoRepository;

    @Autowired
    private ProjectInfoMapper projectInfoMapper;

    @Autowired
    private ProjectInfoService projectInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjectInfoMockMvc;

    private ProjectInfo projectInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectInfoResource projectInfoResource = new ProjectInfoResource(projectInfoService);
        this.restProjectInfoMockMvc = MockMvcBuilders.standaloneSetup(projectInfoResource)
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
    public static ProjectInfo createEntity(EntityManager em) {
        ProjectInfo projectInfo = new ProjectInfo()
            .projectId(DEFAULT_PROJECT_ID)
            .projectName(DEFAULT_PROJECT_NAME);
        return projectInfo;
    }

    @Before
    public void initTest() {
        projectInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectInfo() throws Exception {
        int databaseSizeBeforeCreate = projectInfoRepository.findAll().size();

        // Create the ProjectInfo
        ProjectInfoDTO projectInfoDTO = projectInfoMapper.toDto(projectInfo);
        restProjectInfoMockMvc.perform(post("/api/project-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the ProjectInfo in the database
        List<ProjectInfo> projectInfoList = projectInfoRepository.findAll();
        assertThat(projectInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectInfo testProjectInfo = projectInfoList.get(projectInfoList.size() - 1);
        assertThat(testProjectInfo.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testProjectInfo.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
    }

    @Test
    @Transactional
    public void createProjectInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectInfoRepository.findAll().size();

        // Create the ProjectInfo with an existing ID
        projectInfo.setId(1L);
        ProjectInfoDTO projectInfoDTO = projectInfoMapper.toDto(projectInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectInfoMockMvc.perform(post("/api/project-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectInfo in the database
        List<ProjectInfo> projectInfoList = projectInfoRepository.findAll();
        assertThat(projectInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProjectIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectInfoRepository.findAll().size();
        // set the field null
        projectInfo.setProjectId(null);

        // Create the ProjectInfo, which fails.
        ProjectInfoDTO projectInfoDTO = projectInfoMapper.toDto(projectInfo);

        restProjectInfoMockMvc.perform(post("/api/project-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ProjectInfo> projectInfoList = projectInfoRepository.findAll();
        assertThat(projectInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectInfoRepository.findAll().size();
        // set the field null
        projectInfo.setProjectName(null);

        // Create the ProjectInfo, which fails.
        ProjectInfoDTO projectInfoDTO = projectInfoMapper.toDto(projectInfo);

        restProjectInfoMockMvc.perform(post("/api/project-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ProjectInfo> projectInfoList = projectInfoRepository.findAll();
        assertThat(projectInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjectInfos() throws Exception {
        // Initialize the database
        projectInfoRepository.saveAndFlush(projectInfo);

        // Get all the projectInfoList
        restProjectInfoMockMvc.perform(get("/api/project-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getProjectInfo() throws Exception {
        // Initialize the database
        projectInfoRepository.saveAndFlush(projectInfo);

        // Get the projectInfo
        restProjectInfoMockMvc.perform(get("/api/project-infos/{id}", projectInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectInfo.getId().intValue()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.intValue()))
            .andExpect(jsonPath("$.projectName").value(DEFAULT_PROJECT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjectInfo() throws Exception {
        // Get the projectInfo
        restProjectInfoMockMvc.perform(get("/api/project-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectInfo() throws Exception {
        // Initialize the database
        projectInfoRepository.saveAndFlush(projectInfo);
        int databaseSizeBeforeUpdate = projectInfoRepository.findAll().size();

        // Update the projectInfo
        ProjectInfo updatedProjectInfo = projectInfoRepository.findOne(projectInfo.getId());
        // Disconnect from session so that the updates on updatedProjectInfo are not directly saved in db
        em.detach(updatedProjectInfo);
        updatedProjectInfo
            .projectId(UPDATED_PROJECT_ID)
            .projectName(UPDATED_PROJECT_NAME);
        ProjectInfoDTO projectInfoDTO = projectInfoMapper.toDto(updatedProjectInfo);

        restProjectInfoMockMvc.perform(put("/api/project-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectInfoDTO)))
            .andExpect(status().isOk());

        // Validate the ProjectInfo in the database
        List<ProjectInfo> projectInfoList = projectInfoRepository.findAll();
        assertThat(projectInfoList).hasSize(databaseSizeBeforeUpdate);
        ProjectInfo testProjectInfo = projectInfoList.get(projectInfoList.size() - 1);
        assertThat(testProjectInfo.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testProjectInfo.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectInfo() throws Exception {
        int databaseSizeBeforeUpdate = projectInfoRepository.findAll().size();

        // Create the ProjectInfo
        ProjectInfoDTO projectInfoDTO = projectInfoMapper.toDto(projectInfo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjectInfoMockMvc.perform(put("/api/project-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the ProjectInfo in the database
        List<ProjectInfo> projectInfoList = projectInfoRepository.findAll();
        assertThat(projectInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProjectInfo() throws Exception {
        // Initialize the database
        projectInfoRepository.saveAndFlush(projectInfo);
        int databaseSizeBeforeDelete = projectInfoRepository.findAll().size();

        // Get the projectInfo
        restProjectInfoMockMvc.perform(delete("/api/project-infos/{id}", projectInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjectInfo> projectInfoList = projectInfoRepository.findAll();
        assertThat(projectInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectInfo.class);
        ProjectInfo projectInfo1 = new ProjectInfo();
        projectInfo1.setId(1L);
        ProjectInfo projectInfo2 = new ProjectInfo();
        projectInfo2.setId(projectInfo1.getId());
        assertThat(projectInfo1).isEqualTo(projectInfo2);
        projectInfo2.setId(2L);
        assertThat(projectInfo1).isNotEqualTo(projectInfo2);
        projectInfo1.setId(null);
        assertThat(projectInfo1).isNotEqualTo(projectInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectInfoDTO.class);
        ProjectInfoDTO projectInfoDTO1 = new ProjectInfoDTO();
        projectInfoDTO1.setId(1L);
        ProjectInfoDTO projectInfoDTO2 = new ProjectInfoDTO();
        assertThat(projectInfoDTO1).isNotEqualTo(projectInfoDTO2);
        projectInfoDTO2.setId(projectInfoDTO1.getId());
        assertThat(projectInfoDTO1).isEqualTo(projectInfoDTO2);
        projectInfoDTO2.setId(2L);
        assertThat(projectInfoDTO1).isNotEqualTo(projectInfoDTO2);
        projectInfoDTO1.setId(null);
        assertThat(projectInfoDTO1).isNotEqualTo(projectInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(projectInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(projectInfoMapper.fromId(null)).isNull();
    }
}
