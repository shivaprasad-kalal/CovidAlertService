package com.india.covidalertservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.india.covidalertservice.dto.AlertStatus;
import com.india.covidalertservice.dto.StateData;
import com.india.covidalertservice.dto.SummaryData;

@Service
public class AlertService {
	@Autowired
	private Covid19DataProvider covid19DataProvider;

	public AlertStatus getAlertAboutState(String state) {
		AlertStatus alertStatus = new AlertStatus();
		StateData stateData = covid19DataProvider.getStateData(state);
		alertStatus.setStateData(stateData);
		if (stateData.getTotalConfirmed() < 20000) {
			alertStatus.setAlertLevel("GREEN");
			alertStatus.setMeasuresToBeTaken(Arrays.asList("Everything is normal !"));
		} else if (stateData.getTotalConfirmed() > 20000 && stateData.getTotalConfirmed() < 50000) {
			alertStatus.setAlertLevel("ORANGE");
			alertStatus.setMeasuresToBeTaken(
					Arrays.asList("Only Essential Services Are Allowed", "No one should come out unnecessarly"));
		} else {
			alertStatus.setAlertLevel("RED");
			alertStatus.setMeasuresToBeTaken(
					Arrays.asList("Complete LockDown", "Only Medical & Food Services are allowed"));
		}
		return alertStatus;
	}

	public SummaryData getOverallSummary() {
		return covid19DataProvider.getSummary();
		
	}

}
