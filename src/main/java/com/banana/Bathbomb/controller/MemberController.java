package com.banana.Bathbomb.controller;
import com.banana.Bathbomb.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;



}
