package io.github.tcaswag.web.rest;

import io.github.tcaswag.TcaSwagApp;

import io.github.tcaswag.domain.ProductAsset;
import io.github.tcaswag.domain.Product;
import io.github.tcaswag.repository.ProductAssetRepository;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.tcaswag.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductAssetResource REST controller.
 *
 * @see ProductAssetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TcaSwagApp.class)
public class ProductAssetResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ASSET = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ASSET = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ASSET_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ASSET_CONTENT_TYPE = "image/png";

    @Autowired
    private ProductAssetRepository productAssetRepository;

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

    private MockMvc restProductAssetMockMvc;

    private ProductAsset productAsset;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductAssetResource productAssetResource = new ProductAssetResource(productAssetRepository);
        this.restProductAssetMockMvc = MockMvcBuilders.standaloneSetup(productAssetResource)
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
    public static ProductAsset createEntity(EntityManager em) {
        ProductAsset productAsset = new ProductAsset()
            .title(DEFAULT_TITLE)
            .asset(DEFAULT_ASSET)
            .assetContentType(DEFAULT_ASSET_CONTENT_TYPE);
        // Add required entity
        Product product = ProductResourceIntTest.createEntity(em);
        em.persist(product);
        em.flush();
        productAsset.setProduct(product);
        return productAsset;
    }

    @Before
    public void initTest() {
        productAsset = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductAsset() throws Exception {
        int databaseSizeBeforeCreate = productAssetRepository.findAll().size();

        // Create the ProductAsset
        restProductAssetMockMvc.perform(post("/api/product-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAsset)))
            .andExpect(status().isCreated());

        // Validate the ProductAsset in the database
        List<ProductAsset> productAssetList = productAssetRepository.findAll();
        assertThat(productAssetList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAsset testProductAsset = productAssetList.get(productAssetList.size() - 1);
        assertThat(testProductAsset.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProductAsset.getAsset()).isEqualTo(DEFAULT_ASSET);
        assertThat(testProductAsset.getAssetContentType()).isEqualTo(DEFAULT_ASSET_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createProductAssetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productAssetRepository.findAll().size();

        // Create the ProductAsset with an existing ID
        productAsset.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAssetMockMvc.perform(post("/api/product-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAsset)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAsset in the database
        List<ProductAsset> productAssetList = productAssetRepository.findAll();
        assertThat(productAssetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = productAssetRepository.findAll().size();
        // set the field null
        productAsset.setTitle(null);

        // Create the ProductAsset, which fails.

        restProductAssetMockMvc.perform(post("/api/product-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAsset)))
            .andExpect(status().isBadRequest());

        List<ProductAsset> productAssetList = productAssetRepository.findAll();
        assertThat(productAssetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductAssets() throws Exception {
        // Initialize the database
        productAssetRepository.saveAndFlush(productAsset);

        // Get all the productAssetList
        restProductAssetMockMvc.perform(get("/api/product-assets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAsset.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].assetContentType").value(hasItem(DEFAULT_ASSET_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].asset").value(hasItem(Base64Utils.encodeToString(DEFAULT_ASSET))));
    }
    
    @Test
    @Transactional
    public void getProductAsset() throws Exception {
        // Initialize the database
        productAssetRepository.saveAndFlush(productAsset);

        // Get the productAsset
        restProductAssetMockMvc.perform(get("/api/product-assets/{id}", productAsset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productAsset.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.assetContentType").value(DEFAULT_ASSET_CONTENT_TYPE))
            .andExpect(jsonPath("$.asset").value(Base64Utils.encodeToString(DEFAULT_ASSET)));
    }

    @Test
    @Transactional
    public void getNonExistingProductAsset() throws Exception {
        // Get the productAsset
        restProductAssetMockMvc.perform(get("/api/product-assets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductAsset() throws Exception {
        // Initialize the database
        productAssetRepository.saveAndFlush(productAsset);

        int databaseSizeBeforeUpdate = productAssetRepository.findAll().size();

        // Update the productAsset
        ProductAsset updatedProductAsset = productAssetRepository.findById(productAsset.getId()).get();
        // Disconnect from session so that the updates on updatedProductAsset are not directly saved in db
        em.detach(updatedProductAsset);
        updatedProductAsset
            .title(UPDATED_TITLE)
            .asset(UPDATED_ASSET)
            .assetContentType(UPDATED_ASSET_CONTENT_TYPE);

        restProductAssetMockMvc.perform(put("/api/product-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductAsset)))
            .andExpect(status().isOk());

        // Validate the ProductAsset in the database
        List<ProductAsset> productAssetList = productAssetRepository.findAll();
        assertThat(productAssetList).hasSize(databaseSizeBeforeUpdate);
        ProductAsset testProductAsset = productAssetList.get(productAssetList.size() - 1);
        assertThat(testProductAsset.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProductAsset.getAsset()).isEqualTo(UPDATED_ASSET);
        assertThat(testProductAsset.getAssetContentType()).isEqualTo(UPDATED_ASSET_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductAsset() throws Exception {
        int databaseSizeBeforeUpdate = productAssetRepository.findAll().size();

        // Create the ProductAsset

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAssetMockMvc.perform(put("/api/product-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAsset)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAsset in the database
        List<ProductAsset> productAssetList = productAssetRepository.findAll();
        assertThat(productAssetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductAsset() throws Exception {
        // Initialize the database
        productAssetRepository.saveAndFlush(productAsset);

        int databaseSizeBeforeDelete = productAssetRepository.findAll().size();

        // Get the productAsset
        restProductAssetMockMvc.perform(delete("/api/product-assets/{id}", productAsset.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductAsset> productAssetList = productAssetRepository.findAll();
        assertThat(productAssetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAsset.class);
        ProductAsset productAsset1 = new ProductAsset();
        productAsset1.setId(1L);
        ProductAsset productAsset2 = new ProductAsset();
        productAsset2.setId(productAsset1.getId());
        assertThat(productAsset1).isEqualTo(productAsset2);
        productAsset2.setId(2L);
        assertThat(productAsset1).isNotEqualTo(productAsset2);
        productAsset1.setId(null);
        assertThat(productAsset1).isNotEqualTo(productAsset2);
    }
}
