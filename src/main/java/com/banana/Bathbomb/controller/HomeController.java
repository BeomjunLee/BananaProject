package com.banana.Bathbomb.controller;

import com.banana.Bathbomb.domain.ReviewBoard;
import com.banana.Bathbomb.service.ReviewBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ReviewBoardService reviewBoardService;
    /**
     * 홈페이지로 
     */
    @GetMapping("/")
    public String home(Model model, HttpSession session){
        List<ReviewBoard> boardList = reviewBoardService.homeBoardList();

        model.addAttribute("boardList", boardList);
        model.addAttribute("sessionId", session.getAttribute("sessionId"));
        return "/home";
    }

    /**
     * BATHDAL 로고 클릭시 홈으로
     */
    @GetMapping("/goHome")
    public String goHome(){
        return "redirect:/";
    }

    /**
     * 로그인 페이지
     */
    @GetMapping("/login")
    public String goLogin(){
        return "/login";
    }


    /**
     * 에러 페이지
     */
    @GetMapping("/error")
    public String error() throws Exception {
        throw new Exception("error");
    }

}
