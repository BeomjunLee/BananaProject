package com.banana.Bathbomb.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private int uid;            //회원 uid
    private String id;          //회원 아이디
    private String name;        //회원 이름
    private String email;       // 회원 이메일
    private String phoneNum;    //회원 전화번호
    private int gender;         //회원 성별1이면 남자 0이면 여자
    private String regDate;     //회원 가입일자
}
