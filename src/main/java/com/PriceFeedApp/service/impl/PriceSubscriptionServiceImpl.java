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
import com.PriceFeedApp.dao.PriceSubscriptionDAO;
import com.PriceFeedApp.domain.PriceSubscription;
import com.PriceFeedApp.dto.PriceSubscriptionDTO;
import com.PriceFeedApp.dto.PriceSubscriptionSearchDTO;
import com.PriceFeedApp.dto.PriceSubscriptionPageDTO;
import com.PriceFeedApp.dto.PriceSubscriptionConvertCriteriaDTO;
import com.PriceFeedApp.dto.common.RequestDTO;
import com.PriceFeedApp.dto.common.ResultDTO;
import com.PriceFeedApp.service.PriceSubscriptionService;
import com.PriceFeedApp.util.ControllerUtils;


@Service
public class PriceSubscriptionServiceImpl extends GenericServiceImpl<PriceSubscription, Integer> implements PriceSubscriptionService {

    private final static Logger logger = LoggerFactory.getLogger(PriceSubscriptionServiceImpl.class);

	@Autowired
	PriceSubscriptionDAO priceSubscriptionDao;

	

	@Override
	public GenericDAO<PriceSubscription, Integer> getDAO() {
		return (GenericDAO<PriceSubscription, Integer>) priceSubscriptionDao;
	}
	
	public List<PriceSubscription> findAll () {
		List<PriceSubscription> priceSubscriptions = priceSubscriptionDao.findAll();
		
		return priceSubscriptions;	
		
	}

