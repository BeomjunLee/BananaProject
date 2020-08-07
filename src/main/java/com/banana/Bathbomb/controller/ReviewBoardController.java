package com.banana.Bathbomb.controller;

import com.banana.Bathbomb.service.ReviewBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ReviewBoardController {
    private final ReviewBoardService reviewBoardService;

    @GetMapping("/myReview")//내가 쓴 리뷰로
    public String myReview(){
        return "/myPage/myReview";
    }
}
