package com.banana.Bathbomb.controller;
import com.banana.Bathbomb.domain.Member;
import com.banana.Bathbomb.domain.Subscribe;
import com.banana.Bathbomb.service.MemberService;
import com.banana.Bathbomb.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final SubscribeService subscribeService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 회원가입 폼으로
     */
    @GetMapping("/signUp")
    public String SignUpForm(Model model){
        //빈 껍대기를 가지고 폼으로 넘어감
        model.addAttribute("memberForm", new MemberForm());
        return "/signUpForm";
    }


    /**
     * 회원 가입 로직
     */
    @PostMapping("/signUp")
    public String signUp(@Valid MemberForm form, BindingResult result, Model model){
        //BindingResult에서 에러를 잡아서 폼으로 넘겨줌
        if(result.hasErrors()){
            System.out.println("오류있음");
            return "/signUpForm";
        }

        //현재 날짜 담기
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//날짜 형태
        Date time = new Date();
        String memberRegDate = simpleDateFormat.format(time);

        Member findMember = memberService.findMemberById(form.getMemberId());

        Member member = new Member();
        member.setMemberId(form.getMemberId());
        member.setMemberPw(passwordEncoder.encode(form.getMemberPw())); //비밀번호암호화
        member.setMemberName(form.getMemberName());
        member.setMemberPhoneNum(form.getMemberPhoneNum());
        member.setMemberEmail(form.getMemberEmail());
        member.setMemberGender(form.getMemberGender());
        member.setMemberRegDate(memberRegDate);

        System.out.println("비밀번호 암호화 한 값 : " + member.getMemberPw());
        int resultCode = 0;
        try {
            resultCode = memberService.join(member);
        }catch(IllegalStateException e){
            resultCode = 2;
            System.out.println("아이디 중복 오류");
        }
        model.addAttribute("resultCode", resultCode);
        return "/signUpChk";
    }


    /**
     * 로그인 체크 로직
     */
    @PostMapping("/loginChk")
    public String loginChk(HttpSession session, MemberForm form, Model model){
        //입력된 아이디값을 받아서 회원 검색
        Member findMember = memberService.findMemberById(form.getMemberId());
        String id = form.getMemberId();
        System.out.println("입력된 id값:"+id);

        String pw = form.getMemberPw();
        System.out.println("입력된 pw값:"+pw);

        String resultCode = ""; //오류코드

        if(findMember == null){ //회원 null값 부터 체크 안하면 ERROR
            resultCode = "실패1"; //회원정보x
        }else if(findMember.getMemberId().equals(id) && session.getAttribute("sessionId") == null){
            if(passwordEncoder.matches(pw, findMember.getMemberPw())) { //암호화한 비밀번호값 비교(사용자가 입력한값 먼저, 암호환된 pw)
                resultCode = "성공"; //성공
                session.setAttribute("sessionId", findMember.getMemberUid());//세션생성
            }else{
                resultCode = "실패3";//비밀번호 일치x
            }
        }else if(session.getAttribute("sessionId")  != null){//세션값이 있으면
            resultCode = "실패2";//이미 로그인중
        }
        model.addAttribute("resultCode", resultCode);
        return "/loginChk";
    }


    /**
     * 로그아웃
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("sessionId");//세션제거
        return "/logout";
    }

    /**
     * 회원 수정 폼
     */
    @GetMapping("/myPage/userModifi")
    public String userModifiForm(Model model, HttpSession session){
        //빈객체를 회원수정폼으로
        model.addAttribute("memberForm", new MemberModifiForm());

        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());

        Member findMember = memberService.findMember(sessionId);
        model.addAttribute("member", findMember);
        return "/myPage/userModifi";
    }

    /**
     * 회원 수정
     */
    @PostMapping("/myPage/userModifi")
    public String userModifi(MemberForm form, HttpSession session, Model model){
        //세션값
        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());

        Member member = new Member();
        member.setMemberPw(passwordEncoder.encode(form.getMemberPw())); //암호화
        member.setMemberName(form.getMemberName());
        member.setMemberPhoneNum(form.getMemberPhoneNum());
        member.setMemberEmail(form.getMemberEmail());
        member.setMemberGender(form.getMemberGender());
        member.setMemberUid(sessionId);

        int resultCode = memberService.updateMember(member);
        model.addAttribute("resultCode", resultCode);
        return "/myPage/userModifiChk";
    }


    /**
     * 마이 페이지 회원정보
     */
    @GetMapping("/myPage")//마이페이지
    public String myPage(Model model, HttpSession session){
        //세션값
        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());

        Member member = memberService.findMember(sessionId);
        Subscribe subscribe = subscribeService.findSubscribe(sessionId);
        
        //찾은 멤버 객체 넘기기
        model.addAttribute("member", member);
        model.addAttribute("subscribe", subscribe);
        return "/myPage/myPage";
    }

    /**
     * 아이디 중복 오류
     */
    @ExceptionHandler(IllegalStateException.class)
    public String illegalStateExceptionHandler(Model model, Exception e){
        e.printStackTrace();
        return "/signUpForm";
    }

}
