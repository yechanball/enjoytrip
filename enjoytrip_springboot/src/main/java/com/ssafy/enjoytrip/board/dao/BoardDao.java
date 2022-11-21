package com.ssafy.enjoytrip.board.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.board.model.BoardDto;

@Mapper
public interface BoardDao {

	List<BoardDto> listArticle(Map<String, Object> map) throws SQLException;
	int totalArticleCount(Map<String, Object> map) throws SQLException;
	int writeArticle(BoardDto boardDto) throws SQLException;
	BoardDto getArticle(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	void modifyArticle(BoardDto boardDto) throws SQLException;
	void deleteAllMemo(int articleNo) throws SQLException;
	void deleteArticle(int articleNo) throws SQLException;
	
}
