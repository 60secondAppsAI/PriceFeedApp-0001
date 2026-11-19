package com.PriceFeedApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseFeedHealthDTO {

	private Integer ownerId;

	private Integer feedHealthId;

	private Integer feedHealthStatus;
	
	private Integer nextOrPrevious;
}

