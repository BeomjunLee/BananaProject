package com.banana.Bathbomb.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private int itemUid;            //상품uid
    private int categoryUid;       //카테고리 uid
    private String itemName;        //상품 이름
    private int itemPrice;          //상품 가격
    private String itemContent;     //상품 내용
    private String itemImage;       //상품 이미지
    private int itemStock;          //상품 재고
}
