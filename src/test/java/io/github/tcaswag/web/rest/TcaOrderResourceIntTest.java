package io.github.tcaswag.web.rest;

import io.github.tcaswag.TcaSwagApp;

import io.github.tcaswag.domain.TcaOrder;
import io.github.tcaswag.domain.ProductSku;
import io.github.tcaswag.domain.TcaMember;
import io.github.tcaswag.repository.TcaOrderRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static io.github.tcaswag.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.tcaswag.domain.enumeration.OrderStatus;
/**
 * Test class for the TcaOrderResource REST controller.
 *
 * @see TcaOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TcaSwagApp.class)
public class TcaOrderResourceIntTest {

    private static final Integer DEFAULT_ORDER_ID = 1;
    private static final Integer UPDATED_ORDER_ID = 2;

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.DRAFT;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.SUBMITTED;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_FIRST = false;
    private static final Boolean UPDATED_FIRST = true;

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;

    @Autowired
    private TcaOrderRepository tcaOrderRepository;

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

    private MockMvc restTcaOrderMockMvc;

    private TcaOrder tcaOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TcaOrderResource tcaOrderResource = new TcaOrderResource(tcaOrderRepository);
        this.restTcaOrderMockMvc = MockMvcBuilders.standaloneSetup(tcaOrderResource)
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
    public static TcaOrder createEntity(EntityManager em) {
        TcaOrder tcaOrder = new TcaOrder()
            .orderId(DEFAULT_ORDER_ID)
            .status(DEFAULT_STATUS)
            .date(DEFAULT_DATE)
            .first(DEFAULT_FIRST)
            .amount(DEFAULT_AMOUNT);
        // Add required entity
        ProductSku productSku = ProductSkuResourceIntTest.createEntity(em);
        em.persist(productSku);
        em.flush();
        tcaOrder.getProductSkuses().add(productSku);
        // Add required entity
        TcaMember tcaMember = TcaMemberResourceIntTest.createEntity(em);
        em.persist(tcaMember);
        em.flush();
        tcaOrder.setTcaMember(tcaMember);
        return tcaOrder;
    }

    @Before
    public void initTest() {
        tcaOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createTcaOrder() throws Exception {
        int databaseSizeBeforeCreate = tcaOrderRepository.findAll().size();

        // Create the TcaOrder
        restTcaOrderMockMvc.perform(post("/api/tca-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tcaOrder)))
            .andExpect(status().isCreated());

        // Validate the TcaOrder in the database
        List<TcaOrder> tcaOrderList = tcaOrderRepository.findAll();
        assertThat(tcaOrderList).hasSize(databaseSizeBeforeCreate + 1);
        TcaOrder testTcaOrder = tcaOrderList.get(tcaOrderList.size() - 1);
        assertThat(testTcaOrder.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testTcaOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTcaOrder.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTcaOrder.isFirst()).isEqualTo(DEFAULT_FIRST);
        assertThat(testTcaOrder.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createTcaOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tcaOrderRepository.findAll().size();

        // Create the TcaOrder with an existing ID
        tcaOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTcaOrderMockMvc.perform(post("/api/tca-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tcaOrder)))
            .andExpect(status().isBadRequest());

        // Validate the TcaOrder in the database
        List<TcaOrder> tcaOrderList = tcaOrderRepository.findAll();
        assertThat(tcaOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrderIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcaOrderRepository.findAll().size();
        // set the field null
        tcaOrder.setOrderId(null);

        // Create the TcaOrder, which fails.

        restTcaOrderMockMvc.perform(post("/api/tca-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tcaOrder)))
            .andExpect(status().isBadRequest());

        List<TcaOrder> tcaOrderList = tcaOrderRepository.findAll();
        assertThat(tcaOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcaOrderRepository.findAll().size();
        // set the field null
        tcaOrder.setStatus(null);

        // Create the TcaOrder, which fails.

        restTcaOrderMockMvc.perform(post("/api/tca-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tcaOrder)))
            .andExpect(status().isBadRequest());

        List<TcaOrder> tcaOrderList = tcaOrderRepository.findAll();
        assertThat(tcaOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcaOrderRepository.findAll().size();
        // set the field null
        tcaOrder.setDate(null);

        // Create the TcaOrder, which fails.

        restTcaOrderMockMvc.perform(post("/api/tca-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tcaOrder)))
            .andExpect(status().isBadRequest());

        List<TcaOrder> tcaOrderList = tcaOrderRepository.findAll();
        assertThat(tcaOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcaOrderRepository.findAll().size();
        // set the field null
        tcaOrder.setFirst(null);

        // Create the TcaOrder, which fails.

        restTcaOrderMockMvc.perform(post("/api/tca-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tcaOrder)))
            .andExpect(status().isBadRequest());

        List<TcaOrder> tcaOrderList = tcaOrderRepository.findAll();
        assertThat(tcaOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTcaOrders() throws Exception {
        // Initialize the database
        tcaOrderRepository.saveAndFlush(tcaOrder);

        // Get all the tcaOrderList
        restTcaOrderMockMvc.perform(get("/api/tca-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tcaOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].first").value(hasItem(DEFAULT_FIRST.booleanValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTcaOrder() throws Exception {
        // Initialize the database
        tcaOrderRepository.saveAndFlush(tcaOrder);

        // Get the tcaOrder
        restTcaOrderMockMvc.perform(get("/api/tca-orders/{id}", tcaOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tcaOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.first").value(DEFAULT_FIRST.booleanValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTcaOrder() throws Exception {
        // Get the tcaOrder
        restTcaOrderMockMvc.perform(get("/api/tca-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTcaOrder() throws Exception {
        // Initialize the database
        tcaOrderRepository.saveAndFlush(tcaOrder);

        int databaseSizeBeforeUpdate = tcaOrderRepository.findAll().size();

        // Update the tcaOrder
        TcaOrder updatedTcaOrder = tcaOrderRepository.findById(tcaOrder.getId()).get();
        // Disconnect from session so that the updates on updatedTcaOrder are not directly saved in db
        em.detach(updatedTcaOrder);
        updatedTcaOrder
            .orderId(UPDATED_ORDER_ID)
            .status(UPDATED_STATUS)
            .date(UPDATED_DATE)
            .first(UPDATED_FIRST)
            .amount(UPDATED_AMOUNT);

        restTcaOrderMockMvc.perform(put("/api/tca-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTcaOrder)))
            .andExpect(status().isOk());

        // Validate the TcaOrder in the database
        List<TcaOrder> tcaOrderList = tcaOrderRepository.findAll();
        assertThat(tcaOrderList).hasSize(databaseSizeBeforeUpdate);
        TcaOrder testTcaOrder = tcaOrderList.get(tcaOrderList.size() - 1);
        assertThat(testTcaOrder.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testTcaOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTcaOrder.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTcaOrder.isFirst()).isEqualTo(UPDATED_FIRST);
        assertThat(testTcaOrder.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingTcaOrder() throws Exception {
        int databaseSizeBeforeUpdate = tcaOrderRepository.findAll().size();

        // Create the TcaOrder

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTcaOrderMockMvc.perform(put("/api/tca-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tcaOrder)))
            .andExpect(status().isBadRequest());

        // Validate the TcaOrder in the database
        List<TcaOrder> tcaOrderList = tcaOrderRepository.findAll();
        assertThat(tcaOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTcaOrder() throws Exception {
        // Initialize the database
        tcaOrderRepository.saveAndFlush(tcaOrder);

        int databaseSizeBeforeDelete = tcaOrderRepository.findAll().size();

        // Get the tcaOrder
        restTcaOrderMockMvc.perform(delete("/api/tca-orders/{id}", tcaOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TcaOrder> tcaOrderList = tcaOrderRepository.findAll();
        assertThat(tcaOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TcaOrder.class);
        TcaOrder tcaOrder1 = new TcaOrder();
        tcaOrder1.setId(1L);
        TcaOrder tcaOrder2 = new TcaOrder();
        tcaOrder2.setId(tcaOrder1.getId());
        assertThat(tcaOrder1).isEqualTo(tcaOrder2);
        tcaOrder2.setId(2L);
        assertThat(tcaOrder1).isNotEqualTo(tcaOrder2);
        tcaOrder1.setId(null);
        assertThat(tcaOrder1).isNotEqualTo(tcaOrder2);
    }
}
