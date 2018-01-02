package com.lf.gw.web.rest;

import com.lf.gw.LfgwApp;

import com.lf.gw.domain.SaleAmt2;
import com.lf.gw.repository.SaleAmt2Repository;
import com.lf.gw.service.SaleAmt2Service;
import com.lf.gw.service.dto.SaleAmt2DTO;
import com.lf.gw.service.mapper.SaleAmt2Mapper;
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
 * Test class for the SaleAmt2Resource REST controller.
 *
 * @see SaleAmt2Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LfgwApp.class)
public class SaleAmt2ResourceIntTest {

    private static final Long DEFAULT_AMT_2_ID = 1L;
    private static final Long UPDATED_AMT_2_ID = 2L;

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long UPDATED_PROJECT_ID = 2L;

    private static final Long DEFAULT_SHOP_ID = 1L;
    private static final Long UPDATED_SHOP_ID = 2L;

    private static final String DEFAULT_AMT_2_INFO = "AAAAAAAAAA";
    private static final String UPDATED_AMT_2_INFO = "BBBBBBBBBB";

    @Autowired
    private SaleAmt2Repository saleAmt2Repository;

    @Autowired
    private SaleAmt2Mapper saleAmt2Mapper;

    @Autowired
    private SaleAmt2Service saleAmt2Service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSaleAmt2MockMvc;

    private SaleAmt2 saleAmt2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SaleAmt2Resource saleAmt2Resource = new SaleAmt2Resource(saleAmt2Service);
        this.restSaleAmt2MockMvc = MockMvcBuilders.standaloneSetup(saleAmt2Resource)
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
    public static SaleAmt2 createEntity(EntityManager em) {
        SaleAmt2 saleAmt2 = new SaleAmt2()
            .amt2Id(DEFAULT_AMT_2_ID)
            .projectId(DEFAULT_PROJECT_ID)
            .shopId(DEFAULT_SHOP_ID)
            .amt2Info(DEFAULT_AMT_2_INFO);
        return saleAmt2;
    }

    @Before
    public void initTest() {
        saleAmt2 = createEntity(em);
    }

