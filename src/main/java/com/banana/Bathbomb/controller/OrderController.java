package com.banana.Bathbomb.controller;

import com.banana.Bathbomb.domain.Member;
import com.banana.Bathbomb.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final MemberService memberService;

    @GetMapping("/shop")//쇼핑몰으로
    public String shop(){
        return "/shop/shop";
    }

    @GetMapping("/myPage/myOrderList")//주문 목록으로
    public String myOrderList(Model model, HttpSession session){
        //세션값
        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());

        Member member = memberService.findMember(sessionId);
        //찾은 멤버 객체 넘기기
        model.addAttribute("member", member);
        return "/myPage/myOrderList";
    }
}
