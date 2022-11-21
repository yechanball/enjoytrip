package com.ssafy.enjoytrip.attraction.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.attraction.dao.AttractionDao;
import com.ssafy.enjoytrip.attraction.model.AttractionDto;
import com.ssafy.enjoytrip.attraction.model.DoDto;
import com.ssafy.enjoytrip.attraction.model.SigunguDto;

@Service
public class AttractionServiceImpl implements AttractionService {
	
	private AttractionDao attractionDao;
	
	@Autowired
	private AttractionServiceImpl(AttractionDao attractionDao) {
		this.attractionDao = attractionDao;
	}

	@Override
	public List<DoDto> getDoList() throws Exception {
		return attractionDao.getDoList();
	}

	@Override
	public List<SigunguDto> getSigunguList(int doIdx) throws Exception {
		return attractionDao.getSigunguList(doIdx);
	}

	@Override
	public List<AttractionDto> getAttractionList(Map<String, Integer> map) throws Exception {
		return attractionDao.getAttractionList(map);
	}

	@Override
	public AttractionDto getAttraction(int contentId) throws Exception {
		return attractionDao.getAttraction(contentId);
	}

}
