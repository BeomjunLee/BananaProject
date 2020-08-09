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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.http.HttpRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        //조회수증가
        reviewBoardService.updateViewCount(board.getRvBoardUid());

        System.out.println("게시판 uid : " + board.getRvBoardUid());
        board = reviewBoardService.readBoard(board.getRvBoardUid());
        int sessionId = 0;
        try {
            if(session.getAttribute("sessionId") != null) {
                sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());
            }else {
                sessionId = Integer.parseInt((String)session.getAttribute("sessionId"));
            }
        }catch(Exception e){
            //e.printStackTrace();
        }
        System.out.println("세션 값 : " + sessionId);

        if(sessionId == board.getMemberUid()){
            model.addAttribute("sessionChk", sessionId);
        }else{
            model.addAttribute("sessionChk", null);
        }

        model.addAttribute("sessionId", sessionId);
        model.addAttribute("rvBoardUid", board.getRvBoardUid());
        model.addAttribute("board", board);
        return "/board/reviewRead";
    }

    /**
     * 리뷰 글 쓰기 폼으로
     */
    @GetMapping("/board/reviewWrite")
    public String reviewWrite(){
        return "/board/reviewWrite";
    }

    /**
     * 리뷰 글 쓰기
     */
    @PostMapping("/board/reviewWriteChk")
    public String reviewWriteChk(Model model, ReviewBoard board, HttpSession session, @RequestPart("file") MultipartFile file, HttpServletRequest request){

        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());
        
        //회원찾기
        Member member = memberService.findMember(sessionId);
        
        //현재 날짜 담기
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//날짜 형태
        Date time = new Date();
        String boardRegDate = simpleDateFormat.format(time);

        //업로드파일
        String uploadPath = request.getSession().getServletContext().getRealPath("/file/");
        //String uploadPath = "/file/";
        System.out.println("업로드 Path: " + uploadPath);
        String fileName = System.currentTimeMillis() + file.getOriginalFilename(); //동일한 파일명 처리를 위해
        try{
            file.transferTo(new File(uploadPath + fileName));
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("파일 이름명 : "+ fileName);
        ReviewBoard reviewBoard = new ReviewBoard();
        reviewBoard.setMemberUid(sessionId);
        reviewBoard.setRvBoardWriter(member.getMemberName());
        reviewBoard.setRvBoardItem(board.getRvBoardItem());
        reviewBoard.setRvBoardTitle(board.getRvBoardTitle());
        reviewBoard.setRvBoardContent(board.getRvBoardContent());
        reviewBoard.setRvBoardFile(fileName);
        reviewBoard.setRvBoardViewCount(0);
        reviewBoard.setRvBoardRegDate(boardRegDate);

        int result = reviewBoardService.insertBoard(reviewBoard);

        model.addAttribute("resultCode", result);
        return "/board/reviewWriteChk";
    }

    /**
     * 리뷰 글 수정 폼으로
     */
    @GetMapping("/board/reviewModifi")
    public String reviewModifi(HttpSession session, Model model, ReviewBoard board){

        int rvBoardUid = board.getRvBoardUid();

        board = reviewBoardService.readBoard(board.getRvBoardUid());

        Member member = memberService.findMember(board.getMemberUid());

        model.addAttribute("rvBoardUid", rvBoardUid);
        model.addAttribute("sessionId", session.getAttribute("sessionId"));
        model.addAttribute("member", member);
        model.addAttribute("board", board);
        return"/board/reviewModifi";
    }

    /**
     * 리뷰 글 수정
     */
    @PostMapping("/board/reviewModifiChk")
    public String reviewModifiChk(Model model, ReviewBoard board, HttpSession session){
        int resultCode = 0;
        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());

        ReviewBoard findBoard = reviewBoardService.readBoard(board.getRvBoardUid());

        //주소 도용방지
        if(sessionId == findBoard.getMemberUid()) {
            resultCode = reviewBoardService.updateBoard(board);
        }else{
            resultCode = 2;
        }
        
        model.addAttribute("rvBoardUid", board.getRvBoardUid());
        model.addAttribute("resultCode", resultCode);
        return "/board/reviewModifiChk";
    }

    /**
     * 리뷰 글 삭제
     */
    @GetMapping("/board/reviewDelete")
    public String reviewDelete(Model model, ReviewBoard board, HttpSession session){
        int resultCode = 0;
        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());

        ReviewBoard findBoard = reviewBoardService.readBoard(board.getRvBoardUid());

        //주소 도용방지
        if(sessionId == findBoard.getMemberUid()) {
            resultCode = reviewBoardService.deleteBoard(board.getRvBoardUid());
        }else{
            resultCode = 2;
        }

        model.addAttribute("resultCode", resultCode);
        return "/board/reviewDelete";
    }
}
