package com.lf.gw.web.rest;

import com.lf.gw.LfgwApp;

import com.lf.gw.domain.MenuData;
import com.lf.gw.repository.MenuDataRepository;
import com.lf.gw.service.MenuDataService;
import com.lf.gw.service.dto.MenuDataDTO;
import com.lf.gw.service.mapper.MenuDataMapper;
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
 * Test class for the MenuDataResource REST controller.
 *
 * @see MenuDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LfgwApp.class)
public class MenuDataResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_KEY_WORD = "AAAAAAAAAA";
    private static final String UPDATED_KEY_WORD = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXPENDED = false;
    private static final Boolean UPDATED_EXPENDED = true;

    @Autowired
    private MenuDataRepository menuDataRepository;

    @Autowired
    private MenuDataMapper menuDataMapper;

    @Autowired
    private MenuDataService menuDataService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMenuDataMockMvc;

    private MenuData menuData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MenuDataResource menuDataResource = new MenuDataResource(menuDataService);
        this.restMenuDataMockMvc = MockMvcBuilders.standaloneSetup(menuDataResource)
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
    public static MenuData createEntity(EntityManager em) {
        MenuData menuData = new MenuData()
            .name(DEFAULT_NAME)
            .keyWord(DEFAULT_KEY_WORD)
            .icon(DEFAULT_ICON)
            .url(DEFAULT_URL)
            .expended(DEFAULT_EXPENDED);
        return menuData;
    }

    @Before
    public void initTest() {
        menuData = createEntity(em);
    }

    @Test
    @Transactional
    public void createMenuData() throws Exception {
        int databaseSizeBeforeCreate = menuDataRepository.findAll().size();

        // Create the MenuData
        MenuDataDTO menuDataDTO = menuDataMapper.toDto(menuData);
        restMenuDataMockMvc.perform(post("/api/menu-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuDataDTO)))
            .andExpect(status().isCreated());

        // Validate the MenuData in the database
        List<MenuData> menuDataList = menuDataRepository.findAll();
        assertThat(menuDataList).hasSize(databaseSizeBeforeCreate + 1);
        MenuData testMenuData = menuDataList.get(menuDataList.size() - 1);
        assertThat(testMenuData.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMenuData.getKeyWord()).isEqualTo(DEFAULT_KEY_WORD);
        assertThat(testMenuData.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testMenuData.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testMenuData.isExpended()).isEqualTo(DEFAULT_EXPENDED);
    }

    @Test
    @Transactional
    public void createMenuDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = menuDataRepository.findAll().size();

        // Create the MenuData with an existing ID
        menuData.setId(1L);
        MenuDataDTO menuDataDTO = menuDataMapper.toDto(menuData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenuDataMockMvc.perform(post("/api/menu-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MenuData in the database
        List<MenuData> menuDataList = menuDataRepository.findAll();
        assertThat(menuDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuDataRepository.findAll().size();
        // set the field null
        menuData.setName(null);

        // Create the MenuData, which fails.
        MenuDataDTO menuDataDTO = menuDataMapper.toDto(menuData);

        restMenuDataMockMvc.perform(post("/api/menu-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuDataDTO)))
            .andExpect(status().isBadRequest());

        List<MenuData> menuDataList = menuDataRepository.findAll();
        assertThat(menuDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeyWordIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuDataRepository.findAll().size();
        // set the field null
        menuData.setKeyWord(null);

        // Create the MenuData, which fails.
        MenuDataDTO menuDataDTO = menuDataMapper.toDto(menuData);

        restMenuDataMockMvc.perform(post("/api/menu-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuDataDTO)))
            .andExpect(status().isBadRequest());

        List<MenuData> menuDataList = menuDataRepository.findAll();
        assertThat(menuDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIconIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuDataRepository.findAll().size();
        // set the field null
        menuData.setIcon(null);

        // Create the MenuData, which fails.
        MenuDataDTO menuDataDTO = menuDataMapper.toDto(menuData);

        restMenuDataMockMvc.perform(post("/api/menu-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuDataDTO)))
            .andExpect(status().isBadRequest());

        List<MenuData> menuDataList = menuDataRepository.findAll();
        assertThat(menuDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuDataRepository.findAll().size();
        // set the field null
        menuData.setUrl(null);

        // Create the MenuData, which fails.
        MenuDataDTO menuDataDTO = menuDataMapper.toDto(menuData);

        restMenuDataMockMvc.perform(post("/api/menu-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuDataDTO)))
            .andExpect(status().isBadRequest());

        List<MenuData> menuDataList = menuDataRepository.findAll();
        assertThat(menuDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMenuData() throws Exception {
        // Initialize the database
        menuDataRepository.saveAndFlush(menuData);

        // Get all the menuDataList
        restMenuDataMockMvc.perform(get("/api/menu-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menuData.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].keyWord").value(hasItem(DEFAULT_KEY_WORD.toString())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].expended").value(hasItem(DEFAULT_EXPENDED.booleanValue())));
    }

    @Test
    @Transactional
    public void getMenuData() throws Exception {
        // Initialize the database
        menuDataRepository.saveAndFlush(menuData);

        // Get the menuData
        restMenuDataMockMvc.perform(get("/api/menu-data/{id}", menuData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(menuData.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.keyWord").value(DEFAULT_KEY_WORD.toString()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.expended").value(DEFAULT_EXPENDED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMenuData() throws Exception {
        // Get the menuData
        restMenuDataMockMvc.perform(get("/api/menu-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMenuData() throws Exception {
        // Initialize the database
        menuDataRepository.saveAndFlush(menuData);
        int databaseSizeBeforeUpdate = menuDataRepository.findAll().size();

        // Update the menuData
        MenuData updatedMenuData = menuDataRepository.findOne(menuData.getId());
        // Disconnect from session so that the updates on updatedMenuData are not directly saved in db
        em.detach(updatedMenuData);
        updatedMenuData
            .name(UPDATED_NAME)
            .keyWord(UPDATED_KEY_WORD)
            .icon(UPDATED_ICON)
            .url(UPDATED_URL)
            .expended(UPDATED_EXPENDED);
        MenuDataDTO menuDataDTO = menuDataMapper.toDto(updatedMenuData);

        restMenuDataMockMvc.perform(put("/api/menu-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuDataDTO)))
            .andExpect(status().isOk());

        // Validate the MenuData in the database
        List<MenuData> menuDataList = menuDataRepository.findAll();
        assertThat(menuDataList).hasSize(databaseSizeBeforeUpdate);
        MenuData testMenuData = menuDataList.get(menuDataList.size() - 1);
        assertThat(testMenuData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMenuData.getKeyWord()).isEqualTo(UPDATED_KEY_WORD);
        assertThat(testMenuData.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testMenuData.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testMenuData.isExpended()).isEqualTo(UPDATED_EXPENDED);
    }

    @Test
    @Transactional
    public void updateNonExistingMenuData() throws Exception {
        int databaseSizeBeforeUpdate = menuDataRepository.findAll().size();

        // Create the MenuData
        MenuDataDTO menuDataDTO = menuDataMapper.toDto(menuData);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMenuDataMockMvc.perform(put("/api/menu-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuDataDTO)))
            .andExpect(status().isCreated());

        // Validate the MenuData in the database
        List<MenuData> menuDataList = menuDataRepository.findAll();
        assertThat(menuDataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMenuData() throws Exception {
        // Initialize the database
        menuDataRepository.saveAndFlush(menuData);
        int databaseSizeBeforeDelete = menuDataRepository.findAll().size();

        // Get the menuData
        restMenuDataMockMvc.perform(delete("/api/menu-data/{id}", menuData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MenuData> menuDataList = menuDataRepository.findAll();
        assertThat(menuDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenuData.class);
        MenuData menuData1 = new MenuData();
        menuData1.setId(1L);
        MenuData menuData2 = new MenuData();
        menuData2.setId(menuData1.getId());
        assertThat(menuData1).isEqualTo(menuData2);
        menuData2.setId(2L);
        assertThat(menuData1).isNotEqualTo(menuData2);
        menuData1.setId(null);
        assertThat(menuData1).isNotEqualTo(menuData2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenuDataDTO.class);
        MenuDataDTO menuDataDTO1 = new MenuDataDTO();
        menuDataDTO1.setId(1L);
        MenuDataDTO menuDataDTO2 = new MenuDataDTO();
        assertThat(menuDataDTO1).isNotEqualTo(menuDataDTO2);
        menuDataDTO2.setId(menuDataDTO1.getId());
        assertThat(menuDataDTO1).isEqualTo(menuDataDTO2);
        menuDataDTO2.setId(2L);
        assertThat(menuDataDTO1).isNotEqualTo(menuDataDTO2);
        menuDataDTO1.setId(null);
        assertThat(menuDataDTO1).isNotEqualTo(menuDataDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(menuDataMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(menuDataMapper.fromId(null)).isNull();
    }
}
