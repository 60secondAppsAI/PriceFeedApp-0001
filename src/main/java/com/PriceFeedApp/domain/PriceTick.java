package com.PriceFeedApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="price_ticks")
@Getter @Setter @NoArgsConstructor
public class PriceTick {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="price_tick_id")
	private Integer priceTickId;
    
  	@Column(name="price_feed_id")
	private Integer priceFeedId;
    
  	@Column(name="instrument_id")
	private Integer instrumentId;
    
  	@Column(name="price")
	private Double price;
    
  	@Column(name="size")
	private Double size;
    
  	@Column(name="received_at")
	private Date receivedAt;
    

}
