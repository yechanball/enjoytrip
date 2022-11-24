package com.ssafy.enjoytrip.board.controller;

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

import com.ssafy.enjoytrip.board.model.BoardDto;
import com.ssafy.enjoytrip.board.service.BoardService;
import com.ssafy.enjoytrip.util.SizeConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/board")
@CrossOrigin("*")
@Api("게시판 컨트롤러  API")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@ApiOperation(value = "게시판 글 목록", notes = "게시판의 글 목록을 반환해 줍니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pgno", value = "페이지 번호", required = true, dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "orderby", value = "정렬 기준", required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "orderbydir", value = "정렬 방향", required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "key", value = "검색 기준", required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "word", value = "검색어", required = true, dataType = "String", paramType = "query")
		})
	@GetMapping("/list")
	@Transactional
	public ResponseEntity<?> list(@RequestParam("pgno") int pgNo, @RequestParam("orderby") String orderBy, @RequestParam("orderbydir") String orderByDir, @RequestParam("key") String key, @RequestParam("word") String word) {
		logger.debug("board list 호출");
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("pgNo", pgNo);
			if(("articleNo").equals(orderBy)) {
				orderBy = "article_no";
			}
			map.put("orderBy", orderBy);
			map.put("orderByDir", orderByDir);
			if(("userId").equals(key)) {
				key = "user_id";
			}
			map.put("key", key);
			map.put("word", word);
			logger.debug("list call input : {}", map);
			List<BoardDto> list = boardService.listArticle(map);
			
			int totalCnt = boardService.totalArticleCount(map);
			int spl = SizeConstant.SIZE_PER_LIST;
			int ls = SizeConstant.LIST_SIZE;
			int totalPg = (totalCnt-1)/spl + 1;
			int startPg = (pgNo-1)/ls * ls + 1;
			int endPg = (startPg + ls - 1);
			endPg = (totalPg < endPg) ? totalPg : endPg;
			boolean canPre = (pgNo <= ls) ? false : true;
			boolean canNext = (endPg == totalPg) ? false : true;
			logger.debug("현재 페이지 : "+pgNo + ", 총 게시글수 : "+ totalCnt + ", 총 페이지수 : " + totalPg + ", 시작 페이지 : " + startPg + ", 종료 페이지 : "+ endPg+", 이전 가능 : "+canPre+ ", 다음 가능 : "+ canNext);
				
			Map<String, Object> result = new HashMap<>();
			result.put("articles", list);
			result.put("startpg", startPg);
			result.put("pgno", pgNo);
			result.put("endpg", endPg);
			result.put("pre", canPre);
			result.put("next", canNext);
			
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "게시판 글 작성", notes = "게시판의 글을 작성합니다.")
	@PostMapping("/write")
	@Transactional
	public ResponseEntity<?> write(@RequestBody BoardDto boardDto) {
		logger.debug("board write info : {}", boardDto);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		try {
			boardService.writeArticle(boardDto);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "게시판 글 보기", notes = "게시판의 글을 보여줍니다.")
	@GetMapping("/view/{articleno}")
	@Transactional
	public ResponseEntity<?> write(@PathVariable("articleno") int articleNo) {
		logger.debug("view article_no : {}", articleNo);
		try {
			BoardDto boardDto = boardService.getArticle(articleNo);
			if(boardDto != null) {
				boardService.updateHit(articleNo);
				return new ResponseEntity<BoardDto>(boardDto, HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "게시판 글 수정", notes = "게시판의 글을 수정합니다.")
	@PutMapping("/{articleno}")
	@Transactional
	public ResponseEntity<?> modify(@PathVariable("articleno") int articleNo, @RequestBody BoardDto boardDto) {
		boardDto.setArticleNo(articleNo);
		logger.debug("modify article info : {}", boardDto);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		try {
			boardService.modifyArticle(boardDto);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "게시판 글 삭제", notes = "게시판의 글을 삭제합니다.")
	@DeleteMapping("/{articleno}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable("articleno") int articleNo) {
		logger.debug("delete article_no : {}", articleNo);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		try {
			boardService.deleteAllMemo(articleNo);
			boardService.deleteArticle(articleNo);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
