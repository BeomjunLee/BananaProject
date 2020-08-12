package com.banana.Bathbomb.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Subscribe {
    private int sbUid;               //구독 uid
    private int memberUid;           //회원 uid
    private String addressA;    //address
    private String addressB;    //detial address
    private String sbDeliveryAddress;//배송 주소
    private String sbDeliveryMemo;   //배송 메모
    private int sbPrice = 14000;             //구독 가격
    private String sbRegDate;        //구독 일자
    private String sbDeliveryStatus; //배송 상태
    private String sbCancelStatus;      //구독 취소 상태  취소완료, 취소신청, 구독중
    private String sbCancelDate;     //구독 취소 일자
}
