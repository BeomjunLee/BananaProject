package com.banana.Bathbomb.controller;

import com.banana.Bathbomb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    private MemberService memberService;

    @Autowired public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

}
