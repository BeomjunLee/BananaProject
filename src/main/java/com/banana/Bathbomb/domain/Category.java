package com.banana.Bathbomb.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    private int categoryUid;            //카테고리 uid
    private String categoryName;        //이름
    private String categorySort;        //종류
}
