package com.isengardcode.votenorestaurante.web.rest;

import com.isengardcode.votenorestaurante.Application;
import com.isengardcode.votenorestaurante.domain.Restaurant;
import com.isengardcode.votenorestaurante.repository.RestaurantRepository;
import com.isengardcode.votenorestaurante.web.rest.dto.RestaurantDTO;
import com.isengardcode.votenorestaurante.web.rest.mapper.RestaurantMapper;

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
 * Test class for the RestaurantResource REST controller.
 *
 * @see RestaurantResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RestaurantResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Long DEFAULT_VOTES = 1L;
    private static final Long UPDATED_VOTES = 2L;

    @Inject
    private RestaurantRepository restaurantRepository;

    @Inject
    private RestaurantMapper restaurantMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRestaurantMockMvc;

    private Restaurant restaurant;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RestaurantResource restaurantResource = new RestaurantResource();
        ReflectionTestUtils.setField(restaurantResource, "restaurantRepository", restaurantRepository);
        ReflectionTestUtils.setField(restaurantResource, "restaurantMapper", restaurantMapper);
        this.restRestaurantMockMvc = MockMvcBuilders.standaloneSetup(restaurantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        restaurant = new Restaurant();
        restaurant.setName(DEFAULT_NAME);
        restaurant.setVotes(DEFAULT_VOTES);
    }

    @Test
    @Transactional
    public void createRestaurant() throws Exception {
        int databaseSizeBeforeCreate = restaurantRepository.findAll().size();

        // Create the Restaurant
        RestaurantDTO restaurantDTO = restaurantMapper.restaurantToRestaurantDTO(restaurant);

        restRestaurantMockMvc.perform(post("/api/restaurants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
                .andExpect(status().isCreated());

        // Validate the Restaurant in the database
        List<Restaurant> restaurants = restaurantRepository.findAll();
        assertThat(restaurants).hasSize(databaseSizeBeforeCreate + 1);
        Restaurant testRestaurant = restaurants.get(restaurants.size() - 1);
        assertThat(testRestaurant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRestaurant.getVotes()).isEqualTo(DEFAULT_VOTES);
    }

    @Test
    @Transactional
    public void getAllRestaurants() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurants
        restRestaurantMockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(restaurant.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].votes").value(hasItem(DEFAULT_VOTES.intValue())));
    }

    @Test
    @Transactional
    public void getRestaurant() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get the restaurant
        restRestaurantMockMvc.perform(get("/api/restaurants/{id}", restaurant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(restaurant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.votes").value(DEFAULT_VOTES.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRestaurant() throws Exception {
        // Get the restaurant
        restRestaurantMockMvc.perform(get("/api/restaurants/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRestaurant() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

		int databaseSizeBeforeUpdate = restaurantRepository.findAll().size();

        // Update the restaurant
        restaurant.setName(UPDATED_NAME);
        restaurant.setVotes(UPDATED_VOTES);
        RestaurantDTO restaurantDTO = restaurantMapper.restaurantToRestaurantDTO(restaurant);

        restRestaurantMockMvc.perform(put("/api/restaurants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
                .andExpect(status().isOk());

        // Validate the Restaurant in the database
        List<Restaurant> restaurants = restaurantRepository.findAll();
        assertThat(restaurants).hasSize(databaseSizeBeforeUpdate);
        Restaurant testRestaurant = restaurants.get(restaurants.size() - 1);
        assertThat(testRestaurant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRestaurant.getVotes()).isEqualTo(UPDATED_VOTES);
    }

    @Test
    @Transactional
    public void deleteRestaurant() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

		int databaseSizeBeforeDelete = restaurantRepository.findAll().size();

        // Get the restaurant
        restRestaurantMockMvc.perform(delete("/api/restaurants/{id}", restaurant.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Restaurant> restaurants = restaurantRepository.findAll();
        assertThat(restaurants).hasSize(databaseSizeBeforeDelete - 1);
    }
}
