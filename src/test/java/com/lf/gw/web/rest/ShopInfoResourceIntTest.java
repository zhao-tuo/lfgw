package com.lf.gw.web.rest;

import com.lf.gw.LfgwApp;

import com.lf.gw.domain.ShopInfo;
import com.lf.gw.repository.ShopInfoRepository;
import com.lf.gw.service.ShopInfoService;
import com.lf.gw.service.dto.ShopInfoDTO;
import com.lf.gw.service.mapper.ShopInfoMapper;
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
 * Test class for the ShopInfoResource REST controller.
 *
 * @see ShopInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LfgwApp.class)
public class ShopInfoResourceIntTest {

    private static final Long DEFAULT_SHOP_ID = 1L;
    private static final Long UPDATED_SHOP_ID = 2L;

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long UPDATED_PROJECT_ID = 2L;

    private static final String DEFAULT_SHOP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHOP_NAME = "BBBBBBBBBB";

    @Autowired
    private ShopInfoRepository shopInfoRepository;

    @Autowired
    private ShopInfoMapper shopInfoMapper;

    @Autowired
    private ShopInfoService shopInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShopInfoMockMvc;

    private ShopInfo shopInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShopInfoResource shopInfoResource = new ShopInfoResource(shopInfoService);
        this.restShopInfoMockMvc = MockMvcBuilders.standaloneSetup(shopInfoResource)
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
    public static ShopInfo createEntity(EntityManager em) {
        ShopInfo shopInfo = new ShopInfo()
            .shopId(DEFAULT_SHOP_ID)
            .projectId(DEFAULT_PROJECT_ID)
            .shopName(DEFAULT_SHOP_NAME);
        return shopInfo;
    }

    @Before
    public void initTest() {
        shopInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createShopInfo() throws Exception {
        int databaseSizeBeforeCreate = shopInfoRepository.findAll().size();

        // Create the ShopInfo
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(shopInfo);
        restShopInfoMockMvc.perform(post("/api/shop-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ShopInfo testShopInfo = shopInfoList.get(shopInfoList.size() - 1);
        assertThat(testShopInfo.getShopId()).isEqualTo(DEFAULT_SHOP_ID);
        assertThat(testShopInfo.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testShopInfo.getShopName()).isEqualTo(DEFAULT_SHOP_NAME);
    }

    @Test
    @Transactional
    public void createShopInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shopInfoRepository.findAll().size();

        // Create the ShopInfo with an existing ID
        shopInfo.setId(1L);
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(shopInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShopInfoMockMvc.perform(post("/api/shop-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkShopIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = shopInfoRepository.findAll().size();
        // set the field null
        shopInfo.setShopId(null);

        // Create the ShopInfo, which fails.
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(shopInfo);

        restShopInfoMockMvc.perform(post("/api/shop-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = shopInfoRepository.findAll().size();
        // set the field null
        shopInfo.setProjectId(null);

        // Create the ShopInfo, which fails.
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(shopInfo);

        restShopInfoMockMvc.perform(post("/api/shop-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShopNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shopInfoRepository.findAll().size();
        // set the field null
        shopInfo.setShopName(null);

        // Create the ShopInfo, which fails.
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(shopInfo);

        restShopInfoMockMvc.perform(post("/api/shop-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShopInfos() throws Exception {
        // Initialize the database
        shopInfoRepository.saveAndFlush(shopInfo);

        // Get all the shopInfoList
        restShopInfoMockMvc.perform(get("/api/shop-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shopInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].shopId").value(hasItem(DEFAULT_SHOP_ID.intValue())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].shopName").value(hasItem(DEFAULT_SHOP_NAME.toString())));
    }

    @Test
    @Transactional
    public void getShopInfo() throws Exception {
        // Initialize the database
        shopInfoRepository.saveAndFlush(shopInfo);

        // Get the shopInfo
        restShopInfoMockMvc.perform(get("/api/shop-infos/{id}", shopInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shopInfo.getId().intValue()))
            .andExpect(jsonPath("$.shopId").value(DEFAULT_SHOP_ID.intValue()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.intValue()))
            .andExpect(jsonPath("$.shopName").value(DEFAULT_SHOP_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShopInfo() throws Exception {
        // Get the shopInfo
        restShopInfoMockMvc.perform(get("/api/shop-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShopInfo() throws Exception {
        // Initialize the database
        shopInfoRepository.saveAndFlush(shopInfo);
        int databaseSizeBeforeUpdate = shopInfoRepository.findAll().size();

        // Update the shopInfo
        ShopInfo updatedShopInfo = shopInfoRepository.findOne(shopInfo.getId());
        // Disconnect from session so that the updates on updatedShopInfo are not directly saved in db
        em.detach(updatedShopInfo);
        updatedShopInfo
            .shopId(UPDATED_SHOP_ID)
            .projectId(UPDATED_PROJECT_ID)
            .shopName(UPDATED_SHOP_NAME);
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(updatedShopInfo);

        restShopInfoMockMvc.perform(put("/api/shop-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopInfoDTO)))
            .andExpect(status().isOk());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeUpdate);
        ShopInfo testShopInfo = shopInfoList.get(shopInfoList.size() - 1);
        assertThat(testShopInfo.getShopId()).isEqualTo(UPDATED_SHOP_ID);
        assertThat(testShopInfo.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testShopInfo.getShopName()).isEqualTo(UPDATED_SHOP_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingShopInfo() throws Exception {
        int databaseSizeBeforeUpdate = shopInfoRepository.findAll().size();

        // Create the ShopInfo
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(shopInfo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShopInfoMockMvc.perform(put("/api/shop-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShopInfo() throws Exception {
        // Initialize the database
        shopInfoRepository.saveAndFlush(shopInfo);
        int databaseSizeBeforeDelete = shopInfoRepository.findAll().size();

        // Get the shopInfo
        restShopInfoMockMvc.perform(delete("/api/shop-infos/{id}", shopInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShopInfo.class);
        ShopInfo shopInfo1 = new ShopInfo();
        shopInfo1.setId(1L);
        ShopInfo shopInfo2 = new ShopInfo();
        shopInfo2.setId(shopInfo1.getId());
        assertThat(shopInfo1).isEqualTo(shopInfo2);
        shopInfo2.setId(2L);
        assertThat(shopInfo1).isNotEqualTo(shopInfo2);
        shopInfo1.setId(null);
        assertThat(shopInfo1).isNotEqualTo(shopInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShopInfoDTO.class);
        ShopInfoDTO shopInfoDTO1 = new ShopInfoDTO();
        shopInfoDTO1.setId(1L);
        ShopInfoDTO shopInfoDTO2 = new ShopInfoDTO();
        assertThat(shopInfoDTO1).isNotEqualTo(shopInfoDTO2);
        shopInfoDTO2.setId(shopInfoDTO1.getId());
        assertThat(shopInfoDTO1).isEqualTo(shopInfoDTO2);
        shopInfoDTO2.setId(2L);
        assertThat(shopInfoDTO1).isNotEqualTo(shopInfoDTO2);
        shopInfoDTO1.setId(null);
        assertThat(shopInfoDTO1).isNotEqualTo(shopInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shopInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shopInfoMapper.fromId(null)).isNull();
    }
}
