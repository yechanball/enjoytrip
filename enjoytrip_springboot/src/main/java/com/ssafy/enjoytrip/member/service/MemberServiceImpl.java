package com.ssafy.enjoytrip.member.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.member.model.MemberDto;
import com.ssafy.enjoytrip.member.dao.MemberDao;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		if (memberDto.getUserId() == null || memberDto.getUserPwd() == null)
			return null;
		return sqlSession.getMapper(MemberDao.class).login(memberDto);
	}

	@Override
	public MemberDto userInfo(String userId) throws Exception {
		return sqlSession.getMapper(MemberDao.class).userInfo(userId);
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		sqlSession.getMapper(MemberDao.class).saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return sqlSession.getMapper(MemberDao.class).getRefreshToken(userId);
	}

	@Override
	public void deleteRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		sqlSession.getMapper(MemberDao.class).deleteRefreshToken(map);
	}

	@Override
	public int idCheck(String userId) throws Exception {
		return sqlSession.getMapper(MemberDao.class).idCheck(userId);
	}

	@Override
	public void joinMember(MemberDto memberDto) throws Exception {
		sqlSession.getMapper(MemberDao.class).joinMember(memberDto);
	}

	@Override
	public void modifyMember(MemberDto memberDto) throws Exception {
		sqlSession.getMapper(MemberDao.class).modifyMember(memberDto);
	}

	@Override
	public int findPassMember(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberDao.class).findPassMember(memberDto);
	}
	
	@Override
	public void deleteMember(MemberDto memberDto) throws Exception {
		sqlSession.getMapper(MemberDao.class).deleteMember(memberDto);
	}

//	@Override
//	public List<MemberDto> listMember() throws Exception {
//		return memberDao.listMember();
//	}
}
