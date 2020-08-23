package com.banana.Bathbomb.service;

import com.banana.Bathbomb.domain.Cart;
import com.banana.Bathbomb.domain.Item;
import com.banana.Bathbomb.repository.CartRepository;
import com.banana.Bathbomb.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    /**
     * 장바구니에 추가
     */
    public int addCart(Cart cart, int itemUid, int sessionId){
        //상품테이블에서 상품찾기
        Item item = itemRepository.selectByUid(itemUid);

        //장바구니에 담은 물건 가격 구하기
        int totalPrice = item.getItemPrice() * cart.getCartItemQuantity();   // 총가격 = 상품가격 * 수량

        cart.setItemUid(itemUid);
        cart.setMemberUid(sessionId);   //세션값 넣기
        cart.setCartItemPrice(totalPrice);  //총가격 넣기

        return cartRepository.insert(cart);
    }

    /**
     * 장바구니 삭제
     */
    public int deleteCart(int cartUid){
        return cartRepository.deleteByUid(cartUid);
    }


    /**
     * 나의 장바구니 리스트
     */
    public List<Cart> myCartList(int memberUid){

        return cartRepository.selectAllByMemberUid(memberUid);
    }

    /**
     * uid로 장바구니 찾기
     */
    public Cart findCart(int cartUid){
        return cartRepository.selectAllByUid(cartUid);
    }
}
