package com.banana.Bathbomb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    @GetMapping("/myPage/myCart")//장바구니로
    public String myCart(){
        return "/myPage/myCart";
    }
}
