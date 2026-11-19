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
import com.PriceFeedApp.dao.PriceFeedDAO;
import com.PriceFeedApp.domain.PriceFeed;
import com.PriceFeedApp.dto.PriceFeedDTO;
import com.PriceFeedApp.dto.PriceFeedSearchDTO;
import com.PriceFeedApp.dto.PriceFeedPageDTO;
import com.PriceFeedApp.dto.PriceFeedConvertCriteriaDTO;
import com.PriceFeedApp.dto.common.RequestDTO;
import com.PriceFeedApp.dto.common.ResultDTO;
import com.PriceFeedApp.service.PriceFeedService;
import com.PriceFeedApp.util.ControllerUtils;


@Service
public class PriceFeedServiceImpl extends GenericServiceImpl<PriceFeed, Integer> implements PriceFeedService {

    private final static Logger logger = LoggerFactory.getLogger(PriceFeedServiceImpl.class);

	@Autowired
	PriceFeedDAO priceFeedDao;

	

	@Override
	public GenericDAO<PriceFeed, Integer> getDAO() {
		return (GenericDAO<PriceFeed, Integer>) priceFeedDao;
	}
	
	public List<PriceFeed> findAll () {
		List<PriceFeed> priceFeeds = priceFeedDao.findAll();
		
		return priceFeeds;	
		
	}

	public ResultDTO addPriceFeed(PriceFeedDTO priceFeedDTO, RequestDTO requestDTO) {

		PriceFeed priceFeed = new PriceFeed();

		priceFeed.setPriceFeedId(priceFeedDTO.getPriceFeedId());

		priceFeed.setName(priceFeedDTO.getName());

		priceFeed.setProvider(priceFeedDTO.getProvider());

		priceFeed.setFeedType(priceFeedDTO.getFeedType());

		priceFeed.setStatus(priceFeedDTO.getStatus());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		priceFeed = priceFeedDao.save(priceFeed);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PriceFeed> getAllPriceFeeds(Pageable pageable) {
		return priceFeedDao.findAll(pageable);
	}

	public Page<PriceFeed> getAllPriceFeeds(Specification<PriceFeed> spec, Pageable pageable) {
		return priceFeedDao.findAll(spec, pageable);
	}

	public ResponseEntity<PriceFeedPageDTO> getPriceFeeds(PriceFeedSearchDTO priceFeedSearchDTO) {
	
			Integer priceFeedId = priceFeedSearchDTO.getPriceFeedId(); 
 			String name = priceFeedSearchDTO.getName(); 
 			String provider = priceFeedSearchDTO.getProvider(); 
 			String feedType = priceFeedSearchDTO.getFeedType(); 
 			String status = priceFeedSearchDTO.getStatus(); 
 			String sortBy = priceFeedSearchDTO.getSortBy();
			String sortOrder = priceFeedSearchDTO.getSortOrder();
			String searchQuery = priceFeedSearchDTO.getSearchQuery();
			Integer page = priceFeedSearchDTO.getPage();
			Integer size = priceFeedSearchDTO.getSize();

	        Specification<PriceFeed> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, priceFeedId, "priceFeedId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, provider, "provider"); 
			
			spec = ControllerUtils.andIfNecessary(spec, feedType, "feedType"); 
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("provider")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("feedType")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("status")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<PriceFeed> priceFeeds = this.getAllPriceFeeds(spec, pageable);
		
		//System.out.println(String.valueOf(priceFeeds.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(priceFeeds.getTotalPages()));
		
		List<PriceFeed> priceFeedsList = priceFeeds.getContent();
		
		PriceFeedConvertCriteriaDTO convertCriteria = new PriceFeedConvertCriteriaDTO();
		List<PriceFeedDTO> priceFeedDTOs = this.convertPriceFeedsToPriceFeedDTOs(priceFeedsList,convertCriteria);
		
		PriceFeedPageDTO priceFeedPageDTO = new PriceFeedPageDTO();
		priceFeedPageDTO.setPriceFeeds(priceFeedDTOs);
		priceFeedPageDTO.setTotalElements(priceFeeds.getTotalElements());
		return ResponseEntity.ok(priceFeedPageDTO);
	}

	public List<PriceFeedDTO> convertPriceFeedsToPriceFeedDTOs(List<PriceFeed> priceFeeds, PriceFeedConvertCriteriaDTO convertCriteria) {
		
		List<PriceFeedDTO> priceFeedDTOs = new ArrayList<PriceFeedDTO>();
		
		for (PriceFeed priceFeed : priceFeeds) {
			priceFeedDTOs.add(convertPriceFeedToPriceFeedDTO(priceFeed,convertCriteria));
		}
		
		return priceFeedDTOs;

	}
	
	public PriceFeedDTO convertPriceFeedToPriceFeedDTO(PriceFeed priceFeed, PriceFeedConvertCriteriaDTO convertCriteria) {
		
		PriceFeedDTO priceFeedDTO = new PriceFeedDTO();

		priceFeedDTO.setPriceFeedId(priceFeed.getPriceFeedId());

		priceFeedDTO.setName(priceFeed.getName());

		priceFeedDTO.setProvider(priceFeed.getProvider());

		priceFeedDTO.setFeedType(priceFeed.getFeedType());

		priceFeedDTO.setStatus(priceFeed.getStatus());
		
		return priceFeedDTO;
	}

	public ResultDTO updatePriceFeed(PriceFeedDTO priceFeedDTO, RequestDTO requestDTO) {
		
		PriceFeed priceFeed = priceFeedDao.getById(priceFeedDTO.getPriceFeedId());
		
		priceFeed.setPriceFeedId(ControllerUtils.setValue(priceFeed.getPriceFeedId(), priceFeedDTO.getPriceFeedId()));
		
		priceFeed.setName(ControllerUtils.setValue(priceFeed.getName(), priceFeedDTO.getName()));
		
		priceFeed.setProvider(ControllerUtils.setValue(priceFeed.getProvider(), priceFeedDTO.getProvider()));
		
		priceFeed.setFeedType(ControllerUtils.setValue(priceFeed.getFeedType(), priceFeedDTO.getFeedType()));
		
		priceFeed.setStatus(ControllerUtils.setValue(priceFeed.getStatus(), priceFeedDTO.getStatus()));

        priceFeed = priceFeedDao.save(priceFeed);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PriceFeedDTO getPriceFeedDTOById(Integer priceFeedId) {
	
		PriceFeed priceFeed = priceFeedDao.getById(priceFeedId);
		
		PriceFeedConvertCriteriaDTO convertCriteria = new PriceFeedConvertCriteriaDTO();
		return(this.convertPriceFeedToPriceFeedDTO(priceFeed,convertCriteria));
	}

}
