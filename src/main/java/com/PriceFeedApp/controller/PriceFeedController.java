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

import com.PriceFeedApp.domain.PriceFeed;
import com.PriceFeedApp.dto.PriceFeedDTO;
import com.PriceFeedApp.dto.PriceFeedSearchDTO;
import com.PriceFeedApp.dto.PriceFeedPageDTO;
import com.PriceFeedApp.service.PriceFeedService;
import com.PriceFeedApp.dto.common.RequestDTO;
import com.PriceFeedApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/priceFeed")
@RestController
public class PriceFeedController {

	private final static Logger logger = LoggerFactory.getLogger(PriceFeedController.class);

	@Autowired
	PriceFeedService priceFeedService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PriceFeed> getAll() {

		List<PriceFeed> priceFeeds = priceFeedService.findAll();
		
		return priceFeeds;	
	}

	@GetMapping(value = "/{priceFeedId}")
	@ResponseBody
	public PriceFeedDTO getPriceFeed(@PathVariable Integer priceFeedId) {
		
		return (priceFeedService.getPriceFeedDTOById(priceFeedId));
	}

 	@RequestMapping(value = "/addPriceFeed", method = RequestMethod.POST)
	public ResponseEntity<?> addPriceFeed(@RequestBody PriceFeedDTO priceFeedDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = priceFeedService.addPriceFeed(priceFeedDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/priceFeeds")
	public ResponseEntity<PriceFeedPageDTO> getPriceFeeds(PriceFeedSearchDTO priceFeedSearchDTO) {
 
		return priceFeedService.getPriceFeeds(priceFeedSearchDTO);
	}	

	@RequestMapping(value = "/updatePriceFeed", method = RequestMethod.POST)
	public ResponseEntity<?> updatePriceFeed(@RequestBody PriceFeedDTO priceFeedDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = priceFeedService.updatePriceFeed(priceFeedDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
