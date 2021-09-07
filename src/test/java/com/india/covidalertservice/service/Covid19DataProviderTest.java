package com.india.covidalertservice.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.india.covidalertservice.dto.CountryData;
import com.india.covidalertservice.dto.CovidApiData;
import com.india.covidalertservice.dto.StateData;
import com.india.covidalertservice.dto.SummaryData;
import com.india.covidalertservice.service.Covid19DataProvider;

public class Covid19DataProviderTest {
	@Mock
	private RestTemplate restTemplate;
	@InjectMocks
	private Covid19DataProvider covid19DataProvider;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("get states data test")
	public void getStateDataTest() {
		CovidApiData covidApiData = getCovidApiData();

		when(restTemplate.getForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(covidApiData);

		StateData state = covid19DataProvider.getStateData("Mizoram");
		Assertions.assertAll(() -> Assertions.assertEquals("Mizoram", state.getLoc()),
				() -> Assertions.assertEquals(8000, state.getTotalConfirmed()),
				() -> Assertions.assertEquals(8000, state.getConfirmedCasesIndian()),
				() -> Assertions.assertEquals(0, state.getConfirmedCasesForeign()),
				() -> Assertions.assertEquals(5500, state.getDischarged()),
				() -> Assertions.assertEquals(500, state.getDeaths()));
	}

	@Test
	@DisplayName("get states data test for no data found")
	public void getStateDataTestNoDataFound() {
		CovidApiData covidApiData = getCovidApiData();

		when(restTemplate.getForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(covidApiData);

		StateData maharashtra = covid19DataProvider.getStateData("Maharashtra");
		assertAll(() -> Assertions.assertEquals(null, maharashtra.getLoc()),
				() -> Assertions.assertEquals(null, maharashtra.getTotalConfirmed()),
				() -> Assertions.assertEquals(null, maharashtra.getConfirmedCasesIndian()),
				() -> Assertions.assertEquals(null, maharashtra.getConfirmedCasesForeign()),
				() -> Assertions.assertEquals(null, maharashtra.getDischarged()),
				() -> Assertions.assertEquals(null, maharashtra.getDeaths()));
	}

	@Test
	@DisplayName("get summary data test")
	public void getSummaryDataTest() {

		when(restTemplate.getForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
				.thenReturn(getCovidApiDataForSummary());
		SummaryData summary = covid19DataProvider.getSummary();

		assertAll(

				() -> assertEquals(50, summary.getConfirmedButLocationUnidentified()),
				() -> assertEquals(10, summary.getConfirmedCasesForeign()),
				() -> assertEquals(250000, summary.getConfirmedCasesIndian()),
				() -> assertEquals(5000, summary.getDeaths()), () -> assertEquals(175000, summary.getDischarged()),
				() -> assertEquals(250060, summary.getTotal())

		);
	}

	private CovidApiData getCovidApiDataForSummary() {
		SummaryData summary = new SummaryData();
		summary.setConfirmedButLocationUnidentified(50);
		summary.setConfirmedCasesForeign(10);
		summary.setConfirmedCasesIndian(250000);
		summary.setDeaths(5000);
		summary.setDischarged(175000);
		summary.setTotal(250060);

		CountryData data = new CountryData();
		data.setSummary(summary);

		CovidApiData covidApiData = new CovidApiData();
		covidApiData.setSuccess(true);
		covidApiData.setData(data);
		covidApiData.setLastRefreshed(ZonedDateTime.now());
		covidApiData.setLastOriginUpdate(ZonedDateTime.now());

		return covidApiData;
	}

	private CovidApiData getCovidApiData() {

		StateData stateData = new StateData();
		stateData.setLoc("Mizoram");
		stateData.setConfirmedCasesForeign(0);
		stateData.setConfirmedCasesIndian(8000);
		stateData.setDeaths(500);
		stateData.setDischarged(5500);
		stateData.setTotalConfirmed(8000);

		CountryData data = new CountryData();
		data.setRegional(new StateData[] { stateData });

		CovidApiData covidApiData = new CovidApiData();
		covidApiData.setSuccess(true);
		covidApiData.setData(data);
		covidApiData.setLastRefreshed(ZonedDateTime.now());
		covidApiData.setLastOriginUpdate(ZonedDateTime.now());

		return covidApiData;
	}

}
