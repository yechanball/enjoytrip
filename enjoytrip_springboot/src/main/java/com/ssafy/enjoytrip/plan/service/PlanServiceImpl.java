package com.ssafy.enjoytrip.plan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.plan.dao.PlanDao;
import com.ssafy.enjoytrip.plan.model.PlanDetailDto;
import com.ssafy.enjoytrip.plan.model.PlanDto;

@Service
public class PlanServiceImpl implements PlanService {
	
	private PlanDao planDao;
	
	@Autowired
	private PlanServiceImpl(PlanDao planDao) {
		this.planDao = planDao;
	}
	
	@Override
	public void writePlan(PlanDto planDto) throws Exception {
		planDao.writePlan(planDto);
	}
	
	@Override
	public int findLastPlanId(String userId) throws Exception {
		return planDao.findLastPlanId(userId);
	}

	@Override
	public void writePlanDetail(PlanDetailDto planDetailDto) throws Exception {
		planDao.writePlanDetail(planDetailDto);
	}

	@Override
	public List<PlanDto> getPlanList() throws Exception {
		return planDao.getPlanList();
	}

	@Override
	public List<PlanDto> findPlanList(String userId) throws Exception {
		return planDao.findPlanList(userId);
	}

	@Override
	public List<PlanDetailDto> getPlanDetailList(int planId) throws Exception {
		return planDao.getPlanDetailList(planId);
	}

}
