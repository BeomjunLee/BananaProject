package com.banana.Bathbomb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    
    @GetMapping("/")//홈페이지
    public String home(Model model, HttpSession session){
        int sessionChk = 0;
        //세션확인
        if(session.getAttribute("sessionId") == null){
            sessionChk = 0;
        }else{
            sessionChk = 1;
        }

        model.addAttribute("sessionChk", sessionChk);
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

}
