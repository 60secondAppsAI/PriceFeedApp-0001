package com.PriceFeedApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePriceSubscriptionDTO {

	private Integer ownerId;

	private Integer priceSubscriptionId;

	private Integer priceSubscriptionStatus;
	
	private Integer nextOrPrevious;
}

