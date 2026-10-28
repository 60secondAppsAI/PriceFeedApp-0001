package com.PriceFeedApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PriceFeedDTO {

	private Integer priceFeedId;

	private String name;

	private String provider;

	private String feedType;

	private String status;






}
