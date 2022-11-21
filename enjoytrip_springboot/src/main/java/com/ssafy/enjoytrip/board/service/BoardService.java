package com.ssafy.enjoytrip.board.service;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.board.model.BoardDto;

public interface BoardService {

	List<BoardDto> listArticle(Map<String, Object> map) throws Exception;
	int totalArticleCount(Map<String, Object> map) throws Exception;
	void writeArticle(BoardDto boardDto) throws Exception;
	BoardDto getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	void modifyArticle(BoardDto boardDto) throws Exception;
	void deleteAllMemo(int articleNo) throws Exception;
	void deleteArticle(int articleNo) throws Exception;
	
}
