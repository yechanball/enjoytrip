package com.ssafy.enjoytrip.memo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.member.model.MemberDto;
import com.ssafy.enjoytrip.memo.dao.MemoDao;
import com.ssafy.enjoytrip.memo.model.MemoDto;

@Service
public class MemoServiceImpl implements MemoService {

	private MemoDao memoDao;
	
	@Autowired
	private MemoServiceImpl(MemoDao memoDao) {
	    this.memoDao = memoDao;
	}

	@Override
	public int writeMemo(MemoDto memoDto) throws Exception {
	    return memoDao.writeMemo(memoDto);
	}

	@Override
	public List<MemoDto> listMemo(int articleNo) throws Exception {
	    return memoDao.listMemo(articleNo);
	}

	@Override
	public void deleteMemo(MemoDto memoDto) throws Exception {
		memoDao.deleteMemo(memoDto);
	}
	
}
