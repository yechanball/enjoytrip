package com.ssafy.enjoytrip.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.board.model.BoardDto;
import com.ssafy.enjoytrip.board.dao.BoardDao;
import com.ssafy.enjoytrip.util.SizeConstant;

@Service
public class BoardServiceImpl implements BoardService {

	private BoardDao boardDao;
	
	@Autowired
	private BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	@Override
	public List<BoardDto> listArticle(Map<String, Object> map) throws Exception {
		int pgNo = (int) map.get("pgNo");
		int spl = SizeConstant.SIZE_PER_LIST; // 한 페이지에 보여질 글의 개수
		int start = (pgNo - 1) * spl;
		map.put("start", start);
		map.put("spl", spl);
		return boardDao.listArticle(map);
	}

	@Override
	public int totalArticleCount(Map<String, Object> map) throws Exception {
		return boardDao.totalArticleCount(map);
	}
	
	@Override
	public void writeArticle(BoardDto boardDto) throws Exception {
		boardDao.writeArticle(boardDto);
	}
	
	@Override
	public BoardDto getArticle(int articleNo) throws Exception {
		return boardDao.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		boardDao.updateHit(articleNo);
	}

	@Override
	public void modifyArticle(BoardDto boardDto) throws Exception {
		boardDao.modifyArticle(boardDto);
	}
	
	@Override
	public void deleteAllMemo(int articleNo) throws Exception {
		boardDao.deleteAllMemo(articleNo);
	}

	@Override
	public void deleteArticle(int articleNo) throws Exception {
		boardDao.deleteArticle(articleNo);
	}

}
