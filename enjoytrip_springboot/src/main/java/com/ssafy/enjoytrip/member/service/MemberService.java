package com.ssafy.enjoytrip.member.service;

import com.ssafy.enjoytrip.member.model.MemberDto;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception; // 로그인
	public MemberDto userInfo(String userId) throws Exception; // 회원 정보 호출
	public void saveRefreshToken(String userId, String refreshToken) throws Exception; // 리프레쉬 토큰 저장
	public Object getRefreshToken(String userId) throws Exception; // 리프레쉬 토큰 호출
	public void deleteRefreshToken(String userId) throws Exception; // 리프레쉬 토큰 삭제
	int idCheck(String userId) throws Exception; // 아이디 중복검사
	void joinMember(MemberDto memberDto) throws Exception; // 회원가입
	void modifyMember(MemberDto memberDto) throws Exception; // 회원 정보 수정
	int findPassMember(MemberDto memberDto) throws Exception; /// 비밀번호 찾기
	void deleteMember(MemberDto memberDto) throws Exception; // 회원 정보 삭제	
//	List<MemberDto> listMember() throws Exception; // 회원 목록 호출 
}
