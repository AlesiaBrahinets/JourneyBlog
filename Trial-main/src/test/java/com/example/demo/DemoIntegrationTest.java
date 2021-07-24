package com.example.demo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import com.example.demo.entity.Journey;
import com.example.demo.repository.IJourneyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DemoIntegrationTest {

	@Autowired
	IJourneyRepository iJourneyRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@AfterEach
	public void resetDb() {
		iJourneyRepository.deleteAll();
	}

	@Test
	public void forPostController() throws Exception {

		Journey journey_1 = createTestJourney("Alesia", "Nepal", 2017L, "It was a wonderfull");

		mockMvc.perform(post("/journeys")
				.content(objectMapper.writeValueAsString(journey_1))
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.name").value("Alesia"))
				.andExpect(jsonPath("$.country").value("Nepal"))
				.andExpect(jsonPath("$.year").value(2017L))
				.andExpect(jsonPath("$.description").value("It was a wonderfull"))
				.andReturn().getResponse().equals(new ResponseEntity<>(saveJourney("Alesia", "Nepal", 2017L, "It was a wonderfull"),HttpStatus.CREATED));
	}

	@Test
	public void forGetIdController() throws Exception {
		Journey journey_1 = saveJourney("Alesia", "Nepal", 2017L, "It was a wonderfull");
		Long id = journey_1.getId();
		mockMvc.perform(get("/journeys/{id}", id)).andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(journey_1)))
				.andExpect(jsonPath("$.id").value(id)).andExpect(jsonPath("$.name").value("Alesia"))
				.andExpect(jsonPath("$.country").value("Nepal")).andExpect(jsonPath("$.year").value(2017L))
				.andExpect(jsonPath("$.description").value("It was a wonderfull"));
	}

	@Test
	public void forGetAllJourneyController() throws Exception {

		mockMvc.perform(get("/journeys")).andExpect(status().isNoContent());
		Journey journey_1 = saveJourney("Alesia", "Nepal", 2017L, "It was a wonderfull");
		List<Journey> list = new ArrayList<>();
		list.add(journey_1);
		mockMvc.perform(get("/journeys"))
		       .andExpect(status().isOk())
		       .andReturn().getResponse().equals(new ResponseEntity<List<Journey>>(list, HttpStatus.OK));
	}

	@Test
	public void forUpdateJourneyTest() throws Exception {
		Journey journey_1 = saveJourney("Alesia", "Nepal", 2017L, "It was a wonderfull");
		Long id = journey_1.getId();
		Journey journey_2 = createTestJourney("Olga", "Russia", 2010L, "It was a wonderfull");
		mockMvc.perform(put("/journeys/{id}", id)
				.content(objectMapper.writeValueAsString(journey_2))
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Olga"))
				.andExpect(jsonPath("$.country").value("Russia"))
				.andExpect(jsonPath("$.year").value(2010L))
				.andExpect(jsonPath("$.description").value("It was a wonderfull"))
				.andReturn().getResponse().equals(new ResponseEntity<>(saveJourney("Olga", "Russia", 2010L, "It was a wonderfull"),HttpStatus.OK));
	}

	@Test
	public void forDeleteJourneyTest() throws Exception {
		Journey journey_1 = saveJourney("Alesia", "Nepal", 2017L, "It was a wonderfull");
		Long id = journey_1.getId();
		mockMvc.perform(delete("/journeys/{id}", id))
		       .andExpect(status().isOk());
	}

	@Test
	public void forDeleteJourneyExceptionTest() throws Exception {

		mockMvc.perform(delete("/journeys/{id}", 1))
		       .andExpect(status().isNotFound());
	}

	@Test
	public void forDeleteAllJourneyTest() throws Exception {
		saveJourney("Alesia", "Nepal", 2017L, "It was a wonderfull");
		saveJourney("Olga", "Russia", 2010L, "It was a wonderfull");
		mockMvc.perform(delete("/journeys"))
		       .andExpect(status().isOk());
	}

	private Journey createTestJourney(String name, String country, Long year, String description) {
		Journey journey = new Journey();
		journey.setName(name);
		journey.setCountry(country);
		journey.setYear(year);
		journey.setDescription(description);
		return journey;
	}

	private Journey saveJourney(String name, String country, Long year, String description) {
		Journey journey = createTestJourney(name, country, year, description);
		return iJourneyRepository.save(journey);
	}

}
