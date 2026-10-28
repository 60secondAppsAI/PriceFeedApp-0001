package com.PriceFeedApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePriceFeedDTO {

	private Integer ownerId;

	private Integer priceFeedId;

	private Integer priceFeedStatus;
	
	private Integer nextOrPrevious;
}

