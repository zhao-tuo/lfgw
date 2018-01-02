package com.lf.gw.web.rest;

import com.lf.gw.LfgwApp;

import com.lf.gw.domain.SaleAmt1;
import com.lf.gw.repository.SaleAmt1Repository;
import com.lf.gw.service.SaleAmt1Service;
import com.lf.gw.service.dto.SaleAmt1DTO;
import com.lf.gw.service.mapper.SaleAmt1Mapper;
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
 * Test class for the SaleAmt1Resource REST controller.
 *
 * @see SaleAmt1Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LfgwApp.class)
public class SaleAmt1ResourceIntTest {

    private static final Long DEFAULT_AMT_1_ID = 1L;
    private static final Long UPDATED_AMT_1_ID = 2L;

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long UPDATED_PROJECT_ID = 2L;

    private static final Long DEFAULT_SHOP_ID = 1L;
    private static final Long UPDATED_SHOP_ID = 2L;

    private static final String DEFAULT_AMT_1_INFO = "AAAAAAAAAA";
    private static final String UPDATED_AMT_1_INFO = "BBBBBBBBBB";

    @Autowired
    private SaleAmt1Repository saleAmt1Repository;

    @Autowired
    private SaleAmt1Mapper saleAmt1Mapper;

    @Autowired
    private SaleAmt1Service saleAmt1Service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSaleAmt1MockMvc;

    private SaleAmt1 saleAmt1;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SaleAmt1Resource saleAmt1Resource = new SaleAmt1Resource(saleAmt1Service);
        this.restSaleAmt1MockMvc = MockMvcBuilders.standaloneSetup(saleAmt1Resource)
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
    public static SaleAmt1 createEntity(EntityManager em) {
        SaleAmt1 saleAmt1 = new SaleAmt1()
            .amt1Id(DEFAULT_AMT_1_ID)
            .projectId(DEFAULT_PROJECT_ID)
            .shopId(DEFAULT_SHOP_ID)
            .amt1Info(DEFAULT_AMT_1_INFO);
        return saleAmt1;
    }

    @Before
    public void initTest() {
        saleAmt1 = createEntity(em);
    }

