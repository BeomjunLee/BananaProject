package com.banana.Bathbomb.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class MemberModifiForm {
    @NotBlank(message = "Please enter your Password")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "Please use special characters and numbers 8 to 20 characters") //특수문자 + 숫자가 들어간 8~20문자
    private String memberPw;              //회원 비밀번호

    @NotBlank(message = "Please enter your Re-enter password")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "Please use special characters and numbers 8 to 20 characters") //특수문자 + 숫자가 들어간 8~20문자
    private String memberPw2;             //회원 비밀번호 확인

    @NotBlank(message = "please enter your name")
    @Size(min = 1, max =  20, message = "You can enter only 1 to 20 characters")
    private String memberName;            //회원 이름

    @NotBlank(message = "Please enter your Phone Number")
    @Size(min = 6, max = 20, message = "You can enter only 6 to 20 numbers")
    private String memberPhoneNum;        //회원 전화번호

    @NotBlank(message = "Please enter your E-mail")
    @Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+", message = "Please check the E-mail form")
    @Size(min = 6, max = 50, message = "You can enter only 6 to 50 characters")
    private String memberEmail;           // 회원 이메일

    @NotBlank(message = "Please choose your gender")
    private String memberGender;          //회원 성별: 남자, 여자
}
