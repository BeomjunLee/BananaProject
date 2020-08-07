package com.banana.Bathbomb.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class MemberForm {
    private int memberUid;                //회원 uid

    @NotEmpty(message = "Please enter your ID")
    @Size(min = 1, max =  20, message = "You can enter only 1 to 20 characters")
    private String memberId;              //회원 아이디

    @NotEmpty(message = "Please enter your Password")
    @Size(min = 6, max = 20, message = "You can enter only 8 to 20 characters")
    private String memberPw;              //회원 비밀번호

    @NotEmpty(message = "Please enter your Name")
    @Size(min = 1, max =  20, message = "You can enter only 1 to 20 characters")
    private String memberName;            //회원 이름

    @NotEmpty(message = "Please enter your Phone Number")
    @Size(min = 6, max = 20, message = "You can enter only 1 to 20 characters")
    private String memberPhoneNum;        //회원 전화번호

    @NotEmpty(message = "Please enter your E-mail")
    @Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+", message = "Please check the E-mail form")
    @Size(min = 6, max = 50, message = "You can enter only 6 to 50 characters")
    private String memberEmail;           // 회원 이메일

    private String memberGender;          //회원 성별: 남자, 여자
    private String memberRegDate;         //회원 가입일자
}
