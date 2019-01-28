package io.github.tcaswag.web.rest;

import io.github.tcaswag.TcaSwagApp;

import io.github.tcaswag.domain.ProductSku;
import io.github.tcaswag.repository.ProductSkuRepository;
import io.github.tcaswag.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.tcaswag.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.tcaswag.domain.enumeration.GenderType;
import io.github.tcaswag.domain.enumeration.ProductSize;
/**
 * Test class for the ProductSkuResource REST controller.
 *
 * @see ProductSkuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TcaSwagApp.class)
public class ProductSkuResourceIntTest {

    private static final GenderType DEFAULT_GENDER = GenderType.MALE;
    private static final GenderType UPDATED_GENDER = GenderType.FEMALE;

    private static final ProductSize DEFAULT_SIZE = ProductSize.UNIQUE;
    private static final ProductSize UPDATED_SIZE = ProductSize.XXSMALL;

    @Autowired
    private ProductSkuRepository productSkuRepository;

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

    private MockMvc restProductSkuMockMvc;

    private ProductSku productSku;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductSkuResource productSkuResource = new ProductSkuResource(productSkuRepository);
        this.restProductSkuMockMvc = MockMvcBuilders.standaloneSetup(productSkuResource)
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
    public static ProductSku createEntity(EntityManager em) {
        ProductSku productSku = new ProductSku()
            .gender(DEFAULT_GENDER)
            .size(DEFAULT_SIZE);
        return productSku;
    }

    @Before
    public void initTest() {
        productSku = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductSku() throws Exception {
        int databaseSizeBeforeCreate = productSkuRepository.findAll().size();

        // Create the ProductSku
        restProductSkuMockMvc.perform(post("/api/product-skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSku)))
            .andExpect(status().isCreated());

        // Validate the ProductSku in the database
        List<ProductSku> productSkuList = productSkuRepository.findAll();
        assertThat(productSkuList).hasSize(databaseSizeBeforeCreate + 1);
        ProductSku testProductSku = productSkuList.get(productSkuList.size() - 1);
        assertThat(testProductSku.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testProductSku.getSize()).isEqualTo(DEFAULT_SIZE);
    }

    @Test
    @Transactional
    public void createProductSkuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productSkuRepository.findAll().size();

        // Create the ProductSku with an existing ID
        productSku.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductSkuMockMvc.perform(post("/api/product-skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSku)))
            .andExpect(status().isBadRequest());

        // Validate the ProductSku in the database
        List<ProductSku> productSkuList = productSkuRepository.findAll();
        assertThat(productSkuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = productSkuRepository.findAll().size();
        // set the field null
        productSku.setGender(null);

        // Create the ProductSku, which fails.

        restProductSkuMockMvc.perform(post("/api/product-skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSku)))
            .andExpect(status().isBadRequest());

        List<ProductSku> productSkuList = productSkuRepository.findAll();
        assertThat(productSkuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productSkuRepository.findAll().size();
        // set the field null
        productSku.setSize(null);

        // Create the ProductSku, which fails.

        restProductSkuMockMvc.perform(post("/api/product-skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSku)))
            .andExpect(status().isBadRequest());

        List<ProductSku> productSkuList = productSkuRepository.findAll();
        assertThat(productSkuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductSkus() throws Exception {
        // Initialize the database
        productSkuRepository.saveAndFlush(productSku);

        // Get all the productSkuList
        restProductSkuMockMvc.perform(get("/api/product-skus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSku.getId().intValue())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())));
    }
    
    @Test
    @Transactional
    public void getProductSku() throws Exception {
        // Initialize the database
        productSkuRepository.saveAndFlush(productSku);

        // Get the productSku
        restProductSkuMockMvc.perform(get("/api/product-skus/{id}", productSku.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productSku.getId().intValue()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductSku() throws Exception {
        // Get the productSku
        restProductSkuMockMvc.perform(get("/api/product-skus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductSku() throws Exception {
        // Initialize the database
        productSkuRepository.saveAndFlush(productSku);

        int databaseSizeBeforeUpdate = productSkuRepository.findAll().size();

        // Update the productSku
        ProductSku updatedProductSku = productSkuRepository.findById(productSku.getId()).get();
        // Disconnect from session so that the updates on updatedProductSku are not directly saved in db
        em.detach(updatedProductSku);
        updatedProductSku
            .gender(UPDATED_GENDER)
            .size(UPDATED_SIZE);

        restProductSkuMockMvc.perform(put("/api/product-skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductSku)))
            .andExpect(status().isOk());

        // Validate the ProductSku in the database
        List<ProductSku> productSkuList = productSkuRepository.findAll();
        assertThat(productSkuList).hasSize(databaseSizeBeforeUpdate);
        ProductSku testProductSku = productSkuList.get(productSkuList.size() - 1);
        assertThat(testProductSku.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testProductSku.getSize()).isEqualTo(UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductSku() throws Exception {
        int databaseSizeBeforeUpdate = productSkuRepository.findAll().size();

        // Create the ProductSku

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSkuMockMvc.perform(put("/api/product-skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSku)))
            .andExpect(status().isBadRequest());

        // Validate the ProductSku in the database
        List<ProductSku> productSkuList = productSkuRepository.findAll();
        assertThat(productSkuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductSku() throws Exception {
        // Initialize the database
        productSkuRepository.saveAndFlush(productSku);

        int databaseSizeBeforeDelete = productSkuRepository.findAll().size();

        // Delete the productSku
        restProductSkuMockMvc.perform(delete("/api/product-skus/{id}", productSku.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductSku> productSkuList = productSkuRepository.findAll();
        assertThat(productSkuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSku.class);
        ProductSku productSku1 = new ProductSku();
        productSku1.setId(1L);
        ProductSku productSku2 = new ProductSku();
        productSku2.setId(productSku1.getId());
        assertThat(productSku1).isEqualTo(productSku2);
        productSku2.setId(2L);
        assertThat(productSku1).isNotEqualTo(productSku2);
        productSku1.setId(null);
        assertThat(productSku1).isNotEqualTo(productSku2);
    }
}
