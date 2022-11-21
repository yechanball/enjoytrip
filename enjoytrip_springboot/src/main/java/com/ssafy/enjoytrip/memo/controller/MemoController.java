package com.ssafy.enjoytrip.memo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.memo.model.MemoDto;
import com.ssafy.enjoytrip.memo.service.MemoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/memo")
@CrossOrigin("*")
@Api("게시판 댓글 컨트롤러  API")
public class MemoController {

	private static final Logger logger = LoggerFactory.getLogger(MemoController.class);

	private MemoService memoService;

	@Autowired
	public MemoController(MemoService memoService) {
		this.memoService = memoService;
	}
	
	@ApiOperation(value = "댓글 목록 불러오기", notes = "댓글 목록을 불러옵니다.")
	@GetMapping("/list/{articleno}")
	public ResponseEntity<?> list(@PathVariable("articleno") int articleNo) {
		logger.debug("memo list call article_no : {}", articleNo);
		try {
			List<MemoDto> list = memoService.listMemo(articleNo);
			if(list != null && !list.isEmpty()) {
				return new ResponseEntity<List<MemoDto>>(list, HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "게시판 댓글 작성", notes = "게시판의 댓글을 작성합니다.")
	@PostMapping("/write")
	@Transactional
	public ResponseEntity<?> write(@RequestBody MemoDto memoDto) {
		logger.debug("memo write info : {}", memoDto);
		try {
			memoService.writeMemo(memoDto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "게시판 댓글 삭제", notes = "게시판의 댓글을 삭제합니다.")
	@DeleteMapping("/delete")
	@Transactional
	public ResponseEntity<?> delete(@RequestBody MemoDto memoDto) {
		logger.debug("memo delete info : {}", memoDto);
		try {
			memoService.deleteMemo(memoDto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
