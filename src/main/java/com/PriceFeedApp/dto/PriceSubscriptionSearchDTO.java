package com.PriceFeedApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PriceSubscriptionSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer priceSubscriptionId;
	
	private Integer priceFeedId;
	
	private Integer instrumentId;
	
	private String subscriptionType;
	
	private Boolean active;
	
}
