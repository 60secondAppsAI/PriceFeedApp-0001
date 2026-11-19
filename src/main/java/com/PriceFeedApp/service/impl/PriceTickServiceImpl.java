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
import com.PriceFeedApp.dao.PriceTickDAO;
import com.PriceFeedApp.domain.PriceTick;
import com.PriceFeedApp.dto.PriceTickDTO;
import com.PriceFeedApp.dto.PriceTickSearchDTO;
import com.PriceFeedApp.dto.PriceTickPageDTO;
import com.PriceFeedApp.dto.PriceTickConvertCriteriaDTO;
import com.PriceFeedApp.dto.common.RequestDTO;
import com.PriceFeedApp.dto.common.ResultDTO;
import com.PriceFeedApp.service.PriceTickService;
import com.PriceFeedApp.util.ControllerUtils;


@Service
public class PriceTickServiceImpl extends GenericServiceImpl<PriceTick, Integer> implements PriceTickService {

    private final static Logger logger = LoggerFactory.getLogger(PriceTickServiceImpl.class);

	@Autowired
	PriceTickDAO priceTickDao;

	

	@Override
	public GenericDAO<PriceTick, Integer> getDAO() {
		return (GenericDAO<PriceTick, Integer>) priceTickDao;
	}
	
	public List<PriceTick> findAll () {
		List<PriceTick> priceTicks = priceTickDao.findAll();
		
		return priceTicks;	
		
	}

	public ResultDTO addPriceTick(PriceTickDTO priceTickDTO, RequestDTO requestDTO) {

		PriceTick priceTick = new PriceTick();

		priceTick.setPriceTickId(priceTickDTO.getPriceTickId());

		priceTick.setPriceFeedId(priceTickDTO.getPriceFeedId());

		priceTick.setInstrumentId(priceTickDTO.getInstrumentId());

		priceTick.setPrice(priceTickDTO.getPrice());

		priceTick.setSize(priceTickDTO.getSize());

		priceTick.setReceivedAt(priceTickDTO.getReceivedAt());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		priceTick = priceTickDao.save(priceTick);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PriceTick> getAllPriceTicks(Pageable pageable) {
		return priceTickDao.findAll(pageable);
	}

	public Page<PriceTick> getAllPriceTicks(Specification<PriceTick> spec, Pageable pageable) {
		return priceTickDao.findAll(spec, pageable);
	}

	public ResponseEntity<PriceTickPageDTO> getPriceTicks(PriceTickSearchDTO priceTickSearchDTO) {
	
			Integer priceTickId = priceTickSearchDTO.getPriceTickId(); 
 			Integer priceFeedId = priceTickSearchDTO.getPriceFeedId(); 
 			Integer instrumentId = priceTickSearchDTO.getInstrumentId(); 
   			Date receivedAt = priceTickSearchDTO.getReceivedAt();
 			String sortBy = priceTickSearchDTO.getSortBy();
			String sortOrder = priceTickSearchDTO.getSortOrder();
			String searchQuery = priceTickSearchDTO.getSearchQuery();
			Integer page = priceTickSearchDTO.getPage();
			Integer size = priceTickSearchDTO.getSize();

	        Specification<PriceTick> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, priceTickId, "priceTickId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, priceFeedId, "priceFeedId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, instrumentId, "instrumentId"); 
			
			
			
			spec = ControllerUtils.andIfNecessary(spec, receivedAt, "receivedAt");
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

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

		Page<PriceTick> priceTicks = this.getAllPriceTicks(spec, pageable);
		
		//System.out.println(String.valueOf(priceTicks.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(priceTicks.getTotalPages()));
		
		List<PriceTick> priceTicksList = priceTicks.getContent();
		
		PriceTickConvertCriteriaDTO convertCriteria = new PriceTickConvertCriteriaDTO();
		List<PriceTickDTO> priceTickDTOs = this.convertPriceTicksToPriceTickDTOs(priceTicksList,convertCriteria);
		
		PriceTickPageDTO priceTickPageDTO = new PriceTickPageDTO();
		priceTickPageDTO.setPriceTicks(priceTickDTOs);
		priceTickPageDTO.setTotalElements(priceTicks.getTotalElements());
		return ResponseEntity.ok(priceTickPageDTO);
	}

	public List<PriceTickDTO> convertPriceTicksToPriceTickDTOs(List<PriceTick> priceTicks, PriceTickConvertCriteriaDTO convertCriteria) {
		
		List<PriceTickDTO> priceTickDTOs = new ArrayList<PriceTickDTO>();
		
		for (PriceTick priceTick : priceTicks) {
			priceTickDTOs.add(convertPriceTickToPriceTickDTO(priceTick,convertCriteria));
		}
		
		return priceTickDTOs;

	}
	
	public PriceTickDTO convertPriceTickToPriceTickDTO(PriceTick priceTick, PriceTickConvertCriteriaDTO convertCriteria) {
		
		PriceTickDTO priceTickDTO = new PriceTickDTO();

		priceTickDTO.setPriceTickId(priceTick.getPriceTickId());

		priceTickDTO.setPriceFeedId(priceTick.getPriceFeedId());

		priceTickDTO.setInstrumentId(priceTick.getInstrumentId());

		priceTickDTO.setPrice(priceTick.getPrice());

		priceTickDTO.setSize(priceTick.getSize());

		priceTickDTO.setReceivedAt(priceTick.getReceivedAt());
		
		return priceTickDTO;
	}

	public ResultDTO updatePriceTick(PriceTickDTO priceTickDTO, RequestDTO requestDTO) {
		
		PriceTick priceTick = priceTickDao.getById(priceTickDTO.getPriceTickId());
		
		priceTick.setPriceTickId(ControllerUtils.setValue(priceTick.getPriceTickId(), priceTickDTO.getPriceTickId()));
		
		priceTick.setPriceFeedId(ControllerUtils.setValue(priceTick.getPriceFeedId(), priceTickDTO.getPriceFeedId()));
		
		priceTick.setInstrumentId(ControllerUtils.setValue(priceTick.getInstrumentId(), priceTickDTO.getInstrumentId()));
		
		priceTick.setPrice(ControllerUtils.setValue(priceTick.getPrice(), priceTickDTO.getPrice()));
		
		priceTick.setSize(ControllerUtils.setValue(priceTick.getSize(), priceTickDTO.getSize()));
		
		priceTick.setReceivedAt(ControllerUtils.setValue(priceTick.getReceivedAt(), priceTickDTO.getReceivedAt()));

        priceTick = priceTickDao.save(priceTick);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PriceTickDTO getPriceTickDTOById(Integer priceTickId) {
	
		PriceTick priceTick = priceTickDao.getById(priceTickId);
		
		PriceTickConvertCriteriaDTO convertCriteria = new PriceTickConvertCriteriaDTO();
		return(this.convertPriceTickToPriceTickDTO(priceTick,convertCriteria));
	}

}
