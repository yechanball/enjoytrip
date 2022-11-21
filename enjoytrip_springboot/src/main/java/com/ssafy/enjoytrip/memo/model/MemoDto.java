package com.ssafy.enjoytrip.memo.model;

public class MemoDto {

	private int memoNo;
	private String userId;
	private String comment;
	private int articleNo;
	private String memoTime;
	public int getMemoNo() {
		return memoNo;
	}
	public void setMemoNo(int memoNo) {
		this.memoNo = memoNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	public String getMemoTime() {
		return memoTime;
	}
	public void setMemoTime(String memoTime) {
		this.memoTime = memoTime;
	}
	
}
