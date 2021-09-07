package com.india.covidalertservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.india.covidalertservice.dto.CovidApiData;
import com.india.covidalertservice.dto.StateData;
import com.india.covidalertservice.dto.SummaryData;

@Service
public class Covid19DataProvider {

	private static final String resourceURL = "https://api.rootnet.in/covid19-in/stats/latest";
	@Autowired
	private RestTemplate restTemplate;

	public StateData getStateData(String state) {

		CovidApiData covidApiData = restTemplate.getForObject(resourceURL, CovidApiData.class);
		StateData[] regional = covidApiData.getData().getRegional();

		return Arrays.stream(regional).filter(s -> s.getLoc().equalsIgnoreCase(state)).findAny()
				.orElse(new StateData());

	}

	public SummaryData getSummary() {
		CovidApiData covidApiData = restTemplate.getForObject(resourceURL, CovidApiData.class);
		SummaryData summaryData = covidApiData.getData().getSummary();
		summaryData.setLastUpdated(covidApiData.getLastRefreshed());
		return summaryData;

	}

}
