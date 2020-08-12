package com.banana.Bathbomb.controller;

import com.banana.Bathbomb.domain.Member;
import com.banana.Bathbomb.domain.Subscribe;
import com.banana.Bathbomb.service.MemberService;
import com.banana.Bathbomb.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final MemberService memberService;
    private final SubscribeService subscribeService;

    @GetMapping("/myPage/myCart")//장바구니로
    public String myOrderList(Model model, HttpSession session){
        //세션값
        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());

        Member member = memberService.findMember(sessionId);
        Subscribe subscribe = subscribeService.findSubscribe(sessionId);

        //찾은 멤버 객체 넘기기
        model.addAttribute("member", member);
        model.addAttribute("subscribe", subscribe);

        return "/myPage/myCart";
    }
}
