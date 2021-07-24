package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.controller.JourneyController;
import com.example.demo.entity.Journey;
import com.example.demo.repository.IJourneyRepository;
import com.example.demo.repository.IWeatherRepository;

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
public class DemoApplicationTests {

	@MockBean
	IJourneyRepository iJourneyRepository;
	@Autowired
	JourneyController journeyController;
	@Autowired
	IWeatherRepository weatherRepository;

	@Test
	public void journeyControllerSaveMethodTest() throws Exception {
		Journey journey_1 = new Journey();
		journey_1.setName("Alesia");
		journey_1.setCountry("Nepal");
		journey_1.setYear(2017L);
		journey_1.setDescription("It was a wonderfull");
		Journey journey_2 = new Journey();

		when(iJourneyRepository.save(journey_1)).thenReturn(journey_1);
		assertEquals(new ResponseEntity<>(journey_1, HttpStatus.CREATED), journeyController.save(journey_1));

		doReturn(journey_1).when(iJourneyRepository).save(journey_1);
		assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), journeyController.save(journey_2));
		verify(iJourneyRepository, times(1)).save(journey_1);

	}

	@Test
	public void journeyControllerGetAllJourneysMethodTest() throws Exception {
		Journey journey_1 = new Journey();
		journey_1.setName("Alesia");
		journey_1.setCountry("Nepal");
		journey_1.setYear(2017L);
		journey_1.setDescription("It was a wonderfull");
		List<Journey> list = new ArrayList<>();

		when(iJourneyRepository.findAll()).thenReturn(null);
		assertEquals(new ResponseEntity<>(null, HttpStatus.NOT_FOUND), journeyController.getAllJourneys());

		list.add(journey_1);
		when(iJourneyRepository.findAll()).thenReturn(list);
		assertEquals(new ResponseEntity<List<Journey>>(list, HttpStatus.OK), journeyController.getAllJourneys());
		verify(iJourneyRepository, times(2)).findAll();

	}

	@Test
	public void getJourneyMethodTest() throws Exception {
		Journey journey_1 = new Journey();
		journey_1.setName("Alesia");
		journey_1.setCountry("Nepal");
		journey_1.setYear(2017L);
		journey_1.setDescription("It was a wonderfull");
		Long id = 1L;
		when(iJourneyRepository.findById(id)).thenReturn(Optional.of(journey_1));
		assertEquals(new ResponseEntity<Journey>(journey_1, HttpStatus.OK), journeyController.getJourney(id));
		Long idNew = 2L;
		assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), journeyController.getJourney(idNew));
		verify(iJourneyRepository, times(1)).findById(id);
	}

	@Test
	public void updateJourneyMethodTest() throws Exception {
		Journey journey_1 = new Journey();
		journey_1.setName("Alesia");
		journey_1.setCountry("Nepal");
		journey_1.setYear(2017L);
		journey_1.setDescription("It was a wonderfull");

		when(iJourneyRepository.save(journey_1)).thenReturn(journey_1);
		journeyController.save(journey_1);

		journey_1.setYear(2022L);
		
		assertEquals(2022L, journeyController.updateJourney(journey_1, 1L).getBody().getYear());
		verify(iJourneyRepository, times(2)).save(journey_1);
	}

}
