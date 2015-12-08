package com.isengardcode.votenorestaurante.web.rest;

import com.isengardcode.votenorestaurante.Application;
import com.isengardcode.votenorestaurante.domain.Voter;
import com.isengardcode.votenorestaurante.repository.VoterRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the VoterResource REST controller.
 *
 * @see VoterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VoterResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    @Inject
    private VoterRepository voterRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVoterMockMvc;

    private Voter voter;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VoterResource voterResource = new VoterResource();
        ReflectionTestUtils.setField(voterResource, "voterRepository", voterRepository);
        this.restVoterMockMvc = MockMvcBuilders.standaloneSetup(voterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        voter = new Voter();
        voter.setName(DEFAULT_NAME);
        voter.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createVoter() throws Exception {
        int databaseSizeBeforeCreate = voterRepository.findAll().size();

        // Create the Voter

        restVoterMockMvc.perform(post("/api/voters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(voter)))
                .andExpect(status().isCreated());

        // Validate the Voter in the database
        List<Voter> voters = voterRepository.findAll();
        assertThat(voters).hasSize(databaseSizeBeforeCreate + 1);
        Voter testVoter = voters.get(voters.size() - 1);
        assertThat(testVoter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVoter.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllVoters() throws Exception {
        // Initialize the database
        voterRepository.saveAndFlush(voter);

        // Get all the voters
        restVoterMockMvc.perform(get("/api/voters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(voter.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getVoter() throws Exception {
        // Initialize the database
        voterRepository.saveAndFlush(voter);

        // Get the voter
        restVoterMockMvc.perform(get("/api/voters/{id}", voter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(voter.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVoter() throws Exception {
        // Get the voter
        restVoterMockMvc.perform(get("/api/voters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoter() throws Exception {
        // Initialize the database
        voterRepository.saveAndFlush(voter);

		int databaseSizeBeforeUpdate = voterRepository.findAll().size();

        // Update the voter
        voter.setName(UPDATED_NAME);
        voter.setEmail(UPDATED_EMAIL);

        restVoterMockMvc.perform(put("/api/voters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(voter)))
                .andExpect(status().isOk());

        // Validate the Voter in the database
        List<Voter> voters = voterRepository.findAll();
        assertThat(voters).hasSize(databaseSizeBeforeUpdate);
        Voter testVoter = voters.get(voters.size() - 1);
        assertThat(testVoter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVoter.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void deleteVoter() throws Exception {
        // Initialize the database
        voterRepository.saveAndFlush(voter);

		int databaseSizeBeforeDelete = voterRepository.findAll().size();

        // Get the voter
        restVoterMockMvc.perform(delete("/api/voters/{id}", voter.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Voter> voters = voterRepository.findAll();
        assertThat(voters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
