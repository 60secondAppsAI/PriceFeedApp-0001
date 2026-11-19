package com.PriceFeedApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PriceTickSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer priceTickId;
	
	private Integer priceFeedId;
	
	private Integer instrumentId;
	
	private Double price;
	
	private Double size;
	
	private Date receivedAt;
	
}
