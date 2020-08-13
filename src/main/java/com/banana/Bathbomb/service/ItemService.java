package com.banana.Bathbomb.service;

import com.banana.Bathbomb.domain.Category;
import com.banana.Bathbomb.domain.Item;
import com.banana.Bathbomb.repository.CategoryRepository;
import com.banana.Bathbomb.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 등록
     */
    public int insertCategory(Category category){
        return categoryRepository.insert(category);
    }

    /**
     * 카테고리 uid찾기
     */
    public int findCategoryUid(){
        return categoryRepository.selectUid();
    }

    /**
     * 상품 등록
     */
    public int insertItem(Item item){
        return itemRepository.insert(item);
    }

    /**
     * 상품 삭제
     */
    public int deleteItem(int itemUid){
        return 0;
    }

    /**
     * 상품 수정
     */
    public int updateItem(int itemUid){
        return 0;
    }

    /**
     * 상품 전체 검색
     */
    public List<Item> findAll(int startIndex, int pageSize){
        return itemRepository.selectAll(startIndex, pageSize);
    }

    /**
     * 상품 검색 by uid
     */
    public List<Item> findItemByUid(int itemUid){
        return itemRepository.selectByUid(itemUid);
    }


    /**
     * 상품 검색  by 검색창
     */
    public List<Item> findItemByName(String itemName){
        return itemRepository.selectByName(itemName);
    }

    /**
     * 카테고리로 검색
     */
    public List<Integer> findItemByCategory(String name, String sort){
        return itemRepository.selectByCategory(name, sort);
    }

    /**
     * 총 글수
     */
    public int totalListCount(){
        return itemRepository.totalListCount();
    }


}
