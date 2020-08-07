package com.banana.Bathbomb.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewBoard {
    private int rvBoardUid;         //리뷰 게시판 uid
    private int memberUid;          //회원 멤버 uid
    private String rvBoardTitle;    //리뷰 게시판 제목
    private String rvBoardContent;  //리뷰 게시판 내용
    private String rvBoardFile;     //리뷰 게시판 파일
    private int rvBoardViewCount;   //리뷰 게시판 조회수
    private String rvBoardRegDate;  //리뷰 게시판 등록일

}
