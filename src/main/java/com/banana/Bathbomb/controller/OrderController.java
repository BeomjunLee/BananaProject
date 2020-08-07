package com.banana.Bathbomb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @GetMapping("/shop")//쇼핑몰으로
    public String shop(){
        return "/shop/shop";
    }

    @GetMapping("/myOrderList")//주문 목록으로
    public String myOrderList(){
        return "/myPage/myOrderList";
    }
}
