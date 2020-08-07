package com.banana.Bathbomb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("/header")//헤더
    public String header(HttpSession session, Model model){
        String sessionId = "";
        //세션 확인 예외처리
        try{
            sessionId = (String)session.getAttribute("sessionId");   //처음에 세션값이 null값일때는 null에서 변환시켜주는 (String)변환
        }catch(ClassCastException e){
           //e.printStackTrace();
            sessionId = session.getAttribute("sessionId").toString();   //세션값에 값이 담겨있을땐 Object형에서 변환시켜주는 toString()
        }
        System.out.println("헤더 세션값:" + sessionId);
        model.addAttribute("sessionId", sessionId);
        return "/fragments/header";
    }
    
    @GetMapping("/")//홈페이지
    public String home(){
        return "/home";
    }

    @GetMapping("/goHome")//BATHDAL 로고 클릭시 홈으로
    public String goHome(){
        return "redirect:/";
    }

    @GetMapping("/login")//로그인 페이지
    public String goLogin(){
        return "/login";
    }

    @GetMapping("/myPage")//마이페이지
    public String goMyPage(){
        return "/myPage/myPage";
    }

}
