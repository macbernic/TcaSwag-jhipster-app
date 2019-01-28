package io.github.tcaswag.web.rest;

import io.github.tcaswag.TcaSwagApp;

import io.github.tcaswag.domain.MemberOrderItem;
import io.github.tcaswag.repository.MemberOrderItemRepository;
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

/**
 * Test class for the MemberOrderItemResource REST controller.
 *
 * @see MemberOrderItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TcaSwagApp.class)
public class MemberOrderItemResourceIntTest {

    private static final String DEFAULT_CUSTOM_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_TEXT = "BBBBBBBBBB";

    private static final Float DEFAULT_APPLIED_PRICE = 1F;
    private static final Float UPDATED_APPLIED_PRICE = 2F;

    @Autowired
    private MemberOrderItemRepository memberOrderItemRepository;

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

    private MockMvc restMemberOrderItemMockMvc;

    private MemberOrderItem memberOrderItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MemberOrderItemResource memberOrderItemResource = new MemberOrderItemResource(memberOrderItemRepository);
        this.restMemberOrderItemMockMvc = MockMvcBuilders.standaloneSetup(memberOrderItemResource)
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
    public static MemberOrderItem createEntity(EntityManager em) {
        MemberOrderItem memberOrderItem = new MemberOrderItem()
            .customText(DEFAULT_CUSTOM_TEXT)
            .appliedPrice(DEFAULT_APPLIED_PRICE);
        return memberOrderItem;
    }

    @Before
    public void initTest() {
        memberOrderItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createMemberOrderItem() throws Exception {
        int databaseSizeBeforeCreate = memberOrderItemRepository.findAll().size();

        // Create the MemberOrderItem
        restMemberOrderItemMockMvc.perform(post("/api/member-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberOrderItem)))
            .andExpect(status().isCreated());

        // Validate the MemberOrderItem in the database
        List<MemberOrderItem> memberOrderItemList = memberOrderItemRepository.findAll();
        assertThat(memberOrderItemList).hasSize(databaseSizeBeforeCreate + 1);
        MemberOrderItem testMemberOrderItem = memberOrderItemList.get(memberOrderItemList.size() - 1);
        assertThat(testMemberOrderItem.getCustomText()).isEqualTo(DEFAULT_CUSTOM_TEXT);
        assertThat(testMemberOrderItem.getAppliedPrice()).isEqualTo(DEFAULT_APPLIED_PRICE);
    }

    @Test
    @Transactional
    public void createMemberOrderItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = memberOrderItemRepository.findAll().size();

        // Create the MemberOrderItem with an existing ID
        memberOrderItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberOrderItemMockMvc.perform(post("/api/member-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberOrderItem)))
            .andExpect(status().isBadRequest());

        // Validate the MemberOrderItem in the database
        List<MemberOrderItem> memberOrderItemList = memberOrderItemRepository.findAll();
        assertThat(memberOrderItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAppliedPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberOrderItemRepository.findAll().size();
        // set the field null
        memberOrderItem.setAppliedPrice(null);

        // Create the MemberOrderItem, which fails.

        restMemberOrderItemMockMvc.perform(post("/api/member-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberOrderItem)))
            .andExpect(status().isBadRequest());

        List<MemberOrderItem> memberOrderItemList = memberOrderItemRepository.findAll();
        assertThat(memberOrderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMemberOrderItems() throws Exception {
        // Initialize the database
        memberOrderItemRepository.saveAndFlush(memberOrderItem);

        // Get all the memberOrderItemList
        restMemberOrderItemMockMvc.perform(get("/api/member-order-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberOrderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].customText").value(hasItem(DEFAULT_CUSTOM_TEXT.toString())))
            .andExpect(jsonPath("$.[*].appliedPrice").value(hasItem(DEFAULT_APPLIED_PRICE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getMemberOrderItem() throws Exception {
        // Initialize the database
        memberOrderItemRepository.saveAndFlush(memberOrderItem);

        // Get the memberOrderItem
        restMemberOrderItemMockMvc.perform(get("/api/member-order-items/{id}", memberOrderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(memberOrderItem.getId().intValue()))
            .andExpect(jsonPath("$.customText").value(DEFAULT_CUSTOM_TEXT.toString()))
            .andExpect(jsonPath("$.appliedPrice").value(DEFAULT_APPLIED_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMemberOrderItem() throws Exception {
        // Get the memberOrderItem
        restMemberOrderItemMockMvc.perform(get("/api/member-order-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMemberOrderItem() throws Exception {
        // Initialize the database
        memberOrderItemRepository.saveAndFlush(memberOrderItem);

        int databaseSizeBeforeUpdate = memberOrderItemRepository.findAll().size();

        // Update the memberOrderItem
        MemberOrderItem updatedMemberOrderItem = memberOrderItemRepository.findById(memberOrderItem.getId()).get();
        // Disconnect from session so that the updates on updatedMemberOrderItem are not directly saved in db
        em.detach(updatedMemberOrderItem);
        updatedMemberOrderItem
            .customText(UPDATED_CUSTOM_TEXT)
            .appliedPrice(UPDATED_APPLIED_PRICE);

        restMemberOrderItemMockMvc.perform(put("/api/member-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMemberOrderItem)))
            .andExpect(status().isOk());

        // Validate the MemberOrderItem in the database
        List<MemberOrderItem> memberOrderItemList = memberOrderItemRepository.findAll();
        assertThat(memberOrderItemList).hasSize(databaseSizeBeforeUpdate);
        MemberOrderItem testMemberOrderItem = memberOrderItemList.get(memberOrderItemList.size() - 1);
        assertThat(testMemberOrderItem.getCustomText()).isEqualTo(UPDATED_CUSTOM_TEXT);
        assertThat(testMemberOrderItem.getAppliedPrice()).isEqualTo(UPDATED_APPLIED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingMemberOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = memberOrderItemRepository.findAll().size();

        // Create the MemberOrderItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberOrderItemMockMvc.perform(put("/api/member-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberOrderItem)))
            .andExpect(status().isBadRequest());

        // Validate the MemberOrderItem in the database
        List<MemberOrderItem> memberOrderItemList = memberOrderItemRepository.findAll();
        assertThat(memberOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMemberOrderItem() throws Exception {
        // Initialize the database
        memberOrderItemRepository.saveAndFlush(memberOrderItem);

        int databaseSizeBeforeDelete = memberOrderItemRepository.findAll().size();

        // Delete the memberOrderItem
        restMemberOrderItemMockMvc.perform(delete("/api/member-order-items/{id}", memberOrderItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MemberOrderItem> memberOrderItemList = memberOrderItemRepository.findAll();
        assertThat(memberOrderItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberOrderItem.class);
        MemberOrderItem memberOrderItem1 = new MemberOrderItem();
        memberOrderItem1.setId(1L);
        MemberOrderItem memberOrderItem2 = new MemberOrderItem();
        memberOrderItem2.setId(memberOrderItem1.getId());
        assertThat(memberOrderItem1).isEqualTo(memberOrderItem2);
        memberOrderItem2.setId(2L);
        assertThat(memberOrderItem1).isNotEqualTo(memberOrderItem2);
        memberOrderItem1.setId(null);
        assertThat(memberOrderItem1).isNotEqualTo(memberOrderItem2);
    }
}
