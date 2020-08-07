package com.banana.Bathbomb.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private int memberUid;        //회원 uid
    private String memberId;      //회원 아이디
    private String memberPw;      //회원 아이디
    private String memberName;    //회원 이름
    private String memberPhoneNum;//회원 전화번호
    private String memberEmail;   //회원 이메일
    private String memberGender;  //회원 성별(남, 여)
    private String memberRegDate; //회원 가입일자
}
