package com.PriceFeedApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FeedHealthSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer feedHealthId;
	
	private Integer priceFeedId;
	
	private String status;
	
	private Integer latencyMs;
	
	private Date measuredAt;
	
}
