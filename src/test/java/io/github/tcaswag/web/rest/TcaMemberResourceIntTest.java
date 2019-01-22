package io.github.tcaswag.web.rest;

import io.github.tcaswag.TcaSwagApp;

import io.github.tcaswag.domain.TcaMember;
import io.github.tcaswag.repository.TcaMemberRepository;
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
 * Test class for the TcaMemberResource REST controller.
 *
 * @see TcaMemberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TcaSwagApp.class)
public class TcaMemberResourceIntTest {

    private static final String DEFAULT_NICKNAME = "AAAAAAAAAA";
    private static final String UPDATED_NICKNAME = "BBBBBBBBBB";

    @Autowired
    private TcaMemberRepository tcaMemberRepository;

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

    private MockMvc restTcaMemberMockMvc;

    private TcaMember tcaMember;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TcaMemberResource tcaMemberResource = new TcaMemberResource(tcaMemberRepository);
        this.restTcaMemberMockMvc = MockMvcBuilders.standaloneSetup(tcaMemberResource)
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
    public static TcaMember createEntity(EntityManager em) {
        TcaMember tcaMember = new TcaMember()
            .nickname(DEFAULT_NICKNAME);
        return tcaMember;
    }

    @Before
    public void initTest() {
        tcaMember = createEntity(em);
    }

    @Test
    @Transactional
    public void createTcaMember() throws Exception {
        int databaseSizeBeforeCreate = tcaMemberRepository.findAll().size();

        // Create the TcaMember
        restTcaMemberMockMvc.perform(post("/api/tca-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tcaMember)))
            .andExpect(status().isCreated());

        // Validate the TcaMember in the database
        List<TcaMember> tcaMemberList = tcaMemberRepository.findAll();
        assertThat(tcaMemberList).hasSize(databaseSizeBeforeCreate + 1);
        TcaMember testTcaMember = tcaMemberList.get(tcaMemberList.size() - 1);
        assertThat(testTcaMember.getNickname()).isEqualTo(DEFAULT_NICKNAME);
    }

    @Test
    @Transactional
    public void createTcaMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tcaMemberRepository.findAll().size();

        // Create the TcaMember with an existing ID
        tcaMember.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTcaMemberMockMvc.perform(post("/api/tca-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tcaMember)))
            .andExpect(status().isBadRequest());

        // Validate the TcaMember in the database
        List<TcaMember> tcaMemberList = tcaMemberRepository.findAll();
        assertThat(tcaMemberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNicknameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcaMemberRepository.findAll().size();
        // set the field null
        tcaMember.setNickname(null);

        // Create the TcaMember, which fails.

        restTcaMemberMockMvc.perform(post("/api/tca-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tcaMember)))
            .andExpect(status().isBadRequest());

        List<TcaMember> tcaMemberList = tcaMemberRepository.findAll();
        assertThat(tcaMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTcaMembers() throws Exception {
        // Initialize the database
        tcaMemberRepository.saveAndFlush(tcaMember);

        // Get all the tcaMemberList
        restTcaMemberMockMvc.perform(get("/api/tca-members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tcaMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].nickname").value(hasItem(DEFAULT_NICKNAME.toString())));
    }
    
    @Test
    @Transactional
    public void getTcaMember() throws Exception {
        // Initialize the database
        tcaMemberRepository.saveAndFlush(tcaMember);

        // Get the tcaMember
        restTcaMemberMockMvc.perform(get("/api/tca-members/{id}", tcaMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tcaMember.getId().intValue()))
            .andExpect(jsonPath("$.nickname").value(DEFAULT_NICKNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTcaMember() throws Exception {
        // Get the tcaMember
        restTcaMemberMockMvc.perform(get("/api/tca-members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTcaMember() throws Exception {
        // Initialize the database
        tcaMemberRepository.saveAndFlush(tcaMember);

        int databaseSizeBeforeUpdate = tcaMemberRepository.findAll().size();

        // Update the tcaMember
        TcaMember updatedTcaMember = tcaMemberRepository.findById(tcaMember.getId()).get();
        // Disconnect from session so that the updates on updatedTcaMember are not directly saved in db
        em.detach(updatedTcaMember);
        updatedTcaMember
            .nickname(UPDATED_NICKNAME);

        restTcaMemberMockMvc.perform(put("/api/tca-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTcaMember)))
            .andExpect(status().isOk());

        // Validate the TcaMember in the database
        List<TcaMember> tcaMemberList = tcaMemberRepository.findAll();
        assertThat(tcaMemberList).hasSize(databaseSizeBeforeUpdate);
        TcaMember testTcaMember = tcaMemberList.get(tcaMemberList.size() - 1);
        assertThat(testTcaMember.getNickname()).isEqualTo(UPDATED_NICKNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTcaMember() throws Exception {
        int databaseSizeBeforeUpdate = tcaMemberRepository.findAll().size();

        // Create the TcaMember

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTcaMemberMockMvc.perform(put("/api/tca-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tcaMember)))
            .andExpect(status().isBadRequest());

        // Validate the TcaMember in the database
        List<TcaMember> tcaMemberList = tcaMemberRepository.findAll();
        assertThat(tcaMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTcaMember() throws Exception {
        // Initialize the database
        tcaMemberRepository.saveAndFlush(tcaMember);

        int databaseSizeBeforeDelete = tcaMemberRepository.findAll().size();

        // Get the tcaMember
        restTcaMemberMockMvc.perform(delete("/api/tca-members/{id}", tcaMember.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TcaMember> tcaMemberList = tcaMemberRepository.findAll();
        assertThat(tcaMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TcaMember.class);
        TcaMember tcaMember1 = new TcaMember();
        tcaMember1.setId(1L);
        TcaMember tcaMember2 = new TcaMember();
        tcaMember2.setId(tcaMember1.getId());
        assertThat(tcaMember1).isEqualTo(tcaMember2);
        tcaMember2.setId(2L);
        assertThat(tcaMember1).isNotEqualTo(tcaMember2);
        tcaMember1.setId(null);
        assertThat(tcaMember1).isNotEqualTo(tcaMember2);
    }
}
