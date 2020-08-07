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
    
    @GetMapping("/signUpForm")//회원가입 폼으로
    public String SignUpForm(Model model){
        //빈 껍대기를 가지고 폼으로 넘어감
        model.addAttribute("memberForm", new MemberForm());
        return "/signUpForm";
    }

    @PostMapping("/signUp")//회원 가입 처리
    public String create(@Valid MemberForm form, BindingResult result){
        //BindingResult에서 에러를 잡아서 폼으로 넘겨줌
        if(result.hasErrors()){
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

    @PostMapping("/loginChk")//로그인 체크
    public String loginChk(HttpSession session, Member member, Model model){
        //입력된 아이디값을 받아서 회원 검색
        Member findMember = memberService.findMemberById(member.getMemberId());
        String findId = findMember.getMemberId();
        String findPw = findMember.getMemberPw();

        String sessionId = (String)session.getAttribute("sessionId");
        String resultCode = ""; //오류코드
        if(member.getMemberId().equals(findId) && member.getMemberPw().equals(findPw) && sessionId == null){
            resultCode = "성공"; //성공
            session.setAttribute("sessionId", findMember.getMemberUid());//세션생성
        }else if(member == null){
            resultCode = "실패1"; //회원정보x
        }else if(sessionId != null){
            resultCode = "실패2";//이미 로그인중
        }else{
            resultCode = "실패3";//비밀번호 일치x
        }
        model.addAttribute("resultCode", resultCode);
        return "/loginChk";
    }

}
