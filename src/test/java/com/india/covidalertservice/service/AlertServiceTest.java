package com.india.covidalertservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.time.ZonedDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.india.covidalertservice.dto.AlertStatus;
import com.india.covidalertservice.dto.StateData;
import com.india.covidalertservice.dto.SummaryData;
import com.india.covidalertservice.service.AlertService;
import com.india.covidalertservice.service.Covid19DataProvider;

public class AlertServiceTest {
	@InjectMocks
	private AlertService alertService;
	@Mock
	private Covid19DataProvider covid19DataProvider;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("When the Total No.of Confirmed cases are 8680")
	public void getAlertAboutStateTestGreen() {
		StateData stateData = new StateData();
		stateData.setTotalConfirmed(8680);

		Mockito.when(covid19DataProvider.getStateData(ArgumentMatchers.anyString())).thenReturn(stateData);

		AlertStatus status = alertService.getAlertAboutState("Mizoram");

		assertEquals("GREEN", status.getAlertLevel());
		assertEquals(Arrays.asList("Everything is normal !"), status.getMeasuresToBeTaken());
		assertEquals(stateData, status.getStateData());
		verify(covid19DataProvider).getStateData("Mizoram");

	}

	@Test
	@DisplayName("When the Total No.of Confirmed cases are 21622")
	public void getAlertAboutStateTestOrange() {
		StateData stateData = new StateData();
		stateData.setTotalConfirmed(21622);

		Mockito.when(covid19DataProvider.getStateData(ArgumentMatchers.anyString())).thenReturn(stateData);

		AlertStatus status = alertService.getAlertAboutState("Arunachal Pradesh");

		assertEquals("ORANGE", status.getAlertLevel());
		assertEquals(Arrays.asList("Only Essential Services Are Allowed", "No one should come out unnecessarly"),
				status.getMeasuresToBeTaken());
		assertEquals(stateData, status.getStateData());
		verify(covid19DataProvider).getStateData("Arunachal Pradesh");
	}

	@Test
	@DisplayName("When the Total No.of Confirmed cases are 60000")
	public void getAlertAboutStateTestRed() {
		StateData stateData = new StateData();
		stateData.setTotalConfirmed(60000);

		Mockito.when(covid19DataProvider.getStateData(ArgumentMatchers.anyString())).thenReturn(stateData);

		AlertStatus status = alertService.getAlertAboutState("Maharashtra");

		assertEquals("RED", status.getAlertLevel());
		assertEquals(Arrays.asList("Complete LockDown", "Only Medical & Food Services are allowed"),
				status.getMeasuresToBeTaken());
		assertEquals(stateData, status.getStateData());
		verify(covid19DataProvider).getStateData("Maharashtra");
	}

	@Test
	@DisplayName(value = "get overall summary test")
	public void getOverallSummaryTest() {
		SummaryData summaryData = new SummaryData();
		summaryData.setConfirmedButLocationUnidentified(10);
		summaryData.setConfirmedCasesForeign(1);
		summaryData.setConfirmedCasesIndian(250000);
		summaryData.setDeaths(50000);
		summaryData.setDischarged(70000);
		summaryData.setTotal(2500011);
		summaryData.setLastUpdated(ZonedDateTime.now());

		Mockito.when(covid19DataProvider.getSummary()).thenReturn(summaryData);
		SummaryData actualSummary = alertService.getOverallSummary();
		assertEquals(summaryData, actualSummary);
		verify(covid19DataProvider).getSummary();
	}

}