    @Test
    @Transactional
    public void createSaleAmt2() throws Exception {
        int databaseSizeBeforeCreate = saleAmt2Repository.findAll().size();

        // Create the SaleAmt2
        SaleAmt2DTO saleAmt2DTO = saleAmt2Mapper.toDto(saleAmt2);
        restSaleAmt2MockMvc.perform(post("/api/sale-amt-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleAmt2DTO)))
            .andExpect(status().isCreated());

        // Validate the SaleAmt2 in the database
        List<SaleAmt2> saleAmt2List = saleAmt2Repository.findAll();
        assertThat(saleAmt2List).hasSize(databaseSizeBeforeCreate + 1);
        SaleAmt2 testSaleAmt2 = saleAmt2List.get(saleAmt2List.size() - 1);
        assertThat(testSaleAmt2.getAmt2Id()).isEqualTo(DEFAULT_AMT_2_ID);
        assertThat(testSaleAmt2.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testSaleAmt2.getShopId()).isEqualTo(DEFAULT_SHOP_ID);
        assertThat(testSaleAmt2.getAmt2Info()).isEqualTo(DEFAULT_AMT_2_INFO);
    }

    @Test
    @Transactional
    public void createSaleAmt2WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saleAmt2Repository.findAll().size();

        // Create the SaleAmt2 with an existing ID
        saleAmt2.setId(1L);
        SaleAmt2DTO saleAmt2DTO = saleAmt2Mapper.toDto(saleAmt2);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleAmt2MockMvc.perform(post("/api/sale-amt-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleAmt2DTO)))
            .andExpect(status().isBadRequest());

        // Validate the SaleAmt2 in the database
        List<SaleAmt2> saleAmt2List = saleAmt2Repository.findAll();
        assertThat(saleAmt2List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAmt2IdIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleAmt2Repository.findAll().size();
        // set the field null
        saleAmt2.setAmt2Id(null);

        // Create the SaleAmt2, which fails.
        SaleAmt2DTO saleAmt2DTO = saleAmt2Mapper.toDto(saleAmt2);

        restSaleAmt2MockMvc.perform(post("/api/sale-amt-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleAmt2DTO)))
            .andExpect(status().isBadRequest());

        List<SaleAmt2> saleAmt2List = saleAmt2Repository.findAll();
        assertThat(saleAmt2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleAmt2Repository.findAll().size();
        // set the field null
        saleAmt2.setProjectId(null);

        // Create the SaleAmt2, which fails.
        SaleAmt2DTO saleAmt2DTO = saleAmt2Mapper.toDto(saleAmt2);

        restSaleAmt2MockMvc.perform(post("/api/sale-amt-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleAmt2DTO)))
            .andExpect(status().isBadRequest());

        List<SaleAmt2> saleAmt2List = saleAmt2Repository.findAll();
        assertThat(saleAmt2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShopIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleAmt2Repository.findAll().size();
        // set the field null
        saleAmt2.setShopId(null);

        // Create the SaleAmt2, which fails.
        SaleAmt2DTO saleAmt2DTO = saleAmt2Mapper.toDto(saleAmt2);

        restSaleAmt2MockMvc.perform(post("/api/sale-amt-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleAmt2DTO)))
            .andExpect(status().isBadRequest());

        List<SaleAmt2> saleAmt2List = saleAmt2Repository.findAll();
        assertThat(saleAmt2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmt2InfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleAmt2Repository.findAll().size();
        // set the field null
        saleAmt2.setAmt2Info(null);

        // Create the SaleAmt2, which fails.
        SaleAmt2DTO saleAmt2DTO = saleAmt2Mapper.toDto(saleAmt2);

        restSaleAmt2MockMvc.perform(post("/api/sale-amt-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleAmt2DTO)))
            .andExpect(status().isBadRequest());

        List<SaleAmt2> saleAmt2List = saleAmt2Repository.findAll();
        assertThat(saleAmt2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSaleAmt2S() throws Exception {
        // Initialize the database
        saleAmt2Repository.saveAndFlush(saleAmt2);

        // Get all the saleAmt2List
        restSaleAmt2MockMvc.perform(get("/api/sale-amt-2-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleAmt2.getId().intValue())))
            .andExpect(jsonPath("$.[*].amt2Id").value(hasItem(DEFAULT_AMT_2_ID.intValue())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].shopId").value(hasItem(DEFAULT_SHOP_ID.intValue())))
            .andExpect(jsonPath("$.[*].amt2Info").value(hasItem(DEFAULT_AMT_2_INFO.toString())));
    }

    @Test
    @Transactional
    public void getSaleAmt2() throws Exception {
        // Initialize the database
        saleAmt2Repository.saveAndFlush(saleAmt2);

        // Get the saleAmt2
        restSaleAmt2MockMvc.perform(get("/api/sale-amt-2-s/{id}", saleAmt2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(saleAmt2.getId().intValue()))
            .andExpect(jsonPath("$.amt2Id").value(DEFAULT_AMT_2_ID.intValue()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.intValue()))
            .andExpect(jsonPath("$.shopId").value(DEFAULT_SHOP_ID.intValue()))
            .andExpect(jsonPath("$.amt2Info").value(DEFAULT_AMT_2_INFO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSaleAmt2() throws Exception {
        // Get the saleAmt2
        restSaleAmt2MockMvc.perform(get("/api/sale-amt-2-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSaleAmt2() throws Exception {
        // Initialize the database
        saleAmt2Repository.saveAndFlush(saleAmt2);
        int databaseSizeBeforeUpdate = saleAmt2Repository.findAll().size();

        // Update the saleAmt2
        SaleAmt2 updatedSaleAmt2 = saleAmt2Repository.findOne(saleAmt2.getId());
        // Disconnect from session so that the updates on updatedSaleAmt2 are not directly saved in db
        em.detach(updatedSaleAmt2);
        updatedSaleAmt2
            .amt2Id(UPDATED_AMT_2_ID)
            .projectId(UPDATED_PROJECT_ID)
            .shopId(UPDATED_SHOP_ID)
            .amt2Info(UPDATED_AMT_2_INFO);
        SaleAmt2DTO saleAmt2DTO = saleAmt2Mapper.toDto(updatedSaleAmt2);

        restSaleAmt2MockMvc.perform(put("/api/sale-amt-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleAmt2DTO)))
            .andExpect(status().isOk());

        // Validate the SaleAmt2 in the database
        List<SaleAmt2> saleAmt2List = saleAmt2Repository.findAll();
        assertThat(saleAmt2List).hasSize(databaseSizeBeforeUpdate);
        SaleAmt2 testSaleAmt2 = saleAmt2List.get(saleAmt2List.size() - 1);
        assertThat(testSaleAmt2.getAmt2Id()).isEqualTo(UPDATED_AMT_2_ID);
        assertThat(testSaleAmt2.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testSaleAmt2.getShopId()).isEqualTo(UPDATED_SHOP_ID);
        assertThat(testSaleAmt2.getAmt2Info()).isEqualTo(UPDATED_AMT_2_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingSaleAmt2() throws Exception {
        int databaseSizeBeforeUpdate = saleAmt2Repository.findAll().size();

        // Create the SaleAmt2
        SaleAmt2DTO saleAmt2DTO = saleAmt2Mapper.toDto(saleAmt2);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSaleAmt2MockMvc.perform(put("/api/sale-amt-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleAmt2DTO)))
            .andExpect(status().isCreated());

        // Validate the SaleAmt2 in the database
        List<SaleAmt2> saleAmt2List = saleAmt2Repository.findAll();
        assertThat(saleAmt2List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSaleAmt2() throws Exception {
        // Initialize the database
        saleAmt2Repository.saveAndFlush(saleAmt2);
        int databaseSizeBeforeDelete = saleAmt2Repository.findAll().size();

        // Get the saleAmt2
        restSaleAmt2MockMvc.perform(delete("/api/sale-amt-2-s/{id}", saleAmt2.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SaleAmt2> saleAmt2List = saleAmt2Repository.findAll();
        assertThat(saleAmt2List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleAmt2.class);
        SaleAmt2 saleAmt21 = new SaleAmt2();
        saleAmt21.setId(1L);
        SaleAmt2 saleAmt22 = new SaleAmt2();
        saleAmt22.setId(saleAmt21.getId());
        assertThat(saleAmt21).isEqualTo(saleAmt22);
        saleAmt22.setId(2L);
        assertThat(saleAmt21).isNotEqualTo(saleAmt22);
        saleAmt21.setId(null);
        assertThat(saleAmt21).isNotEqualTo(saleAmt22);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleAmt2DTO.class);
        SaleAmt2DTO saleAmt2DTO1 = new SaleAmt2DTO();
        saleAmt2DTO1.setId(1L);
        SaleAmt2DTO saleAmt2DTO2 = new SaleAmt2DTO();
        assertThat(saleAmt2DTO1).isNotEqualTo(saleAmt2DTO2);
        saleAmt2DTO2.setId(saleAmt2DTO1.getId());
        assertThat(saleAmt2DTO1).isEqualTo(saleAmt2DTO2);
        saleAmt2DTO2.setId(2L);
        assertThat(saleAmt2DTO1).isNotEqualTo(saleAmt2DTO2);
        saleAmt2DTO1.setId(null);
        assertThat(saleAmt2DTO1).isNotEqualTo(saleAmt2DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(saleAmt2Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(saleAmt2Mapper.fromId(null)).isNull();
    }
}
