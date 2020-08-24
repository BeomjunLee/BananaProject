package com.banana.Bathbomb.service;

import com.banana.Bathbomb.domain.Cart;
import com.banana.Bathbomb.domain.Order;
import com.banana.Bathbomb.repository.CartRepository;
import com.banana.Bathbomb.repository.ItemRepository;
import com.banana.Bathbomb.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문 생성
     */
    @Transactional
    public void insertOrder(int sessionId, int[] cartUidList, Order order){
        List<Cart> cart = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date time = new Date();
        String regDate = simpleDateFormat.format(time);

        order.setMemberUid(sessionId);
        order.setOrderDeliveryAddress(order.getAddressA() + " " + order.getAddressB());
        order.setOrderDeliveryStatus("배송 준비중");
        order.setOrderCancelStatus("주문 완료");
        order.setOrderRegDate(regDate);

        for(int i = 0; i < cartUidList.length; i++) {
            cart.add(cartRepository.selectAllByUid(cartUidList[i]));
            order.setOrderItemName(itemRepository.selectByUid(cart.get(i).getItemUid()).getItemName());   //상품 이름 넣기
            order.setOrderPrice(cart.get(i).getCartItemPrice());    //주문 가격 넣기

            int result = orderRepository.insert(order);  //주문
            if(result == 1) cartRepository.deleteByUid(cartUidList[i]); //주문 성공시 장바구니 삭제
        }
    }

    /**
     * 주문 총 요금
     */
    public int orderPrice(int[] cartUidList){
        int charge = 0;
        for(int i = 0; i < cartUidList.length; i++) {
            charge += cartRepository.selectAllByUid(cartUidList[i]).getCartItemPrice();
        }
        return charge;
    }

    /**
     * 주문 목록 보기
     */
    public List<Order> orderList(int memberUid){
        return orderRepository.selectAll(memberUid);
    }

    /**
     * 주문 삭제
     */
    @Transactional
    public int deleteOrder(Order order){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date time = new Date();
        String regDate = simpleDateFormat.format(time);
        order.setOrderCancelStatus("취소 신청");
        order.setOrderCancelDate(regDate);
        return orderRepository.update(order);
    }
}