    @Test
    @Transactional
    public void createSaleAmt1() throws Exception {
        int databaseSizeBeforeCreate = saleAmt1Repository.findAll().size();

        // Create the SaleAmt1
        SaleAmt1DTO saleAmt1DTO = saleAmt1Mapper.toDto(saleAmt1);
        restSaleAmt1MockMvc.perform(post("/api/sale-amt-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleAmt1DTO)))
            .andExpect(status().isCreated());

        // Validate the SaleAmt1 in the database
        List<SaleAmt1> saleAmt1List = saleAmt1Repository.findAll();
        assertThat(saleAmt1List).hasSize(databaseSizeBeforeCreate + 1);
        SaleAmt1 testSaleAmt1 = saleAmt1List.get(saleAmt1List.size() - 1);
        assertThat(testSaleAmt1.getAmt1Id()).isEqualTo(DEFAULT_AMT_1_ID);
        assertThat(testSaleAmt1.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testSaleAmt1.getShopId()).isEqualTo(DEFAULT_SHOP_ID);
        assertThat(testSaleAmt1.getAmt1Info()).isEqualTo(DEFAULT_AMT_1_INFO);
    }

    @Test
    @Transactional
    public void createSaleAmt1WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saleAmt1Repository.findAll().size();

        // Create the SaleAmt1 with an existing ID
        saleAmt1.setId(1L);
        SaleAmt1DTO saleAmt1DTO = saleAmt1Mapper.toDto(saleAmt1);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleAmt1MockMvc.perform(post("/api/sale-amt-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleAmt1DTO)))
            .andExpect(status().isBadRequest());

        // Validate the SaleAmt1 in the database
        List<SaleAmt1> saleAmt1List = saleAmt1Repository.findAll();
        assertThat(saleAmt1List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAmt1IdIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleAmt1Repository.findAll().size();
        // set the field null
        saleAmt1.setAmt1Id(null);

        // Create the SaleAmt1, which fails.
        SaleAmt1DTO saleAmt1DTO = saleAmt1Mapper.toDto(saleAmt1);

        restSaleAmt1MockMvc.perform(post("/api/sale-amt-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleAmt1DTO)))
            .andExpect(status().isBadRequest());

        List<SaleAmt1> saleAmt1List = saleAmt1Repository.findAll();
        assertThat(saleAmt1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleAmt1Repository.findAll().size();
        // set the field null
        saleAmt1.setProjectId(null);

        // Create the SaleAmt1, which fails.
        SaleAmt1DTO saleAmt1DTO = saleAmt1Mapper.toDto(saleAmt1);

        restSaleAmt1MockMvc.perform(post("/api/sale-amt-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleAmt1DTO)))
            .andExpect(status().isBadRequest());

        List<SaleAmt1> saleAmt1List = saleAmt1Repository.findAll();
        assertThat(saleAmt1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShopIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleAmt1Repository.findAll().size();
        // set the field null
        saleAmt1.setShopId(null);

        // Create the SaleAmt1, which fails.
        SaleAmt1DTO saleAmt1DTO = saleAmt1Mapper.toDto(saleAmt1);

        restSaleAmt1MockMvc.perform(post("/api/sale-amt-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleAmt1DTO)))
            .andExpect(status().isBadRequest());

        List<SaleAmt1> saleAmt1List = saleAmt1Repository.findAll();
        assertThat(saleAmt1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmt1InfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleAmt1Repository.findAll().size();
        // set the field null
        saleAmt1.setAmt1Info(null);

        // Create the SaleAmt1, which fails.
        SaleAmt1DTO saleAmt1DTO = saleAmt1Mapper.toDto(saleAmt1);

        restSaleAmt1MockMvc.perform(post("/api/sale-amt-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleAmt1DTO)))
            .andExpect(status().isBadRequest());

        List<SaleAmt1> saleAmt1List = saleAmt1Repository.findAll();
        assertThat(saleAmt1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSaleAmt1S() throws Exception {
        // Initialize the database
        saleAmt1Repository.saveAndFlush(saleAmt1);

        // Get all the saleAmt1List
        restSaleAmt1MockMvc.perform(get("/api/sale-amt-1-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleAmt1.getId().intValue())))
            .andExpect(jsonPath("$.[*].amt1Id").value(hasItem(DEFAULT_AMT_1_ID.intValue())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].shopId").value(hasItem(DEFAULT_SHOP_ID.intValue())))
            .andExpect(jsonPath("$.[*].amt1Info").value(hasItem(DEFAULT_AMT_1_INFO.toString())));
    }

    @Test
    @Transactional
    public void getSaleAmt1() throws Exception {
        // Initialize the database
        saleAmt1Repository.saveAndFlush(saleAmt1);

        // Get the saleAmt1
        restSaleAmt1MockMvc.perform(get("/api/sale-amt-1-s/{id}", saleAmt1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(saleAmt1.getId().intValue()))
            .andExpect(jsonPath("$.amt1Id").value(DEFAULT_AMT_1_ID.intValue()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.intValue()))
            .andExpect(jsonPath("$.shopId").value(DEFAULT_SHOP_ID.intValue()))
            .andExpect(jsonPath("$.amt1Info").value(DEFAULT_AMT_1_INFO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSaleAmt1() throws Exception {
        // Get the saleAmt1
        restSaleAmt1MockMvc.perform(get("/api/sale-amt-1-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSaleAmt1() throws Exception {
        // Initialize the database
        saleAmt1Repository.saveAndFlush(saleAmt1);
        int databaseSizeBeforeUpdate = saleAmt1Repository.findAll().size();

        // Update the saleAmt1
        SaleAmt1 updatedSaleAmt1 = saleAmt1Repository.findOne(saleAmt1.getId());
        // Disconnect from session so that the updates on updatedSaleAmt1 are not directly saved in db
        em.detach(updatedSaleAmt1);
        updatedSaleAmt1
            .amt1Id(UPDATED_AMT_1_ID)
            .projectId(UPDATED_PROJECT_ID)
            .shopId(UPDATED_SHOP_ID)
            .amt1Info(UPDATED_AMT_1_INFO);
        SaleAmt1DTO saleAmt1DTO = saleAmt1Mapper.toDto(updatedSaleAmt1);

        restSaleAmt1MockMvc.perform(put("/api/sale-amt-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleAmt1DTO)))
            .andExpect(status().isOk());

        // Validate the SaleAmt1 in the database
        List<SaleAmt1> saleAmt1List = saleAmt1Repository.findAll();
        assertThat(saleAmt1List).hasSize(databaseSizeBeforeUpdate);
        SaleAmt1 testSaleAmt1 = saleAmt1List.get(saleAmt1List.size() - 1);
        assertThat(testSaleAmt1.getAmt1Id()).isEqualTo(UPDATED_AMT_1_ID);
        assertThat(testSaleAmt1.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testSaleAmt1.getShopId()).isEqualTo(UPDATED_SHOP_ID);
        assertThat(testSaleAmt1.getAmt1Info()).isEqualTo(UPDATED_AMT_1_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingSaleAmt1() throws Exception {
        int databaseSizeBeforeUpdate = saleAmt1Repository.findAll().size();

        // Create the SaleAmt1
        SaleAmt1DTO saleAmt1DTO = saleAmt1Mapper.toDto(saleAmt1);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSaleAmt1MockMvc.perform(put("/api/sale-amt-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleAmt1DTO)))
            .andExpect(status().isCreated());

        // Validate the SaleAmt1 in the database
        List<SaleAmt1> saleAmt1List = saleAmt1Repository.findAll();
        assertThat(saleAmt1List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSaleAmt1() throws Exception {
        // Initialize the database
        saleAmt1Repository.saveAndFlush(saleAmt1);
        int databaseSizeBeforeDelete = saleAmt1Repository.findAll().size();

        // Get the saleAmt1
        restSaleAmt1MockMvc.perform(delete("/api/sale-amt-1-s/{id}", saleAmt1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SaleAmt1> saleAmt1List = saleAmt1Repository.findAll();
        assertThat(saleAmt1List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleAmt1.class);
        SaleAmt1 saleAmt11 = new SaleAmt1();
        saleAmt11.setId(1L);
        SaleAmt1 saleAmt12 = new SaleAmt1();
        saleAmt12.setId(saleAmt11.getId());
        assertThat(saleAmt11).isEqualTo(saleAmt12);
        saleAmt12.setId(2L);
        assertThat(saleAmt11).isNotEqualTo(saleAmt12);
        saleAmt11.setId(null);
        assertThat(saleAmt11).isNotEqualTo(saleAmt12);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleAmt1DTO.class);
        SaleAmt1DTO saleAmt1DTO1 = new SaleAmt1DTO();
        saleAmt1DTO1.setId(1L);
        SaleAmt1DTO saleAmt1DTO2 = new SaleAmt1DTO();
        assertThat(saleAmt1DTO1).isNotEqualTo(saleAmt1DTO2);
        saleAmt1DTO2.setId(saleAmt1DTO1.getId());
        assertThat(saleAmt1DTO1).isEqualTo(saleAmt1DTO2);
        saleAmt1DTO2.setId(2L);
        assertThat(saleAmt1DTO1).isNotEqualTo(saleAmt1DTO2);
        saleAmt1DTO1.setId(null);
        assertThat(saleAmt1DTO1).isNotEqualTo(saleAmt1DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(saleAmt1Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(saleAmt1Mapper.fromId(null)).isNull();
    }
}
