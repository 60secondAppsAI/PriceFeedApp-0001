package com.PriceFeedApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.PriceFeedApp.domain.FeedHealth;
import com.PriceFeedApp.dto.FeedHealthDTO;
import com.PriceFeedApp.dto.FeedHealthSearchDTO;
import com.PriceFeedApp.dto.FeedHealthPageDTO;
import com.PriceFeedApp.dto.FeedHealthConvertCriteriaDTO;
import com.PriceFeedApp.service.GenericService;
import com.PriceFeedApp.dto.common.RequestDTO;
import com.PriceFeedApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface FeedHealthService extends GenericService<FeedHealth, Integer> {

	List<FeedHealth> findAll();

	ResultDTO addFeedHealth(FeedHealthDTO feedHealthDTO, RequestDTO requestDTO);

	ResultDTO updateFeedHealth(FeedHealthDTO feedHealthDTO, RequestDTO requestDTO);

    Page<FeedHealth> getAllFeedHealths(Pageable pageable);

    Page<FeedHealth> getAllFeedHealths(Specification<FeedHealth> spec, Pageable pageable);

	ResponseEntity<FeedHealthPageDTO> getFeedHealths(FeedHealthSearchDTO feedHealthSearchDTO);
	
	List<FeedHealthDTO> convertFeedHealthsToFeedHealthDTOs(List<FeedHealth> feedHealths, FeedHealthConvertCriteriaDTO convertCriteria);

	FeedHealthDTO getFeedHealthDTOById(Integer feedHealthId);



	
}
