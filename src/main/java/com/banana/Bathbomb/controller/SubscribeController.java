package com.banana.Bathbomb.controller;
import com.banana.Bathbomb.domain.Member;
import com.banana.Bathbomb.service.MemberService;
import com.banana.Bathbomb.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class SubscribeController {
    private final SubscribeService subscribeService;
    private final MemberService memberService;

    @GetMapping("/myPage/mySubscribeList")//구독 내역으로
    public String myOrderList(Model model, HttpSession session){
        //세션값
        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());

        Member member = memberService.findMember(sessionId);
        //찾은 멤버 객체 넘기기
        model.addAttribute("member", member);
        return "/myPage/mySubscribeList";
    }

    @GetMapping("/subscribe/subscribe")//구독하기 버튼
    public String subscribe(){
        return "/subscribe/subscribeType";
    }

    @GetMapping("/subscribe/subscribeResult")//구독 결제 정보
    public String subscribeResult(){
        return "/subscribe/subscribeResult";
    }

    @GetMapping("/subscribe/subscribeInfo")//구독 결제 입력
    public String subscribeInfo(){
        return "/subscribe/subscribeInfo";
    }

}
