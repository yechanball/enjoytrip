package com.ssafy.enjoytrip.member.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.member.model.MemberDto;

@Mapper
public interface MemberDao {

	public MemberDto login(MemberDto memberDto) throws SQLException;
	public MemberDto userInfo(String userId) throws SQLException;
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	public Object getRefreshToken(String userId) throws SQLException;
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
	int idCheck(String userId) throws SQLException;
	void joinMember(MemberDto memberDto) throws SQLException;
	void modifyMember(MemberDto memberDto) throws SQLException;
	int findPassMember(MemberDto memberDto) throws SQLException;
	void deleteMember(MemberDto memberDto) throws SQLException;
	List<MemberDto> listMember() throws SQLException;
}
