package com.banana.Bathbomb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/header")//헤더
    public String header(){
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
