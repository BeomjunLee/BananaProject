package com.banana.Bathbomb.repository;


import com.banana.Bathbomb.domain.Member;

public interface MemberRepository {
    int save(Member member);  //회원가입 정보 저장
    Member findOne(int uid);  //uid로 회원 조회
    int update(int uid);      //회원 수정
    int delete(int uid);      //회원 삭제
}
