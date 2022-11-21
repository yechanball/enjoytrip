package com.ssafy.enjoytrip.memo.service;

import java.util.List;

import com.ssafy.enjoytrip.member.model.MemberDto;
import com.ssafy.enjoytrip.memo.model.MemoDto;

public interface MemoService {
	public int writeMemo(MemoDto memoDto) throws Exception;
	public List<MemoDto> listMemo(int articleNo) throws Exception;
	public void deleteMemo(MemoDto memoDto) throws Exception;
}
