package com.PriceFeedApp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.PriceFeedApp.dao.GenericDAO;
import com.PriceFeedApp.service.GenericService;
import com.PriceFeedApp.service.impl.GenericServiceImpl;
import com.PriceFeedApp.dao.FeedHealthDAO;
import com.PriceFeedApp.domain.FeedHealth;
import com.PriceFeedApp.dto.FeedHealthDTO;
import com.PriceFeedApp.dto.FeedHealthSearchDTO;
import com.PriceFeedApp.dto.FeedHealthPageDTO;
import com.PriceFeedApp.dto.FeedHealthConvertCriteriaDTO;
import com.PriceFeedApp.dto.common.RequestDTO;
import com.PriceFeedApp.dto.common.ResultDTO;
import com.PriceFeedApp.service.FeedHealthService;
import com.PriceFeedApp.util.ControllerUtils;


@Service
public class FeedHealthServiceImpl extends GenericServiceImpl<FeedHealth, Integer> implements FeedHealthService {

    private final static Logger logger = LoggerFactory.getLogger(FeedHealthServiceImpl.class);

	@Autowired
	FeedHealthDAO feedHealthDao;

	

	@Override
	public GenericDAO<FeedHealth, Integer> getDAO() {
		return (GenericDAO<FeedHealth, Integer>) feedHealthDao;
	}
	
	public List<FeedHealth> findAll () {
		List<FeedHealth> feedHealths = feedHealthDao.findAll();
		
		return feedHealths;	
		
	}

	public ResultDTO addFeedHealth(FeedHealthDTO feedHealthDTO, RequestDTO requestDTO) {

		FeedHealth feedHealth = new FeedHealth();

		feedHealth.setFeedHealthId(feedHealthDTO.getFeedHealthId());

		feedHealth.setPriceFeedId(feedHealthDTO.getPriceFeedId());

		feedHealth.setStatus(feedHealthDTO.getStatus());

		feedHealth.setLatencyMs(feedHealthDTO.getLatencyMs());

		feedHealth.setMeasuredAt(feedHealthDTO.getMeasuredAt());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		feedHealth = feedHealthDao.save(feedHealth);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<FeedHealth> getAllFeedHealths(Pageable pageable) {
		return feedHealthDao.findAll(pageable);
	}

	public Page<FeedHealth> getAllFeedHealths(Specification<FeedHealth> spec, Pageable pageable) {
		return feedHealthDao.findAll(spec, pageable);
	}

	public ResponseEntity<FeedHealthPageDTO> getFeedHealths(FeedHealthSearchDTO feedHealthSearchDTO) {
	
			Integer feedHealthId = feedHealthSearchDTO.getFeedHealthId(); 
 			Integer priceFeedId = feedHealthSearchDTO.getPriceFeedId(); 
 			String status = feedHealthSearchDTO.getStatus(); 
 			Integer latencyMs = feedHealthSearchDTO.getLatencyMs(); 
 			Date measuredAt = feedHealthSearchDTO.getMeasuredAt();
 			String sortBy = feedHealthSearchDTO.getSortBy();
			String sortOrder = feedHealthSearchDTO.getSortOrder();
			String searchQuery = feedHealthSearchDTO.getSearchQuery();
			Integer page = feedHealthSearchDTO.getPage();
			Integer size = feedHealthSearchDTO.getSize();

	        Specification<FeedHealth> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, feedHealthId, "feedHealthId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, priceFeedId, "priceFeedId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			
			spec = ControllerUtils.andIfNecessary(spec, latencyMs, "latencyMs"); 
			
			spec = ControllerUtils.andIfNecessary(spec, measuredAt, "measuredAt");
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("status")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<FeedHealth> feedHealths = this.getAllFeedHealths(spec, pageable);
		
		//System.out.println(String.valueOf(feedHealths.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(feedHealths.getTotalPages()));
		
		List<FeedHealth> feedHealthsList = feedHealths.getContent();
		
		FeedHealthConvertCriteriaDTO convertCriteria = new FeedHealthConvertCriteriaDTO();
		List<FeedHealthDTO> feedHealthDTOs = this.convertFeedHealthsToFeedHealthDTOs(feedHealthsList,convertCriteria);
		
		FeedHealthPageDTO feedHealthPageDTO = new FeedHealthPageDTO();
		feedHealthPageDTO.setFeedHealths(feedHealthDTOs);
		feedHealthPageDTO.setTotalElements(feedHealths.getTotalElements());
		return ResponseEntity.ok(feedHealthPageDTO);
	}

	public List<FeedHealthDTO> convertFeedHealthsToFeedHealthDTOs(List<FeedHealth> feedHealths, FeedHealthConvertCriteriaDTO convertCriteria) {
		
		List<FeedHealthDTO> feedHealthDTOs = new ArrayList<FeedHealthDTO>();
		
		for (FeedHealth feedHealth : feedHealths) {
			feedHealthDTOs.add(convertFeedHealthToFeedHealthDTO(feedHealth,convertCriteria));
		}
		
		return feedHealthDTOs;

	}
	
	public FeedHealthDTO convertFeedHealthToFeedHealthDTO(FeedHealth feedHealth, FeedHealthConvertCriteriaDTO convertCriteria) {
		
		FeedHealthDTO feedHealthDTO = new FeedHealthDTO();

		feedHealthDTO.setFeedHealthId(feedHealth.getFeedHealthId());

		feedHealthDTO.setPriceFeedId(feedHealth.getPriceFeedId());

		feedHealthDTO.setStatus(feedHealth.getStatus());

		feedHealthDTO.setLatencyMs(feedHealth.getLatencyMs());

		feedHealthDTO.setMeasuredAt(feedHealth.getMeasuredAt());
		
		return feedHealthDTO;
	}

	public ResultDTO updateFeedHealth(FeedHealthDTO feedHealthDTO, RequestDTO requestDTO) {
		
		FeedHealth feedHealth = feedHealthDao.getById(feedHealthDTO.getFeedHealthId());
		
		feedHealth.setFeedHealthId(ControllerUtils.setValue(feedHealth.getFeedHealthId(), feedHealthDTO.getFeedHealthId()));
		
		feedHealth.setPriceFeedId(ControllerUtils.setValue(feedHealth.getPriceFeedId(), feedHealthDTO.getPriceFeedId()));
		
		feedHealth.setStatus(ControllerUtils.setValue(feedHealth.getStatus(), feedHealthDTO.getStatus()));
		
		feedHealth.setLatencyMs(ControllerUtils.setValue(feedHealth.getLatencyMs(), feedHealthDTO.getLatencyMs()));
		
		feedHealth.setMeasuredAt(ControllerUtils.setValue(feedHealth.getMeasuredAt(), feedHealthDTO.getMeasuredAt()));

        feedHealth = feedHealthDao.save(feedHealth);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public FeedHealthDTO getFeedHealthDTOById(Integer feedHealthId) {
	
		FeedHealth feedHealth = feedHealthDao.getById(feedHealthId);
		
		FeedHealthConvertCriteriaDTO convertCriteria = new FeedHealthConvertCriteriaDTO();
		return(this.convertFeedHealthToFeedHealthDTO(feedHealth,convertCriteria));
	}

}
