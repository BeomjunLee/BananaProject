package com.banana.Bathbomb.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private int orderUid;                   //주문 uid
    private int memberUid;                  //회원 uid
    private String orderDeliveryAddress;    //배송주소
    private String orderDeliveryMemo;       //배송메모
    private String orderDeliveryStatus;     //배송상태
    private int orderPrice;                 //주문가격
    private String orderRegDate;            //주문날짜
    private String orderCancelStatus;       //주문취소상태
    private String orderCancelDate;         //주문취소날짜
}
