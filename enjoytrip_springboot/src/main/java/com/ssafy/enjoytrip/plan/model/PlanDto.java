package com.ssafy.enjoytrip.plan.model;

import java.util.List;

public class PlanDto {
	private int planId;
	private String title;
	private String content;
	private String userId;
	private String planTime;
	private List<PlanDetailDto> planDetailList;
	
	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPlanTime() {
		return planTime;
	}

	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}

	public List<PlanDetailDto> getPlanDetailList() {
		return planDetailList;
	}

	public void setPlanDetailList(List<PlanDetailDto> planDetailList) {
		this.planDetailList = planDetailList;
	}

}
