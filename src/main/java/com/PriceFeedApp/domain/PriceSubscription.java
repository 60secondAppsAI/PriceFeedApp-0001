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
@Table(name="price_subscriptions")
@Getter @Setter @NoArgsConstructor
public class PriceSubscription {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="price_subscription_id")
	private Integer priceSubscriptionId;
    
  	@Column(name="price_feed_id")
	private Integer priceFeedId;
    
  	@Column(name="instrument_id")
	private Integer instrumentId;
    
  	@Column(name="subscription_type")
	private String subscriptionType;
    
  	@Column(name="active")
	private Boolean active;
    

}
