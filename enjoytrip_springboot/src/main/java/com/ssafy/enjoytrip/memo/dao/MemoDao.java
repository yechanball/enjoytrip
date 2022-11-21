package com.ssafy.enjoytrip.memo.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.member.model.MemberDto;
import com.ssafy.enjoytrip.memo.model.MemoDto;

@Mapper
public interface MemoDao {
	
	public int writeMemo(MemoDto memoDto) throws SQLException;
	public List<MemoDto> listMemo(int articleNo) throws SQLException;
	public void deleteMemo(MemoDto memoDto) throws SQLException;

}
