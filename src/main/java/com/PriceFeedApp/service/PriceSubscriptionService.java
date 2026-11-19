package com.PriceFeedApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.PriceFeedApp.domain.PriceSubscription;
import com.PriceFeedApp.dto.PriceSubscriptionDTO;
import com.PriceFeedApp.dto.PriceSubscriptionSearchDTO;
import com.PriceFeedApp.dto.PriceSubscriptionPageDTO;
import com.PriceFeedApp.dto.PriceSubscriptionConvertCriteriaDTO;
import com.PriceFeedApp.service.GenericService;
import com.PriceFeedApp.dto.common.RequestDTO;
import com.PriceFeedApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PriceSubscriptionService extends GenericService<PriceSubscription, Integer> {

	List<PriceSubscription> findAll();

	ResultDTO addPriceSubscription(PriceSubscriptionDTO priceSubscriptionDTO, RequestDTO requestDTO);

	ResultDTO updatePriceSubscription(PriceSubscriptionDTO priceSubscriptionDTO, RequestDTO requestDTO);

    Page<PriceSubscription> getAllPriceSubscriptions(Pageable pageable);

    Page<PriceSubscription> getAllPriceSubscriptions(Specification<PriceSubscription> spec, Pageable pageable);

	ResponseEntity<PriceSubscriptionPageDTO> getPriceSubscriptions(PriceSubscriptionSearchDTO priceSubscriptionSearchDTO);
	
	List<PriceSubscriptionDTO> convertPriceSubscriptionsToPriceSubscriptionDTOs(List<PriceSubscription> priceSubscriptions, PriceSubscriptionConvertCriteriaDTO convertCriteria);

	PriceSubscriptionDTO getPriceSubscriptionDTOById(Integer priceSubscriptionId);



	
}
