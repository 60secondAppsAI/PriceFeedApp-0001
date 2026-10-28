package com.PriceFeedApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PriceTickDTO {

	private Integer priceTickId;

	private Integer priceFeedId;

	private Integer instrumentId;

	private Double price;

	private Double size;

	private Date receivedAt;






}
