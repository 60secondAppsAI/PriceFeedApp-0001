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

import com.PriceFeedApp.domain.FeedHealth;
import com.PriceFeedApp.dto.FeedHealthDTO;
import com.PriceFeedApp.dto.FeedHealthSearchDTO;
import com.PriceFeedApp.dto.FeedHealthPageDTO;
import com.PriceFeedApp.service.FeedHealthService;
import com.PriceFeedApp.dto.common.RequestDTO;
import com.PriceFeedApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/feedHealth")
@RestController
public class FeedHealthController {

	private final static Logger logger = LoggerFactory.getLogger(FeedHealthController.class);

	@Autowired
	FeedHealthService feedHealthService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<FeedHealth> getAll() {

		List<FeedHealth> feedHealths = feedHealthService.findAll();
		
		return feedHealths;	
	}

	@GetMapping(value = "/{feedHealthId}")
	@ResponseBody
	public FeedHealthDTO getFeedHealth(@PathVariable Integer feedHealthId) {
		
		return (feedHealthService.getFeedHealthDTOById(feedHealthId));
	}

 	@RequestMapping(value = "/addFeedHealth", method = RequestMethod.POST)
	public ResponseEntity<?> addFeedHealth(@RequestBody FeedHealthDTO feedHealthDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = feedHealthService.addFeedHealth(feedHealthDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/feedHealths")
	public ResponseEntity<FeedHealthPageDTO> getFeedHealths(FeedHealthSearchDTO feedHealthSearchDTO) {
 
		return feedHealthService.getFeedHealths(feedHealthSearchDTO);
	}	

	@RequestMapping(value = "/updateFeedHealth", method = RequestMethod.POST)
	public ResponseEntity<?> updateFeedHealth(@RequestBody FeedHealthDTO feedHealthDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = feedHealthService.updateFeedHealth(feedHealthDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
