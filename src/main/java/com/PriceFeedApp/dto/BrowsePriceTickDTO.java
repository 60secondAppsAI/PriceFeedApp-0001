package com.PriceFeedApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePriceTickDTO {

	private Integer ownerId;

	private Integer priceTickId;

	private Integer priceTickStatus;
	
	private Integer nextOrPrevious;
}

