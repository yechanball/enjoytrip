package com.ssafy.enjoytrip.attraction.service;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.attraction.model.AttractionDto;
import com.ssafy.enjoytrip.attraction.model.DoDto;
import com.ssafy.enjoytrip.attraction.model.SigunguDto;

public interface AttractionService {
	List<DoDto> getDoList() throws Exception;
	List<SigunguDto> getSigunguList(int doIdx) throws Exception;
	List<AttractionDto> getAttractionList(Map<String, Integer> map) throws Exception;
	List<AttractionDto> searchAttractionList(Map<String, Object> map) throws Exception;
	AttractionDto getAttraction(int contentId) throws Exception;
}
