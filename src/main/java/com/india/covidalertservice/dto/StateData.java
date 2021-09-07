package com.india.covidalertservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateData {
	private String loc;
	private Integer confirmedCasesIndian;
	private Integer confirmedCasesForeign;
	private Integer discharged;
	private Integer deaths;
	private Integer totalConfirmed;
	
}
