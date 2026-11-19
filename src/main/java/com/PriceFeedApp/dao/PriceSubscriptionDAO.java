package com.PriceFeedApp.dao;

import java.util.List;
import java.util.Date;

import com.PriceFeedApp.dao.GenericDAO;
import com.PriceFeedApp.domain.PriceSubscription;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface PriceSubscriptionDAO extends GenericDAO<PriceSubscription, Integer> {
  
	List<PriceSubscription> findAll();
	


}

