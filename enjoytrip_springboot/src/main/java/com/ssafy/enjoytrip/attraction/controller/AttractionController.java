package com.ssafy.enjoytrip.attraction.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.attraction.model.AttractionDto;
import com.ssafy.enjoytrip.attraction.model.DoDto;
import com.ssafy.enjoytrip.attraction.model.SigunguDto;
import com.ssafy.enjoytrip.attraction.service.AttractionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/attraction")
@CrossOrigin("*")
@Api("관광지 정보 컨트롤러  API")
public class AttractionController {

	private static final Logger logger = LoggerFactory.getLogger(AttractionController.class);

	private AttractionService attractionService;

	@Autowired
	public AttractionController(AttractionService attractionService) {
		this.attractionService = attractionService;
	}
	
	@ApiOperation(value = "전국 도 목록", notes = "전국의 도 목록을 반환해 줍니다.")
	@GetMapping("/dolist")
	public ResponseEntity<?> doList() {
		logger.debug("doList 호출");
		try {
			List<DoDto> list = attractionService.getDoList();
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<DoDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "시군구 목록", notes = "doIdx에 해당하는 시군구 목록을 반환해 줍니다.")
	@ApiImplicitParams({@ApiImplicitParam(name = "doidx", value = "도 번호", required = true, dataType = "Intger", paramType = "path")})
	@GetMapping("/sigungulist/{doidx}")
	public ResponseEntity<?> sigunguList(@PathVariable("doidx") int doIdx) {
		logger.debug("sigunguList 호출 -> doIdx : {}", doIdx);
		try {
			List<SigunguDto> list = attractionService.getSigunguList(doIdx);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<SigunguDto>>(list, HttpStatus.OK);
			} else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "관광지 목록", notes = "doIdx, sigunguIdx, contentTypeId에 해당하는 관광지 목록을 반환해 줍니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "doidx", value = "도 번호", required = true, dataType = "Intger", paramType = "path"),
		@ApiImplicitParam(name = "sigunguidx", value = "시군구 번호", required = true, dataType = "Intger", paramType = "path"),
		@ApiImplicitParam(name = "contenttypeid", value = "분류 번호", required = true, dataType = "Intger", paramType = "path")
		})
	@GetMapping("/attractionlist/{doidx}/{sigunguidx}/{contenttypeid}")
	public ResponseEntity<?> attractionList(@PathVariable("doidx") int doIdx, @PathVariable("sigunguidx") int sigunguIdx, @PathVariable("contenttypeid") int contentTypeId) {
		Map<String, Integer> map = new HashMap<>();
		map.put("doIdx", doIdx);
		map.put("sigunguIdx", sigunguIdx);
		map.put("contentTypeId", contentTypeId);
		logger.debug("attractionList 호출 -> map : {}", map);
		try {
			List<AttractionDto> list = attractionService.getAttractionList(map);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<AttractionDto>>(list, HttpStatus.OK);
			} else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "관광지 검색 목록", notes = "doIdx, sigunguIdx, contentTypeId, word에 해당하는 관광지 목록을 100개까지 반환해 줍니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "doidx", value = "도 번호", required = true, dataType = "Intger", paramType = "path"),
		@ApiImplicitParam(name = "sigunguidx", value = "시군구 번호", required = true, dataType = "Intger", paramType = "path"),
		@ApiImplicitParam(name = "contenttypeid", value = "분류 번호", required = true, dataType = "Intger", paramType = "path"),
		@ApiImplicitParam(name = "word", value = "검색어", required = true, dataType = "String", paramType = "path")
		})
	@GetMapping("/search/{doidx}/{sigunguidx}/{contenttypeid}/{word}")
	public ResponseEntity<?> searchAttractionList(@PathVariable("doidx") int doIdx, @PathVariable("sigunguidx") int sigunguIdx, @PathVariable("contenttypeid") int contentTypeId, @PathVariable("word") String word) {
		Map<String, Object> map = new HashMap<>();
		map.put("doIdx", doIdx);
		map.put("sigunguIdx", sigunguIdx);
		map.put("contentTypeId", contentTypeId);
		map.put("word", word);
		logger.debug("searchAttractionList 호출 -> map : {}", map);
		try {
			List<AttractionDto> list = attractionService.searchAttractionList(map);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<AttractionDto>>(list, HttpStatus.OK);
			} else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}


	@ApiOperation(value = "관광지 상세정보", notes = "contentId에 해당하는 관광지 상세정보를 반환해 줍니다.")
	@ApiImplicitParams({@ApiImplicitParam(name = "contentid", value = "관광지 번호", required = true, dataType = "Intger", paramType = "path")})
	@GetMapping("/view/{contentid}")
	public ResponseEntity<?> view(@PathVariable("contentid") int contentId) {
		logger.debug("view 호출 -> contentId : {}", contentId);
		try {
			AttractionDto attractionDto = attractionService.getAttraction(contentId);
			if (attractionDto != null) {
				return new ResponseEntity<AttractionDto>(attractionDto, HttpStatus.OK);
			} else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
