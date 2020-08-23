package com.banana.Bathbomb.controller;

import com.banana.Bathbomb.domain.Cart;
import com.banana.Bathbomb.domain.Member;
import com.banana.Bathbomb.domain.Order;
import com.banana.Bathbomb.domain.Subscribe;
import com.banana.Bathbomb.service.CartService;
import com.banana.Bathbomb.service.MemberService;
import com.banana.Bathbomb.service.OrderService;
import com.banana.Bathbomb.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final MemberService memberService;
    private final SubscribeService subscribeService;
    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping("/myPage/myOrderList")//주문 목록으로
    public String myOrderList(Model model, HttpSession session){
        //세션값
        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());
        Member member = memberService.findMember(sessionId);
        Subscribe subscribe = subscribeService.findSubscribe(sessionId);
        List<Order> orderList = orderService.orderList(sessionId);
        //찾은 멤버 객체 넘기기
        model.addAttribute("member", member);
        model.addAttribute("subscribe", subscribe);
        model.addAttribute("orderList", orderList);
        return "/myPage/myOrderList";
    }

    /**
     * 주문 폼으로
     */
    @GetMapping("/order")
    public String orderForm(Model model, HttpSession session, @RequestParam("cartUidList") int[] cartUidList){
        //세션값
        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());
        Member member = memberService.findMember(sessionId);
        //총 요금 출력
        int charge = 0;
        for(int i = 0; i < cartUidList.length; i++) {
            charge += cartService.findCart(cartUidList[i]).getCartItemPrice();
        }
        model.addAttribute("charge", charge);
        model.addAttribute("member", member);
        model.addAttribute("cartUidList", cartUidList);
        return "/shop/orderPage";
    }
    
    /**
     * 주문 하기
     */
    @PostMapping("/order")
    public String order(Model model, HttpSession session, @RequestParam("cartUidList") int[] cartUidList, Order order){
        //세션값
        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());
        for(int i = 0; i < cartUidList.length; i++) {
            System.out.println(cartUidList[i]);
        }
        orderService.insertOrder(sessionId, cartUidList, order);
        return "/shop/orderComplete";
    }

}
