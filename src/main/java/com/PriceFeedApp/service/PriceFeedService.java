package com.PriceFeedApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.PriceFeedApp.domain.PriceFeed;
import com.PriceFeedApp.dto.PriceFeedDTO;
import com.PriceFeedApp.dto.PriceFeedSearchDTO;
import com.PriceFeedApp.dto.PriceFeedPageDTO;
import com.PriceFeedApp.dto.PriceFeedConvertCriteriaDTO;
import com.PriceFeedApp.service.GenericService;
import com.PriceFeedApp.dto.common.RequestDTO;
import com.PriceFeedApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PriceFeedService extends GenericService<PriceFeed, Integer> {

	List<PriceFeed> findAll();

	ResultDTO addPriceFeed(PriceFeedDTO priceFeedDTO, RequestDTO requestDTO);

	ResultDTO updatePriceFeed(PriceFeedDTO priceFeedDTO, RequestDTO requestDTO);

    Page<PriceFeed> getAllPriceFeeds(Pageable pageable);

    Page<PriceFeed> getAllPriceFeeds(Specification<PriceFeed> spec, Pageable pageable);

	ResponseEntity<PriceFeedPageDTO> getPriceFeeds(PriceFeedSearchDTO priceFeedSearchDTO);
	
	List<PriceFeedDTO> convertPriceFeedsToPriceFeedDTOs(List<PriceFeed> priceFeeds, PriceFeedConvertCriteriaDTO convertCriteria);

	PriceFeedDTO getPriceFeedDTOById(Integer priceFeedId);



	
}
