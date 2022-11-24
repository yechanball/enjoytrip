package com.ssafy.enjoytrip.plan.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.plan.model.PlanDetailDto;
import com.ssafy.enjoytrip.plan.model.PlanDto;

@Mapper
public interface PlanDao {
	void writePlan(PlanDto planDto) throws SQLException;
	int findLastPlanId(String userId) throws SQLException;
	void writePlanDetail(PlanDetailDto planDetailDto) throws SQLException;
	List<PlanDto> getPlanList() throws SQLException;
	List<PlanDto> findPlanList(String userId) throws SQLException;
	List<PlanDetailDto> getPlanDetailList(int planId) throws SQLException;	
}
