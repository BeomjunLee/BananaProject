package com.banana.Bathbomb.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Subscribe {
    private int subscribeUid;          //구독uid
    private int memberUid;             //회원uid
    private String subscribeStatus;

}
