package com.banana.Bathbomb.controller;
import com.banana.Bathbomb.domain.Member;
import com.banana.Bathbomb.domain.Subscribe;
import com.banana.Bathbomb.service.MemberService;
import com.banana.Bathbomb.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class SubscribeController {
    private final SubscribeService subscribeService;
    private final MemberService memberService;

    /**
     *  내 구독내역으로
     */
    @GetMapping("/myPage/mySubscribeList")//구독 내역으로
    public String myOrderList(Model model, HttpSession session){
        //세션값
        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());

        Member member = memberService.findMember(sessionId);
        Subscribe subscribe = subscribeService.findSubscribe(sessionId);

        model.addAttribute("subscribe", subscribe);
        //찾은 멤버 객체 넘기기
        model.addAttribute("member", member);
        return "/myPage/mySubscribeList";
    }

    /**
     * 구독하기 버튼클릭
     */
    @GetMapping("/subscribe/subscribe")
    public String subscribe(){
        return "/subscribe/subscribeType";
    }

    /**
     * 구독하기 결제 정보
     */
    @GetMapping("/subscribe/subscribeResult")
    public String subscribeResult(Model model, Subscribe subscribe){
        int charge = subscribe.getSbPrice();
        model.addAttribute("charge", charge);
        return "/subscribe/subscribeResult";
    }

    /**
     * 구독하기 결제 폼
     */
    @GetMapping("/subscribe/subscribeInfo")
    public String subscribeInfo(HttpSession session, Model model){
        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());
        Member member = memberService.findMember(sessionId);
        Subscribe subscribe = new Subscribe();
        int charge = subscribe.getSbPrice();
        model.addAttribute("charge", charge);
        model.addAttribute("member", member);
        return "/subscribe/subscribeInfo";
    }

    /**
     * 구독하기 결제 처리
     */
    @PostMapping("/subscribe/subscribeChk")
    public String subscribeChk(Subscribe subscribe, HttpSession session, Model model){
        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());
        //현재 날짜 담기
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//날짜 형태
        Date time = new Date();
        String regDate = simpleDateFormat.format(time);

        Subscribe sc = new Subscribe();

        sc.setMemberUid(sessionId); //회원 uid
        sc.setSbDeliveryAddress(subscribe.getAddressA() + " " + subscribe.getAddressB()); //배송 주소
        sc.setSbDeliveryMemo(subscribe.getSbDeliveryMemo());//배송 메모
        sc.setSbPrice(subscribe.getSbPrice());//구독 가격
        sc.setSbRegDate(regDate);//구독 일자
        sc.setSbDeliveryStatus("배송 준비중"); //배송상태
        sc.setSbCancelStatus("구독중");
        //구독테이블에서 회원정보 찾기
        Subscribe findSubscribe = subscribeService.findSubscribe(sessionId);
        
        int resultCode = 0;
        if(findSubscribe != null){
            resultCode = 2;
            model.addAttribute("resultCode", resultCode);
            return "/subscribe/subscribeChk";
        }else if(findSubscribe == null){
            resultCode = subscribeService.doSubscribe(sc);
            return "/subscribe/subscribeComplete";
        }else{
            model.addAttribute("resultCode", resultCode);
            return "/subscribe/subscribeChk";
        }
    }

    /**
     * 구독 결제 완료창
     */
    @GetMapping("/subscribe/subscribeComplete")
    public String subscribeComplete(){
        return "/subscribe/subscribeComplete";
    }

    /**
     * 구독 취소
     */
    @GetMapping("/subscribe/unSubscribe")
    public String unSubscribe(HttpSession session, Model model){

        int resultCode = 0;
        int uid = Integer.parseInt(session.getAttribute("sessionId").toString());
        Subscribe subscribe = subscribeService.findSubscribe(uid);

        if(subscribe.getSbDeliveryStatus().equals("배송 준비중")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date time = new Date();
            String localTime = simpleDateFormat.format(time);
            resultCode = subscribeService.deleteSubscribe("취소신청", localTime, uid);
        }else{
            resultCode = 2; //이미 배송중이라서 취소 불가능
        }
        model.addAttribute("resultCode", resultCode);
        return "/subscribe/unSubscribe";
    }


    /**
     * 설문조사
     */
    @GetMapping("/subscribe/subscribeSurvey")
    public String subscribeSurvey(){
        return "/subscribe/subscribeSurvey";
    }

    /**
     * 설문결과
     */
    @GetMapping("/subscribe/subscribeResult2")
    public String subscribeR(){
        return "/subscribe/subscribeResult2";
    }
}