	public ResultDTO addPriceSubscription(PriceSubscriptionDTO priceSubscriptionDTO, RequestDTO requestDTO) {

		PriceSubscription priceSubscription = new PriceSubscription();

		priceSubscription.setPriceSubscriptionId(priceSubscriptionDTO.getPriceSubscriptionId());

		priceSubscription.setPriceFeedId(priceSubscriptionDTO.getPriceFeedId());

		priceSubscription.setInstrumentId(priceSubscriptionDTO.getInstrumentId());

		priceSubscription.setSubscriptionType(priceSubscriptionDTO.getSubscriptionType());

		priceSubscription.setActive(priceSubscriptionDTO.getActive());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		priceSubscription = priceSubscriptionDao.save(priceSubscription);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PriceSubscription> getAllPriceSubscriptions(Pageable pageable) {
		return priceSubscriptionDao.findAll(pageable);
	}

	public Page<PriceSubscription> getAllPriceSubscriptions(Specification<PriceSubscription> spec, Pageable pageable) {
		return priceSubscriptionDao.findAll(spec, pageable);
	}

	public ResponseEntity<PriceSubscriptionPageDTO> getPriceSubscriptions(PriceSubscriptionSearchDTO priceSubscriptionSearchDTO) {
	
			Integer priceSubscriptionId = priceSubscriptionSearchDTO.getPriceSubscriptionId(); 
 			Integer priceFeedId = priceSubscriptionSearchDTO.getPriceFeedId(); 
 			Integer instrumentId = priceSubscriptionSearchDTO.getInstrumentId(); 
 			String subscriptionType = priceSubscriptionSearchDTO.getSubscriptionType(); 
  			String sortBy = priceSubscriptionSearchDTO.getSortBy();
			String sortOrder = priceSubscriptionSearchDTO.getSortOrder();
			String searchQuery = priceSubscriptionSearchDTO.getSearchQuery();
			Integer page = priceSubscriptionSearchDTO.getPage();
			Integer size = priceSubscriptionSearchDTO.getSize();

	        Specification<PriceSubscription> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, priceSubscriptionId, "priceSubscriptionId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, priceFeedId, "priceFeedId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, instrumentId, "instrumentId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, subscriptionType, "subscriptionType"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("subscriptionType")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<PriceSubscription> priceSubscriptions = this.getAllPriceSubscriptions(spec, pageable);
		
		//System.out.println(String.valueOf(priceSubscriptions.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(priceSubscriptions.getTotalPages()));
		
		List<PriceSubscription> priceSubscriptionsList = priceSubscriptions.getContent();
		
		PriceSubscriptionConvertCriteriaDTO convertCriteria = new PriceSubscriptionConvertCriteriaDTO();
		List<PriceSubscriptionDTO> priceSubscriptionDTOs = this.convertPriceSubscriptionsToPriceSubscriptionDTOs(priceSubscriptionsList,convertCriteria);
		
		PriceSubscriptionPageDTO priceSubscriptionPageDTO = new PriceSubscriptionPageDTO();
		priceSubscriptionPageDTO.setPriceSubscriptions(priceSubscriptionDTOs);
		priceSubscriptionPageDTO.setTotalElements(priceSubscriptions.getTotalElements());
		return ResponseEntity.ok(priceSubscriptionPageDTO);
	}

	public List<PriceSubscriptionDTO> convertPriceSubscriptionsToPriceSubscriptionDTOs(List<PriceSubscription> priceSubscriptions, PriceSubscriptionConvertCriteriaDTO convertCriteria) {
		
		List<PriceSubscriptionDTO> priceSubscriptionDTOs = new ArrayList<PriceSubscriptionDTO>();
		
		for (PriceSubscription priceSubscription : priceSubscriptions) {
			priceSubscriptionDTOs.add(convertPriceSubscriptionToPriceSubscriptionDTO(priceSubscription,convertCriteria));
		}
		
		return priceSubscriptionDTOs;

	}
	
	public PriceSubscriptionDTO convertPriceSubscriptionToPriceSubscriptionDTO(PriceSubscription priceSubscription, PriceSubscriptionConvertCriteriaDTO convertCriteria) {
		
		PriceSubscriptionDTO priceSubscriptionDTO = new PriceSubscriptionDTO();

		priceSubscriptionDTO.setPriceSubscriptionId(priceSubscription.getPriceSubscriptionId());

		priceSubscriptionDTO.setPriceFeedId(priceSubscription.getPriceFeedId());

		priceSubscriptionDTO.setInstrumentId(priceSubscription.getInstrumentId());

		priceSubscriptionDTO.setSubscriptionType(priceSubscription.getSubscriptionType());

		priceSubscriptionDTO.setActive(priceSubscription.getActive());
		
		return priceSubscriptionDTO;
	}

	public ResultDTO updatePriceSubscription(PriceSubscriptionDTO priceSubscriptionDTO, RequestDTO requestDTO) {
		
		PriceSubscription priceSubscription = priceSubscriptionDao.getById(priceSubscriptionDTO.getPriceSubscriptionId());
		
		priceSubscription.setPriceSubscriptionId(ControllerUtils.setValue(priceSubscription.getPriceSubscriptionId(), priceSubscriptionDTO.getPriceSubscriptionId()));
		
		priceSubscription.setPriceFeedId(ControllerUtils.setValue(priceSubscription.getPriceFeedId(), priceSubscriptionDTO.getPriceFeedId()));
		
		priceSubscription.setInstrumentId(ControllerUtils.setValue(priceSubscription.getInstrumentId(), priceSubscriptionDTO.getInstrumentId()));
		
		priceSubscription.setSubscriptionType(ControllerUtils.setValue(priceSubscription.getSubscriptionType(), priceSubscriptionDTO.getSubscriptionType()));
		
		priceSubscription.setActive(ControllerUtils.setValue(priceSubscription.getActive(), priceSubscriptionDTO.getActive()));

        priceSubscription = priceSubscriptionDao.save(priceSubscription);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PriceSubscriptionDTO getPriceSubscriptionDTOById(Integer priceSubscriptionId) {
	
		PriceSubscription priceSubscription = priceSubscriptionDao.getById(priceSubscriptionId);
		
		PriceSubscriptionConvertCriteriaDTO convertCriteria = new PriceSubscriptionConvertCriteriaDTO();
		return(this.convertPriceSubscriptionToPriceSubscriptionDTO(priceSubscription,convertCriteria));
	}

}
