package com.india.covidalertservice.dto;

import java.util.List;


public class AlertStatus {
	private String alertLevel; 
	private List<String> measuresToBeTaken;
	private StateData stateData;
	
	public String getAlertLevel() {
		return alertLevel;
	}
	public void setAlertLevel(String alertLevel) {
		this.alertLevel = alertLevel;
	}
	public List<String> getMeasuresToBeTaken() {
		return measuresToBeTaken;
	}
	public void setMeasuresToBeTaken(List<String> measuresToBeTaken) {
		this.measuresToBeTaken = measuresToBeTaken;
	}
	public StateData getStateData() {
		return stateData;
	}
	public void setStateData(StateData stateData) {
		this.stateData = stateData;
	}
	
}
