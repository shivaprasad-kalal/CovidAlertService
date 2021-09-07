package com.india.covidalertservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.india.covidalertservice.dto.AlertStatus;
import com.india.covidalertservice.dto.SummaryData;
import com.india.covidalertservice.service.AlertService;

@RestController
@RequestMapping("/covid19/india")
public class AlertController {
	@Autowired
	private AlertService alertService;

	@GetMapping("/{state}")
	private AlertStatus getAlertAboutState(@PathVariable String state) {

		return alertService.getAlertAboutState(state);

	}

	@GetMapping("/summary")
	private SummaryData getOverallSummary() {
		return alertService.getOverallSummary();
	}

}
