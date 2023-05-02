package edu.kh.comm.board.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.comm.board.model.service.BoardService;
import edu.kh.comm.board.model.vo.BoardDetail;
import edu.kh.comm.member.model.service.MemberService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService service;
	
	
	//게시글 목록 조회
	
	//@PathVariable("value") : URL 경로에 포함되어 있는 값을 변수로 사용할 수 있게 하는 역할
	// -> 자동으로 request scope에 등록하는 것은 jsp에서 ${} EL 작성 가능하다는 의미!
	
	
	@GetMapping("/list/{boardCode}")
	public String boardList(@PathVariable("boardCode") int boardCode
							, @RequestParam(value="cp", required=false, defaultValue="1") int cp
							, Model model) {
		
		//게시글 목록 조회 서비스 호출
		//1) 게시판 이름 조회
		//2) 페이지네이션 객체 생성
		//3) 게시글 목록 조회
		
		Map<String, Object> map = null;
		map = service.selectBoardList(cp, boardCode);
				
		model.addAttribute("map",map);
		
		return "board/boardList";
	}
	
	//게시글 상세 조회
	@GetMapping("/detail/{boardCode}/{boardNo}")
	public String boardDetail(@PathVariable("boardCode") int boardCode
							,@PathVariable("boardNo") int boardNo
							,@RequestParam(value="cp", required=false, defaultValue="1") int cp
							, Model model
							) {
		
		//게시글 상세 조회 서비스 호출
		BoardDetail detail = service.selectBoardDetail(boardNo);
		model.addAttribute("detail", detail);
		return "board/boardDetail";
		
		
		//@ModelAttribute는 별도의 required 속성이 없어서 무조건 필수!
		// -> 세션에 loginMember가 엇으면 예외 발생
		
		//해결방법 : HttpSession을 이용
		
		//상세 조회 성공시
		//-> 쿠키를 이용한 조회수 중복 증가 방지 코드 + 본인의 글은 조회수 증가 x

		
		
		
		
	}
}
