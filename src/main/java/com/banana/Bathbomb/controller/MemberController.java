package com.banana.Bathbomb.controller;
import com.banana.Bathbomb.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;



}
