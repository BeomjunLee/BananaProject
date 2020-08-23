package com.banana.Bathbomb.controller;

import com.banana.Bathbomb.domain.Cart;
import com.banana.Bathbomb.domain.Item;
import com.banana.Bathbomb.domain.Member;
import com.banana.Bathbomb.domain.Subscribe;
import com.banana.Bathbomb.service.CartService;
import com.banana.Bathbomb.service.ItemService;
import com.banana.Bathbomb.service.MemberService;
import com.banana.Bathbomb.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final MemberService memberService;
    private final SubscribeService subscribeService;
    private final CartService cartService;
    private final ItemService itemService;

    /**
     * 내 장바구니로
     */
    @GetMapping("/myPage/myCart")
    public String myOrderList(Model model, HttpSession session){
        //세션값
        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());

        Member member = memberService.findMember(sessionId);
        Subscribe subscribe = subscribeService.findSubscribe(sessionId);
        List<Cart> cartList = cartService.myCartList(sessionId);

        List<Item> itemList = new ArrayList<>();

        if(cartList != null) {  //null 오류 처리
            //카트리스트의 cartUid를 이용해 item도 똑같이 List를 만들어 담아줌(cartList와 itemList의 index의 itemUid가 같음) -> view에 item테이블의 값들을 나타내기위해
            for (int i = 0; i < cartList.size(); i++) {
                itemList.add(i, itemService.findItemByUid(cartList.get(i).getItemUid()));
            }
        }
        //찾은 객체 넘기기
        model.addAttribute("member", member);
        model.addAttribute("subscribe", subscribe);
        model.addAttribute("cartList", cartList);
        model.addAttribute("itemList", itemList);

        return "/myPage/myCart";
    }
    
    /**
     * 장바구니 추가
     */
    @GetMapping("/shop/cart")
    public String addCart(Cart cart, HttpSession session, @RequestParam("itemUid") int itemUid, Model model){
        int resultCode = 0; //오류
        
        //로그인 안하면 로그인창으로
        if(session.getAttribute("sessionId") == null){
            resultCode = 2; //로그인 해야됨
            model.addAttribute("resultCode", resultCode);
            model.addAttribute("itemUid", itemUid);
            return "/shop/cartChk";
        }

        //세션값
        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());

        resultCode = cartService.addCart(cart, itemUid, sessionId);  //장바구니 추가 (성공시 1리턴)

        model.addAttribute("resultCode", resultCode);
        return "/shop/cartChk";
    }

    /**
     * 장바구니 삭제
     */
    @GetMapping("/shop/deleteCart")
    public String deleteCart(@RequestParam("cartUid") int cartUid){

        //장바구니 삭제
        cartService.deleteCart(cartUid);

        //기존의 페이지를 다시 호출하는것이다 forward로 호출하면 req resp 유지문제가 생길수 있으므로 redirect로 다시 호출
        return "redirect:/myPage/myCart";
    }
}
