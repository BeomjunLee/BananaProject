package com.banana.Bathbomb.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {
    private int cartUid;             //장바구니 uid
    private int itemUid;             //상품uid
    private int memberUid;           //회원uid
    private int cartItemPrice;     //바구니에 담은 상품가격
    private int cartItemQuantity;   //바구니에 담은 상품 수량
}
