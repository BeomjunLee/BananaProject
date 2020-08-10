package com.banana.Bathbomb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    /**
     * 홈페이지로 
     */
    @GetMapping("/")
    public String home(Model model, HttpSession session){

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
//    @GetMapping("/error")
//    public String error() throws Exception {
//        throw new Exception("error");
//    }

}
