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

import com.PriceFeedApp.domain.PriceSubscription;
import com.PriceFeedApp.dto.PriceSubscriptionDTO;
import com.PriceFeedApp.dto.PriceSubscriptionSearchDTO;
import com.PriceFeedApp.dto.PriceSubscriptionPageDTO;
import com.PriceFeedApp.service.PriceSubscriptionService;
import com.PriceFeedApp.dto.common.RequestDTO;
import com.PriceFeedApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/priceSubscription")
@RestController
public class PriceSubscriptionController {

	private final static Logger logger = LoggerFactory.getLogger(PriceSubscriptionController.class);

	@Autowired
	PriceSubscriptionService priceSubscriptionService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PriceSubscription> getAll() {

		List<PriceSubscription> priceSubscriptions = priceSubscriptionService.findAll();
		
		return priceSubscriptions;	
	}

	@GetMapping(value = "/{priceSubscriptionId}")
	@ResponseBody
	public PriceSubscriptionDTO getPriceSubscription(@PathVariable Integer priceSubscriptionId) {
		
		return (priceSubscriptionService.getPriceSubscriptionDTOById(priceSubscriptionId));
	}

 	@RequestMapping(value = "/addPriceSubscription", method = RequestMethod.POST)
	public ResponseEntity<?> addPriceSubscription(@RequestBody PriceSubscriptionDTO priceSubscriptionDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = priceSubscriptionService.addPriceSubscription(priceSubscriptionDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/priceSubscriptions")
	public ResponseEntity<PriceSubscriptionPageDTO> getPriceSubscriptions(PriceSubscriptionSearchDTO priceSubscriptionSearchDTO) {
 
		return priceSubscriptionService.getPriceSubscriptions(priceSubscriptionSearchDTO);
	}	

	@RequestMapping(value = "/updatePriceSubscription", method = RequestMethod.POST)
	public ResponseEntity<?> updatePriceSubscription(@RequestBody PriceSubscriptionDTO priceSubscriptionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = priceSubscriptionService.updatePriceSubscription(priceSubscriptionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
