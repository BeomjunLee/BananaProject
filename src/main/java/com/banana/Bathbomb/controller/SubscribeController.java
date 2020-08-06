package com.banana.Bathbomb.controller;
import com.banana.Bathbomb.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SubscribeController {
    private final SubscribeService subscribeService;


}
