package com.ssafy.enjoytrip.attraction.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.attraction.model.AttractionDto;
import com.ssafy.enjoytrip.attraction.model.DoDto;
import com.ssafy.enjoytrip.attraction.model.SigunguDto;

@Mapper
public interface AttractionDao {
	List<DoDto> getDoList() throws SQLException;
	List<SigunguDto> getSigunguList(int sigunguDoIdx) throws SQLException;
	List<AttractionDto> getAttractionList(Map<String, Integer> map) throws SQLException;
	List<AttractionDto> searchAttractionList(Map<String, Object> map) throws SQLException;
	AttractionDto getAttraction(int contentId) throws SQLException;
}
