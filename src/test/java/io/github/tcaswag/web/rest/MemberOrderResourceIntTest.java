package io.github.tcaswag.web.rest;

import io.github.tcaswag.TcaSwagApp;

import io.github.tcaswag.domain.MemberOrder;
import io.github.tcaswag.domain.Member;
import io.github.tcaswag.repository.MemberOrderRepository;
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

import io.github.tcaswag.domain.enumeration.OrderStatus;
/**
 * Test class for the MemberOrderResource REST controller.
 *
 * @see MemberOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TcaSwagApp.class)
public class MemberOrderResourceIntTest {

    private static final String DEFAULT_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_ID = "BBBBBBBBBB";

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.DRAFT;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.SUBMITTED;

    private static final Boolean DEFAULT_FIRST = false;
    private static final Boolean UPDATED_FIRST = true;

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;

    @Autowired
    private MemberOrderRepository memberOrderRepository;

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

    private MockMvc restMemberOrderMockMvc;

    private MemberOrder memberOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MemberOrderResource memberOrderResource = new MemberOrderResource(memberOrderRepository);
        this.restMemberOrderMockMvc = MockMvcBuilders.standaloneSetup(memberOrderResource)
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
    public static MemberOrder createEntity(EntityManager em) {
        MemberOrder memberOrder = new MemberOrder()
            .orderId(DEFAULT_ORDER_ID)
            .status(DEFAULT_STATUS)
            .first(DEFAULT_FIRST)
            .amount(DEFAULT_AMOUNT);
        // Add required entity
        Member member = MemberResourceIntTest.createEntity(em);
        em.persist(member);
        em.flush();
        memberOrder.setTcaMember(member);
        return memberOrder;
    }

    @Before
    public void initTest() {
        memberOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createMemberOrder() throws Exception {
        int databaseSizeBeforeCreate = memberOrderRepository.findAll().size();

        // Create the MemberOrder
        restMemberOrderMockMvc.perform(post("/api/member-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberOrder)))
            .andExpect(status().isCreated());

        // Validate the MemberOrder in the database
        List<MemberOrder> memberOrderList = memberOrderRepository.findAll();
        assertThat(memberOrderList).hasSize(databaseSizeBeforeCreate + 1);
        MemberOrder testMemberOrder = memberOrderList.get(memberOrderList.size() - 1);
        assertThat(testMemberOrder.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testMemberOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMemberOrder.isFirst()).isEqualTo(DEFAULT_FIRST);
        assertThat(testMemberOrder.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createMemberOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = memberOrderRepository.findAll().size();

        // Create the MemberOrder with an existing ID
        memberOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberOrderMockMvc.perform(post("/api/member-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberOrder)))
            .andExpect(status().isBadRequest());

        // Validate the MemberOrder in the database
        List<MemberOrder> memberOrderList = memberOrderRepository.findAll();
        assertThat(memberOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrderIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberOrderRepository.findAll().size();
        // set the field null
        memberOrder.setOrderId(null);

        // Create the MemberOrder, which fails.

        restMemberOrderMockMvc.perform(post("/api/member-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberOrder)))
            .andExpect(status().isBadRequest());

        List<MemberOrder> memberOrderList = memberOrderRepository.findAll();
        assertThat(memberOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberOrderRepository.findAll().size();
        // set the field null
        memberOrder.setStatus(null);

        // Create the MemberOrder, which fails.

        restMemberOrderMockMvc.perform(post("/api/member-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberOrder)))
            .andExpect(status().isBadRequest());

        List<MemberOrder> memberOrderList = memberOrderRepository.findAll();
        assertThat(memberOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberOrderRepository.findAll().size();
        // set the field null
        memberOrder.setFirst(null);

        // Create the MemberOrder, which fails.

        restMemberOrderMockMvc.perform(post("/api/member-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberOrder)))
            .andExpect(status().isBadRequest());

        List<MemberOrder> memberOrderList = memberOrderRepository.findAll();
        assertThat(memberOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMemberOrders() throws Exception {
        // Initialize the database
        memberOrderRepository.saveAndFlush(memberOrder);

        // Get all the memberOrderList
        restMemberOrderMockMvc.perform(get("/api/member-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].first").value(hasItem(DEFAULT_FIRST.booleanValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getMemberOrder() throws Exception {
        // Initialize the database
        memberOrderRepository.saveAndFlush(memberOrder);

        // Get the memberOrder
        restMemberOrderMockMvc.perform(get("/api/member-orders/{id}", memberOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(memberOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.first").value(DEFAULT_FIRST.booleanValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMemberOrder() throws Exception {
        // Get the memberOrder
        restMemberOrderMockMvc.perform(get("/api/member-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMemberOrder() throws Exception {
        // Initialize the database
        memberOrderRepository.saveAndFlush(memberOrder);

        int databaseSizeBeforeUpdate = memberOrderRepository.findAll().size();

        // Update the memberOrder
        MemberOrder updatedMemberOrder = memberOrderRepository.findById(memberOrder.getId()).get();
        // Disconnect from session so that the updates on updatedMemberOrder are not directly saved in db
        em.detach(updatedMemberOrder);
        updatedMemberOrder
            .orderId(UPDATED_ORDER_ID)
            .status(UPDATED_STATUS)
            .first(UPDATED_FIRST)
            .amount(UPDATED_AMOUNT);

        restMemberOrderMockMvc.perform(put("/api/member-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMemberOrder)))
            .andExpect(status().isOk());

        // Validate the MemberOrder in the database
        List<MemberOrder> memberOrderList = memberOrderRepository.findAll();
        assertThat(memberOrderList).hasSize(databaseSizeBeforeUpdate);
        MemberOrder testMemberOrder = memberOrderList.get(memberOrderList.size() - 1);
        assertThat(testMemberOrder.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testMemberOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMemberOrder.isFirst()).isEqualTo(UPDATED_FIRST);
        assertThat(testMemberOrder.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingMemberOrder() throws Exception {
        int databaseSizeBeforeUpdate = memberOrderRepository.findAll().size();

        // Create the MemberOrder

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberOrderMockMvc.perform(put("/api/member-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberOrder)))
            .andExpect(status().isBadRequest());

        // Validate the MemberOrder in the database
        List<MemberOrder> memberOrderList = memberOrderRepository.findAll();
        assertThat(memberOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMemberOrder() throws Exception {
        // Initialize the database
        memberOrderRepository.saveAndFlush(memberOrder);

        int databaseSizeBeforeDelete = memberOrderRepository.findAll().size();

        // Get the memberOrder
        restMemberOrderMockMvc.perform(delete("/api/member-orders/{id}", memberOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MemberOrder> memberOrderList = memberOrderRepository.findAll();
        assertThat(memberOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberOrder.class);
        MemberOrder memberOrder1 = new MemberOrder();
        memberOrder1.setId(1L);
        MemberOrder memberOrder2 = new MemberOrder();
        memberOrder2.setId(memberOrder1.getId());
        assertThat(memberOrder1).isEqualTo(memberOrder2);
        memberOrder2.setId(2L);
        assertThat(memberOrder1).isNotEqualTo(memberOrder2);
        memberOrder1.setId(null);
        assertThat(memberOrder1).isNotEqualTo(memberOrder2);
    }
}
