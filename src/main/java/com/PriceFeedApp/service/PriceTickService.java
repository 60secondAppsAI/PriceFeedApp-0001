package com.PriceFeedApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.PriceFeedApp.domain.PriceTick;
import com.PriceFeedApp.dto.PriceTickDTO;
import com.PriceFeedApp.dto.PriceTickSearchDTO;
import com.PriceFeedApp.dto.PriceTickPageDTO;
import com.PriceFeedApp.dto.PriceTickConvertCriteriaDTO;
import com.PriceFeedApp.service.GenericService;
import com.PriceFeedApp.dto.common.RequestDTO;
import com.PriceFeedApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PriceTickService extends GenericService<PriceTick, Integer> {

	List<PriceTick> findAll();

	ResultDTO addPriceTick(PriceTickDTO priceTickDTO, RequestDTO requestDTO);

	ResultDTO updatePriceTick(PriceTickDTO priceTickDTO, RequestDTO requestDTO);

    Page<PriceTick> getAllPriceTicks(Pageable pageable);

    Page<PriceTick> getAllPriceTicks(Specification<PriceTick> spec, Pageable pageable);

	ResponseEntity<PriceTickPageDTO> getPriceTicks(PriceTickSearchDTO priceTickSearchDTO);
	
	List<PriceTickDTO> convertPriceTicksToPriceTickDTOs(List<PriceTick> priceTicks, PriceTickConvertCriteriaDTO convertCriteria);

	PriceTickDTO getPriceTickDTOById(Integer priceTickId);



	
}
