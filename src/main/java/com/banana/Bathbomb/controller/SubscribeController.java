package com.banana.Bathbomb.controller;

import com.banana.Bathbomb.domain.Subscribe;
import com.banana.Bathbomb.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class SubscribeController {
    private final SubscribeService subscribeService;

    @GetMapping("/")
        public String fail(){
            return "/";
    }

    @GetMapping("/")
    public String subscribe(HttpSession session){
        Subscribe subscribe = new Subscribe();
        int memberUid = Integer.parseInt(session.getAttribute("sessionId").toString());
        System.out.println(memberUid);

        subscribe.setMemberUid(memberUid);
        subscribe.setSubscribeStatus("구독중");

        System.out.println(subscribe.getMemberUid() + " " + subscribe.getSubscribeStatus());
        Subscribe findSubscribe = subscribeService.findSubscribe(memberUid);
        if(findSubscribe == null){
            subscribeService.doSubscribe(subscribe);
            return "/";
        }
        return "/";
    }

}
