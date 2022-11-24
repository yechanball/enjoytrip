package com.ssafy.enjoytrip.plan.service;

import java.util.List;

import com.ssafy.enjoytrip.plan.model.PlanDetailDto;
import com.ssafy.enjoytrip.plan.model.PlanDto;

public interface PlanService {
	void writePlan(PlanDto planDto) throws Exception;
	int findLastPlanId(String userId) throws Exception;
	void writePlanDetail(PlanDetailDto planDetailDto) throws Exception;
	List<PlanDto> getPlanList() throws Exception;
	List<PlanDto> findPlanList(String userId) throws Exception;
	List<PlanDetailDto> getPlanDetailList(int planId) throws Exception;
}
