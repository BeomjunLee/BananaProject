package com.banana.Bathbomb.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Subscribe {
    private int sbUid;                  //구독 uid
    private int memberUid;             //회원 uid
    private String sbDeliveryAddress;   //배송 주소
    private String sbDeliveryMemo;      //배송 메모
    private int sbPrice;                 //구독 가격
    private String sbRegDate;           //구독 일자
    private int sbRepeatCnt;           //구독 연속 여부 체크
    private String sbDeliveryStatus;     //배송 상태
    private String sbCancelStatus;      //구독 취소 여부
    private String sbCancelDate;        //구독 취소 일자

}
