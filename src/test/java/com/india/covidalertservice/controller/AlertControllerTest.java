package com.india.covidalertservice.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.india.covidalertservice.dto.AlertStatus;
import com.india.covidalertservice.dto.SummaryData;
import com.india.covidalertservice.service.AlertService;

@SpringBootTest
@AutoConfigureMockMvc
public class AlertControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AlertService alertService;

	@Test
	@DisplayName(value = "get alert about state")
	public void getAlertAboutState() throws Exception {

		AlertStatus alertStatus = new AlertStatus();
		alertStatus.setAlertLevel("GREEN");
		Mockito.when(alertService.getAlertAboutState(ArgumentMatchers.anyString())).thenReturn(alertStatus);

		mockMvc.perform(MockMvcRequestBuilders.get("/covid19/india/Mizoram")).andExpect(status().isOk())
				.andExpect(content().json("{\"alertLevel\":\"GREEN\",\"measuresToBeTaken\":null,\"stateData\":null}"));

	}

	@Test
	@DisplayName("get summary test")
	public void getSummaryTest() throws Exception {
		SummaryData summary = new SummaryData();
		Mockito.when(alertService.getOverallSummary()).thenReturn(summary);
		mockMvc.perform(MockMvcRequestBuilders.get("/covid19/india/summary")).andExpect(status().isOk())
				.andExpect(content().json(
						"{\"total\":null,\"confirmedCasesIndian\":null,\"confirmedCasesForeign\":null,\"discharged\":null,\"deaths\":null,\"confirmedButLocationUnidentified\":null}"));
	}

	@Test
	@DisplayName("invalid endpoint test")
	public void invalidEndPoint() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/india123")).andExpect(status().isNotFound());
	}

}
