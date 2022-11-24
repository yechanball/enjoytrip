package com.ssafy.enjoytrip.plan.model;

public class PlanDetailDto {
	private int planId;
	private int planOrder;
	private int contentId;
	
	public int getPlanId() {
		return planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
	public int getPlanOrder() {
		return planOrder;
	}
	public void setPlanOrder(int planOrder) {
		this.planOrder = planOrder;
	}
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
}
