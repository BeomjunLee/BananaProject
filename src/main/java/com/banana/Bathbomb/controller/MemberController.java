package com.banana.Bathbomb.controller;
import com.banana.Bathbomb.domain.Member;
import com.banana.Bathbomb.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String signUp(@Valid MemberForm form, BindingResult result){
        //BindingResult에서 에러를 잡아서 폼으로 넘겨줌
        if(result.hasErrors()){
            System.out.println("오류있음");
            return "/signUpForm";
        }

        //현재 날짜 담기
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//날짜 형태
        Date time = new Date();
        String memberRegDate = simpleDateFormat.format(time);
        
        Member member = new Member();
        member.setMemberId(form.getMemberId());
        member.setMemberPw(form.getMemberPw());
        member.setMemberName(form.getMemberName());
        member.setMemberPhoneNum(form.getMemberPhoneNum());
        member.setMemberEmail(form.getMemberEmail());
        member.setMemberGender(form.getMemberGender());
        member.setMemberRegDate(memberRegDate);

        memberService.join(member);

        return "redirect:/login";
    }


    /**
     * 로그인 체크 로직
     */
    @PostMapping("/loginChk")
    public String loginChk(HttpSession session, MemberForm form, Model model){
        //입력된 아이디값을 받아서 회원 검색
        Member findMember = memberService.findMemberById(form.getMemberId());
        System.out.println("입력된 로그인 아이디 값:"+form.getMemberId());
        String id = form.getMemberId();
        System.out.println("일치하는 회원 id값:"+id);
        String pw = form.getMemberPw();
        System.out.println("일치하는 회원 pw값:"+pw);

        String resultCode = ""; //오류코드

        if(findMember == null){ //회원 null값 부터 체크 안하면 ERROR
            resultCode = "실패1"; //회원정보x
        }else if(findMember.getMemberId().equals(id) && findMember.getMemberPw().equals(pw) && session.getAttribute("sessionId") == null){
            resultCode = "성공"; //성공
            session.setAttribute("sessionId", findMember.getMemberUid());//세션생성
        }else if(session.getAttribute("sessionId")  != null){//세션값이 있으면
            resultCode = "실패2";//이미 로그인중
        }else{
            resultCode = "실패3";//비밀번호 일치x
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
    public String userModifi(@Valid MemberModifiForm form, BindingResult result, HttpSession session, Model model){
        //BindingResult에서 에러를 잡아서 폼으로 넘겨줌
        if(result.hasErrors()){
            System.out.println("오류있음");
            return "/myPage/userModifi";
        }

        int sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());

        Member member = new Member();
        member.setMemberPw(form.getMemberPw());
        member.setMemberName(form.getMemberName());
        member.setMemberPhoneNum(form.getMemberPhoneNum());
        member.setMemberEmail(form.getMemberEmail());
        member.setMemberGender(form.getMemberGender());
        member.setMemberUid(sessionId);

        int resultCode = memberService.updateMember(member);
        model.addAttribute("resultCode", resultCode);
        return "/myPage/userModifiChk";
    }

}
