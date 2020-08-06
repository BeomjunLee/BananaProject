package com.banana.Bathbomb.controller;

import com.banana.Bathbomb.domain.Member;
import com.banana.Bathbomb.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("")
    public String home(){
        return "";
    }

    @GetMapping("/")
        public String h(){
            return "";
        }


    @PostMapping("/")
    public String signUp(MemberForm form, HttpSession session){
        String memberId = form.getId();
        Member member = new Member();
        member.setMemberId(memberId);
        memberService.join(member);
        //멤버 찾기
        Member findMember = memberService.findMemberById(memberId);
        session.setAttribute("sessionId", findMember.getMemberUid());
        return "redirect:/homepage";
    }
}
