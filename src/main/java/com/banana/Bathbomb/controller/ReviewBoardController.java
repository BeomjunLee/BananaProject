package com.banana.Bathbomb.controller;

import com.banana.Bathbomb.service.ReviewBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ReviewBoardController {
    private final ReviewBoardService reviewBoardService;
}
