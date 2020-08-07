package com.banana.Bathbomb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")//홈페이지
    public String home(){
        return "/home";
    }

    @GetMapping("/goHome")//BATHDAL 로고 클릭시
    public String goHome(){
        return "redirect:/";
    }

}
