package com.PriceFeedApp.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.PriceFeedApp.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.PriceFeedApp.domain.PriceTick;
import com.PriceFeedApp.dto.PriceTickDTO;
import com.PriceFeedApp.dto.PriceTickSearchDTO;
import com.PriceFeedApp.dto.PriceTickPageDTO;
import com.PriceFeedApp.service.PriceTickService;
import com.PriceFeedApp.dto.common.RequestDTO;
import com.PriceFeedApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/priceTick")
@RestController
public class PriceTickController {

	private final static Logger logger = LoggerFactory.getLogger(PriceTickController.class);

	@Autowired
	PriceTickService priceTickService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PriceTick> getAll() {

		List<PriceTick> priceTicks = priceTickService.findAll();
		
		return priceTicks;	
	}

	@GetMapping(value = "/{priceTickId}")
	@ResponseBody
	public PriceTickDTO getPriceTick(@PathVariable Integer priceTickId) {
		
		return (priceTickService.getPriceTickDTOById(priceTickId));
	}

 	@RequestMapping(value = "/addPriceTick", method = RequestMethod.POST)
	public ResponseEntity<?> addPriceTick(@RequestBody PriceTickDTO priceTickDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = priceTickService.addPriceTick(priceTickDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/priceTicks")
	public ResponseEntity<PriceTickPageDTO> getPriceTicks(PriceTickSearchDTO priceTickSearchDTO) {
 
		return priceTickService.getPriceTicks(priceTickSearchDTO);
	}	

	@RequestMapping(value = "/updatePriceTick", method = RequestMethod.POST)
	public ResponseEntity<?> updatePriceTick(@RequestBody PriceTickDTO priceTickDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = priceTickService.updatePriceTick(priceTickDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
