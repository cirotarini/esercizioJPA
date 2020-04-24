package it.plan.eserciziojpa.web.rest;

import it.plan.eserciziojpa.EsercizioJpa032020App;
import it.plan.eserciziojpa.domain.RigaOrdine;
import it.plan.eserciziojpa.repository.RigaOrdineRepository;
import it.plan.eserciziojpa.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static it.plan.eserciziojpa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RigaOrdineResource} REST controller.
 */
@SpringBootTest(classes = EsercizioJpa032020App.class)
public class RigaOrdineResourceIT {

    private static final Integer DEFAULT_QUANTITA = 1;
    private static final Integer UPDATED_QUANTITA = 2;

    @Autowired
    private RigaOrdineRepository rigaOrdineRepository;

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

    private MockMvc restRigaOrdineMockMvc;

    private RigaOrdine rigaOrdine;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RigaOrdineResource rigaOrdineResource = new RigaOrdineResource(rigaOrdineRepository);
        this.restRigaOrdineMockMvc = MockMvcBuilders.standaloneSetup(rigaOrdineResource)
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
    public static RigaOrdine createEntity(EntityManager em) {
        RigaOrdine rigaOrdine = new RigaOrdine()
            .quantita(DEFAULT_QUANTITA);
        return rigaOrdine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RigaOrdine createUpdatedEntity(EntityManager em) {
        RigaOrdine rigaOrdine = new RigaOrdine()
            .quantita(UPDATED_QUANTITA);
        return rigaOrdine;
    }

    @BeforeEach
    public void initTest() {
        rigaOrdine = createEntity(em);
    }

    @Test
    @Transactional
    public void createRigaOrdine() throws Exception {
        int databaseSizeBeforeCreate = rigaOrdineRepository.findAll().size();

        // Create the RigaOrdine
        restRigaOrdineMockMvc.perform(post("/api/riga-ordines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rigaOrdine)))
            .andExpect(status().isCreated());

        // Validate the RigaOrdine in the database
        List<RigaOrdine> rigaOrdineList = rigaOrdineRepository.findAll();
        assertThat(rigaOrdineList).hasSize(databaseSizeBeforeCreate + 1);
        RigaOrdine testRigaOrdine = rigaOrdineList.get(rigaOrdineList.size() - 1);
        assertThat(testRigaOrdine.getQuantita()).isEqualTo(DEFAULT_QUANTITA);
    }

    @Test
    @Transactional
    public void createRigaOrdineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rigaOrdineRepository.findAll().size();

        // Create the RigaOrdine with an existing ID
        rigaOrdine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRigaOrdineMockMvc.perform(post("/api/riga-ordines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rigaOrdine)))
            .andExpect(status().isBadRequest());

        // Validate the RigaOrdine in the database
        List<RigaOrdine> rigaOrdineList = rigaOrdineRepository.findAll();
        assertThat(rigaOrdineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantitaIsRequired() throws Exception {
        int databaseSizeBeforeTest = rigaOrdineRepository.findAll().size();
        // set the field null
        rigaOrdine.setQuantita(null);

        // Create the RigaOrdine, which fails.

        restRigaOrdineMockMvc.perform(post("/api/riga-ordines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rigaOrdine)))
            .andExpect(status().isBadRequest());

        List<RigaOrdine> rigaOrdineList = rigaOrdineRepository.findAll();
        assertThat(rigaOrdineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRigaOrdines() throws Exception {
        // Initialize the database
        rigaOrdineRepository.saveAndFlush(rigaOrdine);

        // Get all the rigaOrdineList
        restRigaOrdineMockMvc.perform(get("/api/riga-ordines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rigaOrdine.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantita").value(hasItem(DEFAULT_QUANTITA)));
    }
    
    @Test
    @Transactional
    public void getRigaOrdine() throws Exception {
        // Initialize the database
        rigaOrdineRepository.saveAndFlush(rigaOrdine);

        // Get the rigaOrdine
        restRigaOrdineMockMvc.perform(get("/api/riga-ordines/{id}", rigaOrdine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rigaOrdine.getId().intValue()))
            .andExpect(jsonPath("$.quantita").value(DEFAULT_QUANTITA));
    }

    @Test
    @Transactional
    public void getNonExistingRigaOrdine() throws Exception {
        // Get the rigaOrdine
        restRigaOrdineMockMvc.perform(get("/api/riga-ordines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRigaOrdine() throws Exception {
        // Initialize the database
        rigaOrdineRepository.saveAndFlush(rigaOrdine);

        int databaseSizeBeforeUpdate = rigaOrdineRepository.findAll().size();

        // Update the rigaOrdine
        RigaOrdine updatedRigaOrdine = rigaOrdineRepository.findById(rigaOrdine.getId()).get();
        // Disconnect from session so that the updates on updatedRigaOrdine are not directly saved in db
        em.detach(updatedRigaOrdine);
        updatedRigaOrdine
            .quantita(UPDATED_QUANTITA);

        restRigaOrdineMockMvc.perform(put("/api/riga-ordines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRigaOrdine)))
            .andExpect(status().isOk());

        // Validate the RigaOrdine in the database
        List<RigaOrdine> rigaOrdineList = rigaOrdineRepository.findAll();
        assertThat(rigaOrdineList).hasSize(databaseSizeBeforeUpdate);
        RigaOrdine testRigaOrdine = rigaOrdineList.get(rigaOrdineList.size() - 1);
        assertThat(testRigaOrdine.getQuantita()).isEqualTo(UPDATED_QUANTITA);
    }

    @Test
    @Transactional
    public void updateNonExistingRigaOrdine() throws Exception {
        int databaseSizeBeforeUpdate = rigaOrdineRepository.findAll().size();

        // Create the RigaOrdine

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRigaOrdineMockMvc.perform(put("/api/riga-ordines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rigaOrdine)))
            .andExpect(status().isBadRequest());

        // Validate the RigaOrdine in the database
        List<RigaOrdine> rigaOrdineList = rigaOrdineRepository.findAll();
        assertThat(rigaOrdineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRigaOrdine() throws Exception {
        // Initialize the database
        rigaOrdineRepository.saveAndFlush(rigaOrdine);

        int databaseSizeBeforeDelete = rigaOrdineRepository.findAll().size();

        // Delete the rigaOrdine
        restRigaOrdineMockMvc.perform(delete("/api/riga-ordines/{id}", rigaOrdine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RigaOrdine> rigaOrdineList = rigaOrdineRepository.findAll();
        assertThat(rigaOrdineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
