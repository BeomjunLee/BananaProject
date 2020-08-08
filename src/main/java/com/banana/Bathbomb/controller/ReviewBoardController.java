package com.banana.Bathbomb.controller;

import com.banana.Bathbomb.domain.Member;
import com.banana.Bathbomb.domain.Pagination;
import com.banana.Bathbomb.domain.ReviewBoard;
import com.banana.Bathbomb.service.MemberService;
import com.banana.Bathbomb.service.ReviewBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewBoardController {
    private final ReviewBoardService reviewBoardService;
    private final MemberService memberService;

    /**
     * 내가 쓴 리뷰로 
     */
    @GetMapping("/myPage/myReview")
    public String myOrderList(Model model, HttpSession session){
        //세션값
        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());

        Member member = memberService.findMember(sessionId);
        //찾은 멤버 객체 넘기기
        model.addAttribute("member", member);
        return "/myPage/myReview";
    }
    
    /**
     * 리뷰게시판으로
     */
    @GetMapping("/board/review")
    public String review(Model model, HttpSession session, @RequestParam(defaultValue = "1") int page){
        //세션넘기기
        model.addAttribute("sessionId", session.getAttribute("sessionId"));

        int totalListCnt = reviewBoardService.totalListCount(); //전체 글 수
        Pagination pagination = new Pagination(totalListCnt, page); //Pagination객체 생성후 전체 글수랑 page수를 입력

        int startIndex = pagination.getStartIndex();    //sql검색 처음시작 인덱스 0, 10, 20, 30 순으로 가야됨(페이지 수를 10개로 했으니)
        int pageSize = pagination.getPageSize();        //페이지 수(10)
        System.out.println("전체글수: " + pagination.getTotalListCnt() + " | 현재 페이지: " + pagination.getPage() + " | 시작페이지:" +
                pagination.getStartPage() + " | 끝페이지:" + pagination.getEndPage() + "");//확인용

        List<ReviewBoard> boardList = reviewBoardService.findBoardList(startIndex, pageSize);

        model.addAttribute("boardList", boardList); //List형 board를 html에 보냄
        model.addAttribute("pagination", pagination);
        return "board/review";
    }

    /**
     * 리뷰 글 읽기
     */
    @GetMapping("/board/reviewRead")
    public String reviewRead(Model model, ReviewBoard board, HttpSession session){

        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());
        Member member = memberService.findMember(sessionId);
        reviewBoardService.updateViewCount(board.getRvBoardUid());
        board = reviewBoardService.readBoard(board.getRvBoardUid());

        model.addAttribute("member", member);
        model.addAttribute("board", board);
        return "/board/reviewRead";
    }
}
