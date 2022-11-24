package com.ssafy.enjoytrip.plan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.plan.model.PlanDetailDto;
import com.ssafy.enjoytrip.plan.model.PlanDto;
import com.ssafy.enjoytrip.plan.service.PlanService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/plan")
@CrossOrigin("*")
@Api("여행 계획 컨트롤러  API")
public class PlanController {
	
	private static final Logger logger = LoggerFactory.getLogger(PlanController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private PlanService planService;
	
	@Autowired
	public PlanController(PlanService planService) {
		this.planService = planService;
	}
	
	@ApiOperation(value = "여행계획 작성", notes = "여행 계획을 작성한다")
	@PostMapping("/write")
	@Transactional
	public ResponseEntity<?> write(@RequestBody PlanDto planDto) {
		logger.debug("plan write info : {}", planDto);
		System.out.println(planDto.getTitle());
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			planService.writePlan(planDto);
			int planId = planService.findLastPlanId(planDto.getUserId());
			List<PlanDetailDto> list = planDto.getPlanDetailList();
			if(list != null && !list.isEmpty()) {
				for (int i = 0, size = list.size(); i < size; i++) {
					list.get(i).setPlanId(planId);
					planService.writePlanDetail(list.get(i));					
				}
			}
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("여행계획 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "여행계획 목록 호출", notes = "여행 계획 목록을 반환해준다")
	@GetMapping("/list")
	@Transactional
	public ResponseEntity<?> list() {
		logger.debug("plan list call");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		List<PlanDto> list = null;
		try {
			list = planService.getPlanList();
			if (list != null && !list.isEmpty()) {
				for (int i = 0, size = list.size(); i < size; i++) {
					list.get(i).setPlanDetailList(planService.getPlanDetailList(list.get(i).getPlanId()));
				}
				resultMap.put("message", SUCCESS);
				resultMap.put("list", list);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.NO_CONTENT;
			}
		} catch (Exception e) {
			logger.error("여행계획 목록 호출 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원 여행계획 목록 호출", notes = "특정 회원의 여행 계획 목록을 반환해준다")
	@GetMapping("/list/{userid}")
	@Transactional
	public ResponseEntity<?> findList(@PathVariable("userid") @ApiParam(value = "여행계획 목록을 찾을 회원의 아이디", required = true) String userId) {
		logger.debug("plan list call -> userid: {}", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		List<PlanDto> list = null;
		try {
			list = planService.findPlanList(userId);
			if (list != null && !list.isEmpty()) {
				for (int i = 0, size = list.size(); i < size; i++) {
					list.get(i).setPlanDetailList(planService.getPlanDetailList(list.get(i).getPlanId()));
				}
				resultMap.put("message", SUCCESS);
				resultMap.put("list", list);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.NO_CONTENT;
			}
		} catch (Exception e) {
			logger.error("회원 여행계획 목록 호출 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
