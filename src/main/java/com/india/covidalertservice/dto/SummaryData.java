package com.india.covidalertservice.dto;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SummaryData {
	private Integer total;
	private Integer confirmedCasesIndian;
	private Integer confirmedCasesForeign;
	private Integer discharged;
	private Integer deaths;
	private Integer confirmedButLocationUnidentified;
	private ZonedDateTime lastUpdated;
}
