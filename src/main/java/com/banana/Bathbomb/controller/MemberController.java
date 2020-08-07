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

        String sessionId = "";  //세션값
        String resultCode = ""; //오류코드
        
        //예외처리
        try{
            sessionId = (String)session.getAttribute("sessionId");   //처음에 세션값이 null값일때는 null에서 변환시켜주는 (String)변환
        }catch(ClassCastException e){
            //e.printStackTrace();
            sessionId = session.getAttribute("sessionId").toString();   //세션값에 값이 담겨있을땐 Object형에서 변환시켜주는 toString()
        }

        if(findMember == null){ //회원 null값 부터 체크 안하면 ERROR
            resultCode = "실패1"; //회원정보x
        }else if(findMember.getMemberId().equals(id) && findMember.getMemberPw().equals(pw) && sessionId == null){
            resultCode = "성공"; //성공
            session.setAttribute("sessionId", findMember.getMemberUid());//세션생성
        }else if(sessionId != null){
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
}
